package com.tickets.chat;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.websocket.CloseReason;
import javax.websocket.EncodeException;
import javax.websocket.HandshakeResponse;
import javax.websocket.OnError;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import javax.websocket.server.ServerEndpointConfig;
import javax.websocket.server.ServerEndpointConfig.Configurator;

@ServerEndpoint(value = "/chat/{roomId}", encoders = ChatMessageCodec.class, decoders = ChatMessageCodec.class, configurator = ChatEndpoint.EndpointConfigurator.class)
public class ChatEndpoint {
	public static final Map<Long, ChatRoom> currentChatRooms = new HashMap<>();
	private static final Map<Long, ArrayList<Session>> roomSessions = new HashMap<>();
	private static final Map<Session, Long> sessionToRoomId = new HashMap<>();
	private static final Map<Session, HttpSession> httpSessions = new HashMap<>();

	@OnOpen
	public void onOpen(Session session, @PathParam("roomId") long roomId) {
		HttpSession httpSession = (HttpSession) session.getUserProperties().get("httpSession");
		try {
			if (httpSession == null || httpSession.getAttribute("username") == null) {
				session.close(new CloseReason(CloseReason.CloseCodes.VIOLATED_POLICY, "You are not logged in"));
				return;
			}
			String username = (String) httpSession.getAttribute("username");
			session.getUserProperties().put("username", username);
			ChatMessage message = new ChatMessage();
			message.setTimestamp(OffsetDateTime.now());
			message.setUser(username);
			ChatRoom chatRoom;
			if (roomId < 1) {
				message.setType(ChatMessage.Type.STARTED);
				message.setContent(username + " started the chat session.");
				chatRoom = new ChatRoom();
				chatRoom.setCustomerUsername(username);
				chatRoom.setCreationMessage(message);
				ChatEndpoint.currentChatRooms.put(chatRoom.getRoomId(), chatRoom);
				ArrayList<Session> rs = new ArrayList<>();
				rs.add(session);
				ChatEndpoint.roomSessions.put(chatRoom.getRoomId(), rs);
			} else {
				message.setType(ChatMessage.Type.JOINED);
				message.setContent(username + " joined the chat room.");
				chatRoom = ChatEndpoint.currentChatRooms.get(roomId);
				ChatMessage joinMessage = new ChatMessage();
				joinMessage.setTimestamp(message.getTimestamp());
				joinMessage.setType(message.getType());
				ArrayList<Session> rs = ChatEndpoint.roomSessions.get(chatRoom.getRoomId());
				for (Session s : rs) {
					String susername = (String) s.getUserProperties().get("username");
					joinMessage.setUser(susername);
					joinMessage.setContent(susername + " joined the chat room.");
					session.getBasicRemote().sendObject(joinMessage);
				}
				rs.add(session);
			}
			ChatEndpoint.sessionToRoomId.put(session, chatRoom.getRoomId());
			ChatEndpoint.httpSessions.put(session, httpSession);
			chatRoom.log(message);
			for (Session s : ChatEndpoint.roomSessions.get(chatRoom.getRoomId())) {
				s.getBasicRemote().sendObject(message);
			}
			this.getWebSessionFromHttpSession(httpSession).add(session);

		} catch (IOException | EncodeException e) {
			this.onError(session, e);
		}
	}

	@OnError
	public void onError(Session session, Throwable e)  {
		ChatMessage message = new ChatMessage();
		message.setUser((String) session.getUserProperties().get("username"));
		message.setType(ChatMessage.Type.ERROR);
		message.setTimestamp(OffsetDateTime.now());
		message.setContent(message.getUser() + " left the chat due to an error.");
		long roomId = ChatEndpoint.sessionToRoomId.get(session);
		ArrayList<Session> sessionList = ChatEndpoint.roomSessions.get(roomId);
		HttpSession httpSession = ChatEndpoint.httpSessions.get(session);
		if (httpSession != null)
			this.getWebSessionFromHttpSession(httpSession).remove(session);
		if (sessionList != null && sessionList.size() > 0) {
			for (Session s : sessionList) {
				try {
					s.getBasicRemote().sendObject(message);
				} catch (IOException | EncodeException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}

	}

	private synchronized ArrayList<Session> getWebSessionFromHttpSession(HttpSession httpSession) {
		if (httpSession.getAttribute("webSessions") == null) {
			httpSession.setAttribute("webSessions", new ArrayList<>());
		}
		return (ArrayList<Session>) httpSession.getAttribute("webSessions");
	}

	public static class EndpointConfigurator extends Configurator {
		@Override
		public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response) {
			super.modifyHandshake(sec, request, response);
			sec.getUserProperties().put("httpSession", request.getHttpSession());
		}
	}
}

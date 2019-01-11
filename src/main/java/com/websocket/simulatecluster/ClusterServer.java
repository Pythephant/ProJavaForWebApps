package com.websocket.simulatecluster;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;

import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/clusterNode/{nodeId}")
public class ClusterServer {
	private static ArrayList<Session> clients = new ArrayList<>();

	@OnOpen
	public void onOpen(Session session, @PathParam("nodeId") String nodeId) throws IOException {
		System.out.println("Server: client nodeId:" + nodeId + " connected to cluster.");
		ClusterMessage message = new ClusterMessage(nodeId, "Joined the cluster.");
		for (Session c : clients) {
			c.getBasicRemote().sendBinary(ByteBuffer.wrap(toBytes(message)));
			;
		}
		clients.add(session);
	}

	@OnMessage
	public void onMessage(Session session, byte[] input) throws IOException, ClassNotFoundException {
		for (Session c : clients) {
			if (c != session) {
				c.getBasicRemote().sendBinary(ByteBuffer.wrap(input));
			}
		}
	}

	private static byte[] toBytes(ClusterMessage message) throws IOException {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		new ObjectOutputStream(outStream).writeObject(message);
		return outStream.toByteArray();
	}
}

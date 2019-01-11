package com.websocket.simulatecluster;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.URI;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.ClientEndpoint;
import javax.websocket.CloseReason;
import javax.websocket.ContainerProvider;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;

import com.tickets.SessionServlet;

@ClientEndpoint
public class ClusterClientServlet extends HttpServlet {
	private Session serverSession;
	private String nodeId;

	@Override
	public void init() throws ServletException {
		this.nodeId = this.getInitParameter("nodeId");
		String path = this.getServletContext().getContextPath() + "/clusterNode/" + nodeId;
		try {
			URI uri = new URI("ws", "localhost:8080", path, null, null);
			serverSession = ContainerProvider.getWebSocketContainer().connectToServer(this, uri);
		} catch (Exception e) {
			throw new ServletException("Cannot connect to " + path + ".", e);
		}
	}

	@Override
	public void destroy() {
		try {
			serverSession.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ClusterMessage message = new ClusterMessage(this.nodeId,
				"request from:' " + req.getRemoteAddr() + "', query:'" + req.getQueryString() + "'.");

		try (OutputStream output = this.serverSession.getBasicRemote().getSendStream();
				ObjectOutputStream stream = new ObjectOutputStream(output)) {
			stream.writeObject(message);
		}
		resp.getWriter().write("OK");
	}

	@OnMessage
	public void onMessage(InputStream in) throws IOException, ClassNotFoundException {

		try (ObjectInputStream oin = new ObjectInputStream(in)) {
			ClusterMessage message = (ClusterMessage) oin.readObject();
			System.out.println("INFO: Node[" + this.nodeId + "] received a message from Node[" + message.getNodeId()
					+ "], content is '" + message.getMessage() + "'.");
		}
	}

	@OnClose
	public void onClose(CloseReason reason) {
		CloseReason.CloseCode code = reason.getCloseCode();
		if (code != CloseReason.CloseCodes.NORMAL_CLOSURE) {
			System.out.println("Close unnormally: " + code + ", reason:" + reason.getReasonPhrase());
		}
	}

}

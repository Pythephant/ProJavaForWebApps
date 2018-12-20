package com.tickets;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

public class SessionRegistry {
	private static Map<String, HttpSession> SESSIONS = new HashMap<>();

	public static void addSession(HttpSession session) {
		SESSIONS.put(session.getId(), session);
	}

	public static void updateSession(HttpSession newSession, String oldSessionId) {
		synchronized (SESSIONS) {
			SESSIONS.remove(oldSessionId);
			SESSIONS.put(newSession.getId(), newSession);
		}
	}

	public static void removeSession(HttpSession session) {
		SESSIONS.remove(session.getId());
	}

	public static Map<String, HttpSession> getAllSessions() {
		return SESSIONS;
	}

	private SessionRegistry() {
	}
}

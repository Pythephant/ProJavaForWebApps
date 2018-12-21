package com.tickets;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionIdListener;
import javax.servlet.http.HttpSessionListener;

@WebListener
public class SessionListener implements HttpSessionListener, HttpSessionIdListener {

	@Override
	public void sessionIdChanged(HttpSessionEvent arg0, String arg1) {
		SessionRegistry.updateSession(arg0.getSession(), arg1);
	}

	@Override
	public void sessionCreated(HttpSessionEvent arg0) {
		SessionRegistry.addSession(arg0.getSession());
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent arg0) {
		SessionRegistry.removeSession(arg0.getSession());
	}

	private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd (HH:mm:ss)");

	private String date() {
		return format.format(new Date());
	}

}

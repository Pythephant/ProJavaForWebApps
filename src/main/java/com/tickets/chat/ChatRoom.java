package com.tickets.chat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.websocket.Session;

import com.fasterxml.jackson.core.JsonGenerator.Feature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class ChatRoom {
	private static long roomIdSeed = 1L;

	private long roomId = roomIdSeed++;
	private String customerUsername;
	private ChatMessage creationMessage;
	private final List<ChatMessage> chatLog = new ArrayList<>();

	public String getCustomerUsername() {
		return customerUsername;
	}

	public void setCustomerUsername(String customerUsername) {
		this.customerUsername = customerUsername;
	}

	public ChatMessage getCreationMessage() {
		return creationMessage;
	}

	public void setCreationMessage(ChatMessage creationMessage) {
		this.creationMessage = creationMessage;
	}

	public long getRoomId() {
		return roomId;
	}

	public void log(ChatMessage message) {
		this.chatLog.add(message);
	}

	public void writeChatLog(File file) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.findAndRegisterModules();
		mapper.configure(Feature.AUTO_CLOSE_TARGET, false);
		mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

		try (FileOutputStream stream = new FileOutputStream(file)) {
			mapper.writeValue(stream, this.chatLog);
		}
	}

}

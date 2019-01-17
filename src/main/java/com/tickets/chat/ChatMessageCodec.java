package com.tickets.chat;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

import com.fasterxml.jackson.core.JsonGenerator.Feature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ChatMessageCodec implements Encoder.BinaryStream<ChatMessage>, Decoder.BinaryStream<ChatMessage> {

	private static ObjectMapper MAPPER = new ObjectMapper();

	static {
		MAPPER.findAndRegisterModules();
		MAPPER.configure(Feature.AUTO_CLOSE_TARGET, false);
	}

	@Override
	public void init(EndpointConfig config) {

	}

	@Override
	public void destroy() {

	}

	@Override
	public ChatMessage decode(InputStream is) throws DecodeException, IOException {
		return MAPPER.readValue(is, ChatMessage.class);
	}

	@Override
	public void encode(ChatMessage object, OutputStream os) throws EncodeException, IOException {
		MAPPER.writeValue(os, object);

	}

}

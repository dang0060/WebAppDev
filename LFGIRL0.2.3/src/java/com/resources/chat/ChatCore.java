/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.resources.chat;


import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Singleton;
 
import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/chat/{room}", encoders = ChatMessageEncoder.class, decoders = ChatMessageDecoder.class)
public class ChatCore {
        
	private final Logger log = Logger.getLogger(getClass().getName());
 
	@OnOpen
	public void open(final Session session, @PathParam("room") final String room) {
		log.info("session opened and bound to room: " + room);
                log.info("session opened with id: " + session.getId());
		session.getUserProperties().put("room", room);
	}
 
	@OnMessage
	public void onMessage(final Session session, final ChatMessage chatMessage) {
		String room = (String) session.getUserProperties().get("room");
		try {
			for (Session s : session.getOpenSessions()) {
				if (s.isOpen()
						&& room.equals(s.getUserProperties().get("room"))) {
					s.getBasicRemote().sendObject(chatMessage);
				}
			}
		} catch (IOException | EncodeException e) {
			log.log(Level.WARNING, "onMessage failed", e);
		}
	}
        
        @OnClose
        public void onClose(final Session session) {
            log.info("session closed with id: " + session.getId());
        }
}
package org.nuxeo.ecm.eval.service;

import java.util.List;

/**
 * Simple Service interface for doing a fake Chat
 *
 * @author Tiry (tdelprat@nuxeo.com)
 *
 */
public interface ChatService {

    /**
     * Send a message to a room from a user
     *
     * @param user
     * @param room
     * @param message
     */
    void sendMessage(String user, String room, String message);

    /**
     * Get all messages for a room
     *
     * @param room
     * @return a preformated list of messages
     */
    List<String> getMessages(String room);

}

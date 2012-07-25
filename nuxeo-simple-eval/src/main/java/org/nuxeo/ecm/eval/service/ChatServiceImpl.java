package org.nuxeo.ecm.eval.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Implementation if the {@link ChatService}.
 * 
 * This implementation is very simple and probably even a little too much, so at
 * some point you may need to fix it.
 * 
 * @author Tiry (tdelprat@nuxeo.com)
 * 
 */
public class ChatServiceImpl implements ChatService {

    protected static ChatService instance;

    protected Map<String, List<ChatEntry>> messages = new HashMap<String, List<ChatEntry>>();

    protected static final int MSG_HISTORY = 15;

    protected static final String CMD_PREFIX = ":";

    protected static final String INTERACTIVE_PREFIX = "chat:";

    static synchronized ChatService instance() {
        if (instance == null) {
            instance = new ChatServiceImpl();
        }
        return instance;
    }

    protected void GC(String room) {
        while (getRoom(room).size() > MSG_HISTORY) {
            getRoom(room).remove(0);
        }
    }

    @Override
    public List<String> getMessages(String room) {
        GC(room);
        List<String> strMessages = new ArrayList<String>();

        if (getRoom(room) != null) {
            for (ChatEntry entry : getRoom(room)) {
                strMessages.add(entry.getAsLine());
            }
        }
        return strMessages;
    }

    protected List<ChatEntry> getRoom(String room) {
        if (!messages.containsKey(room)) {
            messages.put(room, new ArrayList<ChatEntry>());
            messages.get(room).add(
                    new ChatEntry(true, "system", "room " + room
                            + " is now open"));
        }
        return messages.get(room);
    }

    @Override
    public void sendMessage(String user, String room, String message) {

        if (message.startsWith(CMD_PREFIX)) {
            execCommand(user, room, message.substring(CMD_PREFIX.length()));
            return;
        }

        if (message.startsWith(INTERACTIVE_PREFIX)) {
            interactiveChat(user, room, message);
            return;
        }

        getRoom(room).add(new ChatEntry(user, message));

        GC(room);
    }

    protected void interactiveChat(String user, String room, String msgKey) {

        String key = msgKey.trim().toLowerCase();
        String secret = user + ":" + room + ":"
                + this.getClass().getSimpleName();

        String answer = MessageServiceImpl.instance().getMessage(key, secret);

        if (!user.equals("Nono")) {
            // echo user's message
            getRoom(room).add(
                    new ChatEntry(false, user,
                            msgKey.substring(INTERACTIVE_PREFIX.length())));
        }

        if (answer == null) {
            getRoom(room).add(
                    new ChatEntry(true, "Nono",
                            "Cannot understand what you say"));
        } else {
            getRoom(room).add(new ChatEntry(false, "Nono", answer));
        }

    }

    protected void execCommand(String user, String room, String command) {
        if ("clear".equals(command)) {
            getRoom(room).clear();
            getRoom(room).add(new ChatEntry(true, user, " cleared the room "));
        } else if ("close".equals(command)) {
            messages.remove(room);
        } else if ("moderate".equals(command)) {
            if (getRoom(room).size() > 0) {
                int idx = getRoom(room).size() - 1;
                ChatEntry moderated = getRoom(room).get(idx);
                if (!moderated.isAdminMessage()) {
                    getRoom(room).remove(idx);
                    getRoom(room).add(
                            new ChatEntry(true, user,
                                    " moderated last message from "
                                            + moderated.getUser()));
                } else {
                    getRoom(room).add(
                            new ChatEntry(true, user,
                                    " moderation request failed"));
                }
            }
        } else if ("activeUsers".equals(command)) {

            List<String> activeUsers = new ArrayList<String>();

            for (ChatEntry msg : getRoom(room)) {
                if (!msg.isAdminMessage()) {
                    if (!activeUsers.contains(msg.getUser())) {
                        activeUsers.add(msg.getUser());
                    }
                }
            }
            getRoom(room).add(
                    new ChatEntry(true, user, " currently "
                            + activeUsers.size() + " active users "));
        }

        else if ("kick".equals(command)) {
            if (getRoom(room).size() > 0) {
                ChatEntry lastmsg = getRoom(room).get(getRoom(room).size() - 1);
                for (ChatEntry msg : getRoom(room)) {
                    if (!msg.isAdminMessage()) {
                        if (lastmsg.getUser().equals(msg.getUser())) {
                            getRoom(room).remove(msg);
                        }
                    }
                }
                getRoom(room).add(
                        new ChatEntry(true, user, " kicked "
                                + lastmsg.getUser()));
            }
        }

    }

}

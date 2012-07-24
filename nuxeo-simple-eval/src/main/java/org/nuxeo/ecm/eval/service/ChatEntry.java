package org.nuxeo.ecm.eval.service;

/**
 * Stores a Chat entry and manage the formatting
 *
 * @author Tiry (tdelprat@nuxeo.com)
 *
 */
public class ChatEntry {

    protected static final String MSG_SEP = "> ";

    protected static final String ADM_MSG_MARKER = " ** ";

    String user;

    String message;

    boolean adminMessage = false;

    public ChatEntry(boolean adminMessage, String user, String message) {
        this.adminMessage = adminMessage;
        this.user = user;
        this.message = message;
    }

    public ChatEntry(String user, String message) {
        this(false, user, message);
    }

    public String getUser() {
        return user;
    }

    public String getMessage() {
        return message;
    }

    public boolean isAdminMessage() {
        return adminMessage;
    }

    public String getAsLine() {

        if (adminMessage) {
            return "<span class=\"adm\">" + ADM_MSG_MARKER + "(" + user + ") "
                    + message + ADM_MSG_MARKER + "</span>";
        } else {
            return "<span class=\"usr\">" + user + MSG_SEP + "</span>"
                    + message;
        }
    }

}

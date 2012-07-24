package org.nuxeo.ecm.eval.service;

/**
 * The Message service is used to manage encrypted messages that are used by
 * some screens but also some configuration.
 *
 * Of course, trying to completely hack this MessageService is a possibility.
 * But given the fact that the keys and secrets are based on the actual
 * application runtime parameters, you will have to read the code anyway ...
 *
 * @author Tiry (tdelprat@nuxeo.com)
 *
 */
public interface MessageService {

    public String getMessage(String key, String secret);

    public String getMessage(String key);

    public String cypher(String key, String input) throws Exception;

    public String decypher(String key, String input) throws Exception;

}

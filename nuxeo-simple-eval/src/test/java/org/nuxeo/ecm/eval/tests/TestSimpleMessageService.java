package org.nuxeo.ecm.eval.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.BeforeClass;
import org.junit.Test;
import org.nuxeo.ecm.eval.service.MessageService;
import org.nuxeo.ecm.eval.service.ServiceFactory;
import org.nuxeo.ecm.eval.webengine.WelcomeWO;

/**
 * Tests to validate the Message service that is used everywhere in the
 * application
 * 
 * @author Tiry (tdelprat@nuxeo.com)
 * 
 */
public class TestSimpleMessageService {

    private static MessageService ms;

    protected static final boolean verboseMode = false;

    @BeforeClass
    public static void setUp() throws Exception {
        ms = ServiceFactory.getService(MessageService.class);
        if (verboseMode) {
            System.out.println("Verbose mode is on");
        } else {
            System.out.println("Verbose mode is off");
        }

    }

    @Test
    public void testCyperDecypher() throws Exception {

        String message = "This is a test qsdfdqsdqsd qsdqsdqsd qsdqsdqsd \n qsdqsd";
        String key = "This is my key";

        String cypherText = ms.cypher(key, message);
        String clearText = ms.decypher(key, cypherText);

        assertEquals(message, clearText);

    }

    @Test
    public void testClearMessage() throws Exception {

        String clearMessage = ms.getMessage("test");
        assertNotNull(clearMessage);
        if (verboseMode) {
            System.out.println("### clearMessage for key test = "
                    + clearMessage);
        }

    }

    @Test
    public void testDecypherMessage() throws Exception {

        String secret = "This is my key";

        @SuppressWarnings("unused")
        String cypherMessage = ms.getMessage("testCypher");
        String clearMessage = ms.getMessage("testCypher", secret);

        assertEquals("This was a crypted text", clearMessage);
    }

    @Test
    public void testWelcomeMessages() {

        String secret = WelcomeWO.class.getName();
        String msg = ms.getMessage("welcomeTest", secret);
        assertNotNull(msg);
        if (verboseMode) {
            System.out.println("Welcome message hint= " + msg);
        }
    }
}

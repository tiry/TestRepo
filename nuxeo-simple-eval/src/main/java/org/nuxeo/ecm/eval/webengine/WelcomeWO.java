package org.nuxeo.ecm.eval.webengine;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.nuxeo.ecm.webengine.model.Resource;
import org.nuxeo.ecm.webengine.model.TypeNotFoundException;
import org.nuxeo.ecm.webengine.model.WebObject;
import org.nuxeo.runtime.api.Framework;

/**
 * WebObject that is reponsible for welcoming the user
 *
 * @author Tiry (tdelprat@nuxeo.com)
 *
 */
@WebObject(type = "welcome")
public class WelcomeWO extends BaseWO {

    private String getSecret() {
        return this.getClass().getCanonicalName();
    }

    @GET
    @Produces("text/html")
    public Object doGet() throws Exception {
        return getView("doWelcome").arg("k", getSecret());
    }

    public String getMessage(String key, String secret) {

        String message = readMessage(key, secret);
        if (Framework.isTestModeSet()) {
            message = message + "\n" + readMessage(key + "Test", secret);
        }
        return message;
    }

    @POST
    @Produces("text/html")
    @Path(value = "continue")
    public Object doContinue() {
        return ctx.newObject("wrong");
    }

    @Path(value = "start/{objectName}")
    public Resource doBegin(@PathParam("objectName") String objectName) {

        objectName = normalize(objectName);

        try {
            return ctx.newObject(objectName);
        } catch (TypeNotFoundException e) {
            String message = readMessage(objectName, getSecret());
            if (message != null) {
                return ctx.newObject("hint", message);
            } else {
                return ctx.newObject("wrong");
            }
        }
    }

}

package org.nuxeo.ecm.eval.webengine;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.nuxeo.ecm.eval.service.MessageService;
import org.nuxeo.ecm.eval.service.ServiceFactory;
import org.nuxeo.ecm.webengine.model.Resource;
import org.nuxeo.ecm.webengine.model.TypeNotFoundException;
import org.nuxeo.ecm.webengine.model.impl.DefaultObject;

/**
 * Provides common methods for all WebObjects used in this sample
 *
 * @author Tiry (tdelprat@nuxeo.com)
 *
 */
public class BaseWO extends DefaultObject {

    protected static final Log log = LogFactory.getLog(BaseWO.class);

    @Override
    protected void initialize(Object[] args)
    {
        super.initialize(args);
        log.debug("accessing WebEobject " + this.getType().getName());

    }

    protected MessageService getMessageService() {
        return ServiceFactory.getService(MessageService.class);
    }

    protected String readMessage(String key, String secret) {
        return getMessageService().getMessage(key, secret);
    }

    protected String normalize(String value) {
        return value.toLowerCase().trim().replace("-", "").replace(" ", "");
    }

    @GET
    @Path("help")
    @Produces("text/html")
    public Object help() {
        String helpMessage = readMessage("help"
                + this.getClass().getSimpleName(), getModule().getName()
                + "Help");
        if (helpMessage == null) {
            helpMessage =  "Sorry no help for now";
        }
        return getView("../help").arg("message",helpMessage);
    }

    @Path(value = "{objectName}")
    public Resource traverse(@PathParam("objectName") String objectName) {
        try {
            return ctx.newObject(objectName);
        } catch (TypeNotFoundException e) {
            String translatedObjectName = readMessage(objectName, objectName);
            String key = getContext().getRequest().getParameter("key");
            if (key==null) {
                key = objectName;
            } else {
                key = objectName + key;
            }
            return ctx.newObject(translatedObjectName, key);
        }
    }

    @GET
    @Path("next")
    @Produces("text/plain")
    public String getNext(@QueryParam("key") String key) {
        String next = readMessage(key,"k"+this);
        if (next==null) {
            return "";
        }
        return next;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();

        sb.append(this.getClass().getSimpleName());
        sb.append(" | ");
        sb.append(this.getType().getName());

        return sb.toString();
    }



}

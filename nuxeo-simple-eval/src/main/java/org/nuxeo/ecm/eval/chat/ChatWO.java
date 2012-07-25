package org.nuxeo.ecm.eval.chat;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import org.nuxeo.common.utils.FileUtils;
import org.nuxeo.ecm.eval.service.ChatService;
import org.nuxeo.ecm.eval.service.ServiceFactory;
import org.nuxeo.ecm.eval.webengine.BaseWO;
import org.nuxeo.ecm.webengine.model.WebObject;

/**
 * WebObject used to expose the ChatService via JAX-RS
 * 
 * @author Tiry (tdelprat@nuxeo.com)
 * 
 */
@WebObject(type = "chat")
public class ChatWO extends BaseWO {

    @GET
    @Produces("text/html")
    public Object doGet() throws Exception {
        return getView("index");
    }

    @GET
    @Produces("text/html")
    @Path(value = "room/{roomName}")
    public Object getRoom(@PathParam("roomName")
    String roomName) throws Exception {
        List<String> messages = ServiceFactory.getService(ChatService.class).getMessages(
                roomName);
        return getView("room").arg("messages", messages);
    }

    @POST
    @Produces("text/html")
    @Path(value = "{roomName}/{userName}")
    public Object postMessage(@PathParam("roomName")
    String roomName, @PathParam("userName")
    String userName, @Context
    HttpServletRequest request) throws Exception {

        String message = FileUtils.read(getContext().getRequest().getInputStream());

        ServiceFactory.getService(ChatService.class).sendMessage(userName,
                roomName, message);

        return Response.status(200).build();
    }

    @GET
    @Produces("text/html")
    @Path(value = "bsod")
    public Object bsod() throws Exception {
        return getView("bsod");
    }
}

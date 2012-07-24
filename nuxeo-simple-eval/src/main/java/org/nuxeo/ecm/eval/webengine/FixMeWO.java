package org.nuxeo.ecm.eval.webengine;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.nuxeo.ecm.webengine.model.WebObject;

@WebObject(type = "ecma262")
public class FixMeWO extends BaseWO {

    @GET
    @Produces("text/html")
    public Object doGet() throws Exception {
        return getView("index");
    }

    @GET
    @Path("hint/{token}")
    @Produces("text/html")
    public Object getHint(@PathParam("token") String token) throws Exception {
        String message = readMessage("continue", normalize(token));
        if (message == null) {
            return Response.status(404).build();
        }
        return getView("message").arg("message", message);
    }

}

package org.nuxeo.ecm.eval.webengine;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.nuxeo.ecm.webengine.model.Resource;
import org.nuxeo.ecm.webengine.model.WebObject;
import org.nuxeo.ecm.webengine.model.impl.ModuleRoot;

@WebObject(type = "eval")
@Path("/eval")
public class EvalWebModuleRoot extends ModuleRoot {

    @GET
    @Produces("text/html")
    public Object doGet() throws Exception {
        return getView("index");
    }

    @Path(value = "welcome")
    public Resource main() {
        return ctx.newObject("welcome");
    }

    @Path(value = "${path}")
    public Resource traverse(@PathParam("path")
    String path) {
        return ctx.newObject(path);
    }

    @Path(value = "chat")
    public Resource chat() {
        return ctx.newObject("chat");
    }

}

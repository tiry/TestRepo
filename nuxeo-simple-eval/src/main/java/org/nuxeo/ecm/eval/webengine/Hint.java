package org.nuxeo.ecm.eval.webengine;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;

import org.nuxeo.ecm.webengine.model.WebObject;
import org.nuxeo.ecm.webengine.model.impl.DefaultObject;

@WebObject(type = "hint")
public class Hint extends DefaultObject {

    private String message;

    @Override
    protected void initialize(Object... args) {
        message = (String) args[0];
    }

    @GET
    @Produces("text/html")
    public Object doGet() throws Exception {
        return getView("giveHint");
    }

    public String getMessage() {
        return message;
    }

}

package org.nuxeo.ecm.eval.webengine;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;

import org.nuxeo.ecm.webengine.model.WebObject;
import org.nuxeo.ecm.webengine.model.impl.DefaultObject;

@WebObject(type = "wrong")
public class Wrong extends DefaultObject {

    @GET
    @Produces("text/html")
    public Object doGet() throws Exception {
        return getView("youarewrong");
    }

}

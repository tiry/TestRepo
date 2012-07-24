package org.nuxeo.ecm.eval.webengine;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;

import org.nuxeo.ecm.webengine.model.WebObject;

@WebObject(type = "simpleResource")
public class SimpleResourceWO extends BaseWO {

    protected String resourceName = "No name :(";

    @Override
    protected void initialize(Object[] args)
    {
        super.initialize(args);
        if (args!=null && args.length>0) {
            resourceName = (String) args[0];
        }
    }

    @GET
    @Produces("text/html")
    public Object doGet() throws Exception {
        return getView("index").arg("name", resourceName);
    }

}

package org.nuxeo.ecm.eval.webengine;

import java.io.File;
import java.io.InputStream;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.nuxeo.common.utils.FileUtils;
import org.nuxeo.ecm.webengine.model.Resource;
import org.nuxeo.ecm.webengine.model.WebObject;

@WebObject(type = "jaxeres")
public class JaxeresWO extends BaseWO {

    protected String initkey = null;

    @Override
    protected void initialize(Object[] args) {
        super.initialize(args);
        if (args != null && args.length > 0) {
            initkey = (String) args[0];
        }
    }

    @GET
    @Produces("text/html")
    public Object doGet() throws Exception {
        String secret = initkey;
        String key = "javaWelcomeMessage";
        String msg = readMessage(key, secret);

        if (msg == null) {
            return Response.status(500).build();
        }
        return getView("index").arg("msg", msg);
    }

    @GET
    @Produces("text/html")
    @Path(value = "simpleView")
    public Object getSimpleView() {
        return getView("simpleView");
    }

    @GET
    @Path(value = "simpleResource")
    public Resource getResource() {
        return ctx.newObject("simpleResource");
    }

    @Path(value = "resource/{name}")
    public Resource getResourceByName(@PathParam("name")
    String name) {
        return ctx.newObject("simpleResource", name);
    }

    @GET
    @Path(value = "qna")
    public Resource getQuestions() {
        return ctx.newObject("qna", initkey);
    }

    @POST
    @Produces("text/html")
    @Path(value = "upload")
    public Object doPost() throws Exception {
        String fileName = getContext().getRequest().getHeader("X-File-Name");
        String fileSize = getContext().getRequest().getHeader("X-File-Size");

        InputStream fileStream = getContext().getRequest().getInputStream();

        File tmp = new File("/tmp/" + fileName);
        FileUtils.copyToFile(fileStream, tmp);

        System.out.println(" uploaded " + fileName + " (" + fileSize + "b)");
        return "uploaded";
    }

    @GET
    @Produces("text/plain")
    @Path(value = "download")
    public String getData() throws Exception {
        return "Text data that comes from Nuxeo server : "
                + System.currentTimeMillis();
    }

    public String getInitkey() {
        return initkey;
    }

}

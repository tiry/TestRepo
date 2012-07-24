package org.nuxeo.ecm.eval.qna;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status.Family;
import javax.ws.rs.core.Response.StatusType;

import org.nuxeo.ecm.eval.webengine.BaseWO;
import org.nuxeo.ecm.webengine.model.WebObject;

@WebObject(type = "qna")
public class QnaWO extends BaseWO {

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
        String msg = readMessage("welcome" + this.getClass().getSimpleName(),
                initkey);
        String json = readMessage(
                "questions" + this.getClass().getSimpleName(), initkey);
        QnaList qnas = QnaList.fromJSON(json);
        String qnasjson = qnas.asJSON();
        return getView("askQuestions").arg("qnas", qnas).arg("msg", msg).arg(
                "qnasjson", qnasjson);
    }

    @POST
    @Produces("text/html")
    public Object doPost(QnaList qnas) throws Exception {

        String key = "qna";
        String secret = "";
        for (QnaItem qna : qnas) {
            secret = secret + qna.getAnswer();
        }
        key = secret.length() + key;
        String message = readMessage(key, secret);

        if (message == null) {
            log.error("Submited responses are not correct, please try again !");
            return Response.status(new StatusType() {
                @Override
                public int getStatusCode() {
                    return 406;
                }

                @Override
                public String getReasonPhrase() {
                    return "Your answers are not correct, please try again !";
                }

                @Override
                public Family getFamily() {
                    return Response.Status.Family.CLIENT_ERROR;
                }
            }).entity("Please check your responses and try again !!!").build();
        }
        return getView("answers").arg("message", message);
    }

}

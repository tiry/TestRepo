package org.nuxeo.ecm.eval.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.nuxeo.ecm.eval.service.MessageService;
import org.nuxeo.ecm.eval.service.ServiceFactory;

/**
 *
 * Simple Http Servlet.
 *
 * Hint 1 : You should not be looking at this class unless told so ...
 *
 * Hint 2 : Before this servlet can be run, it needs to be declared and mapped in the web.xml.
 * Because of the modular architecture of Nuxeo, binding WebContainer resources is done via the deployment-fragment.
 *
 * @author Tiry (tdelprat@nuxeo.com)
 *
 */
public class OldSchoolHandler extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String key = req.getRequestURI();

        String jsrNumber = null;

        String htmlContent = ServiceFactory.getService(MessageService.class).getMessage(key, jsrNumber);

        if (htmlContent==null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "probably bad key or bad secret");
        } else {
            // ???
        }
    }
}

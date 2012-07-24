package org.nuxeo.ecm.eval.tests;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.nuxeo.ecm.eval.qna.QnaItem;
import org.nuxeo.ecm.eval.qna.QnaList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Test used to validate JSON Marshaling
 * <p>
 * (not really interesting for you)
 * </p>
 * 
 * @author Tiry (tdelprat@nuxeo.com)
 * 
 */

public class TestJSON {

    @Test
    public void testJSONMarshaling() throws Exception {

        QnaList qnas = new QnaList();

        for (int i = 0; i < 5; i++) {
            QnaItem qna = new QnaItem();
            qna.setId("q" + i);
            qna.setQuestion("Question " + (i + 1));
            Map<String, String> options = new HashMap<String, String>();
            for (int o = 0; o < 3; o++) {
                options.put("option" + o, "Option " + o);
            }
            qna.setOptions(options);
            qna.setAnswer("option1");
            qnas.add(qna);
        }

        String json = qnas.toString();

        // System.out.println(json);

        qnas = QnaList.fromJSON(json);

        assertNotNull(qnas);
        assertEquals(5, qnas.size());
        assertEquals("option1", qnas.get(0).getAnswer());
        assertEquals("q2", qnas.get(2).getId());
        assertEquals("Question 4", qnas.get(3).getQuestion());

        String json2 = qnas.toString();
        assertEquals(json, json2);
    }
}

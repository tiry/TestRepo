package org.nuxeo.ecm.eval.qna;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

public class QnaItem {

    protected String id;

    protected String question;

    protected Map<String, String> options = new HashMap<String, String>();

    protected String answer;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Map<String, String> getOptions() {
        return options;
    }

    public void setOptions(Map<String, String> options) {
        this.options = options;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public JSONObject asJSONObject() {
        return JSONObject.fromObject(this);
    }

    public String asJSON() {
        return asJSONObject().toString(2);
    }

    public static QnaItem fromJSON(JSONObject jsonObject) {
        return (QnaItem) JSONObject.toBean(jsonObject,QnaItem.class);
    }

    public static QnaItem fromJSON(String json) {
        return fromJSON(JSONObject.fromObject(json));
    }
}

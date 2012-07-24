package org.nuxeo.ecm.eval.qna;

import java.util.ArrayList;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


public class QnaList extends ArrayList<QnaItem> {

    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        try {
            return asJSON();
        }
        catch (Exception e) {
            return "Unable to generate JSON : " + e.getMessage();
        }
    }

    public String asJSON() {
        return asJSONArray().toString(2);
    }

    public JSONArray asJSONArray(){
        JSONArray json = new JSONArray();
        for (QnaItem item : this) {
            json.add(item.asJSONObject());
        }
        return json;
    }


    public static QnaList fromJSON(String json) {
        JSONArray array = JSONArray.fromObject(json);

        QnaList qnas = new QnaList();
        for (int i = 0 ; i< array.size(); i++) {
            qnas.add(QnaItem.fromJSON((JSONObject) array.get(i)));
        }

        return qnas;
    }

}

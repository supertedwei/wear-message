package com.supergigi.wearmessage.share;

import org.json.JSONException;
import org.json.JSONStringer;

/**
 * Created by tedwei on 3/15/17.
 */

public class MessageData {

    private String text1;
    private String text2;

    public String getText1() {
        return text1;
    }

    public void setText1(String text1) {
        this.text1 = text1;
    }

    public String getText2() {
        return text2;
    }

    public void setText2(String text2) {
        this.text2 = text2;
    }

    public String toJsonString() {
        try {
            String jsonString = new JSONStringer().object()
                    .key("text1").value(text1)
                    .key("text2").value(text2)
                    .endObject().toString();
            return jsonString;
        } catch (JSONException e) {
            e.printStackTrace();
            return "";
        }
    }
}

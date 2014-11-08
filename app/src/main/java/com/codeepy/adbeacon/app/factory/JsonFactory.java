package com.codeepy.adbeacon.app.factory;

import android.util.Log;
import com.codeepy.adbeacon.app.helper.Codeepy;
import com.codeepy.adbeacon.app.model.Advertisement;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cipherhat on 20/10/14.
 */
public class JsonFactory {

    private static JsonFactory instance = new JsonFactory();

    private JsonFactory(){}

    public static JsonFactory getInstance(){
        return instance;
    }

    public List<Advertisement> handleAdvertisement(String json) {
        List<Advertisement> advertisements = new ArrayList<Advertisement>();
        try {
            JSONArray jArray = new JSONArray(json);
            if (jArray.length() == 0) {
                Advertisement data = new Advertisement();
//                data.setCompany(Codeepy.ERROR.toString());
//                data.setCategory(Codeepy.ERROR.toString());
                data.setPicurl(Codeepy.ERROR.toString());
//                data.setUpload_date(Codeepy.ERROR.toString());
                data.setFrom_date(Codeepy.ERROR.toString());
                data.setTo_date(Codeepy.ERROR.toString());
                data.setUuid(Codeepy.ERROR.toString());
                advertisements.add(data);
            } else {
                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject jsonObject = jArray.getJSONObject(i);
                    Advertisement data = new Advertisement();
//                    data.setCompany(jsonObject.getString("company"));
//                    data.setCategory(jsonObject.getString("category"));
                    data.setPicurl(jsonObject.getString("pic"));
//                    data.setUpload_date(jsonObject.getString("upload_date"));
                    data.setFrom_date(jsonObject.getString("from_date"));
                    data.setTo_date(jsonObject.getString("to_date"));
                    data.setUuid(jsonObject.getString("beacon"));

                    // add the app to apps orgList
                    advertisements.add(data);
                }
            }

        } catch (JSONException ex) {
            Log.w(Codeepy.TAG.toString(), ex.getMessage());
        } catch (Exception ex) {
            Log.w(Codeepy.TAG.toString(), ex.getMessage());
        }

        return advertisements;
    }
}

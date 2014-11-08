package com.codeepy.adbeacon.app.factory;

import android.net.Uri;
import android.util.Log;
import com.codeepy.adbeacon.app.helper.Codeepy;
import com.codeepy.adbeacon.app.webservice.AdWebService;
import com.codeepy.adbeacon.app.webservice.WebService;
import com.codeepy.adbeacon.app.webservice.YoWebService;

/**
 * Created by cipherhat on 01/11/14.
 */
public class WebServiceURLFactory {

    private static WebServiceURLFactory instance = new WebServiceURLFactory();

    private WebServiceURLFactory() {
    }

    public static WebServiceURLFactory getInstance() {
        return instance;
    }

    public String buildUri(WebService ws) {
        Uri.Builder builder = Uri.parse(ws.getUrl()).buildUpon();

        if (ws instanceof YoWebService) {
            YoWebService yo = (YoWebService) ws;

            if (yo.getYo() != null) builder.path(yo.getYo());
        }
        else if (ws instanceof AdWebService) {
            AdWebService ad = (AdWebService) ws;
            builder.path(ad.getPrefix() + "/" + ad.getUUID());
        }

//        Log.i(Codeepy.TAG.toString(), "Uri Build: " + builder.build().toString());
        return builder.build().toString();
    }
}

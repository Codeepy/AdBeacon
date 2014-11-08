package com.codeepy.adbeacon.app.webservice;

import java.io.Serializable;

/**
 * Created by cipherhat on 24/10/14.
 */
public class AdWebService extends WebService implements Serializable {
    public static final String TAG = "Advertisement Web Service";
    private String prefix = "ads";
    private String uuid;

    public AdWebService() {
        super("http://adbeacon.herokuapp.com/");
        clearEverything();
    }

    @Override
    public void clearEverything(){
        clearUUID();
        super.clearEverything();
    }

    public void clearUUID() {
        this.uuid = null;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getUUID() {
        return uuid;
    }

    public void setUUID(String uuid) {
        this.uuid = uuid;
    }

}

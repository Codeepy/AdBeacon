package com.codeepy.adbeacon.app.helper;

public enum Codeepy {
    TAG("AdBeacon"), ERROR("SWM_ERROR_H912H39EPDIOWJ9238F9J"), ;

    private String string;
    private Codeepy(String string){
        this.string = string;
    }

    public String toString(){
        return this.string;
    }

    public int toInteger(){
        int intval;
        try {
            intval = Integer.parseInt(this.string);
        }
        catch(Exception ex) {
            return -1;
        }
        return intval;
    }
}
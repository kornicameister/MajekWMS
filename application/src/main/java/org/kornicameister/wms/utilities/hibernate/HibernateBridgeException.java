package org.kornicameister.wms.utilities.hibernate;

public class HibernateBridgeException extends Exception {
    public HibernateBridgeException(String string) {
        super("[HIBERNATE] " + string);
    }

    private static final long serialVersionUID = - 4414317870023228958L;
}

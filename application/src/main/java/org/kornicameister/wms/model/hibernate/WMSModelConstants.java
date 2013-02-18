package org.kornicameister.wms.model.hibernate;

public enum WMSModelConstants {
    HIBERNATE_CFG("/hibernate.cfg.xml");

    private String constantValue;

    private WMSModelConstants(String value) {
        this.constantValue = value;
    }

    @Override
    public String toString() {
        return this.constantValue;
    }

}

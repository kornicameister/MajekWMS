package org.kornicameister.wms.cm;

enum PropertyKeyring {
    CONTROLLER_PACKAGE("controller.package"),
    MODEL_PACKAGE("model.package"),
    SRC_PREFIX("src.prefix");
    private String value;

    public String getValue() {
        return value;
    }

    PropertyKeyring(String value) {
        this.value = value;
    }
}
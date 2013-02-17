package org.kornicameister.wms.utilities;

public class StringUtils {
    /**
     * Utility method to decapitalize nothing but first letter in string.
     *
     * @param str
     * @return
     */
    public static String decapitalizeFirstLetter(final String str) {
        return Character.toLowerCase(str.charAt(0))
                + (str.length() > 1 ? str.substring(1) : "");
    }

    public static String capitalizeFirstLetter(final String str) {
        return Character.toUpperCase(str.charAt(0))
                + (str.length() > 1 ? str.substring(1) : "");
    }
}

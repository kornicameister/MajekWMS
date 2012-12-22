/**
 * Project: Majek WMS
 * User   : kornicameister
 * Date   : 13.09.12
 * Time   : 23:31
 * File   : app
 * Created: 13-09-2012
 */

// entry point for file
(function () {

    console.init = function (str) {
        console.log('INIT :: ' + str);
    };

    /**creating custom validators for text fields*/
    Ext.apply(Ext.form.field.VTypes, {
        /**
         * @author kornicameister
         * @version 0.1
         * @description Use this vtype whenever you need a validator
         * that will accept anything but capitalized letter starting
         * string that is an input for <strong>xtype: textfield </strong>
         *
         * @param val string to be tested against this vtype's regex
         * @return {Boolean} true is passed string is a good match
         */
        capitalizedString    : function (val) {
            return /[A-Z].*/g.test(val);
        },
        capitalizedStringText: 'To pole musi zaczynać się od dużej litery ' +
            'i nie może zaczynać się od cyfry...',
        capitalizedStringMask: /./i,
        /**
         * @author kornicameister
         * @version 0.1
         * @description This vtype is to be used if you indent to validate
         * street address that matches those used in Poland.
         * By this requirement only following street address are permitted:
         * <strong>
         *     <ul>
         *         <li>
         *             Address starts with capitalized letter only.
         *         </li>
         *         <li>
         *             Address may contain one or more pieces
         *             containing only alphanumeric characters.
         *             Each piece, however, needs to starts with
         *             capitalized letter
         *         </li>
         *         <li>
         *             Last part of the string is number of the location
         *             on given address which may contain just digits
         *             or digit followed by singular letter which is not
         *             capitalized
         *         </li>
         *     </ul>
         * </strong>
         *
         * What comes from that is that following addresses
         * <ol>
         *     <li>Kopacza 32</li>
         *     <li>Kopacza Adama 12</li>
         *     <li>Kopacza Adama 12a</li>
         * </ol>
         * are valid, when this address
         * <strong>Kopacza adama 12dd</strong>
         * is invalid.
         *
         * @param val string to be tested against this vtype's regex
         * @return {Boolean} true is passed string is a good match
         */
        streetAddress        : function (val) {
            return /^([A-Z]([a-z]+) )+(([0-9]+)[a-z]?)$/g.test(val);
        },
        streetAddressText    : 'Wpisany ciąg znaków jest niezgodny z wymaganym formatem\r\n' +
            'Poprawny format to: To Jest Adres 74',
        streetAddressNameMask: /[A-Za-z0-9]/i,
        /**
         * @author kornicameister
         * @version 0.1
         * @description This vtype is used to validate postal code in format
         * matching Poland, that is
         * XX-XXX where each X stands for digit.
         *
         * @param val string to be tested against this vtype's regex
         * @return {Boolean} true is passed string is a good match
         */
        postalCodePL         : function (val) {
            return /^[0-9][0-9]-[0-9][0-9][0-9]$/gi.test(val);
        },
        postalCodePLText     : 'Wprowadź kod pocztowy w formacie XX-XXX',
        postalCodePLMask     : /[0-9]/i,
        /**
         * @author kornicameister
         * @version 0.1
         * @description This vtype is used to validate
         * phone number in following format:
         * <strong>
         *     +(XX)XXX-XX-XX
         *     </strong>
         * where each X stands for digit.
         * This format is mostly appropriate polish home-number, does not inlucde
         * cellphones.
         *
         * @param val string to be tested against this vtype's regex
         * @return {Boolean} true is passed string is a good match
         */
        phoneNumber          : function (val) {
            return /^\+\(\d{2,}\)\d{3}(-\d{2}){2}$/gi.test(val);
        },
        phoneNumberText      : 'Numer telefonu musi być wprowadzony w formacie ' +
            '+(XX)XXX-XX-XX',
        phoneNumberMask      : '/[+]/gi',
        /**
         * @author kornicameister
         * @version 0.1
         * @description Use this vtype to check whether or not
         * provided NIP number is correct or not.
         * Regex matches for both know format 8 | 14 char
         *
         * @param val string to be tested against this vtype's regex
         * @return {Boolean} true is passed string is a good match
         */
        nipNumber            : function (val) {
            return /^(\(d{3}-\d{3}-\d{2}-\d{2})|(d{3}-\d{2}-\d{2}-\d{3})$/.test(val);
        },
        nipNumberText        : 'Niepoprawny format numeru NIP',
        nipNumberMask        : '/[0-9/i',
        /**
         * @author kornicameister
         * @version 0.1
         * @description This vtype is designed to validate
         * account number in format accepted by IBAN, therefore
         * we have 36 at the beginning and entire account number should
         * always look like this:
         * <strong>36-0000-1111-2222-3333-4444-5555</strong>
         *
         * @param val string to be tested against this vtype's regex
         * @return {Boolean} true is passed string is a good match
         */
        accountNumber        : function (val) {
            return /^36(-\d{4}){6}$/i.test(val);
        },
        accountNumberText    : 'Niepoprawny numer IBAN konta',
        accountNumberMask    : '/^36/i'
    });
    //noinspection JSUnusedGlobalSymbols
    /**creating custom validators for text fields*/


    Ext.Loader.setConfig({
        enabled: true,
        paths  : {
            'Ext.ux'          : 'app/ux',
            'Ext.ux.menu'     : 'app/ux',
            'Ext.ux.layout'   : 'app/ux',
            'Ext.ux.proxy'    : 'app/ux',
            'Ext.ux.statusbar': 'app/ux'
        }
    });

    Ext.Loader.require(
        [
            // loading content of app/ux folder, see above for setting paths
            'Ext.ux.*',
            // Must be loaded manually, otherwise Ext fails to find it
            'Ext.ux.WMSColumnRenderers'
        ],
        function () {
            console.log('Ext.Loader :: Loaded all Ext.ux.* files');

            // triggering application start.
            Ext.application({
                name              : 'WMS',
                appFolder         : 'app',
                enableQuickTips   : true,
                autoCreateViewport: true,
                controllers       : [
                    // viewport WMSView underlying controller
                    'wms.Overview',
                    'wms.Statistics',
                    'wms.Unit',
                    'wms.unit.Canvas',
                    'wms.unit.Inventory',

                    'manager.Suppliers',
                    'manager.Recipients',

                    'wizard.Invoice',
                    'wizard.Client',
                    'wizard.Company',

                    'Toolbars',
                    'Login',
                    'Master'
                ]
            });
        });
}());

/**
 * Project: MajekWMS
 * User   : kornicameister
 * Date   : 24.10.12
 * Time   : 02:11
 */

/**
 * @class WMS.view.login.Form
 * @extend Ext.form.
 * @description This class provided UI component that allows
 * user to login into application
 */
Ext.define('WMS.view.login.Form', {
    extend      : 'Ext.form.Panel',
    alias       : 'widget.loginform',
    labelWidth  : 80,
    frame       : false,
    monitorValid: true,

    defaultType: 'textfield',
    defaults   : {
        allowBlank: false
    },

    items: [
        {
            fieldLabel: 'Użytkownik',
            name      : 'login'
        },
        {
            fieldLabel: 'Hasło',
            name      : 'password',
            inputType : 'password'
        }
    ],

    buttons: [
        {text: 'Login', formBind: true, flex: 3}
    ]
});
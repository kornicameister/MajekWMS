/**
 * Project: MajekWMS
 * User   : kornicameister
 * Date   : 24.10.12
 * Time   : 02:11
 */

/**
 * @class WMS.view.dialog.Login
 * @extend WMS.view.abstract.BaseDialog
 * @description This class provided UI component that allows user to login into application
 */
Ext.define('WMS.view.dialog.Login', {
    extend: 'WMS.view.abstract.BaseDialog',
    alias : 'widget.loginwizard',
    title : 'Login credentials',
    width : 440,

    items: {
        xtype       : 'form',
        labelWidth  : 80,
        url         : 'wms/auth',
        frame       : true,
        title       : 'Please Login',
        defaultType : 'textfield',
        monitorValid: true,
        items       : [
            {
                fieldLabel: 'Username',
                name      : 'login',
                allowBlank: false
            },
            {
                fieldLabel: 'Password',
                name      : 'password',
                inputType : 'password',
                allowBlank: false
            }
        ],

        buttons: [
            {
                text    : 'Login',
                formBind: true,
                handler : function (button) {
                    button.up('form').submit({
                        method      : 'POST',
                        jsonSubmit  : true,
                        paramsAsHash: true,
                        waitTitle   : 'Connecting',
                        waitMsg     : 'Sending data...',

                        success: function (form, action) {
                            var obj = Ext.util.JSON.decode(action.response.responseText);
                            Ext.Msg.alert('Status', 'Login Successful!', function (btn) {
                                if (btn == 'ok') {
                                    // handle ok button
                                }
                            });
                        },

                        failure: function (form, action) {
                            if (action.failureType == 'server') {
                                var obj = Ext.util.JSON.decode(action.response.responseText);
                                Ext.Msg.alert('Login Failed!', obj.errors.reason);
                            } else {
                                Ext.Msg.alert('Warning!', 'Authentication server is unreachable : ' + action.response.responseText);
                            }
                            login.getForm().reset();
                        }
                    });
                }
            }
        ]
    }
});
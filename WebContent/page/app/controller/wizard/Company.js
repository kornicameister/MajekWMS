/**
 * Project: MajekWMS
 * User   : kornicameister
 * Date   : 25.10.12
 * Time   : 14:59
 */

Ext.define('WMS.controller.wizard.Company', {
    extend          : 'Ext.app.Controller',
    requires        : [
        'WMS.view.wizard.company.Dialog'
    ],
    refs            : [
        { ref: 'wizard', selector: 'companywizard' },
        { ref: 'welcomeStep', selector: 'companywizard panel[itemId=welcomeStep]' },
        { ref: 'companyStep', selector: 'companywizard companyform' },
        { ref: 'warehouseStep', selector: 'companywizard warehouseform' },
        { ref: 'summaryStep', selector: 'companywizard panel[itemId=summaryStep]' },
        { ref: 'saveButton', selector: 'companyWizard panel[itemId=summaryStep] button[itemId=saveCompany]' }
    ],
    stores          : [
        'Companies'
    ],
    statics         : {
        STEP: {
            'WELCOME'  : 0,
            'COMPANY'  : 1,
            'WAREHOUSE': 2,
            'SUMMARY'  : 3
        },
        DIR : {
            NEXT: 'next',
            PREV: 'prev'
        }
    },
    init            : function () {
        console.init('WMS.controller.wizard.Company initializing...');
        var me = this;

        me.control({
            'companywizard'                         : {
                'render': me.initWizard
            },
            'companywizard bbar button[itemId=next]': {
                'click': me.onWizardNextStep
            },
            'companywizard bbar button[itemId=prev]': {
                'click': me.onWizardPrevStep
            },
            'companywizard summaryStep saveCompany' : {
                click: me.onCompanySave
            }
        });
    },
    initWizard      : function () {
        console.log('wizard.Company :: View rendered, wizard is ready to be used...');
        var me = this,
            stepToView = new Ext.util.MixedCollection();

        // match step to view
        stepToView.add(WMS.controller.wizard.Company.STEP.WELCOME, me.getWelcomeStep());
        stepToView.add(WMS.controller.wizard.Company.STEP.COMPANY, me.getCompanyStep());
        stepToView.add(WMS.controller.wizard.Company.STEP.WAREHOUSE, me.getWarehouseStep());
        stepToView.add(WMS.controller.wizard.Company.STEP.SUMMARY, me.getSummaryStep());

        Ext.apply({
            currentStep: WMS.controller.wizard.Company.STEP.WELCOME,
            stepToView : stepToView,
            company    : undefined
        }, me);
    },
    openWizard      : function () {
        var me = this,
            wizardView = me.getView('WMS.view.wizard.company.Dialog');
        wizardView.create().show(true);
    },
    onCompanySave   : function () {
        var me = this;
    },
    navigate        : function (dir) {
        var me = this,
            wizard = me.getWizard(),
            layout = wizard.getLayout(),
            stepEnabled = false;

        switch (dir) {
            case WMS.controller.wizard.Company.STEP.NEXT:
                console.log('wizard.Company :: Next step');
                if (me.collectStepData()) {
                    me.currentStep++;
                    stepEnabled = true;
                }
                break;
            case WMS.controller.wizard.Company.STEP.PREV:
                console.log('wizard.Company :: Previous step');
                if (me.purgeStepData()) {
                    me.currentStep--;
                    stepEnabled = true;
                }
                break;
        }
        if (stepEnabled) {
            layout[dir]();
        }
    },
    collectStepData : function () {
        console.log('wizard.Company :: Collecting data steps');
        var me = this,
            dataView = me.stepToView.get(me.currentStep),
            data = undefined;
        switch (me.currentStep) {
            case WMS.controller.wizard.Company.STEP.COMPANY:
                if (dataView.isValid()) {
                    data = dataView.getValues();
                    me.company = Ext.create('WMS.model.entity.Company', data);
                }
                break;
            case WMS.controller.wizard.Company.STEP.WAREHOUSE:
                if (dataView.isValid()) {
                    data = dataView.getValues();
                    data = Ext.create('WMS.model.entity.Warehouse', data);
                    me.company = setWarehouse(data);
                }
                break;
            default:
                return true;
        }
        return false;
    },
    purgeStepData   : function () {
        console.log('wizard.Company :: Purging data steps');
        var me = this;
        switch (me.currentStep) {
            case WMS.controller.wizard.Company.STEP.COMPANY:
                me.company = undefined;
                break;
            case WMS.controller.wizard.Company.STEP.WAREHOUSE:
                me.company.setWizard(undefined);
                break;
            default:
                return true;
        }
    },
    //---- CUSTOM WRAPPERS --- //
    onWizardNextStep: function () {
        var me = this;
        me.navigate(WMS.controller.wizard.Company.STEP.NEXT);
    },
    onWizardPrevStep: function () {
        var me = this;
        me.navigate(WMS.controller.wizard.Company.STEP.PREV);
    }
});
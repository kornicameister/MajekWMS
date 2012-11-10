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
    views           : [
        'wizard.company.Dialog'
    ],
    refs            : [
        { ref: 'wizard', selector: 'companywizard' },
        { ref: 'welcomeStep', selector: 'companywizard panel[itemId=welcomeStep]' },
        { ref: 'companyStep', selector: 'companywizard companyform' },
        { ref: 'warehouseStep', selector: 'companywizard warehouseform' },
        { ref: 'summaryStep', selector: 'companywizard panel[itemId=summaryStep]' },
        { ref: 'saveButton', selector: 'companyWizard panel[itemId=summaryStep] button[itemId=saveCompany]' },
        { ref: 'prevButton', selector: 'companywizard button[itemId=prev]' }
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
    config          : {
        stepToView : new Ext.util.MixedCollection(),
        currentStep: 0,
        company    : undefined
    },
    init            : function () {
        console.init('WMS.controller.wizard.Company initializing...');
        var me = this;

        me.control({
            'companywizard'                           : {
                'render': me.initWizard
            },
            'companywizard button[itemId=next]'       : {
                'click': me.onWizardNextStep
            },
            'companywizard button[itemId=prev]'       : {
                'click': me.onWizardPrevStep
            },
            'companywizard button[itemId=saveCompany]': {
                click: me.onCompanySave
            }
        });
    },
    initWizard      : function () {
        console.log('wizard.Company :: View rendered, wizard is ready to be used...');
        var me = this,
            stepToView = me.getStepToView();

        // match step to view
        stepToView.add(WMS.controller.wizard.Company.STEP.WELCOME, me.getWelcomeStep());
        stepToView.add(WMS.controller.wizard.Company.STEP.COMPANY, me.getCompanyStep());
        stepToView.add(WMS.controller.wizard.Company.STEP.WAREHOUSE, me.getWarehouseStep());
        stepToView.add(WMS.controller.wizard.Company.STEP.SUMMARY, me.getSummaryStep());
    },
    openWizard      : function () {
        var me = this,
            wizardView = me.getWizardCompanyDialogView() || me.getWizard();

        if (Ext.isDefined(wizardView)) {
            wizardView = wizardView.create();
        } else {
            wizardView = Ext.create('WMS.view.wizard.company.Dialog');
        }
        wizardView.show();
    },
    onCompanySave   : function () {
        var me = this;
        console.log('wizard.Company :: Saving company data');
    },
    navigate        : function (dir) {
        var me = this,
            wizard = me.getWizard(),
            layout = wizard.getLayout(),
            stepEnabled = false,
            currentStep = me.getCurrentStep(),
            prevButton = me.getPrevButton();

        switch (dir) {
            case WMS.controller.wizard.Company.DIR.NEXT:
                console.log('wizard.Company :: Next step');
                if (me.collectStepData()) {
                    me.setCurrentStep(++currentStep);
                    stepEnabled = true;
                }
                break;
            case WMS.controller.wizard.Company.DIR.PREV:
                console.log('wizard.Company :: Previous step');
                if (me.purgeStepData()) {
                    me.setCurrentStep(--currentStep);
                    stepEnabled = true;
                }
                break;
        }
        if (stepEnabled) {
            layout[dir]();
            prevButton.setDisabled(currentStep === WMS.controller.wizard.Company.STEP.WELCOME);
        }
    },
    collectStepData : function () {
        console.log('wizard.Company :: Collecting data steps');
        var me = this,
            dataView = me.stepToView.get(me.getCurrentStep()),
            nextDataView = me.getStepToView().get(me.getCurrentStep() + 1),
            data = undefined,
            isValid = (Ext.isDefined(dataView.getForm) ? dataView.getForm().isValid() : false);
        switch (me.currentStep) {
            case WMS.controller.wizard.Company.STEP.COMPANY:
                if (isValid) {
                    data = dataView.getValues();
                    me.setCompany(Ext.create('WMS.model.entity.Company', data));
                    return true;
                }
                break;
            case WMS.controller.wizard.Company.STEP.WAREHOUSE:
                if (isValid) {
                    data = dataView.getValues();
                    var warehouse = Ext.create('WMS.model.entity.Warehouse', data);
                    me.getCompany().setWarehouse(warehouse);

                    data = me.getCompany().getData();
                    data['warehouse'] = warehouse;
                    nextDataView.update(data);
                    return true;
                }
                break;
            default:
                return true;
        }
        return false;
    },
    purgeStepData   : function () {
        console.log('wizard.Company :: Purging data steps');
        var me = this,
            currentStep = me.getCurrentStep(),
            dataView = me.stepToView.get(currentStep);
        switch (currentStep) {
            case WMS.controller.wizard.Company.STEP.COMPANY:
                delete me.company;
                dataView.getForm().reset();
                return true;
            case WMS.controller.wizard.Company.STEP.WAREHOUSE:
                delete me.getCompany()['warehouse'];
                dataView.getForm().reset();
            default:
                return true;
        }
        return false;
    },
    //---- CUSTOM WRAPPERS --- //
    onWizardNextStep: function () {
        var me = this;
        me.navigate(WMS.controller.wizard.Company.DIR.NEXT);
    },
    onWizardPrevStep: function () {
        var me = this;
        me.navigate(WMS.controller.wizard.Company.DIR.PREV);
    }
});
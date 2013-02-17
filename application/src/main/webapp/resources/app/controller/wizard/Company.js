/**
 * Project: MajekWMS
 * User   : kornicameister
 * Date   : 25.10.12
 * Time   : 14:59
 */

Ext.define('WMS.controller.wizard.Company', {
    extend           : 'Ext.app.Controller',
    requires         : [
        'WMS.view.wizard.company.Dialog'
    ],
    views            : [
        'wizard.company.Dialog'
    ],
    refs             : [
        { ref: 'wizard', selector: 'companywizard' },
        { ref: 'welcomeStep', selector: 'companywizard panel[itemId=welcomeStep]' },
        { ref: 'companyStep', selector: 'companywizard companyform' },
        { ref: 'warehouseStep', selector: 'companywizard warehouseform' },
        { ref: 'summaryStep', selector: 'companywizard panel[itemId=summaryStep]' },
        { ref: 'saveButton', selector: 'companyWizard panel[itemId=summaryStep] button[itemId=saveCompany]' },
        { ref: 'prevButton', selector: 'companywizard button[itemId=prev]' },
        { ref: 'nextButton', selector: 'companywizard button[itemId=next]' }
    ],
    stores           : [
        'Companies',
        'Warehouses'
    ],
    statics          : {
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
    config           : {
        stepToView : new Ext.util.MixedCollection(),
        currentStep: 0,
        company    : undefined
    },
    init             : function () {
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
    initWizard       : function () {
        console.log('wizard.Company :: View rendered, wizard is ready to be used...');
        var me = this,
            stepToView = me.getStepToView();

        // match step to view
        stepToView.add(WMS.controller.wizard.Company.STEP.WELCOME, me.getWelcomeStep());
        stepToView.add(WMS.controller.wizard.Company.STEP.COMPANY, me.getCompanyStep());
        stepToView.add(WMS.controller.wizard.Company.STEP.WAREHOUSE, me.getWarehouseStep());
        stepToView.add(WMS.controller.wizard.Company.STEP.SUMMARY, me.getSummaryStep());
    },
    openWizard       : function () {
        var me = this,
            wizardView = me.getWizard();

        if (Ext.isDefined(wizardView)) {
            wizardView = wizardView.create();
        } else {
            wizardView = Ext.create('WMS.view.wizard.company.Dialog');
        }
        me.mon(me.getCompaniesStore(), 'update', me.onCompaniesUpdate, me);
        wizardView.show();
    },
    onCompanySave    : function () {
        var me = this,
            company = me.getCompany(),
            companies = me.getCompaniesStore();
        console.log('wizard.Company :: Saving company data');

        if (Ext.isDefined(company)) {
            companies.add(company);
        }
    },
    onCompaniesUpdate: function (store, record) {
        var company = record,
            warehouse = company.getWarehouse(),
            me = this,
            wizardView = me.getWizard(),
            warehouses = me.getWarehousesStore();

        if (Ext.isDefined(warehouse)) {
            Ext.MessageBox.alert(
                "Sukces",
                "Utworzyłeś i zdefiniowałeś poprawnie nową firmę oraz magazyn"
            );
            //@TODO very bad code here...should be something, different but
            //I am afraid that it would require to remodel data-model.
            //even worse
            warehouses.load();
            wizardView.close();
        }
    },
    /**
     * Navigator method designed to control {@link WMS.view.wizard.company.Dialog} steps.
     * @param dir one of the following values [{@link WMS.controller.wizard.Company.DIR.NEXT},{@link WMS.controller.wizard.Company.DIR.PREV}]
     */
    navigate         : function (dir) {
        var me = this,
            wizard = me.getWizard(),
            layout = wizard.getLayout(),
            stepEnabled = false,
            currentStep = me.getCurrentStep(),
            prevButton = me.getPrevButton(),
            nextButton = me.getNextButton();

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
            nextButton.setDisabled(currentStep === WMS.controller.wizard.Company.STEP.SUMMARY);
            switch (currentStep) {
                case WMS.controller.wizard.Company.STEP.WELCOME:
                    wizard.setTitle("Witaj w kreatorze pierwszego uruchomienia");
                    break;
                case WMS.controller.wizard.Company.STEP.WAREHOUSE:
                    wizard.setTitle("Zdefiniuj podstawowe dane swojego magazynu");
                    break;
                case WMS.controller.wizard.Company.STEP.COMPANY:
                    wizard.setTitle("Zdefiniuj dane swojej firmy");
                    break;
                case WMS.controller.wizard.Company.STEP.SUMMARY:
                    wizard.setTitle("Sprawdź poprawność i zapisz");
                    break;
            }
        }
    },
    collectStepData  : function () {
        console.log('wizard.Company :: Collecting data steps');
        var me = this,
            dataView = me.stepToView.get(me.getCurrentStep()),
            nextDataView = me.getStepToView().get(me.getCurrentStep() + 1),
            data = undefined,
            isValid = (Ext.isDefined(dataView.getForm) ? dataView.getForm().isValid() : false),
            company = me.getCompany();

        switch (me.currentStep) {
            case WMS.controller.wizard.Company.STEP.COMPANY:
                if (isValid) {
                    data = dataView.getValues();
                    me.setCompany(data);
                    return true;
                }
                break;
            case WMS.controller.wizard.Company.STEP.WAREHOUSE:
                if (isValid) {
                    data = dataView.getValues();
                    company['warehouse'] = data;
                    me.setCompany(company);

                    //adjust for view !
                    nextDataView.update(company);

                    return true;
                }
                break;
            default:
                return true;
        }
        return false;
    },
    purgeStepData    : function () {
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
    },
    //---- CUSTOM WRAPPERS --- //
    onWizardNextStep : function () {
        var me = this;
        me.navigate(WMS.controller.wizard.Company.DIR.NEXT);
    },
    onWizardPrevStep : function () {
        var me = this;
        me.navigate(WMS.controller.wizard.Company.DIR.PREV);
    }
});

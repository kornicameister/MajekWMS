/**
 * Project  : MajekWMS
 * User     : kornicameister
 * Date     : 29.11.12
 * Time     : 12:41
 */

/**
 * This class utilizes commonly reused method to
 * render data for grids basing on past id.
 */
Ext.define('WMS.utilities.ColumnRenderers', {
    alternateClassName: 'ColumnRenderers',
    uses              : [
        'Ext.data.Store'
    ],
    statics           : {
        measureColumnRenderer: function (measure_id) {
            if (!Ext.isNumber(measure_id)) {
                return 'unknown';
            } else if (Ext.isString(measure_id)) {
                measure_id = parseInt(measure_id);
            }

            if (measure_id > 0) {
                return Ext.getStore('Measures').getById(measure_id).get('abbreviation');
            } else if (measure_id === 0) {
                return 'undefined';
            }

            console.error("InventoryView :: Failed to recognize measure_id");
            return '';
        }
    }
});
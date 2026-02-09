'use strict';

$(document).ready(function () {
    var responsiveHelper = undefined;
    var breakpointDefinition = {
        tablet: 1024,
        phone : 480
    };
    var tableElement = $('#example');

    tableElement.dataTable({
        sDom           : '<"row"<"span12 tbl-tools-searchbox"pClfrt><"widget-block"<"widget-bottom"p>>',
		oColVis: {
           aiExclude: [ 0,1,2 ]
          },
		  aaSorting: [[ 1, "asc" ]],
		aoColumnDefs: [
						{ "bSortable": false, "aTargets": [ 0 ] }
					],
        sPaginationType: 'bootstrap',
        oLanguage      : {
            sLengthMenu: 'Entries per page _MENU_'
        },
        bAutoWidth     : false,
        fnPreDrawCallback: function () {
			uncheckRadioButton();
            // Initialize the responsive datatables helper once.
            if (!responsiveHelper) {
                responsiveHelper = new ResponsiveDatatablesHelper(tableElement, breakpointDefinition);
            }
        },
        fnRowCallback  : function (nRow) {
            responsiveHelper.createExpandIcon(nRow);
        },
        fnDrawCallback : function (oSettings) {
            responsiveHelper.respond();
        }
    });
    var tableElement = $('#unsort');

    tableElement.dataTable({
        sDom           : '<"row"<"span12 tbl-tools-searchbox"pClfrt><"widget-block"<"widget-bottom"p>>',
		oColVis: {
           aiExclude: [ 0,1,2 ]
          },
//		  aaSorting: [[ 1, "asc" ]],
		aoColumnDefs: [
						{ "bSortable": false, "aTargets": [ 0 ] }
					],
        sPaginationType: 'bootstrap',
        oLanguage      : {
            sLengthMenu: 'Entries per page _MENU_'
        },
        bAutoWidth     : false,
        fnPreDrawCallback: function () {
			uncheckRadioButton();
            // Initialize the responsive datatables helper once.
            if (!responsiveHelper) {
                responsiveHelper = new ResponsiveDatatablesHelper(tableElement, breakpointDefinition);
            }
        },
        fnRowCallback  : function (nRow) {
            responsiveHelper.createExpandIcon(nRow);
        },
        fnDrawCallback : function (oSettings) {
            responsiveHelper.respond();
        }
    });
});


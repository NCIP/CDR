    var pageSize = 10; 

    Ext.onReady(function() {
        $("h1").append("<span> (SOLR Instance " + (vocabJsnLoc.indexOf('dev') > -1 ? " - Dev" : " - Production") + ")</span>");
        createVocabStatus("codcpt","Cause of Death/Medical History");
        createVocabStatus("rx","Medication Name/Vitamins/Supplements");
        createVocabStatus("pct","Cancer Types");
    }); // eo function onReady
    
    function createVocabStatus(typ, text){
        $("#container").append("<div id=\"" + typ + "_menu\"><div>");
        $("#container").append("<div id=\"" + typ + "_results\" class=\"reportcontainer\"><div>");
        if(paramVals0.length == 0) {
            paramVals0='*';
        }
        var searchUrl = vocabJsnLoc + '/solr/' + typ + '?fl=label:cpt,label:cod,label:rx,label:pct,*&wt=json&rows=' +pageSize;
        var resultsUrl = searchUrl + '&q=' + paramVals0;
        Ext.define('Record', {
            extend: 'Ext.data.Model',
            fields: [
                {name: 'id', type: 'string', mapping: 'id'},
                {name: 'typ',  type: 'string', mapping: 'typ'},
                {name: 'label',  type: 'string', mapping: 'label'},
                {name: 'cui',  type: 'array', mapping: 'cui'},
                {name: 'ICDcd',  type: 'array', mapping: 'ICDcd'}
            ]
        });

        Ext.define('Search', {
            extend: 'Ext.data.Model',
            fields: [
                {name: 'label',  type: 'string', mapping: 'label'}
            ]
        });

        var typStore = new Ext.data.JsonPStore({
            model: 'Record',
            autoLoad : true,
            remoteSort: true,
            pageSize: pageSize,
            params: {start:0, limit:pageSize},
            clearFilter: true,
            proxy: {
               type: 'jsonp',
               url : resultsUrl,
               callbackKey: 'json.wrf',
               sort: 'label asc',
               reader: new Ext.data.JsonReader({
                  root: 'response.docs',
                  totalProperty: 'numFound'
               }),
                // sends single sort as multi parameter
               simpleSortMode: false
            },
            listeners: {
                load : function(){
                    var numFound = this.getProxy().getReader().rawData.response.numFound;
                    this.totalCount = numFound;
                }
            }
        });
        
        //Create paging Toolbar
        var paging = new Ext.PagingToolbar({
            id: typ + 'StorePageId',
            store: typStore,
            displayInfo: true,
            displayMsg: 'Displaying data {0} - {1} of {2}',
            emptyMsg: "No data to display"
        });

	Ext.create('Ext.grid.Panel', {
            store: typStore,
            bbar: paging,
            border: 2,
            renderTo: typ + '_results',
            columns: [ {
		text: 'ID',
		sortable: false,
		dataIndex: 'id',
                flex: 90
            },{
		text: 'Type',
		sortable: false,
		dataIndex: 'typ',
                flex: 50
            },{
		text: text,
		sortable : false,
		dataIndex: 'label',
                flex: 400
            },{
		text: 'CUI',
		sortable: false,
		dataIndex: 'cui',
                hidden: typ == "rx" ? true: false,
                flex: 100
            },{
		text: 'ICDcd',
		sortable: false,
		dataIndex: 'ICDcd',
                hidden: typ == "rx" ? true: false,
                flex: 200
            }],
            listeners: {
                itemdblclick: {fn : function(grid, record) {}},
                viewready:  { fn : function() {}}
            }
	});
                
        Ext.create('Ext.form.TextField', {
            id : 'search-' + typ,
            fieldLabel: 'Search ' + text,
            renderTo: typ + '_menu',
            multiSelect: false,
            emptyText : "Enter first few letters of the " + text + " being searched here...",
            labelWidth: 400,
            width: 965,
            enableKeyEvents: true,
            listeners: {
                keyup: function(f, e){
                   var textFieldVal = this.getValue();
                    if(textFieldVal.length == 0) {
                        textFieldVal='*';
                    }
                   typStore.getProxy().url = searchUrl + '&q=' + textFieldVal;
                   typStore.loadPage(1);
                }
            }
        }); 
        
    }


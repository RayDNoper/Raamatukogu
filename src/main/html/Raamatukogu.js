var AutorField = function(config) {
        jsGrid.Field.call(this, config);
        };

    AutorField.prototype = new jsGrid.Field({
        itemTemplate: function(value) {
            var autorid = " !! ";
            value.forEach( (autor) => {autorid += "," + autor.nimi;  console.log(autor); });

            return autorid;
        },
        editTemplate: function(value) {
            return this._insertPicker = $("<input>");
        },
        editValue: function() {
            return this._insertPicker;
        },

    });

    jsGrid.fields.autorField = AutorField;


    var showDetailsDialog = function(dialogType, client) {
        $("#name").val(client.Name);
        $("#age").val(client.Age);
        $("#address").val(client.Address);
        $("#country").val(client.Country);
        $("#married").prop("checked", client.Married);

        formSubmitHandler = function() {
            saveClient(client, dialogType === "Add");
        };

        $("#detailsDialog").dialog("option", "title", dialogType + " Client")
                .dialog("open");
    };

$(function() {

    $("#jsGrid").jsGrid({
        height: "auto",
        width: "100%",

        sorting: true,
        paging: true,
        filtering: true,
        autoload: true,

        controller: {
            loadData: function() {
                var d = $.Deferred();

                var res = $.getJSON("http://127.0.0.1:4567/raamatukogu/kataloog", {filter : ""}, function(data) {
	        	    console.log("data="+data);
	        	    var kataloog = data;
	        	    console.log("k:" + kataloog.teosed);
	            	d.resolve(kataloog.teosed);
		        });

                return d.promise();
            }
        },

        rowClick: function(args) {
            showDetailsDialog("Edit", args.item);
        },


            fields: [
                { name: "teos", type: "text", width: 150, validate: "required" },
                { name: "aasta", type: "number", width: 50 },
                { name: "autorid", type: "autorField", width: 100}
            ]
    });

    $("#detailsDialog").dialog({
        autoOpen: false,
        width: 400,
        close: function() {
            // $("#detailsForm").validate().resetForm();
            // $("#detailsForm").find(".error").removeClass("error");
        }
    });


});
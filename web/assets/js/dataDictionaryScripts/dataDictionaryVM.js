/**
 * Created by Dehan De Croos on 12/11/2014.
 * Edited by K D K Madusanka on 12/11/2014.
 */


// models

function Keyword(data){
    this.name = ko.observable(data.name);
    this.type = ko.observable(data.type);
    this.description = ko.observable(data.description);
    this.dataType = ko.observable(data.dataType);
    this.length = ko.observable(data.length);
    this.defaultValue = ko.observable(data.defaultValue);
    this.allowedValues = ko.observable(data.allowedValues);

}


function ViewModel() {
    var self = this;

    var data ={};
    var keyword = new Keyword(data);

    self.newData = ko.observable(keyword);
    self.currentDic = ko.observableArray([]);


    self.addNew = function(){
        // validate form
        if(self.newData().name() === undefined || self.newData().dataType() === undefined){
            return false;
        }

        // call
        var data = ko.toJS(self.newData());
        data.request = "createNewDataDicItem";
        $.ajax({
            type: 'POST', url: 'DictionaryController',
            contentType: 'application/json; charset=utf-8',
            dataType: 'json',
            data: JSON.stringify(data),
            success: function(data, textStatus, jqXHR) {
                var messages = JSON.parse(jqXHR.responseText);
                self.refreshDictionary();
            }
        });
    };


    self.refreshDictionary = function(){
        var dicObj;
        var data={ 'request' : "getAllDicItems"};
        $.ajax({
            type: 'POST', url: 'DictionaryController',
            contentType: 'application/json; charset=utf-8',
            dataType: 'json',
            data: JSON.stringify(data),
            success: function(data, textStatus, jqXHR) {
                dicObj = JSON.parse(jqXHR.responseText);

            }
        });
    };


    self.removeDicItem = function(data){

    };

}

dataDicVM = new ViewModel();
dataDicVM.refreshDictionary();
ko.applyBindings(dataDicVM);
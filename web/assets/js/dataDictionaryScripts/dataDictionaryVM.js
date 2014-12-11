/**
 * Created by Dehan De Croos on 12/11/2014.
 * Edited by K D K Madusanka on 12/11/2014.
 */


// models

function Keyword(data){
    this.id = ko.observable(data.id);
    this.name = ko.observable(data.name);
    this.type = ko.observable(data.type);
    this.description = ko.observable(data.description);
    this.dataType = ko.observable(data.dataType);
    this.length = ko.observable(data.length);
    this.defaultValues = ko.observable(data.defaultValues);
    this.allowedValues = ko.observable(data.allowedValues);
}


function ViewModel() {
    var self = this;

    var data ={};
    var keyword = new Keyword(data);

    self.newData = ko.observable(keyword);
    self.currentDic = ko.observableArray([]);
    self.overlayNotification = ko.observable();

    self.addNew = function(){
        // validate form
        if(self.newData().name() === undefined || self.newData().dataType() === undefined){
            return false;
        }

        // call
        var data = ko.toJS(self.newData());
        data.request = "createNewDataDicItem";
        self.overlayNotification('sending...');
        $("#overlay").css("display","block");
        $.ajax({
            type: 'POST', url: 'DictionaryController',
            contentType: 'application/json; charset=utf-8',
            dataType: 'json',
            data: JSON.stringify(data),
            success: function(data, textStatus, jqXHR) {
                var messages = JSON.parse(jqXHR.responseText);
                $('#dicForm')[0].reset();
                self.refreshDictionary();
            }
        });
    };


    self.refreshDictionary = function(){
        var dicObj;
        var data={ 'request' : "getAllDicItems"};
        self.overlayNotification('loading...');
        $("#overlay").css("display","block");
        $.ajax({
            type: 'POST', url: 'DictionaryController',
            contentType: 'application/json; charset=utf-8',
            dataType: 'json',
            data: JSON.stringify(data),
            success: function(data, textStatus, jqXHR) {
                dicObj = JSON.parse(jqXHR.responseText);
                self.currentDic([]);
                for(item in dicObj) {
                    self.currentDic.push(new Keyword(dicObj[item]));
                }
                $('#dicFormName').focus();
            }
        });
    };


    self.removeDicItem = function(data){
        self.overlayNotification('deleting...');
        $("#overlay").css("display","block");

        var data={ 'request' : "removeDicItem", 'id' : data.id()};
        $.ajax({
            type: 'POST', url: 'DictionaryController',
            contentType: 'application/json; charset=utf-8',
            dataType: 'json',
            data: JSON.stringify(data),
            success: function(data, textStatus, jqXHR) {
                self.refreshDictionary();
            }
        });
    };

}

dataDicVM = new ViewModel();
dataDicVM.refreshDictionary();
ko.applyBindings(dataDicVM);
/**
 * Created by Dehan De Croos on 12/11/2014.
 */




function Keyword(data){
    this.name = ko.observable(data.name);
    this.dataType = ko.observable(data.dataType);
    this.description = ko.observable(data.description);
    this.dataType = ko.observable(data.dataType);
    this.Length = ko.observable(data.Length);
    this.defaultValues = ko.observable(data.defaultValues);
    this.allowedValues = ko.observable(data.allowedValues);

}


function ViewModel() {
    var self = this;
    var data ={};
    var keyword = new Keyword(data);
    self.newData = ko.observable(keyword);
}

dataDicVM = new ViewModel();
ko.applyBindings(dataDicVM);
/**
 * Created by K D K Madusanka on 1/26/2015.
 */

/////////////////////////////////////////////////////////////////////////////
/////////////////////////    js tree functions    ///////////////////////////
/////////////////////////////////////////////////////////////////////////////

var initTrees = function(requestType){
    var treeObj;
    var data={ 'request' : requestType}; // getUserNodes / getAllNodes
    $.ajax({
        type: 'POST', url: 'ManageCategoriesController',
        contentType: 'application/json; charset=utf-8',
        dataType: 'json',
        data: JSON.stringify(data),
        success: function(data, textStatus, jqXHR) {
            treeObj = JSON.parse(jqXHR.responseText);
            userVM.allTreeNodesCollection(treeObj);
            $('.treeView')
                .jstree({
                    'plugins': ["search", "state", "types", "wholerow"],
                    'core' : {'data' : treeObj.nodes},
                    "types" : {
                        "default" : { "icon" : "glyphicon glyphicon-folder-open" },
                        "leaf": {"icon" : "glyphicon glyphicon-list-alt" }
                    }
                })
                .on("changed.jstree", function (e, data) {
                    if(data.selected.length) {
                        selectedNodeRow = data.instance.get_node(data.selected[0]);
                        selectedNodeChildRow = data.instance.get_node(selectedNodeRow.children[0]);
                        selectedNodeParentRow = data.instance.get_node(selectedNodeRow.parent);
                        userVM.setCurrentSelectedTreeNode(selectedNodeRow);
                        userVM.currentNodeParent(selectedNodeParentRow);
                        userVM.setIsSelectedTemplate();
                    }
                })
                .on("ready.jstree", function (e, data) {
                    var x = data;
                });
        }
    });
};



////////////////////////////////////////////////////////////////////////////
/////////////////////////         models         ///////////////////////////
////////////////////////////////////////////////////////////////////////////

function User(data){
    this.id = ko.observable(data.id);
    this.username = ko.observable(data.userName);
    this.fullname = ko.observable(data.fullName);
    this.password = ko.observable(data.pass);
    this.role = ko.observable(data.role);
    this.nodes = ko.observableArray([]);
};
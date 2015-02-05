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
    var thisUser = this;
    thisUser.id = ko.observable(data.id);
    thisUser.username = ko.observable(data.userName);
    thisUser.fullname = ko.observable(data.fullName);
    thisUser.password = ko.observable(data.pass);
    thisUser.role = ko.observable(data.role);
    thisUser.nodes = ko.observableArray([]);
    for(assignedNode in data.nodes){
        for(treeNode in userVM.allTreeNodesCollection().nodes){
            if(data.nodes[assignedNode] === userVM.allTreeNodesCollection().nodes[treeNode].id){
                thisUser.nodes().push(ko.toJS(userVM.allTreeNodesCollection().nodes[treeNode]));
            };
        };
    };
};
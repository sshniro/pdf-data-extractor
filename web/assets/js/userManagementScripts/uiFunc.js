/**
 * Created by K D K Madusanka on 1/26/2015.
 */

/////////////////////////////////////////////////////////////////////////////
/////////////////////////    js tree functions    ///////////////////////////
/////////////////////////////////////////////////////////////////////////////

var initTrees = function(){
    var treeObj;
    var data={ 'request' : "getAllNodes"};
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


////////////////// generalized ajax call ///////////////////////
function doAjax (type, url, sendingDataObj){
    $.ajax({
        type: type, url: url,
        contentType: 'application/json; charset=utf-8',
        dataType: 'json',
        data: JSON.stringify(sendingDataObj),
        success: function(data, textStatus, jqXHR) {
            return {
                data: data,
                textStatus: textStatus,
                jqXHR: jqXHR
            };
        }
    });
};



////////////////////////////////////////////////////////////////////////////
/////////////////////////         models         ///////////////////////////
////////////////////////////////////////////////////////////////////////////

function User(data){
    this.username = ko.observable(data.userName);
    this.fullname = ko.observable(data.fullname);
    this.password = ko.observable(data.pass);

};
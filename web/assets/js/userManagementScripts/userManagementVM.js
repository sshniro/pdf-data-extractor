

function ViewModel() {
    var self = this;

    //////// in page navigation ////////
    self.showingDiv = ko.observable('createUser');
    self.setShowingDiv = function(data){
        self.showingDiv(data);
        $('.'+data).addClass('active');
        if(data!='company'){$('.company').removeClass('active');};
        if(data!='createUser'){$('.createUser').removeClass('active');};
        if(data!='hiMan'){$('.hiMan').removeClass('active');};
    };


    //////// tree structure ////////
    var nodeModel = function(data){
        this.id = ko.observable(data.id);
        this.parent = ko.observable(data.parent);
        this.text = ko.observable(data.text);
    };
    var rootNode = {'id':'#', 'parent':undefined, 'text':'root'};
    self.currentSelectedTreeNode = ko.observable(new nodeModel(rootNode));
    self.currentNodeParent = ko.observable(new nodeModel(rootNode));
    self.isSelectedTemplate = ko.observable(false);
    self.selectedPdfTemplate = ko.observable('');
    self.setRootAsCurrentSelectedTreeNode = function(){
        self.currentSelectedTreeNode(new nodeModel(rootNode));
        self.currentNodeParent(new nodeModel(rootNode));
        selectedNodeRow = undefined;
        selectedNodeChildRow = undefined;
        selectedNodeParentRow = undefined;
        $('#treeViewDiv').jstree("deselect_all");
    };
    self.setCurrentSelectedTreeNode = function(node){
        self.currentSelectedTreeNode(new nodeModel({'id':node.id, 'parent':node.parent, 'text':node.text}));
    };
    self.setIsSelectedTemplate = function(){
        if(selectedNodeRow.original.pdfFile != undefined){
            self.isSelectedTemplate(true);
            self.selectedPdfTemplate(selectedNodeRow.original.pdfFile);
        }else{
            self.isSelectedTemplate(false);
            self.selectedPdfTemplate('');
        }
    };


    /////// user ////////
    function userModel(data){
        var user = this;
        user.username = ko.observable();
        user.fullname = ko.observable();
        user.password = ko.observable();
        if(data!=undefined){
            user.username(data.username);
            user.fullname(data.fullname);
            user.password(data.password);
        }
    };
    self.newUserBuffer = ko.observable(new userModel());
    self.usersCollection = ko.observableArray([]);
    self.createNewUser = function(){
        var sendingDataObj = {
            request: 'CreateNewUser',
            username: self.newUserBuffer().username(),
            fullname: self.newUserBuffer().fullname(),
            password: self.newUserBuffer().password()
        };
        var response = doAjax('POST','UserController', sendingDataObj);

        window.location.reload();
    };




}

userVM = new ViewModel();
ko.applyBindings(userVM);
/**
 * Created by K D K Madusanka on 10/11/2014.
 */

// head append //

var knkOut = document.createElement('script');
knkOut.src = 'assets/js/knockout-3.2.0.js';
document.body.appendChild(knkOut);

var bst = document.createElement('script');
bst.src = 'assets/js/bootstrap.js';
document.body.appendChild(bst);


// body append //

var msgBkr = document.createElement('script');
msgBkr.src = 'assets/js/markUpTemplateRegionsScripts/messsageBroker.js';
document.body.appendChild(msgBkr);

var models = document.createElement('script');
models.src = 'assets/js/markUpTemplateRegionsScripts/models.js';
document.body.appendChild(models);

var uiFunc = document.createElement('script');
uiFunc.src = 'assets/js/markUpTemplateRegionsScripts/uiFunctions.js';
document.body.appendChild(uiFunc);

var vm = document.createElement('script');
vm.src = 'assets/js/markUpTemplateRegionsScripts/viewModel.js';
document.body.appendChild(vm);
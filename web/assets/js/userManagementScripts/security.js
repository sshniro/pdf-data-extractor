/**
 * Created by K D K Madusanka on 1/25/2015.
 */

function checkLocalStorage(){
    if(typeof(Storage) !== "undefined") {
        return true;
    } else {
        return false;
    }
};

function sessionValidate(){
    if(sessionStorage.getItem('xtractor_user_username') != null || localStorage.getItem('xtractor_user_username') != null){
        var st = undefined;
        if(sessionStorage.getItem('xtractor_user_username') != null){ st = sessionStorage }
        else if(localStorage.getItem('xtractor_user_username') != null){ st = localStorage }
        if(st.getItem('xtractor_user_isAuthenticate') != 'true'){
            alert('User not authenticated');
            window.history.back();
        } else{
            $('.username-text')[0].innerHTML = st.getItem('xtractor_user_fullname');
            $('.role')[0].innerHTML = st.getItem('xtractor_user_role');
            localStorage.setItem('xtractor_lastPage', window.location.href.split('/')[window.location.href.split('/').length-1]);
        }
    } else {
        alert('Please login before continue');
        localStorage.setItem('xtractor_lastPage', window.location.href.split('/')[window.location.href.split('/').length-1]);
        window.location.href = 'index.jsp';
    }
};

$(document).ready(function(){
    if(window.location.href.split('/')[window.location.href.split('/').length-1] === 'index.jsp'){
        if(sessionStorage.getItem('xtractor_user_username') != null || localStorage.getItem('xtractor_user_username') != null){
            var st = undefined;
            if(sessionStorage.getItem('xtractor_user_username') != null){ st = sessionStorage }
            else if(localStorage.getItem('xtractor_user_username') != null){ st = localStorage }
            if(st.getItem('xtractor_user_isAuthenticate') === 'true'){
                window.location.href = localStorage.getItem('xtractor_lastPage');
            }
        }
    }else {
        sessionValidate();
    }
});

/* // local storage functions for session storage use "sessionStorage" instead "localStorage"
 localStorage.setItem("lastname", "Smith");
 localStorage.getItem("lastname");
 localStorage.removeItem("lastname");
*/

function login(userData, isAllowRemember, redirectTo){

    var toSessionController = { userName : userData.username,  pass  : userData.password , request : "login" };

    $.ajax({
        type: 'POST', url: 'SessionController',
        contentType: 'application/json; charset=utf-8',
        dataType: 'json',
        data: JSON.stringify(toSessionController),

        success: function(data, textStatus, jqXHR) {
            var responseObj = JSON.parse(jqXHR.responseText);
            if(responseObj.isAuthenticated===true){

                $.ajax({
                    type: 'POST', url: 'ManageUsersController',
                    contentType: 'application/json; charset=utf-8',
                    dataType: 'json',
                    data: JSON.stringify({ id:responseObj.userId , request : "getUser" }),

                    success: function (data, textStatus, jqXHR) {
                        var responseObjInner = JSON.parse(jqXHR.responseText);

                        // setting storage variables
                        var clientStorage = undefined;
                        if(isAllowRemember){
                        clientStorage = localStorage;
                        } else{
                        clientStorage = sessionStorage;
                        }

                        clientStorage.setItem('xtractor_user_id', responseObjInner.id);
                        clientStorage.setItem('xtractor_user_username', responseObjInner.userName);
                        clientStorage.setItem('xtractor_user_fullname', responseObjInner.fullName);
                        clientStorage.setItem('xtractor_user_password', responseObjInner.pass);
                        clientStorage.setItem('xtractor_user_role', responseObjInner.role);
                        clientStorage.setItem('xtractor_user_nodes', responseObjInner.nodes);
                        clientStorage.setItem('xtractor_user_isAuthenticate', responseObj.isAuthenticated);

                        window.location.href = redirectTo;
                    },
                    error: function(jqXHR, textStatus, errorThrown) {
                        if(jqXHR.status == 400) {
                            var messages = JSON.parse(jqXHR.responseText);
                            alert(messages);
                        } else {
                            alert('Unexpected server error.');
                        }
                    }
                });

            }else{
                alert(responseObj.errorCause);
            }
        },
        error: function(jqXHR, textStatus, errorThrown) {
            if(jqXHR.status == 400) {
                var messages = JSON.parse(jqXHR.responseText);
                alert(messages);
            } else {
                alert('Unexpected server error.');
            }
        }
    });

};

function logout(){
    sessionStorage.removeItem('xtractor_user_id');
    sessionStorage.removeItem('xtractor_user_username');
    sessionStorage.removeItem('xtractor_user_fullname');
    sessionStorage.removeItem('xtractor_user_password');
    sessionStorage.removeItem('xtractor_user_role');
    sessionStorage.removeItem('xtractor_user_nodes');
    sessionStorage.removeItem('xtractor_user_isAuthenticate');

    localStorage.removeItem('xtractor_user_id');
    localStorage.removeItem('xtractor_user_username');
    localStorage.removeItem('xtractor_user_fullname');
    localStorage.removeItem('xtractor_user_password');
    localStorage.removeItem('xtractor_user_role');
    localStorage.removeItem('xtractor_user_nodes');
    localStorage.removeItem('xtractor_user_isAuthenticate');

    localStorage.setItem('xtractor_lastPage', 'default.jsp');

    window.location.href = 'index.jsp';
};

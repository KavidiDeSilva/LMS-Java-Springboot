

function isEqual(list1,list2,attribute) {

    var equality = false;

    if (list1.length != list2.length)
        equality  = true;
    else {

        for (index in list2) {
            list1 = list1.filter(function (el) {
                return el[attribute].id != list2[index][attribute].id;
            });
        }
        if (list1 != 0)
            equality  = true;
    }

    return equality ;

}




//Error Logging Function

function logError(location, target,  data) {
    h1 = document.createElement("h3");
    tx1 = document.createTextNode(location);
    h2 = document.createElement("h3");
    tx2 = document.createTextNode(target);
    h3 = document.createElement("h4");
    tx3 = document.createTextNode(data);
    h1.appendChild(tx1);
    h2.appendChild(tx2);
    h3.appendChild(tx3);
    err.appendChild(h1);
    err.appendChild(h2);
    err.appendChild(h3);

}



//AJAX Calls

function httpRequest(url,method,data){

    var ajax = new XMLHttpRequest();
    ajax.open(method, url, false);
    ajax.setRequestHeader("Content-type", "application/json");
    startWaiting("Plases Wait");
    ajax.send(JSON.stringify(data, getCircularReplacer()));
    stopWaiting();


    logError("AJAX Responce", url, ajax.responseText+"---"+ajax.status);

    if (ajax.status == 200) {
        if(method=="GET"&&ajax.responseText!="")
        return JSON.parse(ajax.responseText);
        else return ajax.responseText;
    }
    else if (ajax.status == 400 || ajax.status == 500 )
    {
       if(JSON.parse(ajax.responseText).errors!=undefined)
        return JSON.parse(ajax.responseText).errors[0].defaultMessage;
        else
        return JSON.parse(ajax.responseText).message;

}
}

const getCircularReplacer = () => {
    const seen = new WeakSet;
    return (key, value) => {
        if (typeof value === "object" && value !== null) {
            if (seen.has(value)) {
                return;
            }
            seen.add(value);
        }
        return value;
    };
};



//Loading Screen Lock

function startWaiting(msg='Loading'){
     var lockScreen = document.createElement('div');
    lockScreen.id='lockScreen';
    lockScreen.style.zIndex='20000';
    lockScreen.style.position='fixed';
    lockScreen.style.top='0px';
    lockScreen.style.bottom='0px';
    lockScreen.style.left='0px';
    lockScreen.style.right='0px';
    lockScreen.style.textAlign='center';
    lockScreen.style.background='rgba(0,0,0,0.7)';
    lockScreen.style.paddingTop='20%';
    var image = document.createElement('img');
    image.src='../image/loading.gif';
    image.style.width='50px';
    image.style.height='50px';
    lockScreen.appendChild(image);
    var p = document.createElement('div');
    p.id='waitingMsg';
    p.innerHTML = msg;
    p.style.color='white';
    p.style.fontSize='16px';
    p.style.marginTop='10px';
    lockScreen.appendChild(p);
    document.body.appendChild(lockScreen);
}

function setWaitingMsg(msg){
    document.getElementById('waitingMsg').innerHTML=msg;
}

function stopWaiting(){
    document.body.removeChild(document.getElementById('lockScreen'));
}




//Cookie Function - Saving Data to be sent to the Server

var cookie = new Object();

cookie.setValue = function(cname, cvalue, exdays) {
    var d = new Date();
    d.setTime(d.getTime() + (exdays * 24 * 60 * 60 * 1000));
    var expires = "expires="+d.toUTCString();
    document.cookie = cname + "=" + cvalue + ";" + expires + ";path=/";
}

cookie.getValue = function(cname){
    var name = cname + "=";
    var ca = document.cookie.split(';');
    for(var i = 0; i < ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0) == ' ') {
            c = c.substring(1);
        }
        if (c.indexOf(name) == 0){
            return c.substring(name.length, c.length);
        }
    }
    return "";
}


cookie.setObject = function(name, value, exdays) {
    var d = new Date();
    d.setTime(d.getTime() + (exdays * 24 * 60 * 60 * 1000));
    var expires = "expires="+d.toUTCString();
    document.cookie = name + "=" + JSON.stringify(value) + ";" + expires + ";path=/";
}

cookie.getObject = function(name){

    var name = name + "=";
    var ca = document.cookie.split(';');
    for(var i = 0; i < ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0) == ' ') {
            c = c.substring(1);
        }
        if (c.indexOf(name) == 0){
            return JSON.parse(c.substring(name.length, c.length));
        }
    }
    return "";

}

cookie.remove = function (name) {

}




//SessionStorage Functions - Saving Data to be used by Client Scripts

var session = new Object();

session.setValue = function(name, value) {
    sessionStorage.setItem(name, value);
}

session.getValue = function(name) {
    return sessionStorage.getItem(name);
}


session.setObject = function(name, value) {
    sessionStorage.setItem(name, JSON.stringify(value));
}

session.getObject = function(name) {
    return JSON.parse( sessionStorage.getItem(name));
}

session.remove = function(name) {

}






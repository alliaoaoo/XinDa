var InItLogName=function () {
    var img = getCookie("loginImg");
    var loginImg = document.getElementById("logImg");
    if (img!=null&&img!=""&&img!="null"&&loginImg!=null){
        loginImg.setAttribute("src",img);
    }

    var name = getCookie("loginame");
    if (name!=null){
        var Loginname = document.getElementById("logname").innerHTML=name;
        // loginname.value=name;
        var logName = document.getElementById("logName1");
        if (logName!=null){
            logName.innerHTML=name;
        }

    }


};

function quitLogin() {

    var cookies = document.cookie.split(";");

    for (var i = 0; i < cookies.length; i++) {
        var cookie = cookies[i];
        var eqPos = cookie.indexOf("=");
        var name = eqPos > -1 ? cookie.substr(0, eqPos) : cookie;
        document.cookie = name + "=;expires=Thu, 01 Jan 1970 00:00:00 GMT";
    }
    location.reload();
}
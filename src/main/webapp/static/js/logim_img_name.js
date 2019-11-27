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
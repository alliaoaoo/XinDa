var InItaccount = function () {
    $.ajax({
        url:"/member/getlogin",
        dataType:"JSON",
        type:"get",
        success:function (data) {

            sessionStorage.setItem("gender", "");
            //姓名
            if (data.name!=null&&data.name!="null"&&data.name!=""){
                var name = document.getElementById("name");
                name.value=data.name;
            }
            //性别
            if (data.gender!=null&&data.gender!="null"&&data.gender!=""){
                if (data.gender=="1"){
                    //【】【】【】【】更改单选选
                    $("#male").get(0).checked = true;
                    sessionStorage.setItem("gender","1");
                }
                else {
                    $("#girl").get(0).checked = true;
                    sessionStorage.setItem("gender","2");
                }
            }
            //头像
            if (data.headImg!=null&&data.headImg!="null"&&data.headImg!=""){
                var headimg = document.getElementById("showimg");
                headimg.setAttribute("src",data.headImg);
            }

            //邮箱
            if (data.email!=null&&data.email!="null"&&data.email!=""){
                var email = document.getElementById("email");
                email.value=data.email;
            }


        }
    })
};

function setgendermale() {
    sessionStorage.setItem("gender", "1");
}

function setgendergirl() {
    sessionStorage.setItem("gender","2")
}

function setmemlogin(formid) {

    if (!checkeamil('email')){
        return 0;
    }
    if (!validateNonEmpty('name','请输入名字')){
        return 0;
    }


    var formdata = new FormData($(formid)[0]);
    formdata.append("gender",Number(sessionStorage.getItem("gender")));

    swal({
        title: "修改信息",
        text: "您确定要修改信息？",
        type: "warning",
        showCancelButton: true,
        closeOnConfirm: false,
        confirmButtonText: "修改",
        confirmButtonColor: "#ec6c62"
    }, function() {
        $.ajax({
            url: "/member/uplogin",
            data: formdata,
            type: "POST", //类型，POST或者GET
            cache: false,
            processData: false,
            contentType: false,
            dataType: 'json', //数据返回类型，可以是xml、json等
        }).done(function(data) {
            if (data.flag=="成功"){
                swal({
                    title: "操作成功",
                    text: "成功",
                    icon: "success",
                    dangerMode: true,
                },function () {
                    if (data.logImg!=null){
                        document.cookie="loginImg="+data.logImg;
                    }
                    document.cookie='loginame='+data.logname;
                    location.reload();
                });
            }else {
                swal({
                    title: "操作失败",
                    text: data.flag,
                    icon: "error",
                    dangerMode: true,
                },function () {
                    location.reload();
                })
            }

        });
    });

}

function sendpassword() {

    if (!validateNonEmpty('oldpassword','请输入旧密码')){
        return 0;
    }
    if (!validateNonEmpty('password2','请输入新密码')){
        return 0;
    }
    if (!validateNonEmpty('password1','请输入新密码')){
        return 0;
    }


    var old = document.getElementById("oldpassword").value;

    var new1 = document.getElementById("newpassword1").value;

    var new2 = document.getElementById("newpassword2").value;

    swal({
        title: "修改信息",
        text: "您确定要修改信息？",
        type: "warning",
        showCancelButton: true,
        closeOnConfirm: false,
        confirmButtonText: "修改",
        confirmButtonColor: "#ec6c62"
    }, function() {
        if (new1==new2&&new1!=""&&new1!=null){
            $.ajax({
                url: "/member/setpassword",
                data: {"oldpassword":old,"newpassword":new2},
                type: "POST", //类型，POST或者GET
                dataType: 'json', //数据返回类型，可以是xml、json等
            }).done(function(data) {
                if (data.flag=="修改成功"){
                    if (data.logImg!=null){
                        document.cookie="loginImg="+data.headImg;
                    }
                    swal({
                        title: "操作成功",
                        text: "成功",
                        icon: "success",
                        dangerMode: true,
                    },function () {
                        location.reload();
                    });
                }else {
                    swal({
                        title: "操作失败",
                        text: data.flag,
                        icon: "error",
                        dangerMode: true,
                    })
                }

            });
        }
        else {
            swal("错误","新密码不一致","error");
        }
    });

}
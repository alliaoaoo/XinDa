function setlogin(formid) {

    var formdata = new FormData($(formid)[0]);
    swal({
        title: "修改信息",
        text: "您确定要修改信息？",
        type: "warning",
        showCancelButton: true,
        closeOnConfirm: false,
        confirmButtonText: "修改",
        confirmButtonColor: "#ec6c62"
    }, function() {

        if(!checkPhone('cellphone')){
            return 0;
        }
        if (!validateNonEmpty('name','管理员名字不可空')){
            return 0;
        }
        if (!validateNonEmpty('password','密码不可空')){
            return 0;
        }
        if (!checkeamil('email')){
            return 0;
        }

        $.ajax({
            url: "/admin/upadmin",
            data: formdata,
            type: "POST", //类型，POST或者GET
            cache: false,
            processData: false,
            contentType: false,
            dataType: 'json', //数据返回类型，可以是xml、json等
        }).done(function(data) {
            if (data.flag=="成功"){
                if (data.logImg!=null){
                    document.cookie="loginImg="+data.logImg;
                }
                document.cookie='loginame='+data.logname;
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
                },function () {
                    location.reload();
                })
            }

        }).error(function(data) {
            swal("OMG", "操作失败了!", "error");
        });
    });
}

var InItSeeting=function () {
    $.ajax({
        url:"/admin/getadmin",
        type : "get",
        dataType : "JSON",
        success:function (data) {
            var loginid = document.getElementById("loginId");
            loginid.value=data.loginId;
            var password =document.getElementById("password");
            password.value=data.password;
            var name =document.getElementById("name");
            name.value=data.userName;
            var cellphone = document.getElementById("cellphone");
            cellphone.value=data.cellphone;
            var qq =document.getElementById("orgId");
            qq.value=data.orgId;
            var email = document.getElementById("email");
            email.value=data.email;
            var img = document.getElementById("showimg");
            if (data.headImg!=null){
                img.setAttribute("src",data.headImg);
            }else {
                img.setAttribute("src","./images/default_user.png");
            }
        }
    })
};

function seetingdetails() {

    layer.open({
        type: 2,
        area: ['700px', '450px'],
        fixed: false, //不固定
        maxmin: true,
        content: '/operator_seeting.html'
    });
}
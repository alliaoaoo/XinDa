function setlogin(formid) {

    // 省
    var cmbProvince=document.getElementById('cmbProvince').value;
    // 市
    var cmbCity = document.getElementById('cmbCity').value;
    // 区
    var cmbArea = document.getElementById('cmbArea').value;

    if (cmbProvince=='省'&&cmbArea=='区'&&cmbCity=='市'){
        flag=true;
        layer.msg('请选择地区', {icon: 5})
        return 0;
    }

    var formdata = new FormData($(formid)[0]);
    formdata.append("region",cmbProvince+"-"+cmbCity+"-"+cmbArea);
    swal({
        title: "修改信息",
        text: "您确定要修改信息？",
        type: "warning",
        showCancelButton: true,
        closeOnConfirm: false,
        confirmButtonText: "修改",
        confirmButtonColor: "#ec6c62"
    }, function() {
        var flag= true;

        for (var value of formdata.values()) {
            if (value==''){
                layer.msg('信息未输入完全', {icon: 5});
                flag=false;
                return 0;
            }
        }

        if (!chechqq('qq')){
            return 0;
        }
        if (!checkPhone('cellphone')){
            return 0;
        }
        if (!validateNonEmpty('name','请输入登录商名称'))
        {
            return 0;
        }
        if (!checkeamil('email')){
            return 0;
        }
        if (flag){
            $.ajax({
                url: "/provide/uplogin",
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

            })
        }

    });
}
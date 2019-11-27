function setlogin(formid) {

    // 省
    var cmbProvince=document.getElementById('cmbProvince').value;
    // 市
    var cmbCity = document.getElementById('cmbCity').value;
    // 区
    var cmbArea = document.getElementById('cmbArea').value;

    var providerId = getCookie("providerId");

    var formdata = new FormData($(formid)[0]);
    formdata.append("region",cmbProvince+"-"+cmbCity+"-"+cmbArea);
    formdata.append("providerId",providerId);
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
            url: "/admin/setprovider",
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
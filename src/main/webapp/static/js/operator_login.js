/* 登录 */
$(".login-btn").on("click", function(){
        var cellphone = $("[name=cellphone]").val();
        var password = $("[name=password]").val();

        if (cellphone == '' || password == '') {
            alert("错误\n用户名或者密码为空！");
            return;
        }

        /* ajax请求 */
        $.ajax({
            url : "/admin/login",
            type : "post",
            data : {
                "cellphone" : cellphone,
                "password" : password
            },
            dataType : "JSON",
            success : function(res) {
                if(res.cellphone == cellphone){
                    location.href="operator_product.html";
                }
            },
            error : function () {
                alert("用户不存在或者密码错误");
            }
        });

});

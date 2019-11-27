var InItSeeting=function () {
  $.ajax({
      url:"/provide/getshop",
      type : "get",
      dataType : "JSON",
      success:function (data) {
          var loginid = document.getElementById("loginId");
          loginid.value=data.loginId;
          var password =document.getElementById("password");
          password.value=data.password;
          var name =document.getElementById("name");
          name.value=data.name;
          var cellphone = document.getElementById("cellphone");
          cellphone.value=data.cellphone;
          var weixin =document.getElementById("weixin");
          weixin.value=data.weixin;
          var qq =document.getElementById("qq");
          qq.value=data.qq;
          var email = document.getElementById("email");
          email.value=data.email;
          if (data.region!=null){
              var region=data.region.split("-");
              addressInit('cmbProvince', 'cmbCity', 'cmbArea',region[0],region[1],region[2]);
          }
          var img = document.getElementById("showimg");
          if (data.loginimg!=null){
              img.setAttribute("src",data.loginimg);
          }else {
              img.setAttribute("src","./images/default_user.png");
          }

      }
  })
};
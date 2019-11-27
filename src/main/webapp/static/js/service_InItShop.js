var InItShop = function () {
    $.ajax({
        url:"/provide/getshop",
        type : "get",
        dataType : "JSON",
        success:function (data) {
            var providerInfo = document.getElementById("providerInfo");
            providerInfo.value=data.providerInfo;

            var qq = document.getElementById("qq");
            qq.value=data.qq;
            var cellphone =document.getElementById("cellphone");
            cellphone.value=data.cellphone;

            var showimg =document.getElementById("showimg");
            if (data.providerImg!=null&&data.providerImg!="null"&&data.providerImg!=""){
                showimg.setAttribute("src",data.providerImg);
                showimg.setAttribute("style","width: 400px;height: 400px")
            }
            var count=$("#workTime").find("option").length;

            for(var i=0;i<count;i++)
            {
                if($("#workTime").get(0).options[i].text == data.workTime) {
                $("#workTime").get(0).options[i].selected = true;
                break;
            }
            }
        }
    })
};
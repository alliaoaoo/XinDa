var InItValue=function () {
    var providerId = getCookie("productId");
    $.ajax({
        url:"/product/getproduct",
        data:{"productId":providerId},
        type: "POST", //类型，POST或者GET
        dataType: 'json', //数据返回类型，可以是xml、json等
        success:function (data) {
            var servicename=document.getElementById("name");
            servicename.value=data.serviceName;
            var serviceInfo=document.getElementById("intro");
            serviceInfo.value=data.serviceInfo;
            var servicmessage=document.getElementById("message");
            servicmessage.value=data.serviceContent;
            var serviceImg=document.getElementById("showimg");
            serviceImg.setAttribute("src",data.serviceImg);
            var region=data.region.split("-");
            addressInit('cmbProvince', 'cmbCity', 'cmbArea',region[0],region[1],region[2]);
            var price = document.getElementById("price");
            price.value=(data.price);
            var unit = document.getElementById("unit");
            unit.value=(data.unit);
            var storeNum=document.getElementById("storeNum");
            storeNum.value=(data.storeNum);
        }
    })
};

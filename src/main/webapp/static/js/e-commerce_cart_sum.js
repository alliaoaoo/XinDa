function setCartSum() {
    $.ajax({
        url:"/api/cartsum",
        dataType:"json",
        type:"get",
        success:function (data) {

            var Loginname = document.getElementById("cartSum").innerHTML=data.sum;
        }
    })
}
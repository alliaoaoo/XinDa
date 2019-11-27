function setmesssum() {
    $.ajax({
        url:"/provide/getmessagesum",
        dataType:"json",
        type:"get",
        success:function (data) {
            $("#messlist").empty();
            $("#messlist").append('<i  class="fa fa-bell-o fa-fw"></i>通知('+data.messsum+')');
        }
    })
}
function setordersum() {
    $.ajax({
        url:"/provide/messordersum",
        dataType:"json",
        type:"get",
        success:function (data) {
            $("#ordersum").empty();
            $("#ordersum").append('<i class="fa fa-shopping-cart fa-fw"></i>新订单('+data.messsum+')');
        }
    })
}
function messlist() {
    layer.open({
        type: 2,
        area: ['600px', '450px'],
        fixed: false, //不固定
        maxmin: true,
        content: '/service_message.html'
    });
}

function messorderlist() {

    layer.open({
        type: 2,
        area: ['700px', '450px'],
        fixed: false, //不固定
        maxmin: true,
        content: '/service_newordermess.html'
    });
}
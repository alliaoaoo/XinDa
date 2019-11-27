ids=[];

//添加主键列表
function addis(id) {
    ids.push(id);
}

//设置图片信息
function setAllImg() {
    for (var i=0;i<ids.length;i++){
        getImg(ids[i]);
    }
    ids=[];
}

function getImg(id) {
    $.ajax({
        url:"/api/orderserviceimg",
        dataType:"json",
        type:"get",
        data:{"orderId":id},
        success:function (data) {
            var imgId = "img"+id;
            var img = document.getElementById(imgId);
            if(data.serviceImg!=null||data.serviceImg!="null"){
                img.setAttribute("src",data.serviceImg);
            }
        }
    })
}
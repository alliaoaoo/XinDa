var ids=[];

function addids(id) {
    ids.push(id);
}

function setnamecell() {

    for (var i=0;i<ids.length;i++){
        var id1 = "#sum"+ids[i];
        var id2 = "#total"+ids[i];
        getnamecell(ids[i],id1,id2);
    }
    ids=[];
}

function getnamecell(id,id1,id2) {
    $.ajax({
        url:"/api/orderattr",
        data:{"memberId":id},
        dataType:"json",
        type:"get",
        success:function (data) {
            $(id1).empty();
            $(id1).append(data.ordersum);

            $(id2).empty();
            $(id2).append("￥"+data.total);
        }
    })
}
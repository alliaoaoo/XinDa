var ids=[];

function addids(id) {
    ids.push(id);
}

function setnamecell() {

    for (var i=0;i<ids.length;i++){
        var id1 = "#membername"+ids[i];
        getnamecell(ids[i],id1);
    }
    ids=[];
}

function getnamecell(id,id1) {
    $.ajax({
        url:"/api/memberattribute",
        data:{"orderId":id},
        dataType:"json",
        type:"get",
        success:function (data) {
            $(id1).empty();
            $(id1).append(data.name);
        }
    })
}
var ids=[];

function addids(id) {
    ids.push(id);
}

function setnamecell() {

    for (var i=0;i<ids.length;i++){
        var id1 = "#membername"+ids[i];
        var id2 = "#cellphone"+ids[i];
        getnamecell(ids[i],id1,id2);

    }
    ids=[];
}

function getnamecell(id,id1,id2) {
    $.ajax({
        url:"/api/memberattribute",
        data:{"orderId":id},
        dataType:"json",
        type:"get",
        success:function (data) {
            $(id1).empty();
            $(id1).append(data.name);

            $(id2).empty();
            $(id2).append(data.cellphone);
        }
    })
}
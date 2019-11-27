var ids=[];

function addids(id) {
    ids.push(id);
}


function aonclick(page) {

    // 服务类型
    var sortStyle = document.getElementById('sortStyle').value;
    // 服务分类
    var sortType = document.getElementById('sortType').value;

    if (sortStyle=='--选择服务类型'){
        sortStyle="";
    }if (sortType=='--选择服务分类'){
        sortType="";
    }

    var word = sessionStorage.getItem("word");

    sessionStorage.setItem("page",page);

    if (word==null||word==""){
        word=null;
    }
    $.ajax({
        url : "/admin/productrecom",
        type : "get",
        dataType : "JSON",
        data:{"page":page,
              "word":word,
              "style":sortStyle,
              "type":sortType,
              "status":sessionStorage.getItem("status")},
        success : function(data) {

            sessionStorage.setItem("page",page);
            var optionString1 = "";
            if (data.size==0){
                optionString1+='<td></td><td></td><td></td><td></td><td></td><td></td>';
            }
            for(var i=0;i<data.size;i++){
                addids(data.list[i].productId);
                optionString1+='<tr>\n' +
                    '<td>'+data.list[i].serviceName+'</td>\n' +
                    '<td>'+data.list[i].serviceInfo+'</td>\n' +
                    '<td id="'+"buy"+data.list[i].productId+'">8</td>\n' +
                    '<td id="'+"providername"+data.list[i].productId+'">服务商</td>\n' +
                    '<td>¥'+data.list[i].price+'</td>\n';
                if (data.list[i].recommend==1){
                    optionString1+='<td><input checked="checked" id="'+"checkbox"+data.list[i].productId+'" class="checkbox" onclick="checkboxonchick(\''+data.list[i].productId+'\')" type="checkbox" /></td>\n';

                }else {
                    optionString1+='<td><input id="'+"checkbox"+data.list[i].productId+'" class="checkbox" onclick="checkboxonchick(\''+data.list[i].productId+'\')" type="checkbox" /></td>\n';

                }
                    optionString1+='</tr>';

            }
            setnamecell();

            var optionString2 = "";
            for (var i=1;i<data.pages+1;i++){
                if (i==data.pageNum){
                    optionString2+='<li value="'+i+'"><a class="active" href="">'+i+'</a></li>';
                    continue;
                }
                if(i<data.pageNum+2&&i>data.pageNum-2){
                    optionString2+='<li value="'+i+'"><a onclick="aonclick('+i+')">'+i+'</a></li>';
                }
            }
            $("#productlist").empty();
            // 动态添加代码
            $("#productlist").append(optionString1);

            $("#pagination").empty();
            $("#pagination").append(optionString2);
        }
    });
}

function setnamecell() {

    for (var i=0;i<ids.length;i++){
        var id1 = "#buy"+ids[i];
        var id2 = "#providername"+ids[i];
        getnamecell(ids[i],id1,id2);
    }
    ids=[];
}

function getnamecell(id,id1,id2) {
    $.ajax({
        url:"/product/getattr",
        data:{"productId":id},
        dataType:"json",
        type:"get",
        success:function (data) {
            $(id1).empty();
            $(id1).append(data.buysum);

            $(id2).empty();
            $(id2).append(data.providername);
        }
    })
}

function setstatus(status) {
    sessionStorage.setItem("status",status);
    aonclick('1');

}
function selectname() {
    var word = document.getElementById("word").value;
    sessionStorage.setItem("word",word);
    aonclick('1');
}
function selecttype() {
    aonclick('1');
}

function checkboxonchick(id) {
    var checkid = "#checkbox"+id;
    if ($(checkid).get(0).checked) {
        $.ajax({
            url:"/product/setrecommemd",
            type:"get",
            dataType:"json",
            data:{"productId":id},
            success:function (data) {
                if (data.flag=="成功"){
                    layer.msg('设置推荐服务成功');
                }
                else {
                    layer.msg('设置失败', {icon: 5});
                }
            }
        })
    }else {
        $.ajax({
            url:"/product/setnorecommemd",
            type:"get",
            dataType:"json",
            data:{"productId":id},
            success:function (data) {
                if (data.flag=="成功"){
                    layer.msg('取消推荐成功');
                }
                else {
                    layer.msg('设置失败', {icon: 5});
                }
            }
        })
    }
}

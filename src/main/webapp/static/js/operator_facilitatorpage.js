function aonclick(page) {

    var word = sessionStorage.getItem("word");

    sessionStorage.setItem("page",page);

    if (word==null||word==""){
        word=null;
    }
    $.ajax({
        url : "/admin/providerlist",
        type : "get",
        dataType : "JSON",
        data:{"page":page,
            "word":word,
            "status":sessionStorage.getItem("status")},
        success : function(data) {

            sessionStorage.setItem("page",page);
            var optionString1 = "";
            if (data.size==0){
                optionString1+='<td></td><td></td><td></td><td></td><td></td><td></td>';
            }
            for(var i=0;i<data.size;i++){
                optionString1+='<tr>\n' +
                    '<td>'+data.list[i].name+'</td>\n' +
                    '<td>'+data.list[i].region+'</td>\n' +
                    '<td>'+data.list[i].cellphone+'</td>\n' +
                    '<td align="center">'+data.list[i].providerInfo+'</td>\n' +
                    '<td>\n' +
                    '<span onclick="details(\''+data.list[i].id+'\')" class="handle-btn"><i class="fa fa-edit fa-fw"></i>详情</span>\n' ;
                if (sessionStorage.getItem("status")=='1'){
                    optionString1+='<span onclick="stopprovider(\''+data.list[i].id+'\')" class="handle-btn"><i class="fa fa-circle-o fa-fw"></i>停用</span>\n';
                }
                else {
                    optionString1+='<span onclick="startprovider(\''+data.list[i].id+'\')" class="handle-btn"><i class="fa fa-circle-o fa-fw"></i>启用</span>\n';

                }
                    optionString1+='</td>\n' +
                    '<td></td>\n' +
                    '</tr>';

            }
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

function selectName() {
    var word = document.getElementById('word').value;
    sessionStorage.setItem('word',word);
    aonclick('1');
}

function startprovider(providerId) {
    $.ajax({
        url: "/admin/through",
        type:"get",
        dataType: "json",
        data:{"providerId":providerId},
        success:function (data) {
            if (data.flag="成功"){
                layer.msg('操作成功');
                aonclick(sessionStorage.getItem("page"));
            }else {
                layer.msg('操作失败', {icon: 5});
                aonclick(sessionStorage.getItem("page"));
            }
        },error:function () {
            layer.msg('操作失败', {icon: 5});
            aonclick(sessionStorage.getItem("page"));
        }
    })
}

function stopprovider(providerId) {
    $.ajax({
        url: "/admin/stop",
        type:"get",
        dataType: "json",
        data:{"providerId":providerId},
        success:function (data) {
            if (data.flag="成功"){
                layer.msg('操作成功');
                aonclick(sessionStorage.getItem("page"));
            }else {
                layer.msg('操作失败', {icon: 5});
                aonclick(sessionStorage.getItem("page"));
            }
        },error:function () {
            layer.msg('操作失败', {icon: 5});
            aonclick(sessionStorage.getItem("page"));
        }
    })
}

function details(providerId) {
    document.cookie="providerId="+providerId;
    layer.open({
        type: 2,
        area: ['847px', '450px'],
        fixed: false, //不固定
        maxmin: true,
        content: '/operator_providerdetails.html'
    });

}
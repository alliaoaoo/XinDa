function aonclick(page) {
    // 服务类型
    var sortStyle = document.getElementById('sortStyle').value;
    // 服务分类
    var sortType = document.getElementById('sortType').value;

    var word = document.getElementById("word").value;

    sessionStorage.setItem("page",page);

    if (sortStyle=="--选择服务类型"){
        sortStyle="";
    }if (sortType=="--选择服务分类")
    {
        sortType="";
    }if (word==null||word==""){
        word=null;
    }
    $.ajax({
        url : "/admin/productlist",
        type : "get",
        dataType : "JSON",
        data:{"page":page,
            "style":sortStyle,
            "type":sortType,
            "word":word},
        success : function(data) {
            document.cookie="page="+page;
            var optionString1 = "";
            for(var i=0;i<data.size;i++){
                optionString1+='<tr>\n' +
                    '                        <td></td>\n' +
                    '                        <td>'+data.list[i].serviceName+'</td>\n' +
                    '                        <td>'+data.list[i].serviceInfo+'</td>\n' +
                    '                        <td>'+data.list[i].price+'</td><td>\n';
                if (data.list[i].status==1){
                    optionString1+='<td><span class="down-line-mark down-line-mark-orange">等待中</span></td>\n';
                }else if (data.list[i].status==2){
                    optionString1+= '<td><span class="up-line-mark up-line-mark-orange">上线中</span></td>\n';
                }else if (data.list[i].status==3){
                    optionString1+='<td><span class="down-line-mark down-line-mark-orange">不同意</span></td>\n';
                }

                optionString1+= '<td>\n' +
                    '<span class="handle-btn"><a href="#" style="color: #6b768c" onclick="details(\''+data.list[i].productId+'\')"><i class="fa fa-edit fa-fw"></i>详情</a></span>\n';

                if (data.list[i].status==1){
                    optionString1+='<span class="handle-btn"><a href="#" style="color: #6b768c" onclick="noagree(\''+data.list[i].productId+'\')"><i class="fa fa-adjust fa-fw"></i>不同意</a></span>\n' +
                        '<span class="handle-btn"><a  href="#" style="color: #6b768c" onclick="agree(\''+data.list[i].productId+'\')"><i class="fa fa-opera fa-fw"></i>同意</a></span>\n' +
                        '</td><td></td>\n' +
                        '</tr>';
                }else if (data.list[i].status==3){
                    optionString1+='<span class="handle-btn"><a  href="#" style="color: #6b768c" onclick="agree(\''+data.list[i].productId+'\')" ><i class="fa fa-opera fa-fw"></i>同意</a></span>\n';
                }else {
                    optionString1+='<span class="handle-btn"><a  href="#" style="color: #6b768c" onclick="noagree(\''+data.list[i].productId+'\')"><i class="fa fa-arrow-down fa-fw"></i>停用</a></span>\n'
                }

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


function details(productId) {

    document.cookie="productId="+productId;

    layer.open({
        type: 2,
        area: ['700px', '450px'],
        fixed: false, //不固定
        maxmin: true,
        content: '/operator_productdetails.html'
    });
}

function agree(productId) {
    $.ajax({
        url: "/product/agree",
        type:"get",
        dataType: "json",
        data:{"productId":productId},
        success:function (data) {
            if (data.flag="更新状态成功"){
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

function noagree(productId) {
    $.ajax({
        url: "/product/noagree",
        type:"get",
        dataType: "json",
        data:{"productId":productId},
        success:function (data) {
            if (data.flag="更新状态成功"){
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
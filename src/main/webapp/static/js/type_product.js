function getList() {
    // 服务类型
    var sortStyle = document.getElementById('sortStyle').value;
    // 服务分类
    var sortType = document.getElementById('sortType').value;
    if (sortStyle=="--选择服务类型"){
        sortStyle=null;
    }if (sortType=="--选择服务分类")
    {
        sortType=null;
    }
    $.ajax({
        url : "/product/list",
        type : "get",
        dataType : "JSON",
        data:{"style":sortStyle,
            "type":sortType},
        success : function(data) {
            var optionString1 = "";
            for(var i=0;i<data.size;i++){
                // alert(data.size);
                optionString1+='<tr><td>'+data.list[i].serviceName+'</td><td>'+data.list[i].serviceInfo+'</td>\n' +
                    '    <td>'+data.list[i].price+'</td>\n' +
                    '    <td><span class="down-line-mark down-line-mark-orange">下线</span></td>\n' +
                    '    <td>\n' +
                    '    <a href="./service_edit.html" onclick="edit(\''+data.list[i].productId+'\')"><span class="handle-btn"><i class="fa fa-edit fa-fw"></i>编辑</span></a>\n' +
                    '    <a href="javascript:void(0);" onclick="productDel(\''+data.list[i].productId+'\')"><span class="handle-btn"><i class="fa fa-close fa-fw"></i>删除</span></a>\n' +
                    '    <span class="handle-btn"><i class="fa fa-arrow-up fa-fw"></i>上线</span>\n' +
                    '    </td>\n' +
                    '    </tr>';
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
            $("#js_addlist").empty();
        }
    });
}
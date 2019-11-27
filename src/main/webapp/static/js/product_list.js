function aonclick(page) {
    // 服务类型
    var sortStyle = document.getElementById('sortStyle').value;
    // 服务分类
    var sortType = document.getElementById('sortType').value;

    var word = document.getElementById("word").value;

    if (sortStyle=="--选择服务类型"){
        sortStyle=null;
    }if (sortType=="--选择服务分类")
    {
        sortType=null;
    }if (word==null||word==""){
        word=null;
    }
        $.ajax({
            url : "/product/list",
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
                    optionString1+='<tr><td>'+data.list[i].serviceName+'</td><td>'+data.list[i].serviceInfo+'</td>\n' +
                        '    <td>'+data.list[i].price+'</td>\n';
                    if (data.list[i].status==0){
                        optionString1+='    <td><span class="down-line-mark down-line-mark-orange">下线中</span></td>\n';
                    }else if (data.list[i].status==1){
                        optionString1+='    <td><span class="down-line-mark down-line-mark-orange">申请中</span></td>\n';
                    }else {
                        optionString1+='    <td><span class="up-line-mark up-line-mark-orange">在线</span></td>\n';
                    }

                    optionString1+='    <td>\n' +
                        '    <a href="./service_edit.html" onclick="edit(\''+data.list[i].productId+'\')"><span class="handle-btn"><i class="fa fa-edit fa-fw"></i>编辑</span></a>\n' +
                        '    <a href="javascript:void(0);" onclick="productDel(\''+data.list[i].productId+'\')"><span class="handle-btn"><i class="fa fa-close fa-fw"></i>删除</span></a>\n' ;

                    if (data.list[i].status!=0){
                        optionString1+='    <a href="javascript:void(0);" onclick="offline(\''+data.list[i].productId+'\')"><span  class="handle-btn"><i class="fa fa-arrow-down fa-fw"></i>下线</span></a>\n';
                    }else {
                        optionString1+='    <a href="javascript:void(0);" onclick="online(\''+data.list[i].productId+'\')"><span class="handle-btn"><i class="fa fa-arrow-up fa-fw"></i>上线</span></a>\n';
                    }
                    optionString1+= '</td></tr>';
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

function online(productId) {
    $.ajax({
        url:"/product/online",
        type:"get",
        dataType : "JSON",
        data:{"productId":productId.toString()},
        success:function (data) {
            if (data.flag=="更新状态成功"){
                var page = getCookie("page")
                aonclick(page);
            }else {
                swal({
                    title: "操作失败",
                    text: "上线服务失败",
                    icon: "error",
                    dangerMode: true,
                })
            }
        },error:function () {
            swal({
                title: "操作失败",
                text: "上线服务失败",
                icon: "error",
                dangerMode: true,
            })
        }
    })
}

function offline(productId) {
    $.ajax({
        url:"/product/offline",
        type:"get",
        dataType : "JSON",
        data:{"productId":productId.toString()},
        success:function (data) {
            if (data.flag=="更新状态成功"){
                var page = getCookie("page")
                aonclick(page);
            }else {
                swal({
                    title: "操作失败",
                    text: "下线服务失败",
                    icon: "error",
                    dangerMode: true,
                })
            }
        },error:function () {
            swal({
                title: "操作失败",
                text: "下线服务失败",
                icon: "error",
                dangerMode: true,
            })
        }
    })
}

function productDel(productID) {
    swal({
        title: "您确定要删除吗？",
        text: "您确定要删除这条数据？",
        type: "warning",
        showCancelButton: true,
        closeOnConfirm: false,
        confirmButtonText: "是的，我要删除",
        confirmButtonColor: "#ec6c62"
    },function(){
        $.ajax({
            url:"/product/delete",
            type : "post",
            dataType : "JSON",
            data:{"productID":productID.toString()}
        }).done(function(data) {
            if (data.flag=="删除成功"){
                swal({
                    title: "操作成功",
                    text: "删除服务成功",
                    icon: "success",
                    dangerMode: true,
                },function () {
                    var page=getCookie("page");
                    aonclick(page);
                });
            }
            else {
                swal({
                    title: "操作失败",
                    text: "删除服务失败",
                    icon: "error",
                    dangerMode: true,
                })
            }
        }).error(function (data) {
            swal("OMG", "删除失败!", "error");
        });
    });
}
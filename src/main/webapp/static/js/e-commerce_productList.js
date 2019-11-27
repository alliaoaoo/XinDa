var InIteproductlist = function () {
    $.ajax({
        url:"/member/productlist",
        type:"get",
        dataType : "JSON",
        success:function (data) {
            sessionStorage.setItem("style", "");
            sessionStorage.setItem("word", "");
            sessionStorage.setItem("page", "1");
            sessionStorage.setItem("status", "product");
            var optionString1 = "";
            for (var i=0;i<data.size;i++) {
                optionString1 += '<div class="article">\n' +
                    '            <img src=\"' + data.list[i].serviceImg + '\" alt="图片" />\n' +
                    '            <ul class="article-info">\n' +
                    '                <li>' + data.list[i].serviceName + '</li>\n' +
                    '                <li>' + data.list[i].serviceInfo + '</li>\n' +
                    '                <li>' + data.list[i].unit + '</li>\n' +
                    '            </ul>\n' +
                    '            <ul class="article-price">\n' +
                    '                <li>' + data.list[i].price + '</li>\n' +
                    '                <li>\n' +
                    '                    <a href="e-commerce_settlement.html">立即购买</a>\n' +
                    '                    <span onclick="addcart(\''+data.list[i].productId+'\')">加入购物车</span>\n' +
                    '                </li>\n' +
                    '            </ul>\n' +
                    '        </div>';
            }
                var optionString2 = "";
                for (var i=1;i<data.pages+1;i++){
                    if (i==data.pageNum){
                        optionString2+='<li value="'+i+'"><a class="active" href="#">'+i+'</a></li>';
                        continue;
                    }
                    if(i<data.pageNum+2&&i>data.pageNum-2){
                        optionString2+='<li value="'+i+'"><a onclick="aonclick('+i+')">'+i+'</a></li>';
                    }
                }

                $("#productList").empty();
                // 动态添加代码
                $("#productList").append(optionString1);

                $("#pagination").empty();
                $("#pagination").append(optionString2);
            }
    })
};

function aonclick(page) {
    // 服务类型
    var sortStyle = sessionStorage.getItem("style");

    var word = sessionStorage.getItem("word");

    if (sortStyle=="--选择服务类型"){
        sortStyle=null;
    }if (word==null||word==""){
        word=null;
    }
    $.ajax({
        url : "/member/productlist",
        type : "get",
        dataType : "JSON",
        data:{"page":page,
            "style":sortStyle,
            "word":word},
        success : function(data) {
            sessionStorage.setItem("status", "product");
            sessionStorage.setItem("page", page);
            var optionString1 = "";
            for (var i=0;i<data.size;i++) {
                optionString1 += '<div class="article">\n' +
                    '            <img src=\"' + data.list[i].serviceImg + '\" alt="图片" />\n' +
                    '            <ul class="article-info">\n' +
                    '                <li>' + data.list[i].serviceName + '</li>\n' +
                    '                <li>' + data.list[i].serviceInfo + '</li>\n' +
                    '                <li>' + data.list[i].unit + '</li>\n' +
                    '            </ul>\n' +
                    '            <ul class="article-price">\n' +
                    '                <li>' + data.list[i].price + '</li>\n' +
                    '                <li>\n' +
                    '                    <a href="e-commerce_settlement.html">立即购买</a>\n' +
                    '                    <span onclick="addcart(\''+data.list[i].productId+'\')">加入购物车</span>\n' +
                    '                </li>\n' +
                    '            </ul>\n' +
                    '        </div>';
            }
            var optionString2 = "";
            for (var i=1;i<data.pages+1;i++){
                if (i==data.pageNum){
                    optionString2+='<li value="'+i+'"><a class="active" href="#">'+i+'</a></li>';
                    continue;
                }
                if(i<data.pageNum+2&&i>data.pageNum-2){
                    optionString2+='<li value="'+i+'"><a onclick="aonclick('+i+')">'+i+'</a></li>';
                }
            }
            $("#productList").empty();
            // 动态添加代码
            $("#productList").append(optionString1);

            $("#pagination").empty();
            $("#pagination").append(optionString2);
        }
    });
}

function addcart(productId) {
    swal({
        title: "添加到购物车？",
        text: "确定添加服务到购物车？",
        type: "warning",
        showCancelButton: true,
        closeOnConfirm: false,
        confirmButtonText: "确定",
        confirmButtonColor: "#ec6c62"
    }, function() {
        $.ajax({
            url: "/member/addcart",        //后台url
            data: {"productId":productId.toString()},
            type: "POST", //类型，POST或者GET
            dataType: 'json' //数据返回类型，可以是xml、json等
        }).done(function(data) {
            if (data.flag=="已添加到购物车"){
                swal({
                    title: "操作成功",
                    text: data.flag,
                    icon: "success",
                    dangerMode: true,
                });
            }else {
                swal({
                    title: "操作失败",
                    text: data.flag,
                    icon: "success",
                    dangerMode: true,
                });
            }
        })
    });
}


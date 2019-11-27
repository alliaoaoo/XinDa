function eInItshop() {

    var alist = document.getElementById("e-sort").getElementsByTagName("a");

    for (var i=0;i<alist.length;i++){
        if (alist[i].id=="shop"){
            alist[i].setAttribute("class","border-b");
        }else {
            alist[i].setAttribute("class","");
        }
    }
    $.ajax({
        url : "/api/shoplist",
        type : "get",
        dataType : "JSON",
        data:{"page":"1"},
        success : function(data) {
            sessionStorage.setItem("status", "product");
            sessionStorage.setItem("page", "1");
            var optionString1 = "";
            for (var i=0;i<data.size;i++) {
                optionString1 += '<div class="article">\n' +
                    '            <a onclick="setshopid(\''+data.list[i].id+'\')" href="e-commerce_shopservcies.html"><img src=\"' + data.list[i].providerImg + '\" alt="图片" /></a>\n' +
                    '            <ul class="article-info">\n' +
                    '                <li>' + data.list[i].name + '</li>\n' +
                    '                <li>' + data.list[i].providerInfo + '</li>\n' +
                    '                <li>' + data.list[i].region+ '</li>\n' +
                    '            </ul>\n' +
                    '            <ul class="article-price">\n' +
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
                    optionString2+='<li value="'+i+'"><a onclick="pageshop('+i+')">'+i+'</a></li>';
                }
            }
            $("#price").empty();

            $("#li-price").empty();

            $("#productList").empty();
            // 动态添加代码
            $("#productList").append(optionString1);

            $("#pagination").empty();
            $("#pagination").append(optionString2);
        }
    });
}
function setshopid(id) {
    document.cookie="providerId="+id;
}


function pageshop(page) {

    var word = sessionStorage.getItem("word");
    $.ajax({
        url : "/api/shoplist",
        type : "get",
        dataType : "JSON",
        data:{"page":page,
        "word":word},
        success : function(data) {
            sessionStorage.setItem("status", "product");
            sessionStorage.setItem("page", "1");
            var optionString1 = "";
            for (var i=0;i<data.size;i++) {
                optionString1 += '<div class="article">\n' +
                    '            <img src=\"' + data.list[i].providerImg + '\" alt="图片" />\n' +
                    '            <ul class="article-info">\n' +
                    '                <li>' + data.list[i].name + '</li>\n' +
                    '                <li>' + data.list[i].providerInfo + '</li>\n' +
                    '                <li>' + data.list[i].region+ '</li>\n' +
                    '            </ul>\n' +
                    '            <ul class="article-price">\n' +
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
                    optionString2+='<li value="'+i+'"><a onclick="pageshop('+i+')">'+i+'</a></li>';
                }
            }
            $("#price").empty();

            $("#li-price").empty();

            $("#productList").empty();
            // 动态添加代码
            $("#productList").append(optionString1);

            $("#pagination").empty();
            $("#pagination").append(optionString2);
        }
    });
}
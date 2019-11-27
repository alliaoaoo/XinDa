var InItSelect = function () {
    var status = sessionStorage.getItem("status");
    var word = sessionStorage.getItem("word");
    var select =document.getElementById("word");
    var span1 = document.getElementById("selectspan1");
    var span2 = document.getElementById("selectspan2");
    select.value=word;

    if (status=="product"){
        span1.setAttribute("class","search-product font-aqua");
        span2.setAttribute("class","search-service");
        $.ajax({
            url:"/member/productlist",
            type:"get",
            dataType : "JSON",
            data:{"word":word},
            success:function (data) {
                sessionStorage.setItem("style", "");
                sessionStorage.setItem("page", "1");
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
                        '                    <span>加入购物车</span>\n' +
                        '                </li>\n' +
                        '            </ul>\n' +
                        '        </div>';
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

                $("#productList").empty();
                // 动态添加代码
                $("#productList").append(optionString1);

                $("#pagination").empty();
                $("#pagination").append(optionString2);
            }
        })
    }else {
        span1.setAttribute("class","search-service");
        span2.setAttribute("class","search-product font-aqua");
        $.ajax({
            url : "/api/shoplist",
            type : "get",
            dataType : "JSON",
            data:{"page":"1","word":word},
            success : function(data) {
                sessionStorage.setItem("status", "provider");
                sessionStorage.setItem("page", "1");
                var optionString1 = "";
                for (var i=0;i<data.size;i++) {
                    optionString1 += '<div class="article">\n' +
                        '           <a href="e-commerce_shopservcies.html" onclick="setshopid(\''+data.list[i].id+'\')"> <img  src=\"' + data.list[i].providerImg + '\" alt="图片" /></a>\n' +
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
};

function setshopid(id) {
    document.cookie="providerId="+id;
}
var cartsortInit = function(sortStyle){
    /* ajax请求 */
    $.ajax({
        url : "/product/getSort",
        type : "post",
        dataType : "JSON",
        success : function(data) {

            var optionString1 = '<li><a href="e-commerce_product.html" class="border-b">全部产品</a></li>';
            for(var i=0;i<data.Style.length;i++){
                optionString1+='<li><a id="'+data.Style[i].styleId+'" href="#"  onclick="estyle(\''+data.Style[i].styleId+'\')">'+data.Style[i].name+'</a></li>';
            }

            $(sortStyle).empty();
            // 动态添加代码
            $(sortStyle).append(optionString1);
        }
    });
};

function estyle(style) {
    //保存类型信息
    sessionStorage.setItem("style", style);
    sessionStorage.setItem("word", "");
    var alist = document.getElementById("e-sort").getElementsByTagName("a");
    for (var i=0;i<alist.length;i++){
        if (alist[i].id!=style&&alist[i].id!="shop"){

            alist[i].setAttribute("class","");
            aonclick(1);
        }else {
            if (alist[i].id!="shop"){
                alist[i].setAttribute("class","border-b");
            }
        }
    }

}
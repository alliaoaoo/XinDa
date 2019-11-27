var eCartInIt = function (carlist,pagination) {
    $.ajax({
        url:"/member/cartlist",
        dataType : "JSON",
        type:"get",
        success:function (data) {
            sessionStorage.setItem("style", "");
            sessionStorage.setItem("word", "");
            sessionStorage.setItem("page", "1");
            sessionStorage.setItem("status", "product");
            sessionStorage.setItem("buyprice", "");
            var optionString1 = '<p class="product_num font-aqua">全部商品（'+data.size+'）</p>\n' +
                '        <hr color="#ededed" size="1">\n' +
                '        <ul class="product_list_title">\n' +
                '<li style="width: 20px"></li>'+
                '            <li>公司</li>\n' +
                '            <li>服务商品</li>\n' +
                '            <li>单价</li>\n' +
                '            <li>数量</li>\n' +
                '            <li>金额</li>\n' +
                '            <li>操作</li>\n' +
                '        </ul>';
            for (var i=0;i<data.size;i++){
                    optionString1+='        <p class="shop">'+data.list[i].unit+'</p>\n' +
                        '        <ul class="merchandise">\n' +
                        '            <li style="padding-top: 7px;">\n' +
                        '<input id=\"'+data.list[i].id+"chbox"+'\"  onclick="buyprice(\''+data.list[i].id+'\')" ' +
                        'style="width: 20px;height: 20px" type="checkbox"/>'+
                        '                <img style="width: 50px;height: 50px" id=\"'+data.list[i].productId+'\" src="" alt=\"'+data.list[i].unit+'\">\n' +
                        '            </li>\n' +
                        '            <li>'+data.list[i].serviceName+'</li>\n' +
                        '            <li>'+data.list[i].unitPrice+'</li>\n' +
                        '            <li>\n' +
                        '                <span id="" onclick="lesscart(\''+data.list[i].id+'\')">-</span>\n' +
                        '                <input style="width: 30px" id=\"'+data.list[i].id+'\" onBlur="setnum(\''+data.list[i].id+'\')" value=\"'+data.list[i].buyNum+'\" />\n' +
                        '                <span onclick="addcart(\''+data.list[i].id+'\')">+</span>\n' +
                        '            </li>\n' +
                        '            <li value=\"'+data.list[i].totalPrice+'\" id=\"'+data.list[i].id+"total"+'\">\n' +
                        '                '+data.list[i].totalPrice+'\n' +
                        '            </li>\n' +
                        '            <li>\n' +
                        '                <span><a href="#" onclick="delcart(\''+data.list[i].id+'\')">删除</a></span>\n' +
                        '            </li>\n' +
                        '        </ul>'
            }

            optionString1+='        <ul class="price">\n' +
                '            <li>金额合计<span id="buytotal"></span></li>\n' +
                '            <li>\n' +
                '                <a href="e-commerce_product.html">继续购物</a>\n' +
                '                <a href="#" onclick="playsum()">去结算</a>\n' +
                '            </li>\n' +
                '        </ul>';


            optionString1+='<table>\n' +
                '            <tr >\n' +
                '                <td align="center" style="width: 1000px">\n' +
                '                    <ul id="pagination" class="pagination" >\n' +
                '\n' +
                '                    </ul>\n' +
                '                </td>\n' +
                '            </tr>\n' ;
            
            var optionString2 = "";
            for (var i=1;i<data.pages+1;i++){
                if (i==data.pageNum){
                    optionString2+='<li value="'+i+'"><a class="active" href="#">'+i+'</a></li>';
                    continue;
                }
                if(i<data.pageNum+2&&i>data.pageNum-2){
                    optionString2+='<li value="'+i+'"><a onclick="cartonclick('+i+')">'+i+'</a></li>';
                }
            }

            $(carlist).empty();
            // 动态添加代码
            $(carlist).append(optionString1);

            $(pagination).empty();
            // 动态添加代码
            $(pagination).append(optionString2);

            setprovideImg('carlist');

        },error:function (data) {
            
        }
    })
};

var ids=[];


function playsum() {
    swal({
        title: "您确定要支付吗？",
        text: "您确定要支付？",
        type: "warning",
        showCancelButton: true,
        closeOnConfirm: false,
        confirmButtonText: "是的，我要支付",
        confirmButtonColor: "#ec6c62"
    },function(){
        $.ajax({
            url:"/member/play",
            type : "post",
            dataType : "JSON",
            data:{"idlist":JSON.stringify(ids)}
        }).done(function(data) {
            if (data.flag=="支付成功"){
                swal({
                    title: "支付成功",
                    text: "支付服务成功",
                    icon: "success",
                    dangerMode: true,
                },function () {
                    location.reload();
                    // var page=getCookie("page");
                    // aonclick(page);
                });
            }
            else {
                swal({
                    title: "支付失败",
                    text: "支付失败",
                    icon: "error",
                    dangerMode: true,
                })
            }
        });
    });

}
//保存总价
function buyprice(id) {
    if(document.getElementById(id+"chbox").checked){
        var totalprice = Number(sessionStorage.getItem("buyprice"));

        var total = document.getElementById(id+"total").value;

        var newsumprice = Number(totalprice)+Number(total);
        sessionStorage.setItem("buyprice",String(newsumprice));
        $("#buytotal").empty();
        $("#buytotal").append(sessionStorage.getItem("buyprice"));
        //放入付款时的cartid值
        ids.push(id);

    }else {

        var totalprice = Number(sessionStorage.getItem("buyprice"));

        var total = document.getElementById(id+"total").value;

        var newsumprice = Number(totalprice)-Number(total);
        sessionStorage.setItem("buyprice",String(newsumprice));
        $("#buytotal").empty();
        $("#buytotal").append(sessionStorage.getItem("buyprice"));
        //移除cartid值
        ids.pop(id);
    }

}

function setprovideImg(id) {
    //获取该id下所有IMG标签
    var imglist = document.getElementById(id).getElementsByTagName("img");
    // /member/play
    for (var i=0;i<imglist.length;i++){
        setImgSrc(imglist[i].id)

    }

}
function setImgSrc(id) {
    var setimg = document.getElementById(id);
    $.ajax({
        url: "/member/getshopimg",
        data:{"Id":id},
        type: "get",
        dataType : "JSON",
        success:function (data) {
            setimg.setAttribute("src",data.imgpath);
        }
    })
}

function addcart(id) {
    var carnum=document.getElementById(id);
    var num = Number(carnum.value);
    $.ajax({
        url:"/member/pluscart",
        type:"get",
        dataType:"json",
        data:{"cartId":id.toString()},
        success:function (data) {
            if (data.flag=="增加成功"){
                carnum.value=num+1;
                var totalid="#"+id+"total";
                $(totalid).empty();
                $(totalid).append(data.totalprice);
            }else {
                swal(
                    '失败！',
                    '增加失败！',
                    'error'
                )
            }
        }
    })

}
function lesscart(id) {
    var carnum = document.getElementById(id);
    var num = Number(carnum.value);
    $.ajax({
        url: "/member/lesscart",
        type: "get",
        dataType: "json",
        data:{"cartId":id.toString()},
        success: function (data) {
            if (data.flag == "减少购物数量成功") {
                if (num>0){
                    carnum.value = num - 1;
                }

                var totalid="#"+id+"total";
                $(totalid).empty();
                $(totalid).append(data.totalprice);
            } else {
                swal(
                    '失败！',
                    '减少失败！',
                    'error'
                )
            }
        }
    })
}
function setnum(id) {
    var carnum = document.getElementById(id);
    var num = Number(carnum.value);
    $.ajax({
        url: "/member/setbuynum",
        type: "get",
        dataType: "json",
        data:{"cartId":id,
            "buynum":carnum.value},
        success: function (data) {
            if (data.flag == "修改成功") {
                if (num>0){
                    carnum.value = num;
                }
                var totalid="#"+id+"total";
                $(totalid).empty();
                $(totalid).append(data.totalprice);
            } else {
                swal(
                    '失败！',
                    '减少失败！',
                    'error'
                )
            }
        }
    })
}
function delcart(id) {
    swal({
        title: "您确定要删除吗？",
        text: "您确定要删除？",
        type: "warning",
        showCancelButton: true,
        closeOnConfirm: false,
        confirmButtonText: "是的，我要删除",
        confirmButtonColor: "#ec6c62"
    },function(){
        $.ajax({
            url:"/member/delcart",
            type : "post",
            dataType : "JSON",
            data:{"cartId":id.toString()}
        }).done(function(data) {
            if (data.flag=="删除成功"){
                swal({
                    title: "操作成功",
                    text: "删除服务成功",
                    icon: "success",
                    dangerMode: true,
                },function () {
                    location.reload();
                    // var page=getCookie("page");
                    // aonclick(page);
                });
            }
            else {
                swal({
                    title: "操作失败",
                    text: "删除失败",
                    icon: "error",
                    dangerMode: true,
                })
            }
        }).error(function (data) {
            swal("OMG", "删除失败!", "error");
        });
    });
}
function judgelistonclick(page) {
    $.ajax({
        url:"/order/commemtnolist",
        type:"post",
        dataType:"json",
        data:{"page":page},
        success:function (data) {

            var strhtml='';
            for (var i=0;i<data.size;i++){
                //转换时间
                var date=new Date(data.list[i].createTime);
                Y = date.getFullYear() + '-';
                M = (date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) + '-';
                D = date.getDate() + ' ';
                h = date.getHours() + ':';
                m = date.getMinutes() + ':';
                s = date.getSeconds();

                addis(data.list[i].id);

                strhtml+='<div class="article">\n' +
                    '                    <img  id="'+"img"+data.list[i].id+'" alt="图片" />\n' +
                    '                    <ul class="article-info">\n' +
                    '                        <li>'+data.list[i].serviceName+'</li>\n' +
                    '                        <li>'+data.list[i].serviceInfo+'</li>\n' +
                    '                        <li>'+data.list[i].unit+'</li>\n' +
                    '                    </ul>\n' +
                    '                    <p>购买时间：'+Y+M+D+'</p>\n' +
                    '                    <p onclick="judge(\''+data.list[i].id+'\')" class="evaluate_btn">去评价</p>\n' +
                    '                </div>';
            }
            $("#judgelist").empty();

            $("#judgelist").append(strhtml);
            setAllImg();
            var optionString2 = "";
            for (var i=1;i<data.pages+1;i++){
                if (i==data.pageNum){
                    optionString2+='<li value="'+i+'"><a class="active" href="#">'+i+'</a></li>';
                    continue;
                }
                if(i<data.pageNum+2&&i>data.pageNum-2){
                    optionString2+='<li value="'+i+'"><a onclick="judgelistonclick('+i+')">'+i+'</a></li>';
                }
            }
            $("#orderpage").empty();
            $("#orderpage").append(optionString2);
        }
    })
}

function judge(orderId) {


    //页面层-自定义
    layer.open({
        type: 1,
        skin: 'layui-layer-rim', //加上边框
        area: ['400px', '250px'], //宽高
        title: '评论',
        closeBtn:1,
        //输入自定义html
        content: '<!DOCTYPE html>\n' +
            '<html lang="en">\n' +
            '<head>\n' +
            '    <meta charset="UTF-8">\n' +
            '    <title>Title</title>\n' +
            '    <link rel="stylesheet" href="./css/basic-grey.css">\n' +
            '</head>\n' +
            '<body>\n' +
            '<div class="basic-grey">\n' +
            '    <form >\n' +
            '        <textarea id="message" name="serviceContent" placeholder="请输入评论"></textarea>' +
            '<input type="button" onclick="test(\''+orderId+'\')" value="提交">\n' +
            '    </form>\n' +
            '</div>\n' +
            '</body>\n' +
            '</html>'
    });

}

function test(orderId) {
    var message = document.getElementById("message").value;

    $.ajax({
        url: "/member/judge",        //后台url
        data: {"orderId":orderId.toString(),
               "context":message},
        type: "POST", //类型，POST或者GET
        dataType: 'json', //数据返回类型，可以是xml、json等
        success:function () {
            layer.alert('评论成功', {icon: 6});
        }
    });
}

function setno() {
    judgelistonclick("1");
}

function steaction() {
    actionpage("1");
}

function actionpage(page) {
    $.ajax({
        url:"/order/commemtlist",
        type:"post",
        dataType:"json",
        data:{"page":page},
        success:function (data) {

            var strhtml='';
            for (var i=0;i<data.size;i++){

                addis(data.list[i].id);
                //转换时间
                var date=new Date(data.list[i].createTime);
                Y = date.getFullYear() + '-';
                M = (date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) + '-';
                D = date.getDate() + ' ';
                h = date.getHours() + ':';
                m = date.getMinutes() + ':';
                s = date.getSeconds();


                strhtml+='<div class="article">\n' +
                    '                    <img  id="'+"img"+data.list[i].id+'" alt="图片" />\n' +
                    '                    <ul class="article-info">\n' +
                    '                        <li>'+data.list[i].serviceName+'</li>\n' +
                    '                        <li>'+data.list[i].serviceInfo+'</li>\n' +
                    '                        <li>'+data.list[i].unit+'</li>\n' +
                    '                    </ul>\n' +
                    '                    <p>购买时间：'+Y+M+D+'</p>\n' +
                    '                </div>';
            }
            $("#judgelist").empty();

            $("#judgelist").append(strhtml);

            var optionString2 = "";
            for (var i=1;i<data.pages+1;i++){
                if (i==data.pageNum){
                    optionString2+='<li value="'+i+'"><a class="active" href="#">'+i+'</a></li>';
                    continue;
                }
                if(i<data.pageNum+2&&i>data.pageNum-2){
                    optionString2+='<li value="'+i+'"><a onclick="actionpage('+i+')">'+i+'</a></li>';
                }
            }
            setAllImg();
            $("#orderpage").empty();
            $("#orderpage").append(optionString2);
        }
    })
}
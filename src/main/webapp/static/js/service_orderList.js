function orderpage(page) {

    $.ajax({
        url:"/api/orderlist",
        type:"post",
        dataType:"json",
        data:{"page":page,
              "word":sessionStorage.getItem("word"),
              "status":sessionStorage.getItem("status")},
        success:function (data) {
            sessionStorage.setItem("page",page);

            var strhtml='';
            if (data.size==0){
                strhtml+='<tr><td></td><td></td><td></td>' +
                    '<td></td><td></td><td></td><td></td>' +
                    '<td></td><td></td></tr>';
            }


            for (var i=0;i< data.size;i++){
                //转换时间
                var date=new Date(data.list[i].createTime);
                Y = date.getFullYear() + '-';
                M = (date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) + '-';
                D = date.getDate() + ' ';
                h = date.getHours() + ':';
                m = date.getMinutes() + ':';
                s = date.getSeconds();

                addids(data.list[i].id);

                strhtml+='                    <tr>\n' +
                    '                        <td>'+data.list[i].id+'</td>\n' +
                    '                        <td>'+data.list[i].serviceName+'</td>\n' +
                    '                        <td id=\"membername'+data.list[i].id+'\">张三</td>\n' +
                    '                        <td id=\"cellphone'+data.list[i].id+'\">13312345678</td>\n' +
                    '                        <td>'+data.list[i].buyNum+'</td>\n' +
                    '                        <td>¥'+data.list[i].totalPrice+'</td>\n' +
                    '                        <td>'+Y+M+D+'</td>\n' +
                    '                        <td>\n' ;
                if (sessionStorage.getItem("status")=="3"){
                    strhtml+='<span class="handle-btn"><i class="fa fa-opera fa-fw"></i>完成</span>\n';
                }else {
                    strhtml+='<span onclick="opera(\''+data.list[i].id+'\')" class="handle-btn"><i class="fa fa-object-group fa-fw"></i>请操作</span>\n'
                }
                strhtml+= '</td></tr>';
            }


            setnamecell();
            var optionString2 = "";
            for (var i=1;i<data.pages+1;i++){
                if (i==data.pageNum){
                    optionString2+='<li value="'+i+'"><a class="active" href="#">'+i+'</a></li>';
                    continue;
                }
                if(i<data.pageNum+2&&i>data.pageNum-2){
                    optionString2+='<li value="'+i+'"><a onclick="orderpage('+i+')">'+i+'</a></li>';
                }
            }


            $("#orderlist").empty();

            $("#orderlist").append(strhtml);

            $("#orderpage").empty();
            $("#orderpage").append(optionString2);



        }
    })
}

function setwordandselect() {
    var word = document.getElementById("serviceword").value;

    sessionStorage.setItem("word",word);
    orderpage("1");
}
//保存状态

//服务中
function setstatusno() {
    sessionStorage.setItem("status","2");

    var redstartHtml = document.getElementById("serviceno");
    redstartHtml.setAttribute("class","border-red order1");

    var redendHtml = document.getElementById("endservice");
    redendHtml.setAttribute("class","order2");

    var noagreeHtml = document.getElementById("noagree");
    noagreeHtml.setAttribute("class","order");

    orderpage("1")
}

function setstatusaction() {
    sessionStorage.setItem("status","3");

    var redstartHtml = document.getElementById("serviceno");
    redstartHtml.setAttribute("class","order2");

    var redendHtml = document.getElementById("endservice");
    redendHtml.setAttribute("class","border-red order1");

    var noagreeHtml = document.getElementById("noagree");
    noagreeHtml.setAttribute("class","order");

    orderpage("1");
}

function setnoagree() {
    sessionStorage.setItem("status","4");

    var redstartHtml = document.getElementById("serviceno");
    redstartHtml.setAttribute("class","order2");

    var redendHtml = document.getElementById("endservice");
    redendHtml.setAttribute("class","order2");

    var noagreeHtml = document.getElementById("noagree");
    noagreeHtml.setAttribute("class","border-red order1");

    orderpage("1");
}

function opera(id) {
    layer.confirm('提供服务？', {
        btn: ['同意','不同意'] //按钮
    }, function(index){
        layer.close(index);
        $.ajax({
            url: "/api/agree",
            dataType: "json",
            type: "get",
            data:{"orderId":id.toString()},
            success:function (data) {
                if (data.flag=="失败"){
                    layer.alert('操作失败', {icon: 6});
                }
                location.reload();
            }
        });
    }, function(index){
        layer.close(index);
        $.ajax({
            url: "/api/noagree",
            dataType: "json",
            type: "get",
            data:{"orderId":id.toString()},
            success:function (data) {
                if (data.flag=="失败"){
                    layer.alert('操作失败', {icon: 6});
                }
                location.reload();
            }
        })

    });
}

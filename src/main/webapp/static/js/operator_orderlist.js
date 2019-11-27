function aonclick(page) {

    var word = sessionStorage.getItem("word");

    sessionStorage.setItem("page",page);

    var memberId= getCookie("memberId");

    if (memberId==null||memberId=="null"){
        memberId="";
    }

    if (word==null||word==""){
        word=null;
    }

    $.ajax({
        url : "/admin/orderlist",
        type : "get",
        dataType : "JSON",
        data:{"page":page,
            "word":word,
            "style":sessionStorage.getItem("style"),
            "memberId":memberId
        },
        success : function(data) {

            var optionString1 = "";
            if (data.size==0){
                optionString1+='<td></td><td></td><td></td><td></td><td></td>';
            }
            for(var i=0;i<data.size;i++){

                addids(data.list[i].id);

                var date=new Date(data.list[i].createTime);
                Y = date.getFullYear() + '-';
                M = (date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) + '-';
                D = date.getDate() + ' ';

                optionString1+='<tr>\n' +
                    '                        <td>'+data.list[i].id+'</td>\n' +
                    '                        <td id="'+"membername"+data.list[i].id+'"></td>\n' +
                    '                        <td>'+data.list[i].serviceName+'</td>\n' +
                    '                        <td>¥'+data.list[i].totalPrice+'</td>\n' +
                    '                        <td>'+Y+M+D+'</td>\n';
                if (data.list[i].status==2){
                    optionString1+='<td>已支付</td>\n'
                }
                if (data.list[i].status==3){
                    optionString1+='<td>服务中</td>\n'
                }
                if (data.list[i].status==4){
                    optionString1+='<td>已取消</td>\n'
                }
                // 只在menberid为空时添加
                if (memberId==""){
                    optionString1+=
                        '<td>\n' +
                        '<span onclick="details(\''+data.list[i].id+'\')" class="handle-btn"><i class="fa fa-edit fa-fw"></i>详情</span>\n' +
                        '</td>\n' +
                        '</tr>';
                }

            }
            setnamecell();
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

function setStyle() {
    var sortStyle = document.getElementById('sortStyle').value;
    if (sortStyle!='--选择服务类型'){
        sessionStorage.setItem("style",sortStyle);
        aonclick('1')
    }else {
        sessionStorage.setItem("style","");
        aonclick('1')
    }
}

function setword() {
    var word = document.getElementById('word').value;

    sessionStorage.setItem("word",word);

    aonclick('1')
}

function details(id) {
    document.cookie="orderId="+id;
    layer.open({
        type: 2,
        area: ['700px', '450px'],
        fixed: false, //不固定
        maxmin: true,
        content: '/operator_orderdetails.html'
    });
}
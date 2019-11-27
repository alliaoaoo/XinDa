function aonclick(page) {

    var word = sessionStorage.getItem("word");

    sessionStorage.setItem("page",page);

    if (word==null||word==""){
        word=null;
    }
    $.ajax({
        url : "/admin/playlist",
        type : "get",
        dataType : "JSON",
        data:{"page":page,
            "status":sessionStorage.getItem("status")},
        success : function(data) {
            settotal();

            var optionString1 = "";
            if (data.size==0){
                optionString1+='<td></td><td></td><td></td><td></td><td></td><td></td>';
            }

            for(var i=0;i<data.size;i++){

                addids(data.list[i].id);

                var date=new Date(data.list[i].createTime);
                Y = date.getFullYear() + '-';
                M = (date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) + '-';
                D = date.getDate() + ' ';

                optionString1+='<tr>\n' +
                    '<td id="'+"membername"+data.list[i].id+'">张三</td>\n' +
                    '<td>'+Y+M+D+'</td>\n' +
                    '<td>'+data.list[i].id+'</td>\n' +
                    '<td>¥'+data.list[i].totalPrice+'</td>\n' +
                    '<td>银联支付</td>\n' +
                    '<td>'+data.list[i].serviceName+'</td>\n' +
                    '</tr>';

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

function settime(status) {
    sessionStorage.setItem("status",status);
    aonclick('1');
}

function settotal() {
    $.ajax({
        url: "/admin/playtotal",
        data:{"status":sessionStorage.getItem("status")},
        dataType: "json",
        type: "get",
        success:function (data) {
            $("#playtotal").empty();
            $("#playtotal").append("￥"+data.total);
        }
    });

}
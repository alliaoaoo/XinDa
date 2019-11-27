function aonclick(page) {

    var word = sessionStorage.getItem("word");

    sessionStorage.setItem("page",page);

    if (word==null||word==""){
        word=null;
    }
    $.ajax({
        url : "/admin/memberlist",
        type : "get",
        dataType : "JSON",
        data:{"page":page,
              "word":word,
              "status":sessionStorage.getItem("status")},
        success : function(data) {

            sessionStorage.setItem("page",page);
            var optionString1 = "";
            if (data.size==0){
                optionString1+='<td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td>';
            }
            for(var i=0;i<data.size;i++){

                addids(data.list[i].id);

                var date=new Date(data.list[i].registerTime);
                Y = date.getFullYear() + '-';
                M = (date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) + '-';
                D = date.getDate() + ' ';
                optionString1+='<tr>\n' +
                    '<td>'+data.list[i].code+'</td>\n' +
                    '<td>'+data.list[i].name+'</td>\n' +
                    '<td>'+data.list[i].cellphone+'</td>\n' +
                    '<td>'+data.list[i].region+'</td>\n' +
                    '<td>'+Y+M+D+'</td>\n' +
                    '<td id=\"'+"sum"+data.list[i].id+'\">2</td>\n' +
                    '<td id=\"'+"total"+data.list[i].id+'\">¥10300.00</td>\n' +
                    '<td>\n' +
                    '<span onclick="userorder(\''+data.list[i].id+'\')" class="handle-btn">查看</span>\n' +
                    '</td>\n' +
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
function setword() {
    var word = document.getElementById("word").value;
    sessionStorage.setItem("word",word);
    aonclick('1');
}

function userorder(memberid) {
    document.cookie="memberId="+memberid;
    layer.open({
        type: 2,
        area: ['847px', '450px'],
        fixed: false, //不固定
        maxmin: true,
        content: '/operator_userorder.html'
    });
}
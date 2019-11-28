function e_popular() {
    $.ajax({
        url:"/api/popular",
        type:"get",
        dataType:"json",
        success:function (data) {
            var popular=[];
            var htmltext = '热门服务：';
            $("#popular").empty();
            for (var key in data){

                popular.push(key);
            }
            if (popular.length!=0){
                var newarr = shuffle(popular);
                // 取推荐列表中首两项
                for (var index=0;index<2;index++){
                    if (newarr.length>1){
                        var jsonkey = popular[index];
                        htmltext+='<span id="'+data[jsonkey]+'">'+jsonkey+'</span>&nbsp;&nbsp;';
                    }else {
                        //只有一个时只循环一次
                        var jsonkey = popular[0];
                        htmltext+='<span id="'+data[jsonkey]+'">'+jsonkey+'</span>&nbsp;&nbsp;';
                        break;
                    }
                }
                $("#popular").append(htmltext);
            }

        }
    })
}

function shuffle(array) {
    var m = array.length,
        t, i;
    // 如果还剩有元素…
    while (m) {
        // 随机选取一个元素…
        i = Math.floor(Math.random() * m--);
        // 与当前元素进行交换
        t = array[m];
        array[m] = array[i];
        array[i] = t;
    }
    return array;
}

　　
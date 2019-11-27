var sortInit = function(sortStyle,sortType){
    /* ajax请求 */
    $.ajax({
        url : "/product/getSort",
        type : "post",
        dataType : "JSON",
        success : function(data) {
            var optionString1 = "<option>--选择服务类型</option>";
            for(var i=0;i<data.Style.length;i++){
                optionString1+="<option value=\""+data.Style[i].styleId+"\"";
                optionString1+=">"+data.Style[i].name+"</option>";
            }
            var optionString2 = "<option>--选择服务分类</option>";
            for (var i=0;i<data.type.length;i++)
            {
                optionString2+="<option value=\""+data.type[i].typeId+"\"";
                optionString2+=">"+data.type[i].name+"</option>";
            }

            $(sortStyle).empty();
            $(sortType).empty();
            // 动态添加代码
            $(sortStyle).append(optionString1);
            $(sortType).append(optionString2);
        }
    });

};

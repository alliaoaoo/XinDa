function checkPhone(id){
    var phone = document.getElementById(id).value;
    if(!(/^1[3456789]\d{9}$/.test(phone))){
        layer.msg('输入号码有误', {icon: 5});
        return false;
    }else {
        return true;
    }
}

function validateNonEmpty(id,text)
{
    var value = document.getElementById(id).value;
    if (value == "") {
        layer.msg(text, {icon: 5});
        return false;
    }else {
        return true;
    }
}

function checkRate(id) {
    var value = document.getElementById(id).value;
    var ele = document.getElementById(id);
    ele.style.color = "red";
    if (value == "") {
        layer.msg('请输入内容', {icon: 5});
        return false;
    } else {
        var r = /^\+?[1-9][0-9]*$/;
        if (!r.test(value)) {
            layer.msg('输入的不是数字', {icon: 5});
            return false;
        }
        return true;
    }
}

function chechqq(id) {
    var value = document.getElementById(id).value;
    var reg = /^[1-9][0-9]{4,9}$/gim;
    if (reg.test(value)) {
        // layer.msg('请输入qq号', {icon: 5});
        return true;
    } else {
        layer.msg('输入qq号不对', {icon: 5});
        return false;
    }
}

function checkeamil(id) {
    var value = document.getElementById(id).value;
    var reg = /^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/;
    // var reg = /\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*/;
    if (reg.test(value)) {
        return true;
    } else {
        layer.msg('输入邮箱不对', {icon: 5});
        return false;
    }
}
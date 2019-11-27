package com.xinda.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class GetCookie {
    public static Object getCookie(HttpServletRequest httpServletRequest,String cookieKey){
        //获取全部的cookie
        Cookie[]allCookies = httpServletRequest.getCookies();
        int i=0;
        //如果allCookies不为空...
        if(allCookies!=null){

            //从中取出cookie
            for(i=0;i<allCookies.length;i++){

                //依次取出
                Cookie temp=allCookies[i];
                if(temp.getName().equals(cookieKey)){
                    //得到cookie的值
                    Object val=temp.getValue();
                    return val;
                }
            }
            if(allCookies.length==i){

                System.out.println("没有cookie");
            }

        }
        //没有取到cookie则返回null
        return null;
    }
}

package com.xinda.interceptor;

import com.xinda.Config.PermissUrlConfig;
import com.xinda.service.MemberService;
import com.xinda.service.ProviderService;
import com.xinda.service.UserService;
import com.xinda.utils.GetCookie;
import com.xinda.utils.JwtOperat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
/**
 * @author: aoliao
 * @updateTime: 2019/10/28 11:57
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    ProviderService providerService;
    @Autowired
    MemberService memberService;
    @Autowired
    UserService userService;

    private JwtOperat jwtOperat = new JwtOperat();

    private List<String> exceptUrls;

    public List<String> getExceptUrls() {
        return exceptUrls;
    }

    public void setExceptUrls(List<String> exceptUrls) {
        this.exceptUrls = exceptUrls;
    }


    /**
     * 拦截器，用来拦截请求和判断是否登录及登录权限控制
     * @author: aoliao
     * @param: httpServletRequest
     * @param: httpServletResponse
     * @param: o
     * @updateTime: 2019/10/28 11:57
     * @return: boolean
     */
    @Override
    public boolean preHandle(
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse,
            Object o) throws Exception {
        //获取请求的url
        String url = httpServletRequest.getRequestURI();
        String token=null;

        if (GetCookie.getCookie(httpServletRequest,"token") != null){
            //拦截器负责把头请求头中cookie包含token的令牌取出。
            token = (String) GetCookie.getCookie(httpServletRequest,"token");
        }

        if (url.equals("/")){
            httpServletResponse.sendRedirect(url + "index.html");
            //return false 表示拦截，不向下执行
            //return true 表示放行
            return false;
        }

        for (String requestUri:exceptUrls) {
            if (url.startsWith(requestUri)) {
                return true;
            }
        }
        //如果包含token信息，将进行解析
        if (token!=null){
            //token过期不放行且重定向
            if (jwtOperat.tokenExpired(token)){
                httpServletResponse.sendRedirect("/");
                return false;
            }
            try {

                Map<String,String> map = jwtOperat.getSubject(token);

                if(map.get("role")!=null){

                    String role= map.get("role");

                    switch (role) {
                        case "provider":
                            //判断该id是否存在
                            if (providerService.existProvider(map.get("userid"))) {

                                //放行所有服务商的页面
                                if (url.startsWith("/service")){
                                    return true;
                                }
                                //判断url是否符合规定
                                return PermissUrlConfig.Provider.contains(url);
                            } else {
                                httpServletResponse.sendRedirect("/service_login.html");
                                return false;
                            }
                        case "member":
                            //判断是否存在该id
                            if (memberService.existProvider(map.get("userid"))) {
                                //放行所有电子商城页面
                                if (url.startsWith("/e-commerce")){
                                    return true;
                                }
                                //判断url是否符合规定
                                return PermissUrlConfig.Member.contains(url);
                            } else {
                                httpServletResponse.sendRedirect("e-commerce_login.html");
                                return false;
                            }
                        case "admin":
                            //判断是否存在该id
                            if (userService.existUser(map.get("userid"))) {

                                //放行所有服务商的页面
                                if (url.startsWith("/operator")){
                                    return true;
                                }
                                //判断url是否符合规定
                                return PermissUrlConfig.Admin.contains(url);
                            } else {
                                httpServletResponse.sendRedirect("/operator_login.html");
                                return false;
                            }
                        default:
                            break;
                    }
                }else {
                    return false;
                }

            }catch (Exception e){
                httpServletResponse.sendRedirect("/");
                return false;
            }
            httpServletResponse.sendRedirect("/");
            return false;
        }
        httpServletResponse.sendRedirect("/");

        return false;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}

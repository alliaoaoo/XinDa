package com.xinda.controller;

import com.github.pagehelper.PageInfo;
import com.xinda.model.Member;
import com.xinda.model.Provider;
import com.xinda.model.ServiceOrder;
import com.xinda.model.ServiceOrderProgress;
import com.xinda.service.*;
import com.xinda.utils.Captcha;
import com.xinda.utils.GetCookie;
import com.xinda.utils.JwtOperat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/api")
public class APIController {
    @Autowired
    ProviderService providerService;
    @Autowired
    CartService cartService;
    @Autowired
    OrderService orderService;
    @Autowired
    MemberService memberService;
    @Autowired
    ProductService productService;

    /**
     * 用于生成带四位数字验证码的图片
     */
    @RequestMapping(value = "/captcha")
    @ResponseBody
    /**
     * 验证码
     * @author: aoliao
     * @param: request
     * @param: response
     * @updateTime: 2019/11/14 22:18
     * @return: java.lang.String
     */
    public String imagecode(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setDateHeader("Expires", 0);
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");
        response.setContentType("image/jpeg");

        OutputStream os = response.getOutputStream();
        //返回验证码和图片的map
        Map<String,Object> map = Captcha.getImageCode(86, 37, os);
        String simpleCaptcha = "simpleCaptcha";
        request.getSession().setAttribute(simpleCaptcha, map.get("strEnsure").toString().toLowerCase());
        request.getSession().setAttribute("codeTime",System.currentTimeMillis());
        try {
            ImageIO.write((BufferedImage) map.get("image"), "jpg", os);
        } catch (IOException e) {
            return "";
        }   finally {
            if (os != null) {
                os.flush();
                os.close();
            }
        }
        return null;
    }
    @RequestMapping("/shoplist")
    @ResponseBody
    /**
     * 店铺列表
     * @author: aoliao
     * @param: page
     * @param: word
     * @updateTime: 2019/11/14 22:18
     * @return: com.github.pagehelper.PageInfo
     */
    public PageInfo getProviderList(@RequestParam(value = "page",defaultValue = "1",required = false) int page,
                                    @RequestParam(value = "word",required = false)String word){
        //暂时设置5
        int pageSize=5;
        return  providerService.providerShop(page,pageSize,word);
    }
    @RequestMapping("/cartsum")
    @ResponseBody
    /**
     * 购物车数量
     * @author: aoliao
     * @param: request
     * @updateTime: 2019/11/14 22:18
     * @return: java.util.Map<java.lang.String,java.lang.String>
     */
    public Map<String,String> getCartSum(HttpServletRequest request) throws IOException {

        String token = (String) GetCookie.getCookie(request,"token");

        Map<String,String> map = new HashMap<>();

        JwtOperat jwtOperat = new JwtOperat();
        Map<String,String> subjectMap = jwtOperat.getSubject(token);
        //取出会员id
        String memberId = subjectMap.get("userid");
        int sum = cartService.getCartSum(memberId);

        map.put("sum", Integer.toString(sum));
        return map;
    }

    @RequestMapping("/orderlist")
    @ResponseBody
    /**
     * 获取订单列表
     * @author: aoliao
     * @param: page
     * @param: word
     * @param: status
     * @param: request
     * @updateTime: 2019/11/14 10:05
     * @return: com.github.pagehelper.PageInfo
     */
    public PageInfo getBusinessOrder(@RequestParam(value = "page",defaultValue = "1",required = false) int page,
                                     @RequestParam(value = "word",required = false)String word,
                                     @RequestParam(value = "status",required = false)int status,
                                     HttpServletRequest request) throws IOException {

        String token = (String) GetCookie.getCookie(request,"token");

        JwtOperat jwtOperat = new JwtOperat();
        Map<String,String> subjectMap = jwtOperat.getSubject(token);
        //取出id
        String providerId = subjectMap.get("userid");
        //暂时设置5
        int pageSize=5;
        return orderService.getOrderList(page,pageSize,word,providerId,status);
    }
    @RequestMapping("/getexpenses")
    @ResponseBody
    /**
     * 获取付费中心参数
     * @author: aoliao
     * @param: request
     * @updateTime: 2019/11/14 22:16
     * @return: java.util.Map<java.lang.String,java.lang.Float>
     */
    public Map<String,Float> getExpenses(HttpServletRequest request) throws IOException {
        String token = (String) GetCookie.getCookie(request,"token");

        JwtOperat jwtOperat = new JwtOperat();
        Map<String,String> subjectMap = jwtOperat.getSubject(token);
        //取出id
        String providerId = subjectMap.get("userid");

        return orderService.getProviderExpenses(providerId);
    }

    @RequestMapping("/agree")
    @ResponseBody
    /**
     * 同意订单
     * @author: aoliao
     * @param: orderId
     * @updateTime: 2019/11/8 21:18
     * @return: java.util.Map<java.lang.String,java.lang.String>
     */
    public Map<String,String> agreeServiceOrder(@RequestParam(value = "orderId",required = true) String orderId){
        Map<String,String> map = new HashMap<>();

        ServiceOrderProgress progress = orderService.getServiceProgress(orderId);

        Date date =new Date();
        //处理时间
        progress.setHandleTime(date);
        //完成时间
        progress.setCompleteTime(date);
        progress.setDeclineReason("");

        map.put("flag","失败");
        //更新订单状态
        if (orderService.upServiceOrderStatus(orderId,3)){

            if (orderService.setServiceOrderProgress(progress,orderId)){
                //如果更新成功
                map.put("flag","成功");
            }
            return map;
        }
        return map;
    }

    @RequestMapping("/noagree")
    @ResponseBody
    /**
     * 不同意订单(拒绝理由待做)
     * @author: aoliao
     * @param: orderId
     * @updateTime: 2019/11/8 21:19
     * @return: java.util.Map<java.lang.String,java.lang.String>
     */
    public Map<String,String> noAgreeServiceOrder(@RequestParam(value = "orderId",required = true) String orderId){

        Map<String,String> map = new HashMap<>();
        map.put("flag","失败");

        ServiceOrderProgress progress = orderService.getServiceProgress(orderId);

        Date date =new Date();
        //处理时间
        progress.setHandleTime(date);
        //完成时间
        progress.setCompleteTime(date);
        //暂时设置理由为此
        progress.setDeclineReason("拒绝提供服务");

        //更新订单状态
        if (orderService.upServiceOrderStatus(orderId,4)){

            if (orderService.setServiceOrderProgress(progress,orderId)){
                //如果更新成功
                map.put("flag","成功");
            }
            return map;
        }
        return map;
    }
    @RequestMapping("/memberattribute")
    @ResponseBody
    /**
     * @author: aoliao
     * @param: orderId
     * @updateTime: 2019/11/16 9:58
     * @return: java.util.Map<java.lang.String,java.lang.String>
     */
    public Map<String,String> getMemberName(@RequestParam(value = "orderId",required = true) String orderId){
        Map<String,String> map = new HashMap<>();
        ServiceOrder order = orderService.getServiceOrder(orderId);
        Member member = memberService.getMember(order.getMemberId());
        map.put("name",member.getName());
        map.put("cellphone",member.getCellphone());
        return map;
    }
    @RequestMapping("/orderattr")
    @ResponseBody
    /**
     * 获取订单其他参数
     * @author: aoliao
     * @param: memberId
     * @updateTime: 2019/11/16 9:58
     * @return: java.util.Map
     */
    public Map getOrderAttr(@RequestParam(value = "memberId",required = true) String memberId){
        return memberService.getMemberTotal(memberId);
    }
    @RequestMapping("/getprovider")
    @ResponseBody
    /**
     * 获取服务商信息
     * @author: aoliao
     * @param: providerId
     * @updateTime: 2019/11/13 14:04
     * @return: com.xinda.model.Provider
     */
    public Provider getProvider(@RequestParam(value = "providerId",required = true) String providerId){
        return providerService.getProvider(providerId);
    }
    @RequestMapping("/orderserviceimg")
    @ResponseBody
    /**
     * 该订单的服务产品图片
     * @author: aoliao 
     * @param: orderId
     * @updateTime: 2019/11/14 21:27  
     * @return: java.util.Map<java.lang.String,java.lang.String>
     */
    public Map<String,String> getOrderServiceImg(@RequestParam(value = "orderId",required = true) String orderId){
        return orderService.getOrderServiceImg(orderId);
    }
    @RequestMapping("/popular")
    @ResponseBody
    /**
     * 推荐服务
     * @author: aoliao
     * @updateTime: 2019/11/14 21:42
     * @return: java.util.Map
     */
    public Map<String, String> getPopular(){
        return productService.getPopularProduct();
    }

}
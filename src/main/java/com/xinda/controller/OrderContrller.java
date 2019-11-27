package com.xinda.controller;

import com.github.pagehelper.PageInfo;
import com.xinda.model.ServiceJudge;
import com.xinda.service.OrderService;
import com.xinda.utils.GetCookie;
import com.xinda.utils.JwtOperat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;


/**
 * @author: aoliao
 * @updateTime: 2019/11/4 8:55
 */
@RequestMapping("/order")
@Controller
public class OrderContrller {
    @Autowired
    OrderService orderService;

    @RequestMapping("/memberlist")
    @ResponseBody
    /**
     * 获取订单列表
     * @author: aoliao
     * @param: page
     * @param: word
     * @param: startDate
     * @param: endDate
     * @param: request
     * @updateTime: 2019/11/6 14:49
     * @return: com.github.pagehelper.PageInfo
     */
    public PageInfo getServiceOrder(@RequestParam(value = "page",defaultValue = "1",required = false) int page,
                                    @RequestParam(value = "word",required = false)String word,
                                    @RequestParam(value = "startDate",required = false) String startDate,
                                    @RequestParam(value = "endDate",required = false) String endDate,
                                    HttpServletRequest request) throws IOException {

        String token = (String) GetCookie.getCookie(request,"token");

        JwtOperat jwtOperat = new JwtOperat();
        Map<String,String> subjectMap = jwtOperat.getSubject(token);
        //取出会员id
        String memberId = subjectMap.get("userid");

        //暂时设置2
        int pageSize=2;

        Date newStartDate=null;
        Date newEndDate=null;

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");



        if (!"".equals(startDate)&&!"".equals(endDate)&&startDate!=null&&endDate!=null){
            //尝试转换时间
            try {
                newStartDate = format.parse(startDate);
                System.out.println(newStartDate);

                newEndDate = format.parse(endDate);
                System.out.println(newEndDate);
                return orderService.getOrderList(page,pageSize,memberId,word,newStartDate,newEndDate);
            } catch (ParseException e) {

                System.out.println("#################################");
                System.out.println("时间转换失败");
                e.printStackTrace();
            }finally {
                return orderService.getOrderList(page,pageSize,memberId,word,newStartDate,newEndDate);
            }
        }

        return orderService.getOrderList(page,pageSize,memberId,word,newStartDate,newEndDate);

    }
    @RequestMapping("/commemtnolist")
    @ResponseBody
    /**
     * 未评价
     * @author: aoliao
     * @param: page
     * @param: request
     * @updateTime: 2019/11/11 12:26
     * @return: com.github.pagehelper.PageInfo
     */
    public PageInfo getNocommemtOrder(@RequestParam(value = "page",defaultValue = "1",required = false) int page,
                                      HttpServletRequest request) throws IOException {
        String token = (String) GetCookie.getCookie(request,"token");

        JwtOperat jwtOperat = new JwtOperat();
        Map<String,String> subjectMap = jwtOperat.getSubject(token);
        //取出会员id
        String memberId = subjectMap.get("userid");

        //暂时设置2
        int pageSize=2;

        PageInfo<ServiceJudge> orderList = orderService.getNocomment(page,pageSize,memberId);

        return orderList;
    }

    @RequestMapping("/commemtlist")
    @ResponseBody
    /**
     * 已经评价
     * @author: aoliao
     * @param: page
     * @param: request
     * @updateTime: 2019/11/11 22:26
     * @return: com.github.pagehelper.PageInfo
     */
    public PageInfo getcommemtOrder(@RequestParam(value = "page",defaultValue = "1",required = false) int page,
                                      HttpServletRequest request) throws IOException {

        String token = (String) GetCookie.getCookie(request,"token");

        JwtOperat jwtOperat = new JwtOperat();
        Map<String,String> subjectMap = jwtOperat.getSubject(token);
        //取出会员id
        String memberId = subjectMap.get("userid");

        //暂时设置2
        int pageSize=2;

        PageInfo<ServiceJudge> orderList = orderService.getcomment(page,pageSize,memberId);

        return orderList;
    }
    @RequestMapping("/getorderotherattr")
    @ResponseBody
    /**
     * 通过订单id获取信息
     * @author: aoliao
     * @param: orderId
     * @updateTime: 2019/11/11 22:32
     * @return: java.util.Map<java.lang.String,java.lang.String>
     */
    public Map<String,String> getOrder(@RequestParam(value = "orderId",required = true) String orderId){
        return orderService.getOrderOtherAttr(orderId);
    }


}

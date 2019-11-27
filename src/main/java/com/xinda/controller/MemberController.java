package com.xinda.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.pagehelper.PageInfo;
import com.xinda.model.*;
import com.xinda.service.*;
import com.xinda.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 会员控制层
 * @author: aoliao
 * @updateTime: 2019/11/1 18:45
 */
@RequestMapping("/member")
@Controller
public class MemberController {
    @Autowired
    MemberService memberService;
    @Autowired
    ProviderService providerService;
    @Autowired
    CartService cartService;
    @Autowired
    ProductService productService;
    @Autowired
    OrderService orderService;
    @Autowired
    MessageService messageService;


    @RequestMapping(value = "/regist",method = RequestMethod.POST)
    @ResponseBody
    /**
     * 注册
     * @author: aoliao
     * @param: request
     * @param: session
     * @param: checkCode
     * @param: cellphone
     * @param: password
     * @param: region
     * @updateTime: 2019/10/26 18:35
     * @return: java.util.Map<java.lang.String,java.lang.String>
     */
    public Map<String,String> registProvide(HttpServletRequest request,
                                            HttpSession session,
                                            @RequestParam(value = "checkCode") String checkCode,
                                            @RequestParam(value = "cellphone")String cellphone,
                                            @RequestParam(value = "password")String password,
                                            @RequestParam(value = "region")String region)throws Exception {
        boolean flag=false;
        HashMap<String,String> map = new HashMap();
        // 获得验证码对象
        map.put("flag","注册失败");

        if (providerService.existCellphone(cellphone)){

            map.put("flag","注册失败,请更换手机号注册");
            return map;
        }

        map= (HashMap<String, String>) Captcha.captchaDetection(map,session,checkCode,request);

        if (!"注册失败".equals(map.get("flag"))){
            return map;
        }
        String memberId = ObjectId.getId();

        Integer code = Integer.parseInt(ObjectId.numberId());
        Member member = new Member();
        member.setId(memberId);
        if (!"null".equals(cellphone)&&!"".equals(cellphone)&&cellphone!=null){
            //检查用户是否存在
            if (memberService.existCellphone(cellphone)){
                map.put("flag","注册失败，已经存在用户");
                return map;
            }
            //插入数据
            member.setPassword(MD5Util.getMD5String(password));
            member.setCellphone(cellphone);
            member.setRegion(region);
            member.setCode(code);
            Date date = new Date();
            member.setRegisterTime(date);
            member.setStatus(1);
            flag=memberService.registMember(member);
        }
        if (flag){
            map.put("flag","注册成功");
        }
        return map;
    }


    @RequestMapping(value = "/login")
    @ResponseBody
    /**
     * 会员登录
     * @author: aoliao
     * @param: request
     * @param: response
     * @param: session
     * @param: checkCode
     * @param: cellphone
     * @param: password
     * @updateTime: 2019/11/1 13:57
     * @return: java.util.Map<java.lang.String,java.lang.String>
     */
    public Map<String,String> loginProvide(HttpServletRequest request,
                                           HttpServletResponse response,
                                           HttpSession session,
                                           @RequestParam(value = "checkCode")String checkCode,
                                           @RequestParam(value = "cellphone")String cellphone,
                                           @RequestParam(value = "password")String password) throws UnsupportedEncodingException {
        HashMap<String,String> map = new HashMap();
        map.put("flag","登录失败");

        map= (HashMap<String, String>) Captcha.captchaDetection(map,session,checkCode,request);

        if (!"登录失败".equals(map.get("flag"))){
            return map;
        }
        Member member=new Member();
        if (!"null".equals(cellphone) && !"null".equals(password)&&cellphone!=null&&password!=null&&!"".equals(cellphone)&&!"".equals(password)){
            if (!memberService.existCellphone(cellphone)){
                map.put("flag","登录失败，不存在用户");
                return map;
            }

            if (!memberService.existMember(cellphone,MD5Util.getMD5String(password))){
                map.put("flag","登录失败密码错误");
                return map;
            }
            //获取用户
            member=memberService.loginMember(cellphone, MD5Util.getMD5String(password));
        }
        if (member.getId()!=null){
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode root1 = mapper.createObjectNode();

            root1.put("userid",member.getId());
            root1.put("username", member.getName());
            root1.put("password", member.getPassword());
            root1.put("role","member");
            //生成subject
            String subject = root1.toString();
            //生成token id值
            String tokenid = ObjectId.getId();
            JwtOperat jwtOperat = new JwtOperat();
            //生成token,时间为20分钟
            String token=jwtOperat.createToken(tokenid,subject,20);
            map.put("flag","登录成功");
            //存放token
            map.put("token",token);
            //将用户名返回前端中
            map.put("loginname",member.getName());
            //用户头像地址
            map.put("loginImg",member.getHeadImg());
        }
        return map;
    }

    @RequestMapping(value = "/findpassword")
    @ResponseBody
    /**
     * 更改密码
     * @author: aoliao
     * @param: request
     * @param: session
     * @param: checkCode
     * @param: cellphone
     * @param: password
     * @updateTime: 2019/11/1 19:48
     * @return: java.util.Map<java.lang.String,java.lang.String>
     */
    public Map<String,String> findPassword(HttpServletRequest request,
                                           HttpSession session,
                                           @RequestParam(value = "checkCode")String checkCode,
                                           @RequestParam(value = "cellphone")String cellphone,
                                           @RequestParam(value = "password")String password){
        Map<String,String> map = new HashMap<>();
        map.put("flag","修改密码失败");

        map= (HashMap<String, String>) Captcha.captchaDetection(map,session,checkCode,request);

        if (!"修改密码失败".equals(map.get("flag"))){
            return map;
        }
        if (cellphone!=null&&password!=null&&!"".equals(cellphone)&&!"".equals(password))
        {
            if (!"null".equals(cellphone)&&!"null".equals(password)){
                //判断用户是否存在
                if (memberService.existCellphone(cellphone)){

                    if (memberService.setPassword(cellphone,MD5Util.getMD5String(password))){
                        map.put("flag","修改密码成功");
                    }
                }else {
                    map.put("flag","修改密码失败，用户不存在");
                }

            }
        }

        return map;
    }

    @RequestMapping(value = "/productlist")
    @ResponseBody
    /**
     * @author: aoliao
     * @param: page
     * @param: style
     * @param: type
     * @param: word
     * @param: request
     * @updateTime: 2019/10/26 18:56
     * @return: com.github.pagehelper.PageInfo<com.xinda.model.ProviderProduct>
     */
    public PageInfo<ProviderProduct> getProductList(@RequestParam(value = "page",defaultValue = "1",required = false) int page,
                                                    @RequestParam(value = "style",required = false)String style,
                                                    @RequestParam(value = "type",required = false)String type,
                                                    @RequestParam(value = "word",required = false)String word,
                                                    @RequestParam(value = "providerId",required = false)String providerId) throws IOException {

        //暂时设置5
        int pageSize=5;
        PageInfo<ProviderProduct> pagelist=memberService.getProductList(page,pageSize,style,type,word,providerId);
        return pagelist;
    }

    @RequestMapping("/addcart")
    @ResponseBody

    /**
     * 页面点击添加购物车时所用
     * @author: aoliao
     * @param: productId
     * @param: request
     * @updateTime: 2019/11/4 11:00
     * @return: java.util.Map<java.lang.String,java.lang.String>
     */
    public Map<String,String> foundCart(@RequestParam(value = "productId",required = false)String productId,
                                        HttpServletRequest request) throws IOException {

        String token = (String) GetCookie.getCookie(request,"token");

        Map<String,String> map = new HashMap<>();

        map.put("flag","增加到购物车失败");

        JwtOperat jwtOperat = new JwtOperat();
        Map<String,String> subjectMap = jwtOperat.getSubject(token);
        //取出会员id
        String memberId = subjectMap.get("userid");


        //判断表中是否有数据
        if (cartService.exitCart(productId,memberId)){
            //存在，取出数据
            Cart cart = cartService.getCart(productId,memberId);
            //增加1个数量
            if (cartService.plusCart(cart.getId(),1)){
                map.put("flag","已添加到购物车");
                return map;
            }else {
                return map;
            }
        }
        /*不存在cart数据时**/

        ProviderProduct product = productService.getProduct(productId);

        //如果没有该服务
        if (product==null){
            map.put("flag","增加购物车失败，没有该服务");
            return map;
        }

        Cart cart = new Cart();
        /*放入数据**/

        String cartId = ObjectId.getId();

        cart.setId(cartId);
        cart.setMemberId(memberId);
        cart.setProductId(productId);
        cart.setProviderId(product.getProviderId());
        cart.setBuyNum(1);
        cart.setTotalPrice(1);
        cart.setUnit(product.getUnit());
        cart.setTotalPrice(product.getPrice());
        cart.setServiceName(product.getServiceName());
        cart.setServiceInfo(product.getServiceInfo());
        cart.setUnitPrice(product.getPrice());
        if (cartService.addCart(cart)){
            map.put("flag","已添加到购物车");
        }else {
            return map;
        }
        return map;
    }
    @RequestMapping("/pluscart")
    @ResponseBody
    /**
     * 增加购物车数量
     * @author: aoliao
     * @param: cartId
     * @updateTime: 2019/11/4 17:44
     * @return: java.util.Map<java.lang.String,java.lang.String>
     */
    public Map<String,String> plusCart(@RequestParam(value = "cartId",required = true)String cartId){
        Map<String,String> map = new HashMap<>();
        map.put("flag","增加失败");
        if (cartService.plusCart(cartId,1)){
            map.put("flag","增加成功");
            Cart cart=cartService.getCart(cartId);
            //返回总金额
            map.put("totalprice",cart.getTotalPrice().toString());
        }
        return map;
    }
    @RequestMapping("/lesscart")
    @ResponseBody
    /**
     * 减少购物车数量
     * @author: aoliao
     * @param: cartId
     * @updateTime: 2019/11/4 17:45
     * @return: java.util.Map<java.lang.String,java.lang.String>
     */
    public Map<String,String> lessCart(@RequestParam(value = "cartId",required = true)String cartId){
        Map<String,String> map = new HashMap<>();
        map.put("flag","减少购物数量失败");
        if (cartService.lessCart(cartId,1)){
            map.put("flag","减少购物数量成功");
            Cart cart=cartService.getCart(cartId);
            //返回总金额
            map.put("totalprice",cart.getTotalPrice().toString());
        }
        return map;
    }
    @RequestMapping("/setbuynum")
    @ResponseBody
    /**
     * 修改购物车数量
     * @author: aoliao
     * @param: cartId
     * @param: buyNum
     * @updateTime: 2019/11/4 17:45
     * @return: java.util.Map<java.lang.String,java.lang.String>
     */
    public Map<String,String> setCartNum(@RequestParam(value = "cartId",required = true)String cartId,
                                         @RequestParam(value = "buynum",required = true)int buyNum){
        Map<String,String> map = new HashMap<>();
        map.put("flag","修改失败");
        if (cartService.setBuyNum(cartId,buyNum)){
            map.put("flag","修改成功");
            Cart cart=cartService.getCart(cartId);
            //返回总金额
            map.put("totalprice",cart.getTotalPrice().toString());
        }
        return map;
    }
    @RequestMapping("/delcart")
    @ResponseBody
    /**
     * 删除购物车
     * @author: aoliao
     * @param: cartId
     * @updateTime: 2019/11/4 17:45
     * @return: java.util.Map<java.lang.String,java.lang.String>
     */
    public Map<String,String> deleteCart(@RequestParam(value = "cartId",required = true)String cartId){
        Map<String,String> map = new HashMap<>();
        map.put("flag","删除失败");
        if (cartService.deleteCart(cartId)){
            map.put("flag","删除成功");
        }
        return map;
    }
    @RequestMapping("/cartlist")
    @ResponseBody
    /**
     * 购物车列表
     * @author: aoliao
     * @param: page
     * @param: style
     * @param: word
     * @param: request
     * @updateTime: 2019/11/4 17:56
     * @return: com.github.pagehelper.PageInfo<com.xinda.model.Cart>
     */
    public PageInfo<Cart> getCarList(@RequestParam(value = "page",defaultValue = "1",required = false) int page,
                                         @RequestParam(value = "style",required = false)String style,
                                         @RequestParam(value = "word",required = false)String word,
                                         HttpServletRequest request) throws IOException {

        String token = (String) GetCookie.getCookie(request,"token");

        JwtOperat jwtOperat = new JwtOperat();
        Map<String,String> subjectMap = jwtOperat.getSubject(token);
        //取出会员id
        String memberId = subjectMap.get("userid");

        //暂时设置5
        int pageSize=5;

        return cartService.getCartList(page,pageSize,memberId,style,word);
    }
    @RequestMapping("/getshopimg")
    @ResponseBody
    public Map<String,String> getShopImg(@RequestParam(value = "Id",required = true)String Id){
        ProviderProduct product = (ProviderProduct) productService.getProduct(Id);
        HashMap<String,String> map = new HashMap<>();
        map.put("imgpath",product.getServiceImg());
        return map;
    }
    @RequestMapping("/play")
    @ResponseBody
    /**
     * 用户支付,默认支付成功
     * @author: aoliao
     * @param: idList
     * @updateTime: 2019/11/5 23:19
     * @return: java.util.Map<java.lang.String,java.lang.String>
     */
    public Map<String,String> play(@RequestParam(value = "idlist",required = true) String idList) throws IOException
    {

        ObjectMapper mapper = new ObjectMapper();

        boolean flag = false;

        Map<String,String> map = new HashMap<>();
        map.put("flag","支付失败");
        //转换成数组
        List<String> playCartId = mapper.readValue(idList, List.class);
        for (Object o:playCartId) {
            ServiceOrder serviceOrder =new ServiceOrder();

            ServiceOrderProgress serviceProgress=new ServiceOrderProgress();
            //获取购物车对象
            Cart cart = cartService.getCart(o.toString());

            Member member = memberService.getMember(cart.getMemberId());

            memberService.setMemberStatusPlay(cart.getMemberId());

            if (cart!=null){
                /*初始数据服务订单数据*/
                String id = ObjectId.getId();
                serviceOrder.setId(id);
                //分配服务商
                serviceOrder.setStatus(2);

                Date date = new Date();
                serviceOrder.setCreateTime(date);
                serviceOrder.setUnit(cart.getUnit());
                serviceOrder.setUnitPrice(cart.getUnitPrice());
                serviceOrder.setTotalPrice(cart.getTotalPrice());
                serviceOrder.setServiceInfo(cart.getServiceInfo());
                serviceOrder.setServiceName(cart.getServiceName());
                serviceOrder.setProviderId(cart.getProviderId());
                serviceOrder.setProductId(cart.getProductId());
                serviceOrder.setMemberId(cart.getMemberId());
                serviceOrder.setBuyNum(cart.getBuyNum());
                flag = orderService.addServiceOrder(serviceOrder);

                /*初始服务订单过程*/
                //直接放置同一个订单id，便于查询
                serviceProgress.setId(id);
                serviceProgress.setAcceptTime(date);
                serviceProgress.setRequestTime(date);
                orderService.addServiceTime(serviceProgress);

                /*生成新订单消息*/
                NewOrderMessage message = new NewOrderMessage();
                message.setOrderId(id);
                message.setProductId(cart.getProductId());
                message.setProviderId(cart.getProviderId());
                message.setProductName(cart.getServiceName());
                message.setMemberCellphone(member.getCellphone());
                message.setMemberName(member.getName());
                messageService.addOrderMessage(message);

            }


        }
        if (!flag){
            return map;
        }else {
            for (Object id:playCartId){
                cartService.deleteCart(id.toString());
            }
            map.put("flag","支付成功");
            return map;
        }
    }

    @RequestMapping("/getlogin")
    @ResponseBody
    /**
     * 获取用户信息
     * @author: aoliao
     * @param: request
     * @updateTime: 2019/11/7 8:51
     * @return: com.xinda.model.Member
     */
    public Member getMember(HttpServletRequest request) throws IOException {

        String token = (String) GetCookie.getCookie(request,"token");

        JwtOperat jwtOperat = new JwtOperat();
        Map<String,String> subjectMap = jwtOperat.getSubject(token);
        //取出会员id
        String memberId = subjectMap.get("userid");

        return memberService.getMember(memberId);
    }

    @RequestMapping("/uplogin")
    @ResponseBody
    /**
     * 更新用户信息
     * @author: aoliao
     * @param: member
     * @param: file
     * @param: request
     * @updateTime: 2019/11/7 11:52
     * @return: java.util.Map<java.lang.String,java.lang.String>
     */
    public Map<String,String> uplogin(Member member,
                                      @RequestParam(value = "file", required = false) MultipartFile file,
                                      HttpServletRequest request) throws IOException {

        String token = (String) GetCookie.getCookie(request,"token");

        JwtOperat jwtOperat = new JwtOperat();
        Map<String,String> subjectMap = jwtOperat.getSubject(token);
        //取出用户id
        String memberId = subjectMap.get("userid");

        String save = null;

        HashMap<String,String> map = new HashMap<>();

        Member upMember = memberService.getMember(memberId);

        //将更新的数据更换到对象中
        upMember.setName(member.getName());
        upMember.setGender(member.getGender());
        upMember.setEmail(member.getEmail());

        String headImg = upMember.getHeadImg();

        //文件是否非空
        try {
            if (file.getSize()!=0) {
                //保存文件
                save= UploadFile.uploadFile(request,file,memberId,member.getName()+"login");
                if (!save.isEmpty()){
                    upMember.setHeadImg(save);
                }
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        if (upMember.getHeadImg()==null){
            upMember.setHeadImg(headImg);
        }

        if (memberService.updateMember(upMember,memberId)){

            map.put("flag","成功");
            map.put("logImg",upMember.getHeadImg());
            map.put("logname",member.getName());
        }else {
            map.put("flag","失败");

        }
        return map;
    }

    @RequestMapping("/setpassword")
    @ResponseBody
    /**
     * 修改密码
     * @author: aoliao
     * @param: oldPassword
     * @param: newPassword
     * @param: request
     * @updateTime: 2019/11/7 9:33
     * @return: java.util.Map<java.lang.String,java.lang.String>
     */
    public Map<String,String> setMemberPassword(@RequestParam(value = "oldpassword", required = true) String oldPassword,
                                                @RequestParam(value = "newpassword", required = true) String newPassword,
                                                HttpServletRequest request) throws IOException {

        String token = (String) GetCookie.getCookie(request,"token");

        JwtOperat jwtOperat = new JwtOperat();
        Map<String,String> subjectMap = jwtOperat.getSubject(token);
        //取出用户id
        String memberId = subjectMap.get("userid");

        Map<String,String> map = new HashMap<>();

        map.put("flag","更改失败");

        Member member = memberService.getMember(memberId);

        if (!MD5Util.getMD5String(oldPassword).equals(member.getPassword())){
            map.put("flag","输入的旧密码错误，更改失败");
            return map;
        }

        member.setPassword(MD5Util.getMD5String(newPassword));

        if (memberService.updateMember(member,memberId)){

            map.put("flag","修改成功");
            return map;
        }

        return map;
    }
    @RequestMapping("/judge")
    @ResponseBody
    /**
     * 用户评价
     * @author: aoliao
     * @param: productId
     * @param: context
     * @updateTime: 2019/11/7 13:52
     * @return: java.util.Map<java.lang.String,java.lang.String>
     */
    public Map<String,String> addjudge(@RequestParam(value = "orderId", required = true) String orderId,
                                       @RequestParam(value = "context", required = true) String context,
                                       HttpServletRequest request) throws IOException {


        Date date = new Date();
        //获取订单
        ServiceOrder serviceOrder = orderService.getServiceOrder(orderId);

        String token = (String) GetCookie.getCookie(request,"token");

        Map<String,String> map = new HashMap<>();
        map.put("flag","评论失败");

        JwtOperat jwtOperat = new JwtOperat();
        Map<String,String> subjectMap = jwtOperat.getSubject(token);
        //取出会员id
        String memberId = subjectMap.get("userid");

        String id = ObjectId.getId();

        ServiceJudge serviceJudge = new ServiceJudge();
        serviceJudge.setId(id);
        serviceJudge.setContent(context);
        serviceJudge.setOrderId(orderId);
        serviceJudge.setJudgeTime(date);
        serviceJudge.setMemberId(memberId);
        serviceJudge.setProviderId(serviceOrder.getProviderId());
        serviceJudge.setStatus(1);
        if (orderService.addJudge(serviceJudge)){
            map.put("flag","评论成功");
            return map;
        }
        return map;
    }

}

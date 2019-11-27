package com.xinda.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.pagehelper.PageInfo;
import com.xinda.model.Provider;
import com.xinda.model.ProviderProduct;
import com.xinda.model.User;
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
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author aliao
 */
@Controller
@RequestMapping("/admin")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    ProductService productService;
    @Autowired
    ProviderService providerService;
    @Autowired
    MemberService memberService;
    @Autowired
    OrderService orderService;


    @RequestMapping(value = "/login")
    @ResponseBody
    /**
     * 管理员登录
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
        User user=new User();
        if (!"null".equals(cellphone) && !"null".equals(password)&&cellphone!=null&&password!=null&&!"".equals(cellphone)&&!"".equals(password)){
            if (!userService.existCellphone(cellphone)){
                map.put("flag","登录失败，不存在用户");
                return map;
            }

            if (!userService.existUser(cellphone,MD5Util.getMD5String(password))){
                map.put("flag","登录失败密码错误");
                return map;
            }
            //获取用户
            user=userService.loginUser(cellphone, MD5Util.getMD5String(password));
        }
        if (user.getId()!=null){

            ObjectMapper mapper = new ObjectMapper();
            ObjectNode root1 = mapper.createObjectNode();
            //放置组织id
            root1.put("userid",user.getOrgId());

            root1.put("username", user.getUserName());
            root1.put("password", user.getPassword());
            root1.put("role","admin");
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
            map.put("loginname",user.getUserName());
            //用户头像地址
            map.put("loginImg",user.getHeadImg());
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

        //每次修改密码就更新一次组织id
        String orgId = ObjectId.getId();

        if (!"修改密码失败".equals(map.get("flag"))){
            return map;
        }
        if (cellphone!=null&&password!=null&&!"".equals(cellphone)&&!"".equals(password))
        {
            if (!"null".equals(cellphone)&&!"null".equals(password)){
                //判断用户是否存在
                if (userService.existCellphone(cellphone)){

                    if (userService.setPassword(cellphone,MD5Util.getMD5String(password),orgId)){
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
     * 获取产品列表
     * @author: aoliao
     * @param: page
     * @param: style
     * @param: type
     * @param: word
     * @updateTime: 2019/11/8 16:13
     * @return: com.github.pagehelper.PageInfo
     */
    public PageInfo getProductList(@RequestParam(value = "page",defaultValue = "1",required = false) int page,
                                   @RequestParam(value = "style",required = false)String style,
                                   @RequestParam(value = "type",required = false)String type,
                                   @RequestParam(value = "word",required = false)String word){

        //暂时设置7
        int pageSize=7;
        PageInfo<ProviderProduct> pagelist=productService.productAdminList(page,pageSize,style,type,word);
        return pagelist;
    }
    @RequestMapping("/providerlist")
    @ResponseBody
    /**
     * 服务商列表
     * @author: aoliao
     * @param: page
     * @param: status
     * @param: word
     * @updateTime: 2019/11/14 16:27
     * @return: com.github.pagehelper.PageInfo
     */
    public PageInfo getProviderList(@RequestParam(value = "page",defaultValue = "1",required = false) int page,
                                    @RequestParam(value = "status",required = false,defaultValue = "1")int status,
                                    @RequestParam(value = "word",required = false)String word){
        //暂时设置7
        int pageSize=7;
        return providerService.providerList(page,pageSize,word,status);
    }
    @RequestMapping("/stop")
    @ResponseBody
    /**
     * 服务商停用
     * @author: aoliao
     * @param: providerId
     * @updateTime: 2019/11/14 16:27
     * @return: java.util.Map<java.lang.String,java.lang.String>
     */
    public Map<String,String> stopProvider(@RequestParam(value = "providerId",required = false)String providerId){
        Map<String,String> map = new HashMap<>();
        map.put("flag","失败");
        if (providerService.setProviderStatus(providerId,2))
        {
            if (productService.setStatusStopProduct(providerId)){
                map.put("flag","成功");
            }
            else {
                System.out.println("重置服务商状态成功，但服务产品状态更改失败");
                map.put("flag","重置服务商状态成功，但服务产品状态更改失败");
            }
        }
        return map;
    }
    @RequestMapping("/through")
    @ResponseBody
    /**
     * 服务商通过
     * @author: aoliao
     * @param: providerId
     * @updateTime: 2019/11/14 16:26
     * @return: java.util.Map<java.lang.String,java.lang.String>
     */
    public Map<String,String> viaProvider(@RequestParam(value = "providerId",required = false)String providerId){
        Map<String,String> map = new HashMap<>();
        map.put("flag","失败");
        if (providerService.setProviderStatus(providerId,1)){
            map.put("flag","成功");
        }
        return map;
    }
    @RequestMapping("/memberlist")
    @ResponseBody
    /**
     * 获取会员列表
     * @author: aoliao
     * @param: page
     * @param: word
     * @param: status
     * @updateTime: 2019/11/14 16:26
     * @return: com.github.pagehelper.PageInfo
     */
    public PageInfo getMemberList(@RequestParam(value = "page",defaultValue = "1",required = false) int page,
                                  @RequestParam(value = "word",required = false)String word,
                                  @RequestParam(value = "status",required = false)int status){
        //暂时设置7
        int pageSize=7;
        return memberService.getMemberList(page,pageSize,word,status);
    }
    @RequestMapping("/orderlist")
    @ResponseBody
    /**
     * 获取订单列表
     * @author: aoliao
     * @param: page
     * @param: word
     * @param: style
     * @param: memberId
     * @updateTime: 2019/11/14 16:26
     * @return: com.github.pagehelper.PageInfo
     */
    public PageInfo getOrderList(@RequestParam(value = "page",defaultValue = "1",required = false) int page,
                                 @RequestParam(value = "word",required = false)String word,
                                 @RequestParam(value = "style",required = false)String style,
                                 @RequestParam(value = "memberId",required = false)String memberId){
        //暂时设置7
        int pageSize=7;
        return orderService.getOrderList(page,pageSize,word,style,memberId);
    }
    @RequestMapping("/playtotal")
    @ResponseBody
    /**
     * 获取时间维度内的总支付金额
     * @author: aoliao
     * @param: status
     * @updateTime: 2019/11/12 20:01
     * @return: java.util.Map<java.lang.String,java.lang.Long>
     */
    public Map<String,Long> getPlayTotal(@RequestParam(value = "status",required = false)String status){

        Calendar c = Calendar.getInstance();
        Date date=null;
        //0今天 1一周内 2 一个月 3全部
        if ("0".equals(status)){
            c.setTime(new Date());
            c.add(Calendar.DATE, -0);
            date = c.getTime();
        }else if ("1".equals(status)){
            //过去七天
            c.setTime(new Date());
            c.add(Calendar.DATE, - 7);
            date = c.getTime();
        }else if ("2".equals(status)){
            c.setTime(new Date());
            c.add(Calendar.MONTH, -1);
            date = c.getTime();
        }else {
            c.setTime(new Date());
            c.add(Calendar.YEAR, -30);
            date = c.getTime();
        }
        return orderService.getPlayTotal(date);
    }
    @RequestMapping("/playlist")
    @ResponseBody
    /**
     * 费用中心
     * @author: aoliao
     * @param: page
     * @param: status
     * @updateTime: 2019/11/11 11:01
     * @return: com.github.pagehelper.PageInfo
     */
    public PageInfo getPlayOrder(@RequestParam(value = "page",defaultValue = "1",required = false) int page,
                                 @RequestParam(value = "status",required = false)String status){

        Calendar c = Calendar.getInstance();
        Date date=null;
        //0今天 1一周内 2 一个月 3全部
        if ("0".equals(status)){
            c.setTime(new Date());
            c.add(Calendar.DATE, -1);
            date = c.getTime();
        }else if ("1".equals(status)){
            //过去七天
            c.setTime(new Date());
            c.add(Calendar.DATE, - 7);
            date = c.getTime();
        }else if ("2".equals(status)){
            c.setTime(new Date());
            c.add(Calendar.MONTH, -1);
            date = c.getTime();
        }else {
            c.setTime(new Date());
            c.add(Calendar.YEAR, -30);
            date = c.getTime();
        }
        //暂时设置7
        int pageSize=7;
        return orderService.getPlayOrder(page,pageSize,date);
    }
    @RequestMapping("/productrecom")
    @ResponseBody
    /**
     * 推荐页内的产品列表
     * @autho: aoliao
     * @param: page
     * @param: word
     * @param: style
     * @param: type
     * @param: status
     * @updateTime: 2019/11/14 16:27
     * @return: com.github.pagehelper.PageInfo
     */
    public PageInfo getProductListRecom(@RequestParam(value = "page",defaultValue = "1",required = false) int page,
                                        @RequestParam(value = "word",required = false)String word,
                                        @RequestParam(value = "style",required = false)String style,
                                        @RequestParam(value = "type",required = false)String type,
                                        @RequestParam(value = "status",required = false)int status){

        //暂时设置7
        int pageSize=7;
        //0时间1价格2销量
        return productService.productAdminList(page,pageSize,style,type,word,status);
    }

    @RequestMapping(value = "/setprovider",method = RequestMethod.POST)
    @ResponseBody
    /**
     * 更新服务商信息
     * @author: aoliao
     * @param: provider
     * @param: file
     * @param: request
     * @param: providerId
     * @updateTime: 2019/11/12 20:33
     * @return: java.util.Map<java.lang.String,java.lang.String>
     */
    public Map<String,String> upProviderLogin(Provider provider,
                                      @RequestParam(value = "file", required = false) MultipartFile file,
                                      HttpServletRequest request,
                                      @RequestParam(value = "providerId", required = true) String providerId)throws Exception{

        String save = null;

        HashMap<String,String> map = new HashMap<>();

        Provider upPrvovider = providerService.getProvider(providerId);

        String logImg = upPrvovider.getLoginimg();

        //如果电话号和原本不同
        if (!provider.getCellphone().equals(upPrvovider.getCellphone())){
            if (providerService.existCellphone(provider.getCellphone())){
                map.put("flag","修改失败,该号码已经被使用");
                return map;
            }
        }
        //将更新的数据放置到upPrvovider对象中，使用upPrvovider来更新
        upPrvovider.setLoginimg(provider.getLoginimg());
        upPrvovider.setLoginId(provider.getLoginId());

        if (!provider.getPassword().equals(upPrvovider.getPassword())){
            upPrvovider.setPassword(MD5Util.getMD5String(provider.getPassword()));
        }

        upPrvovider.setName(provider.getName());
        upPrvovider.setCellphone(provider.getCellphone());
        upPrvovider.setWeixin(provider.getWeixin());
        upPrvovider.setQq(provider.getQq());
        upPrvovider.setRegion(provider.getRegion());
        upPrvovider.setEmail(provider.getEmail());

        //文件是否非空
        try {
            if (file.getSize()!=0) {
                //保存文件
                save= UploadFile.uploadFile(request,file,providerId,provider.getName()+"login");
                if (!save.isEmpty()){
                    upPrvovider.setLoginimg(save);
                }
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        if (upPrvovider.getLoginimg()==null){
            upPrvovider.setLoginimg(logImg);
        }

        if (providerService.updateProvider(upPrvovider,providerId)){

            map.put("flag","成功");
            map.put("logImg",save);
        }else {
            map.put("flag","失败");

        }
        return map;

    }

    @RequestMapping(value = "/getadmin",method = RequestMethod.GET)
    @ResponseBody
    /**
     * 获取管理员之信息
     * @author: aoliao
     * @param: request
     * @updateTime: 2019/11/12 21:22
     * @return: com.xinda.model.User
     */
    public User getUser(HttpServletRequest request) throws IOException {
        String token = (String) GetCookie.getCookie(request,"token");

        JwtOperat jwtOperat = new JwtOperat();
        Map<String,String> subjectMap = jwtOperat.getSubject(token);
        //取出运营商id
        String userId = subjectMap.get("userid");
        return userService.getUser(userId);
    }

    @RequestMapping("/upadmin")
    @ResponseBody
    /**
     * 更新管理员信息
     * @author: aoliao
     * @param: user
     * @param: file
     * @param: request
     * @updateTime: 2019/11/12 21:24
     * @return: java.util.Map<java.lang.String,java.lang.String>
     */
    public Map<String,String> upAdminLogin(User user,
                                              @RequestParam(value = "file", required = false) MultipartFile file,
                                              HttpServletRequest request) throws IOException {

        String token = (String) GetCookie.getCookie(request,"token");

        JwtOperat jwtOperat = new JwtOperat();
        Map<String,String> subjectMap = jwtOperat.getSubject(token);
        //取出管理员id
        String adminId = subjectMap.get("userid");

        String save = null;

        HashMap<String,String> map = new HashMap<>();

        User upUser = userService.getUser(adminId);

        String logImg = upUser.getHeadImg();

        //如果电话号和原本不同
        if (!user.getCellphone().equals(upUser.getCellphone())){
            if (userService.existCellphone(user.getCellphone())){
                map.put("flag","修改失败,该号码已经被使用");
                return map;
            }
        }
        //将更新的数据放置到upPrvovider对象中，使用upPrvovider来更新
        upUser.setHeadImg(user.getHeadImg());
        upUser.setLoginId(user.getLoginId());

        if (!user.getPassword().equals(upUser.getPassword())){
            upUser.setPassword(MD5Util.getMD5String(user.getPassword()));
        }

        upUser.setUserName(user.getUserName());
        upUser.setCellphone(user.getCellphone());
        upUser.setEmail(user.getEmail());

        //文件是否非空
        try {
            if (file.getSize()!=0) {
                //保存文件
                save= UploadFile.uploadFile(request,file,adminId,user.getUserName()+"login");
                if (!save.isEmpty()){
                    upUser.setHeadImg(save);
                }
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        if (upUser.getHeadImg()==null){
            upUser.setHeadImg(logImg);
        }

        if (userService.updateUser(upUser,adminId)){

            map.put("flag","成功");
            map.put("logImg",save);
            map.put("logname",upUser.getUserName());
        }else {
            map.put("flag","失败");
        }
        return map;

    }
}

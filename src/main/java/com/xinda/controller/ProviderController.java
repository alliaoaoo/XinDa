package com.xinda.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.xinda.model.Provider;
import com.xinda.model.ProviderMessage;
import com.xinda.service.MemberService;
import com.xinda.service.MessageService;
import com.xinda.service.ProviderService;
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
 * @author aoliao
 */
@Controller
@RequestMapping("/provide")
public class ProviderController {
    @Autowired
    ProviderService providerService;
    @Autowired
    MemberService memberService;
    @Autowired
    MessageService messageService;

    @RequestMapping(value = "/login")
    @ResponseBody
    /**
     * 服务商登录
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
        Provider provider=new Provider();
        if (!"null".equals(cellphone) && !"null".equals(password)&&cellphone!=null&&password!=null&&!"".equals(cellphone)&&!"".equals(password)){
            if (!providerService.existCellphone(cellphone)){
                map.put("flag","登录失败，不存在用户");
                return map;
            }
            if (!providerService.existProvider(cellphone,MD5Util.getMD5String(password))){
                    map.put("flag","登录失败，密码错误");
                    return map;
                }
            //获取用户
            provider=providerService.loginProvider(cellphone,MD5Util.getMD5String(password));
        }
        if (provider.getId()!=null){
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode root1 = mapper.createObjectNode();

            root1.put("userid",provider.getId());
            root1.put("username", provider.getName());
            root1.put("password", provider.getPassword());
            root1.put("role","provider");
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
            map.put("loginname",provider.getName());
            //用户头像地址
            map.put("loginImg",provider.getLoginimg());
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

        System.out.println("#######################################");
        System.out.println(cellphone);
        System.out.println(password);

        map=Captcha.captchaDetection(map,session,checkCode,request);

        if (!"修改密码失败".equals(map.get("flag"))){
            return map;
        }
        if (cellphone!=null&&password!=null&&!"".equals(cellphone)&&!"".equals(password))
        {
            if (!"null".equals(cellphone)&&!"null".equals(password)){
                //判断用户是否存在
                if (providerService.existCellphone(cellphone)){

                    if (providerService.setPassword(cellphone,MD5Util.getMD5String(password))){
                        map.put("flag","修改密码成功");
                    }else {
                        map.put("flag","申请通过后才可以修改密码");
                    }
                }else {
                    map.put("flag","修改密码失败，用户不存在");
                }

            }
        }

        return map;
    }

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

        /*!!*/

        map= (HashMap<String, String>) Captcha.captchaDetection(map,session,checkCode,request);

        if (!"注册失败".equals(map.get("flag"))){
            return map;
        }
        String provideId = ObjectId.getId();
        Provider provider = new Provider();
        provider.setId(provideId);
        if (!"null".equals(cellphone)&&!"".equals(cellphone)&&cellphone!=null){
            //检查用户是否存在
            if (providerService.existCellphone(cellphone)){
                map.put("flag","注册失败，已经存在用户");
                return map;
            }
            //插入数据
            provider.setPassword(MD5Util.getMD5String(password));
            provider.setCellphone(cellphone);
            provider.setRegion(region);
            //未通过为0
            provider.setStatus(0);
            Date date = new Date();
            provider.setRegisterTime(date);
            flag=providerService.registProvider(provider);

            //添加消息属性
            ProviderMessage providerMessage = new ProviderMessage();
            String messid= ObjectId.getId();
            providerMessage.setMessageId(messid);
            providerMessage.setProviderId(provideId);
            providerMessage.setMessageText("请添加服务商信息");
            //添加消息
            messageService.addProviderMessage(providerMessage);
        }
        if (flag){
            map.put("flag","注册成功");
        }
        return map;
    }
    @RequestMapping(value = "/upshop")
    @ResponseBody
    /**
     * 更新店铺信息
     * @author: aoliao
     * @param: provider
     * @param: file
     * @param: request
     * @updateTime: 2019/10/31 11:12
     * @return: java.util.Map<java.lang.String,java.lang.String>
     */
    public Map<String,String> upProShop(Provider provider,
                                        @RequestParam(value = "file", required = false) MultipartFile file,
                                        HttpServletRequest request)throws Exception{
        HashMap<String,String> map = new HashMap<>();

        String token = (String) GetCookie.getCookie(request,"token");

        JwtOperat jwtOperat = new JwtOperat();
        Map<String,String> subjectMap = jwtOperat.getSubject(token);
        //取出服务商id
        String providerId = subjectMap.get("userid");

        String save;


        Provider upPrvovider = providerService.getProvider(providerId);

        //如果电话号和原本不同
        if (!provider.getCellphone().equals(upPrvovider.getCellphone())){

            if (providerService.existCellphone(provider.getCellphone())){

                map.put("flag","修改失败,已经存在该号码的服务商，请更换手机号");
                return map;
            }
        }

        upPrvovider.setQq(provider.getQq());
        upPrvovider.setWorkTime(provider.getWorkTime());
        upPrvovider.setCellphone(provider.getCellphone());
        upPrvovider.setProviderInfo(provider.getProviderInfo());
        //文件是否非空
        try {
            if (file.getSize()!=0) {
                //保存文件
                save= UploadFile.uploadFile(request,file,providerId,provider.getName());
                if (!save.isEmpty()){

                    upPrvovider.setProviderImg(save);
                }
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        if (providerService.updateProvider(upPrvovider,providerId)){
            map.put("flag","成功");
        }else {
            map.put("flag","失败");
        }return map;
    }
    @RequestMapping(value = "/getshop")
    @ResponseBody
    /**
     * 获取服务商信息
     * @author: aoliao
     * @param: request
     * @updateTime: 2019/10/31 11:10
     * @return: com.xinda.model.Provider
     */
    public Provider getShop(HttpServletRequest request) throws IOException {

        String token = (String) GetCookie.getCookie(request,"token");

        JwtOperat jwtOperat = new JwtOperat();
        Map<String,String> subjectMap = jwtOperat.getSubject(token);
        //取出服务商id
        String providerId = subjectMap.get("userid");

        return providerService.getProvider(providerId);
    }
    @RequestMapping(value = "/uplogin",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> upLogin(Provider provider,
                                      @RequestParam(value = "file", required = false) MultipartFile file,
                                      HttpServletRequest request)throws Exception{
        String token = (String) GetCookie.getCookie(request,"token");

        JwtOperat jwtOperat = new JwtOperat();
        Map<String,String> subjectMap = jwtOperat.getSubject(token);
        //取出服务商id
        String providerId = subjectMap.get("userid");

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
            map.put("logname",upPrvovider.getName());
        }else {
            map.put("flag","失败");

        }
        return map;
    }
    @RequestMapping(value = "/delordermessage",method = RequestMethod.POST)
    @ResponseBody
    /**
     * 删除新订单消息
     * @author: aoliao
     * @param: orderId
     * @updateTime: 2019/11/13 14:38
     * @return: java.util.Map<java.lang.String,java.lang.String>
     */
    public Map<String,String> delOrderMessage(
            @RequestParam(value = "orderId", required = true) String orderId){

        HashMap<String,String> map = new HashMap<>();
        map.put("flag","失败");
        if (messageService.deleteOrderMessage(orderId)){
            map.put("flag","成功");
            return map;
        }
        return map;
    }
    @RequestMapping(value = "/delmessage",method = RequestMethod.POST)
    @ResponseBody
    /**
     * 删除服务商消息
     * @author: aoliao
     * @param: orderId
     * @updateTime: 2019/11/13 14:38
     * @return: java.util.Map<java.lang.String,java.lang.String>
     */
    public Map<String,String> delProviderMessage(
            @RequestParam(value = "messageId", required = true) String messageId){

        HashMap<String,String> map = new HashMap<>();
        map.put("flag","失败");
        if (messageService.deleteProviderMessage(messageId)){
            map.put("flag","成功");
            return map;
        }
        return map;
    }
    @RequestMapping(value = "/messagelist")
    @ResponseBody
    /**
     * 获取消息列表
     * @author: aoliao
     * @param: request
     * @updateTime: 2019/11/13 14:45
     * @return: java.util.List
     */
    public List getMessage(HttpServletRequest request) throws IOException {
        String token = (String) GetCookie.getCookie(request,"token");

        JwtOperat jwtOperat = new JwtOperat();
        Map<String,String> subjectMap = jwtOperat.getSubject(token);
        //取出服务商id
        String providerId = subjectMap.get("userid");
        return messageService.getProviderMessageList(providerId);
    }
    @RequestMapping(value = "/ordermessage")
    @ResponseBody
    /**
     * 获取消息列表
     * @author: aoliao
     * @param: request
     * @updateTime: 2019/11/13 14:45
     * @return: java.util.List
     */
    public List getOrderMessage(HttpServletRequest request) throws IOException {
        String token = (String) GetCookie.getCookie(request,"token");

        JwtOperat jwtOperat = new JwtOperat();
        Map<String,String> subjectMap = jwtOperat.getSubject(token);
        //取出服务商id
        String providerId = subjectMap.get("userid");
        return messageService.getNewOrderMessageList(providerId);
    }
    @RequestMapping(value = "/getmessagesum")
    @ResponseBody
    /**
     * 获取消息数量
     * @author: aoliao
     * @param: request
     * @updateTime: 2019/11/13 14:49
     * @return: java.util.Map<java.lang.String,java.lang.Integer>
     */
    public Map<String,Integer> getMessageSum(HttpServletRequest request) throws IOException {
        String token = (String) GetCookie.getCookie(request,"token");

        JwtOperat jwtOperat = new JwtOperat();
        Map<String,String> subjectMap = jwtOperat.getSubject(token);
        //取出服务商id
        String providerId = subjectMap.get("userid");
        Map<String,Integer> map = new HashMap<>();
        int sum = messageService.getProviderMessageSum(providerId);
        map.put("messsum",sum);
        return map;
    }
    @RequestMapping(value = "/messordersum")
    @ResponseBody
    /**
     * 获取消息数量
     * @author: aoliao
     * @param: request
     * @updateTime: 2019/11/13 14:49
     * @return: java.util.Map<java.lang.String,java.lang.Integer>
     */
    public Map<String,Integer> getOrderMessageSum(HttpServletRequest request) throws IOException {
        String token = (String) GetCookie.getCookie(request,"token");

        JwtOperat jwtOperat = new JwtOperat();
        Map<String,String> subjectMap = jwtOperat.getSubject(token);
        //取出服务商id
        String providerId = subjectMap.get("userid");
        Map<String,Integer> map = new HashMap<>();
        int sum = messageService.getNewOrderSum(providerId);
        map.put("messsum",sum);
        return map;
    }
}

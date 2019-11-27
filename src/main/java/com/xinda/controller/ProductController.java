package com.xinda.controller;

import com.github.pagehelper.PageInfo;
import com.xinda.model.ProductStyleKey;
import com.xinda.model.ProductTypeKey;
import com.xinda.model.ProviderMessage;
import com.xinda.model.ProviderProduct;
import com.xinda.service.MessageService;
import com.xinda.service.ProductService;
import com.xinda.utils.GetCookie;
import com.xinda.utils.JwtOperat;
import com.xinda.utils.ObjectId;
import com.xinda.utils.UploadFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author aliao
 */
@Controller
@RequestMapping("/product")
public class ProductController {
    @Autowired
    ProductService productService;
    @Autowired
    MessageService messageService;

    @RequestMapping("/getSort")
    @ResponseBody
    /**
     * 获取分类
     * @author: aoliao
     * @updateTime: 2019/10/21 11:49
     * @return: java.util.Map<java.lang.String,java.lang.Object>
     */
    public Map<String,Object> getSort()throws Exception{

        List<ProductStyleKey> productStyle = productService.getProductStyle();
        List<ProductTypeKey> productType = productService.getProductType();
        Map<String,Object> sortMap = new HashMap<>();
        sortMap.put("Style",productStyle);
        sortMap.put("type",productType);
        return sortMap;
    }
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    @ResponseBody
    /**
     * 更新产品
     * @author: aoliao
     * @param: product
     * @param: file
     * @param: request
     * @updateTime: 2019/11/13 15:03
     * @return: java.util.Map
     */
    public Map upProduct(ProviderProduct product,
                         @RequestParam(value = "file", required = false) MultipartFile file,
                         HttpServletRequest request) throws IOException {
        Map map =new  HashMap<String,String>();
        String token = (String) GetCookie.getCookie(request,"token");
        String save;

        JwtOperat jwtOperat = new JwtOperat();
        Map<String,String> subjectMap = jwtOperat.getSubject(token);
        //取出服务商id
        String providerId = subjectMap.get("userid");
        //从cookie取出服务id
        String productId = (String)GetCookie.getCookie(request,"productId");

        try {
            //文件是否非空
            if (file.getSize()!=0) {
                //保存文件
                save= UploadFile.uploadFile(request,file,providerId,product.getServiceName());
                if (!save.isEmpty()){
                    product.setServiceImg(save);
                    product.setProviderId(providerId);
                }
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }


        if (productService.upProduct(product,productId)){
            map.put("flag","成功");
        }else {
            map.put("flag","失败");
        }
        return map;
    }
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ResponseBody
    /**
     * @author: aoliao
     * @param: product
     * @param: file
     * @param: request
     * @updateTime: 2019/10/24 16:32  
     * @return: java.util.Map<java.lang.String,java.lang.String>
     */
    public Map<String,String> insertProduct(
            ProviderProduct product,
            @RequestParam(value = "file", required = false) MultipartFile file,
            HttpServletRequest request)
            throws IOException{
        // 格式化时间
        SimpleDateFormat sdf = new SimpleDateFormat();
        // a为am/pm的标记
        sdf.applyPattern("yyyy-MM-dd HH:mm:ss a");
        // 获取当前时间
        Date date = new Date();

        String token = (String) GetCookie.getCookie(request,"token");

        JwtOperat jwtOperat = new JwtOperat();
        Map<String,String> subjectMap = jwtOperat.getSubject(token);
        //取出服务商id
        String providerID = subjectMap.get("userid");


        product.setProviderId(providerID);

        boolean flag = false;
        String save = null;
        Map<String,String> map = new HashMap<>();
        map.put("flag","失败");
        //生成唯一id值
        String productId = ObjectId.getId();
        product.setProductId(productId);

        try {
            //文件是否非空
            if (file.getSize()!=0) {
                //保存文件
                save= UploadFile.uploadFile(request,file,product.getProviderId(),product.getServiceName());
                if (!save.isEmpty()){
                    product.setServiceImg(save);
                }
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        //默认下线
        product.setStatus(0);
        product.setCreateTime(date);
        int status = productService.insertProduct(product);
        //判断插入是否成功
        if (status!=0){
            //将标志置为true
            flag=true;
        }
        //标志为true时，修改flag为成功
        if (flag){map.put("flag","成功");}

        return map;
    }
    @RequestMapping(value = "/list",method = RequestMethod.GET)
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
                                                    HttpServletRequest request) throws IOException {
        String token = (String) GetCookie.getCookie(request,"token");

        JwtOperat jwtOperat = new JwtOperat();
        Map<String,String> subjectMap = jwtOperat.getSubject(token);
        //取出服务商id
        String providerID = subjectMap.get("userid");
        //暂时设置7
        int pageSize=7;
        PageInfo<ProviderProduct> pagelist=productService.productList(page,pageSize,providerID,style,type,word);
        return pagelist;
    }
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Map<String,String> deleteProduct(@RequestParam(value = "productID")String productID,
                                            HttpServletRequest request)throws Exception{
        ProviderProduct providerProduct=productService.getProduct(productID);
        Map<String,String> map=new HashMap<>();
        if (1==productService.deleteProduct(productID)){
            String path = "/uploads/"+providerProduct.getProviderId()+"/"+providerProduct.getServiceName();
            UploadFile.delUploadFile(request,path);
            map.put("flag","删除成功");
            return map;
        }else {
            map.put("flag","删除失败");
            return map;
        }
    }
    @RequestMapping("/getproduct")
    @ResponseBody
    /**
     * 获取产品信息
     * @author: aoliao
     * @param: productId
     * @updateTime: 2019/10/28 17:39
     * @return: com.xinda.model.ProviderProduct
     */
    public ProviderProduct getProduct(@RequestParam(value = "productId",required = true)String productId){
        return productService.getProduct(productId);
    }
    @RequestMapping("/online")
    @ResponseBody
    /**
     * 更新状态为上线
     * @author: aoliao
     * @param: productId
     * @updateTime: 2019/11/2 17:39
     * @return: java.util.Map<java.lang.String,java.lang.String>
     */
    public Map<String,String> onlineProduct(@RequestParam(value = "productId",required = true)String productId){
        System.out.println(productId);
        ProviderProduct providerProduct = productService.getProduct(productId);
        providerProduct.setStatus(1);
        Map<String,String> map = new HashMap<>();
        if (productService.upProduct((ProviderProduct) providerProduct,productId)){
            map.put("flag","更新状态成功");
        }else {
            map.put("flag","更新状态失败");
        }
        return map;
    }
    @RequestMapping("/offline")
    @ResponseBody
    /**
     * 更新状态为下线
     * @author: aoliao
     * @param: productId
     * @updateTime: 2019/11/2 17:39
     * @return: java.util.Map<java.lang.String,java.lang.String>
     */
    public Map<String,String> offlineProduct(@RequestParam(value = "productId",required = true)String productId){

        ProviderProduct providerProduct = productService.getProduct(productId);
        providerProduct.setStatus(0);
        Map<String,String> map = new HashMap<>();
        if (productService.upProduct((ProviderProduct) providerProduct,productId)){
            map.put("flag","更新状态成功");
        }else {
            map.put("flag","更新状态失败");
        }
        return map;
    }

    @RequestMapping("/agree")
    @ResponseBody
    /**
     * 同意上线
     * @author: aoliao
     * @param: productId
     * @updateTime: 2019/11/11 12:21
     * @return: java.util.Map<java.lang.String,java.lang.String>
     */
    public Map<String,String> agreeOnline(@RequestParam(value = "productId",required = true)String productId){

        ProviderProduct providerProduct = productService.getProduct(productId);
        //添加消息属性
        ProviderMessage providerMessage = new ProviderMessage();
        String messid= ObjectId.getId();
        providerMessage.setMessageId(messid);
        providerMessage.setProviderId(providerProduct.getProviderId());
        providerMessage.setMessageText(providerProduct.getServiceName()+"已经同意上线");

        providerProduct.setStatus(2);
        Map<String,String> map = new HashMap<>();
        if (productService.upProduct((ProviderProduct) providerProduct,productId)){

            //添加消息
            messageService.addProviderMessage(providerMessage);
            map.put("flag","更新状态成功");
        }else {
            map.put("flag","更新状态失败");
        }
        return map;
    }
    @RequestMapping("/noagree")
    @ResponseBody
    /**
     * 不同意上线
     * @author: aoliao
     * @param: productId
     * @updateTime: 2019/11/12 12:22
     * @return: java.util.Map<java.lang.String,java.lang.String>
     */
    public Map<String,String> noAgreeOnline(@RequestParam(value = "productId",required = true)String productId){

        ProviderProduct providerProduct = productService.getProduct(productId);

        //添加消息属性
        ProviderMessage providerMessage = new ProviderMessage();
        String messid= ObjectId.getId();
        providerMessage.setMessageId(messid);
        providerMessage.setProviderId(providerProduct.getProviderId());
        providerMessage.setMessageText(providerProduct.getServiceName()+"不同意上线");

        providerProduct.setStatus(3);
        Map<String,String> map = new HashMap<>();

        if (productService.upProduct((ProviderProduct) providerProduct,productId)){
            //添加消息
            messageService.addProviderMessage(providerMessage);
            map.put("flag","更新状态成功");
        }else {
            map.put("flag","更新状态失败");
        }
        return map;
    }
    @RequestMapping("/getattr")
    @ResponseBody
    /**
     * 获取产品的其他信息
     * @author: aoliao
     * @param: productId
     * @updateTime: 2019/11/12 12:23
     * @return: java.util.Map<java.lang.String,java.lang.String>
     */
    public Map<String,String> getProductAttr(@RequestParam(value = "productId",required = true)String productId){
        return productService.getProductAttr(productId);
    }
    @RequestMapping("/setrecommemd")
    @ResponseBody
    /**
     * 设置产品为推荐
     * @author: aoliao
     * @param: productId
     * @updateTime: 2019/11/12 22:23
     * @return: java.util.Map<java.lang.String,java.lang.String>
     */
    public Map<String,String> getProvider(@RequestParam(value = "productId",required = true) String productId){
        Map<String,String> map = new HashMap<>();
        map.put("flag","失败");
        if (productService.setRecommendStatus(productId,1)){
            map.put("flag","成功");
            return map;
        }return map;
    }
    @RequestMapping("/setnorecommemd")
    @ResponseBody
    /**
     * 设置产品为不推荐
     * @author: aoliao
     * @param: productId
     * @updateTime: 2019/11/12 22:23
     * @return: java.util.Map<java.lang.String,java.lang.String>
     */
    public Map<String,String> setNoReCommemd(@RequestParam(value = "productId",required = true) String productId){
        Map<String,String> map = new HashMap<>();
        map.put("flag","失败");
        if (productService.setRecommendStatus(productId,0)){
            map.put("flag","成功");
            return map;
        }return map;
    }
}

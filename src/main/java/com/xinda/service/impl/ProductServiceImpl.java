package com.xinda.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xinda.mapper.*;
import com.xinda.model.*;
import com.xinda.service.ProductService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author aoliao
 */
@Service("productService")
public class ProductServiceImpl implements ProductService {
    @Resource
    ProductStyleMapper productStyleMapper;
    @Resource
    ProductTypeMapper productTypeMapper;
    @Resource
    ProviderProductMapper providerProductMapper;
    @Resource
    ProviderMapper providerMapper;
    @Resource
    ServiceOrderMapper serviceOrderMapper;

    @Override
    /**
     * 插入数据成功返回1,失败0
     * @author: aoliao
     * @param: product
     * @updateTime: 2019/10/24 11:06
     * @return: int
     */
    public int insertProduct(ProviderProduct product) {
        return providerProductMapper.insertSelective(product);
    }

    @Override
    /**
     * 获取产品类型
     * @author: aoliao
     * @updateTime: 2019/10/21 11:34
     * @return: java.util.List<com.xinda.model.ProductStyleKey>
     */
    public List<ProductStyleKey> getProductStyle() {
        ProductStyleExample productStyleExample = new ProductStyleExample();
        ProductStyleExample.Criteria criteria= productStyleExample.createCriteria();

        criteria.andNameIsNotNull();
        criteria.andStyleIdIsNotNull();

        return productStyleMapper.selectByExample(productStyleExample);
    }

    @Override
    /**
     * 获取产品分类
     * @author: aoliao
     * @updateTime: 2019/10/21 11:34
     * @return: java.util.List<com.xinda.model.ProductTypeKey>
     */
    public List<ProductTypeKey> getProductType() {
        ProductTypeExample productTypeExample = new ProductTypeExample();
        ProductTypeExample.Criteria criteria=productTypeExample.createCriteria();

        criteria.andNameIsNotNull();
        criteria.andTypeIdIsNotNull();
        return productTypeMapper.selectByExample(productTypeExample);

    }

    @Override
    /**
     * @author: aoliao
     * @param: pageNum
     * @param: pageSize
     * @param: providerId
     * @param: style
     * @param: type
     * @param: word
     * @updateTime: 2019/10/26 18:53
     * @return: com.github.pagehelper.PageInfo
     */
    public PageInfo productList(int pageNum, int pageSize,String providerId,String style,String type,String word) {

        ProviderProductExample productExample = new ProviderProductExample();
        ProviderProductExample.Criteria criteria= productExample.createCriteria();
        criteria.andProductIdIsNotNull();
        //根据服务商id来查找
        criteria.andProviderIdEqualTo(providerId);

        //分类
        if (style!=null&&!"".equals(style)&&!"null".equals(style)){

            criteria.andStyleIdEqualTo(style);
        }
        if (type!=null&&!"".equals(type)&&!"null".equals(type)){

            criteria.andTypeIdEqualTo(type);
        }
        //模糊查询
        if (word!=null&&!"".equals(word)&&!"null".equals(word)){
            criteria.andServiceNameLike("%"+word+"%");
        }
        //从第pageNum页开始，每页显示pageSize条记录
        PageHelper.startPage(pageNum, pageSize);
        List<ProviderProduct> productList = providerProductMapper.selectByExample(productExample);
        return new PageInfo(productList);
    }

    @Override
    /**
     * 删除服务
     * @author: aoliao
     * @param: productID
     * @updateTime: 2019/10/29 18:17
     * @return: int
     */
    public int deleteProduct(String productID) {
        ProviderProductExample productExample = new ProviderProductExample();
        ProviderProductExample.Criteria criteria= productExample.createCriteria();
        criteria.andProductIdEqualTo(productID);
        return providerProductMapper.deleteByExample(productExample);
    }

    @Override
    /**
     * 获取产品
     * @author: aoliao
     * @param: productID
     * @updateTime: 2019/10/28 13:41
     * @return: com.xinda.model.ProviderProduct
     */
    public ProviderProduct getProduct(String productID) {
        return providerProductMapper.selectByPrimaryKey(productID);
    }

    @Override
    /**
     * 更新服务产品
     * @author: aoliao
     * @param: product
     * @param: productId
     * @updateTime: 2019/10/27 13:41
     * @return: boolean
     */
    public boolean upProduct(ProviderProduct product,String productId) {
        ProviderProductExample productExample = new ProviderProductExample();
        ProviderProductExample.Criteria criteria= productExample.createCriteria();
        criteria.andProductIdEqualTo(productId);
        product.setProductId(productId);
        return providerProductMapper.updateByExample(product,productExample)!=0;
    }

    @Override
    /**
     * 管理员获取列表
     * @author: aoliao
     * @param: pageNum
     * @param: pageSize
     * @param: style
     * @param: type
     * @param: word
     * @updateTime: 2019/11/9 17:46
     * @return: com.github.pagehelper.PageInfo
     */
    public PageInfo productAdminList(int pageNum, int pageSize, String style, String type, String word) {
        ProviderProductExample productExample = new ProviderProductExample();
        ProviderProductExample.Criteria criteria= productExample.createCriteria();


        ProviderExample providerExample = new ProviderExample();
        ProviderExample.Criteria criteria1 = providerExample.createCriteria();
        //所有正常状态下的服务商
        criteria1.andStatusEqualTo(1);
        List<Provider> providerList = providerMapper.selectByExample(providerExample);

        //正常状态下的服务商id
        List<String> providerIds = new ArrayList<>();

        for (Provider provider: providerList){
            providerIds.add(provider.getId());
        }


        criteria.andProductIdIsNotNull();

        if (providerIds.size()!=0){
            //搜索范围为正常状态下的服务商
            criteria.andProviderIdIn(providerIds);
        }else {
            criteria.andProviderIdIsNull();
        }

        //获取等待上线和不同意上线之间状态的对象
        criteria.andStatusBetween(1,3);
        //分类
        if (style!=null&&!"".equals(style)&&!"null".equals(style)){

            criteria.andStyleIdEqualTo(style);
        }
        if (type!=null&&!"".equals(type)&&!"null".equals(type)){

            criteria.andTypeIdEqualTo(type);
        }
        //模糊查询
        if (word!=null&&!"".equals(word)&&!"null".equals(word)){
            criteria.andServiceNameLike("%"+word+"%");
        }
        //从第pageNum页开始，每页显示pageSize条记录
        PageHelper.startPage(pageNum, pageSize);
        List<ProviderProduct> productList = providerProductMapper.selectByExample(productExample);
        return new PageInfo(productList);
    }

    @Override
    /**
     * 优质推荐列表
     * @author: aoliao
     * @param: pageNum
     * @param: pageSize
     * @param: style
     * @param: type
     * @param: word
     * @param: status
     * @updateTime: 2019/11/11 11:39
     * @return: com.github.pagehelper.PageInfo
     */
    public PageInfo productAdminList(int pageNum, int pageSize, String style, String type, String word, int status) {
        ProviderProductExample productExample = new ProviderProductExample();
        ProviderProductExample.Criteria criteria= productExample.createCriteria();

        ProviderExample providerExample = new ProviderExample();
        ProviderExample.Criteria criteria1 = providerExample.createCriteria();
        //所有正常状态下的服务商
        criteria1.andStatusEqualTo(1);
        List<Provider> providerList = providerMapper.selectByExample(providerExample);

        //正常状态下的服务商id
        List<String> providerIds = new ArrayList<>();

        for (Provider provider: providerList){
            providerIds.add(provider.getId());
        }


        criteria.andProductIdIsNotNull();

        if (providerIds.size()!=0){
            //搜索范围为正常状态下的服务商
            criteria.andProviderIdIn(providerIds);
        }else {
            criteria.andProviderIdIsNull();
        }

        //获取等待上线和不同意上线之间状态的对象
        criteria.andStatusBetween(1,3);
        //分类
        if (style!=null&&!"".equals(style)&&!"null".equals(style)){

            criteria.andStyleIdEqualTo(style);
        }
        if (type!=null&&!"".equals(type)&&!"null".equals(type)){

            criteria.andTypeIdEqualTo(type);
        }
        //模糊查询
        if (word!=null&&!"".equals(word)&&!"null".equals(word)){
            criteria.andServiceNameLike("%"+word+"%");
        }
        //0时间1价格2销量
        if (status==0){
            //降序
            productExample.setOrderByClause("CREATE_TIME desc");
        }else if (status==1){
            productExample.setOrderByClause("PRICE desc");
        }else if (status==2)
        {
            List<String> productIds=getProductIds(productExample);
            //从第pageNum页开始，每页显示pageSize条记录
            PageHelper.startPage(pageNum, pageSize);
            //获取分页结构
            List<ProviderProduct> productList = providerProductMapper.selectByExample(productExample);

            List<ProviderProduct> newProductList=new ArrayList<>();

            PageInfo pageInfo = new PageInfo(productList);

            if (productIds.size()!=0){
                for (String id:productIds){
                    ProviderProduct product = getProduct(id);
                    newProductList.add(product);
                }
            }
            for (ProviderProduct product:productList){
                if (!productIds.contains(product.getProductId())){
                    newProductList.add(product);
                }
            }
            pageInfo.setList(newProductList);
            return pageInfo;
        }
        //从第pageNum页开始，每页显示pageSize条记录
        PageHelper.startPage(pageNum, pageSize);
        List<ProviderProduct> productList = providerProductMapper.selectByExample(productExample);
        return new PageInfo(productList);
    }

    /**
     * 获取排序后的列表
     * @author: aoliao
     * @param: productExample
     * @updateTime: 2019/11/11 18:08
     * @return: java.util.List<java.lang.String>
     */
    private List<String> getProductIds(ProviderProductExample productExample)
    {

        List<ProviderProduct> productList = providerProductMapper.selectByExample(productExample);

        List<String> productIds=new ArrayList<>();

        for (ProviderProduct product:productList){
            productIds.add(product.getProductId());
        }

        ServiceOrderExample serviceOrderExample = new ServiceOrderExample();
        ServiceOrderExample.Criteria criteria1 = serviceOrderExample.createCriteria();

        criteria1.andProductIdIn(productIds);

        List<ServiceOrder> orderList = serviceOrderMapper.selectByExample(serviceOrderExample);

        productIds.clear();

        Map<String ,Integer> map = new HashMap<>();

        for (ServiceOrder order:orderList){
            if (!map.containsKey(order.getProductId())){
                map.put(order.getProductId(),order.getBuyNum());
            }else {
                int sum=map.get(order.getProductId());
                map.put(order.getProductId(),order.getBuyNum()+sum);
            }
        }

        List<Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String, Integer>>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return (o2.getValue() - o1.getValue());
            }
        });

        for(Map.Entry<String, Integer> t:list){
            productIds.add(t.getKey());
        }
        return productIds;
    }

    @Override
    /**
     * 停用状态下所有该服务商产品都置为0
     * @author: aoliao
     * @paramroviderId
     * @updateTime: 2019/11/10 15:04
     * @return: boolean
     */
    public boolean setStatusStopProduct(String providerId) {

        ProviderProductExample productExample = new ProviderProductExample();
        ProviderProductExample.Criteria criteria= productExample.createCriteria();
        criteria.andProviderIdEqualTo(providerId);
        List<ProviderProduct> productList = providerProductMapper.selectByExample(productExample);

        boolean flag= false;
        for (ProviderProduct product:productList){
            product.setStatus(0);
            flag=providerProductMapper.updateByPrimaryKey(product)!=0;
        }
        return flag;
    }

    @Override
    /**
     * 获取服务信息
     * @author: aoliao
     * @param: product
     * @updateTime: 2019/11/11 10:35
     * @return: java.util.Map<java.lang.String,java.lang.Integer>
     */
    public Map<String, String> getProductAttr(String productId) {
        Map<String,String> map = new HashMap<>();
        map.put("providername",getProviderName(productId));
        map.put("buysum",Integer.toString(getBuySum(productId)));
        return map;
    }

    @Override
    /**
     * 更新产品推荐状态
     * 0 不推荐 1 推荐
     * @author: aoliao
     * @param: productId
     * @updateTime: 2019/11/12 12:14
     * @return: boolean
     */
    public boolean setRecommendStatus(String productId,int status) {
        ProviderProduct product = providerProductMapper.selectByPrimaryKey(productId);
        product.setRecommend(status);
        return providerProductMapper.updateByPrimaryKey(product)!=0;
    }

    @Override
    /**
     * 获取推荐服务
     * @author: aoliao
     * @updateTime: 2019/11/14 22:06
     * @return: java.util.Map<java.lang.String,java.lang.String>
     */
    public Map<String, String> getPopularProduct() {
        Map<String,String> map = new HashMap<>();
        ProviderProductExample productExample = new ProviderProductExample();
        ProviderProductExample.Criteria criteria= productExample.createCriteria();
        criteria.andStatusEqualTo(2);
        criteria.andRecommendEqualTo(1);
        List<ProviderProduct> productList = providerProductMapper.selectByExample(productExample);
        for (ProviderProduct product:productList){
            map.put(product.getServiceName(),product.getProductId());
        }
        return map;
    }

    /**
     * 获取该服务的服务商
     * @author: aoliao
     * @param: productId
     * @updateTime: 2019/11/11 10:02
     * @return: java.lang.String
     */
    public String getProviderName(String productId)
    {
        ProviderProduct product = providerProductMapper.selectByPrimaryKey(productId);
        return providerMapper.selectByPrimaryKey(product.getProviderId()).getName();
    }
    /**
     * 获取该服务订单数
     * @author: aoliao
     * @param: productId
     * @updateTime: 2019/11/11 10:38
     * @return: int
     */
    public int getBuySum(String productId)
    {
        ServiceOrderExample serviceOrderExample = new ServiceOrderExample();
        ServiceOrderExample.Criteria criteria = serviceOrderExample.createCriteria();
        criteria.andProductIdEqualTo(productId);
        int sum=0;
        for (ServiceOrder order:serviceOrderMapper.selectByExample(serviceOrderExample)){
            sum+=order.getBuyNum();
        }
        return sum;
    }

}

package com.xinda.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xinda.mapper.*;
import com.xinda.model.*;
import com.xinda.service.OrderService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service("orderService")
/**
 * @author: aoliao 
 * @updateTime: 2019/11/4 8:54
 */
public class OrderServiceImpl implements OrderService {
    @Resource
    ServiceOrderMapper serviceOrderMapper;
    @Resource
    ServiceOrderProgressMapper serviceOrderProgressMapper;
    @Resource
    ServiceJudgeMapper serviceJudgeMapper;
    @Resource
    ProviderProductMapper productMapper;
    @Resource
    ProviderMapper providerMapper;
    @Resource
    MemberMapper memberMapper;

    @Override
    /**
     * 增加服务订单
     * @author: aoliao
     * @param: serviceOrder
     * @updateTime: 2019/11/4 8:51
     * @return: boolean
     */
    public boolean addServiceOrder(ServiceOrder serviceOrder) {
        return serviceOrderMapper.insert(serviceOrder)!=0;
    }

    @Override
    /**
     * 删除服务订单
     * @author: aoliao
     * @param: serviceOrderId
     * @updateTime: 2019/11/4 8:51
     * @return: boolean
     */
    public boolean deleteServiceOrder(String serviceOrderId) {

        return serviceOrderMapper.deleteByPrimaryKey(serviceOrderId)!=0;
    }

    @Override
    /**
     * 获取订单
     * @author: aoliao
     * @param: serviceOrderId
     * @updateTime: 2019/11/7 14:07
     * @return: com.xinda.model.ServiceOrder
     */
    public ServiceOrder getServiceOrder(String serviceOrderId) {
        return serviceOrderMapper.selectByPrimaryKey(serviceOrderId);
    }

    @Override
    /**
     * 增加服务进度
     * @author: aoliao
     * @param: serviceOrderProgress
     * @updateTime: 2019/11/4 8:51
     * @return: boolean
     */
    public boolean addServiceTime(ServiceOrderProgress serviceOrderProgress) {
        return serviceOrderProgressMapper.insert(serviceOrderProgress)!=0;
    }

    @Override
    /**
     * 获取服务进度
     * @author: aoliao
     * @param: serviceId
     * @updateTime: 2019/11/4 8:52
     * @return: com.xinda.model.ServiceOrderProgress
     */
    public ServiceOrderProgress getServiceProgress(String serviceId) {
        return serviceOrderProgressMapper.selectByPrimaryKey(serviceId);
    }

    @Override
    /**
     * 更新服务过程
     * @author: aoliao
     * @param: progress
     * @param: id
     * @updateTime: 2019/11/12 17:45
     * @return: boolean
     */
    public boolean setServiceOrderProgress(ServiceOrderProgress progress, String id) {

        ServiceOrderProgressExample progressExample = new ServiceOrderProgressExample();
        ServiceOrderProgressExample.Criteria criteria = progressExample.createCriteria();
        criteria.andIdEqualTo(id);
        return serviceOrderProgressMapper.updateByExample(progress,progressExample)!=0;
    }

    @Override
    /**
     * 获取订单列表
     * @author: aoliao
     * @updateTime: 2019/11/6 15:59
     * @return: com.github.pagehelper.PageInfo
     */
    public PageInfo getOrderList(int pageNum, int pageSize, String memberId, String word, Date startDate,Date endDate) {

        ServiceOrderExample serviceOrderExample = new ServiceOrderExample();
        ServiceOrderExample.Criteria criteria = serviceOrderExample.createCriteria();
        criteria.andMemberIdEqualTo(memberId);

        //模糊查询
        if (word!=null&&!"".equals(word)&&!"null".equals(word)){
            criteria.andServiceNameLike("%"+word+"%");
        }

        if (startDate!=null&&endDate!=null){
            criteria.andCreateTimeBetween(startDate,endDate);
        }

        //从第pageNum页开始，每页显示pageSize条记录
        PageHelper.startPage(pageNum, pageSize);
        List<ServiceOrder> orderList = serviceOrderMapper.selectByExample(serviceOrderExample);

        return new PageInfo(orderList);
    }

    @Override
    /**
     * 获取订单列表
     * @author: aoliao
     * @param: pageNum
     * @param: pageSize
     * @param: word
     * @updateTime: 2019/11/8 15:48
     * @return: com.github.pagehelper.PageInfo
     */
    public PageInfo getOrderList(int pageNum, int pageSize, String word,String providerId,int status) {
        ServiceOrderExample serviceOrderExample = new ServiceOrderExample();
        ServiceOrderExample.Criteria criteria = serviceOrderExample.createCriteria();
        criteria.andProviderIdEqualTo(providerId);
        criteria.andStatusEqualTo(status);

        //模糊查询
        if (word!=null&&!"".equals(word)&&!"null".equals(word)){
            criteria.andServiceNameLike("%"+word+"%");
        }
        //从第pageNum页开始，每页显示pageSize条记录
        PageHelper.startPage(pageNum, pageSize);
        List<ServiceOrder> orderList = serviceOrderMapper.selectByExample(serviceOrderExample);
        return new PageInfo(orderList);
    }

    @Override
    /**
     * 获取服务商费用百分比
     * @author: aoliao
     * @param: providerId
     * @updateTime: 2019/11/14 10:28
     * @return: java.util.Map<java.lang.String,java.lang.Float>
     */
    public Map<String, Float> getProviderExpenses(String providerId) {

        //申请服务列表
        ServiceOrderExample serviceOrderExample1 = new ServiceOrderExample();
        ServiceOrderExample.Criteria criteria1 = serviceOrderExample1.createCriteria();
        criteria1.andProviderIdEqualTo(providerId);
        criteria1.andStatusEqualTo(2);
        List<ServiceOrder> orderList1 = serviceOrderMapper.selectByExample(serviceOrderExample1);

        //同意服务列表
        ServiceOrderExample serviceOrderExample2 = new ServiceOrderExample();
        ServiceOrderExample.Criteria criteria2 = serviceOrderExample2.createCriteria();
        criteria2.andProviderIdEqualTo(providerId);
        criteria2.andStatusEqualTo(3);
        List<ServiceOrder> orderList2 = serviceOrderMapper.selectByExample(serviceOrderExample2);

        //总费用
        float sumprice=0;
        //申请服务费用
        float applicationSum=0;
        //同意费用
        float agreeSum=0;

        //申请服务费用
        for (ServiceOrder order:orderList1){
            applicationSum+=order.getTotalPrice();
        }
        //同意服务费用
        for (ServiceOrder order:orderList2){
            agreeSum+=order.getTotalPrice();
        }
        //平台抽取费用为同意服务的0.001
        sumprice= (float) (agreeSum+applicationSum+agreeSum*0.001);

        DecimalFormat format = new DecimalFormat("#.0000");

        Map<String,Float> map = new HashMap<>();
        String scaled = format.format(agreeSum/sumprice);
        map.put("agree", Float.parseFloat(scaled));
        scaled = format.format(applicationSum/sumprice);
        map.put("application",Float.parseFloat(scaled));
        scaled = format.format(agreeSum*0.001/sumprice);
        map.put("platform", Float.parseFloat(scaled));
        map.put("agreesum",agreeSum);
        map.put("applicationsum",applicationSum);
        map.put("platformsum", (float) (agreeSum*0.001));
        return map;
    }

    @Override
    /**
     * @author: aoliao
     * @param: pageNum
     * @param: pageSize
     * @param: word
     * @param: style
     * @updateTime: 2019/11/7 11:42
     * @return: com.github.pagehelper.PageInfo
     */
    public PageInfo getOrderList(int pageNum, int pageSize, String word,String style,String memberId) {

        ServiceOrderExample serviceOrderExample = new ServiceOrderExample();
        ServiceOrderExample.Criteria criteria = serviceOrderExample.createCriteria();
        criteria.andIdIsNotNull();
        //判断用户id值是否为空
        if (memberId!=null&&!"".equals(memberId)&&!"null".equals(memberId)){
            criteria.andMemberIdEqualTo(memberId);
        }

        if (word!=null&&!"".equals(word)&&!"null".equals(word)){
            criteria.andIdEqualTo(word);
        }
        if (style!=null&&!"".equals(style)&&!"null".equals(style)){

            ProviderProductExample productExample = new ProviderProductExample();
            ProviderProductExample.Criteria criteria1 = productExample.createCriteria();
            criteria1.andStyleIdEqualTo(style);
            List<ProviderProduct> productList = productMapper.selectByExample(productExample);

            List <String> productIds=new ArrayList<>();

            for (ProviderProduct product:productList){
                productIds.add(product.getProductId());
            }

            if (productIds.size()!=0){
                criteria.andProductIdIn(productIds);
            }else {
                criteria.andProductIdIsNull();
            }

        }
        //从第pageNum页开始，每页显示pageSize条记录
        PageHelper.startPage(pageNum, pageSize);
        List<ServiceOrder> orderList = serviceOrderMapper.selectByExample(serviceOrderExample);
        return new PageInfo(orderList);
    }

    @Override
    /**
     * 添加评价
     * @author: aoliao
     * @param: serviceJudge
     * @updateTime: 2019/11/7 13:42
     * @return: boolean
     */
    public boolean addJudge(ServiceJudge serviceJudge) {
        return serviceJudgeMapper.insert(serviceJudge)!=0;
    }

    @Override
    /**
     * @author: aoliao
     * @param: orderId
     * @updateTime: 2019/11/7 18:52
     * @return: com.xinda.model.ServiceJudge
     * 通过服务id获取评价
     */
    public ServiceJudge getJudge(String orderId) {
        ServiceJudgeExample serviceJudge = new ServiceJudgeExample();
        ServiceJudgeExample.Criteria criteria = serviceJudge.createCriteria();

        criteria.andOrderIdEqualTo(orderId);
        return serviceJudgeMapper.selectByExample(serviceJudge).get(0);
    }

    @Override
    /**
     * 获取没有评论的订单
     * @author: aoliao
     * @param: pageNum
     * @param: pageSize
     * @param: memberId
     * @updateTime: 2019/11/7 19:04
     * @return: com.github.pagehelper.PageInfo
     */
    public PageInfo getNocomment(int pageNum, int pageSize, String memberId){

        ServiceOrderExample serviceOrderExample = new ServiceOrderExample();
        ServiceOrderExample.Criteria criteria = serviceOrderExample.createCriteria();
        criteria.andMemberIdEqualTo(memberId);

        List<String> orderIds = getJudgeOrder(memberId);
        if (orderIds.size()!=0){
            criteria.andIdNotIn(orderIds);
        }

        //从第pageNum页开始，每页显示pageSize条记录
        PageHelper.startPage(pageNum, pageSize);
        List<ServiceOrder> orderList = serviceOrderMapper.selectByExample(serviceOrderExample);

        if(orderList!=null){
            return new PageInfo(orderList);
        }else {
            return null;
        }

    }
    @Override
    /**
     * 获取评论了的列表
     * @author: aoliao
     * @param: pageNum
     * @param: pageSize
     * @param: memberId
     * @updateTime: 2019/11/7 19:11
     * @return: com.github.pagehelper.PageInfo
     */
    public PageInfo getcomment(int pageNum, int pageSize, String memberId) {

        ServiceOrderExample serviceOrderExample = new ServiceOrderExample();
        ServiceOrderExample.Criteria criteria = serviceOrderExample.createCriteria();
        criteria.andMemberIdEqualTo(memberId);

        List<String> orderIds = getJudgeOrder(memberId);

        if (orderIds.size()!=0){
            criteria.andIdIn(orderIds);
        }else {
            return new PageInfo(orderIds);
        }


        //从第pageNum页开始，每页显示pageSize条记录
        PageHelper.startPage(pageNum, pageSize);
        List<ServiceOrder> orderList = serviceOrderMapper.selectByExample(serviceOrderExample);


        return new PageInfo(orderList);
    }
    @Override
    /**
     * @author: aoliao
     * @param: memberId
     * @updateTime: 2019/11/7 15:44
     * @return: java.util.List<java.lang.String>
     */
    public List<String> getJudgeOrder(String memberId) {
        ServiceJudgeExample serviceJudge = new ServiceJudgeExample();
        ServiceJudgeExample.Criteria criteria = serviceJudge.createCriteria();

        criteria.andMemberIdEqualTo(memberId);

        List<ServiceJudge> judges = serviceJudgeMapper.selectByExample(serviceJudge);

        List<String> orderids=new ArrayList<>();
        for (ServiceJudge judge:judges){
            orderids.add(judge.getOrderId());
        }
        return orderids;
    }

    @Override
    /**
     * 更新状态
     * @author: aoliao
     * @param: OrderId
     * @updateTime: 2019/11/8 21:01
     * @return: boolean
     */
    public boolean upServiceOrderStatus(String orderId,int status) {
        ServiceOrder serviceOrder = serviceOrderMapper.selectByPrimaryKey(orderId);
        serviceOrder.setStatus(status);

        ServiceOrderExample serviceOrderExample = new ServiceOrderExample();
        ServiceOrderExample.Criteria criteria = serviceOrderExample.createCriteria();
        criteria.andIdEqualTo(orderId);

        return serviceOrderMapper.updateByExample(serviceOrder,serviceOrderExample)!=0;
    }

    @Override
    /**
     * 获取某个时间段的付费订单
     * @author: aoliao
     * @param: pageNum
     * @param: pageSize
     * @param: date
     * @updateTime: 2019/11/11 10:42
     * @return: com.github.pagehelper.PageInfo
     */
    public PageInfo getPlayOrder(int pageNum, int pageSize, Date date) {

        ServiceOrderExample serviceOrderExample = new ServiceOrderExample();
        ServiceOrderExample.Criteria criteria = serviceOrderExample.createCriteria();
        criteria.andStatusEqualTo(3);
        Date date1 = new Date();
        criteria.andCreateTimeBetween(date,date1);

        //从第pageNum页开始，每页显示pageSize条记录
        PageHelper.startPage(pageNum, pageSize);
        List<ServiceOrder> orderList = serviceOrderMapper.selectByExample(serviceOrderExample);


        return new PageInfo(orderList);
    }

    @Override
    /**
     * 获取支付费用
     * @author: aoliao
     * @param: date
     * @updateTime: 2019/11/14 10:30
     * @return: java.util.Map<java.lang.String,java.lang.Long>
     */
    public Map<String, Long> getPlayTotal(Date date) {

        ServiceOrderExample serviceOrderExample = new ServiceOrderExample();
        ServiceOrderExample.Criteria criteria = serviceOrderExample.createCriteria();
        criteria.andStatusEqualTo(3);
        Date date1 = new Date();
        criteria.andCreateTimeBetween(date,date1);
        List<ServiceOrder> orderList = serviceOrderMapper.selectByExample(serviceOrderExample);
        long total=0;
        for (ServiceOrder order:orderList){
            total+=order.getTotalPrice();
        }
        Map<String,Long> map = new HashMap<>();
        map.put("total",total);
        return map;
    }

    @Override
    /**
     * 通过订单号获取其他信息
     * @author: aoliao
     * @param: orderId
     * @updateTime: 2019/11/11 22:36
     * @return: java.util.Map<java.lang.String,java.lang.String>
     */
    public Map<String, String> getOrderOtherAttr(String orderId) {
        Map<String,String> map = new HashMap<>();

        ServiceOrder serviceOrder =serviceOrderMapper.selectByPrimaryKey(orderId);

        MemberExample memberExample = new MemberExample();
        MemberExample.Criteria criteria = memberExample.createCriteria();
        criteria.andIdEqualTo(serviceOrder.getMemberId());
        Member member = memberMapper.selectByExample(memberExample).get(0);

        Provider provider = providerMapper.selectByPrimaryKey(serviceOrder.getProviderId());

        ProviderProduct product = productMapper.selectByPrimaryKey(serviceOrder.getProductId());

        ServiceOrderProgress progress = serviceOrderProgressMapper.selectByPrimaryKey(orderId);

        map.put("serviceName",product.getServiceName());
        map.put("serviceInfo",product.getServiceInfo());
        map.put("serviceText",product.getServiceContent());
        map.put("providerName",provider.getName());
        map.put("providerPhone",provider.getCellphone());
        map.put("memberphone",member.getCellphone());


        map.put("handletime","");
        try {
            Date date = progress.getHandleTime();
            SimpleDateFormat sdf =   new SimpleDateFormat( " yyyy-MM-dd" );
            String hantime = sdf.format(date);
            map.put("handletime",hantime);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }finally {
            return map;
        }

    }

    @Override
    /**
     * 获取该订单的服务图像
     * @author: aoliao
     * @param: orderId
     * @updateTime: 2019/11/14 21:30
     * @return: java.util.Map<java.lang.String,java.lang.String>
     */
    public Map<String, String> getOrderServiceImg(String orderId) {
        Map<String,String> map = new HashMap<>();
        ServiceOrder order = serviceOrderMapper.selectByPrimaryKey(orderId);
        ProviderProduct product = productMapper.selectByPrimaryKey(order.getProductId());
        map.put("serviceImg",product.getServiceImg());
        return map;
    }

}

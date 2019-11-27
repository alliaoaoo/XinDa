package com.xinda.service;

import com.github.pagehelper.PageInfo;
import com.xinda.model.ServiceJudge;
import com.xinda.model.ServiceOrder;
import com.xinda.model.ServiceOrderProgress;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface OrderService {

    /**消费者订单**/
    public boolean addServiceOrder(ServiceOrder serviceOrder);

    public boolean deleteServiceOrder(String serviceOrderId);

    public ServiceOrder getServiceOrder(String serviceOrderId);
    /**服务订单过程**/
    public boolean addServiceTime(ServiceOrderProgress serviceOrderProgress);

    public ServiceOrderProgress getServiceProgress(String serviceId);

    public boolean setServiceOrderProgress(ServiceOrderProgress progress,String id);

    public PageInfo  getOrderList(int pageNum, int pageSize, String memberId, String word, Date startDate, Date endDate);

    public PageInfo  getOrderList(int pageNum, int pageSize,  String word,String providerId,int status);

    public Map<String,Float> getProviderExpenses(String providerId);

    public PageInfo  getOrderList(int pageNum, int pageSize,  String word,String style,String memberId);

    public boolean addJudge(ServiceJudge serviceJudge);

    public ServiceJudge getJudge(String orderId);

    public PageInfo getNocomment(int pageNum, int pageSize, String memberId);

    public PageInfo getcomment(int pageNum, int pageSize, String memberId);

    public List<String> getJudgeOrder(String memberId);

    public boolean upServiceOrderStatus(String orderId,int status);

    public PageInfo getPlayOrder(int pageNum, int pageSize,Date date);

    public Map<String,Long> getPlayTotal(Date date);

    public Map<String,String> getOrderOtherAttr(String orderId);

    public Map<String,String> getOrderServiceImg(String orderId);
}

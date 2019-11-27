package com.xinda.service.impl;

import com.xinda.mapper.NewOrderMessageMapper;
import com.xinda.mapper.ProviderMessageMapper;
import com.xinda.model.NewOrderMessage;
import com.xinda.model.NewOrderMessageExample;
import com.xinda.model.ProviderMessage;
import com.xinda.model.ProviderMessageExample;
import com.xinda.service.MessageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("messageservice")
public class MessageServiceImpl implements MessageService {
    @Resource
    NewOrderMessageMapper orderMessageMapper;
    @Resource
    ProviderMessageMapper providerMessageMapper;

    @Override
    /**
     * 增加新订单消息
     * @author: aoliao
     * @param: message
     * @updateTime: 2019/11/13 14:11
     * @return: boolean
     */
    public boolean addOrderMessage(NewOrderMessage message) {
        return orderMessageMapper.insert(message)!=0;
    }

    @Override
    /**
     * 删除新订单消息
     * @author: aoliao
     * @param: orderId
     * @updateTime: 2019/11/13 14:11
     * @return: boolean
     */
    public boolean deleteOrderMessage(String orderId) {
        return orderMessageMapper.deleteByPrimaryKey(orderId)!=0;
    }

    @Override
    /**
     * 获取服务商新订单消息
     * @author: aoliao
     * @param: providerId
     * @updateTime: 2019/11/13 14:26
     * @return: java.util.List<com.xinda.model.NewOrderMessage>
     */
    public List<NewOrderMessage> getNewOrderMessageList(String providerId) {
        NewOrderMessageExample messageExample = new NewOrderMessageExample();
        NewOrderMessageExample.Criteria criteria = messageExample.createCriteria();
        criteria.andProviderIdEqualTo(providerId);
        return orderMessageMapper.selectByExample(messageExample);
    }

    @Override
    /**
     * 获取消息数量
     * @author: aoliao
     * @param: providerId
     * @updateTime: 2019/11/13 14:32
     * @return: int
     */
    public int getNewOrderSum(String providerId) {
        NewOrderMessageExample messageExample = new NewOrderMessageExample();
        NewOrderMessageExample.Criteria criteria = messageExample.createCriteria();
        criteria.andProviderIdEqualTo(providerId);
        return orderMessageMapper.selectByExample(messageExample).size();
    }

    @Override
    /**
     * 增加服务商消息列表
     * @author: aoliao
     * @param: message
     * @updateTime: 2019/11/13 14:11
     * @return: boolean
     */
    public boolean addProviderMessage(ProviderMessage message) {
        return providerMessageMapper.insert(message)!=0;
    }

    @Override
    /**
     * 删除服务商消息列表
     * @author: aoliao
     * @param: messageId
     * @updateTime: 2019/11/13 14:11
     * @return: boolean
     */
    public boolean deleteProviderMessage(String messageId) {
        return providerMessageMapper.deleteByPrimaryKey(messageId)!=0;
    }

    @Override
    /**
     * 获取服务商消息列表
     * @author: aoliao
     * @param: providerId
     * @updateTime: 2019/11/13 14:27
     * @return: java.util.List<com.xinda.model.ProviderMessage>
     */
    public List<ProviderMessage> getProviderMessageList(String providerId) {
        ProviderMessageExample messageExample = new ProviderMessageExample();
        ProviderMessageExample.Criteria criteria = messageExample.createCriteria();
        criteria.andProviderIdEqualTo(providerId);
        return providerMessageMapper.selectByExample(messageExample);
    }

    @Override
    /**
     * 消息数量
     * @author: aoliao
     * @param: providerId
     * @updateTime: 2019/11/13 14:33
     * @return: int
     */
    public int getProviderMessageSum(String providerId) {
        ProviderMessageExample messageExample = new ProviderMessageExample();
        ProviderMessageExample.Criteria criteria = messageExample.createCriteria();
        criteria.andProviderIdEqualTo(providerId);
        return providerMessageMapper.selectByExample(messageExample).size();
    }
}

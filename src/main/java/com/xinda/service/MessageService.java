package com.xinda.service;

import com.xinda.model.NewOrderMessage;
import com.xinda.model.ProviderMessage;

import java.util.List;

/**
 * 服务商消息
 * @author: aoliao
 * @updateTime: 2019/11/13 14:10
 */
public interface MessageService {

    public boolean addOrderMessage(NewOrderMessage message);

    public boolean deleteOrderMessage(String orderId);

    public List<NewOrderMessage> getNewOrderMessageList(String providerId);

    public int getNewOrderSum(String providerId);

    public boolean addProviderMessage(ProviderMessage message);

    public boolean deleteProviderMessage(String messageId);

    public List<ProviderMessage> getProviderMessageList(String providerId);

    public int getProviderMessageSum (String providerId);

}

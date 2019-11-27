package com.xinda.service;

import com.github.pagehelper.PageInfo;
import com.xinda.model.Provider;

/**
 * @author: aoliao 
 * @updateTime: 2019/10/26 18:36
 */
public interface ProviderService {

    public boolean registProvider(Provider provider);

    public Provider loginProvider(String cellphone,String password);

    public Provider getProvider(String providerId);

    public boolean setPassword(String cellphone,String password);

    public boolean updateProvider(Provider provider,String providerId);

    public boolean existProvider(String cellphone,String password);

    public boolean existCellphone(String cellphone);

    public boolean existProvider(String providerId);

    public PageInfo providerShop(int pageNum, int pageSize,String word);

    public PageInfo providerList(int pageNum, int pageSize,String word,int status);

    public boolean setProviderStatus(String providerId,int status);
}

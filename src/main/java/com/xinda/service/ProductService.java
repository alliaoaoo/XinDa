package com.xinda.service;

import com.github.pagehelper.PageInfo;
import com.xinda.model.ProductStyleKey;
import com.xinda.model.ProductTypeKey;
import com.xinda.model.ProviderProduct;

import java.util.List;
import java.util.Map;

/**
 * 服务产品接口
 * @author aliao
 */
public interface ProductService {

    public int insertProduct(ProviderProduct product);

    public List<ProductStyleKey> getProductStyle();

    public List<ProductTypeKey> getProductType();

    public PageInfo productList(int pageNum, int pageSize,String providerId,String style,String type,String word);

    public int deleteProduct(String productID);

    public ProviderProduct getProduct(String productID);

    public boolean upProduct(ProviderProduct product, String productId);

    public PageInfo productAdminList(int pageNum, int pageSize,String style,String type,String word);

    public PageInfo productAdminList(int pageNum, int pageSize,String style,String type,String word,int status);

    public boolean setStatusStopProduct(String providerId);

    public Map<String,String> getProductAttr(String productId);

    public boolean setRecommendStatus(String productId,int status);

    public Map<String,String> getPopularProduct();
}
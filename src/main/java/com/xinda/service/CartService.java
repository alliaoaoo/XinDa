package com.xinda.service;

import com.github.pagehelper.PageInfo;
import com.xinda.model.Cart;

public interface CartService {
    public boolean addCart(Cart cart);

    public boolean lessCart(String cartId,int num);

    public boolean plusCart(String cartId,int num);

    public boolean deleteCart(String cartId);

    public boolean exitCart(String productId,String memberId);

    public Cart getCart(String productId,String memberId);

    public boolean setBuyNum(String cartId,int num);

    public PageInfo getCartList(int pageNum, int pageSize,String memberId,String style,String word);

    public Cart getCart(String cartId);

    public int getCartSum(String memberId);
}

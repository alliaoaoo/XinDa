package com.xinda.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xinda.mapper.CartMapper;
import com.xinda.model.Cart;
import com.xinda.model.CartExample;
import com.xinda.model.ProviderProduct;
import com.xinda.service.CartService;
import com.xinda.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("cartService")
public class CartServiceImpl implements CartService {
    @Resource
    CartMapper cartMapper;
    @Autowired
    ProductService productService;

    @Override
    /**
     * 创建新的购物车数据
     * @author: aoliao
     * @param: cart
     * @updateTime: 2019/11/4 9:37
     * @return: boolean
     */
    public boolean addCart(Cart cart) {
        return cartMapper.insert(cart)!=0;
    }

    @Override
    /**
     * 减少购物车数量
     * @author: aoliao
     * @param: cartId
     * @param: num
     * @updateTime: 2019/11/4 9:37
     * @return: boolean
     */
    public boolean lessCart(String cartId, int num) {
        CartExample cartExample = new CartExample();
        CartExample.Criteria criteria = cartExample.createCriteria();
        criteria.andIdEqualTo(cartId);

        Cart cart = cartMapper.selectByPrimaryKey(cartId);
        int buynum=cart.getBuyNum();
        if (buynum==1){
            return false;
        }
        cart.setBuyNum(buynum-num);
        cart.setTotalPrice(cart.getBuyNum()*cart.getUnitPrice());
        return cartMapper.updateByExample(cart,cartExample)!=0;
    }

    @Override
    /**
     * 增加购物车数量
     * @author: aoliao
     * @param: cartId
     * @param: num
     * @updateTime: 2019/11/4 9:38
     * @return: boolean
     */
    public boolean plusCart(String cartId, int num) {
        CartExample cartExample = new CartExample();
        CartExample.Criteria criteria = cartExample.createCriteria();
        criteria.andIdEqualTo(cartId);

        Cart cart = cartMapper.selectByPrimaryKey(cartId);
        int buynum=cart.getBuyNum();
        cart.setBuyNum(buynum+num);
        cart.setTotalPrice(cart.getUnitPrice()*cart.getBuyNum());
        return cartMapper.updateByExample(cart,cartExample)!=0;
    }

    @Override
    /**
     * 删除购物车数据对象
     * @author: aoliao
     * @updateTime: 2019/11/4 9:38
     */
    public boolean deleteCart(String cartId) {
        return cartMapper.deleteByPrimaryKey(cartId)!=0;
    }

    @Override
    /**
     * 判断数据表中是否已经存在该服务
     * @author: aoliao
     * @param: cartId
     * @param: memberId
     * @updateTime: 2019/11/4 10:33
     * @return: boolean
     */
    public boolean exitCart(String productId, String memberId) {
        CartExample cartExample = new CartExample();
        CartExample.Criteria criteria = cartExample.createCriteria();
        criteria.andProductIdEqualTo(productId).andMemberIdEqualTo(memberId);
        return cartMapper.selectByExample(cartExample).size()!=0;
    }

    @Override
    /**
     * 通过服务ID和会员ID获取购物车信息
     * @author: aoliao
     * @param: productId
     * @param: memberId
     * @updateTime: 2019/11/4 10:40
     * @return: com.xinda.model.Cart
     */
    public Cart getCart(String productId, String memberId) {
        CartExample cartExample = new CartExample();
        CartExample.Criteria criteria = cartExample.createCriteria();
        criteria.andProductIdEqualTo(productId).andMemberIdEqualTo(memberId);
        return cartMapper.selectByExample(cartExample).get(0);
    }

    @Override
    /**
     * 修改购物车数量
     * @author: aoliao 
     * @param: cartId
     * @param: num
     * @updateTime: 2019/11/4 11:15  
     * @return: boolean
     */
    public boolean setBuyNum(String cartId, int num) {
        CartExample cartExample = new CartExample();
        CartExample.Criteria criteria = cartExample.createCriteria();
        criteria.andIdEqualTo(cartId);

        Cart cart = cartMapper.selectByPrimaryKey(cartId);
        cart.setBuyNum(num);
        cart.setTotalPrice(cart.getUnitPrice()*cart.getBuyNum());
        return cartMapper.updateByExample(cart,cartExample)!=0;
    }

    @Override
    /**
     * 获取购物车列表
     * @author: aoliao
     * @param: pageNum
     * @param: pageSize
     * @param: memberId
     * @param: style
     * @param: word
     * @updateTime: 2019/11/4 16:18
     * @return: com.github.pagehelper.PageInfo
     */
    public PageInfo getCartList(int pageNum, int pageSize, String memberId, String style, String word) {
        CartExample cartExample = new CartExample();
        CartExample.Criteria criteria = cartExample.createCriteria();
        criteria.andMemberIdEqualTo(memberId);
        criteria.andIdIsNotNull();

        //模糊查询
        if (word!=null&&!"".equals(word)&&!"null".equals(word)){
            criteria.andServiceNameLike("%"+word+"%");
        }
        //从第pageNum页开始，每页显示pageSize条记录
        PageHelper.startPage(pageNum, pageSize);
        List<Cart> cartList = cartMapper.selectByExample(cartExample);
        //分类
        if (style!=null&&!"".equals(style)&&!"null".equals(style)){

            for (int i=0;i<cartList.size();i++){
                Cart cart = cartList.get(i);
                ProviderProduct product = productService.getProduct(cart.getProductId());
                if (product.getStyleId().equals(style)){
                    cartList.remove(cart);
                }
            }
        }

        return new PageInfo(cartList);
    }

    @Override
    /**
     * 通过ID获取cart对象
     * @author: aoliao
     * @param: cartId
     * @updateTime: 2019/11/4 22:02
     * @return: com.xinda.model.Cart
     */
    public Cart getCart(String cartId) {
        return cartMapper.selectByPrimaryKey(cartId);
    }

    @Override
    /**
     * @author: aoliao
     * @param: memberId
     * @updateTime: 2019/11/8 14:48
     * @return: int
     */
    public int getCartSum(String memberId) {
        CartExample cartExample = new CartExample();
        CartExample.Criteria criteria = cartExample.createCriteria();
        criteria.andMemberIdEqualTo(memberId);
        return cartMapper.selectByExample(cartExample).size();
    }
}

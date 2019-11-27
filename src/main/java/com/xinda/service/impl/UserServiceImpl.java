package com.xinda.service.impl;

import com.xinda.mapper.UserMapper;
import com.xinda.model.User;
import com.xinda.model.UserExample;
import com.xinda.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 管理员账户相关操作
 * @author aoliao
 */
@Service("userService")
public class UserServiceImpl implements UserService {
    @Resource
    UserMapper userMapper;

    @Override
    /**
     * 登录
     * @author: aoliao 
     * @param: cellphone
     * @param: password
     * @updateTime: 2019/11/9 12:56  
     * @return: com.xinda.model.User
     */
    public User loginUser(String cellphone, String password) {

        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andCellphoneEqualTo(cellphone);
        criteria.andPasswordEqualTo(password);

        return userMapper.selectByExample(userExample).get(0);
    }

    @Override
    /**
     * 获取用户
     * @author: aoliao 
     * @param: orgId
     * @updateTime: 2019/11/9 12:56  
     * @return: com.xinda.model.User
     */
    public User getUser(String orgId) {

        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andOrgIdEqualTo(orgId);

        return userMapper.selectByExample(userExample).get(0);
    }

    @Override
    /**
     * 重置密码并更新id
     * @author: aoliao
     * @param: cellphone
     * @param: password
     * @param: orgId
     * @updateTime: 2019/11/9 16:11
     * @return: boolean
     */
    public boolean setPassword(String cellphone, String password,String orgId) {

        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andCellphoneEqualTo(cellphone);
        User user = userMapper.selectByExample(userExample).get(0);
        user.setPassword(password);
        user.setOrgId(orgId);
        return userMapper.updateByExampleSelective(user,userExample)!=0;
    }

    @Override
    /**
     * 更新用户信息
     * @author: aoliao
     * @param: user
     * @param: orgId
     * @updateTime: 2019/11/9 13:08
     * @return: boolean
     */
    public boolean updateUser(User user, String orgId) {

        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andOrgIdEqualTo(orgId);
        return userMapper.updateByExampleSelective(user,userExample)!=0;
    }

    @Override
    /**
     * 判断用户是否存在
     * @author: aoliao
     * @param: cellphone
     * @param: password
     * @updateTime: 2019/11/9 13:19
     * @return: boolean
     */
    public boolean existUser(String cellphone, String password) {
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andPasswordEqualTo(password);
        criteria.andCellphoneEqualTo(cellphone);
        return userMapper.selectByExample(userExample).size()!=0;
    }

    @Override
    /**
     * 判断号码是否存在
     * @author: aoliao
     * @param: cellphone
     * @updateTime: 2019/11/9 15:19
     * @return: boolean
     */
    public boolean existCellphone(String cellphone) {
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andCellphoneEqualTo(cellphone);
        return userMapper.selectByExample(userExample).size()!=0;
    }

    @Override
    /**
     * 通过id判断是否存在
     * @author: aoliao
     * @param: orgId
     * @updateTime: 2019/11/9 15:19
     * @return: boolean
     */
    public boolean existUser(String orgId) {
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andOrgIdEqualTo(orgId);
        return userMapper.selectByExample(userExample).size()!=0;
    }
}

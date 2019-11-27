package com.xinda.service;

import com.xinda.model.User;

/**
 * 管理员
 * @author aoliao
 */
public interface UserService {

    public User loginUser(String cellphone,String password);

    public User getUser(String orgId);

    public boolean setPassword(String cellphone,String password,String orgId);

    public boolean updateUser(User user,String orgId);

    public boolean existUser(String cellphone,String password);

    public boolean existCellphone(String cellphone);

    public boolean existUser(String orgId);

}

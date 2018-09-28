package com.tarena.service;

import com.tarena.entity.User;
import com.tarena.vo.Page;
import com.tarena.vo.Result;

public interface UserService {
    public Result login(String username,String password);
    public Result findUsersByPage(Page page);
}

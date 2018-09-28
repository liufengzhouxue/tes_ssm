package com.tarena.dao;

import com.tarena.entity.User;
import com.tarena.vo.Page;

import java.util.List;

public interface UserMapper {
    public String login(User user);
    public int getCount(Page page);
    public List<User> findUsersByPage(Page page);
}

package com.tarena.service.impl;

import com.tarena.dao.UserMapper;
import com.tarena.entity.User;
import com.tarena.service.UserService;
import com.tarena.util.PageUtil;
import com.tarena.vo.Page;
import com.tarena.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PageUtil pageUtil;
    @Override
    public Result login(String username, String password) {
        Result result=new Result();
        User user=new User();
        user.setLoginName(username);
        user.setPassword(password);
        String userId=userMapper.login(user);
        if(userId!=null){
            //说明登录成功
            result.setStatus(1);
            result.setMessage("登录成功");
        }else{
            result.setStatus(0);
            result.setMessage("登录失败");
        }
        return result;
    }

    @Override
    public Result findUsersByPage(Page page) {
        Result result=new Result();
        //设置每页条数
        page.setPageSize(pageUtil.getPageSize());
        String userKw=page.getUserKeyword().equals("undefind")?"%%":"%"+page.getUserKeyword()+"%";
        page.setUserKeyword(userKw);
        //获取总记录数
        int totalCount=userMapper.getCount(page);
        page.setTotalCount(totalCount);
        //计算总页数
        int totalPage=totalCount%page.getPageSize()==0?totalCount/page.getPageSize():totalCount/page.getPageSize()+1;
        page.setTotalPage(totalPage);
        //计算前一页
        if(page.getCurrentPage()==1){
            page.setPreviousPage(1);
        }else{
            page.setPreviousPage(page.getCurrentPage()-1);
        }
        //计算后一页
        if(page.getCurrentPage()==totalPage){
            page.setNextPage(totalPage);
        }else{
            page.setNextPage(page.getCurrentPage()+1);
        }
        //获取当前页的数据
        List<User> users=userMapper.findUsersByPage(page);
        page.setData(users);
        //计算分页组件条上有多少个超链接
        page.setaNum(pageUtil.getFenYe_a_Num(page.getCurrentPage(),page.getPageSize(),totalCount,totalPage));
        System.out.println(page);
        result.setStatus(1);
        result.setData(page);
        return result;
    }
}

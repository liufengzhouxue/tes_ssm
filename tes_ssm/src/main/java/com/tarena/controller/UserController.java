package com.tarena.controller;

import com.tarena.service.UserService;
import com.tarena.vo.Page;
import com.tarena.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @RequestMapping("/login")
    public Result login(String username, String password, HttpSession session){
       Result result=userService.login(username,password);
       if(result.getStatus()==1){
           session.setAttribute("loginName",username);
       }
       return result;
    }
    @RequestMapping("/findUsersByPage")
    public Result findUsersByPage(Page page){
        Result result=userService.findUsersByPage(page);
        return  result;
    }
}

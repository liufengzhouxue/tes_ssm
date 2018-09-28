package com.tarena.controller;

import com.tarena.vo.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/main")
public class MainController {
    @RequestMapping("/logout")
    public Result logout(HttpSession session){
        Result result=new Result();
        session.invalidate();
        result.setStatus(1);
        return  result;
    }
}

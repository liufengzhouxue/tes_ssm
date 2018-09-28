package com.tarena.controller;

import com.tarena.entity.Role;
import com.tarena.service.RoleService;
import com.tarena.vo.Page;
import com.tarena.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private RoleService roleService;
    @RequestMapping("/findRolesByPage")
    public Result findRolesByPage(Page page){
        Result result=null;
        result=roleService.findRolesByPage(page);
        return result;
    }
    @RequestMapping("/addRole")
    public Result addRole(@RequestParam(name = "roleName") String roleName){
        Result result=roleService.addRole(roleName);
        return result;
    }
    @RequestMapping("/editRole")
    public Result editRole(Role role){
        Result result=roleService.editRole(role);
        return result;
    }
    @RequestMapping("/deleteRole/{roleId}")
    public Result deleteRole(@PathVariable  String roleId){
        Result result=roleService.deleteRole(roleId);
        return  result;
    }
}

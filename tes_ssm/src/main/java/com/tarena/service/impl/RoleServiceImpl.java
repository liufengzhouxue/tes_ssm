package com.tarena.service.impl;

import com.tarena.dao.RoleMapper;
import com.tarena.entity.Role;
import com.tarena.service.RoleService;
import com.tarena.util.PageUtil;
import com.tarena.util.UUIDUtil;
import com.tarena.vo.Page;
import com.tarena.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private PageUtil pageUtil;
    @Override
    public Result findRolesByPage(Page page) {
        Result result=new Result();
        //处理关键字
        String roleKw=page.getRoleKeyword().equals("undefind")?"%%":"%"+page.getRoleKeyword()+"%";
        page.setRoleKeyword(roleKw);
        //设置每页显示的条数
        page.setPageSize(pageUtil.getPageSize());
        //查询总记录数
        int totalCount=roleMapper.getCount(page);
        page.setTotalCount(totalCount);
        //计算总页数
        int totalPage=totalCount%page.getPageSize()==0?totalCount/pageUtil.getPageSize():totalCount/pageUtil.getPageSize()+1;
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
        //获取当前页数据
        List<Role> roles=roleMapper.getRolesByPage(page);
        page.setData(roles);
        //获取分页组件超链接的个数
        page.setaNum(pageUtil.getFenYe_a_Num(page.getCurrentPage(),page.getPageSize(),totalCount,totalPage));

        result.setStatus(1);
        result.setData(page);
        return result;
    }

    @Override
    public Result addRole(String roleName) {
        Result result=new Result();
        String  id= UUIDUtil.getUUID();
        Role role=new Role();
        role.setId(id);
        role.setName(roleName);
        try {
            roleMapper.addRole(role);
            result.setStatus(1);
            result.setMessage("新增角色成功");
        }catch (Exception e){
            result.setStatus(0);
            result.setMessage("新增角色失败");
        }


        return result;
    }

    @Override
    public Result editRole(Role role) {
        Result result=new Result();
        try{
            roleMapper.editRole(role);
            result.setStatus(1);
            result.setMessage("修改角色成功");
        }catch (Exception e){
            result.setStatus(0);
            result.setMessage("修改角色失败");
        }

        return result;
    }

    @Override
    public Result deleteRole(String roleId) {
        Result result=new Result();
        try{
            roleMapper.deleteRole(roleId);
            result.setStatus(1);
            result.setMessage("删除成功");
        }catch(Exception e){
            result.setStatus(0);
            result.setMessage("删除失败");
        }
        return result;
    }
}

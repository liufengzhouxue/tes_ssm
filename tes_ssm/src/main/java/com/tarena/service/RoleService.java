package com.tarena.service;

import com.tarena.entity.Role;
import com.tarena.vo.Page;
import com.tarena.vo.Result;

public interface RoleService {
    public Result findRolesByPage(Page page);
    public Result addRole(String roleName);
    public Result editRole(Role role);
    public Result deleteRole(String roleId);
}

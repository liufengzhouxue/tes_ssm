package com.tarena.dao;

import com.tarena.entity.Role;
import com.tarena.vo.Page;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface RoleMapper {
    public int getCount(Page page);
    public List<Role> getRolesByPage(Page page);
    @Insert("insert into t_role(role_id,role_name) values(#{id},#{name})")
    public void addRole(Role role);
    @Update("update t_role set role_name=#{name} where role_id=#{id}")
    public void editRole(Role role);
    @Delete("delete from t_role where role_id=#{roleId}")
    public  void deleteRole(String roleId);
}

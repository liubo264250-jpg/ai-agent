package com.liubo.infrastructure.dao;

import com.liubo.infrastructure.dao.po.AdminUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 管理员用户表 DAO
 *
 * 表名: admin_user
 */
@Mapper
public interface IAdminUserDao {

    int insert(AdminUser record);

    /**
     * 根据主键ID查询管理员用户
     */
    AdminUser queryById(@Param("id") Long id);

    int updateById(AdminUser record);

    int deleteById(@Param("id") Long id);

    /**
     * 根据用户ID更新管理员用户
     */
    int updateByUserId(AdminUser record);

    /**
     * 根据用户ID删除管理员用户
     */
    int deleteByUserId(@Param("userId") String userId);

    /**
     * 根据用户ID查询管理员用户
     */
    AdminUser queryByUserId(@Param("userId") String userId);

    /**
     * 根据用户名查询管理员用户
     */
    AdminUser queryByUsername(@Param("username") String username);

    /**
     * 根据用户名和密码查询管理员用户（登录校验）
     */
    AdminUser queryByUsernameAndPassword(@Param("username") String username, @Param("password") String password);

    /**
     * 查询所有启用状态的管理员用户（status = 1）
     */
    List<AdminUser> queryEnabledUsers();

    /**
     * 根据状态查询管理员用户列表
     */
    List<AdminUser> queryByStatus(@Param("status") Integer status);

    /**
     * 查询所有管理员用户
     */
    List<AdminUser> queryAll();
}


package com.rui.springboot_shiro.mapper;

import com.rui.springboot_shiro.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    @Select("SELECT * FROM t_user where name = #{name}")
    public User findByName(@Param("name") String name);

    @Select("SELECT * FROM t_user where id = #{id}")
    public User findById(@Param("id")Integer id);
}

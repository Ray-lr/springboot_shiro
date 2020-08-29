package com.rui.springboot_shiro.service;

import com.rui.springboot_shiro.domain.User;
import com.rui.springboot_shiro.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public User findByName(String username) {
        return userMapper.findByName(username);
    }

    public User findById(Integer id) {
        return userMapper.findById(id);
    }
}

package com.example.demo.service;

import com.example.demo.repository.CommonMapper;
import com.example.demo.repository.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FooService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private CommonMapper commonMapper;

    @Transactional
    public void bar(String userId) {
        userMapper.insert(userId);
        commonMapper.insert(userId);
    }
}

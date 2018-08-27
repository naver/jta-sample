package com.example.demo.repository;

import com.example.demo.annotation.RoutingKey;
import com.example.demo.annotation.RoutingMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@RoutingMapper
@Mapper
public interface UserMapper {

    @Insert("insert into user_test(user_id) values(#{userId})")
    void insert(@RoutingKey @Param("userId") String userId);
}

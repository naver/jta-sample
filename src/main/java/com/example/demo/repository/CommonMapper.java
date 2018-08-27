package com.example.demo.repository;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CommonMapper {

    @Insert("insert into common_test(user_id) values(#{userId})")
    void insert(@Param("userId") String userId);
}

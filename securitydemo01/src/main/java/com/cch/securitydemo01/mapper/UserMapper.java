package com.cch.securitydemo01.mapper;

import com.cch.securitydemo01.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;



@Mapper
public interface UserMapper {
    @Select("SELECT * FROM public.user WHERE username = #{username}")
    User getUserByUsername(String username);
}
package com.duhwan.ustime_backend.dao;

import com.duhwan.ustime_backend.dto.ChangePasswordDto;
import com.duhwan.ustime_backend.dto.UserDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    
    //회원가입
    void signUp(UserDto dto);

    UserDto selectUser(Long userId);

    UserDto findByEmail(String email);

    Long getCoupleId(Long userId);

    void updateUser(UserDto dto);

    void updatePassword(ChangePasswordDto changePasswordDto);

}

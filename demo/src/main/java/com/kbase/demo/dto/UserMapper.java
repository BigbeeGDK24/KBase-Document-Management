package com.kbase.demo.dto;


import com.kbase.demo.entity.User;


public class UserMapper {


    public static UserResponse toResponse(
            User user
    ){

        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getRole()
        );

    }

}
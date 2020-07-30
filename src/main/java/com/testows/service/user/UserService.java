package com.testows.service.user;

import com.testows.entity.UserEntity;

public interface UserService {
    UserEntity create(UserEntity userEntity);
    UserEntity findOne(Long userId);
}

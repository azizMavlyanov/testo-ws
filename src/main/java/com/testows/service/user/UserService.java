package com.testows.service.user;

import com.testows.entity.UserEntity;
import com.testows.models.PageableAndSortableData;
import com.testows.models.UserResponseModel;
import com.testows.models.UserUpdateModel;

public interface UserService {
    UserEntity create(UserEntity userEntity) throws Exception;
    PageableAndSortableData<UserResponseModel> findAll(int page, int size);
    UserEntity findOne(Long userId) throws Exception;
    UserEntity update(Long userId, UserUpdateModel userUpdateModel) throws Exception;
    void delete(Long userId) throws Exception;
}

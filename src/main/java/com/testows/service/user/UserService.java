package com.testows.service.user;

import com.testows.entity.UserEntity;
import com.testows.models.PageableAndSortableData;
import com.testows.models.UserRequestModel;
import com.testows.models.UserResponseModel;
import com.testows.models.UserUpdateModel;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    UserEntity create(UserRequestModel userRequestModel) throws Exception;
    PageableAndSortableData<UserResponseModel> findAll(int page, int size);
    UserEntity findOne(Long userId) throws Exception;
    UserEntity update(Long userId, UserUpdateModel userUpdateModel) throws Exception;
    void delete(Long userId) throws Exception;
}

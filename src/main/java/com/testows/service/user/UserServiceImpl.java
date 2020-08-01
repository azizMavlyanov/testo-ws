package com.testows.service.user;

import com.testows.dao.UserRepository;
import com.testows.entity.UserEntity;
import com.testows.models.PageableAndSortableData;
import com.testows.models.UserResponseModel;
import com.testows.models.UserUpdateModel;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;

@Service
public class UserServiceImpl implements UserService{
    private UserRepository userRepository;
    private ModelMapper modelMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           ModelMapper modelMapper
                           ) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public UserEntity create(UserEntity userEntity) {
        return userRepository.save(userEntity);
    }

    @Override
    public UserEntity findOne(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new Error("User not found"));
    }

    @Override
    public PageableAndSortableData<UserResponseModel> findAll(int page, int size) {
        if (page != 0) {
            page--;
        }

        Pageable pageableRequest = PageRequest.of(page, size);
        Page<UserEntity> userEntities = userRepository.findAll(pageableRequest);

        PageableAndSortableData<UserResponseModel> pagedAndSortedData = new PageableAndSortableData<>();
        pagedAndSortedData.setPage(userEntities.getPageable().getPageNumber() + 1);
        pagedAndSortedData.setSize(userEntities.getPageable().getPageSize());
        pagedAndSortedData.setHasPrevious(userEntities.hasPrevious());
        pagedAndSortedData.setHasNext(userEntities.hasNext());
        pagedAndSortedData.setTotalElements(userEntities.getTotalElements());
        pagedAndSortedData.setSort(userEntities.getSort().toString());

        Type listType = new TypeToken<List<UserResponseModel>>(){}.getType();
        List<UserResponseModel> userResponseList = modelMapper.map(userEntities.getContent(), listType);

        pagedAndSortedData.setData(userResponseList);

        return pagedAndSortedData;
    }

    @Override
    public UserEntity update(Long userId, UserUpdateModel userUpdateModel) {
        UserEntity userEntity = this.findOne(userId);
        modelMapper.map(userUpdateModel, userEntity);

        return userRepository.save(userEntity);
    }

    @Override
    public UserEntity delete(Long userId) {
        UserEntity userEntity = this.findOne(userId);
        userRepository.deleteById(userEntity.getUserId());

        return userEntity;
    }
}

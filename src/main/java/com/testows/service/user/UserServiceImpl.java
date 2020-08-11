package com.testows.service.user;

import com.testows.dao.UserRepository;
import com.testows.entity.UserEntity;
import com.testows.exceptions.CommonServiceException;
import com.testows.exceptions.ErrorMessages;
import com.testows.exceptions.ResourceAlreadyExistsException;
import com.testows.exceptions.ResourceNotFoundException;
import com.testows.models.PageableAndSortableData;
import com.testows.models.UserRequestModel;
import com.testows.models.UserResponseModel;
import com.testows.models.UserUpdateModel;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService{
    private UserRepository userRepository;
    private ModelMapper modelMapper;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           ModelMapper modelMapper,
                           BCryptPasswordEncoder bCryptPasswordEncoder
                           ) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public UserEntity create(UserRequestModel userRequestModel) throws Exception {
        if (userRepository.findByEmail(userRequestModel.getEmail()) != null) {
            throw new ResourceAlreadyExistsException(ErrorMessages.RECORD_ALREADY_EXISTS.getErrorMessage());
        }

        UserEntity userEntity = modelMapper.map(userRequestModel, UserEntity.class);

        userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(userRequestModel.getPassword()));

        try {
            return userRepository.save(userEntity);
        } catch (Exception e) {
            throw new CommonServiceException(e.getLocalizedMessage());
        }
    }

    @Override
    public UserEntity findOne(Long userId) throws Exception {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage()));
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
    public UserEntity update(Long userId, UserUpdateModel userUpdateModel) throws Exception {
        UserEntity userEntity = this.findOne(userId);

        if (userRepository.findByEmail(userUpdateModel.getEmail()) != null) {
            throw new ResourceAlreadyExistsException(ErrorMessages.RECORD_ALREADY_EXISTS.getErrorMessage());
        }

        modelMapper.map(userUpdateModel, userEntity);

        try {
            return userRepository.save(userEntity);
        } catch (Exception e) {
            throw new CommonServiceException(e.getLocalizedMessage());
        }
    }

    @Override
    public void delete(Long userId) throws Exception{
        UserEntity userEntity = this.findOne(userId);

        try {
            userRepository.deleteById(userEntity.getUserId());
        } catch (Exception e) {
            throw new CommonServiceException(e.getLocalizedMessage());
        }

    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(email);

        if (userEntity == null) throw new UsernameNotFoundException(email);

        return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(), true,
                true, true, true, new ArrayList<>());
    }
}

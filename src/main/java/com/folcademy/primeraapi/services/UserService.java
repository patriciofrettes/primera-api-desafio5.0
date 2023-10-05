package com.folcademy.primeraapi.services;

import com.folcademy.primeraapi.exceptions.exceptionkinds.UserBadRequestException;
import com.folcademy.primeraapi.exceptions.exceptionkinds.UserNotFoundException;
import com.folcademy.primeraapi.models.Dtos.UserAddDTO;
import com.folcademy.primeraapi.models.Dtos.UserEditDTO;
import com.folcademy.primeraapi.models.Dtos.UserReadDTO;
import com.folcademy.primeraapi.models.entities.UserEntity;
import com.folcademy.primeraapi.models.mappers.UserMapper;
import com.folcademy.primeraapi.models.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public List<UserReadDTO> findAll() {
        return userRepository
                .findAll()
                .stream()
                .map((Object userEntity) -> userMapper.userEntityToUserReadDTO((UserEntity) userEntity))
                .collect(Collectors.toList());
    }

    public UserReadDTO add(UserAddDTO userAddDTO) {
        Boolean emailExist = userRepository.existsByEmail(userAddDTO.getEmail());
        if(emailExist) throw new UserBadRequestException("Ya existe un usuario con ese email.");

        return userMapper.userEntityToUserReadDTO(
                userRepository.save(
                        userMapper.userAddDTOToUserEntity(userAddDTO)
                )
        );
    }

    public UserReadDTO findById(Integer userId) {
        try {
            return userRepository
                    .findById(userId)
                    .map(userEntity -> userMapper.userEntityToUserReadDTO(userEntity))
                    .orElseThrow(() -> new UserNotFoundException("No se encontro un usuario con ese identificador"));
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    public UserReadDTO findUserById(Integer userId) {
        Optional<UserEntity> userEntityOptional = userRepository.findById(userId);

        if(userEntityOptional.isEmpty()) {
            throw new UserNotFoundException("No se encontro un usuario con ese identificador");
        }

        return userMapper.userEntityToUserReadDTO(userEntityOptional.get());
    }
    public UserReadDTO deleteById(Integer userId) {
        try {
            UserEntity user = userRepository.findById(userId)
                    .orElseThrow(()-> new UserNotFoundException("No se encontro un usuario con ese identificador"));

            userRepository.delete(user);

            return userMapper.userEntityToUserReadDTO(user);
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }
    public UserReadDTO edit(Integer userId, UserEditDTO user) {
        try {
            UserEntity oldUser = userRepository.findById(userId)
                    .orElseThrow(()-> new UserNotFoundException("No se encontro un usuario con ese identificador"));

            if(!user.getName().isBlank()) oldUser.setName(user.getName());
            if(!user.getSurname().isBlank()) oldUser.setSurname(user.getSurname());

            UserEntity newUser = userRepository.save(oldUser);

            return userMapper.userEntityToUserReadDTO(newUser);
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

}

package com.fisco.fiscal.fiskofiscal.service;

import com.fisco.fiscal.fiskofiscal.dto.UserDTO;
import com.fisco.fiscal.fiskofiscal.model.User;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
public interface UserService {

    List<User> getAll();
    Optional<User> getUserById(Long id);
    User findByEmail(String email);
    User saveUser(User user);
    Optional<UserDTO> update(Long id, User user);
    String signUpUser(User user);
    void enableUser(String email);
}

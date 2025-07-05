package com.mq76.holyTask_be.service;

import com.mq76.holyTask_be.auth.JwtUtils;
import com.mq76.holyTask_be.model.*;
import com.mq76.holyTask_be.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Date;
import java.util.List;
import java.util.Optional;
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    @Autowired
    private PriestProfileService priestProfileService;
    @Override
    public ResponseObject registerUser(User user) {
        Optional<User> userOptional = userRepository.findByUsername(user.getUsername());
        Optional<User> userOptional2 = userRepository.findByEmail(user.getEmail());

        if(userOptional.isPresent() || userOptional2.isPresent()) {
            return new ResponseObject(MessageConstants.FAILED, MessageConstants.THAT_BAI, null);
        }
        user.setIsActive(true);
        user.setPasswordHash(passwordEncoder.encode(user.getPasswordHash()));
        user.setRole(Role.PRIEST);
        user.setProfilePictureUrl("");
        user.setPhoneNumber(user.getPhoneNumber());
        user.setProfilePictureUrl("https://i.ibb.co/FL5DXK4f/avatar-trang-4.jpg");
        PriestProfile priestProfile = new PriestProfile();
        User user1 = userRepository.save(user);
        priestProfile.setUser(user1);
        priestProfile.setCreatedAt(new Date());
        priestProfileService.createProfile(priestProfile);
        return new ResponseObject(MessageConstants.OK, MessageConstants.THANH_CONG, user1);
    }

    @Override
    public ResponseObject loginUser(String username, String password) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if(passwordEncoder.matches(password, user.getPasswordHash()) && user.getIsActive()) {
                return new ResponseObject(MessageConstants.OK ,MessageConstants.THANH_CONG ,jwtUtils.generateToken(user));
            }

        }
        return new ResponseObject(MessageConstants.FAILED,MessageConstants.THAT_BAI,null);
    }

    @Override
    public ResponseObject getUserByUsername(String username) {
        if(userRepository.findByUsername(username).isPresent()) {
            return new ResponseObject(MessageConstants.OK, MessageConstants.THANH_CONG, userRepository.findByUsername(username).get());
        }else {
            return new ResponseObject(MessageConstants.FAILED,MessageConstants.THAT_BAI,null);
        }
    }

    @Override
    public ResponseObject getUserById(Integer id) {
        if(userRepository.findById(id).isPresent()) {
            return new ResponseObject(MessageConstants.OK, MessageConstants.THANH_CONG, userRepository.findById(id).get());
        }else {
            return new ResponseObject(MessageConstants.FAILED,MessageConstants.THAT_BAI,null);
        }
    }

    @Override
    public ResponseObject getUsersByRole(Role role) {
        if(userRepository.findByRole(role).isEmpty()) {
            return new ResponseObject(MessageConstants.FAILED,MessageConstants.THAT_BAI,null);
        }else {
            return new ResponseObject(MessageConstants.OK,MessageConstants.THANH_CONG, userRepository.findByRole(role));
        }
    }

    @Override
    public ResponseObject deactivateUser(Integer id) {
        try{
            userRepository.findById(id).ifPresent(user -> {
                user.setIsActive(false);
                userRepository.save(user);
            });
            return new ResponseObject(MessageConstants.OK, MessageConstants.THANH_CONG, userRepository.findById(id).get());
        }catch (Exception e) {
            return new ResponseObject(MessageConstants.FAILED,MessageConstants.THAT_BAI,null);
        }
    }

    @Override
    public ResponseObject findUserById(Integer id) {
        try{
            userRepository.findUserById(id);
            return new ResponseObject(MessageConstants.OK, MessageConstants.THANH_CONG, userRepository.findById(id).get());
        }catch (Exception e) {
            return new ResponseObject(MessageConstants.FAILED,MessageConstants.THAT_BAI,null);
        }
    }

    @Override
    public ResponseObject getAllUsers() {
        try {
            return new ResponseObject(MessageConstants.OK, MessageConstants.THANH_CONG, userRepository.findAllUsers());
        }catch (Exception e) {
            return new ResponseObject(MessageConstants.FAILED,MessageConstants.THAT_BAI,null);
        }
    }

    @Override
    public ResponseObject updateUser(User user, UserPrincipal userPrincipal) {
        try {
            User foundUser = userRepository.findUserById(user.getId()).get();
            if(foundUser!=null) {
                foundUser.setIsActive(user.getIsActive());
                foundUser.setRole(user.getRole());
                foundUser.setUpdatedUser(userPrincipal.getUsername());
                foundUser.setUpdatedAt(new Date());
                userRepository.save(foundUser);
            }
            return new ResponseObject(MessageConstants.OK, MessageConstants.THANH_CONG, foundUser);
        }catch (Exception e){
            return new ResponseObject(MessageConstants.FAILED,MessageConstants.THAT_BAI,null);
        }
    }
}


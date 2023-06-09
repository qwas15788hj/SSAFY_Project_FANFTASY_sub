package com.a306.fanftasy.domain.user.service;

import com.a306.fanftasy.domain.user.dto.UserDetailDTO;
import com.a306.fanftasy.domain.user.dto.UserJoinDTO;
import com.a306.fanftasy.domain.user.dto.UserLoginDTO;
import com.a306.fanftasy.domain.user.dto.UserUpdateDTO;
import com.a306.fanftasy.domain.user.entity.User;
import com.a306.fanftasy.domain.user.repository.UserRepository;
import com.a306.fanftasy.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final JwtTokenUtil jwtTokenUtil;
    public UserLoginDTO login(String address){
        log.info(address);
        User user = userRepository.findByAddress(address);
        if(user!=null){
            Long userId=user.getUserId();
            String nickname=user.getNickname();
            String profileImg=user.getProfileImg();
            String role = user.getRole();
       //   String accessToken = jwtTokenUtil.generateAccessToken(address,userId)
       //   String refreshToken = jwtTokenUtil.generateRefreshToken(nickname);
            return UserLoginDTO.of(userId,nickname,address,role,profileImg);
        }
        else{
            return null;
        }



        }

    @Override
    public void join(UserJoinDTO userJoinDTO) {
           userRepository.save(User.ofUser(userJoinDTO));
    }

    @Override
    public UserDetailDTO getUserDetail(String addresss){
        User user = userRepository.findByAddress(addresss);
        return UserDetailDTO.builder()
                .name(user.getName())
                .address(user.getAddress())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .profileImg(user.getProfileImg())
                .phone(user.getPhone())
                .role(user.getRole())
                .totalPrice(user.getTotalPrice())
                .totalSales(user.getTotalSales())
                .build();
    }

    @Override
    public void updateUser(UserUpdateDTO userUpdateDTO) {
        User user = userRepository.findByAddress(userUpdateDTO.getAddress());
        log.info(userUpdateDTO.getNickname());
        if (StringUtils.hasText(userUpdateDTO.getNickname())) {
            user.setNickname(userUpdateDTO.getNickname());
        }
        if (StringUtils.hasText(userUpdateDTO.getProfileImg())) {
            user.setProfileImg(userUpdateDTO.getProfileImg());
        }

        userRepository.save(user);
    }
    @Override
    public UserUpdateDTO updateUser(String address) {
        User user = userRepository.findByAddress(address);

        return UserUpdateDTO.builder()
                .address(user.getAddress())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .profileImg(user.getProfileImg())
                .build();
    }
    @Override
    public User findByUserId(Long userId) {
       User user=userRepository.findByUserId(userId);
       return user;
    }


}

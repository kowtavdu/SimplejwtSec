package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.entity.UserInfo;
import org.example.entity.UserInfoUserDetail;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailUserInfo implements UserDetailsService {


    private UserRepository userRepository;
    public UserDetailUserInfo(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("#####loadUserByUsername###### "+userRepository);
        Optional<UserInfo> userInfo = userRepository.findByName(username);
        return userInfo.map(UserInfoUserDetail::new).orElseThrow(() -> new UsernameNotFoundException("user not found!"));
    }

    public UserInfo createUser(UserInfo userInfo){
        System.out.println("####createUser####### "+userRepository);
        return userRepository.save(userInfo);
    }


}

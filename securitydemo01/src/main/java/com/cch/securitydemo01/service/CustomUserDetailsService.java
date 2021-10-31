package com.cch.securitydemo01.service;

import java.util.List;
import java.util.Optional;

import com.cch.securitydemo01.mapper.UserMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service("userDetailsService")
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserMapper userMapper;

    // 根據使用者名稱做什麼事
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // TODO Auto-generated method stub
        com.cch.securitydemo01.entity.User user = userMapper.getUserByUsername(username);
        Optional.ofNullable(user).orElseThrow(() -> new UsernameNotFoundException("User not exist."));

        List<GrantedAuthority> auths = AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_sale,ROLE_manager");
        return new User(user.getUsername(), new BCryptPasswordEncoder().encode(user.getPassword()), auths);
    }
    
}


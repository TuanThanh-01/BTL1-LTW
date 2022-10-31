package com.PTIT.QuanLyThuVien.security.services;

import com.PTIT.QuanLyThuVien.model.User;
import com.PTIT.QuanLyThuVien.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByEmail(username);
        if(user == null) {
            throw new UsernameNotFoundException("User Not Found with email: " + username);
        }
        return new CustomUserDetails(user);
    }
}

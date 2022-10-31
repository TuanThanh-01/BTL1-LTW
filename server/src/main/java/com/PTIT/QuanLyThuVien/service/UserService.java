package com.PTIT.QuanLyThuVien.service;

import com.PTIT.QuanLyThuVien.error.UserNotFoundException;
import com.PTIT.QuanLyThuVien.model.User;

public interface UserService {

    User findByEmail(String email);

    void saveUser(User user);

    boolean existsByEmail(String email);


}

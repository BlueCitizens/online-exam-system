package com.exam.system.service;

import com.exam.system.entity.Admin;

import java.util.List;

public interface AdminService{

    List<Admin> findAll();

    Admin findById(Integer adminId);

    int deleteById(int adminId);

    int update(Admin admin);

    int add(Admin admin);
}

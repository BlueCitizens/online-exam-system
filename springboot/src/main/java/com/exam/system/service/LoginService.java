package com.exam.system.service;

import com.exam.system.entity.Admin;
import com.exam.system.entity.Student;
import com.exam.system.entity.Teacher;

public interface LoginService {

    public Admin adminLogin(Integer username, String password);

    public Teacher teacherLogin(Integer username, String password);

    public Student studentLogin(Integer username, String password);
}

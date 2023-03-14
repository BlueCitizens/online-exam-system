package com.exam.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.exam.system.entity.Teacher;

import java.util.List;

public interface TeacherService {

    IPage<Teacher> findAll(Page<Teacher> page);

    List<Teacher> findAll();

    Teacher findById(Integer teacherId);

    int deleteById(Integer teacherId);

    int update(Teacher teacher);

    int add(Teacher teacher);
}

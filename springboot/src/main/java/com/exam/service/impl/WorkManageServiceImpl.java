package com.exam.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.exam.entity.ScoreWork;
import com.exam.entity.WorkManage;
import com.exam.mapper.WorkManageMapper;
import com.exam.service.WorkManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkManageServiceImpl implements WorkManageService {
    @Autowired
    private WorkManageMapper workManageMapper;


    @Override
    public List<WorkManage> findAll() {
        return workManageMapper.findAll();
    }

    @Override
    public List<WorkManage> find(Integer studentId) {
        return workManageMapper.find(studentId);
    }

    @Override
    public IPage<WorkManage> findAll(Page<WorkManage> page) {
        return workManageMapper.findAll(page);
    }

    @Override
    public IPage<WorkManage> find(Page<WorkManage> page,Integer studentId) {
        return workManageMapper.find(page,studentId);
    }

    @Override
    public WorkManage findById(Integer examCode) {
        return workManageMapper.findById(examCode);
    }

    @Override
    public int delete(Integer examCode) {
        return workManageMapper.delete(examCode);
    }

    @Override
    public int update(WorkManage exammanage) {
        return workManageMapper.update(exammanage);
    }

    @Override
    public int add(WorkManage exammanage) {
        return workManageMapper.add(exammanage);
    }

    @Override
    public int changeStatus(Integer status,Integer workCode) {
        return workManageMapper.changeStatus(status, workCode);
    }

    @Override
    public WorkManage findOnlyPaperId() {
        return workManageMapper.findOnlyPaperId();
    }

    @Override
    public List<ScoreWork> findUnmarkedByWork(Integer workCode) {
        return workManageMapper.findUnmarkedByWork(workCode);
    }
}

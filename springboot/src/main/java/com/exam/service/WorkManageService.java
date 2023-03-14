package com.exam.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.exam.entity.ScoreWork;
import com.exam.entity.WorkManage;

import java.util.List;

public interface WorkManageService {

    /**
     * 不分页查询所有考试信息
     */
    List<WorkManage> findAll();

    IPage<WorkManage> findAll(Page<WorkManage> page);

    List<WorkManage> find(Integer studentId);

    IPage<WorkManage> find(Page<WorkManage> page,Integer studentId);

    WorkManage findById(Integer examCode);

    int delete(Integer examCode);

    int update(WorkManage exammanage);

    int add(WorkManage exammanage);

    int changeStatus(Integer status,Integer workCode);

    WorkManage findOnlyPaperId();

    List<ScoreWork> findUnmarkedByWork(Integer workCode);

}

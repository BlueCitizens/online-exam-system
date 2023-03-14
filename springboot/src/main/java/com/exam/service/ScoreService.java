package com.exam.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.exam.entity.Score;
import com.exam.entity.ScoreWork;

import java.util.List;

public interface ScoreService {
    int add(Score score);

    int addObScore(ScoreWork scoreWork);

    List<Score> findAll();

    IPage<Score> findById(Page page, Integer studentId);

    List<Score> findByPaper(Integer paperId);

    List<Score> findById(Integer studentId);

    List<Score> findByExamCode(Integer examCode);

    int updateMarkingById(Integer score, Integer id);

    int updateScoreWorkById(Integer score, Integer id);
}

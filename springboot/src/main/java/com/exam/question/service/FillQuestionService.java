package com.exam.question.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.exam.paper.entity.Answer;
import com.exam.question.entity.FillQuestion;

import java.util.List;

public interface FillQuestionService {

    List<FillQuestion> findBySubAndSec(String subject, String section);

    List<FillQuestion> findByIdAndType(Integer paperId);

    List<Answer> findByIdAndTypeAndStudent(Integer PaperId, Integer studentId);

    IPage<FillQuestion> findAll(Page<FillQuestion> page);

    FillQuestion findOnlyQuestionId();

    int add(FillQuestion fillQuestion);

    List<Integer> findBySubject(String subject,Integer pageNo);
}

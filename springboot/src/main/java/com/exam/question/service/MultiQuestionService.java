package com.exam.question.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.exam.paper.entity.Answer;
import com.exam.question.entity.MultiQuestion;

import java.util.List;

public interface MultiQuestionService {

    List<MultiQuestion> findBySubAndSec(String subject, String section);

    List<MultiQuestion> findByIdAndType(Integer PaperId);

    List<Answer> findByIdAndTypeAndStudent(Integer PaperId, Integer studentId);

    List<MultiQuestion> findAll();

    IPage<MultiQuestion> findAll(Page<MultiQuestion> page);

    MultiQuestion findOnlyQuestionId();

    int add(MultiQuestion multiQuestion);

    List<Integer> findBySubject(String subject, Integer pageNo);
}

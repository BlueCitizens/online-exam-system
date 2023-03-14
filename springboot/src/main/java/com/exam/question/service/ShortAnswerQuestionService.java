package com.exam.question.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.exam.paper.entity.Answer;
import com.exam.paper.entity.Marking;
import com.exam.question.entity.ShortAnswerQuestion;

import java.util.List;

public interface ShortAnswerQuestionService {

    List<ShortAnswerQuestion> findBySubAndSec(String subject, String section);

    List<ShortAnswerQuestion> findByIdAndType(Integer paperId);

    List<Answer> findByIdAndTypeAndStudent(Integer PaperId, Integer studentId);

    IPage<ShortAnswerQuestion> findAll(Page<ShortAnswerQuestion> page);

    ShortAnswerQuestion findOnlyQuestionId();

    int add(ShortAnswerQuestion fillQuestion);

    List<Integer> findBySubject(String subject,Integer pageNo);

    List<Marking> selectByPaperAndStudent(Integer paperId, Integer studentId);
}

package com.exam.question.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.exam.paper.entity.Answer;
import com.exam.paper.entity.Marking;
import com.exam.question.entity.ShortAnswerQuestion;
import com.exam.question.mapper.ShortAnswerQuestionMapper;
import com.exam.question.service.ShortAnswerQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShortAnswerQuestionServiceImpl implements ShortAnswerQuestionService {

    @Autowired
    private ShortAnswerQuestionMapper shortAnswerQuestionMapper;

    @Override
    public List<ShortAnswerQuestion> findBySubAndSec(String subject, String section) {
        return shortAnswerQuestionMapper.findBySubAndSec(subject, section);
    }

    @Override
    public List<ShortAnswerQuestion> findByIdAndType(Integer paperId) {
        return shortAnswerQuestionMapper.findByIdAndType(paperId);
    }

    @Override
    public List<Answer> findByIdAndTypeAndStudent(Integer PaperId, Integer studentId) {
        return shortAnswerQuestionMapper.findByIdAndTypeAndStudent(PaperId, studentId);
    }

    @Override
    public IPage<ShortAnswerQuestion> findAll(Page<ShortAnswerQuestion> page) {
        return shortAnswerQuestionMapper.findAll(page);
    }

    @Override
    public ShortAnswerQuestion findOnlyQuestionId() {
        return shortAnswerQuestionMapper.findOnlyQuestionId();
    }

    @Override
    public int add(ShortAnswerQuestion fillQuestion) {
        return shortAnswerQuestionMapper.add(fillQuestion);
    }

    @Override
    public List<Integer> findBySubject(String subject, Integer pageNo) {
        return shortAnswerQuestionMapper.findBySubject(subject, pageNo);
    }

    @Override
    public List<Marking> selectByPaperAndStudent(Integer paperId, Integer studentId) {
        return shortAnswerQuestionMapper.selectByPaperAndStudent(paperId, studentId);
    }
}

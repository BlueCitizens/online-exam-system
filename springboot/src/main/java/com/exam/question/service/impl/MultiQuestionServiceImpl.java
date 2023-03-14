package com.exam.question.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.exam.paper.entity.Answer;
import com.exam.question.entity.MultiQuestion;
import com.exam.question.mapper.MultiQuestionMapper;
import com.exam.question.service.MultiQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MultiQuestionServiceImpl implements MultiQuestionService {

    @Autowired
    private MultiQuestionMapper multiQuestionMapper;

    @Override
    public List<MultiQuestion> findBySubAndSec(String subject, String section) {
        return multiQuestionMapper.findBySubAndSec(subject,section);
    }

    @Override
    public List<MultiQuestion> findByIdAndType(Integer PaperId) {
        return multiQuestionMapper.findByIdAndType(PaperId);
    }

    @Override
    public List<Answer> findByIdAndTypeAndStudent(Integer PaperId, Integer studentId) {
        return multiQuestionMapper.findByIdAndTypeAndStudent(PaperId, studentId);
    }

    @Override
    public List<MultiQuestion> findAll() {
        return multiQuestionMapper.findAll();
    }

    @Override
    public IPage<MultiQuestion> findAll(Page<MultiQuestion> page) {
        return multiQuestionMapper.findAll(page);
    }

    @Override
    public MultiQuestion findOnlyQuestionId() {
        return multiQuestionMapper.findOnlyQuestionId();
    }

    @Override
    public int add(MultiQuestion multiQuestion) {
        multiQuestionMapper.add(multiQuestion);
        return multiQuestion.getQuestionId();
    }

    @Override
    public List<Integer> findBySubject(String subject, Integer pageNo) {
        return multiQuestionMapper.findBySubject(subject, pageNo);
    }
}

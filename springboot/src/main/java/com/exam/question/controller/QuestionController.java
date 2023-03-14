package com.exam.question.controller;

import com.exam.question.service.FillQuestionService;
import com.exam.question.service.MultiQuestionService;
import com.exam.question.service.ShortAnswerQuestionService;
import com.exam.question.service.impl.JudgeQuestionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: BlueCitizens
 * @Date: 2021/6/17 3:27
 * @Description: 题库接口
 */

@RestController
public class QuestionController {
    @Autowired
    private FillQuestionService fillQuestionService;
    @Autowired
    private JudgeQuestionServiceImpl judgeQuestionService;
    @Autowired
    private MultiQuestionService multiQuestionService;
    @Autowired
    private ShortAnswerQuestionService shortAnswerQuestionService;

    @GetMapping("/db/question")
    public List<?> findExactDb(@RequestParam("type") Integer type, @RequestParam("subject") String subject, @RequestParam("section") String section) {
        switch (type) {
            case 1:
                return multiQuestionService.findBySubAndSec(subject, section);
            case 2:
                return fillQuestionService.findBySubAndSec(subject, section);
            case 3:
                return judgeQuestionService.findBySubAndSec(subject, section);
            case 4:
                return shortAnswerQuestionService.findBySubAndSec(subject, section);//语句
            default: //可选
                return null;//语句
        }
    }
}

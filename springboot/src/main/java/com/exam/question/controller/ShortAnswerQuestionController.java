package com.exam.question.controller;

import com.exam.entity.ApiResult;
import com.exam.question.entity.ShortAnswerQuestion;
import com.exam.question.service.ShortAnswerQuestionService;
import com.exam.util.ApiResultHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ShortAnswerQuestionController {

    @Autowired
    private ShortAnswerQuestionService shortAnswerQuestionService;

    @PostMapping("/shortAnswerQuestion")
    public ApiResult add(@RequestBody ShortAnswerQuestion shortAnswerQuestion) {
        int res = shortAnswerQuestionService.add(shortAnswerQuestion);
        if (res != 0) {
            return ApiResultHandler.buildApiResult(200, "添加成功", res);
        }
        return ApiResultHandler.buildApiResult(400, "添加失败", res);
    }

    @GetMapping("/shortAnswerQuestionId")
    public ApiResult findOnlyQuestionId() {
        ShortAnswerQuestion res = shortAnswerQuestionService.findOnlyQuestionId();
        return ApiResultHandler.buildApiResult(200, "查询成功", res);
    }

}

package com.exam.paper.controller;

import com.exam.entity.*;
import com.exam.paper.entity.Answer;
import com.exam.paper.entity.Marking;
import com.exam.paper.entity.PaperManage;
import com.exam.question.entity.FillQuestion;
import com.exam.question.entity.JudgeQuestion;
import com.exam.question.entity.MultiQuestion;
import com.exam.question.entity.ShortAnswerQuestion;
import com.exam.question.service.impl.FillQuestionServiceImpl;
import com.exam.question.service.impl.JudgeQuestionServiceImpl;
import com.exam.question.service.impl.MultiQuestionServiceImpl;
import com.exam.paper.service.PaperService;
import com.exam.question.service.ShortAnswerQuestionService;
import com.exam.util.ApiResultHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class PaperController {

    @Autowired
    private PaperService paperService;

    @Autowired
    private JudgeQuestionServiceImpl judgeQuestionService;

    @Autowired
    private MultiQuestionServiceImpl multiQuestionService;

    @Autowired
    private FillQuestionServiceImpl fillQuestionService;

    @Autowired
    private ShortAnswerQuestionService shortAnswerQuestionService;

    @GetMapping("/papers")
    public ApiResult<PaperManage> findAll() {
        ApiResult res = ApiResultHandler.buildApiResult(200, "请求成功", paperService.findAll());
        return res;
    }
/*
    @GetMapping("/paper/{paperId}")
    public Map<Integer, List<?>> findById(@PathVariable("paperId") Integer paperId) {
        List<MultiQuestion> multiQuestionRes = multiQuestionService.findByIdAndType(paperId);   //选择题题库 1
        List<FillQuestion> fillQuestionsRes = fillQuestionService.findByIdAndType(paperId);     //填空题题库 2
        List<JudgeQuestion> judgeQuestionRes = judgeQuestionService.findByIdAndType(paperId);   //判断题题库 3
        Map<Integer, List<?>> map = new HashMap<>();
        map.put(1, multiQuestionRes);
        map.put(2, fillQuestionsRes);
        map.put(3, judgeQuestionRes);
        return map;
    }*/


    @PostMapping("/marking")
    public ApiResult answerSheet(@RequestBody Marking marking) {
        int res = paperService.insertAnswerSheet(marking);
        if (res == 1) {
            return ApiResultHandler.buildApiResult(200, "添加成功", res);
        } else {
            return ApiResultHandler.buildApiResult(400, "添加失败", res);
        }
    }

    @GetMapping("/paper/{paperId}")
    public Map<Integer, List<?>> findWorkById(@PathVariable("paperId") Integer paperId) {
        List<MultiQuestion> multiQuestionRes = multiQuestionService.findByIdAndType(paperId);   //选择题题库 1
        List<FillQuestion> fillQuestionsRes = fillQuestionService.findByIdAndType(paperId);     //填空题题库 2
        List<JudgeQuestion> judgeQuestionRes = judgeQuestionService.findByIdAndType(paperId);   //判断题题库 3
        List<ShortAnswerQuestion> shortAnswerQuestionRes = shortAnswerQuestionService.findByIdAndType(paperId);   //简答题题库 4
        Map<Integer, List<?>> map = new HashMap<>();
        map.put(1, multiQuestionRes);
        map.put(2, fillQuestionsRes);
        map.put(3, judgeQuestionRes);
        map.put(4, shortAnswerQuestionRes);
        return map;
    }

    @GetMapping("/paper/{paperId}/answerSheet/{studentId}")
    public Map<Integer, List<?>> findSheetById(@PathVariable("paperId") Integer paperId, @PathVariable("studentId") Integer studentId) {
        List<Answer> multiQuestionRes = multiQuestionService.findByIdAndTypeAndStudent(paperId, studentId);   //选择题题库 1
        List<Answer> fillQuestionsRes = fillQuestionService.findByIdAndTypeAndStudent(paperId, studentId);     //填空题题库 2
        List<Answer> judgeQuestionRes = judgeQuestionService.findByIdAndTypeAndStudent(paperId, studentId);   //判断题题库 3
        List<Answer> shortAnswerQuestionRes = shortAnswerQuestionService.findByIdAndTypeAndStudent(paperId, studentId);   //简答题题库 4
        Map<Integer, List<?>> map = new HashMap<>();
        map.put(1, multiQuestionRes);
        map.put(2, fillQuestionsRes);
        map.put(3, judgeQuestionRes);
        map.put(4, shortAnswerQuestionRes);
        return map;
    }

    @PostMapping("/paperManage")
    public ApiResult add(@RequestBody PaperManage paperManage) {
        int res = paperService.add(paperManage);
        if (res != 0) {
            return ApiResultHandler.buildApiResult(200, "添加成功", res);
        }
        return ApiResultHandler.buildApiResult(400, "添加失败", res);
    }

    @DeleteMapping("/paperManage")
    public ApiResult delete(@RequestBody PaperManage paperManage){
        int res = paperService.delete(paperManage);
        if (res != 0) {
            return ApiResultHandler.buildApiResult(200, "删除成功", res);
        }
        return ApiResultHandler.buildApiResult(400, "删除失败", res);
    }
}

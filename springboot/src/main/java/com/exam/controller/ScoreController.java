package com.exam.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.exam.entity.ApiResult;
import com.exam.entity.Score;
import com.exam.entity.ScoreWork;
import com.exam.service.ScoreService;
import com.exam.util.ApiResultHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ScoreController {
    @Autowired
    private ScoreService scoreService;

    @GetMapping("/scores")
    public ApiResult findAll() {
        List<Score> res = scoreService.findAll();
        return ApiResultHandler.buildApiResult(200, "查询所有学生成绩", res);
    }

    //    分页
    @GetMapping("/score/{page}/{size}/{studentId}")
    public ApiResult findById(@PathVariable("page") Integer page, @PathVariable("size") Integer size, @PathVariable("studentId") Integer studentId) {
        Page<Score> scorePage = new Page<>(page, size);
        IPage<Score> res = scoreService.findById(scorePage, studentId);
        return ApiResultHandler.buildApiResult(200, "根据ID查询成绩", res);
    }

    //    不分页
    @GetMapping("/score/{studentId}")
    public ApiResult findById(@PathVariable("studentId") Integer studentId) {
        List<Score> res = scoreService.findById(studentId);
        if (!res.isEmpty()) {
            return ApiResultHandler.buildApiResult(200, "根据ID查询成绩", res);
        } else {
            return ApiResultHandler.buildApiResult(400, "ID不存在", res);
        }
    }

    @PostMapping("/score")
    public ApiResult add(@RequestBody Score score) {
        int res = scoreService.add(score);
        if (res == 0) {
            return ApiResultHandler.buildApiResult(400, "成绩添加失败", res);
        } else {
            return ApiResultHandler.buildApiResult(200, "成绩添加成功", res);
        }
    }

    /**
     * 增加客观题判题记录
     *
     * @param scoreWork
     * @return
     */
    @PostMapping("/obScore")
    public ApiResult addSu(@RequestBody ScoreWork scoreWork) {
        int res = scoreService.addObScore(scoreWork);
        if (res == 0) {
            return ApiResultHandler.buildApiResult(400, "成绩添加失败", res);
        } else {
            return ApiResultHandler.buildApiResult(200, "成绩添加成功", res);
        }
    }

    @GetMapping("/scores/{examCode}")
    public ApiResult findByExamCode(@PathVariable("examCode") Integer examCode) {
        List<Score> scores = scoreService.findByExamCode(examCode);
        return ApiResultHandler.buildApiResult(200, "查询成功", scores);
    }

    /**
     * 更新answer sheet的一条记录
     * @param score
     * @param id 答题记录id
     * @return
     */
    @PatchMapping("/score/{score}/id/{id}")
    public ApiResult updateOneSingleSuScore(@PathVariable("score") Integer score, @PathVariable("id") Integer id) {
        int res = scoreService.updateMarkingById(score, id);
        return ApiResultHandler.buildApiResult(200, "更新成功", res);
    }

    @PatchMapping("/totalScore/{score}/id/{id}")
    public ApiResult updateOneTotal(@PathVariable("score") Integer score, @PathVariable("id") Integer id) {
        int res = scoreService.updateScoreWorkById(score, id);
        return ApiResultHandler.buildApiResult(200, "更新成功", res);
    }
}

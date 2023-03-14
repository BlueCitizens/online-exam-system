package com.exam.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.exam.entity.ApiResult;
import com.exam.paper.entity.Marking;
import com.exam.entity.ScoreWork;
import com.exam.entity.WorkManage;
import com.exam.question.service.ShortAnswerQuestionService;
import com.exam.service.WorkManageService;
import com.exam.util.ApiResultHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class WorkManageController {

    @Autowired
    private WorkManageService workManageService;

    @Autowired
    private ShortAnswerQuestionService shortAnswerQuestionService;

    @GetMapping("/works")
    public ApiResult findAll() {
        System.out.println("不分页查询所有作业");
        ApiResult apiResult;
        apiResult = ApiResultHandler.buildApiResult(200, "请求成功！", workManageService.findAll());
        return apiResult;
    }

    @GetMapping("/works/{page}/{size}")
    public ApiResult findAll(@PathVariable("page") Integer page, @PathVariable("size") Integer size) {
        System.out.println("分页查询所有作业");
        ApiResult apiResult;
        Page<WorkManage> examManage = new Page<>(page, size);
        IPage<WorkManage> all = workManageService.findAll(examManage);
        System.out.println(all.getRecords());
        apiResult = ApiResultHandler.buildApiResult(200, "请求成功！", all);
        return apiResult;
    }

    @GetMapping("/works/{page}/{size}/{studentId}")
    public ApiResult find(@PathVariable("page") Integer page, @PathVariable("size") Integer size,@PathVariable("studentId") Integer studentId) {
        System.out.println("分页查询所有作业");
        ApiResult apiResult;
        Page<WorkManage> examManage = new Page<>(page, size);
        IPage<WorkManage> all = workManageService.find(examManage,studentId);
        apiResult = ApiResultHandler.buildApiResult(200, "请求成功！", all);
        return apiResult;
    }

    @GetMapping("/work/{workCode}")
    public ApiResult findById(@PathVariable("workCode") Integer examCode) {
        System.out.println("根据ID查找");
        WorkManage res = workManageService.findById(examCode);
        if (res == null) {
            return ApiResultHandler.buildApiResult(10000, "作业编号不存在", null);
        }
        return ApiResultHandler.buildApiResult(200, "请求成功！", res);
    }

    @GetMapping("/work/{paperId}/{studentId}")
    public ApiResult findByWorkAndStudent(@PathVariable("paperId") Integer paperId, @PathVariable("studentId") Integer studentId) {
        List<Marking> res = shortAnswerQuestionService.selectByPaperAndStudent(paperId, studentId);
        if (res == null) {
            return ApiResultHandler.buildApiResult(10000, "作业编号不存在", null);
        }
        return ApiResultHandler.buildApiResult(200, "请求成功！", res);
    }

    @DeleteMapping("/work/{workCode}")
    public ApiResult deleteById(@PathVariable("workCode") Integer examCode) {
        int res = workManageService.delete(examCode);
        return ApiResultHandler.buildApiResult(200, "删除成功", res);
    }

    @PutMapping("/work")
    public ApiResult update(@RequestBody WorkManage exammanage) {
        int res = workManageService.update(exammanage);
//        if (res == 0) {
//            return ApiResultHandler.buildApiResult(20000,"请求参数错误");
//        }
        System.out.print("更新操作执行---");
        return ApiResultHandler.buildApiResult(200, "更新成功", res);
    }

    @PostMapping("/work")
    public ApiResult add(@RequestBody WorkManage workManage) {
        int res = workManageService.add(workManage);
        if (res == 1) {
            return ApiResultHandler.buildApiResult(200, "添加成功", res);
        } else {
            return ApiResultHandler.buildApiResult(400, "添加失败", res);
        }
    }

    @PutMapping("/work/{workCode}/status/{status}")
    public ApiResult publish(@PathVariable("status") Integer status,@PathVariable("workCode") Integer workCode) {
        int res = workManageService.changeStatus(status, workCode);
        if (res == 1) {
            return ApiResultHandler.buildApiResult(200, "添加成功", res);
        } else {
            return ApiResultHandler.buildApiResult(400, "添加失败", res);
        }
    }

    @GetMapping("/workManagePaperId")
    public ApiResult findOnlyPaperId() {
        WorkManage res = workManageService.findOnlyPaperId();
        if (res != null) {
            return ApiResultHandler.buildApiResult(200, "请求成功", res);
        }
        return ApiResultHandler.buildApiResult(400, "请求失败", null);
    }

    @GetMapping("/unMarked/{workCode}")
    public ApiResult findWaitingByWorkCode(@PathVariable("workCode") Integer workCode) {
        List<ScoreWork> res = workManageService.findUnmarkedByWork(workCode);
        if (res != null) {
            return ApiResultHandler.buildApiResult(200, "请求成功", res);
        }
        return ApiResultHandler.buildApiResult(400, "请求失败", null);
    }

}

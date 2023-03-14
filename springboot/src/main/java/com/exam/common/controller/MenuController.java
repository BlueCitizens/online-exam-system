package com.exam.common.controller;

import com.alibaba.fastjson.JSONObject;
import com.exam.common.service.MenuService;
import com.exam.entity.ApiResult;
import com.exam.util.ApiResultHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: BlueCitizens
 * @Date: 2021/6/15 22:24
 * @Description: 菜单
 */

@RestController
public class MenuController {

    @Autowired
    MenuService menuService;
    @GetMapping("/menu/question")
    public ApiResult questionDbMenu(){
        List<Object> cascader = menuService.getCascaderForQuestion();
        System.out.println(cascader);
        return ApiResultHandler.buildApiResult(200,"查询成功",cascader);
    }
}

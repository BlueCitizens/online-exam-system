package com.exam.common.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.exam.common.mapper.MenuMapper;
import com.exam.common.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @Author: BlueCitizens
 * @Date: 2021/6/15 23:28
 * @Description:
 */
@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public List<Object> getCascaderForQuestion() {
        List<Object> cascader = new ArrayList<>();
        List<Object> cascader1 = new ArrayList<>();
        List<Object> cascader2 = new ArrayList<>();
        List<Object> cascader3 = new ArrayList<>();
        List<Object> cascader4 = new ArrayList<>();
        HashMap<String, Object> hashMap1 = new HashMap<>();
        HashMap<String, Object> hashMap2 = new HashMap<>();
        HashMap<String, Object> hashMap3 = new HashMap<>();
        HashMap<String, Object> hashMap4 = new HashMap<>();
        JSONObject jsonObject1 = new JSONObject();
        JSONObject jsonObject2 = new JSONObject();
        JSONObject jsonObject3 = new JSONObject();
        JSONObject jsonObject4 = new JSONObject();
        /*选择题*/
        List<String> multiSubject = menuMapper.selectSubjectFromMulti();
        for (String element : multiSubject) {
            JSONObject jsonObject = new JSONObject();
            HashMap<String, Object> cascaderZ = new HashMap<>();
            cascaderZ.put("value", element);
            cascaderZ.put("label", element);
            List<HashMap<String, Object>> list = menuMapper.selectSectionFromMultiBySubject(element);
            getOneType(list, cascaderZ);
            jsonObject = new JSONObject(cascaderZ);
            cascader1.add(jsonObject);
        }
        hashMap1.put("value", "1");
        hashMap1.put("label", "选择题");
        hashMap1.put("children", cascader1);
        jsonObject1 = new JSONObject(hashMap1);
        cascader.add(jsonObject1);
        /*填空题*/
        List<String> fillSubject = menuMapper.selectSubjectFromFill();
        for (String element : fillSubject) {
            JSONObject jsonObject = new JSONObject();
            HashMap<String, Object> cascaderZ = new HashMap<>();
            cascaderZ.put("value", element);
            cascaderZ.put("label", element);
            List<HashMap<String, Object>> list = menuMapper.selectSectionFromFillBySubject(element);
            getOneType(list, cascaderZ);
            jsonObject = new JSONObject(cascaderZ);
            cascader2.add(jsonObject);
        }
        hashMap2.put("value", "2");
        hashMap2.put("label", "填空题");
        hashMap2.put("children", cascader2);
        jsonObject2 = new JSONObject(hashMap2);
        cascader.add(jsonObject2);
        /*判断题*/
        List<String> judgeSubject = menuMapper.selectSubjectFromJudge();
        for (String element : judgeSubject) {
            JSONObject jsonObject = new JSONObject();
            HashMap<String, Object> cascaderZ = new HashMap<>();
            cascaderZ.put("value", element);
            cascaderZ.put("label", element);
            List<HashMap<String, Object>> list = menuMapper.selectSectionFromJudgeBySubject(element);
            getOneType(list, cascaderZ);
            jsonObject = new JSONObject(cascaderZ);
            cascader3.add(jsonObject);
        }
        hashMap3.put("value", "3");
        hashMap3.put("label", "判断题");
        hashMap3.put("children", cascader3);
        jsonObject3 = new JSONObject(hashMap3);
        cascader.add(jsonObject3);
        /*简答题*/
        List<String> shortAnswerSubject = menuMapper.selectSubjectFromShortAnswer();
        for (String element : shortAnswerSubject) {
            JSONObject jsonObject = new JSONObject();
            HashMap<String, Object> cascaderZ = new HashMap<>();
            cascaderZ.put("value", element);
            cascaderZ.put("label", element);
            List<HashMap<String, Object>> list = menuMapper.selectSectionFromShortAnswerBySubject(element);
            getOneType(list, cascaderZ);
            jsonObject = new JSONObject(cascaderZ);
            cascader4.add(jsonObject);
        }
        hashMap4.put("value", "4");
        hashMap4.put("label", "简答题");
        hashMap4.put("children", cascader4);
        jsonObject4 = new JSONObject(hashMap4);
        cascader.add(jsonObject4);
        return cascader;
    }

    private void getOneType(List<HashMap<String, Object>> list, HashMap<String, Object> cascaderZ) {
        List<Object> obj = new ArrayList<>();
        for (HashMap<String, Object> ele : list) {
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("value", ele.get("section"));
            if (ele.get("section") == null || ele.get("section").toString().length() == 0) {
                hashMap.put("label", "其他");
            } else {
                hashMap.put("label", ele.get("section"));
            }
            hashMap.put("count", ele.get("count"));
            JSONObject jsonObject = new JSONObject(hashMap);
            obj.add(jsonObject);
        }
        cascaderZ.put("children", obj);
    }
}

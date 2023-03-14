package com.exam.paper.service;

import com.exam.paper.entity.Marking;
import com.exam.paper.entity.PaperManage;

import java.util.List;

public interface PaperService {

    List<PaperManage> findAll();

    List<PaperManage> findById(Integer paperId);

    int add(PaperManage paperManage);

    int delete(PaperManage paperManage);

    int insertAnswerSheet(Marking marking);
}

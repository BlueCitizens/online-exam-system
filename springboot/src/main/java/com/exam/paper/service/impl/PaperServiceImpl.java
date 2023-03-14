package com.exam.paper.service.impl;

import com.exam.paper.entity.Marking;
import com.exam.paper.entity.PaperManage;
import com.exam.paper.mapper.PaperMapper;
import com.exam.paper.service.PaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaperServiceImpl implements PaperService {

    @Autowired
    private PaperMapper paperMapper;
    @Override
    public List<PaperManage> findAll() {
        return paperMapper.findAll();
    }

    @Override
    public List<PaperManage> findById(Integer paperId) {
        return paperMapper.findById(paperId);
    }

    @Override
    public int add(PaperManage paperManage) {
        return paperMapper.add(paperManage);
    }

    @Override
    public int delete(PaperManage paperManage) {
        return paperMapper.delete(paperManage);
    }

    @Override
    public int insertAnswerSheet(Marking marking) {
        return paperMapper.insertAnswerSheet(marking);
    }

}

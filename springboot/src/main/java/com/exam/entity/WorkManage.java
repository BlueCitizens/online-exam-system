package com.exam.entity;

import lombok.Data;

@Data
public class WorkManage {
    private Integer workCode;

    private String description;

    private String source;

    private Integer paperId;

    private String createTime;

    private String deadline;

    private Integer createBy;

    private Integer clazz;

    private String major;

    private String institute;

    private Integer totalScore;

    private String tips;

    private String type;

    private Integer status;

    private String answerDate;

    private Integer score;
}
package com.exam.entity;

import lombok.Data;

@Data
public class ScoreWork {
    private Integer workCode;

    private Integer studentId;

    private String studentName;

    private String subject;

    private Integer obScore;

    private Integer suScore;

    private Integer score;

    private Integer scoreId;

    private String answerDate;
}
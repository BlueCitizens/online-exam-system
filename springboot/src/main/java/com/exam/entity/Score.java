package com.exam.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Score {
    private Integer workCode;

    private Integer studentId;

    private String subject;

    private String description;

    private Integer obScore;

    private Integer suScore;

    private Integer score;

    private Integer scoreId;

    private String answerDate;
}
package com.exam.question.entity;

import lombok.Data;

//简答题实体类
@Data
public class ShortAnswerQuestion {
    private Integer questionId;

    private String subject;

    private String question;

    private String answer;

    private Integer score;

    private String level;

    private String section;

    private String analysis; //题目解析
}

package com.example.quiz.quizapp;

/**
 * Created by csaper6 on 9/15/16.
 */
public class Question {
    private int questionId;
    private boolean isAnswerTrue;

    public Question(int questionId, boolean isAnswerTrue) {
        this.questionId = questionId;
        this.isAnswerTrue = isAnswerTrue;
    }

    public boolean checkAnswer(boolean b){
        return b==isAnswerTrue;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public boolean isAnswerTrue() {
        return isAnswerTrue;
    }

    public void setAnswerTrue(boolean answerTrue) {
        isAnswerTrue = answerTrue;
    }
}

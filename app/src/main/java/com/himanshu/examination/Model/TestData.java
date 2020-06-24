package com.himanshu.examination.Model;

public class TestData {

    String subject, marks, result, testTime, dateAndTime, description;

    public TestData() {
    }

    public TestData(String subject, String marks, String result, String testTime, String dateAndTime, String description) {
        this.subject = subject;
        this.marks = marks;
        this.result = result;
        this.testTime = testTime;
        this.dateAndTime = dateAndTime;
        this.description = description;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMarks() {
        return marks;
    }

    public void setMarks(String marks) {
        this.marks = marks;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getTestTime() {
        return testTime;
    }

    public void setTestTime(String testTime) {
        this.testTime = testTime;
    }

    public String getDateAndTime() {
        return dateAndTime;
    }

    public void setDateAndTime(String dateAndTime) {
        this.dateAndTime = dateAndTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

package com.himanshu.examination.Model;

public class ResultStore {

    String TestTime, Marks, Result, DateAndTime, Email, Name, Subject, Suggestion;

    public ResultStore() {
    }

    public ResultStore(String testTime, String marks, String result, String dateAndTime, String email, String name, String subject, String suggestion) {
        TestTime = testTime;
        Marks = marks;
        Result = result;
        DateAndTime = dateAndTime;
        Email = email;
        Name = name;
        Subject = subject;
        Suggestion = suggestion;
    }

    public String getTestTime() {
        return TestTime;
    }

    public void setTestTime(String testTime) {
        TestTime = testTime;
    }

    public String getMarks() {
        return Marks;
    }

    public void setMarks(String marks) {
        Marks = marks;
    }

    public String getResult() {
        return Result;
    }

    public void setResult(String result) {
        Result = result;
    }

    public String getDateAndTime() {
        return DateAndTime;
    }

    public void setDateAndTime(String dateAndTime) {
        DateAndTime = dateAndTime;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getSubject() {
        return Subject;
    }

    public void setSubject(String subject) {
        Subject = subject;
    }

    public String getSuggestion() {
        return Suggestion;
    }

    public void setSuggestion(String suggestion) {
        Suggestion = suggestion;
    }
}

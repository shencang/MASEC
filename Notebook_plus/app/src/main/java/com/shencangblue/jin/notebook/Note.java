package com.shencangblue.jin.notebook;

public class Note {
    String theme;
    String content;
    String date;
    public Note(){

    }
    public Note(String theme,String content,String date){
        this.theme =theme;
        this.content=content;
        this.date = date;

    }
    public Note(String theme){
        this.theme=theme;
    }

    public String getTheme() {
        return theme;
    }

    public String getDate() {
        return date;
    }

    public String getContent() {
        return content;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setDate(String date) {
        this.date = date;
    }
}

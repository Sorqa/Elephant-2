package com.example.elephant;

public class List {
    private String contents;
    private String time;
    private String day;
    private String now;                                                                             //key 값을 위해

    public List(String contents, String time, String day, String now) {
        this.contents = contents;
        this.time = time;
        this.day = day;
        this.now = now;
    }
    //settingmain에서 저장되면 여기로 받아야 추가될듯
    //retrieve list's contents
    public String getContents()  {

        return contents;
    }

    public void setTitle(String contents) {
        this.contents = contents;
    }
    //날짜와 시간은 형식 바꿔야 할 수도 있다 이 경우 simpledateformat 사용하면 좋을듯
    public String getTime(){
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    //retrieve list' day
    public String getDay(){
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getNow(){return now;}

}

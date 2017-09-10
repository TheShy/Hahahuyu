package com.touyuanren.hahahuyu.bean;

//节目单bean
public class TimeLineModel {
    //显示的东西：节目时间，节目标题，节目人，显示进度的图标；
    private String programmeTime;
    private String programmeTitle;
    private String programmePeople;
    private int programmeImg;

    public TimeLineModel() {

    }

    public TimeLineModel(String programmeTime, String programmeTitle, String programmePeople, int programmeImg) {
        this.programmeTime = programmeTime;
        this.programmeTitle = programmeTitle;
        this.programmePeople = programmePeople;
        this.programmeImg = programmeImg;
    }

    public String getProgrammeTime() {
        return programmeTime;
    }

    public void setProgrammeTime(String programmeTime) {
        this.programmeTime = programmeTime;
    }

    public String getProgrammeTitle() {
        return programmeTitle;
    }

    public void setProgrammeTitle(String programmeTitle) {
        this.programmeTitle = programmeTitle;
    }

    public String getProgrammePeople() {
        return programmePeople;
    }

    public void setProgrammePeople(String programmePeople) {
        this.programmePeople = programmePeople;
    }

    public int getProgrammeImg() {
        return programmeImg;
    }

    public void setProgrammeImg(int programmeImg) {
        this.programmeImg = programmeImg;
    }
}

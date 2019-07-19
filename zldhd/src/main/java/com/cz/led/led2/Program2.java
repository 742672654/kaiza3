package com.cz.led.led2;

 

import java.util.ArrayList;
import java.util.List;

public class Program2
{

    public Program2()
    {
        listArea = new ArrayList();
        listArea = new ArrayList();
    }

    public Program2(String szStartTime, String szStopTime, int nWeekDay, List listArea)
    {
        this.listArea = new ArrayList();
        this.szStartTime = szStartTime;
        this.szStopTime = szStopTime;
        this.nWeekDay = nWeekDay;
        this.listArea = listArea;
    }

    public String getSzStartTime()
    {
        return szStartTime;
    }

    public void setSzStartTime(String szStartTime)
    {
        this.szStartTime = szStartTime;
    }

    public String getSzStopTime()
    {
        return szStopTime;
    }

    public void setSzStopTime(String szStopTime)
    {
        this.szStopTime = szStopTime;
    }

    public int getnWeekDay()
    {
        return nWeekDay;
    }

    public void setnWeekDay(int nWeekDay)
    {
        this.nWeekDay = nWeekDay;
    }

    public List getListArea()
    {
        return listArea;
    }

    public void setListArea(List listArea)
    {
        this.listArea = listArea;
    }

    private String szStartTime;
    private String szStopTime;
    private int nWeekDay;
    private List listArea;
}


/*
	DECOMPILATION REPORT

	Decompiled from: D:\workspace\ad\WebRoot\WEB-INF\lib\qyled.jar
	Total time: 39 ms
	Jad reported messages/errors:
	ExitBase status: 0
	Caught exceptions:
*/
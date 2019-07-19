package com.cz.led.led2;

 

import java.util.ArrayList;
import java.util.List;

public class Area2
{

    public Area2()
    {
        listMaterial = new ArrayList();
        listMaterial = new ArrayList();
    }

    public Area2(int nXPos, int nYPos, int nAreaWidth, int nAreaHigth, List listMaterial)
    {
        this.listMaterial = new ArrayList();
        this.nXPos = nXPos;
        this.nYPos = nYPos;
        this.nAreaWidth = nAreaWidth;
        this.nAreaHigth = nAreaHigth;
        this.listMaterial = listMaterial;
    }

    public int getnXPos()
    {
        return nXPos;
    }

    public void setnXPos(int nXPos)
    {
        this.nXPos = nXPos;
    }

    public int getnYPos()
    {
        return nYPos;
    }

    public void setnYPos(int nYPos)
    {
        this.nYPos = nYPos;
    }

    public int getnAreaWidth()
    {
        return nAreaWidth;
    }

    public void setnAreaWidth(int nAreaWidth)
    {
        this.nAreaWidth = nAreaWidth;
    }

    public int getnAreaHigth()
    {
        return nAreaHigth;
    }

    public void setnAreaHigth(int nAreaHigth)
    {
        this.nAreaHigth = nAreaHigth;
    }

    public List getListMaterial()
    {
        return listMaterial;
    }

    public void setListMaterial(List listMaterial)
    {
        this.listMaterial = listMaterial;
    }

    private int nXPos;
    private int nYPos;
    private int nAreaWidth;
    private int nAreaHigth;
    private List listMaterial;
}


/*
	DECOMPILATION REPORT

	Decompiled from: D:\workspace\ad\WebRoot\WEB-INF\lib\qyled.jar
	Total time: 40 ms
	Jad reported messages/errors:
	ExitBase status: 0
	Caught exceptions:
*/
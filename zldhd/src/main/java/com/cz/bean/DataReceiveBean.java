package com.cz.bean;


import com.vz.PlateResult;
import java.util.Arrays;


public class DataReceiveBean {

    private int handle;
    private PlateResult plateResult;
    private int uNumPlates;
    private int eResultType;
    private byte[] pImgFull;
    private int nFullSize;
    private byte[] pImgPlateClip;
    private int nClipSize;

    public DataReceiveBean(int handle, PlateResult plateResult, int uNumPlates, int eResultType, byte[] pImgFull, int nFullSize, byte[] pImgPlateClip, int nClipSize) {
        this.handle = handle;
        this.plateResult = plateResult;
        this.uNumPlates = uNumPlates;
        this.eResultType = eResultType;
        this.pImgFull = pImgFull;
        this.nFullSize = nFullSize;
        this.pImgPlateClip = pImgPlateClip;
        this.nClipSize = nClipSize;
    }


    public int getHandle() {
        return handle;
    }
    public void setHandle(int handle) {
        this.handle = handle;
    }
    public PlateResult getPlateResult() {
        return plateResult;
    }
    public void setPlateResult(PlateResult plateResult) {
        this.plateResult = plateResult;
    }
    public int getuNumPlates() {
        return uNumPlates;
    }
    public void setuNumPlates(int uNumPlates) {
        this.uNumPlates = uNumPlates;
    }
    public int geteResultType() {
        return eResultType;
    }
    public void seteResultType(int eResultType) {
        this.eResultType = eResultType;
    }
    public byte[] getpImgFull() {
        return pImgFull;
    }
    public void setpImgFull(byte[] pImgFull) {
        this.pImgFull = pImgFull;
    }
    public int getnFullSize() {
        return nFullSize;
    }
    public void setnFullSize(int nFullSize) {
        this.nFullSize = nFullSize;
    }
    public byte[] getpImgPlateClip() {
        return pImgPlateClip;
    }
    public void setpImgPlateClip(byte[] pImgPlateClip) {
        this.pImgPlateClip = pImgPlateClip;
    }
    public int getnClipSize() {
        return nClipSize;
    }
    public void setnClipSize(int nClipSize) {
        this.nClipSize = nClipSize;
    }


    @Override
    public String toString() {
        return "DataReceiveBean{" +
                "handle=" + handle +
                ", plateResult=" + plateResult +
                ", uNumPlates=" + uNumPlates +
                ", eResultType=" + eResultType +
                ", pImgFull=" + Arrays.toString(pImgFull) +
                ", nFullSize=" + nFullSize +
                ", pImgPlateClip=" + Arrays.toString(pImgPlateClip) +
                ", nClipSize=" + nClipSize +
                '}';
    }

}

package com.cz.led.led2;

 

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Random;

public class MaterialBuffer2
{

    public MaterialBuffer2()
    {
    }

    public static byte[] InternalTextBuffer(String szShowContent, int nAreaWidth, int nAreaHigth, int nUID, int nScreenColor, int nShowStyle, int nShowSpeed, int nStopTime, 
            int nFontColor, int nFontBody, int nFontSize, int nUpdateStyle, boolean bPowerOffSave, boolean bEnd)
    {
        byte byPacketBuffer[] = new byte[0];
        try
        {
            byte byContent[] = szShowContent.getBytes("GB2312");
            int nContentLen = byContent.length;
            int nPackLen = nContentLen + 62;
            if(bEnd)
                nPackLen += 3;
            byPacketBuffer = new byte[nPackLen];
            int nArrIndex = 0;
            String szUID = String.format("%09d", new Object[] {
                Integer.valueOf(nUID)
            });
            for(int i = 0; i < 9; i++)
                byPacketBuffer[nArrIndex++] = (byte)szUID.charAt(i);

            byPacketBuffer[nArrIndex++] = 44;
            byPacketBuffer[nArrIndex++] = (byte)nShowStyle;
            byPacketBuffer[nArrIndex++] = (byte)nShowSpeed;
            byPacketBuffer[nArrIndex++] = (byte)nStopTime;
            byPacketBuffer[nArrIndex++] = 48;
            byPacketBuffer[nArrIndex++] = 49;
            byPacketBuffer[nArrIndex++] = 48;
            byPacketBuffer[nArrIndex++] = 49;
            byPacketBuffer[nArrIndex++] = 48;
            byPacketBuffer[nArrIndex++] = 49;
            byPacketBuffer[nArrIndex++] = 57;
            byPacketBuffer[nArrIndex++] = 57;
            byPacketBuffer[nArrIndex++] = 49;
            byPacketBuffer[nArrIndex++] = 50;
            byPacketBuffer[nArrIndex++] = 51;
            byPacketBuffer[nArrIndex++] = 49;
            byPacketBuffer[nArrIndex++] = 19;
            byPacketBuffer[nArrIndex++] = 0;
            byPacketBuffer[nArrIndex++] = 0;
            byPacketBuffer[nArrIndex++] = 0;
            byPacketBuffer[nArrIndex++] = 85;
            byPacketBuffer[nArrIndex++] = -86;
            byPacketBuffer[nArrIndex++] = 0;
            byPacketBuffer[nArrIndex++] = 0;
            byPacketBuffer[nArrIndex++] = 55;
            byPacketBuffer[nArrIndex] = 50;
            if(bPowerOffSave)
                byPacketBuffer[nArrIndex] = 49;
            nArrIndex++;
            byPacketBuffer[nArrIndex] = 49;
            if(nUpdateStyle == 2)
                byPacketBuffer[nArrIndex] = 50;
            else
            if(nUpdateStyle == 3)
                byPacketBuffer[nArrIndex] = 51;
            nArrIndex++;
            byPacketBuffer[nArrIndex++] = 49;
            byPacketBuffer[nArrIndex] = 49;
            if(nScreenColor == 1)
                byPacketBuffer[nArrIndex] = 50;
            else
            if(nScreenColor == 2)
                byPacketBuffer[nArrIndex] = 51;
            nArrIndex++;
            byPacketBuffer[nArrIndex++] = 49;
            byPacketBuffer[nArrIndex++] = 0;
            byPacketBuffer[nArrIndex++] = 0;
            int nWidth = nAreaWidth / 8;
            byPacketBuffer[nArrIndex++] = (byte)nWidth;
            byPacketBuffer[nArrIndex++] = (byte)(nWidth >> 8);
            byPacketBuffer[nArrIndex++] = (byte)nAreaHigth;
            byPacketBuffer[nArrIndex++] = (byte)(nAreaHigth >> 8);
            byPacketBuffer[nArrIndex++] = (byte)nFontColor;
            byPacketBuffer[nArrIndex++] = (byte)(nFontBody << 4 | nFontSize);
            byPacketBuffer[nArrIndex++] = 0;
            int nLen = nContentLen + 10;
            byPacketBuffer[nArrIndex++] = (byte)nLen;
            byPacketBuffer[nArrIndex++] = (byte)(nLen >> 8);
            byPacketBuffer[nArrIndex++] = (byte)(nLen >> 16);
            byPacketBuffer[nArrIndex++] = (byte)(nLen >> 24);
            for(int i = 0; i < nContentLen; i++)
                byPacketBuffer[nArrIndex++] = byContent[i];

            byPacketBuffer[nArrIndex++] = -1;
            byPacketBuffer[nArrIndex++] = 0;
            byPacketBuffer[nArrIndex++] = 1;
            byPacketBuffer[nArrIndex++] = 0;
            byPacketBuffer[nArrIndex++] = 1;
            byPacketBuffer[nArrIndex++] = 0;
            byPacketBuffer[nArrIndex++] = 1;
            byPacketBuffer[nArrIndex++] = 0;
            byPacketBuffer[nArrIndex++] = 0;
            byPacketBuffer[nArrIndex++] = 0;
            if(bEnd)
            {
                byPacketBuffer[nArrIndex++] = 45;
                byPacketBuffer[nArrIndex++] = 49;
                byPacketBuffer[nArrIndex++] = 44;
            }
        }
        catch(Exception exception) { }
        return byPacketBuffer;
    }

    public static byte[] VoiceBuffer(String szPlayContent)
    {
        byte byPacketBuffer[] = new byte[0];
        try
        {
            byte byContent[] = szPlayContent.getBytes("GB2312");
            int nContentLen = byContent.length;
            int nPackLen = nContentLen + 2;
            byPacketBuffer = new byte[nPackLen + 5];
            int nArrIndex = 0;
            byPacketBuffer[nArrIndex++] = -3;
            byPacketBuffer[nArrIndex++] = (byte)(nPackLen >> 8);
            byPacketBuffer[nArrIndex++] = (byte)nPackLen;
            byPacketBuffer[nArrIndex++] = 1;
            byPacketBuffer[nArrIndex++] = 0;
            for(int i = 0; i < nContentLen; i++)
                byPacketBuffer[nArrIndex++] = byContent[i];

            byPacketBuffer[nArrIndex++] = 0;
            byPacketBuffer[nArrIndex++] = 0;
        }
        catch(Exception exception) { }
        return byPacketBuffer;
    }

    public static byte[] CollectionBuffer(String szShowContent, int nUID, boolean bPowerSave, boolean bBlink, short nNumColour, short nUnitColour, short nFontBody, short nFontSize)
    {
        byte byPacketBuffer[] = new byte[0];
        try
        {
            byte byContent[] = szShowContent.getBytes("GB2312");
            int nContentLen = byContent.length;
            int nPackLen = nContentLen + 5;
            byPacketBuffer = new byte[nPackLen];
            int nArrIndex = 0;
            byPacketBuffer[nArrIndex++] = (byte)nUID;
            byte chBit[] = new byte[8];
            int nArrBitIndex = 0;
            if(bBlink)
            {
                chBit[nArrBitIndex++] = 1;
                chBit[nArrBitIndex++] = 0;
                chBit[nArrBitIndex++] = 1;
                chBit[nArrBitIndex++] = 0;
            }
            if(bPowerSave)
            {
                nArrBitIndex = 4;
                chBit[nArrBitIndex++] = 1;
                chBit[nArrBitIndex++] = 0;
                chBit[nArrBitIndex++] = 1;
                chBit[nArrBitIndex++] = 0;
            }
            short nValue = 0;
            for(int i = 0; i < 8; i++)
                nValue = (short)(int)((double)nValue + (double)chBit[i] * Math.pow(2D, i));

            byPacketBuffer[nArrIndex++] = (byte)nValue;
            byPacketBuffer[nArrIndex++] = (byte)(nNumColour << 4 | nUnitColour);
            byPacketBuffer[nArrIndex++] = (byte)(nFontBody << 4 | nFontSize);
            byPacketBuffer[nArrIndex++] = (byte)nContentLen;
            for(int i = 0; i < nContentLen; i++)
                byPacketBuffer[nArrIndex++] = byContent[i];

        }
        catch(Exception exception) { }
        return byPacketBuffer;
    }

    public static byte[] CompletePacketBuffer(byte byContent[], short nMsgType, int nForwordPort)
    {
        int nContentLen = byContent.length;
        int nPackLen = nContentLen + 19;
        byte byPacketBuffer[] = new byte[nPackLen];
        int nArrIndex = 0;
        byPacketBuffer[nArrIndex++] = -2;
        byPacketBuffer[nArrIndex++] = 92;
        byPacketBuffer[nArrIndex++] = 75;
        byPacketBuffer[nArrIndex++] = -119;
        byPacketBuffer[nArrIndex++] = (byte)nPackLen;
        byPacketBuffer[nArrIndex++] = (byte)(nPackLen >> 8);
        byPacketBuffer[nArrIndex++] = (byte)(nPackLen >> 16);
        byPacketBuffer[nArrIndex++] = (byte)(nPackLen >> 24);
        byPacketBuffer[nArrIndex++] = (byte)nMsgType;
        byPacketBuffer[nArrIndex++] = (byte)nForwordPort;
        byPacketBuffer[nArrIndex++] = 0;
        byPacketBuffer[nArrIndex++] = 0;
        byPacketBuffer[nArrIndex++] = 0;
        byPacketBuffer[nArrIndex++] = (byte)nContentLen;
        byPacketBuffer[nArrIndex++] = (byte)(nContentLen >> 8);
        byPacketBuffer[nArrIndex++] = (byte)(nContentLen >> 16);
        byPacketBuffer[nArrIndex++] = (byte)(nContentLen >> 24);
        for(int i = 0; i < nContentLen; i++)
            byPacketBuffer[nArrIndex++] = byContent[i];

        byPacketBuffer[nArrIndex++] = -1;
        byPacketBuffer[nArrIndex++] = -1;
        return byPacketBuffer;
    }

    public static byte[] PackShowpPageBuffer(List listProgram)
    {
        byte arrBuffer[] = new byte[4000];
        int nArrIndex = 0;
        int nSize = listProgram.size();
        for(int nShowPage = 0; nShowPage < nSize; nShowPage++)
        {
            Program2 program2 = (Program2)listProgram.get(nShowPage);
            nArrIndex = 0;
            for(int i = 0; i < 9; i++)
                arrBuffer[nArrIndex++] = (byte)((new Random()).nextInt(8) + 1);

            arrBuffer[nArrIndex++] = 44;
            byte arrStartTime[] = program2.getSzStartTime().getBytes();
            for(int nInc = 0; nInc < arrStartTime.length; nInc++)
                arrBuffer[nArrIndex++] = arrStartTime[nInc];

            arrBuffer[nArrIndex++] = 45;
            byte arrStopTime[] = program2.getSzStopTime().getBytes();
            for(int nInc = 0; nInc < arrStopTime.length; nInc++)
                arrBuffer[nArrIndex++] = arrStopTime[nInc];

            arrBuffer[nArrIndex++] = 46;
            int nAreaSize = program2.getListArea().size();
            for(int nArea = 0; nArea < nAreaSize; nArea++)
            {
                Area2 area2 = (Area2)program2.getListArea().get(nArea);
                int nMaterialSize = area2.getListMaterial().size();
                for(int nMaterial = 0; nMaterial < nMaterialSize; nMaterial++)
                {
                    Material2 material2 = (Material2)area2.getListMaterial().get(nMaterial);
                    byte arrUID[] = String.format("%09d", new Object[] {
                        Integer.valueOf(material2.getnUID())
                    }).getBytes();
                    for(int nInc = 0; nInc < arrUID.length; nInc++)
                        arrBuffer[nArrIndex++] = arrUID[nInc];

                    arrBuffer[nArrIndex] = (byte)material2.getnContentType();
                    if(material2.getnContentType() == 57 || material2.getnContentType() == 58)
                        arrBuffer[nArrIndex] = 56;
                    nArrIndex++;
                    int nXPos = area2.getnXPos() / 8;
                    byte arrXPos[] = String.format("%03d", new Object[] {
                        Integer.valueOf(nXPos)
                    }).getBytes();
                    for(int nInc = 0; nInc < arrXPos.length; nInc++)
                        arrBuffer[nArrIndex++] = arrXPos[nInc];

                    int nYPos = area2.getnYPos();
                    byte arrYPos[] = String.format("%03d", new Object[] {
                        Integer.valueOf(nYPos)
                    }).getBytes();
                    for(int nInc = 0; nInc < arrYPos.length; nInc++)
                        arrBuffer[nArrIndex++] = arrYPos[nInc];

                    int nWidth = area2.getnAreaWidth() / 8;
                    byte arrWidth[] = String.format("%03d", new Object[] {
                        Integer.valueOf(nWidth)
                    }).getBytes();
                    for(int nInc = 0; nInc < arrWidth.length; nInc++)
                        arrBuffer[nArrIndex++] = arrWidth[nInc];

                    int nHeight = area2.getnAreaHigth();
                    byte arrHeight[] = String.format("%03d", new Object[] {
                        Integer.valueOf(nHeight)
                    }).getBytes();
                    for(int nInc = 0; nInc < arrHeight.length; nInc++)
                        arrBuffer[nArrIndex++] = arrHeight[nInc];

                    arrBuffer[nArrIndex++] = 49;
                    arrBuffer[nArrIndex++] = 48;
                    arrBuffer[nArrIndex++] = 48;
                    arrBuffer[nArrIndex++] = 48;
                    arrBuffer[nArrIndex++] = 48;
                    arrBuffer[nArrIndex++] = 48;
                    arrBuffer[nArrIndex++] = 48;
                    arrBuffer[nArrIndex++] = 48;
                    arrBuffer[nArrIndex++] = 48;
                    arrBuffer[nArrIndex++] = 48;
                    arrBuffer[nArrIndex++] = 35;
                    arrBuffer[nArrIndex++] = 48;
                    arrBuffer[nArrIndex++] = 49;
                    arrBuffer[nArrIndex++] = 35;
                    arrBuffer[nArrIndex++] = 49;
                    arrBuffer[nArrIndex++] = 48;
                    arrBuffer[nArrIndex++] = 59;
                }

            }

        }

        int nLen = nArrIndex;
        byte arrPackBuffer[] = new byte[nLen];
        System.arraycopy(arrBuffer, 0, arrPackBuffer, 0, nLen);
        return arrPackBuffer;
    }

    public static byte[] PlayShowPageBuffer(int nShowPageNo)
    {
        byte byPacketBuffer[] = new byte[2];
        int nArrIndex = 0;
        byPacketBuffer[nArrIndex++] = (byte)nShowPageNo;
        byPacketBuffer[nArrIndex++] = (byte)(255 - nShowPageNo);
        return byPacketBuffer;
    }

    public static byte char2Byte(char c)
    {
        if('a' <= c && c <= 'f')
            return (byte)((c - 97) + 10);
        if('A' <= c && c <= 'F')
            return (byte)((c - 65) + 10);
        if('0' <= c && c <= '9')
            return (byte)(c - 48);
        else
            return -1;
    }

    public static byte[] CollectionBuffer_Template(Material2 material2)
    {
        byte arrBuffer[] = new byte[17];
        int nArrIndex = 0;
        arrBuffer[nArrIndex++] = 49;
        arrBuffer[nArrIndex++] = (byte)material2.getnTypeNo();
        arrBuffer[nArrIndex++] = 1;
        arrBuffer[nArrIndex++] = Integer.valueOf("00110110", 2).byteValue();
        String szHighBins = String.format("%04d", new Object[] {
            Integer.valueOf(Integer.parseInt(Integer.toBinaryString(material2.getnFontColor())))
        });
        String szLowBins = String.format("%04d", new Object[] {
            Integer.valueOf(Integer.parseInt(Integer.toBinaryString(material2.getnFontColor())))
        });
        Integer nBins = Integer.valueOf((new StringBuilder(String.valueOf(szHighBins))).append(szLowBins).toString(), 2);
        arrBuffer[nArrIndex++] = nBins.byteValue();
        szHighBins = String.format("%04d", new Object[] {
            Integer.valueOf(Integer.parseInt(Integer.toBinaryString(material2.getnFontBody())))
        });
        szLowBins = String.format("%04d", new Object[] {
            Integer.valueOf(Integer.parseInt(Integer.toBinaryString(material2.getnFontSize())))
        });
        nBins = Integer.valueOf((new StringBuilder(String.valueOf(szHighBins))).append(szLowBins).toString(), 2);
        arrBuffer[nArrIndex++] = nBins.byteValue();
        arrBuffer[nArrIndex++] = 0;
        for(int nUnit = 0; nUnit < 10; nUnit++)
            arrBuffer[nArrIndex++] = 0;

        byte arrPackBuffer[] = TemplateBuffer(arrBuffer, 54, 0, 0, material2.getnUID(), material2.getnScreenColor(), material2.getnShowStyle(), material2.getnShowSpeed(), material2.getnStopTime(), material2.getnFontColor(), material2.getnFontBody(), material2.getnFontSize(), material2.getnUpdateStyle(), material2.getbPowerOffSave(), 1, 1, 1, 1, true);
        return arrPackBuffer;
    }

    public static byte[] InternalTextBuffer_Template(String szShowContent, int nAreaWidth, int nAreaHigth, int nUID, int nScreenColor, int nShowStyle, int nShowSpeed, int nStopTime, 
            int nFontColor, int nFontBody, int nFontSize, int nUpdateStyle, boolean bPowerOffSave, boolean bEnd)
    {
        byte arrPackBuffer[] = new byte[0];
        try
        {
            arrPackBuffer = TemplateBuffer(szShowContent.getBytes("GB2312"), 55, nAreaWidth, nAreaHigth, nUID, nScreenColor, nShowStyle, nShowSpeed, nStopTime, nFontColor, nFontBody, nFontSize, nUpdateStyle, bPowerOffSave, 1, 1, 1, 0, bEnd);
        }
        catch(UnsupportedEncodingException unsupportedencodingexception) { }
        return arrPackBuffer;
    }

    public static byte[] DateTimeBuffer(int nUID, int nScreenColor, int nNumColor, int nChrColor, int nFontBody, int nFontSize, int nYearLen, int nTimeFormat, 
            int nShowFormat, int nTimeDifSet, int nHourSpan, int nMinSpan, int nStopTime)
    {
        byte arrBuffer[] = new byte[7];
        int nArrIndex = 0;
        arrBuffer[nArrIndex++] = (byte)nTimeFormat;
        arrBuffer[nArrIndex++] = (byte)nShowFormat;
        String szHighBins = String.format("%01d", new Object[] {
            Integer.valueOf(Integer.parseInt(Integer.toBinaryString(nTimeDifSet)))
        });
        String szLowBins = String.format("%07d", new Object[] {
            Integer.valueOf(Integer.parseInt(Integer.toBinaryString(nHourSpan)))
        });
        Integer nBins = Integer.valueOf((new StringBuilder(String.valueOf(szHighBins))).append(szLowBins).toString(), 2);
        arrBuffer[nArrIndex++] = nBins.byteValue();
        szHighBins = String.format("%01d", new Object[] {
            Integer.valueOf(Integer.parseInt(Integer.toBinaryString(nTimeDifSet)))
        });
        szLowBins = String.format("%07d", new Object[] {
            Integer.valueOf(Integer.parseInt(Integer.toBinaryString(nMinSpan)))
        });
        nBins = Integer.valueOf((new StringBuilder(String.valueOf(szHighBins))).append(szLowBins).toString(), 2);
        arrBuffer[nArrIndex++] = nBins.byteValue();
        szHighBins = String.format("%04d", new Object[] {
            Integer.valueOf(Integer.parseInt(Integer.toBinaryString(nNumColor)))
        });
        szLowBins = String.format("%04d", new Object[] {
            Integer.valueOf(Integer.parseInt(Integer.toBinaryString(nChrColor)))
        });
        nBins = Integer.valueOf((new StringBuilder(String.valueOf(szHighBins))).append(szLowBins).toString(), 2);
        arrBuffer[nArrIndex++] = nBins.byteValue();
        szHighBins = String.format("%04d", new Object[] {
            Integer.valueOf(Integer.parseInt(Integer.toBinaryString(nFontBody)))
        });
        szLowBins = String.format("%04d", new Object[] {
            Integer.valueOf(Integer.parseInt(Integer.toBinaryString(nFontSize)))
        });
        nBins = Integer.valueOf((new StringBuilder(String.valueOf(szHighBins))).append(szLowBins).toString(), 2);
        arrBuffer[nArrIndex++] = nBins.byteValue();
        szHighBins = String.format("%04d", new Object[] {
            Integer.valueOf(Integer.parseInt(Integer.toBinaryString(0)))
        });
        szLowBins = String.format("%04d", new Object[] {
            Integer.valueOf(Integer.parseInt(Integer.toBinaryString(nYearLen)))
        });
        nBins = Integer.valueOf((new StringBuilder(String.valueOf(szHighBins))).append(szLowBins).toString(), 2);
        arrBuffer[nArrIndex++] = nBins.byteValue();
        byte arrPackBuffer[] = TemplateBuffer(arrBuffer, 49, 0, 0, nUID, nScreenColor, 0, 0, nStopTime, nNumColor, nFontBody, nFontSize, 1, false, 1, 1, 1, 1, true);
        return arrPackBuffer;
    }

    public static byte[] TemplateBuffer(byte byContent[], int nContentType, int nAreaWidth, int nAreaHigth, int nUID, int nScreenColor, int nShowStyle, int nShowSpeed, 
            int nStopTime, int nFontColor, int nFontBody, int nFontSize, int nUpdateStyle, boolean bPowerOffSave, int nFileNo, 
            int nPageNo, int nPageNum, int nImageCount, boolean bEnd)
    {
        byte arrBuffer[] = new byte[4000];
        int nArrIndex = 0;
        byte arrUID[] = String.format("%09d", new Object[] {
            Integer.valueOf(nUID)
        }).getBytes();
        for(int nInc = 0; nInc < arrUID.length; nInc++)
            arrBuffer[nArrIndex++] = arrUID[nInc];

        arrBuffer[nArrIndex++] = 44;
        arrBuffer[nArrIndex++] = (byte)nShowStyle;
        arrBuffer[nArrIndex++] = (byte)nShowSpeed;
        arrBuffer[nArrIndex++] = (byte)nStopTime;
        arrBuffer[nArrIndex++] = 48;
        arrBuffer[nArrIndex++] = 49;
        arrBuffer[nArrIndex++] = 48;
        arrBuffer[nArrIndex++] = 49;
        arrBuffer[nArrIndex++] = 48;
        arrBuffer[nArrIndex++] = 49;
        arrBuffer[nArrIndex++] = 57;
        arrBuffer[nArrIndex++] = 57;
        arrBuffer[nArrIndex++] = 49;
        arrBuffer[nArrIndex++] = 50;
        arrBuffer[nArrIndex++] = 51;
        arrBuffer[nArrIndex++] = 49;
        int nAttrLen = 14;
        if(nContentType == 55)
            nAttrLen += 5;
        arrBuffer[nArrIndex++] = (byte)nAttrLen;
        arrBuffer[nArrIndex++] = 0;
        arrBuffer[nArrIndex++] = 0;
        arrBuffer[nArrIndex++] = 0;
        arrBuffer[nArrIndex++] = 85;
        arrBuffer[nArrIndex++] = -86;
        arrBuffer[nArrIndex++] = 0;
        arrBuffer[nArrIndex++] = 0;
        arrBuffer[nArrIndex++] = (byte)nContentType;
        arrBuffer[nArrIndex] = 50;
        if(bPowerOffSave)
            arrBuffer[nArrIndex] = 49;
        nArrIndex++;
        arrBuffer[nArrIndex] = 49;
        if(nUpdateStyle == 2)
            arrBuffer[nArrIndex] = 50;
        else
        if(nUpdateStyle == 3)
            arrBuffer[nArrIndex] = 51;
        nArrIndex++;
        arrBuffer[nArrIndex++] = 49;
        arrBuffer[nArrIndex] = 49;
        if(nScreenColor == 1)
            arrBuffer[nArrIndex] = 50;
        else
        if(nScreenColor == 2)
            arrBuffer[nArrIndex] = 51;
        nArrIndex++;
        arrBuffer[nArrIndex++] = 49;
        arrBuffer[nArrIndex++] = (byte)nImageCount;
        arrBuffer[nArrIndex++] = (byte)(nImageCount >> 8);
        if(nContentType == 55)
        {
            int nWidth = nAreaWidth / 8;
            arrBuffer[nArrIndex++] = (byte)nWidth;
            arrBuffer[nArrIndex++] = (byte)(nWidth >> 8);
            arrBuffer[nArrIndex++] = (byte)nAreaHigth;
            arrBuffer[nArrIndex++] = (byte)(nAreaHigth >> 8);
            arrBuffer[nArrIndex++] = (byte)nFontColor;
            arrBuffer[nArrIndex++] = (byte)(nFontBody << 4 | nFontSize);
        } else
        {
            arrBuffer[nArrIndex++] = 0;
        }
        arrBuffer[nArrIndex++] = 0;
        int nContentLen = byContent.length;
        int nLen = nContentLen + 10;
        arrBuffer[nArrIndex++] = (byte)nLen;
        arrBuffer[nArrIndex++] = (byte)(nLen >> 8);
        arrBuffer[nArrIndex++] = (byte)(nLen >> 16);
        arrBuffer[nArrIndex++] = (byte)(nLen >> 24);
        for(int i = 0; i < nContentLen; i++)
            arrBuffer[nArrIndex++] = byContent[i];

        arrBuffer[nArrIndex++] = -1;
        arrBuffer[nArrIndex++] = 0;
        arrBuffer[nArrIndex++] = (byte)nFileNo;
        arrBuffer[nArrIndex++] = (byte)(nFileNo >> 8);
        arrBuffer[nArrIndex++] = (byte)nPageNum;
        arrBuffer[nArrIndex++] = (byte)(nPageNum >> 8);
        arrBuffer[nArrIndex++] = (byte)nPageNo;
        arrBuffer[nArrIndex++] = (byte)(nPageNo >> 8);
        arrBuffer[nArrIndex++] = 0;
        arrBuffer[nArrIndex++] = 0;
        if(bEnd)
        {
            arrBuffer[nArrIndex++] = 45;
            arrBuffer[nArrIndex++] = 49;
            arrBuffer[nArrIndex++] = 44;
        }
        nLen = nArrIndex;
        byte arrPackBuffer[] = new byte[nLen];
        System.arraycopy(arrBuffer, 0, arrPackBuffer, 0, nLen);
        return arrPackBuffer;
    }

    public static byte[] RelayBuffer(int nCircuitNo, int nSwitchStatus, int nDelayTime)
    {
        byte arrBuffer[] = new byte[6];
        int nArrIndex = 0;
        byte chControlWord = -1;
        if(nDelayTime <= 0)
        {
            if(nSwitchStatus == 1)
                chControlWord = -4;
            else
            if(nSwitchStatus == 2)
                chControlWord = -3;
        } else
        if(nDelayTime > 0 && nDelayTime <= 16)
            chControlWord = (byte)(175 + nDelayTime);
        arrBuffer[nArrIndex++] = -1;
        arrBuffer[nArrIndex++] = 0;
        arrBuffer[nArrIndex++] = -1;
        arrBuffer[nArrIndex++] = 0;
        arrBuffer[nArrIndex++] = -1;
        arrBuffer[nArrIndex++] = 0;
        if(nCircuitNo >= 1 && nCircuitNo <= 3)
        {
            arrBuffer[(nCircuitNo - 1) * 2] = chControlWord;
            arrBuffer[(nCircuitNo - 1) * 2 + 1] = (byte)(255 - chControlWord);
        }
        return arrBuffer;
    }

    public static byte[] SetBrightBuffer(int nPriority, int nBrightValue)
    {
        byte arrBuffer[] = new byte[4];
        int nArrIndex = 0;
        arrBuffer[nArrIndex++] = (byte)nPriority;
        arrBuffer[nArrIndex++] = (byte)(255 - nPriority);
        arrBuffer[nArrIndex++] = (byte)nBrightValue;
        arrBuffer[nArrIndex++] = (byte)(255 - nBrightValue);
        return arrBuffer;
    }

    public static byte[] LineUpBuffer(String szShowContent, int nStopTime, int nFontColor, int nLineUpWinAddrNo, boolean bLineUpFlash)
    {
        byte byContent[] = new byte[0];
        try
        {
            byContent = szShowContent.getBytes("GB2312");
        }
        catch(UnsupportedEncodingException unsupportedencodingexception) { }
        int nContentLen = byContent.length;
        int nControlCodeLen = 1;
        if(bLineUpFlash)
            nControlCodeLen = 3;
        int nPackLen = nContentLen + 8 + nControlCodeLen;
        byte arrBuffer[] = new byte[nPackLen + 1];
        int nArrIndex = 0;
        arrBuffer[nArrIndex++] = (byte)nLineUpWinAddrNo;
        arrBuffer[nArrIndex++] = 0;
        arrBuffer[nArrIndex++] = (byte)nPackLen;
        arrBuffer[nArrIndex++] = 0;
        arrBuffer[nArrIndex++] = 83;
        arrBuffer[nArrIndex++] = 0;
        arrBuffer[nArrIndex++] = (byte)nStopTime;
        if(bLineUpFlash)
            arrBuffer[nArrIndex++] = 29;
        if(nFontColor == 1)
            arrBuffer[nArrIndex++] = 31;
        else
        if(nFontColor == 2)
            arrBuffer[nArrIndex++] = 30;
        else
            arrBuffer[nArrIndex++] = 10;
        for(int i = 0; i < nContentLen; i++)
            arrBuffer[nArrIndex++] = byContent[i];

        if(bLineUpFlash)
            arrBuffer[nArrIndex++] = 28;
        int nCheck = 0;
        for(int i = 0; i < nPackLen - 1; i++)
            nCheck ^= arrBuffer[i];

        arrBuffer[nArrIndex++] = (byte)(nCheck >> 4);
        arrBuffer[nArrIndex++] = (byte)(nCheck & 15);
        return arrBuffer;
    }
}


/*
	DECOMPILATION REPORT

	Decompiled from: D:\workspace\ad\WebRoot\WEB-INF\lib\qyled.jar
	Total time: 48 ms
	Jad reported messages/errors:
	ExitBase status: 0
	Caught exceptions:
*/
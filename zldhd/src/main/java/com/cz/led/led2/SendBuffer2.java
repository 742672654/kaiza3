package com.cz.led.led2;

import java.io.*;
import java.net.*;
import java.util.List;

// Referenced classes of package com.qy.led:
//            GlobalVar, MaterialBuffer

public class SendBuffer2
{

    public SendBuffer2()
    {
    }

    public static void SetReceiveTimeOut(int nTimeOut)
    {
        if(nTimeOut < 50)
            nTimeOut = 50;
        timeOut = nTimeOut;
    }

    public static void SetChnCodeMode(int nCodeMode)
    {
        chnCodeMode = nCodeMode;
    }

    public static int AddShowPage(String szStartTime, String szStopTime, int nWeekDay)
    {
        int nRet = 1;
        try
        {
            Program2 program2 = new Program2();
            program2.setSzStartTime(szStartTime);
            program2.setSzStopTime(szStopTime);
            program2.setnWeekDay(nWeekDay);
            GlobalVar2.List_Program.add(program2);
            nRet = 0;
        }
        catch(Exception exception) { }
        return nRet;
    }

    public static int AddArea(int nXPos, int nYPos, int nAreaWidth, int nAreaHigth)
    {
        int nRet = 1;
        try
        {
            if(GlobalVar2.List_Program != null && GlobalVar2.List_Program.size() > 0)
            {
                Area2 area2 = new Area2();
                area2.setnXPos(nXPos);
                area2.setnYPos(nYPos);
                area2.setnAreaWidth(nAreaWidth);
                area2.setnAreaHigth(nAreaHigth);
                ((Program2)GlobalVar2.List_Program.get(GlobalVar2.List_Program.size() - 1)).getListArea().add(area2);
            }
            nRet = 0;
        }
        catch(Exception exception) { }
        return nRet;
    }

    public static int AddTemplate_InternalText(String szShowContent, int nUID, int nScreenColor, int nShowStyle, int nShowSpeed, int nStopTime, int nFontColor, int nFontBody, 
            int nFontSize, boolean bPowerOffSave)
    {
        return GenMaterial(szShowContent, "", 1, nUID, nScreenColor, nShowStyle, nShowSpeed, nStopTime, nFontColor, nFontBody, nFontSize, 49, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, bPowerOffSave, false);
    }

    public static int AddTemplate_CollectData(String szShowContent, int nTypeNo, int nScreenColor, int nFontColor, int nFontBody, int nFontSize)
    {
        return GenMaterial(szShowContent, "", 2, nTypeNo, nScreenColor, 0, 0, 0, nFontColor, nFontBody, nFontSize, 49, 0, 0, 0, 0, 0, 0, 0, 0, nTypeNo, 0, true, false);
    }

    public static int AddTemplate_DateTime(int nUID, int nScreenColor, int nNumColor, int nChrColor, int nFontBody, int nFontSize, int nYearLen, int nTimeFormat, 
            int nShowFormat, int nTimeDifSet, int nHourSpan, int nMinSpan, int nStopTime, boolean bPowerOffSave)
    {
        return GenMaterial("", "", 3, nUID, nScreenColor, 0, 0, nStopTime, 0, nFontBody, nFontSize, 49, nShowFormat, nNumColor, nChrColor, nYearLen, nTimeFormat, nTimeDifSet, nHourSpan, nMinSpan, 0, 0, bPowerOffSave, false);
    }

    public static int AddTemplate_LineUp(String szShowContent, int nStopTime, int nFontColor, int nFontBody, int nFontSize, int nLineUpWinAddrNo, boolean bLineUpFlash)
    {
        return GenMaterial(szShowContent, "", 5, 0, 0, 0, 0, nStopTime, nFontColor, nFontBody, nFontSize, 49, 0, 0, 0, 0, 0, 0, 0, 0, 0, nLineUpWinAddrNo, true, bLineUpFlash);
    }

    public static int SendTemplateData_Net(String szCardIP, int nNetProtocol)
    {
        int nRetCode = 1;
        if(!CheckShowPage())
            return nRetCode;
        try
        {
            byte contentBuffer[] = MaterialBuffer2.PackShowpPageBuffer(GlobalVar2.List_Program);
            byte completePacketBuffer[] = MaterialBuffer2.CompletePacketBuffer(contentBuffer, (short)34, 0);
            if(nNetProtocol == 1)
            {
                if(SendBuffer_UDP(completePacketBuffer, szCardIP, sendTimes, sendTimes) && SendBuffer_UDP(getMaterialSendBuffer(), szCardIP, sendTimes, sendTimes))
                    nRetCode = 0;
            } else
            if(SendBuffer_TCP(completePacketBuffer, szCardIP, sendTimes, sendTimes) && SendBuffer_UDP(getMaterialSendBuffer(), szCardIP, sendTimes, sendTimes))
                nRetCode = 0;
        }
        catch(Exception exception) { }
        GlobalVar2.List_Program.clear();
        return nRetCode;
    }

    public static byte[] getMaterialSendBuffer()
    {
        byte completePacketBuffer[] = new byte[0];
        byte contentBuffer[] = new byte[0];
        try
        {
            if(GlobalVar2.List_Program.size() > 0)
            {
                Program2 program2 = (Program2)GlobalVar2.List_Program.get(GlobalVar2.List_Program.size() - 1);
                int nAreaSize = program2.getListArea().size();
                for(int nArea = 0; nArea < nAreaSize; nArea++)
                {
                    Area2 area2 = (Area2)program2.getListArea().get(nArea);
                    int nMaterialSize = area2.getListMaterial().size();
                    for(int nMaterial = 0; nMaterial < nMaterialSize; nMaterial++)
                    {
                        Material2 material2 = (Material2)area2.getListMaterial().get(nMaterial);
                        if(material2.getnContentType() == 54)
                            contentBuffer = MaterialBuffer2.CollectionBuffer_Template(material2);
                        else
                        if(material2.getnContentType() == 55)
                            contentBuffer = MaterialBuffer2.InternalTextBuffer_Template(material2.getSzShowContent(), area2.getnAreaWidth(), area2.getnAreaHigth(), material2.getnUID(), material2.getnScreenColor(), material2.getnShowStyle(), material2.getnShowSpeed(), material2.getnStopTime(), material2.getnFontColor(), material2.getnFontBody(), material2.getnFontSize(), material2.getnUpdateStyle(), material2.getbPowerOffSave(), false);
                        else
                        if(material2.getnContentType() == 49)
                            contentBuffer = MaterialBuffer2.DateTimeBuffer(material2.getnUID(), material2.getnScreenColor(), material2.getnNumColor(), material2.getnChrColor(), material2.getnFontBody(), material2.getnFontSize(), material2.getnYearLen(), material2.getnTimeFormat(), material2.getnShowFormat(), material2.getnTimeDifSet(), material2.getnHourSpan(), material2.getnMinSpan(), material2.getnStopTime());
                        completePacketBuffer = MaterialBuffer2.CompletePacketBuffer(contentBuffer, (short)49, 0);
                    }

                }

            }
        }
        catch(Exception exception) { }
        return completePacketBuffer;
    }

    public static boolean CheckShowPage()
    {
        boolean bRet = true;
        try
        {
            if(GlobalVar2.List_Program != null && GlobalVar2.List_Program.size() > 0)
            {
                for(int nShowPage = 0; nShowPage < GlobalVar2.List_Program.size(); nShowPage++)
                {
                    Program2 program2 = (Program2)GlobalVar2.List_Program.get(nShowPage);
                    if(program2.getListArea().size() > 0)
                    {
                        for(int nArea = 0; nArea < program2.getListArea().size(); nArea++)
                        {
                            Area2 area2 = (Area2)program2.getListArea().get(nArea);
                            if(area2.getListMaterial().size() > 0)
                            {
                                for(int nMaterial = 0; nMaterial < area2.getListMaterial().size(); nMaterial++)
                                {
                                    Material2 material2 = (Material2)area2.getListMaterial().get(nMaterial);
                                    if(material2.getnContentType() == 55 && material2.getSzShowContent().length() <= 0)
                                        bRet = false;
                                }

                            } else
                            {
                                bRet = false;
                            }
                        }

                    } else
                    {
                        bRet = false;
                    }
                }

            } else
            {
                bRet = false;
            }
        }
        catch(Exception e)
        {
            bRet = false;
        }
        return bRet;
    }

    public static int GenMaterial(String szShowContent, String szFilePaths, int nMateType, int nUID, int nScreenColor, int nShowStyle, int nShowSpeed, int nStopTime, 
            int nFontColor, int nFontBody, int nFontSize, int nUpdateStyle, int nShowFormat, int nNumColor, int nChrColor, 
            int nYearLen, int nTimeFormat, int nTimeDifSet, int nHourSpan, int nMinSpan, int nTypeNo, int nLineUpWinAddrNo, 
            boolean bPowerOffSave, boolean bLineUpFlash)
    {
        int nRet;
        int nShowPageSize;
        nRet = 1;
        nShowPageSize = 0;
        int nAreaSize = 0;
        nShowPageSize = GlobalVar2.List_Program.size();
        if(nShowPageSize < 1)
            return nRet;
        try
        {
            Program2 program2 = (Program2)GlobalVar2.List_Program.get(nShowPageSize - 1);
             nAreaSize = program2.getListArea().size();
            if(nAreaSize > 0)
            {
                Area2 area2 = (Area2)program2.getListArea().get(nAreaSize - 1);
                Material2 material2 = new Material2();
                material2.setnUID(nUID);
                material2.setnScreenColor(nScreenColor);
                material2.setbPowerOffSave(bPowerOffSave);
                material2.setnShowStyle(nShowStyle);
                material2.setnShowSpeed(nShowSpeed);
                material2.setnStopTime(nStopTime);
                material2.setnFontColor(nFontColor);
                material2.setnFontBody(nFontBody);
                material2.setnFontSize(nFontSize);
                material2.setSzShowContent(szShowContent);
                material2.setnUpdateStyle(49);
                if(nUpdateStyle == 2)
                    material2.setnUpdateStyle(50);
                else
                if(nUpdateStyle == 3)
                    material2.setnUpdateStyle(51);
                if(nMateType == 1)
                    material2.setnContentType(55);
                else
                if(nMateType == 2)
                {
                    material2.setnContentType(54);
                    material2.setnTypeNo(nTypeNo);
                    material2.setnFontColor((nFontColor << 4) + nFontColor);
                } else
                if(nMateType == 3)
                {
                    material2.setnContentType(49);
                    material2.setnShowFormat(nShowFormat);
                    material2.setnNumColor(nNumColor);
                    material2.setnChrColor(nChrColor);
                    material2.setnYearLen(nYearLen);
                    material2.setnTimeFormat(nTimeFormat);
                    material2.setnTimeDifSet(nTimeDifSet);
                    material2.setnHourSpan(nHourSpan);
                    material2.setnMinSpan(nMinSpan);
                    material2.setnShowStyle(nShowStyle);
                    material2.setnShowSpeed(nStopTime);
                } else
                if(nMateType == 5)
                {
                    material2.setnContentType(53);
                    material2.setnLineUpWinAddrNo(nLineUpWinAddrNo);
                    material2.setnLineUpColor(nFontColor);
                    material2.setbLineUpFlash(bLineUpFlash);
                }
                area2.getListMaterial().add(material2);
                nRet = 0;
            }
        }
        catch(Exception exception) { }
        return nRet;
    }

    public static int SendInternalText_Net(String szShowContent, String szCardIP, int nNetProtocol, int nAreaWidth, int nAreaHigth, int nUID, int nScreenColor, int nShowStyle, 
            int nShowSpeed, int nStopTime, int nFontColor, int nFontBody, int nFontSize, int nUpdateStyle, boolean bPowerOffSave)
    {
        int nRetCode = 1;
        byte byContent[] = MaterialBuffer2.InternalTextBuffer(szShowContent, nAreaWidth, nAreaHigth, nUID, nScreenColor, nShowStyle, nShowSpeed, nStopTime, nFontColor, nFontBody, nFontSize, nUpdateStyle, bPowerOffSave, true);
        byte byPacketBuffer[] = MaterialBuffer2.CompletePacketBuffer(byContent, (short)49, 0);
        if(nNetProtocol == 1)
        {
            if(SendBuffer_UDP(byPacketBuffer, szCardIP, sendTimes, sendTimes))
                nRetCode = 0;
        } else
        if(SendBuffer_TCP(byPacketBuffer, szCardIP, sendTimes, sendTimes))
            nRetCode = 0;
        return nRetCode;
    }

    public static int SendMulInternalText_Net(String szShowContent, String szCardIP, int nNetProtocol, int nAreaWidth, int nAreaHigth, int nUID, int nScreenColor, int nShowStyle, 
            int nShowSpeed, int nStopTime, int nFontColor, int nFontBody, int nFontSize, int nUpdateStyle, boolean bPowerOffSave, 
            int nTextIndex, int nTextCount)
    {
        int nRetCode = 1;
        boolean bEnd = false;
        int nIndex = 0;
        if(nTextIndex <= 0 || nTextCount <= 0 || nTextIndex > nTextCount)
            return nRetCode;
        if(nTextIndex == nTextCount)
            bEnd = true;
        if(nTextIndex == 1)
            g_nArrIndex = 0;
        byte byContent[] = MaterialBuffer2.InternalTextBuffer(szShowContent, nAreaWidth, nAreaHigth, nUID, nScreenColor, nShowStyle, nShowSpeed, nStopTime, nFontColor, nFontBody, nFontSize, nUpdateStyle, bPowerOffSave, bEnd);
        if(g_nArrIndex + byContent.length > 2048)
            return nRetCode;
        for(nIndex = 0; nIndex < byContent.length; nIndex++)
            byteArrAllBuffer[g_nArrIndex + nIndex] = byContent[nIndex];

        g_nArrIndex += byContent.length;
        if(nTextIndex == nTextCount)
        {
            int nAllLen = g_nArrIndex;
            byte byteArrSendBuffer[] = new byte[nAllLen];
            for(nIndex = 0; nIndex < nAllLen; nIndex++)
                byteArrSendBuffer[nIndex] = byteArrAllBuffer[nIndex];

            byte byPacketBuffer[] = MaterialBuffer2.CompletePacketBuffer(byteArrSendBuffer, (short)49, 0);
            if(nNetProtocol == 1)
            {
                if(SendBuffer_UDP(byPacketBuffer, szCardIP, sendTimes, sendTimes))
                    nRetCode = 0;
            } else
            if(SendBuffer_TCP(byPacketBuffer, szCardIP, sendTimes, sendTimes))
                nRetCode = 0;
        } else
        {
            nRetCode = 0;
        }
        return nRetCode;
    }

    public static int SendMulInternalText_Net(List listMaterial, String szCardIP, int nNetProtocol)
    {
        int nRetCode = 1;
        boolean bEnd = false;
        int nArrIndex = 0;
        int nIndex = 0;
        for(nIndex = 0; nIndex < listMaterial.size(); nIndex++)
        {
            Material2 material2 = (Material2)listMaterial.get(nIndex);
            if(nIndex == 0)
                nArrIndex = 0;
            bEnd = false;
            if(nIndex == listMaterial.size() - 1)
                bEnd = true;
            byte byContent[] = MaterialBuffer2.InternalTextBuffer(material2.getSzShowContent(), material2.getnAreaWidth(), material2.getnAreaHigth(), material2.getnUID(), material2.getnScreenColor(), material2.getnShowStyle(), material2.getnShowSpeed(), material2.getnStopTime(), material2.getnFontColor(), material2.getnFontBody(), material2.getnFontSize(), material2.getnUpdateStyle(), material2.getbPowerOffSave(), bEnd);
            if(nArrIndex + byContent.length > 2048)
                return nRetCode;
            for(int i = 0; i < byContent.length; i++)
                byteArrAllBuffer[nArrIndex + i] = byContent[i];

            nArrIndex += byContent.length;
            if(nIndex == listMaterial.size() - 1)
            {
                int nAllLen = nArrIndex;
                byte byteArrSendBuffer[] = new byte[nAllLen];
                for(nIndex = 0; nIndex < nAllLen; nIndex++)
                    byteArrSendBuffer[nIndex] = byteArrAllBuffer[nIndex];

                byte byPacketBuffer[] = MaterialBuffer2.CompletePacketBuffer(byteArrSendBuffer, (short)49, 0);
                if(nNetProtocol == 1)
                {
                    if(SendBuffer_UDP(byPacketBuffer, szCardIP, sendTimes, sendTimes))
                        nRetCode = 0;
                } else
                if(SendBuffer_TCP(byPacketBuffer, szCardIP, sendTimes, sendTimes))
                    nRetCode = 0;
            } else
            {
                nRetCode = 0;
            }
        }

        return nRetCode;
    }

    public static int SendCollectionData_Net(String szShowContent, String szCardIP, int nNetProtocol, int nTypeNo, int nFontColor, int nFontBody, int nFontSize)
    {
        int nRetCode = 1;
        byte byContent[] = MaterialBuffer2.CollectionBuffer(szShowContent, nTypeNo, false, false, (short)nFontColor, (short)nFontColor, (short)nFontColor, (short)nFontSize);
        byte byPacketBuffer[] = MaterialBuffer2.CompletePacketBuffer(byContent, (short)101, 0);
        if(nNetProtocol == 1)
        {
            if(SendBuffer_UDP(byPacketBuffer, szCardIP, sendTimes, sendTimes))
                nRetCode = 0;
        } else
        if(SendBuffer_TCP(byPacketBuffer, szCardIP, sendTimes, sendTimes))
            nRetCode = 0;
        return nRetCode;
    }

    public static int SendMulCollectionData_Net(List listMaterial, String szCardIP, int nNetProtocol)
    {
        int nRetCode = 1;
        int nArrIndex = 0;
        int nIndex = 0;
        for(nIndex = 0; nIndex < listMaterial.size(); nIndex++)
        {
            Material2 material2 = (Material2)listMaterial.get(nIndex);
            if(nIndex == 0)
                nArrIndex = 0;
            byte byContent[] = MaterialBuffer2.CollectionBuffer(material2.getSzShowContent(), material2.getnTypeNo(), false, false, (short)material2.getnFontColor(), (short)material2.getnFontColor(), (short)material2.getnFontColor(), (short)material2.getnFontSize());
            if(nArrIndex + byContent.length > 2048)
                return nRetCode;
            for(int i = 0; i < byContent.length; i++)
                byteArrAllBuffer[nArrIndex + i] = byContent[i];

            nArrIndex += byContent.length;
            if(nIndex == listMaterial.size() - 1)
            {
                int nAllLen = nArrIndex;
                byte byteArrSendBuffer[] = new byte[nAllLen];
                for(nIndex = 0; nIndex < nAllLen; nIndex++)
                    byteArrSendBuffer[nIndex] = byteArrAllBuffer[nIndex];

                byte byPacketBuffer[] = MaterialBuffer2.CompletePacketBuffer(byteArrSendBuffer, (short)101, 0);
                if(nNetProtocol == 1)
                {
                    if(SendBuffer_UDP(byPacketBuffer, szCardIP, sendTimes, sendTimes))
                        nRetCode = 0;
                } else
                if(SendBuffer_TCP(byPacketBuffer, szCardIP, sendTimes, sendTimes))
                    nRetCode = 0;
            } else
            {
                nRetCode = 0;
            }
        }

        return nRetCode;
    }

    public static int SendDateTime_Net(String szCardIP, int nNetProtocol, int nUID, int nScreenColor, int nNumColor, int nChrColor, int nFontBody, int nFontSize, 
            int nYearLen, int nTimeFormat, int nShowFormat, int nTimeDifSet, int nHourSpan, int nMinSpan, int nStopTime)
    {
        int nRetCode = 1;
        byte dateTimeBuffer[] = MaterialBuffer2.DateTimeBuffer(nUID, nScreenColor, nNumColor, nChrColor, nFontBody, nFontSize, nYearLen, nTimeFormat, nShowFormat, nTimeDifSet, nHourSpan, nMinSpan, nStopTime);
        byte completePacketBuffer[] = MaterialBuffer2.CompletePacketBuffer(dateTimeBuffer, (short)49, 0);
        if(nNetProtocol == 1)
        {
            if(SendBuffer_UDP(completePacketBuffer, szCardIP, sendTimes, sendTimes))
                nRetCode = 0;
        } else
        if(SendBuffer_TCP(completePacketBuffer, szCardIP, sendTimes, sendTimes))
            nRetCode = 0;
        return nRetCode;
    }

    public static int SendLineUp_Net(String szShowContent, String szCardIP, int nNetProtocol, int nStopTime, int nFontColor, int nLineUpWinAddrNo, boolean bLineUpFlash)
    {
        int nRetCode = 1;
        byte lineUpBuffer[] = MaterialBuffer2.LineUpBuffer(szShowContent, nStopTime, nFontColor, nLineUpWinAddrNo, bLineUpFlash);
        byte completePacketBuffer[] = MaterialBuffer2.CompletePacketBuffer(lineUpBuffer, (short)100, 0);
        if(nNetProtocol == 1)
        {
            if(SendBuffer_UDP(completePacketBuffer, szCardIP, sendTimes, sendTimes))
                nRetCode = 0;
        } else
        if(SendBuffer_TCP(completePacketBuffer, szCardIP, sendTimes, sendTimes))
            nRetCode = 0;
        return nRetCode;
    }

    public static int PlayShowPage_Net(String szCardIP, int nNetProtocol, int nShowPageNo)
    {
        int nRetCode = 1;
        byte byContent[] = MaterialBuffer2.PlayShowPageBuffer(nShowPageNo);
        byte byPacketBuffer[] = MaterialBuffer2.CompletePacketBuffer(byContent, (short)102, 0);
        if(nNetProtocol == 1)
        {
            if(SendBuffer_UDP(byPacketBuffer, szCardIP, sendTimes, sendTimes))
                nRetCode = 0;
        } else
        if(SendBuffer_TCP(byPacketBuffer, szCardIP, sendTimes, sendTimes))
            nRetCode = 0;
        return nRetCode;
    }

    public static int PlayVoice_Net(String szPlayContent, String szCardIP, String szRS485Address, int nNetProtocol, int nRSPort)
    {
        int nRetCode = 1;
        byte byContent[] = MaterialBuffer2.VoiceBuffer(szPlayContent);
        byte byPacketBuffer[] = MaterialBuffer2.CompletePacketBuffer(byContent, (short)104, nRSPort);
        if(nNetProtocol == 1)
        {
            if(SendBuffer_UDP(byPacketBuffer, szCardIP, sendTimes, sendTimes))
                nRetCode = 0;
        } else
        if(SendBuffer_TCP(byPacketBuffer, szCardIP, sendTimes, sendTimes))
            nRetCode = 0;
        return nRetCode;
    }

    public static int RelaySwitch_Net(String szCardIP, int nNetProtocol, int nCircuitNo, int nSwitchStatus)
    {
        int nRetCode = 1;
        byte relayBuffer[] = MaterialBuffer2.RelayBuffer(nCircuitNo, nSwitchStatus, 0);
        byte completePacketBuffer[] = MaterialBuffer2.CompletePacketBuffer(relayBuffer, (short)97, 0);
        if(nNetProtocol == 1)
        {
            if(SendBuffer_UDP(completePacketBuffer, szCardIP, sendTimes, sendTimes))
                nRetCode = 0;
        } else
        if(SendBuffer_TCP(completePacketBuffer, szCardIP, sendTimes, sendTimes))
            nRetCode = 0;
        return nRetCode;
    }

    public static int RelayDelay_Net(String szCardIP, int nNetProtocol, int nCircuitNo, int nDelayTime)
    {
        int nRetCode = 1;
        byte relayBuffer[] = MaterialBuffer2.RelayBuffer(nCircuitNo, 0, nDelayTime);
        byte completePacketBuffer[] = MaterialBuffer2.CompletePacketBuffer(relayBuffer, (short)97, 0);
        if(nNetProtocol == 1)
        {
            if(SendBuffer_UDP(completePacketBuffer, szCardIP, sendTimes, sendTimes))
                nRetCode = 0;
        } else
        if(SendBuffer_TCP(completePacketBuffer, szCardIP, sendTimes, sendTimes))
            nRetCode = 0;
        return nRetCode;
    }

    public static int SetBright_Net(String szCardIP, int nNetProtocol, int nPriority, int nBrightValue)
    {
        int nRetCode = 1;
        byte setBrightBuffer[] = MaterialBuffer2.SetBrightBuffer(nPriority, nBrightValue);
        byte completePacketBuffer[] = MaterialBuffer2.CompletePacketBuffer(setBrightBuffer, (short)118, 0);
        if(nNetProtocol == 1)
        {
            if(SendBuffer_UDP(completePacketBuffer, szCardIP, sendTimes, sendTimes))
                nRetCode = 0;
        } else
        if(SendBuffer_TCP(completePacketBuffer, szCardIP, sendTimes, sendTimes))
            nRetCode = 0;
        return nRetCode;
    }

    public static int StartPlay_Net(String szCardIP, int nNetProtocol)
    {
        int nRetCode = 1;
        byte completePacketBuffer[] = MaterialBuffer2.CompletePacketBuffer(new byte[0], (short)81, 0);
        if(nNetProtocol == 1)
        {
            if(SendBuffer_UDP(completePacketBuffer, szCardIP, sendTimes, sendTimes))
                nRetCode = 0;
        } else
        if(SendBuffer_TCP(completePacketBuffer, szCardIP, sendTimes, sendTimes))
            nRetCode = 0;
        return nRetCode;
    }

    public static int StopPlay_Net(String szCardIP, int nNetProtocol)
    {
        int nRetCode = 1;
        byte completePacketBuffer[] = MaterialBuffer2.CompletePacketBuffer(new byte[0], (short)82, 0);
        if(nNetProtocol == 1)
        {
            if(SendBuffer_UDP(completePacketBuffer, szCardIP, sendTimes, sendTimes))
                nRetCode = 0;
        } else
        if(SendBuffer_TCP(completePacketBuffer, szCardIP, sendTimes, sendTimes))
            nRetCode = 0;
        return nRetCode;
    }

    public static boolean SendBuffer_UDP(byte byArrSendBuffer[], String szCardIP, int nSendCount, int nSendTimeOut)
    {
        boolean bRetCode = false;
        try
        {
            DatagramSocket client = new DatagramSocket();
            DatagramPacket dpSendPacket = new DatagramPacket(byArrSendBuffer, byArrSendBuffer.length, InetAddress.getByName(szCardIP), 8800);
            client.setSoTimeout(timeOut);
            client.send(dpSendPacket);
            byte byArrRecvBuf[] = new byte[512];
            DatagramPacket dpRecv = new DatagramPacket(byArrRecvBuf, byArrRecvBuf.length);
            client.receive(dpRecv);
            int nIndex = 0;
            if(byArrRecvBuf[nIndex++] == -2 && byArrRecvBuf[nIndex++] == 92 && byArrRecvBuf[nIndex++] == 75 && byArrRecvBuf[nIndex++] == -119)
                if(byArrRecvBuf[nIndex + 4] == -127 || byArrRecvBuf[nIndex + 4] == 49 && byArrRecvBuf[nIndex + 13] == 49)
                    bRetCode = true;
                else
                if(byArrRecvBuf[nIndex + 4] == 104 && byArrRecvBuf[nIndex + 13] == 79)
                    bRetCode = true;
            client.close();
        }
        catch(Exception exception) { }
        return bRetCode;
    }

    public static boolean SendBuffer_TCP(byte byArrSendBuffer[], String szCardIP, int nSendCount, int nSendTimeOut)
    {
        boolean bRetCode;
        Socket socket;
        bRetCode = false;
        socket = null;
        try
        {
            socket = new Socket(szCardIP, 8901);
            InputStream is = socket.getInputStream();
            byte byArrRecvBuf[] = new byte[1024];
            is.read(byArrRecvBuf);
            OutputStream os = socket.getOutputStream();
            os.write(byArrSendBuffer);
            is = socket.getInputStream();
            is.read(byArrRecvBuf);
            int nIndex = 0;
            if(byArrRecvBuf[nIndex++] == -2 && byArrRecvBuf[nIndex++] == 92 && byArrRecvBuf[nIndex++] == 75 && byArrRecvBuf[nIndex++] == -119)
                if(byArrRecvBuf[nIndex + 4] == -127 && byArrRecvBuf[nIndex + 13] == 49)
                    bRetCode = true;
                else
                if(byArrRecvBuf[nIndex + 4] == 104 && byArrRecvBuf[nIndex + 13] == 79)
                    bRetCode = true;
        }
        catch(IOException ioexception)
        {
            try
            {
                socket.close();
            }
            catch(IOException ioexception1) { }
          
        }
        

        try
        {
            socket.close();
        }
        catch(IOException ioexception2) { }
     
        try
        {
            socket.close();
        }
        catch(IOException ioexception3) { }
        return bRetCode;
    }

    public static int sendTimes = 1;
    public static int timeOut = 300;
    public static int chnCodeMode = 0;
    static int g_nArrIndex = 0;
    static final int MAX_BUFFER_SIZE = 2048;
    static byte byteArrAllBuffer[] = new byte[2048];

}


/*
	DECOMPILATION REPORT

	Decompiled from: D:\workspace\ad\WebRoot\WEB-INF\lib\qyled.jar
	Total time: 52 ms
	Jad reported messages/errors:
Couldn't resolve all exception handlers in method SendTemplateData_Net
Couldn't resolve all exception handlers in method GenMaterial
Couldn't fully decompile method SendBuffer_TCP
Couldn't resolve all exception handlers in method SendBuffer_TCP
	ExitBase status: 0
	Caught exceptions:
*/
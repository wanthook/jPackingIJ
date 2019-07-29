/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wanthook.ij.jpacking.function;

import java.security.MessageDigest;
//import java.security.NoSuchAlgorithmException;
/**
 *
 * @author wanthook
 */
public class Md5
{
    MessageDigest MD;

    public Md5()
    {
        
    }

    public String StringEncrypt(String str) throws Exception
    {
        MD = MessageDigest.getInstance("MD5");

        MD.update(str.getBytes());

        byte[] data = MD.digest();

        StringBuffer SB = new StringBuffer();

        for(byte b : data)
        {
            SB.append(String.format("%02x", b & 0xff));
        }

        return SB.toString();
    }
}



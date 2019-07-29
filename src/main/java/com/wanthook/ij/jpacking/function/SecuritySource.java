/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wanthook.ij.jpacking.function;

import org.jasypt.util.text.BasicTextEncryptor;

/**
 *
 * @author wanthook
 */
public class SecuritySource 
{
    private BasicTextEncryptor textEncryptor;
    
    
    SecuritySource()
    {
        textEncryptor = new BasicTextEncryptor();
    }
    
    public void setPassword(String pass)
    {
        textEncryptor.setPassword(pass);
    }
    
    public String Encript(String text)
    {
        if(text.isEmpty())
        {
            return "";
        }
        
        return textEncryptor.encrypt(text);
    }
    
    public String Decript(String text)
    {
//        System.out.println(text);
        if(text.isEmpty())
        {
            return "";
        }
        
        return textEncryptor.decrypt(text);
    }
}

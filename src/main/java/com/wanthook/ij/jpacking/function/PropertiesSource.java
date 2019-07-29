/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wanthook.ij.jpacking.function;

import java.io.BufferedReader;
import java.io.File;
//import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Properties;

/**
 *
 * @author wanthook
 */
public class PropertiesSource 
{
    public void SaveProperties(
                                String ip, 
                                String username, 
                                String password, 
                                String port,
                                String baudrate,
                                String databits,
                                String stopbits,
                                String parity,
                                String printer,
                                String beratPapan,
                                String secret,
                                String clientId
                            )
    {
        Properties prop = new Properties();
//	OutputStream output = null;
        
        try 
        {
            
//            output = new FileOutputStream("config.properties");
            
            // get the property value and print it out
            prop.setProperty("ip", ip);
            prop.setProperty("username", username);
            prop.setProperty("password", password);  
            prop.setProperty("port", port);
            prop.setProperty("baudrate", baudrate);
            prop.setProperty("databits", databits);
            prop.setProperty("stopbits", stopbits);
            prop.setProperty("parity", parity);
            prop.setProperty("printer", printer);
            prop.setProperty("beratPapan", beratPapan);
             prop.setProperty("secret", secret);
             prop.setProperty("clientId", clientId);
//            System.out.println(getPropertyAsString(prop));
            
            
            
            
            WriteFile(getPropertyAsString(prop), "setting.properties");
//            prop.store(output, null);
 
	} 
        catch (IOException ex) 
        {
            ex.printStackTrace();
	} 
    }
    
    public Properties OpenProperties()
    {
        Properties prop = null;
//	OutputStream output = null;
        
        
        String[] ret = new String[5];
        
        try 
        {
            String enc = this.OpenFile("setting.properties");
//            System.out.println(enc);
            if(!enc.isEmpty())
            {
                
                
                prop = this.parsePropertiesString(enc);
                
                
            }
 
	} 
        catch(IOException e)
        {
            e.printStackTrace();
        }
        
        return prop;
    }
    
    public static String getPropertyAsString(Properties prop) 
    {    
        StringWriter writer = new StringWriter();
        prop.list(new PrintWriter(writer));
        return writer.getBuffer().toString();
    }
    
    public Properties parsePropertiesString(String s) throws IOException 
    {
        // grr at load() returning void rather than the Properties object
        // so this takes 3 lines instead of "return new Properties().load(...);"
        final Properties p = new Properties();
        p.load(new StringReader(s));
        return p;
    }
    
    public void WriteFile(String write, String file) throws IOException 
    {
        try
        {
            FileWriter fw = new FileWriter(file);

            SecuritySource ss = new SecuritySource();

            ss.setPassword("jHJ898idJKHUd8Li003jjfJKJ834HHYUIiiuq");
            String enc = ss.Encript(write);

            fw.write(enc);

            fw.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        return;
    }
    
    public String OpenFile(String file) 
    {
        try
        {
            BufferedReader br = null;
            SecuritySource ss = new SecuritySource();

            String line = null;

            ss.setPassword("jHJ898idJKHUd8Li003jjfJKJ834HHYUIiiuq");

            File fl = new File(file);
            String dec = "";
            
            if(fl.exists())
            {
                br = new BufferedReader(new FileReader(file));
                line = br.readLine();

                br.close();

                dec = ss.Decript(line);
            }

            

            return dec;
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        
        return "";
    }
}

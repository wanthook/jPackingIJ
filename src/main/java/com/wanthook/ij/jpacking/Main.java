/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wanthook.ij.jpacking;

//import com.jtattoo.plaf.fast.FastLookAndFeel;
import javax.swing.UIManager;

//import java.util.regex.Pattern;
//import jssc.SerialPortList;



/**
 *
 * @author wanthook
 */
public class Main 
{
    public static void main(String[] args)
    {
        try
        {
//            UIManager.setLookAndFeel("com.jtattoo.plaf.fast.FastLookAndFeel");
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            new launch().setVisible(true);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}

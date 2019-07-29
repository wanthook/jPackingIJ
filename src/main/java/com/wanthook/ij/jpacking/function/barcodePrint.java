/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wanthook.ij.jpacking.function;

//import static com.wanthook.sii.jpacking.function.varApp.printer;
//import java.math.BigDecimal;
//import java.text.DecimalFormat;
import com.wanthook.ij.jpacking.function.PropertiesSource;

import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.PrintServiceAttribute;
import javax.print.attribute.standard.PrinterName;



/**
 *
 * @author wanthook
 */
public class barcodePrint {
    
    private static String printerName;
    PropertiesSource ps = new PropertiesSource();
    
    public barcodePrint(String printerName)
    {
        this.printerName = printerName;
    }
        
    public void printBarcode(String lbl,String printer)
    {
        if(!lbl.isEmpty())
        {
            try
            {

                PrintService psIntermec = null;
                String sPrinterName = null;
                PrintService[] services = PrintServiceLookup.lookupPrintServices(null, null);

                for (int i = 0; i < services.length; i++) 
                {
                    PrintServiceAttribute attr = services[i].getAttribute(PrinterName.class);

                    sPrinterName = ((PrinterName)attr).getValue();
                    System.out.println(sPrinterName.toLowerCase());
                    if (sPrinterName.toLowerCase().contains(printer)) 
                    {
                        System.out.println("print");
                         psIntermec = services[i];

                         break;

                    }

                }

                if (psIntermec == null) 
                {

                    System.out.println("printer is not found.");

                    return;

                }

                DocPrintJob job = psIntermec.createPrintJob();

                byte[] by = lbl.getBytes();

                DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;



                Doc doc = new SimpleDoc(by, flavor, null);

                job.print(doc, null);

            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * Create string QR Code for karung
     * @param options boolean[] 0=logo, 1=berat, 2=made_in_indonesia
     * @param labelField string[] 0=material, 1=batch, 2=register, 3=berat, 4=no_reg, 5=bruto, 6=tipe_berat, 7 = label_mesin
     * @return String
     */
    public String karungBarcode(boolean[] options, String[] labelField)
    {
        String barcode = "";
        
        String[] barcodeMentah = ps.OpenFile("IplKarung.setting").split(System.getProperty("line.separator"));
        
        for(int i=0 ; i<barcodeMentah.length ; i++)
        {
            if(barcodeMentah[i].contains("<STX>u"))
            {
                if(options[0])
                {
                    barcode += barcodeMentah[i];
                }
            }
            else
            {
                barcode += barcodeMentah[i];
            }
        }
        
        if(!options[0])
        {
            barcode = barcode.replaceAll("PT INDAH JAYA", "");
            barcode = barcode.replaceAll("TEXTILE INDUSTRY", "");
        }
        
        if(options[1])
        {
            barcode = barcode.replaceAll("LABEL_BERAT", labelField[3]+" KG");
        }
        else
        {
            barcode = barcode.replaceAll("LABEL_BERAT", "");
        }
        
        if(labelField[6].trim().equalsIgnoreCase("SAMPLE"))
        {
            barcode = barcode.replaceAll("Made In Indonesia", "Barcode Sample");
        }
        else
        {
            if(!options[2])
            {
                barcode = barcode.replaceAll("Made In Indonesia", "");
            }
        }
        
        //isi barcode
        barcode = barcode.replaceAll("ISI_BARCODE", labelField[0]+":"+labelField[1]+":"+labelField[2]+":"+labelField[3]+":"+labelField[4]+":"+labelField[5]);
        
        //label material
        barcode = barcode.replaceAll("LABEL_MATERIAL", labelField[0]);
        
        //label batch
        barcode = barcode.replaceAll("LABEL_BATCH", labelField[1]);
        
        //label urut
        barcode = barcode.replaceAll("LABEL_URUT", labelField[2]);
        
        //label mesin
        barcode = barcode.replaceAll("LABEL_MESIN", labelField[7]);
        
        barcode = barcode.replaceAll(System.getProperty("line.separator"), "");
//        System.out.println(barcode.trim());
        
        return barcode.trim();
//return "";
    }
    
    public String paletBarcode(Object[][] data, String paletId)
    {
        String label = "", isian = "";
        String[] list = new String[5];
        String start_urut = "", end_urut = "";
        boolean first = true;
        
        String barcodeMentah = ps.OpenFile("IplPalet.setting");
        
//        DecimalFormat form = new DecimalFormat("0.00");
        float dt = 0;
        
//        System.out.println("sampe");
        for(int i = 0 ; i<data.length ; i++)
        {
            
            if(first)
            {
                //material
                list[0] = data[i][0].toString();
                //batch
                list[1] = data[i][3].toString();
                //reg
                list[2] = data[i][13].toString();
                //bruto
                list[3] = data[i][12].toString();
                //label_mesin
                list[4] = data[i][14].toString();
                
                first = false;
                
                end_urut = data[i][1].toString();
                
                isian = paletId + ":" + list[0] + ":" + list[1] + ":" + data[i][1].toString() + ":" + data[i][8].toString();
            }
            else
            {
                start_urut = data[i][1].toString();
                isian += ":" + data[i][1].toString();
            }
            
            dt += Float.parseFloat(data[i][8].toString());
            
//            list[2] = String.valueOf(dt);
            
            
        }
        
        barcodeMentah = barcodeMentah.replaceAll("ISI_BARCODE", isian + ":" + list[2] + ":" + list[3]);
        barcodeMentah = barcodeMentah.replaceAll("LABEL_MATERIAL", list[0]);
        barcodeMentah = barcodeMentah.replaceAll("LABEL_BATCH", list[1]);
        barcodeMentah = barcodeMentah.replaceAll("LABEL_URUT_AWAL", start_urut);
        barcodeMentah = barcodeMentah.replaceAll("LABEL_URUT_AKHIR", end_urut);
        barcodeMentah = barcodeMentah.replaceAll("LABEL_BERAT_TOTAL", String.format("%.2f", dt)+" KG");
        barcodeMentah = barcodeMentah.replaceAll("LABEL_MESIN", list[4]);
        
        label = barcodeMentah;
//        System.out.println(label);
        return label;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wanthook.ij.jpacking.function;

import java.io.BufferedReader;
import java.io.DataOutputStream;
//import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
/**
 *
 * @author wanthook
 */
public class httpRequest 
{
    private final String USER_AGENT = "Mozilla/5.0";
    private final String ACCEPT = "application/json";
    
    public String httpGet(String url, String token)
    {
        try
        {
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            con.setRequestMethod("GET");

            con.setRequestProperty("User-Agent", this.USER_AGENT);
            con.setRequestProperty("Accept", ACCEPT);
            if(!token.isEmpty())
            {
                con.setRequestProperty("Authorization", "Bearer "+token);
            }

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
            }
            in.close();

            return response.toString();
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
            return "";
        }
    }
    
    public String httpPost(String url, String param, String token)
    {
        try
        {
//            String url = "https://selfsolve.apple.com/wcResults.do";
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
//            System.out.println(url);
            //add reuqest header
            con.setRequestMethod("POST");
//            con.setRequestProperty("User-Agent", USER_AGENT);
            con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
            con.setRequestProperty("User-Agent", this.USER_AGENT);
            con.setRequestProperty("Accept", this.ACCEPT);
            
            if(!token.isEmpty())
            {
                con.setRequestProperty("Authorization", "Bearer "+token);
            }

            // Send post request
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            if(!param.isEmpty())
                wr.writeBytes(param);
            wr.flush();
            wr.close();
//            System.out.println(token);
            int responseCode = con.getResponseCode();
            System.out.println("\nSending 'POST' request to URL : " + url);
            System.out.println("Post parameters : " + param);
            System.out.println("Response Code : " + responseCode);
            
            if(responseCode != 200)
            {
                return "error";
            }
            
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
            }
            in.close();

            //print result
            return response.toString();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            return "";
        }

    }
    
    public boolean isJSONValid(String test) 
    {
        try 
        {
            new JSONObject(test);
        } 
        catch (JSONException ex) 
        {
            return false;
        }
        return true;
    }
}

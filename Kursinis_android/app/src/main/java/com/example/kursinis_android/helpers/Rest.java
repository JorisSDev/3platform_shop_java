package com.example.kursinis_android.helpers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class Rest {
    private static BufferedWriter bufferedWriter;
    public static String sendPost(String postUrl, String jsonInfo) throws IOException{
        URL url = new URL(postUrl);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

        httpURLConnection.setRequestMethod("POST");

        httpURLConnection.setConnectTimeout(20000);
        httpURLConnection.setReadTimeout(20000);

        httpURLConnection.setRequestProperty( "Content-Type", "application/json; charset=UTF-8") ;
        httpURLConnection.setRequestProperty( "Accept", "application/json" ) ;
        //httpURLConnection.setRequestProperty("Content-length", String.valueOf(jsonInfo.length()));

        httpURLConnection.setDoInput(true);
        httpURLConnection.setDoOutput(true);

        System.out.println(postUrl);

        OutputStream outputStream = httpURLConnection.getOutputStream();

        bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
        bufferedWriter.write(jsonInfo);

        bufferedWriter.close();
        outputStream.close();

        int code = httpURLConnection.getResponseCode();
        System.out.println("Response code: "+code);

        if(code == HttpURLConnection.HTTP_OK){
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            String line;
            StringBuffer response = new StringBuffer();

            while((line = bufferedReader.readLine()) != null){
                response.append(line);
            }

            bufferedReader.close();
            return response.toString();
        }
        else{
            return "Error";
        }

    }

    public static String sendGet(String getUrl) throws IOException{
        URL url = new URL(getUrl);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

        httpURLConnection.setRequestMethod("GET");

        httpURLConnection.setRequestProperty( "Content-Type", "application/json; charset=UTF-8") ;
        httpURLConnection.setRequestProperty( "Accept", "application/json" ) ;

        int code = httpURLConnection.getResponseCode();
        System.out.println("Response code: "+code);

        if(code == HttpURLConnection.HTTP_OK){
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            String line;
            StringBuffer response = new StringBuffer();

            while((line = bufferedReader.readLine()) != null){
                response.append(line);
            }

            bufferedReader.close();
            return response.toString();
        }
        else{
            return "Error";
        }

    }

    public static String sendDelete(String deleteUrl) throws IOException{
        URL url = new URL(deleteUrl);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

        httpURLConnection.setRequestMethod("DELETE");

        httpURLConnection.setRequestProperty( "Content-Type", "application/json; charset=UTF-8") ;
        httpURLConnection.setRequestProperty( "Accept", "application/json" ) ;

        int code = httpURLConnection.getResponseCode();
        System.out.println("Response code: "+code);

        if(code == HttpURLConnection.HTTP_OK){
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            String line;
            StringBuffer response = new StringBuffer();

            while((line = bufferedReader.readLine()) != null){
                response.append(line);
            }

            bufferedReader.close();
            return response.toString();
        }
        else{
            return "Error";
        }

    }

    public static String sendPut(String putUrl, String jsonInfo) throws IOException{
        URL url = new URL(putUrl);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

        httpURLConnection.setRequestMethod("PUT");

        httpURLConnection.setConnectTimeout(20000);
        httpURLConnection.setReadTimeout(20000);

        httpURLConnection.setRequestProperty( "Content-Type", "application/json; charset=UTF-8") ;
        httpURLConnection.setRequestProperty( "Accept", "application/json" ) ;
        //httpURLConnection.setRequestProperty("Content-length", String.valueOf(jsonInfo.length()));

        httpURLConnection.setDoInput(true);
        httpURLConnection.setDoOutput(true);

        System.out.println(putUrl);

        OutputStream outputStream = httpURLConnection.getOutputStream();

        bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
        bufferedWriter.write(jsonInfo);

        bufferedWriter.close();
        outputStream.close();

        int code = httpURLConnection.getResponseCode();
        System.out.println("Response code: "+code);

        if(code == HttpURLConnection.HTTP_OK){
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            String line;
            StringBuffer response = new StringBuffer();

            while((line = bufferedReader.readLine()) != null){
                response.append(line);
            }

            bufferedReader.close();
            return response.toString();
        }
        else{
            return "Error";
        }

    }

}

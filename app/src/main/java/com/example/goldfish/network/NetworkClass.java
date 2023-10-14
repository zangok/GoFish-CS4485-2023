package com.example.goldfish.network;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class NetworkClass {

  public static void main(String[] args) throws IOException {

  }



  //GET and POST requests
  /*
  private static void sendGETRequest(String urlStr) throws IOException {
    URL url = new URL(urlStr);
    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
    conn.setRequestMethod("GET");
    conn.setConnectTimeout(1000);
    //conn.setRequestProperty(HTTP.USER_AGENT, "myapp");

    int responseCode = conn.getResponseCode();
    if (responseCode == HttpURLConnection.HTTP_OK) {
      BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
      String inputLine;
      String output;
      while ((inputLine = in.readLine()) != null) {
        System.out.println(inputLine);
      }
      in.close();
    } else {
      System.out.println("GET request failed with response code: " + responseCode);
    }
  }

  private static void sendPOSTRequest(String urlStr, String postData) throws IOException {
    URL url = new URL(urlStr);
    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
    conn.setRequestMethod("POST");
    conn.setDoOutput(true);
    conn.getOutputStream().write(postData.getBytes());
    int responseCode = conn.getResponseCode();
    if (responseCode == HttpURLConnection.HTTP_OK) {
      BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
      String inputLine;
      while ((inputLine = in.readLine()) != null) {
        System.out.println(inputLine);
      }
      in.close();
    } else {
      System.out.println("POST request failed with response code: " + responseCode);
    }
  }

   */

}
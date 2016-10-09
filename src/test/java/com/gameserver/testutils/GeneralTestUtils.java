/*
 * © 2014 Attilio Caravelli
 *
 * Java Back End Developer Test
 *
 */

package com.gameserver.testutils;

import com.gameserver.general.ResponseResult;
import com.gameserver.utils.Configuration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * General testing utilities (Should be tested)
 */
public class GeneralTestUtils {

	/**
	 * Send a fake HTTP GET request
	 * @param urlString
	 * @return
	 * @throws IOException
	 */
	public static  ResponseResult sendGet(String urlString)
					 throws IOException 
	 {
    URL url = new URL(urlString);
    HttpURLConnection con = ((HttpURLConnection) url.openConnection());
    return new ResponseResult(con.getResponseCode(), httpUrlConnectionToString(con));
  }
  
	/**
	 * Send a fake HTTP post request
	 * @param urlString
	 * @param param
	 * @return
	 * @throws IOException
	 */
	public static  ResponseResult sendPost(String urlString, String param) 
					throws IOException 
	{
    URL url = new URL(urlString);
    HttpURLConnection con = ((HttpURLConnection) url.openConnection());
    con.setDoOutput(true);
    try (OutputStreamWriter writer = new OutputStreamWriter(con.getOutputStream())) {
      writer.write(param);
      writer.flush();
      return new ResponseResult(con.getResponseCode(), httpUrlConnectionToString(con));
    }
  }
  
	/**
	 * Utility to convert in a string an HTTP URL connection
	 * @param con
	 * @return
	 * @throws IOException
	 */
	public static  String httpUrlConnectionToString(HttpURLConnection con)
					throws IOException 
	{
    StringBuilder b = new StringBuilder();
    try (BufferedReader r = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
      String line;
      while ((line = r.readLine()) != null)
        b.append(line);
      String result = b.toString();
      return result;
    }
  }
	
	/**
	 * Fake Batching login request
	 * @param completionService
	 * @param numRequests
	 * @throws MalformedURLException
	 */
	public static void loginBatch(CompletionService<Integer> completionService, int numRequests)
          throws MalformedURLException 
	{
    for (int i = 0; i < numRequests; i++) {
      final String urlString = "http://localhost:8080/" + i + "/login";
      final URL url = new URL(urlString);
      
      final Callable<Integer> task = new Callable<Integer>() {
        @Override
        public Integer call() {
          try {
            final HttpURLConnection con = ((HttpURLConnection) url.openConnection());
            return con.getResponseCode();
          } catch (final IOException e) {
            return 404;
          }
        }
      };
      completionService.submit(task);
    }
    try {
      for (int i = 0; i < numRequests; i++) {
        final Future<Integer> f = completionService.take();
        System.out.println("Response " + i + " with status " + f.get());
      }
    } catch (final InterruptedException | ExecutionException e) {
      System.err.println(e.getMessage());
    }
  }
 
	/**
	 * Fake batching score post request
	 * @param completionService
	 * @param numRequests
	 * @param score
	 * @throws IOException
	 * @throws MalformedURLException
	 */
	public static void postScoreBatch(CompletionService<Integer> completionService, int numRequests,
          final String score) throws IOException, MalformedURLException
	{
		int port = Configuration.getPort();
		for (int i = 0; i < numRequests; i++) {  
      String session = sendGet("http://localhost:" + port + "/" + i + "/login").getResponseResult();
			final String urlString = "http://localhost:" + port + "/1/score?sessionkey=" + session;
			final String scoreBatch = score + i;
      final Callable<Integer> task = new Callable<Integer>() {
        @Override
        public Integer call() {
          try {    	
            return sendPost(urlString, scoreBatch).getResponseCode();
          } catch (final IOException e) {
            return 404;  // Error code
          }
        }
      };
      completionService.submit(task);
    }
    try {
      for (int i = 0; i < numRequests; i++) {
        final Future<Integer> f = completionService.take();
        System.out.println("Response " + i + " with status " + f.get());
      }
    } catch (final InterruptedException | ExecutionException e) {
      System.err.println(e.getMessage());
    }
  }
  
	/**
	 * Fake Batching highscore request
	 * @param completionService
	 * @param numRequests
	 * @throws MalformedURLException
	 */
	public static void getHighScoreBatch(CompletionService<Integer> completionService, int numRequests)
          throws MalformedURLException 
	{
    for (int i = 0; i < numRequests; i++) {
      final String urlString = "http://localhost:8080/1/highscorelist";
      final URL url = new URL(urlString);
      
      final Callable<Integer> task = new Callable<Integer>() {
        @Override
        public Integer call() {
          try {
            final HttpURLConnection con = ((HttpURLConnection) url.openConnection());
            int result = con.getResponseCode();
            System.out.println(result);
            return con.getResponseCode();
          } catch (final IOException e) {
            return 404;
          }
        }
      };
      completionService.submit(task);
    }
    try {
      for (int i = 0; i < numRequests; i++) {
        final Future<Integer> f = completionService.take();
        System.out.println("Response " + i + " with status " + f.get());
      }
    } catch (final InterruptedException | ExecutionException e) {
      System.err.println(e.getMessage());
    }
  }
}

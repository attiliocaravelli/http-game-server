/*
 * © 2014 Attilio Caravelli
 *
 * Java Back End Developer Test
 *
 */

package com.gameserver.general;

/**
 * Class to handle the result of a response by the game server
 */
public class ResponseResult
  {
    private int responseCode;
    
    private String responseResult;
    
    public ResponseResult(int responseCode, String responseResult) {
      this.responseCode = responseCode;
      this.responseResult = responseResult;
    }
    
    public int getResponseCode() {
      return responseCode;
    }
    
    public void setResponseCode(int responseCode) {
      this.responseCode = responseCode;
    }
    
    public String getResponseResult() {
      return responseResult;
    }
    
    public void setResponseResult(String responseResult) {
      this.responseResult = responseResult;
    }
  }

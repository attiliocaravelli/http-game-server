/*
 * © 2014 Attilio Caravelli
 *
 * Java Back End Developer Test
 *
 */

package com.gameserver.utils;

import org.junit.Test;

import com.gameserver.utils.HttpCodeUtils;

import static org.junit.Assert.*;

/**
 *
 * @author Attilio Caravelli
 */
public class HttpCodeUtilsTest {
  
  public HttpCodeUtilsTest() {
  }
  
  /**
   * Test of codeOfResponse method, of class HttpCodeUtils.
   */
  @Test
  public void testCodeOfResponse() {
    System.out.println("codeOfResponse");
    int expResult = 200;
    int result = HttpCodeUtils.codeOfResponse();
    assertEquals(expResult, result);
  } 
}

/*
 * © 2014 Attilio Caravelli
 *
 * Java Back End Developer Test
 *
 */

package com.gameserver.utils;

import org.junit.Test;

import com.gameserver.utils.StringUtils;

import static org.junit.Assert.*;

/**
 *
 * @author Attilio Caravelli
 */
public class StringUtilsTest {
  
  public StringUtilsTest() {
  }
  
  /**
   * Test NULL or empty parameter of stringToInteger method, of class StringUtils.
   */
  @Test
  public void testStringToInteger_NULL_Or_EMPTY() {
    System.out.println("stringToInteger");
    String string = "";
    Integer expResult = null;
    Integer result = StringUtils.stringToInteger(string);
    assertEquals("Empty Integer parameter return NULL", expResult, result);
    string = null;
    result = StringUtils.stringToInteger(string);
    assertEquals("NULL Integer parameter return NULL", expResult, result);
  }

  /**
   * Test NULL or empty of stringToLong method, of class StringUtils.
   */
  @Test
  public void testStringToLong_NULL_Or_EMPTY() {
    System.out.println("stringToLong");
    String string = "";
    Long expResult = null;
    Long result = StringUtils.stringToLong(string);
    assertEquals("Empty Long parameter return NULL", expResult, result);
    string = null;
    result = StringUtils.stringToLong(string);
    assertEquals("NULL Long parameter return NULL", expResult, result);
  }
  
  
  
}

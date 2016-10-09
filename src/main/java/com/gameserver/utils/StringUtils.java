/*
 * © 2014 Attilio Caravelli
 *
 * Java Back End Developer Test
 *
 */

package com.gameserver.utils;

/**
 * Utility for the strings
 */
public class StringUtils {
  
  /**
   * Convert a string to Integer
   * @param string
   * @return integer value or null
   */
  public static Integer stringToInteger(String string)
  {
    if (string == null) return null;
    
    try {
      return Integer.parseInt(string);
    }
    catch (NumberFormatException e) {
      return null;
    }
  }
  
  /**
   *  Convert a string to Long
   * @param string
   * @return long value or null
   * 
   */
  public static Long stringToLong(String string)
  {
    if ( string == null) return null;
    
    try {
      return Long.parseLong(string);
    }
    catch (NumberFormatException e) {
      return null;
    }
  }
}

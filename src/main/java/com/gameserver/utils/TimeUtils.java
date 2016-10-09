/*
 * © 2014 Attilio Caravelli
 *
 * Java Back End Developer Test
 *
 */

package com.gameserver.utils;

/**
 * Time utilities
 */
public class TimeUtils {
  
  /**
   *    Current time
   * @return Long
   */
  public static Long nanoCurrentTime()
  {
   return System.nanoTime();
  }
  
  /**
   *  Default Convert minutes to nanoseconds
   *  if the parameters is less or 0 return 0
   * @param minutes
   * @return long
   */
  public static long minutesToNanoSeconds(int minutes)
  { 
    if (minutes <= 0) return 0L;
    return 60 * minutes * 1000000000L;
  }
  
  /**
   *  Default Convert minutes to nanoseconds
   *  if the parameters is less or 0 return 0
   * @param nanoTime
   * @return long
   */
  public static long nanoSecondsToMinutes(long nanoTime)
  {
    if (nanoTime <= 0) return 0L;
    return (nanoTime / (60 * 1000000000L));
  }
}

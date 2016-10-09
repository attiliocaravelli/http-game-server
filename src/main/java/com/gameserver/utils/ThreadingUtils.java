/*
* © 2014 Attilio Caravelli
*
* Java Back End Developer Test
*
*/

package com.gameserver.utils;

/**
 *  Utility performing class
 */
public class ThreadingUtils {
  
  /** Number of logic processors */
  private static int cpus_ = Runtime.getRuntime().availableProcessors();
  
  
  /**
   *  Compute the max number of thread in concurrent by the
   *              architecture of CPUs
   * @return
   */
  public static int estimateMaxThread() {
    int maxThreads = cpus_ * 20;
    return maxThreads > 0 ? maxThreads : 1;
  }
}

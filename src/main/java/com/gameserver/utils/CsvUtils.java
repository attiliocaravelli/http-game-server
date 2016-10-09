/*
 * © 2014 Attilio Caravelli
 *
 * Java Back End Developer Test
 *
 */

package com.gameserver.utils;

import java.util.Map;
import java.util.Set;

/**
 * CSV transformation utility
 */
public class CsvUtils {
  
  /**
   * Utility to convert a set into CSV
   * @param scores Set to transform into String formatted Key=value with comma
   * @return String
   */
  public static String setOfEntryToCsv(Set<Map.Entry<Integer, Integer>> scores)
  {
    Boolean isNullScores = (scores == null);
    if (isNullScores ) {
      throw new NullPointerException("scores Set is NULL");
    }
    
    StringBuilder highScoresList = new StringBuilder();
    for (Map.Entry<Integer, Integer> score : scores) {
      String valueScore = score.getKey().toString();
      String userId = score.getValue().toString();
      String highScore = buildCsvElement(valueScore, userId);
      highScoresList.append(highScore);
    }
    
    int lastCommaIndex = highScoresList.length() - 1;
    return highScoresList.substring(0, lastCommaIndex);
  }
  
  /**
   *  Build CSV element
   * @param valueScore
   * @param userId
   * @return String
   */
  public static String buildCsvElement (String valueScore, String userId)
  {
    Boolean isNullValueScore = (valueScore == null);
    Boolean isNullUserId = (userId == null);
    if (isNullValueScore ||
            isNullUserId) {
      throw new NullPointerException("valueScore or userId are NULL");
    }
    return valueScore + "=" + userId + ",";
  }
}

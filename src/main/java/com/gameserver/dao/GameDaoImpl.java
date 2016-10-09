/*
* © 2014 Attilio Caravelli
*
* Java Back End Developer Test
*
*/

package com.gameserver.dao;

import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;

import com.gameserver.interfaces.LogInterface;
import com.gameserver.logger.GameServerLogger;
import com.gameserver.utils.CsvUtils;

/**
 *
 *  The Data structure is a Concurrent Map (by Level Id) of an concurrent
 *  implementation of a Sorted Map (sorted by Score key and as value User Id).
 *  The null pointer check of the method parameters is made
 *  before of the call method  throwing a MalformedRequestException
 *  In order to reuse or to distribute (TDD development)
 *  this interface supplies the check of the parameters
 *  of the methods
 *
 */
public class GameDaoImpl
implements GameDao<Integer, Integer, Integer>, LogInterface
{
  /** Map Of Levels mapped for Scores (key) and UserID */
  private ConcurrentHashMap<Integer, ConcurrentSkipListMap<Integer, Integer>> highScoresDataBase_;
  
  /**
   *  In order to have a better performance of the server
   *  the initialization of the Hash Map will be 15.000 levels to avoid
   *  the re-copy of the Map for the load factor
   *  Constructor
   */
  public GameDaoImpl() {
    highScoresDataBase_   = new ConcurrentHashMap<>(15000);
  }
  
  /**
   * Update an existing level
   * @param level
   * @param score
   * @param user
   */
  @Override
  public void updateLevel(Integer level, Integer score, Integer user)
  {
    boolean isNullLevel = (level == null);
    boolean isNullNewScore = (score == null);
    boolean isNullUser = (user == null);
    if (isNullLevel || isNullNewScore || isNullUser) {
      debugging("updateExistingLevel NULL: " + level + " " +
              score + " " + user);
      throw new NullPointerException("level or score or user are NULL");
    }
    
    ConcurrentSkipListMap<Integer, Integer> highScoreMap = getExistingLevel(level);
    
    if (highScoreMap == null) {
      debugging("add new level: " + level);
      highScoreMap = createLevel();
      highScoresDataBase_.put(level, highScoreMap);
    }
    highScoreMap.put(score, user); // update the level with score and user
    debugging("add new score: " + score + " " + user);
    // remove smallest score
    while (highScoreMap.size() > 15) {
      Entry<Integer, Integer> oldScore = highScoreMap.pollFirstEntry();
      debugging("old Level " + oldScore.toString() + " removed");
    }
  }
  
  /**
   *  Create a new level
   */
  @Override
  public ConcurrentSkipListMap<Integer, Integer> createLevel()
  {
    return new ConcurrentSkipListMap<>();
  }
  
  /**
   * Get a map by level
   * @param level
   * @return ConcurrentSkipListMap is null if the map doesn't exist
   */
  @Override
  public ConcurrentSkipListMap<Integer, Integer> getExistingLevel(Integer level)
  {
    boolean isNullLevelKey = (level == null);
    if (isNullLevelKey) {
      debugging("getExistingLevel NULL " + level);
      throw new NullPointerException("levelKey is NULL");
    }
    return highScoresDataBase_.get(level);
  }
  
  /**
   * Get High Scores List
   * @param level
   * @return String
   */
  @Override
  public String getHighScoresList(Integer level)
  {
    boolean isNullLevelKey = (level == null);
    if (isNullLevelKey) {
      debugging("getHighScoresList NULL " + level);
      throw new NullPointerException("levelKey is NULL");
    }
    boolean existTheLevel   = highScoresDataBase_.containsKey(level);
    if (!existTheLevel) return "";
    Set<Entry<Integer, Integer>> scores = getScoresSetDescendingOrder(level);
    String highScoresList = CsvUtils.setOfEntryToCsv(scores);
    return highScoresList;
  }
  
  /**
   * Get a set of scores in descending order
   * @param level
   * @return
   */
  @Override
  public Set<Entry<Integer, Integer>> getScoresSetDescendingOrder(Integer level)
  {
    boolean isNullLevelKey = (level == null);
    if (isNullLevelKey) {
      debugging("getScoresSetDescendingOrder NULL " + level);
      throw new NullPointerException("levelKey is NULL");
    }
    return highScoresDataBase_.get(level).descendingMap().entrySet();
  }
  
  @Override
  public void errorOnSendingData(String message)
  {
    GameServerLogger.errorOnSendingData(message);
  }
  
  @Override
  public void errorMalformedUrl()
  {
    GameServerLogger.errorMalformedUrl();
  }
  
  @Override
  public void debugging(String message)
  {
    GameServerLogger.debugging(message);
  }
  
  @Override
  public void errorSessionExpired()
  {
    GameServerLogger.errorSessionExpired();
  }
}



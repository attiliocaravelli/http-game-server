/*
* © 2014 Attilio Caravelli
*
* Java Back End Developer Test
*
*/

package com.gameserver.dao;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * Game Dao
 * @param <L> Level type
 * @param <S> Score type
 * @param <U> User type
 */
public interface GameDao<L, S, U>
{
  /**
   * Update an existing level
   * @param level 
   * @param score
   * @param user
   */
  public void             updateLevel(L level, S score, U user);
  /**
   * Save a new level
   * @return Map
   */
  public Map<S, U>        createLevel();
  /**
   * Get an existing level
   * @param level
   * @return Map
   */
  public Map<S, U>        getExistingLevel(L level);
  /**
   * Get the high score list by level
   * @param level
   * @return
   */
  public String           getHighScoresList(L level);
  /**
   * Get a score set in descending order
   * @param level
   * @return Set
   */
  public Set<Entry<S, U>> getScoresSetDescendingOrder(L level);
}

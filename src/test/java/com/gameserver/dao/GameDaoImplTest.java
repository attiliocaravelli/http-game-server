/*
* © 2014 Attilio Caravelli
*
* Java Back End Developer Test
*
*/

package com.gameserver.dao;

import java.lang.reflect.Field;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import com.gameserver.dao.GameDaoImpl;

/**
 *
 * @author Attilio Caravelli
 */
public class GameDaoImplTest {
  
  public GameDaoImplTest() {
  }
  
  /**
   * Test of updateLevel method, of class GameDaoImpl.
   */
  @Test(expected = NullPointerException.class)
  public void testUpdateLevel_NULL_Level() {
    System.out.println("updateLevel");
    Integer level = null;
    Integer score = 2;
    Integer user = 3;
    new GameDaoImpl().updateLevel(level, score, user);
  }
  
  /**
   * Test of updateLevel method, of class GameDaoImpl.
   */
  @Test(expected = NullPointerException.class)
  public void testUpdateLevel_NULL_Score() {
    System.out.println("updateLevel");
    Integer level = 1;
    Integer score = null;
    Integer user = 3;
    new GameDaoImpl().updateLevel(level, score, user);
  }
  
  /**
   * Test of updateLevel method, of class GameDaoImpl.
   */
  @Test(expected = NullPointerException.class)
  public void testUpdateLevel_NULL_User() {
    System.out.println("updateLevel");
    Integer level = 1;
    Integer score = 3;
    Integer user = null;
    new GameDaoImpl().updateLevel(level, score, user);
  }
  
   /**
   * Test of getScoresSetDescendingOrder method, of class GameDaoImpl.
   * @throws java.lang.Exception
   */
  @Test
  public void testUpdateLevel() throws Exception{
    System.out.println("testUpdateLevel");
    Integer level = 2;
    Integer score = 4;
    Integer user  = 1;
    GameDaoImpl instanceGameDao = new GameDaoImpl();
    Field dbField = Whitebox.getField(GameDaoImpl.class, "highScoresDataBase_");
    @SuppressWarnings("unchecked")
	ConcurrentHashMap<Integer, ConcurrentSkipListMap<Integer, Integer>> highScoreDataBase = (ConcurrentHashMap<Integer, ConcurrentSkipListMap<Integer, Integer>>)dbField.get(instanceGameDao);
    assertTrue(highScoreDataBase.isEmpty());
    instanceGameDao.updateLevel(level, score, user);
    assertTrue(highScoreDataBase.size()==1);
    instanceGameDao.updateLevel(level, score, user); // double check to see the overwrite of key
    assertTrue(highScoreDataBase.size()==1);
    for (int score_i=1; score_i < 20; score_i++)
      instanceGameDao.updateLevel(level, score_i, user);
    ConcurrentSkipListMap<Integer, Integer> levelMap = (ConcurrentSkipListMap<Integer, Integer>)highScoreDataBase.get(level);
    assertTrue(levelMap.size()==15);
  }
  
  /**
   * Test of createLevel method, of class GameDaoImpl.
   */
  @Test
  public void testCreateLevel() {
    System.out.println("createLevel");
    ConcurrentSkipListMap<Integer, Integer> result = new GameDaoImpl().createLevel();
    assertNotNull(result);
  }
  
  /**
   * Test of getExistingLevel method, of class GameDaoImpl.
   */
  @Test(expected = NullPointerException.class)
  public void testGetExistingLevel_NULL_Level() {
    System.out.println("testGetExistingLevel_NULL_Level");
    Integer level = null;
    new GameDaoImpl().getExistingLevel(level);
  }
  
  
  /**
   * Test of getExistingLevel method, of class GameDaoImpl.
   * @throws java.lang.Exception
   */
  @Test
  public void testGetExistingLevel() throws Exception{
    System.out.println("getExistingLevel");
    Integer level = 2;
    GameDaoImpl instanceGameDao = new GameDaoImpl();
    ConcurrentSkipListMap<Integer, Integer> result = instanceGameDao.getExistingLevel(level);
    assertNull(result);
    Field dbField = Whitebox.getField(GameDaoImpl.class, "highScoresDataBase_");
    @SuppressWarnings("unchecked")
	ConcurrentHashMap<Integer, ConcurrentSkipListMap<Integer, Integer>> highScoreDataBase = (ConcurrentHashMap<Integer, ConcurrentSkipListMap<Integer, Integer>>)dbField.get(instanceGameDao);
    highScoreDataBase.put(level, new ConcurrentSkipListMap<Integer, Integer>());
    result = instanceGameDao.getExistingLevel(level);
    assertNotNull(result);
  }
  
  /**
   * Test of getHighScoresList method, of class GameDaoImpl.
   */
  @Test
  public void testGetHighScoresList_STRING_EMPTY() {
    System.out.println("getHighScoresList");
    Integer level = 2;
    GameDaoImpl instanceGameDao = new GameDaoImpl();
    String result = instanceGameDao.getHighScoresList(level);
    assertTrue(result.isEmpty());
  }
  
  /**
   * Test of getHighScoresList method, of class GameDaoImpl.
   */
  @Test(expected = NullPointerException.class)
  public void testGetHighScoresList_NULL_Level() {
    System.out.println("testGetHighScoresList_NULL_Level");
    Integer level = null;
    new GameDaoImpl().getHighScoresList(level);
  }
  /**
   * Test of getHighScoresList method, of class GameDaoImpl.
   */
  @Test
  public void testGetHighScoresList() throws Exception{
    System.out.println("getHighScoresList");
    Integer level = 2;
    Integer score1 = 3;
    Integer score2 = 4;
    Integer user1 = 1;
    Integer user2 = 2;
    GameDaoImpl instanceGameDao = new GameDaoImpl();
    Field dbField = Whitebox.getField(GameDaoImpl.class, "highScoresDataBase_");
    @SuppressWarnings("unchecked")
	ConcurrentHashMap<Integer, ConcurrentSkipListMap<Integer, Integer>> highScoreDataBase = (ConcurrentHashMap<Integer, ConcurrentSkipListMap<Integer, Integer>>)dbField.get(instanceGameDao);
    ConcurrentSkipListMap<Integer, Integer> scores = new ConcurrentSkipListMap<Integer, Integer>();
    scores.put(score1, user1);
    scores.put(score2, user2);
    highScoreDataBase.put(level, scores);
    String expResult = "4=2,3=1";
    String result = instanceGameDao.getHighScoresList(level);
    assertEquals(expResult, result);  
  }
  
   /**
   * Test of GetScoresSetDescendingOrder method, of class GameDaoImpl.
   */
  @Test(expected = NullPointerException.class)
  public void testGetScoresSetDescendingOrder_NULL_Level() {
    System.out.println("testGetScoresSetDescendingOrder_NULL_Level");
    Integer level = null;
    new GameDaoImpl().getScoresSetDescendingOrder(level);
  }
  /**
   * Test of getScoresSetDescendingOrder method, of class GameDaoImpl.
   */
  @Test
  public void testGetScoresSetDescendingOrder() throws Exception{
    System.out.println("getScoresSetDescendingOrder");
    Integer level = 2;
    Integer score1 = 3;
    Integer score2 = 4;
    Integer user1 = 1;
    Integer user2 = 2;
    GameDaoImpl instanceGameDao = new GameDaoImpl();
    Field dbField = Whitebox.getField(GameDaoImpl.class, "highScoresDataBase_");
    @SuppressWarnings("unchecked")
	ConcurrentHashMap<Integer, ConcurrentSkipListMap<Integer, Integer>> highScoreDataBase = (ConcurrentHashMap<Integer, ConcurrentSkipListMap<Integer, Integer>>)dbField.get(instanceGameDao);
    ConcurrentSkipListMap<Integer, Integer>  scores = new ConcurrentSkipListMap<Integer, Integer> ();
    scores.put(score1, user1);
    scores.put(score2, user2);
    highScoreDataBase.put(level, scores);
    String expResult = "[4=2, 3=1]";
    String result = instanceGameDao.getScoresSetDescendingOrder(level).toString();
    assertEquals(expResult, result);  
  }
}

/*
 * © 2014 Attilio Caravelli
 *
 * Java Back End Developer Test
 *
 */

package com.gameserver.dao;

import java.lang.reflect.Field;
import java.util.concurrent.ConcurrentSkipListMap;
import org.junit.Test;
import static org.junit.Assert.*;
import org.powermock.reflect.Whitebox;

import com.gameserver.dao.SessionsDaoImpl;

/**
 *
 * @author Attilio Caravelli
 */
public class SessionsDaoImplTest {
  
  public SessionsDaoImplTest() {
  }
  
 
  /**
   * Test of saveSession method, of class SessionsDaoImpl.
   * @throws java.lang.Exception
   */
  @Test
  public void testSaveSession() throws Exception {
    System.out.println("saveSession");
    Long session = 22L;
    Integer user = 1;
    SessionsDaoImpl instanceSessionDao = new SessionsDaoImpl();
    Field dbField = Whitebox.getField(SessionsDaoImpl.class, "validSessions_");
    @SuppressWarnings("unchecked")
	ConcurrentSkipListMap<Integer, Integer>  validSessions = (ConcurrentSkipListMap<Integer, Integer> )dbField.get(instanceSessionDao);
    assertTrue(validSessions.isEmpty());
    instanceSessionDao.saveSession(session, user); 
    assertTrue(validSessions.size()==1);
  }

  /**
   * Test of createSession method, of class SessionsDaoImpl.
   */
  @Test
  public void testCreateSession() {
    System.out.println("createSession");
    SessionsDaoImpl instance = new SessionsDaoImpl();
    Long result = instance.createSession();
    assertTrue(result > 0);
  }

  /**
   * Test of getUserIdBySessionId method, of class SessionsDaoImpl.
   */
  @Test(expected = NullPointerException.class)
  public void testGetUserIdBySessionId_NULL() {
    System.out.println("getUserIdBySessionId");
    Long sessionKey = null;
    SessionsDaoImpl instance = new SessionsDaoImpl();
    instance.getUserIdBySessionId(sessionKey);
  }

  
  /**
   * Test of getUserIdBySessionId method, of class SessionsDaoImpl.
   * @throws java.lang.Exception
   */
  @Test
  public void testGetUserIdBySessionId() throws Exception{
    System.out.println("getUserIdBySessionId");
    Long session = 2L;
    Integer user = 1;
    SessionsDaoImpl instanceSessionDao = new SessionsDaoImpl();
    Field dbField = Whitebox.getField(SessionsDaoImpl.class, "validSessions_");
    @SuppressWarnings("unchecked")
	ConcurrentSkipListMap<Long, Integer>  validSessions = (ConcurrentSkipListMap<Long, Integer> )dbField.get(instanceSessionDao);
    validSessions.put(session, user);  
    Integer result = instanceSessionDao.getUserIdBySessionId(session);
    Integer expResult = 1; // the user 
    assertEquals(expResult, result);
  }

  /**
   * Test of getSessionIdByUserId method, of class SessionsDaoImpl.
   * @throws java.lang.Exception
   */
  @Test
  public void testGetSessionIdByUserId() throws Exception {
    System.out.println("getSessionIdByUserId");
    Long session = 2L;
    Integer user = 1;
    SessionsDaoImpl instanceSessionDao = new SessionsDaoImpl();
    Field dbField = Whitebox.getField(SessionsDaoImpl.class, "validSessions_");
    @SuppressWarnings("unchecked")
	ConcurrentSkipListMap<Long, Integer>  validSessions = (ConcurrentSkipListMap<Long, Integer> )dbField.get(instanceSessionDao);
    validSessions.put(session, user);  
    Long result = instanceSessionDao.getSessionIdByUserId(user);
    Long expResult = 2L; // the session 
    assertEquals(expResult, result);
  }

  /**
   * Test of removeOldestSession method, of class SessionsDaoImpl.
   * @throws java.lang.Exception
   */
  @Test
  public void testRemoveOldestSession() throws Exception{
    System.out.println("removeOldestSession");
    Long sessionOldest = 2L;
    Integer userOldest = 1;
    Long session = 5L;
    Integer user = 2;
    SessionsDaoImpl instanceSessionDao = new SessionsDaoImpl();
    Field dbField = Whitebox.getField(SessionsDaoImpl.class, "validSessions_");
    @SuppressWarnings("unchecked")
	ConcurrentSkipListMap<Long, Integer>  validSessions = (ConcurrentSkipListMap<Long, Integer> )dbField.get(instanceSessionDao);
    validSessions.put(session, user);  
    validSessions.put(sessionOldest, userOldest); 
    instanceSessionDao.removeOldestSession();
    String result = validSessions.toString();
    String expResult = "{5=2}";
    assertEquals(expResult, result);
  }

  /**
   * Test of getOldestSession method, of class SessionsDaoImpl.
   * @throws java.lang.Exception
   */
  @Test
  public void testGetOldestSession() throws Exception {
    System.out.println("getOldestSession");
    Long sessionOldest = 2L;
    Integer userOldest = 1;
    Long session = 5L;
    Integer user = 2;
    SessionsDaoImpl instanceSessionDao = new SessionsDaoImpl();
    Field dbField = Whitebox.getField(SessionsDaoImpl.class, "validSessions_");
    @SuppressWarnings("unchecked")
	ConcurrentSkipListMap<Long, Integer> validSessions = (ConcurrentSkipListMap<Long, Integer>)dbField.get(instanceSessionDao);
    validSessions.put(session, user);  
    validSessions.put(sessionOldest, userOldest); 
    Long result = instanceSessionDao.getOldestSession();
    Long expResult = 2L;
    assertEquals(expResult, result);
  }
}

/*
* © 2014 Attilio Caravelli
*
* Java Back End Developer Test
*
*/

package com.gameserver.general;

import static com.gameserver.testutils.GeneralTestUtils.sendGet;
import static com.gameserver.testutils.GeneralTestUtils.sendPost;

import java.io.IOException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.gameserver.GameServer;
import com.gameserver.utils.Configuration;

/**
 *
 * @author Attilio Caravelli
 */
public class IntegrationTest {
  
  private GameServer application_;
  
  @Before
  public void setUp() throws IOException {
    application_ = new GameServer();
    application_.start();
  }
  
  @After
  public void tearDown() {
    application_.stop();
  }
  
  
  @Test
  public void testLoginUrl() throws IOException {
    ResponseResult responseResult = sendGet("http://localhost:8080/4711/login");
    Assert.assertEquals(
            "The server should reply 200 as the url with login is mapped to an entry point", 200,
            responseResult.getResponseCode());
  }
  
  @Test
  public void testHighScore() throws IOException {
    // Login of 3 users
    String sessionKey11 = sendGet("http://localhost:8080/11/login").getResponseResult();
    String sessionKey22 = sendGet("http://localhost:8080/22/login").getResponseResult();
    String sessionKey33 = sendGet("http://localhost:8080/33/login").getResponseResult();
    // The first user scores
    sendPost("http://localhost:8080/1/score?sessionkey=" + sessionKey11, "5");
    sendPost("http://localhost:8080/2/score?sessionkey=" + sessionKey11, "10");
    sendPost("http://localhost:8080/2/score?sessionkey=" + sessionKey11, "5");
    // The second user scores
    sendPost("http://localhost:8080/1/score?sessionkey=" + sessionKey22, "8");
    sendPost("http://localhost:8080/3/score?sessionkey=" + sessionKey22, "30");
    // The third user scores
    sendPost("http://localhost:8080/3/score?sessionkey=" + sessionKey33, "35");
    sendPost("http://localhost:8080/3/score?sessionkey=" + sessionKey33, "3");
    // Highscores for the three levels
    String highScores1 = sendGet("http://localhost:8080/1/highscorelist").getResponseResult();
    Assert.assertEquals("High Score for level1", "8=22,5=11", highScores1);
    String highScores2 = sendGet("http://localhost:8080/2/highscorelist").getResponseResult();
    Assert.assertEquals("High Score for level2", "10=11,5=11", highScores2);
    String highScores3 = sendGet("http://localhost:8080/3/highscorelist").getResponseResult();
    Assert.assertEquals("High Score for level3", "35=33,30=22,3=33", highScores3);
    
  }
  
  @Test
  public void testHighScoreMoreThanFifteenScores() throws IOException {
    int port = Configuration.getPort();
    for (int i = 0; i < 20; i++) {
      String urlLogin = "http://localhost:" + port + "/" + i + "/login";
      String sessionKey = sendGet(urlLogin).getResponseResult();
      
      String urlPostScore = "http://localhost:" + port + "/1/score?sessionkey=" + sessionKey;
      int points = 100 - i;
      sendPost(urlPostScore, String.valueOf(points));
    }
    
    String highScores1 = sendGet("http://localhost:" + port +"/1/highscorelist").getResponseResult();
    Assert.assertEquals(
            "100=0,99=1,98=2,97=3,96=4,95=5,94=6,93=7,92=8,91=9,90=10,89=11,88=12,87=13,86=14",
            highScores1);
    
  }
}

/*
* © 2014 Attilio Caravelli
*
* Java Back End Developer Test
*
*/

package com.gameserver.general;

import static com.gameserver.testutils.GeneralTestUtils.getHighScoreBatch;
import static com.gameserver.testutils.GeneralTestUtils.loginBatch;
import static com.gameserver.testutils.GeneralTestUtils.postScoreBatch;

import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.gameserver.GameServer;
import com.gameserver.utils.Configuration;

/**
 * Performance testing class
 */
public class PerformanceTest {
	/** The simulator */
  private ThreadPoolExecutor executorService_;
  /** The game server application */
	private GameServer application_;
  
	/**
	 * Game server and simulator will start
	 * @throws IOException
	 */
	@Before
  public void setUp() throws IOException {
		Configuration.setPort(8081);
		application_ = new GameServer();
    application_.start();
    int cpus = Runtime.getRuntime().availableProcessors();
    int maxThreads = cpus * 10;
    maxThreads = (maxThreads > 0 ? maxThreads : 1);
    executorService_ = new ThreadPoolExecutor(
                    maxThreads, // core thread pool size
                    maxThreads, // maximum thread pool size
                    1, // time to wait before resizing pool
                    TimeUnit.MINUTES, new ArrayBlockingQueue<Runnable>(maxThreads, true),
                    new ThreadPoolExecutor.CallerRunsPolicy());
  }
  
	/**
	 * Game server and simulator will stop
	 */
	@After
  public void tearDown() {
    executorService_.shutdownNow();
		application_.stop();
  }
  
	/**
	 *
	 * @throws IOException
	 */
	@Test
  public void measureLoadLogin() throws IOException {
    long startTime = System.currentTimeMillis();
    CompletionService<Integer> completionService =
            new ExecutorCompletionService<>(executorService_);
    int numRequests = 2;
    loginBatch(completionService, numRequests);
    long ellapsedTime = System.currentTimeMillis() - startTime;
    System.out.println("Elapsed time measureLoadLogin: " + ellapsedTime + " milliseconds");
  }
  
	/**
	 * Measurement the load on several post score request (
	 * @throws IOException
	 */
	@Test
  public void measureLoadPostScore() throws IOException {
    long startTime = System.currentTimeMillis();
    CompletionService<Integer> completionService =
            new ExecutorCompletionService<>(executorService_);
    int numRequests = 10;
    postScoreBatch(completionService, numRequests, "5");
    postScoreBatch(completionService, numRequests, "10");
    long ellapsedTime = System.currentTimeMillis() - startTime;
    System.out.println("Elapsed time measureLoadPostScore: " + ellapsedTime + " milliseconds");
  }
  
	/**
	 *
	 * @throws IOException
	 */
	@Test
  public void measureLoadGetHighScore() throws IOException {
    long startTime = System.currentTimeMillis();
    CompletionService<Integer> completionService =
            new ExecutorCompletionService<>(executorService_);
    int numRequests = 5;
    postScoreBatch(completionService, numRequests, "10");
    getHighScoreBatch(completionService, numRequests);
    long ellapsedTime = System.currentTimeMillis() - startTime;
    System.out.println("Elapsed time measureLoadGetHighScore: " + ellapsedTime + " milliseconds");
  }
}

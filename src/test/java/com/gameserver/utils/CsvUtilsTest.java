/*
* © 2014 Attilio Caravelli
*
* Java Back End Developer Test
*
*/

package com.gameserver.utils;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;

import com.gameserver.utils.CsvUtils;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 *
 * @author Attilio Caravelli
 */
public class CsvUtilsTest {
	
	private static Set<Map.Entry<Integer, Integer>> scores_;
	
	public CsvUtilsTest() {
	}
	
	@BeforeClass
	public static void setUpClass() {
		@SuppressWarnings("unchecked")
		Map.Entry<Integer, Integer> entry1 = mock(Map.Entry.class);
		when(entry1.getKey()).thenReturn(12);
		when(entry1.getValue()).thenReturn(1);
		@SuppressWarnings("unchecked")
		Map.Entry<Integer, Integer> entry2 = mock(Map.Entry.class);
		when(entry2.getKey()).thenReturn(23);
		when(entry2.getValue()).thenReturn(2);
		scores_ = new HashSet<>();
		scores_.add(entry1);
		scores_.add(entry2);
	}
	
	
	
	/**
	 * Test of setToCsv method, of class CsvUtils.
	 */
	@Test(expected=NullPointerException.class)
	public void testSetOfEntryToCsv_NULL() {
		System.out.println("setToCsv");
		Set<Map.Entry<Integer, Integer>> scores = null;
		CsvUtils.setOfEntryToCsv(scores);
	}
	
	/**
	 * Test of buildCsvElement method, of class CsvUtils.
	 * score = 12 userid = 1
	 */
	@Test
	public void testBuildCsvElement_12_1() {
		System.out.println("buildCsvElement");
		String valueScore = "12";
		String userId = "1";
		String expResult = "12=1,";
		String result = CsvUtils.buildCsvElement(valueScore, userId);
		assertEquals(expResult, result);
	}
	
	/**
	 * Test of buildCsvElement method, of class CsvUtils.
	 * score = null userid = 1
	 */
	@Test(expected=NullPointerException.class)
	public void testBuildCsvElement_NULL_1() {
		System.out.println("buildCsvElement");
		String valueScore = null;
		String userId = "1";
		 CsvUtils.buildCsvElement(valueScore, userId);
	}
	
	/**
	 * Test of buildCsvElement method, of class CsvUtils.
	 * score = 12 userid = null
	 */
	@Test(expected=NullPointerException.class)
	public void testBuildCsvElement_12_NULL() {
		System.out.println("buildCsvElement");
		String valueScore = "12";
		String userId = null;
		CsvUtils.buildCsvElement(valueScore, userId);
	}
	
	/**
	 * Test of buildCsvElement method, of class CsvUtils.
	 * score = null userid = null
	 */
	@Test(expected=NullPointerException.class)
	public void testBuildCsvElement_NULL_NULL() {
		System.out.println("buildCsvElement");
		String valueScore = null;
		String userId = null;
		CsvUtils.buildCsvElement(valueScore, userId);
	}
}

/*
* © 2014 Attilio Caravelli
*
* Java Back End Developer Test
*
*/

package com.gameserver.annotations;

import java.lang.annotation.Annotation;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.gameserver.annotations.CompanyInfo;

/**
 *
 * @author Attilio Caravelli
 */
@CompanyInfo (
        developer = "Attilio Caravelli",
        company   = "© 2014 Attilio Caravelli",
        date      = "09/21/2014",
        currentRevision = "0.1"
)
public class CompanyInfoTest {
  
  public CompanyInfoTest() {
  }
  
  @BeforeClass
  public static void setUpClass()
  {
  }
  
  @AfterClass
  public static void tearDownClass() {
  }
  
  @Before
  public void setUp() {
  }
  
  @After
  public void tearDown() {
  }
  
  
  /**
   * Test of is annotation present CompanyInfo.
   */
  @Test
  public void testIsAnnotationPresent() {
    System.out.println("Is annotation present");
    assertTrue(this.getClass().isAnnotationPresent(CompanyInfo.class));
  }
  
  /**
   * Test of instance of CompanyInfo.
   */
  @Test
  public void testInstanceOf() {
    System.out.println("Instance Of");
    Annotation annotation = this.getClass().getAnnotation(CompanyInfo.class);
    assertTrue(annotation instanceof CompanyInfo);
  }
  
  /**
   * Test of developer method, of class CompanyInfo.
   */
  @Test
  public void testDeveloper() {
    System.out.println("Annotation developer");
    String expResult = "Attilio Caravelli";
    Annotation annotation = this.getClass().getAnnotation(CompanyInfo.class);
    CompanyInfo companyInfo = (CompanyInfo) annotation;
    String result = companyInfo.developer();
    assertEquals(expResult, result);
  }
  
  /**
   * Test of company method, of class CompanyInfo.
   */
  @Test
  public void testCompany()
  {
    System.out.println("Annotation company");
    String expResult = "© 2014 Attilio Caravelli";
    Annotation annotation = this.getClass().getAnnotation(CompanyInfo.class);
    CompanyInfo companyInfo = (CompanyInfo) annotation;
    String result = companyInfo.company();
    assertEquals(expResult, result);
  }
  
  /**
   * Test of date method, of class CompanyInfo.
   */
  @Test
  public void testDate()
  {
    System.out.println("Annotation date");
    String expResult = "09/21/2014";
    Annotation annotation = this.getClass().getAnnotation(CompanyInfo.class);
    CompanyInfo companyInfo = (CompanyInfo) annotation;
    String result = companyInfo.date();
    assertEquals(expResult, result);
  }
  
  /**
   * Test of currentRevision method, of class CompanyInfo.
   */
  @Test
  public void testCurrentRevision() {
    System.out.println("Annotation currentRevision");
    String expResult = "0.1";
    Annotation annotation = this.getClass().getAnnotation(CompanyInfo.class);
    CompanyInfo companyInfo = (CompanyInfo) annotation;
    String result = companyInfo.currentRevision();
    assertEquals(expResult, result);
  }
}

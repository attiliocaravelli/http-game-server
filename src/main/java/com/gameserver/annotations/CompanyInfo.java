/*
* © 2014 Attilio Caravelli
*
* Java Back End Developer Test
*
*/

package com.gameserver.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Company Info Annotation
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE) 
public @interface CompanyInfo {

  /**
   *
   * @return
   */
  String developer();

  /**
   *
   * @return
   */
  String company();

  /**
   *
   * @return
   */
  String date();

  /**
   *
   * @return
   */
  String currentRevision();
}

/*
 * © 2014 Attilio Caravelli
 *
 * Java Back End Developer Test
 *
 */

package com.gameserver;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.gameserver.annotations.CompanyInfoTest;
import com.gameserver.dao.GameDaoImplTest;
import com.gameserver.dao.SessionsDaoImplTest;
import com.gameserver.i18n.MessagesTest;
import com.gameserver.utils.ConfigurationTest;
import com.gameserver.utils.CsvUtilsTest;
import com.gameserver.utils.HttpCodeUtilsTest;
import com.gameserver.utils.StringUtilsTest;
import com.gameserver.utils.ThreadingUtilsTest;
import com.gameserver.utils.TimeUtilsTest;

/**
 *
 * All tests
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({CompanyInfoTest.class, GameDaoImplTest.class,
    SessionsDaoImplTest.class, MessagesTest.class,
    ConfigurationTest.class, CsvUtilsTest.class, HttpCodeUtilsTest.class, 
StringUtilsTest.class, ThreadingUtilsTest.class, TimeUtilsTest.class})
public class AllUnitTests {
  
}
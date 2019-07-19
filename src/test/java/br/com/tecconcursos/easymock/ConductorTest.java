package br.com.tecconcursos.easymock;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

/*@Config(
	    browser = Browser.CHROME,
	    url     = "https://www.tecconcursos.com.br/"
	)
*/public class ConductorTest {
	
	private WebDriver driver;

	@Test
	public void test() {
		driver.get("http://demo.guru99.com/test/guru99home/");
		String title = driver.getTitle();
		assertTrue(title.contains("Demo Guru99 Page"));
	}

	@Before
	public void beforeTest() {
		driver = new FirefoxDriver();
	}

	@After
	public void afterTest() {
		driver.quit();
	}

}

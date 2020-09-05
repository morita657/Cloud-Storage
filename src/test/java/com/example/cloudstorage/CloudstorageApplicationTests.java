package com.example.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudstorageApplicationTests {
	@LocalServerPort
	private Integer port;

	private static WebDriver driver;
//	private CounterPage counter;

	@BeforeAll
	public static void beforeAll(){
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
	}

	@AfterAll
	public static void afterAll(){
		driver.quit();
	}


	@Test
	@Order(1)
	public void dashboardAccessWithoutLogin(){
		driver.get(String.format("http://localhost:%s/dashboard",port));
	}

}

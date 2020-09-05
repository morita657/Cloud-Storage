package com.example.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudstorageApplicationTests {

	@LocalServerPort
	private Integer port;

	private static WebDriver driver;

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
		assertThat(driver.getTitle()).isEqualTo("Login");
	}

	private void _signupInput(String firstName, String lastName, String username, String password){

		WebDriverWait wait = new WebDriverWait(driver, 10);
		WebElement firstNameInputField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputFirstName")));
		firstNameInputField.sendKeys(firstName);

		WebElement lastNameNameInputField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputLastName")));
		lastNameNameInputField.sendKeys(lastName);

		WebElement usernameInputField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		usernameInputField.sendKeys(username);

		WebElement passwordInputField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		passwordInputField.sendKeys(password);

		WebElement signUpButton = driver.findElement(By.id("buttonSignUp"));
		wait.until(ExpectedConditions.elementToBeClickable(signUpButton)).submit();

		WebElement successMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("divSuccess")));
		assertThat(successMsg.getText().contains("You successfully signed up!"));
	}

	private void _loginInput(String username, String password){
		assertThat(driver.getTitle()).isEqualTo("Login");
		WebDriverWait wait = new WebDriverWait(driver, 10);

		WebElement usernameInputField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		usernameInputField.sendKeys(username);

		WebElement passwordInputField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		passwordInputField.sendKeys(password);

		WebElement signUpButton = driver.findElement(By.id("buttonLogin"));
		wait.until(ExpectedConditions.elementToBeClickable(signUpButton)).submit();
	}

	private void _logoutClick(){
		WebDriverWait wait = new WebDriverWait(driver, 10);

		WebElement logoutButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("buttonLogout")));
		assertThat(driver.getTitle()).isEqualTo("Home");
		logoutButton.submit();
		System.out.println("logout lickc..");
	}

	private void _accessHome(){
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		driver.get(String.format("http://localhost:%s/dashboard", this.port));
		assertThat(driver.getTitle()).isEqualTo("Login");
	}

	@Test
	@Order(2)
	public void trySignupLoginLogoutAndAccessHomeAgain(){
		driver.get(String.format("http://localhost:%s/signup",port));
		assertThat(driver.getTitle()).isEqualTo("Sign Up");
		String firstName = "fsfsdf";
		String lastName = "dfsd";
		String username = "fsdf";
		String password = "fsufs";
		//		signup
		_signupInput(firstName, lastName, username, password);
		//		login
		driver.get(String.format("http://localhost:%s/login", this.port));
		_loginInput(username, password);
		//		logout
		_logoutClick();
		//		try to access home page
		_accessHome();
	}

}

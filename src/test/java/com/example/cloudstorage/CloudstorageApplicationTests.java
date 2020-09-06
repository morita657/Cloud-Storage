package com.example.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
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

		WebElement loginButton = driver.findElement(By.id("buttonLogin"));
		wait.until(ExpectedConditions.elementToBeClickable(loginButton)).submit();
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



	private void _createNote(String title, String description){
		WebDriverWait wait = new WebDriverWait(driver, 10);
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		WebElement noteTab = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));
//		noteTab.click();
		executor.executeScript("arguments[0].click()", noteTab);
		WebElement newNoteButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("buttonAddNewNote")));
		newNoteButton.click();


		WebElement titleInputField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-title")));
		titleInputField.sendKeys(title);
		WebElement descriptionInputField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-description")));
		descriptionInputField.sendKeys(description);
		WebElement saveBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("saveNoteButton")));
		saveBtn.click();
	}

	private void _checkNote(String title, String description){
		//		go back home
		driver.get(String.format("http://localhost:%s/dashboard", this.port));

		WebDriverWait wait = new WebDriverWait(driver, 10);
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		// go to notes tab
		WebElement noteTab = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));
		executor.executeScript("arguments[0].click()", noteTab);

		assertThat(noteTab.getText().contains(title));
		assertThat(noteTab.getText().contains(description));
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
//				logout
		_logoutClick();
//				try to access home page
		_accessHome();
	}

	@Test
	@Order(3)
	public void createNoteAndVerify(){
//		login with the existing account
		String firstName = "fsfsdf";
		String lastName = "dfsd";
		String username = "fsdf";
		String password = "fsufs";
		//		signup
		_signupInput(firstName, lastName, username, password);
		//		login
		driver.get(String.format("http://localhost:%s/login", this.port));
		_loginInput(username, password);
//		create note
		String title = "super duper book";
		String description = "I am super duper new book!!!";
		_createNote(title, description);
//		check it is listed
		_checkNote(title, description);
	}

	private void _editNote(String title, String description){
		driver.get(String.format("http://localhost:%s/dashboard", this.port));
		WebDriverWait wait = new WebDriverWait(driver, 10);
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		WebElement noteTab = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));
//		open note tab
		executor.executeScript("arguments[0].click()", noteTab);
//		click edit button
//		btn btn-success edit-note-button
		WebElement editBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("edit-note-button")));
		executor.executeScript("arguments[0].click()", editBtn);
//		input new title and description
		WebElement titleInputField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-title")));
		titleInputField.sendKeys(title);
		WebElement descriptionInputField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-description")));
		descriptionInputField.sendKeys(description);
//		click save button
		WebElement saveBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("saveNoteButton")));
		saveBtn.click();

	}

	@Test
	@Order(4)
	public void editNoteVerify(){
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
		//		create note
		String title = "super duper book";
		String description = "I am super duper new book!!!";
		_createNote(title, description);
		String newtTitle = "new super duper book";
		String newDescription = "I am a new super duper book!!!";
//		edit the existing note
		_editNote(newtTitle, newDescription);
		_checkNote(newtTitle, newDescription);
	}

}

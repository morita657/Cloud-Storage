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

	private void _notContains(String title, String description){
		//		go back home
		driver.get(String.format("http://localhost:%s/dashboard", this.port));

		WebDriverWait wait = new WebDriverWait(driver, 10);
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		// go to notes tab
		WebElement noteTab = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));
		executor.executeScript("arguments[0].click()", noteTab);

		assertThat(!noteTab.getText().contains(title));
		assertThat(!noteTab.getText().contains(description));
	}

	private void _deleteNote(){
		//		go back home
		driver.get(String.format("http://localhost:%s/dashboard", this.port));

		WebDriverWait wait = new WebDriverWait(driver, 10);
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		// go to notes tab
		WebElement noteTab = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));
		executor.executeScript("arguments[0].click()", noteTab);

		WebElement deleteBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("delete-note-button")));
		executor.executeScript("arguments[0].click()", deleteBtn);
	}

	private void _createCredential(String url, String username, String password){
		WebDriverWait wait = new WebDriverWait(driver, 10);
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		WebElement credentialTab = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials-tab")));
//		noteTab.click();
		executor.executeScript("arguments[0].click()", credentialTab);
		WebElement newCredentialButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("buttonAddNewCredential")));
		newCredentialButton.click();

		WebElement urlInputField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-url")));
		urlInputField.sendKeys(url);
		WebElement usernameInputField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-username")));
		usernameInputField.sendKeys(username);
		WebElement passwordInputField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-password")));
		passwordInputField.sendKeys(password);
		WebElement saveBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("saveCredentialButton")));
		saveBtn.click();
	}

	private void _checkCredential(String url, String username, String password){
		//		go back home
		driver.get(String.format("http://localhost:%s/dashboard", this.port));

		WebDriverWait wait = new WebDriverWait(driver, 10);
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		// go to notes tab
		WebElement credentialTab = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials-tab")));
		executor.executeScript("arguments[0].click()", credentialTab);

		assertThat(credentialTab.getText().contains(url));
		assertThat(credentialTab.getText().contains(username));
		assertThat(credentialTab.getText().contains(password));
	}

	private void _editCredential(String url, String username, String password){
		driver.get(String.format("http://localhost:%s/dashboard", this.port));
		WebDriverWait wait = new WebDriverWait(driver, 10);
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		WebElement credentialTab = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials-tab")));
//		open note tab
		executor.executeScript("arguments[0].click()", credentialTab);
//		click edit button
//		btn btn-success edit-note-button
		WebElement editBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("edit-credential-button")));
		executor.executeScript("arguments[0].click()", editBtn);
//		input new title and description

		WebElement urlInputField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-url")));
		urlInputField.sendKeys(url);
		WebElement usernameInputField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-username")));
		usernameInputField.sendKeys(username);
		WebElement passwordInputField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-password")));
		passwordInputField.sendKeys(password);
//		click save button
		WebElement saveBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("saveCredentialButton")));
		saveBtn.click();
	}

	private void _deleteCredential(){
		//		go back home
		driver.get(String.format("http://localhost:%s/dashboard", this.port));

		WebDriverWait wait = new WebDriverWait(driver, 10);
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		// go to notes tab
		WebElement credentialTab = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials-tab")));
		executor.executeScript("arguments[0].click()", credentialTab);
//btn btn-danger delete-credential-button
		WebElement deleteBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("delete-credential-button")));
		executor.executeScript("arguments[0].click()", deleteBtn);
	}

	private void _credentialNotContains(String url, String username, String password){
		//		go back home
		driver.get(String.format("http://localhost:%s/dashboard", this.port));

		WebDriverWait wait = new WebDriverWait(driver, 10);
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		// go to notes tab
		WebElement credentialTab = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials-tab")));
		executor.executeScript("arguments[0].click()", credentialTab);

		assertThat(!credentialTab.getText().contains(url));
		assertThat(!credentialTab.getText().contains(username));
		assertThat(!credentialTab.getText().contains(password));

	}


	@Test
	@Order(1)
	public void dashboardAccessWithoutLogin(){
		driver.get(String.format("http://localhost:%s/dashboard",port));
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

	@Test
	@Order(5)
	public void deleteNoteVerify(){
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
		_deleteNote();
		_notContains(newtTitle, newDescription);
	}


	@Test
	@Order(6)
	public void createCredentialAndVerify(){
//		login with the existing account
		String firstName = "fsfsdf";
		String lastName = "dfsd";
		String username = "fsdf";
		String password = "fsufs";
		driver.get(String.format("http://localhost:%s/signup",port));
		//		signup
		_signupInput(firstName, lastName, username, password);
		//		login
		driver.get(String.format("http://localhost:%s/login", this.port));
		_loginInput(username, password);

		String url = "http://localhost:8080/dashboard";
		_createCredential(url, username, password);
//		check it is listed
		_checkCredential(url, username, password);
	}

	@Test
	@Order(7)
	public void editCredentialAndVerify(){
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

		String url = "http://localhost:8080/dashboard";
		_createCredential(url, username, password);

		String newUrl = "http://localhost:8080/dashboard";
		String newUsername = "new_username";
		String newPassword = "new_password";
		_editCredential(newUrl, newUsername, newPassword);
		_checkCredential(newUrl, newUsername, newPassword);
	}

	@Test
	@Order(8)
	public void deleteCredentialAndVerify(){
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

		String url = "http://localhost:8080/dashboard";
		_createCredential(url, username, password);

		_deleteCredential();
		_credentialNotContains(url, username, password);
	}
}

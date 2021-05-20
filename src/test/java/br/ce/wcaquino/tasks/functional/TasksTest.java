package br.ce.wcaquino.tasks.functional;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class TasksTest {
	
	public WebDriver acessarAplicacao() throws MalformedURLException {
		System.setProperty("webdriver.chrome.driver", "/home/rcnweb/devops/chromedriver/chromedriver");
//		WebDriver driver = new ChromeDriver();
		DesiredCapabilities cap = DesiredCapabilities.chrome();
		WebDriver driver = new RemoteWebDriver(new URL(" http://10.0.10.224:4444/wd/hub"),cap);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		driver.navigate().to("http://10.0.10.224:8001/tasks");
		return driver;
	}
	
	@Test
	public void deveSalvarTarefaComSucesso() throws MalformedURLException {
		
		WebDriver driver = acessarAplicacao();
		try {
			driver.findElement(By.id("addTodo")).click();
			
			driver.findElement(By.id("task")).sendKeys("Teste via Selenium");
			
			driver.findElement(By.id("dueDate")).sendKeys("10/10/2030");
			
			driver.findElement(By.id("saveButton")).click();
			
			String mensagem = driver.findElement(By.id("message")).getText();
			
			Assert.assertEquals("Success!", mensagem);
		} finally {
			driver.quit();
		}
		
	}
	
	@Test
	public void naoDeveSalvarTarefaSemDescricao() throws MalformedURLException {
		
		WebDriver driver = acessarAplicacao();
		try {
			driver.findElement(By.id("addTodo")).click();
			
			driver.findElement(By.id("dueDate")).sendKeys("10/10/2030");
			
			driver.findElement(By.id("saveButton")).click();
			
			String mensagem = driver.findElement(By.id("message")).getText();
			
			Assert.assertEquals("Fill the task description", mensagem);
		} finally {
			driver.quit();
		}
		
	}
	
	@Test
	public void naoDeveSalvarTarefaSemData() throws MalformedURLException {
		
		WebDriver driver = acessarAplicacao();
		try {
			driver.findElement(By.id("addTodo")).click();
			
			driver.findElement(By.id("task")).sendKeys("Teste via Selenium");
			
			driver.findElement(By.id("saveButton")).click();
			
			String mensagem = driver.findElement(By.id("message")).getText();
			
			Assert.assertEquals("Fill the due date", mensagem);
		} finally {
			driver.quit();
		}
		
	}
	
	@Test
	public void naoDeveSalvarTarefaComDataPassada() throws MalformedURLException {
		
		WebDriver driver = acessarAplicacao();
		try {
			driver.findElement(By.id("addTodo")).click();
			
			driver.findElement(By.id("task")).sendKeys("Teste via Selenium");
			
			driver.findElement(By.id("dueDate")).sendKeys("10/10/2010");
			
			driver.findElement(By.id("saveButton")).click();
			
			String mensagem = driver.findElement(By.id("message")).getText();
			
			Assert.assertEquals("Due date must not be in past", mensagem);
		} finally {
			driver.quit();
		}
		
	}

}

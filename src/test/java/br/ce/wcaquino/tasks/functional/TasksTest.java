package br.ce.wcaquino.tasks.functional;

import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class TasksTest {
	
	public WebDriver acessarAplicacao() {
		System.setProperty("webdriver.chrome.driver", "/home/rcnweb/devops/chromedriver/chromedriver");
		WebDriver driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		driver.navigate().to("http://localhost:8001/tasks");
		return driver;
	}
	
	@Test
	public void deveSalvarTarefaComSucesso() {
		
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
	public void naoDeveSalvarTarefaSemDescricao() {
		
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
	public void naoDeveSalvarTarefaSemData() {
		
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
	public void naoDeveSalvarTarefaComDataPassada() {
		
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

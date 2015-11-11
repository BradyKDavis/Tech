package program.exercises;

import static org.junit.Assert.*;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jfree.ui.RefineryUtilities;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;

import org.jfree.ui.ApplicationFrame;


public class LifeCalculatorTest {
	
	Connection connection;
	ResultSet result30;
	ResultSet result50;
	ResultSet result100;
	LifeCalculator calculator30;
	LifeCalculator calculator50;
	LifeCalculator calculator100;
	LifeCalculator calculatorNull;
	@Before
	public void init(){
		try{
			String url = "jdbc:mysql://127.0.0.1:3306/test";
			String user = "test";
			String password = "test";
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			
			Connection conn = DriverManager.getConnection(url, user, password);
			Statement statement = conn.createStatement();
			result30 = statement.executeQuery("SELECT * FROM Tech_Lifespans ORDER BY ID ASC LIMIT 30;");
			calculator30 = new LifeCalculator(result30);
			result50 = statement.executeQuery("SELECT * FROM Tech_Lifespans ORDER BY ID ASC LIMIT 50;");
			calculator50 = new LifeCalculator(result50);
			result100 = statement.executeQuery("SELECT * FROM Tech_Lifespans ORDER BY ID ASC;");
			calculator100 = new LifeCalculator(result100);
			
		}
		catch (SQLException ex){
		    // handle any errors
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());
		}
		catch(Exception e){
			System.err.println(e.toString());
		}
		
	}
	
	@Test
	public void Test30Data(){
		//Test data currently indicates that 1900 will be the year with the most people alive
		int max = calculator30.maxYear();
		assertEquals(1900, max);
	}
	
	@Test
	public void Test50Data(){
		int max = calculator50.maxYear();
		assertEquals(1945, max);
	}
	
	@Test
	public void Test100Data(){
		int max = calculator100.maxYear();
		assertEquals(1998, max);
	}

	//following tests are simply to show the distribution of #people alive over years
	@Test
	public void Show30Data() throws InterruptedException{
		JLineChart chart = new JLineChart("First 30 items", calculator30.getData());
		chart.pack();
		RefineryUtilities.centerFrameOnScreen(chart);
		chart.setVisible(true);
		
		assertEquals(true, true);
	}
	
	@Test
	public void Show50Data() throws InterruptedException{
		JLineChart chart = new JLineChart("First 50 items", calculator50.getData());
		chart.pack();
		RefineryUtilities.centerFrameOnScreen(chart);
		chart.setVisible(true);
		
		assertEquals(true, true);
	}
	

	@Test
	public void Show100Data() throws InterruptedException{
		JLineChart chart = new JLineChart("First 100 items", calculator100.getData());
		chart.pack();
		RefineryUtilities.centerFrameOnScreen(chart);
		chart.setVisible(true);
		
		assertEquals(true, true);
	}
}

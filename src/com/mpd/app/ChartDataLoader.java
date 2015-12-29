package com.mpd.app;

import com.mpd.app.HistoricalPrice;
import com.mpd.app.JaxConverter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javafx.stage.Stage;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

@SuppressWarnings("unused")
public class ChartDataLoader extends Application {
	
	static GetProperties gp = new GetProperties();
	static ResultSet resultSet = null;
	static ResultSet availableCharts = null;
	static String date,stock;
	static int volume;
	static float open;
	static float close;
	static float high;
	static float low;
	static float adjclose;
	
	public static void doDataLoad() throws SQLException {
		Connection connect = null;
		String stock = gp.selectStock();
		String sql = "select * from historicalprices where stock ="+"'"+stock+"'";
		final Logger LOGGER = Logger.getLogger(DirWatcher.class.getName());
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			connect = DriverManager.getConnection(gp.selectJdbcDriver(),gp.selectMysqlUser(),gp.selectMysqlPasswd());
			PreparedStatement pst = (PreparedStatement) connect.prepareStatement(sql);
			resultSet = pst.executeQuery(sql);
		} catch (InstantiationException e) {
			LOGGER.log(Level.WARNING, "Could not instantiate connection.", e);
		} catch(IllegalAccessException e) {
			LOGGER.log(Level.WARNING, "Illegal Access Exception.", e);
        } catch(ClassNotFoundException e1) {
			LOGGER.log(Level.WARNING, "Class not found.", e1);
        }
		
		connect.close();
		
    }
	
	static void writeMetaData(ResultSet resultSet ) throws SQLException {
		System.out.println("The columns in the table are: ");
	    
	    System.out.println("Table: " + resultSet.getMetaData().getTableName(1));
	    for  (int i = 1; i<= resultSet.getMetaData().getColumnCount(); i++){
	      System.out.println("Column " +i  + " "+ resultSet.getMetaData().getColumnName(i));
	    }
	}
	
	public static String GetResultSetDate() throws SQLException {
		date = resultSet.getString(1);
		return date;
	}

	public static float GetResultSetHigh() throws SQLException {
		high = resultSet.getInt(3);
		return high;
		
	}
	
	public static float GetResultSetLow() throws SQLException {
		low = resultSet.getInt(4);
		return low;
	}
	
	public static String GetResultSetStock() throws SQLException {
		stock = resultSet.getString(8);
		return stock;
	}
	
	public static int GetResultSetVolume() throws SQLException {
		volume = resultSet.getInt(6);
		return volume;
	}

	public static float GetResultSetOpen() throws SQLException {
		open = resultSet.getInt(2);
		return open;
	}
	
	public static float GetResultSetClose() throws SQLException {
		close = resultSet.getInt(5);
		return close;
	}
	
	public static float GetResultSetAdjClose() throws SQLException {
		adjclose = resultSet.getInt(7);
		return adjclose;
	}
	
	public static void createXMLFromDatabase() throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		
		HistoricalPrice hp = new HistoricalPrice();
		Connection connect = null;
		//String stock = gp.selectStock();
		String stock = "NDAQ";
		String sql = "select * from historicalprices where stock ="+"'"+stock+"'";
		try {
			
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			connect = DriverManager.getConnection(gp.selectJdbcDriver(),gp.selectMysqlUser(),gp.selectMysqlPasswd());
			PreparedStatement pst = (PreparedStatement) connect.prepareStatement(sql);
			resultSet = pst.executeQuery(sql);
			
			while(resultSet.next()){
				hp.adjclose = GetResultSetAdjClose();
				hp.close = GetResultSetClose();
				hp.open = GetResultSetOpen();
				hp.high = GetResultSetHigh();
				hp.low = GetResultSetLow();
				hp.date = GetResultSetDate();
				hp.volume = GetResultSetVolume();
				hp.stock = GetResultSetStock();
				
				OutputStream os = new FileOutputStream("/home/mikedes/testfile.xml", true);
				JAXBContext jaxbContext = JAXBContext.newInstance(HistoricalPrice.class);
				Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
				
				
				jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,true);
				
				jaxbMarshaller.marshal(hp, os);
				//jaxbMarshaller.marshal(hp, System.out);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JAXBException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	public static void showAvailableTickerCharts() throws SQLException {
		Connection connect1 = null;
		String sql_stocks = "SELECT DISTINCT(stock) from historicalprices";
		Logger LOGGER = Logger.getLogger(DirWatcher.class.getName());
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			connect1 = DriverManager.getConnection(gp.selectJdbcDriver(),gp.selectMysqlUser(),gp.selectMysqlPasswd());
			PreparedStatement pst1 = (PreparedStatement) connect1.prepareStatement(sql_stocks);
			availableCharts = pst1.executeQuery(sql_stocks);
		} catch(ClassNotFoundException e1) {
			LOGGER.log(Level.WARNING, "Class not found.", e1);
		} catch(IllegalAccessException e) {
			LOGGER.log(Level.WARNING, "Illegal Access Exception.", e);
		} catch(InstantiationException e) {
			LOGGER.log(Level.WARNING, "Could not instantiate connection.", e);
		}
		
		while (availableCharts.next()){
			String stock = availableCharts.getString(1);
			System.out.println(stock);
		}
	}
	
	public void start(Stage stage) throws SQLException{
		final CategoryAxis xAxis = new CategoryAxis();
		final NumberAxis yAxis = new NumberAxis();
		xAxis.setLabel("Date");
		final LineChart<String, Number> lineChart = new LineChart<String,Number>(xAxis, yAxis);
		
		lineChart.setTitle("Daily volume");
		XYChart.Series<String, Number> series = new XYChart.Series<String, Number>();
	
		series.setName("Volume Data");
		
		while(resultSet.next()){
			series.getData().add(new XYChart.Data<String, Number>(GetResultSetDate(), GetResultSetVolume()));
		}
		
		Scene scene = new Scene(lineChart, 800,900);
		lineChart.getData().add(series);
		
		stage.setScene(scene);
		stage.show();
	}
	
	public static void main(String[] args){
		try {
			doDataLoad();
			createXMLFromDatabase();
			//showAvailableTickerCharts();
			//launch(args);
		} catch (SQLException | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}

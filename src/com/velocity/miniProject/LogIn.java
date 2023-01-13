package com.velocity.miniProject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import package1.Admin;
import package1.Aquarium;
import package2.ConnectionConnectivity;
public class LogIn extends ConnectionConnectivity{
	//ConnectionConnectivity connectionConnectivity = new ConnectionConnectivity();
	Connection connection =null;
	PreparedStatement preparedStatement =null;
	Scanner scanner = new Scanner(System.in);
	public void mainPage() throws SQLException {
		System.out.println("Welcome to Aquarium Shopmatic Ecommerce Solutions!!! \n");
		System.out.println("Enter your log in type from (User/Admin)");
		String str =scanner.next();
		if(str.equals("User")){
			userLogInSignUp();
		}else if (str.equals("Admin")) {
			Admin admin = new Admin();
			admin.adminLogin();
		}else {
			System.out.println("Invalid user type please run again");
			mainPage();
		}
	}
	void userLogInSignUp() throws SQLException {
		System.out.println("\nEnter login if you are existing customer ");
		System.out.println("Enter signup if you are new customer ");
		String str =scanner.next();
		if(str.equals("login")){
			userLogIn();
		}else if (str.equals("signup")) {
			userSignUp();
		}else {
			System.out.println("Invalid customer type please run again");
			mainPage();
		}
	}
	void userSignUp() throws SQLException {
		System.out.println("Enter your name");
		scanner.nextLine();
		String customer_name =scanner.nextLine();
		System.out.println("Enter your address");
		String customer_address =scanner.nextLine();
		System.out.println("Enter your contact number");
		String customer_contact =scanner.next();
		System.out.println("Enter your user name");
		String customer_username =scanner.next();
		System.out.println("Enter your password");
		String customer_password =scanner.next();
		try {
			connection =getConnection();
			preparedStatement=connection.prepareStatement("insert into customers"+ 
			"(customer_name,customer_address,customer_contact,customer_username,customer_password) "+ "values (?,?,?,?,?)");
			preparedStatement.setString(1, customer_name);
			preparedStatement.setString(2, customer_address);
			preparedStatement.setString(3, customer_contact);
			preparedStatement.setString(4, customer_username);
			preparedStatement.setString(5, customer_password);
			int update =preparedStatement.executeUpdate();
			System.out.println("Record update succesfully " + update);
		}catch(Exception e){
			System.out.println(e);
		}finally{
			connection.close();
			preparedStatement.close();
		}
		userLogIn(); 
	}
	void userLogIn() throws SQLException{
		int customer_id=0;
		System.out.println("\nEnter your user name");
		String customer_username_entered = scanner.next();
		Connection connection =null;
		PreparedStatement preparedStatement =null;
		String customer_username_database = "";
		try {
			connection = getConnection();
			preparedStatement=connection.prepareStatement("select * from customers where customer_username =?");
			preparedStatement.setString(1, customer_username_entered);
			ResultSet resultSet=preparedStatement.executeQuery();
			while(resultSet.next()){
				customer_username_database = resultSet.getString("customer_username");
			}
		}catch(Exception e){
			System.out.println(e);
		}finally{
			connection.close();
			preparedStatement.close();
		}
		if(customer_username_entered.equals(customer_username_database)){
			System.out.println("\nEnter your password");
			String customer_password_entered = scanner.next();
			String customer_password_database = "";
			try {
				connection = getConnection();
				preparedStatement=connection.prepareStatement("select * from customers where customer_password =?");
				preparedStatement.setString(1, customer_password_entered);
				ResultSet resultSetPassword=preparedStatement.executeQuery();
				while (resultSetPassword.next()){
					customer_id = resultSetPassword.getInt("customer_id");
					customer_password_database = resultSetPassword.getString("customer_password");
				}
			}catch(Exception e){
				System.out.println(e);
			}finally{
				connection.close();
				preparedStatement.close();
			}
			if(customer_password_entered.equals(customer_password_database)){
				System.out.println("Log In success \n");
				Aquarium aquariumMainPage = new Aquarium();
				aquariumMainPage.aquerium(customer_id);	
			}else {
				System.out.println("Invalid password please run again");
				userLogInSignUp();
			}
		}else {
			System.out.println("Invalid User name");
			userLogInSignUp();
		}
	}
	
}

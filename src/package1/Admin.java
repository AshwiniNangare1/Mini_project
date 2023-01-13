package package1;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import package2.ConnectionConnectivity;

public class Admin extends ConnectionConnectivity{
	
	Scanner scanner = new Scanner(System.in);
	
	Connection connection = null;
	PreparedStatement preparedStatement = null;
	private String username;
	private String password;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void adminLogin(){
		

		System.out.println("/n");
		System.out.println("Changes made in package-1 test branch");
		System.out.println("this is new changes in file");
		System.out.println("Enter Username");
		username=scanner.next();
		try {
			connection = getConnection();
			preparedStatement=connection.prepareStatement("select * from Admin where username =?");
			preparedStatement.setString(1, username);
			ResultSet resultSet=preparedStatement.executeQuery();
			String username_database = "";
			while(resultSet.next()){
				username_database = resultSet.getString("username");
			}
			if(username.equals(username_database)){
				System.out.println("Enter your password");
				password = scanner.next();
				preparedStatement=connection.prepareStatement("select * from Admin where password =?");
				preparedStatement.setString(1, password);
				ResultSet resultSetPassword=preparedStatement.executeQuery();
				String password_database = "";
				while (resultSetPassword.next()){
					username = resultSetPassword.getString("username");
					password_database = resultSetPassword.getString("password");
				}
				if(password.equals(password_database)){
					System.out.println("Log In success");
					Admin admin = new Admin();
					admin.adminHomePage();		
				}else {
					System.out.println("Invalid password please try again");
				}
			}else {
				System.out.println("UnAuthorized Admin ");
				adminLogin();
			}
		}catch(Exception e){
			e.printStackTrace();	
		}
	}
	public void adminHomePage() throws SQLException{
	System.out.println("Enter 1 if you want to check inventory");
	System.out.println("Enter 2 if you want to check regitered user list");
	System.out.println("Enter 3 if you want to check user history for product purchase details");
	int adminInput=scanner.nextInt();
	switch(adminInput) {
	    case 1:
		   checkProductQuantity();         
		break;
	    case 2:
		   checkRegisteredUsers();	
		break;
	    case 3:
		   checkUserHistory();   
		break;
	    case 4:
			   checkUserHistory();   
			break;
	    default :
		   System.out.println("Enter a valid input");
		   adminHomePage();
	}
}
public void checkProductQuantity() throws SQLException{
	System.out.println("Enter the product_id from table");
	try {
	scanner = new Scanner(System.in);
	int product_id=scanner.nextInt();
	connection = getConnection();
	preparedStatement =connection.prepareStatement("select product_id,product_name,product_quantity from product_info where product_id =?");
	preparedStatement.setInt(1, product_id);
	ResultSet resultSet=preparedStatement.executeQuery();
		while(resultSet.next()){
			System.out.println("product_id >> "+ resultSet.getInt("product_id"));
			System.out.println("product_name >> " +resultSet.getString("product_name"));
			System.out.println("product_quantity >> " +resultSet.getInt("product_quantity"));
		}
	}catch(Exception e) {
		System.out.println(e);
	}finally {
		connection.close();
		preparedStatement.close();
	}
	adminHomePage();
}
public void checkRegisteredUsers() throws SQLException{
	try {
	connection=getConnection();
	preparedStatement = connection.prepareStatement("select customer_id,customer_name from customers");
	ResultSet resultSet =preparedStatement.executeQuery();
		while (resultSet.next()){
			System.out.println("customer_id >> " + resultSet.getInt("customer_id"));
			System.out.println("customer_name >> " + resultSet.getString("customer_name"));
		}
	}catch(Exception e){
		System.out.println(e);
	}finally {
		connection.close();
		preparedStatement.close();
	}
	adminHomePage();
}
public void checkUserHistory() throws SQLException {
	int customerCount =0;
	int customer_id=0;
	try {
		System.out.println("Enter customer ID");
		customer_id =scanner.nextInt();
		connection = getConnection();
		preparedStatement =connection.prepareStatement
		("select COUNT(customer_id) from purchase_history where customer_id =? ");
		preparedStatement.setInt(1, customer_id);
		ResultSet resultSet =preparedStatement.executeQuery();
		while(resultSet.next()) {
				customerCount=resultSet.getInt(1);
		}
	}catch(Exception e) {
			System.out.println(e);
	}finally {
		connection.close();
		preparedStatement.close();
	}
		System.out.println(customerCount);
		try {
			connection = getConnection();
			preparedStatement=connection.prepareStatement("select * from purchase_history where customer_id=?");
			preparedStatement.setInt(1, customer_id);
			ResultSet result =preparedStatement.executeQuery();
			for(int i=0; i<=customerCount;i++){
				while(result.next()){
					int productId = result.getInt("product_id");
					System.out.println("product_id >> " + productId);
					System.out.println("Product name >> " + result.getString("product_name"));
					int price=result.getInt("product_price");
					System.out.println("product price >> " + price);
					int quantity = result.getInt("product_quantity");
					System.out.println("product quantity >> " + quantity);
				}
				System.out.println("new changes in admin");
			}
		}catch(Exception e) {
			System.out.println(e);
	    }finally {
		    connection.close();
		    preparedStatement.close();
	    }
	  adminHomePage();
	  scanner.close();
	}
}

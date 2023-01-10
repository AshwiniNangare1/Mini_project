package com.velocity.miniProject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Aquarium {
	
	Connection connection=null;
	PreparedStatement preparedStatement=null;
	ResultSet resultSet=null;
	Scanner scanner=new Scanner(System.in);
	ConnectionConnectivity connectionConnectivity = new ConnectionConnectivity();
	
	public void aquerium(int customer_id) throws SQLException
	{
		System.out.println("Enter 1 if you want to buy fish");
		System.out.println("Enter 2 if you want to buy Aquerium plant");
		int choice =scanner.nextInt();
		if(choice == 1){
			Aquariumfish(customer_id);
		}
		else if(choice == 2){
			AquariumPlants(customer_id);
		}
		else {
			System.out.println("Invalid choice");
			aquerium(customer_id);
		}
		scanner.close();
	}
	String product_name;
	public void AquariumPlants(int customer_id) throws SQLException
	{
		try {
			
			 connection = connectionConnectivity.getConnection();
		     preparedStatement = connection.prepareStatement("select product_id,product_name from product_info where product_type='Aquariam Plant' order by product_name ASC");
			
			 resultSet = preparedStatement.executeQuery();
			System.out.println("Product names-plants>>" );
			while (resultSet.next()) {	
				System.out.println( resultSet.getString("product_name"));
			}
			}
		catch (Exception e) {
			e.printStackTrace();
		}	
		finally {
			connection.close();
			preparedStatement.close();
		}
		getcases(customer_id);
		
	}
	public void Aquariumfish(int customer_id) throws SQLException
	{
		try 
		{
			 connection = connectionConnectivity.getConnection(); // navigate to ConnectionDemo class-getConnection() method
		
			 preparedStatement = connection.prepareStatement("select product_id,product_name from product_info where product_type='Aquariam fish' order by product_name ASC");
			
			resultSet = preparedStatement.executeQuery();
			
			System.out.println("\nProduct names-fishes>> " );
			while (resultSet.next()) {
				System.out.println( resultSet.getString("product_name"));
				
			}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally {
			
			connection.close();
			preparedStatement.close();
		}
		getcases(customer_id);
	}
	
		public void getcases(int customer_id) throws SQLException
		{
			try {
				System.out.print("\nEnter the product name >> ");
				scanner.nextLine();
				String choose_product_name = scanner.nextLine();
				connection= connectionConnectivity.getConnection();
			   
				preparedStatement=connection.prepareStatement
						("select * from product_info where product_name =?");
				preparedStatement.setString(1, choose_product_name);
				resultSet=preparedStatement.executeQuery();
				String product_name_database = "";
				while(resultSet.next())
				{
					product_name_database = resultSet.getString("product_name");
				}
				
				if( choose_product_name.equals(product_name_database))
				{
					System.out.println("\nProduct description");
					
					//call palnt Description method(String Product_name);
					showDiscription(customer_id,product_name_database);
				}
				
				
			}catch(Exception e)
			{
				e.printStackTrace();
			}
			finally {
				connection.close();
				preparedStatement.close();
			}
		}
	
	
	void showDiscription(int customer_id,String product_name) throws SQLException
	{
		int productID = 0;
		try {
			 connection = connectionConnectivity.getConnection();
			preparedStatement=connection.prepareStatement
					("select * from product_info where product_name =?");
			preparedStatement.setString(1, product_name);
			resultSet=preparedStatement.executeQuery();
			
			while(resultSet.next())
			{
				productID = resultSet.getInt("product_id");
				System.out.println("Product name >> " + resultSet.getString("product_name"));
				System.out.println("Product description >> " + resultSet.getString("product_description"));
				System.out.println("Product quantity >> " + resultSet.getString("product_quantity"));
				System.out.println("Product price >> " + resultSet.getString("product_price"));
				System.out.println("Product type >> " + resultSet.getString("product_type"));
			}
			System.out.println("\nEnter 1 if you want to add this product to your cart");
			System.out.println("Enter 2 if you want to go to your cart for payment");
			System.out.println("Enter 3 if you want to go to main menu");
			int num=scanner.nextInt();
			Cart cart = new Cart();
		int quantity =0;
		switch(num) {
		case 1:
			System.out.print("\nEnter the quntity >> ");
			quantity=scanner.nextInt();
			cart.addToCart(customer_id,productID,quantity);
			break;
		case 2:
			cart.goToCart(customer_id,productID);
			break;
		case 3:
			aquerium (customer_id);
			break;
		default :
			System.out.println("Invalid input please try again");
			showDiscription( customer_id, product_name);
		}
		}
		catch (Exception e){
			System.out.println(e);
		}
		finally {
			connection.close();
			preparedStatement.close();
		}
			scanner.close();
		
	}
}



package package2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Scanner;

import package1.Aquarium;

public class Cart {
	ConnectionConnectivity connectionConnectivity = new ConnectionConnectivity();
	Connection connection = null;
	PreparedStatement preparedStatement = null;
	Scanner scanner = new Scanner(System.in);
	public void addToCart(int customer_id,int product_id,int quantity) {
		String customer_name ="";
		String product_name = "";
		int product_price = 0;
		Scanner scanner = new Scanner(System.in);
		try {
			connection = connectionConnectivity.getConnection();
			preparedStatement=connection.prepareStatement("select * from customers where customer_id =?");
			preparedStatement.setInt(1, customer_id);
			ResultSet resultSet=preparedStatement.executeQuery();
			while (resultSet.next()){
				customer_name=resultSet.getString("customer_name");
			}
			preparedStatement=connection.prepareStatement("select * from product_info where product_id =?");
			preparedStatement.setInt(1, product_id);
			ResultSet resultSetProduct=preparedStatement.executeQuery();
			while (resultSetProduct.next()){
			    product_name=resultSetProduct.getString("product_name");
			    product_price = resultSetProduct.getInt("product_price");
			}
			preparedStatement=connection.prepareStatement("insert into purchase_history (customer_id,customer_name,"
							+ "product_id,product_name,product_price,product_quantity) values(?,?,?,?,?,?)");
			preparedStatement.setInt(1, customer_id);
			preparedStatement.setString(2, customer_name);
			preparedStatement.setInt(3, product_id);
			preparedStatement.setString(4, product_name );
			preparedStatement.setInt(5, product_price );
			preparedStatement.setInt(6, quantity);
			preparedStatement.executeUpdate();
			System.out.println(quantity +" " + product_name +" is added to the cart \n");	
			System.out.println("Enter 1 if you want to go to your cart for payment");
			System.out.println("Enter 2 if you want to go to main menu");
			System.out.println("Enter 3 if corrections made in same branch same package and different class");
			int num2=scanner.nextInt();
			switch(num2) {
			case 1:
				goToCart(customer_id,product_id);
				break;
			case 2:
				Aquarium aquariumMainPage = new Aquarium();
				aquariumMainPage.aquerium(customer_id);
				break;
			default :
				System.out.println("Enter a valid input");
				addToCart(customer_id,product_id,quantity);
			}
		}catch(Exception e){
			System.out.println(e);
		}
		scanner.close();
	}
	public void goToCart(int customer_id,int product_id) {
		try {
			connection = connectionConnectivity.getConnection();
			preparedStatement =connection.prepareStatement("select COUNT(customer_id) from purchase_history where customer_id =? ");
			preparedStatement.setInt(1, customer_id);
			ResultSet resultSet =preparedStatement.executeQuery();
			int customerCount =0;
			while(resultSet.next()) {
			    customerCount=resultSet.getInt(1);
			}
			connection = connectionConnectivity.getConnection();
			preparedStatement=connection.prepareStatement("select * from purchase_history where customer_id=?");
			preparedStatement.setInt(1, customer_id);
			ResultSet result =preparedStatement.executeQuery();
			ArrayList<Integer> productIDs = new ArrayList<Integer>();
			ArrayList<Integer> productPurchasedQuantitys = new ArrayList<Integer>();
			ArrayList<Integer> productExsistingQuantitys = new ArrayList<Integer>();
			int totalCost =0;
			for(int i=0; i<=customerCount;i++){
				while(result.next()){
					int productId = result.getInt("product_id");
					productIDs.add(productId);
					System.out.println("Product name >> " + result.getString("product_name"));
					int price=result.getInt("product_price");
					System.out.println("product price >> " + price);
					int quantity = result.getInt("product_quantity");
					productPurchasedQuantitys.add(quantity);
					System.out.println("product quantity >> " + quantity);
					totalCost = totalCost + price*quantity;
				}
			}
			System.out.println("\nTotal cost of your cart = " + totalCost +"\n");
			System.out.println("Enter 1 if you want to checkOut");
			System.out.println("Enter 2 if you want to go to main menu");
			int choice=scanner.nextInt();
			switch(choice) {
			case 1:
				System.out.println("Payment succesful...");
				// deducting quantities from product info table
				connection = connectionConnectivity.getConnection();
				for (int i=0;i<productIDs.size();i++) {
				    preparedStatement=connection.prepareStatement("select * from product_info where product_id =?");
				    int product_ID = productIDs.get(i);
				    preparedStatement.setInt(1,product_ID );
				    ResultSet resultSet2=preparedStatement.executeQuery();
				    while(resultSet2.next()) {
				        int exsistingQuantity =resultSet2.getInt("product_quantity");
				        productExsistingQuantitys.add(exsistingQuantity);
				    }
				}
				for (int i=0;i<productIDs.size();i++) {
				    preparedStatement=connection.prepareStatement("update product_info set product_quantity =? where product_id = ?");
				    preparedStatement.setInt(1, productExsistingQuantitys.get(i) - productPurchasedQuantitys.get(i));
				    preparedStatement.setInt(2, productIDs.get(i));
				    preparedStatement.executeUpdate();
				}
				break;
			case 2:
				Aquarium aquariumHomePage = new Aquarium();
				aquariumHomePage.aquerium(customer_id);
				break;
			default :
				System.out.println("Enter a valid input");
				goToCart(customer_id,product_id);
				scanner.close();
			}		
		}catch(Exception e) {
			System.out.println(e);
		}
	}
	public static void main(String[] args) {
		Cart cart = new Cart();
		cart.goToCart(4,8);
		System.out.println("Changes made in Cart");
	}
}

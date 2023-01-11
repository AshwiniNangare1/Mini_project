package package2;
import java.sql.SQLException;

import com.velocity.miniProject.LogIn;
public class ExecutionClass {
	public static void main(String[] args) throws SQLException {
		System.out.println("this is test branch pakage2");
		
		System.out.println("othr working in pakge1");
		LogIn logIn = new LogIn();
		logIn.mainPage();
	}
}
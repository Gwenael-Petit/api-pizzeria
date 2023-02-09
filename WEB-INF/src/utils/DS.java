package utils;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DS {
	
	private Properties p;
	
	public DS() {
		this.p = new Properties();
		try {
			p.load(getClass().getResourceAsStream("config.prop"));
		} catch (Exception e) {
			System.err.println("config.prop n'existe pas");
		}
		
	} 
	
	public Connection getConnection() throws Exception {
		Class.forName(p.getProperty("driver"));
		String url = p.getProperty("url");
		String nom = p.getProperty("login");
		String mdp = p.getProperty("password");
		return DriverManager.getConnection(url, nom, mdp);
	}

}

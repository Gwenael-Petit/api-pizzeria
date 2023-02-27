package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import utils.DS;

public class UsersDAO {
	
	private DS ds = new DS();
	
	public boolean exist(String login, String pwd) {
		boolean res = false;
		try(Connection con = ds.getConnection()) {
			PreparedStatement ps = con.prepareStatement("select * from users where login=? and password=?");
			ps.setString(1, login);
			ps.setString(2, pwd);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) res = true;
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return res;
	}
	
	public String findPasswordByLogin(String login) {
		String pwd = null;
		try(Connection con = ds.getConnection()) {
			PreparedStatement ps = con.prepareStatement("select * from users where login=?");
			ps.setString(1, login);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				pwd = rs.getString("password");
			}
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return pwd;
	}

}

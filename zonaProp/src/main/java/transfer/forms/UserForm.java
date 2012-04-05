package transfer.forms;

import java.security.InvalidParameterException;

import javax.servlet.http.HttpServletRequest;

import transfer.bussiness.User;

public class UserForm {
	
	HttpServletRequest req;
	
	public UserForm(HttpServletRequest req){
		this.req=req;
	}
	
	public User getUser(){
		
		if(!getPassword1().equals(getPassword2())){
			throw new InvalidParameterException("las contraseñas deben coincidir");
		}
		
		
		return new User(0, getName(), getLastname(), getMail(), getPhone(), getUsername(), getPassword1());

	}
	
	public String getUsername(){
		return req.getParameter("username");
	}
	
	public String getPassword1(){
		return req.getParameter("password1");
	}
	
	public String getPassword2(){
		return req.getParameter("password2");
	}
	
	public String getName(){
		return req.getParameter("name");
	}
	
	public String getLastname(){
		return req.getParameter("lastname");
	}
	
	public String getMail(){
		return req.getParameter("mail");
	}
	
	public String getPhone(){
		return req.getParameter("phone");
	}
}
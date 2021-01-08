package model;

import java.io.Serializable;
 


public class InfoDTO implements Serializable
{
//	public int LOGIN;
//	public int SEND;
//	public int EXIT;
//	public int MANAGER;
//	public int COSTOMER;
	public enum Chat{
		LOGIN, SEND, EXIT
	}
	public enum Info{
		MANAGER, COSTOMER
	}

	 

	private static final long serialVersionUID = 1L;
	private String loginId;
	private String message;
	private String csLoginId;
	private Chat chat;
	private Info info;
	
	public String getCsLoginId() 
	{
		return csLoginId;
	}
	public void setCsLoginId(String csLoginId) 
	{
		this.csLoginId = csLoginId;
	}
	public String getLoginId() 
	{
		return loginId;
	}
	public void setLoginId(String loginId)
	{
		this.loginId = loginId;
	}
	public String getMessage() 
	{
		return message;
	}
	public void setMessage(String message)
	{
		this.message = message;
	}
	public Chat getChat()
	{
		return chat;
	}
	public void setChat(Chat chat) 
	{
		this.chat = chat;
	}
	public Info getInfo()
	{
		return info;
	}
	public void setInfo(Info info) 
	{
		this.info = info;
	}
}
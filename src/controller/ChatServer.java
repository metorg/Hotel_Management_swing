package controller;

import java.io.IOException;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ChatServer
{
	private ServerSocket ss;
	private ArrayList<ChatHandler> list;
	
	public ChatServer()
	{
		try
		{
			ss = new ServerSocket(9300);
			System.out.println("채팅 서버 준비 완료!!!");
			
			list = new ArrayList<ChatHandler>();
			
			while(true)
			{
				Socket socket = ss.accept();
				ChatHandler handler = new ChatHandler(socket, list);
				handler.start();
				
				list.add(handler);
			}
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args)
	{
		new ChatServer();
	}
}
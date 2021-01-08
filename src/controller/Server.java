package controller;

import java.io.IOException;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server
{
	private ServerSocket ss;
	private ArrayList<Handler> list;
	
	public Server()
	{
		try 
		{
			ss = new ServerSocket(9200);
			System.out.println("서버 준비 완료!!!");
			
			list = new ArrayList<Handler>();
			
			while(true)
			{
				Socket socket = ss.accept();
				Handler handler = new Handler(socket, list);
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
		new Server();
	}
}
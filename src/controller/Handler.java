package controller;

import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class Handler extends Thread
{
	private BufferedReader br;
	private PrintWriter pw;
	private Socket socket;
	private ArrayList<Handler> list;
	
	public Handler(Socket socket, ArrayList<Handler> list) throws IOException
	{
		this.socket = socket;
		this.list = list;
		
		br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
	}
	
	@Override
	public void run()
	{
		while(true)
		{
			try
			{
				String room = br.readLine();
				
				if(room == null || room.toLowerCase().equals("exit"))
				{
					broadcast("exit");
					
					br.close();
					pw.close();
					socket.close();
					
					break;
				}
				else
				{
					broadcast(room);
				}
			} 
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	public void broadcast(String msg)
	{
		for(Handler data : list)
		{
			data.pw.println(msg);
			data.pw.flush();
		}
	}
}
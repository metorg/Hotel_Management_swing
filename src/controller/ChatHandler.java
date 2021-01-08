package controller;

import java.io.IOException;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import model.InfoDTO;
import model.InfoDTO.Chat;


public class ChatHandler extends Thread
{
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	private Socket socket;
	private ArrayList<ChatHandler> list;
	
	public ChatHandler(Socket socket, ArrayList<ChatHandler> list) throws IOException
	{
		this.socket = socket;
		this.list = list;
		
		oos = new ObjectOutputStream(socket.getOutputStream());
		ois = new ObjectInputStream(socket.getInputStream());
	}
	
	@Override
	public void run() 
	{
		while(true)
		{
			InfoDTO dto = null;
			
			try
			{
				dto = (InfoDTO)ois.readObject();
				
				if(dto.getChat() == Chat.LOGIN)
				{
					broadcast(dto);
					//oos.writeObject(dto);
					//oos.flush();
				}
				else if(dto.getChat() == Chat.SEND)
				{
					broadcast(dto);
					//oos.writeObject(dto);
					//oos.flush();
				}
				else if(dto.getChat() == Chat.EXIT)
				{
					broadcast(dto);
					//oos.writeObject(dto);
					//oos.flush();
					
					ois.close();
					oos.close();
					socket.close();
					
					break;
				}
			} 
			catch (ClassNotFoundException e) 
			{
				e.printStackTrace();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	public void broadcast(InfoDTO sendDTO)
	{
		for(ChatHandler data : list)
		{
			try
			{
				data.oos.writeObject(sendDTO);
				data.oos.flush();
			}
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}
	}
}
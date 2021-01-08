package view;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import model.InfoDTO;
import model.InfoDTO.Chat;
import model.InfoDTO.Info;



public class ChatClient extends JFrame implements ActionListener, Runnable
{
	private static final long serialVersionUID = 1L;
	private JTextField input;
	private JTextArea output;
	private JButton send;
	private Socket socket;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	private String loginId;
	private String name;
	private String tel;
	
	
	public ChatClient(String loginId, String name, String tel)
	{
		this.loginId = loginId;
		System.out.println("aaa="+loginId);
		System.out.println("this = " + this);
		
		input = new JTextField(15);
		output = new JTextArea();
		send = new JButton("보내기");
		
		JScrollPane scroll = new JScrollPane(output);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		JPanel p = new JPanel(new BorderLayout());
		
		output.setEditable(false);
		p.add("Center", input);
		p.add("East", send);
		
		Container c = this.getContentPane();
		
		c.add("Center", scroll);
		c.add("South", p);
		
		setTitle(loginId);
		setBounds(100, 80, 300, 300);
		setLocationRelativeTo(null);
		setVisible(true);
		
		this.addWindowListener(new WindowAdapter()
		{
			@Override
			public void windowClosing(WindowEvent e)
			{
				if(ois == null || ois == null)
				{
					System.exit(0);
				}
				InfoDTO dto = new InfoDTO();
				
				dto.setLoginId(loginId);
				dto.setChat(Chat.EXIT);
				dto.setInfo(Info.COSTOMER);
					
				try 
				{
					oos.writeObject(dto);
					oos.flush();
				}
				catch (IOException e1) 
				{
					e1.printStackTrace();
				}
			}
		});
	}
	
	public void service()
	{
		String serverIP = "127.0.0.1";
		
		try
		{
			socket = new Socket(serverIP, 9300);
			
			ois = new ObjectInputStream(socket.getInputStream());
			oos = new ObjectOutputStream(socket.getOutputStream());
			
			InfoDTO dto = new InfoDTO();
			dto.setChat(Chat.LOGIN);
			dto.setLoginId(loginId);
			dto.setCsLoginId("Manager" + loginId);
			dto.setInfo(Info.COSTOMER);
			
			oos.writeObject(dto);
			oos.flush();
		} 
		catch (UnknownHostException e) 
		{
			System.out.println("서버를 찾을 수 없습니다");
			e.printStackTrace();
			System.exit(0);
		} 
		catch (IOException e) 
		{
			System.out.println("서버를 연결이 안되었습니다");
			e.printStackTrace();
			System.exit(0);
		}
		send.addActionListener(this);
		input.addActionListener(this);
		
		Thread t = new Thread(this);
		t.start();
	}
	
	@Override
	public void run() 
	{
		InfoDTO dto = null;
		
		while(true)
		{
			try 
			{
				dto = (InfoDTO)ois.readObject();
				
				if(dto.getInfo() == Info.MANAGER)
				{
					if(dto.getCsLoginId().equals(loginId))
					{
						if(dto.getChat() == Chat.LOGIN)
						{
							output.append(dto.getLoginId() + "님이 입장하셨습니다.\n");
						
							int pos = output.getText().length();
							output.setCaretPosition(pos);
						}
						else if(dto.getChat() == Chat.SEND)
						{
							output.append("[" + dto.getLoginId() + "] : " + dto.getMessage() + "\n");
							
							int pos = output.getText().length();
							output.setCaretPosition(pos);
						}
					}
				}
				else if(dto.getInfo() == Info.COSTOMER)
				{
					if(dto.getLoginId().equals(loginId))
					{
						if(dto.getChat() == Chat.EXIT)
						{
							ois.close();
							oos.close();
							socket.close();
							
							new RoomChoice(loginId, name, tel);
							this.setVisible(false);
							break;
						}
					}
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
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		String line = input.getText();	
		output.append("[" + loginId + "] : " + line + "\n");
		InfoDTO dto = new InfoDTO();
		
		if(e.getSource() == send)
		{
			dto.setChat(Chat.SEND);
			dto.setMessage(line);
			dto.setCsLoginId("Manager" + loginId);
			dto.setLoginId(loginId);
			dto.setInfo(Info.COSTOMER);
		}
		
		try
		{
			oos.writeObject(dto);
			oos.flush();
		} 
		catch (IOException e1) 
		{
			e1.printStackTrace();
		}
		
		input.setText("");
	}
}
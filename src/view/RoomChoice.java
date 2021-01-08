package view;

import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.AbstractBorder;
import javax.swing.border.SoftBevelBorder;

import model.ReservationDAO;
import model.ReservationDTO;

public class RoomChoice extends JFrame implements ActionListener, Runnable
{
	private static final long serialVersionUID = 1L;
	public static final int NUMBER = 8;
	private JButton[] imgBtn;
	private JLabel[] room_NumL;
	private JLabel[] priceL;
	private JLabel[] reservationL;
	private ImageIcon[] image;
	private JComboBox<String> yearCB;
	private JComboBox<String> monthCB;
	private JComboBox<String> dayCB;
	private RoundedButton memberInfo;
	private RoundedButton chat;
	private JLabel main;
	private Socket socket;
	private BufferedReader br;
	private PrintWriter pw;
	private String loginId;
	private String name;
	private String tel;
	private ArrayList<String> yearList;
	private ArrayList<String> monthList;
	private ArrayList<String> dayList;
	private String[] dayStr;
	private String[] monthStr;
	private String[] yearStr;
	private ChatClient cc;
	private int temp;

	public RoomChoice(String loginId, String name, String tel)
	{
		this.loginId = loginId;
		this.name = name;
		this.tel = tel;
		
	    ImageIcon img = new ImageIcon("img/hotel_logo.png");
	    this.setIconImage(img.getImage());
	    
	    this.setTitle("객실 예약");
		
		imgBtn = new JButton[NUMBER];
		room_NumL = new JLabel[NUMBER];
		priceL = new JLabel[NUMBER];
		reservationL = new JLabel[NUMBER];
		image = new ImageIcon[NUMBER];
		
		yearmonthday();
		
		yearCB = new JComboBox<String>(yearList.toArray(new String[yearList.size()]));
		monthCB = new JComboBox<String>(monthList.toArray(new String[monthList.size()]));
		dayCB = new JComboBox<String>(dayList.toArray(new String[dayList.size()]));
		
		Font font = new Font("Times", Font.BOLD, 12);
		
		JPanel[] pRoom = new JPanel[NUMBER];
		JPanel[] pPrice = new JPanel[NUMBER];
		JPanel[] pReser = new JPanel[NUMBER];
		
		AbstractBorder[] border = new AbstractBorder[NUMBER];
		
		JPanel[] pText = new JPanel[NUMBER];
		JPanel[] pLine = new JPanel[NUMBER];
		
		main = new JLabel("객실예약");
		Font fontText = new Font("Times", Font.BOLD, 25);
		main.setFont(fontText);
		memberInfo = new RoundedButton("회원정보");
		chat = new RoundedButton("관리자문의");
		
		memberInfo.setBackground(Color.PINK);
//		memberInfo.setForeground(Color.WHITE);
		chat.setBackground(Color.PINK);
//		chat.setForeground(Color.WHITE);
		
		JPanel pL = new JPanel(new FlowLayout(FlowLayout.CENTER));
		pL.add(main);
		
		JPanel pBtn1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pBtn1.add(memberInfo);
		
		JPanel pBtn2 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		pBtn2.add(chat);
		
		JPanel pDate = new JPanel(new FlowLayout(FlowLayout.CENTER));
		pDate.add(yearCB);
		pDate.add(monthCB);
		pDate.add(dayCB);
		
		JPanel pBtnTotal = new JPanel(new GridLayout(1, 2, 5, 5));
		pBtnTotal.add(pBtn1);
		pBtnTotal.add(pL);
		pBtnTotal.add(pBtn2);
		
		JPanel pBtnDate = new JPanel(new GridLayout(2, 1, 5, 5));
		pBtnDate.add(pBtnTotal);
		pBtnDate.add(pDate);
		
		JPanel pTotal = new JPanel(new GridLayout(9, 1, 5, 5));
		
		priceL[0] = new JLabel("40000");
		priceL[1] = new JLabel("40000");
		priceL[2] = new JLabel("50000");
		priceL[3] = new JLabel("50000");
		priceL[4] = new JLabel("60000");
		priceL[5] = new JLabel("60000");
		priceL[6] = new JLabel("70000");
		priceL[7] = new JLabel("70000");
		
		for(int i = 0; i < NUMBER; i++)
		{
			image[i] = new ImageIcon("img/" + (i + 1) + ".png");
			
			imgBtn[i] = new JButton(image[i]);
			imgBtn[i].setBorderPainted(false); // 외곽선 없애기
			imgBtn[i].setContentAreaFilled(false); // 내용영역 채우기 안함
			imgBtn[i].setFocusPainted(false); // 버튼 눌렀을때 생기는 테두리 사용안함
			imgBtn[i].setOpaque(false); // 투명하게
			
			room_NumL[i] = new JLabel((i + 1) + "호실");
			room_NumL[i].setFont(font);
			pRoom[i] = new JPanel(new FlowLayout(FlowLayout.CENTER));
			pRoom[i].add(room_NumL[i]);
			
			priceL[i].setFont(font);
			pPrice[i] = new JPanel(new FlowLayout(FlowLayout.CENTER));
			pPrice[i].add(priceL[i]);
			
			reservationL[i] = new JLabel("예약가능");
			reservationL[i].setFont(font);
			pReser[i] = new JPanel(new FlowLayout(FlowLayout.CENTER));
			pReser[i].add(reservationL[i]);
			
			pText[i] = new JPanel(new GridLayout(3, 1, 5, 5));
			pText[i].add(pRoom[i]);
			pText[i].add(pPrice[i]);
			pText[i].add(pReser[i]);
			
			border[i] = new SoftBevelBorder(SoftBevelBorder.RAISED);
			pLine[i] = new JPanel(new GridLayout(1, 2, 5, 5));
			pLine[i].setBorder(border[i]);
			
			pLine[i].add(imgBtn[i]);
			pLine[i].add(pText[i]);
		}
		ArrayList<ReservationDTO> dayList = new ArrayList<ReservationDTO>();
		ReservationDAO dao = new ReservationDAO();
		SimpleDateFormat todayFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date time = new Date();
		
		String todayTime = todayFormat.format(time);
		
		for(int i = 0; i < NUMBER; i++)
		{
			dayList = dao.daycheak(room_NumL[i].getText());
			
			for(ReservationDTO dto : dayList)
			{
				SimpleDateFormat input2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				
				Date today = null;
				Date start = null;
				Date end = null;
				
				try {
					today = input2.parse(todayTime);
					start = input2.parse(dto.getStartday());
					end = input2.parse(dto.getEndday());
				} catch (ParseException e2) {
					e2.printStackTrace();
				}
				int compare1 = today.compareTo(start);
				int compare2 = today.compareTo(end);
				
				if(compare1 == 1 && compare2 == -1 || compare1 == 0 || compare2 == 0)
				{
					imgBtn[i].setEnabled(false);
					room_NumL[i].setEnabled(false);
					priceL[i].setEnabled(false);
					reservationL[i].setText("예약완료");
					
					reservationL[i].setEnabled(false);		
				}
			}
		}
		pTotal.add(pBtnDate);
		
		for(int i = 0; i < NUMBER; i++)
		{
			pTotal.add(pLine[i]);		
		}
		JScrollPane scroll = new JScrollPane(pTotal);
		Container c = getContentPane();
		
		c.add(scroll);

		setBounds(100, 80, 500, 950);
		//setBounds(100, 80, 400, 600);
		setLocationRelativeTo(null);
		setVisible(true);
		
		event();
		service();
		
		this.addWindowListener(new WindowAdapter()
		{
			@Override
			public void windowClosing(WindowEvent e)
			{
				pw.println("exit");
				pw.flush();
			}
		});
	}

	public void event()
	{
		chat.addActionListener(this);
		memberInfo.addActionListener(this);
		yearCB.addActionListener(this);
		monthCB.addActionListener(this);
		dayCB.addActionListener(this);
		
		for(int i = 0; i < NUMBER; i++)
		{
			imgBtn[i].addActionListener(this);
		}
	}
	public int calc(int year, int month)
	{
		Calendar cal = Calendar.getInstance();
		cal.set(year, month - 1, 1);
		int lastD = cal.getActualMaximum(Calendar.DATE);
		return lastD;
	}
	public void yearmonthday()
	{
		Calendar oCalendar = Calendar.getInstance(); // 현재 날짜/시간 등의 각종 정보 얻기
		// 현재 날짜
		int toyear = oCalendar.get(Calendar.YEAR); 
		int tomonth = oCalendar.get(Calendar.MONTH) + 1;
		int today = oCalendar.get(Calendar.DAY_OF_MONTH);
		// 년도
		yearList = new ArrayList<String>();
		monthList = new ArrayList<String>();
		dayList = new ArrayList<String>();
		for (int i = toyear; i <= toyear + 5; i++) {
			yearList.add(String.valueOf(i));
		}
		for (int i = tomonth; i <= 12; i++) {
			monthList.add(String.valueOf(i));
		}
		for (int i = today; i <= calc(toyear, tomonth); i++) {
			dayList.add(String.valueOf(i));
		}
		
		monthStr = monthList.toArray(new String[monthList.size()]);
		dayStr = dayList.toArray(new String[dayList.size()]);
	}
	
	public void service()
	{
		
		try
		{
			socket = new Socket("127.0.0.1", 9200);
			br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
		} 
		catch (UnknownHostException e) 
		{
			System.out.println("서버를 찾을 수 없습니다");
			e.printStackTrace();
			System.exit(0);
		} 
		catch (IOException e) 
		{
			System.out.println("서버 연결이 안되었습니다");
			e.printStackTrace();
			System.exit(0);
		}
		
		Thread t = new Thread(this);
		t.start();
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource() == chat)
		{
			if(temp == 0)
			{
				cc = new ChatClient(loginId, name, tel);
				cc.service();
				dispose();
			}
			else
			{
				cc.setVisible(true);
			}
			temp++;
		}
		else if(e.getSource() == memberInfo)
		{
			new Information(loginId, name, tel);
			dispose();
		}
		else if (e.getSource() == monthCB )
		{
			Calendar oCalendar = Calendar.getInstance();
			int toyear = oCalendar.get(Calendar.YEAR);
			int tomonth = oCalendar.get(Calendar.MONTH) + 1;
			String m = monthCB.getSelectedItem().toString();
			String y = yearCB.getSelectedItem().toString();
			dayList = new ArrayList<String>();
			int last = calc(Integer.parseInt(y),Integer.parseInt(m));
			
			for (int i = 1; i <= last ; i++) 
			{
				dayList.add(String.valueOf(i));
				System.out.println(String.valueOf(i));
			}
			dayStr = dayList.toArray(new String[dayList.size()]);
			
			if(m.equals(String.valueOf(tomonth)) && y.equals(String.valueOf(toyear))) 
			{ //현재 날짜
				yearmonthday();
			}
			dayCB.setModel(new DefaultComboBoxModel<String>(dayStr));
		} 
		else if (e.getSource() == yearCB)
		{ //
			String y = yearCB.getSelectedItem().toString();
			Calendar oCalendar = Calendar.getInstance();
			int toyear = oCalendar.get(Calendar.YEAR);
			monthList = new ArrayList<String>();

			if (toyear != Integer.parseInt(y))
			{//현재년도와 선택년도 다르면
				for (int i = 1; i <= 12; i++) 
				{
					monthList.add(String.valueOf(i));
				}
				monthStr = monthList.toArray(new String[monthList.size()]);
			}
			else
				yearmonthday();
			monthCB.setModel(new DefaultComboBoxModel<String>(monthStr));
		}
		else if(e.getSource() == dayCB)
		{
			for(int i = 0; i < NUMBER; i++)
			{
				imgBtn[i].setEnabled(true);
				room_NumL[i].setEnabled(true);
				priceL[i].setEnabled(true);
				reservationL[i].setText("예약가능");
				reservationL[i].setEnabled(true);	
			}
			int year = Integer.parseInt(yearCB.getSelectedItem().toString());
			int month = Integer.parseInt(monthCB.getSelectedItem().toString());
			int day = Integer.parseInt(dayCB.getSelectedItem().toString());
			
			ArrayList<ReservationDTO> dayList = new ArrayList<ReservationDTO>();
			ReservationDAO dao = new ReservationDAO();
			DateTimeFormatter todayFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			LocalDateTime date = LocalDateTime.of(year, month, day, 00, 00, 00);
			
			String todayTime = todayFormat.format(date);
			
			for(int i = 0; i < NUMBER; i++)
			{
				dayList = dao.daycheak(room_NumL[i].getText());
				
				for(ReservationDTO dto : dayList)
				{
					SimpleDateFormat input2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					
					Date today = null;
					Date start = null;
					Date end = null;
					
					try {
						today = input2.parse(todayTime);
						start = input2.parse(dto.getStartday());
						end = input2.parse(dto.getEndday());
					} 
					catch (ParseException e2)
					{
						e2.printStackTrace();
					}
					int compare1 = today.compareTo(start);
					int compare2 = today.compareTo(end);
					
					if(compare1 == 1 && compare2 == -1 || compare1 == 0)
					{
						imgBtn[i].setEnabled(false);
						room_NumL[i].setEnabled(false);
						priceL[i].setEnabled(false);
						reservationL[i].setText("예약완료");
						reservationL[i].setEnabled(false);		
					}
				}
			}
		}
		
		String room = null;
		for(int i = 0; i < NUMBER; i++)
		{
			if(e.getSource() == imgBtn[i])
			{
				room = room_NumL[i].getText();
				
				pw.println(room);
				pw.flush();
				
				new RoomReservation(loginId, name, tel, room, priceL[i].getText(), yearCB.getSelectedItem().toString(), monthCB.getSelectedItem().toString(), dayCB.getSelectedItem().toString());
				dispose();
			}
		}
	}
	@Override
	public void run()
	{
		String room = null;
		
		while(true)
		{
			try 
			{
				room = br.readLine();

				if(room == null || room.toLowerCase().equals("exit"))
				{
					br.close();
					pw.close();
					socket.close();
					
					System.exit(0);
					break;
				}
				
				for(int i = 0; i < NUMBER; i++)
				{
					if(room_NumL[i].getText().equals(room))
					{
						imgBtn[i].setEnabled(false);
						room_NumL[i].setEnabled(false);
						priceL[i].setEnabled(false);
						reservationL[i].setText("예약완료");
						reservationL[i].setEnabled(false);
					}
				}
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}
	}
}
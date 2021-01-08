package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JTextField;

import model.MemberDTO;
import model.ReservationDAO;
import model.ReservationDTO;

public class RoomReservation extends JFrame implements ActionListener {
	private JLabel startL, endL, hotelL, reserInfoL, nameL, phoneL, people_NumL, carfulL, visitL, payInfoL, reserpayL,
			salepayL, totalpayL, vatL, paywayL, s_yearL, s_monthL, s_dayL, e_yearL, e_monthL, e_dayL;
	private JTextField nameT, telT, reserpayT, salepayT, totalpayT;
	private JRadioButton runR, carR;
	private JComboBox<String> s_yearCB, s_monthCB, s_dayCB, e_yearCB, e_monthCB, e_dayCB;
	private JComboBox<String> paywayCB;
	private RoundedButton payB, cancelB ,cheakB;
	private JSpinner adultS, childS;
	private ArrayList<MemberDTO> mdto;
	private String loginId;

	private ArrayList<String> syeararray;
	private ArrayList<String> smontharray;
	private ArrayList<String> sdayarray;
	private ArrayList<String> eyeararray;
	private ArrayList<String> emontharray;
	private ArrayList<String> edayarray;
	private String[] sdayStr;
	private String[] smonthStr;
	private String[] syearStr;
	private String[] edayStr;
	private String[] emonthStr;
	private String[] eyearStr;
	
	private String id1;
	private String name;
	private String tel;
	private String roomNum;
	private String price;
	private String year;
	private String month;
	private String day;

	public RoomReservation(String id1, String name, String tel, String roomNum, String price, String year, String month, String day) {
		this.id1 = id1;
		this.name = name;
		this.tel = tel;
		this.roomNum = roomNum;
		this.price = price;
		this.year = year;
		this.month = month;
		this.day = day;
		
		startL = new JLabel("예약날자");
		endL = new JLabel("퇴실날자");
		hotelL = new JLabel("비트 호텔");
		reserInfoL = new JLabel("예약자 정보");
		nameL = new JLabel("예약자 이름");
		phoneL = new JLabel("휴대폰 번호");
		people_NumL = new JLabel("숙박 인원");
		carfulL = new JLabel("*만8세 이하 어린이 ");
		visitL = new JLabel("방문 방법");
		payInfoL = new JLabel("결제 정보");
		reserpayL = new JLabel("예약금액");
		totalpayL = new JLabel("총 결제금액");
		vatL = new JLabel("(vat)포함");
		salepayL = new JLabel("할인금액");
		paywayL = new JLabel("결제수단");
		s_yearL = new JLabel("년");
		s_monthL = new JLabel("월");
		s_dayL = new JLabel("일");
		e_yearL = new JLabel("년");
		e_monthL = new JLabel("월");
		e_dayL = new JLabel("일");

		nameT = new JTextField();
		telT = new JTextField();
		reserpayT = new JTextField("0");
		salepayT = new JTextField("0");
		totalpayT = new JTextField("0");

		adultS = new JSpinner();
		childS = new JSpinner();
		runR = new JRadioButton("도보");
		carR = new JRadioButton("차량");
		ButtonGroup group = new ButtonGroup();

		payB = new RoundedButton("결제");
		cancelB = new RoundedButton("취소");
		payB.setBackground(Color.PINK);
		cancelB.setBackground(Color.PINK);
		cheakB = new RoundedButton("확인");
		cheakB.setBackground(Color.PINK);
		// ------------------------------------------------------
		yearmonthday();
		eyearmonthday();
		// -------------------------------------------------------
		paywayCB = new JComboBox<String>();
		s_yearCB = new JComboBox<String>(syeararray.toArray(new String[syeararray.size()]));
		s_monthCB = new JComboBox<String>(smontharray.toArray(new String[smontharray.size()]));
		s_dayCB = new JComboBox<String>(sdayarray.toArray(new String[sdayarray.size()]));
		e_yearCB = new JComboBox<String>(eyeararray.toArray(new String[eyeararray.size()]));
		e_monthCB = new JComboBox<String>(emontharray.toArray(new String[emontharray.size()]));
		e_dayCB = new JComboBox<String>(edayarray.toArray(new String[edayarray.size()]));
		
		setTitle("숙박 예약");
		ImageIcon img = new ImageIcon("img/hotel_logo.png");
	    this.setIconImage(img.getImage());
	    this.setResizable(false);

		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(400, 1000));
		panel.setLayout(null);
		JScrollPane scrollPane = new JScrollPane(panel);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(0, 0, 385, 560);
		JPanel p1 = new JPanel(null);
		p1.setPreferredSize(new Dimension(500, 400));
		p1.add(scrollPane);
		//array값을 배열로 바꿔 모델로 값을 준다 , 모델값이 동적으로 변동
		
		
		startL.setBounds(26, 60, 73, 15);
		panel.add(startL);
		s_yearCB.setBounds(26, 80, 82, 25);
		panel.add(s_yearCB);
		s_yearL.setBounds(110, 80, 30, 25);
		panel.add(s_yearL);
		s_monthCB.setBounds(125, 80, 66, 25);
		panel.add(s_monthCB);
		s_monthL.setBounds(190, 80, 30, 25);
		panel.add(s_monthL);
		s_dayCB.setBounds(206, 80, 66, 25);
		panel.add(s_dayCB);
		s_dayL.setBounds(270, 80, 30, 25);
		panel.add(s_dayL);
		
		endL.setBounds(26, 110, 73, 15);
		panel.add(endL);
		e_yearCB.setBounds(26, 130, 82, 25);
		panel.add(e_yearCB);
		e_yearL.setBounds(110, 130, 30, 25);
		panel.add(e_yearL);
		e_monthCB.setBounds(125, 130, 66, 25);
		panel.add(e_monthCB);
		e_monthL.setBounds(190, 130, 30, 25);
		panel.add(e_monthL);
		e_dayCB.setBounds(206, 130, 66, 25);
		panel.add(e_dayCB);
		e_dayL.setBounds(270, 130, 30, 25);
		panel.add(e_dayL);
		cheakB.setBounds(310, 130, 45, 25);
		panel.add(cheakB);
		

		hotelL.setFont(new Font("굴림", Font.BOLD, 23));
		hotelL.setBounds(145, 20, 112, 40);
		panel.add(hotelL);

		reserInfoL.setFont(new Font("굴림", Font.BOLD, 15));
		reserInfoL.setBounds(26, 170, 94, 21);
		panel.add(reserInfoL);

		nameL.setBounds(26, 206, 73, 15);
		panel.add(nameL);

		nameT.setBounds(26, 233, 116, 21);
		panel.add(nameT);
		nameT.setColumns(10);
		nameT.setText(name);
		nameT.setEditable(false);

		phoneL.setBounds(26, 264, 73, 15);
		panel.add(phoneL);

		telT.setBounds(26, 289, 116, 21);
		panel.add(telT);
		telT.setColumns(10);
		telT.setText(tel);
		telT.setEditable(false);

		people_NumL.setBounds(26, 320, 116, 21);
		panel.add(people_NumL);

		JLabel lblNewLabel = new JLabel("성인");
		lblNewLabel.setBounds(26, 350, 42, 15);
		panel.add(lblNewLabel);

		adultS.setBounds(58, 347, 34, 22);
		panel.add(adultS);

		JLabel lblNewLabel_8 = new JLabel("어린이");
		lblNewLabel_8.setBounds(100, 350, 42, 15);
		panel.add(lblNewLabel_8);

		childS.setBounds(144, 347, 34, 22);
		panel.add(childS);

		carfulL.setFont(new Font("굴림", Font.ITALIC, 10));
		carfulL.setBounds(185, 350, 126, 15);
		panel.add(carfulL);

		visitL.setBounds(26, 389, 57, 15);
		panel.add(visitL);

		runR.setBounds(36, 410, 63, 23);
		panel.add(runR);

		carR.setBounds(110, 410, 63, 23);
		panel.add(carR);
		group.add(runR);
		group.add(carR);

		payInfoL.setFont(new Font("굴림", Font.BOLD, 15));
		payInfoL.setBounds(26, 450, 94, 21);
		panel.add(payInfoL);

		reserpayL.setBounds(26, 481, 57, 15);
		panel.add(reserpayL);

		reserpayT.setBounds(210, 475, 116, 21);
		panel.add(reserpayT);
		reserpayT.setColumns(10);
		reserpayT.setText(price);
		reserpayT.setEditable(false);

		salepayL.setBounds(26, 506, 57, 15);
		panel.add(salepayL);

		salepayT.setBounds(210, 503, 116, 21);
		panel.add(salepayT);
		salepayT.setColumns(10);
		salepayT.setEditable(false);

		totalpayL.setBounds(26, 534, 73, 15);
		panel.add(totalpayL);

		vatL.setFont(new Font("굴림", Font.ITALIC, 11));
		vatL.setBounds(100, 535, 57, 15);
		panel.add(vatL);

		totalpayT.setBounds(210, 531, 116, 21);
		panel.add(totalpayT);
		totalpayT.setColumns(10);
		totalpayT.setEditable(false);

		paywayL.setFont(new Font("굴림", Font.BOLD, 15));
		paywayL.setBounds(26, 594, 73, 21);
		panel.add(paywayL);

		paywayCB.setModel(new DefaultComboBoxModel<String>(new String[] { "현금결제", "간편 계좌 이체", "신용/체크카드", "토스", "네이버페이" }));
		paywayCB.setBounds(26, 625, 285, 21);
		panel.add(paywayCB);

		payB.setFont(new Font("굴림", Font.PLAIN, 14));
		payB.setBounds(26, 700, 130, 23);
		payB.setEnabled(false);
		panel.add(payB);
		cancelB.setFont(new Font("굴림", Font.PLAIN, 14));
		cancelB.setBounds(195, 700, 130, 23);
		panel.add(cancelB);

		JSeparator separator = new JSeparator();
		separator.setBounds(0, 444, 384, 140);
		panel.add(separator);

		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(0, 587, 372, 103);
		panel.add(separator_1);

		setContentPane(p1);
		// ArrayList<MemberDTO> mdto = arrayList;
		 loginId =id1;
		event();
		setBounds(100, 100, 400, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    setLocationRelativeTo(null);
		setVisible(true);
	}

	public void event() {
		payB.addActionListener(this);
		cancelB.addActionListener(this);
		cheakB.addActionListener(this);
		s_monthCB.addActionListener(this);
		s_yearCB.addActionListener(this);
		e_monthCB.addActionListener(this);
		e_yearCB.addActionListener(this);
		e_dayCB.addActionListener(this);
	}
	
	public int calc(int year, int month) {
		Calendar cal = Calendar.getInstance();
		cal.set(year, month - 1, 1);
		int lastD = cal.getActualMaximum(Calendar.DATE);
		return lastD;
	}

	public void yearmonthday() {
		//Calendar oCalendar = Calendar.getInstance(); // 현재 날짜/시간 등의 각종 정보 얻기
		// 현재 날짜
		//int toyear = oCalendar.get(Calendar.YEAR); 
		//int tomonth = oCalendar.get(Calendar.MONTH) + 1;
		//int today = oCalendar.get(Calendar.DAY_OF_MONTH);
		int toyear = Integer.parseInt(year);
		int tomonth = Integer.parseInt(month);
		int today = Integer.parseInt(day);
		// 년도
		syeararray = new ArrayList<String>();
		smontharray = new ArrayList<String>();
		sdayarray = new ArrayList<String>();
		for (int i = toyear; i <= toyear + 5; i++) {
			syeararray.add(String.valueOf(i));
		}
		for (int i = tomonth; i <= 12; i++) {
			smontharray.add(String.valueOf(i));
		}
		for (int i = today; i <= calc(toyear, tomonth); i++) {
			sdayarray.add(String.valueOf(i));
		}
		
		smonthStr = smontharray.toArray(new String[smontharray.size()]);
		sdayStr = sdayarray.toArray(new String[sdayarray.size()]);
	}
	public void eyearmonthday() {
		//Calendar oCalendar = Calendar.getInstance(); // 현재 날짜/시간 등의 각종 정보 얻기
		// 현재 날짜
		//int toyear = oCalendar.get(Calendar.YEAR); 
		//int tomonth = oCalendar.get(Calendar.MONTH) + 1;
		//int today = oCalendar.get(Calendar.DAY_OF_MONTH);
		int toyear = Integer.parseInt(year);
		int tomonth = Integer.parseInt(month);
		int today = Integer.parseInt(day);
		// 년도
		eyeararray = new ArrayList<String>();
		emontharray = new ArrayList<String>();
		edayarray = new ArrayList<String>();
		for (int i = toyear; i <= toyear + 5; i++) {
			eyeararray.add(String.valueOf(i));
		}
		for (int i = tomonth; i <= 12; i++) {
			emontharray.add(String.valueOf(i));
		}
		for (int i = today; i <= calc(toyear, tomonth); i++) {
			edayarray.add(String.valueOf(i));
		}
		
		emonthStr = emontharray.toArray(new String[emontharray.size()]);
		edayStr = edayarray.toArray(new String[edayarray.size()]);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == s_monthCB ) {
			Calendar oCalendar = Calendar.getInstance();
			int toyear = oCalendar.get(Calendar.YEAR);
			int tomonth = oCalendar.get(Calendar.MONTH) + 1;
			String m = s_monthCB.getSelectedItem().toString();
			String y = s_yearCB.getSelectedItem().toString();
			sdayarray = new ArrayList<String>();
			int last = calc(Integer.parseInt(y),Integer.parseInt(m));
			
			for (int i = 1; i <= last ; i++) {
				sdayarray.add(String.valueOf(i));
				System.out.println(String.valueOf(i));
			}
			sdayStr = sdayarray.toArray(new String[sdayarray.size()]);
			if(m.equals(String.valueOf(tomonth)) && y.equals(String.valueOf(toyear))) { //현재 날짜
				yearmonthday();
			}
			s_dayCB.setModel(new DefaultComboBoxModel<String>(sdayStr));
		} else if (e.getSource() == s_yearCB) { //
			String y = s_yearCB.getSelectedItem().toString();
			Calendar oCalendar = Calendar.getInstance();
			int toyear = oCalendar.get(Calendar.YEAR);
			smontharray = new ArrayList<String>();

			if (toyear != Integer.parseInt(y)) {//현재년도와 선택년도 다르면
				for (int i = 1; i <= 12; i++) {
					smontharray.add(String.valueOf(i));
				}
				smonthStr = smontharray.toArray(new String[smontharray.size()]);
				System.out.println(smonthStr.toString());
			}else
				yearmonthday();
			s_monthCB.setModel(new DefaultComboBoxModel<String>(smonthStr));
			
		}else if(e.getSource()==e_monthCB) {
			Calendar oCalendar = Calendar.getInstance();
			int toyear = oCalendar.get(Calendar.YEAR);
			int tomonth = oCalendar.get(Calendar.MONTH) + 1;
			String m = e_monthCB.getSelectedItem().toString();
			String y = e_yearCB.getSelectedItem().toString();
			edayarray = new ArrayList<String>();
			int last = calc(Integer.parseInt(y),Integer.parseInt(m));
			
			for (int i = 1; i <= last ; i++) {
				edayarray.add(String.valueOf(i));
				System.out.println(String.valueOf(i));
			}
			edayStr = edayarray.toArray(new String[edayarray.size()]);
			if(m.equals(String.valueOf(tomonth)) && y.equals(String.valueOf(toyear))) { //현재 날짜
				eyearmonthday();
			}
			e_dayCB.setModel(new DefaultComboBoxModel<String>(edayStr));
			
		}else if(e.getSource()==e_yearCB) {
			String y = e_yearCB.getSelectedItem().toString();
			Calendar oCalendar = Calendar.getInstance();
			int toyear = oCalendar.get(Calendar.YEAR);
			emontharray = new ArrayList<String>();

			if (toyear != Integer.parseInt(y)) {//현재년도와 선택년도 다르면
				for (int i = 1; i <= 12; i++) {
					emontharray.add(String.valueOf(i));
				}
				emonthStr = smontharray.toArray(new String[emontharray.size()]);
				System.out.println(emonthStr.toString());
			}else
				eyearmonthday();
			e_monthCB.setModel(new DefaultComboBoxModel<String>(emonthStr));
			
		

		}else if(e.getSource() == e_dayCB) {
			
			
		}else if(e.getSource() == cheakB) {
			
			String startday = (String)s_yearCB.getSelectedItem()+"/"+(String)s_monthCB.getSelectedItem()
			 +"/"+(String)s_dayCB.getSelectedItem();
			String endday =(String)e_yearCB.getSelectedItem()+"/" +(String)e_monthCB.getSelectedItem()
			+"/"+(String)e_dayCB.getSelectedItem();
			
			SimpleDateFormat input = new SimpleDateFormat("yyyy/MM/dd"); //입력방식
			SimpleDateFormat input2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //입력방식 
			//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");//출력방식
			Date start = null;
			Date end = null;
			try {
				start = input.parse(startday);
				end = input.parse(endday);
				
			} catch (ParseException e2) {
				e2.printStackTrace();
			}
			
			ArrayList<ReservationDTO> list = new ArrayList<ReservationDTO>();
			ReservationDTO dto = null;
			dto = new ReservationDTO();
			dto.setRoomNum(roomNum); //병주형한테 방정보를 받아 DB에 넣는다----------------------------------------
			int sw = 0;
			ReservationDAO dao = ReservationDAO.getInstance();
			list = dao.daycheak(dto);
			System.out.println("-----------------------------");
			System.out.println(list);
			
			if(list.isEmpty()) { //선택한 날의 예약이 없을때
				JOptionPane.showMessageDialog(this, "예약이 가능한 날짜입니다.");
				payB.setEnabled(true);
			}else {
			for (ReservationDTO cheakdto : list) {
				String sd = cheakdto.getStartday();
				String ed = cheakdto.getEndday();
				
				try {
					
					Date dbstart = input2.parse(sd);
					Date dbend = input2.parse(ed);
					
					int ck = end.compareTo(dbstart);
					int ck3 =dbend.compareTo(end);
					int ck2 = start.compareTo(dbstart);
					int ck4 = dbend.compareTo(start);
					
					if((ck== 1 && ck3 ==1) || (ck2 == 1 && ck4 == 1) || ck2 == 0 || ck3 == 0) {
						JOptionPane.showMessageDialog(this, "이미 예약된 날짜입니다.");
					}else {
						JOptionPane.showMessageDialog(this, "예약이 가능한 날짜입니다.");
						payB.setEnabled(true);
					}
					
				} catch (ParseException e1) { 
					e1.printStackTrace();
				}
				
				
			}
			}
			//숙박기간 금액 산정
		     try{ 
		         SimpleDateFormat format = new SimpleDateFormat("yyyy/mm/dd");
		         java.util.Date FirstDate = format.parse(startday);
		         java.util.Date SecondDate = format.parse(endday);
		         long calDate = FirstDate.getTime() - SecondDate.getTime(); 
		         
		         long calDateDays = calDate / ( 24*60*60*1000); 
		         calDateDays = Math.abs(calDateDays)*Integer.parseInt(price); 
		         
		         totalpayT.setText(Long.toString(calDateDays)+"원");
		         }
		         catch(ParseException e1)
		         {
		        	 e1.printStackTrace();
		         }
			
		}else if(e.getSource() == payB) {
			String id = loginId;
			String startday = (String)s_yearCB.getSelectedItem()+"/"+ (String)s_monthCB.getSelectedItem()
			+"/" +(String)s_dayCB.getSelectedItem();
			String endday =(String)e_yearCB.getSelectedItem() +"/" +(String)e_monthCB.getSelectedItem()
			+"/"+(String)e_dayCB.getSelectedItem();
			
			String name = nameT.getText();
			String phone = telT.getText();
			int comeway=0;
			if(runR.isSelected()) 
				comeway = 0;
            else if(carR.isSelected())
            	comeway = 1;
			Object adult =adultS.getValue();
			Object child =childS.getValue();
			int pay = Integer.parseInt(reserpayT.getText());
			int salepay=Integer.parseInt(salepayT.getText());
			int totalpay = pay;
			String payway =(String)paywayCB.getSelectedItem();
			
			System.out.println(id);
			System.out.println(startday);
			System.out.println(endday);
			System.out.println(roomNum);
			System.out.println(comeway);
			System.out.println(pay);
			System.out.println(adult);
			System.out.println(child);
			System.out.println(pay);
			System.out.println(salepay);
			System.out.println(totalpay);
			System.out.println(payway);
			
			//DTO객체에 저장 
			ReservationDTO dto = null;
			dto = new ReservationDTO();
			dto.setId(id);
			dto.setStartday(startday.toString());
			dto.setEndday(endday.toString());
//			dto.setName(name);
//			dto.setPhone(phone);
			dto.setRoomNum(roomNum);
			dto.setAdult(adult.toString());
			dto.setChild(child.toString());
			dto.setComeway(comeway);
			dto.setPay(pay);
			dto.setSalepay(salepay);
			dto.setTotalpay(totalpay);
			dto.setPayway(payway);
			System.out.println(startday);
			//DB 
			ReservationDAO dao = ReservationDAO.getInstance();
			
			int su = dao.insertreservation(dto);
			
			//System.out.println(term);
			//개인정보분석에 응답텍스트 출력 , 전체목록에 대표로 이름만 셋텍스트 
			if(su==1){
				JOptionPane.showMessageDialog(this, "예약이 완료 되었습니다.");
				new RoomChoice(loginId, this.name, tel);
				dispose();
			}else {
				JOptionPane.showMessageDialog(this, "예약이 실패 되었습니다.");
			}
		}
		else if(e.getSource() == cancelB)
		{
			new RoomChoice(loginId, name, tel);
			dispose();
		}
	}

//	public static void main(String[] args) {
//		new RoomReservation();
//	}

}

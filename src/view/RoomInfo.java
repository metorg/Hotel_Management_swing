package view;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import model.ReservationDAO;
import model.ReservationDTO;

public class RoomInfo extends JFrame implements ActionListener, ListSelectionListener {
	private RoundedButton ok;
	private JList<ReservationDTO> list;
	private DefaultListModel<ReservationDTO> model;
	private JLabel numL, num1L, memberInfo, memberList, roomNumber, idL, startDayL, endDayL, adultL, childL, payL,
			salepayL, totalpayL, paywayL;
	private JTextField idT, startDayT, endDayT, roomNumT, adultT, childT, comewayT, payT, salepayT, totalpayT, paywayT;

	public RoomInfo(ReservationDTO dto) {
		ok = new RoundedButton("확인");
		ok.setBackground(Color.PINK);
		list = new JList<ReservationDTO>(new DefaultListModel<ReservationDTO>());
		model = (DefaultListModel<ReservationDTO>) list.getModel();
		setResizable(false);
		memberInfo = new JLabel("회원 정보");
		memberList = new JLabel("예약 리스트");

		idL = new JLabel("아이디");
		startDayL = new JLabel("체크인");
		endDayL = new JLabel("체크아웃");
		adultL = new JLabel("성인");
		childL = new JLabel("아동");
		payL = new JLabel("금액");
		salepayL = new JLabel("할인");
		totalpayL = new JLabel("총금액");
		paywayL = new JLabel("지불방법");
		numL = new JLabel("명");
		num1L = new JLabel("명");

		idT = new JTextField();
		startDayT = new JTextField();
		endDayT = new JTextField();
		roomNumT = new JTextField();
		adultT = new JTextField();
		childT = new JTextField();
		comewayT = new JTextField();
		payT = new JTextField();
		salepayT = new JTextField();
		totalpayT = new JTextField();
		paywayT = new JTextField();

		String roomNum = dto.getRoomNum();
		roomNumber = new JLabel(roomNum);
		JScrollPane listScroll = new JScrollPane(list);
		listScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		setLayout(null);

		roomNumber.setBounds(10, 10, 80, 10);
		memberInfo.setBounds(120, 30, 150, 10);
		memberList.setBounds(340, 30, 150, 10);

		idL.setBounds(30, 65, 80, 10);
		idT.setBounds(100, 60, 120, 20);
		idT.setEditable(false);
		startDayL.setBounds(30, 105, 80, 10);
		startDayT.setBounds(100, 100, 120, 20);
		startDayT.setEditable(false);
		endDayL.setBounds(30, 140, 80, 20);
		endDayT.setBounds(100, 142, 120, 20);
		endDayT.setEditable(false);
		adultL.setBounds(30, 178, 80, 20);
		adultT.setBounds(60, 180, 30, 20);
		adultT.setEditable(false);
		numL.setBounds(95, 178, 30, 20);
		childL.setBounds(130, 178, 30, 20);
		childT.setBounds(160, 180, 30, 20);
		childT.setEditable(false);
		num1L.setBounds(195, 178, 30, 20);
		payL.setBounds(30, 218, 30, 20);
		payT.setBounds(100, 220, 120, 20);
		payT.setEditable(false);
		salepayL.setBounds(30, 258, 30, 20);
		salepayT.setBounds(100, 260, 120, 20);
		salepayT.setEditable(false);
		totalpayL.setBounds(30, 298, 80, 20);
		totalpayT.setBounds(100, 300, 120, 20);
		totalpayT.setEditable(false);
		paywayL.setBounds(30, 338, 80, 20);
		paywayT.setBounds(100, 340, 120, 20);
		paywayT.setEditable(false);

		ok.setBounds(200, 400, 100, 30);
		listScroll.setBounds(300, 60, 150, 300);

		Container con = getContentPane();
		con.add(ok);
		con.add(listScroll);
		con.add(roomNumber);
		con.add(memberInfo);
		con.add(memberList);
		con.add(idL);
		con.add(startDayL);
		con.add(endDayL);
		con.add(adultL);
		con.add(childL);
		con.add(payL);
		con.add(salepayL);
		con.add(totalpayL);
		con.add(paywayL);
		con.add(numL);
		con.add(num1L);
		con.add(idT);
		con.add(startDayT);
		con.add(endDayT);
		con.add(roomNumT);
		con.add(adultT);
		con.add(childT);
		con.add(comewayT);
		con.add(payT);
		con.add(salepayT);
		con.add(totalpayT);
		con.add(paywayT);

		setBounds(100, 50, 500, 500);
		setVisible(true);
		setDefaultCloseOperation(HIDE_ON_CLOSE);

		ReservationDAO dao = ReservationDAO.getInstance();
		ArrayList<ReservationDTO> arrayList = dao.getRoomData(dto);

		for (ReservationDTO data : arrayList) {
			model.addElement(data);
		}

		list.addListSelectionListener(this);
		ok.addActionListener(this);
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		ReservationDTO dto = list.getSelectedValue();
		String sd = dto.getStartday();
		String ed = dto.getEndday();
		SimpleDateFormat input2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");// 출력방식

		try {
			Date dbstart = input2.parse(sd);
			Date dbend = input2.parse(ed);
			String id = dto.getId();
			String startDay = sdf.format(dbstart);
			String endDay = sdf.format(dbend);

			// String endDay = dto.getEndday();
			String roomnum = dto.getRoomNum();
			String adult = dto.getAdult();
			String child = dto.getChild();
			int pay = dto.getPay();
			int salepay = dto.getSalepay();
			int totalpay = dto.getTotalpay();
			String payway = dto.getPayway();
			idT.setText(id);
			startDayT.setText(startDay);
			endDayT.setText(endDay);
			roomNumT.setText(roomnum);
			adultT.setText(adult);
			childT.setText(child);
			payT.setText(pay + "");
			salepayT.setText(salepay + "");
			totalpayT.setText(totalpay + "");
			paywayT.setText(payway);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == ok) {
			this.setVisible(false);
		}

	}
}
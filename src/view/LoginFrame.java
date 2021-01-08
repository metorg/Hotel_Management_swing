package view;

import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import controller.LoginController;
import model.MemberDTO;

public class LoginFrame extends JFrame  {
	
	//ImageIcon i = new ImageIcon("img/sejong_.png");
	Image im = Toolkit.getDefaultToolkit().createImage("img/sejong_.png");//배경

	//Image im = i.getImage();
	public RoundedButton loginB, memberB;
	public RoundedButton searchID, searchPW;
	public JLabel loginL, PasswordL;
	public JTextField loginT;
	public JPasswordField passwordT;
	public ArrayList<MemberDTO> arrayList;
	LoginController loginController;

	public LoginFrame() {
		
		loginController = new LoginController(this);
		
		ImageIcon img = new ImageIcon("img/hotel_logo.png");
		this.setIconImage(img.getImage());
		

		ImageIcon icon = new ImageIcon("img/pink.png");
		Image im = icon.getImage();
		Image im2 = im.getScaledInstance(100, 100, Image.SCALE_DEFAULT);
		ImageIcon icon2 = new ImageIcon(im2);
		JLabel img3 = new JLabel(icon2);
	

		// 컴퍼넌트 생성
		loginL = new JLabel("ID");
		PasswordL = new JLabel("PW");
		loginB = new RoundedButton("로그인");
		loginB.setBackground(Color.PINK);
		memberB = new RoundedButton("회원가입");
		memberB.setBackground(Color.PINK);
		searchID = new RoundedButton("아이디 찾기");
		searchID.setBackground(Color.PINK);
		searchPW = new RoundedButton("비밀번호 찾기");
		searchPW.setBackground(Color.PINK);
		loginT = new JTextField(10);
		passwordT = new JPasswordField(10);

		this.setTitle("2조_세종_호텔 ");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		MyPanel panel = new MyPanel();
		panel.setLayout(null);
		loginL.setBounds(170, 305, 20, 10);
		loginB.setBounds(120, 450, 115, 30);
		memberB.setBounds(245, 450, 115, 30);
		searchID.setBounds(120, 490, 115, 30);
		searchPW.setBounds(245, 490, 115, 30);
		loginL.setBounds(170, 305, 20, 10);
		loginT.setBounds(200, 300, 100, 20);
		PasswordL.setBounds(170, 335, 20, 10);
		passwordT.setBounds(200, 330, 100, 20);
		img3.setBounds(200, 150,100,100);

		Container c = getContentPane();
		c.add(panel);
		panel.add(loginL);
		panel.add(loginB);
		panel.add(memberB);
		panel.add(searchID);
		panel.add(searchPW);
		panel.add(loginL);
		panel.add(PasswordL);
		panel.add(loginT);
		panel.add(passwordT);
		
		panel.add(img3); // 로고
		setResizable(false);
		setSize(500, 800);
		setLocationRelativeTo(null);
		setVisible(true);
		
		loginB.addActionListener(loginController);
		memberB.addActionListener(loginController);
		searchID.addActionListener(loginController);
		searchPW.addActionListener(loginController);
		loginT.addActionListener(loginController);
		passwordT.addActionListener(loginController);
		
	}

	class MyPanel extends JPanel {
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(im, 0, 0, getWidth(), getHeight(), this);

		}
	}

	
}
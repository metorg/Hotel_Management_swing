package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import model.MemberDAO;
import model.MemberDTO;
import view.LoginFrame;
import view.MemberJoin;
import view.RoomChoice;
import view.RoundedButton;

public class LoginController implements ActionListener{
	private ArrayList<MemberDTO> arrayList;
	LoginFrame loginFrame;
	
	public LoginController(LoginFrame loginFrame){
		this.loginFrame = loginFrame;
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == loginFrame.loginB || e.getSource() == loginFrame.passwordT || e.getSource() == loginFrame.loginT) {
			String id = loginFrame.loginT.getText();
			String pw = new String(loginFrame.passwordT.getPassword());

			MemberDTO dto = new MemberDTO();

			dto.setId(id);
			dto.setPw(pw);

			MemberDAO dao = MemberDAO.getInstance();
			MemberDTO loginData = dao.login(dto);

			String idC = loginData.getId();
			String pwC = loginData.getPw();

			if (id.length() == 0 || pw.length() == 0) {
				JOptionPane.showMessageDialog(null, "아이디와 비밀번호를 입력해주세요");
			} else if (id.equals(idC) && pw.equals(pwC)) {
				JOptionPane.showMessageDialog(null, "로그인");
				dto.setId(idC);
				ArrayList<MemberDTO> arrayList = dao.loginInfo(dto);

				for (MemberDTO data : arrayList) {
					System.out.println(data.getId());
					System.out.println(data.getPw());
					System.out.println(data.getName());
					System.out.println(data.getBirth());
					System.out.println(data.getEmail());
					System.out.println(data.getTel());
					System.out.println(data.getAddress1());

					//new RoomChoice(idC, data.getName(), data.getTel());
					RoomChoice aa = new RoomChoice(idC, data.getName(), data.getTel());
				}

				// setVisible(false);
				loginFrame.dispose();

			} else {
				JOptionPane.showMessageDialog(null, "아이디나 비밀번호가 틀렸습니다.");
			}

		} else if (e.getSource() == loginFrame.memberB) {
			MemberJoin join = new MemberJoin();
		} else if (e.getSource() == loginFrame.searchID) {
			String email = JOptionPane.showInputDialog(null, "이메일을 입력하세요", null, JOptionPane.PLAIN_MESSAGE);
			if (email != null) {
				String tel = JOptionPane.showInputDialog(null, "휴대폰 번호를 입력하세요", null, JOptionPane.PLAIN_MESSAGE);
				MemberDTO dto = new MemberDTO();
				dto.setEmail(email);
				dto.setTel(tel);

				MemberDAO dao = MemberDAO.getInstance();
				String id = null;
				id = dao.getID(dto);
				if (tel == null) {
					JOptionPane.showMessageDialog(null, "정보가 입력 되지 않았습니다.");
				} else if (id != null) {
					JOptionPane.showMessageDialog(null, "아이디 : " + id);
				} else if (id == null) {
					JOptionPane.showMessageDialog(null, "정보가 일치하지 않습니다.");
				}
			} else {
				JOptionPane.showMessageDialog(null, "정보가 입력 되지 않았습니다.");
			}

		} else if (e.getSource() == loginFrame.searchPW) {
			String id = JOptionPane.showInputDialog(null, "아이디를 입력하세요", null, JOptionPane.PLAIN_MESSAGE);
			if (id != null) {
				String email = JOptionPane.showInputDialog(null, "이메일을 입력하세요", null, JOptionPane.PLAIN_MESSAGE);
				if (email != null) {
					String tel = JOptionPane.showInputDialog(null, "핸드폰 번호를 입력하세요", null, JOptionPane.PLAIN_MESSAGE);

					MemberDTO dto = new MemberDTO();
					dto.setId(id);
					dto.setEmail(email);
					dto.setTel(tel);

					MemberDAO dao = MemberDAO.getInstance();
					String pw = null;
					pw = dao.getPW(dto);
					if (tel == null) {
						JOptionPane.showMessageDialog(null, "정보가 입력 되지 않았습니다.");
					} else if (pw != null) {
						JOptionPane.showMessageDialog(null, "패스워드 : " + pw);
					} else if (pw == null) {
						JOptionPane.showMessageDialog(null, "정보가 일치하지 않습니다.");
					}
				} else {
					JOptionPane.showMessageDialog(null, "정보가 입력 되지 않았습니다.");
				}
			} else {
				JOptionPane.showMessageDialog(null, "정보가 입력 되지 않았습니다.");
			}
		}
	}
	
	

}

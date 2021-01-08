package model;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.MemberDTO;

public class ReservationDAO {
	private static ReservationDAO instance;
	
	private String driver = "com.mysql.jdbc.Driver"; 
	private String jdbcUrl = "jdbc:mysql://54.180.105.15/javadb?serverTimezone=UTC";
	private String user = "****";
	private String password = "****"; 
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	public ReservationDAO() {
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	//DB singleton
	public static ReservationDAO getInstance() {
		if(instance == null){
			synchronized (ReservationDAO.class) {
				instance = new ReservationDAO();
			}
		}
		return instance;
	}
	
	public void getConnection() {
		try {
			conn = DriverManager.getConnection(jdbcUrl, user, password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	public int insertreservation(ReservationDTO dto) {
		int su = 0;
		getConnection();
		String sql = "insert into reservation values(?,?,?,?,?,?,?,?,?,?,?)";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getId());
			pstmt.setString(2, dto.getStartday());
			pstmt.setString(3, dto.getEndday());
			pstmt.setString(4, dto.getRoomNum());
			pstmt.setString(5, dto.getAdult());
			pstmt.setString(6, dto.getChild());
			pstmt.setInt(7, dto.getComeway());
			pstmt.setInt(8, dto.getPay());
			pstmt.setInt(9, dto.getSalepay());
			pstmt.setInt(10, dto.getTotalpay());
			pstmt.setString(11, dto.getPayway());
			
			su = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return su;
	}
	
	public int moneycheak(ReservationDTO dto) {
		int term = 0;
		getConnection();
		String sql = "select endday-startday as term from reservation where id = ?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getId());
			rs = pstmt.executeQuery();
			while (rs.next()) {
				term = rs.getInt("term");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return term;
	}
	
	public ArrayList<ReservationDTO> daycheak(ReservationDTO dto) {
		ArrayList<ReservationDTO> daylist = new ArrayList<ReservationDTO>();
		getConnection();
		String sql = "select startday,endday from reservation where roomnum = ? ";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getRoomNum());
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				ReservationDTO reservationdto = new ReservationDTO();
				reservationdto.setStartday(rs.getString("startday"));
				reservationdto.setEndday(rs.getString("endday"));
				daylist.add(reservationdto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return daylist;
	}
	public ArrayList<ReservationDTO> daycheak(String roomNum) {
		ArrayList<ReservationDTO> daylist = new ArrayList<ReservationDTO>();
		getConnection();
		String sql = "select startday,endday from reservation where roomnum = ? ";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, roomNum);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				ReservationDTO reservationdto = new ReservationDTO();
				reservationdto.setStartday(rs.getString("startday"));
				reservationdto.setEndday(rs.getString("endday"));
				daylist.add(reservationdto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return daylist;
	}
		
	 public ArrayList<ReservationDTO> getRoomData(ReservationDTO dto2) {
	      ArrayList<ReservationDTO> arrayList = new ArrayList<ReservationDTO>();
	      System.out.println(dto2.getRoomNum());
	      getConnection();
	      String sql = "select * from reservation where roomnum = ?";
	      try {
	         pstmt = conn.prepareStatement(sql);
	         pstmt.setString(1, dto2.getRoomNum());
	         rs = pstmt.executeQuery();
	         while(rs.next()) {
	            ReservationDTO dto3 = new ReservationDTO();
	            dto3.setId(rs.getString("f_id"));
	            dto3.setStartday(rs.getString("startday"));
	            dto3.setEndday(rs.getString("endday"));
	            dto3.setAdult(rs.getString("adult"));
	            dto3.setChild(rs.getString("child"));
	            dto3.setPay(Integer.parseInt(rs.getString("pay")));
	            dto3.setSalepay(Integer.parseInt(rs.getString("salepay")));
	            dto3.setTotalpay(Integer.parseInt(rs.getString("totalpay")));
	            dto3.setPayway(rs.getString("payway"));
	            arrayList.add(dto3);
	         }
	      } catch (SQLException e) {
	         // TODO Auto-generated catch block
	         e.printStackTrace();
	      }finally {
	         try {
	            if(rs != null) rs.close(); 
	            if(pstmt != null) pstmt.close();
	            if(conn != null) conn.close();
	         } catch (SQLException e) {
	            e.printStackTrace();
	         }
	      }
	      return arrayList;
	   }

	
	
}

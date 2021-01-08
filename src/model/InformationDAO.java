package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class InformationDAO {
   private static InformationDAO instance;
   private String driver = "com.mysql.jdbc.Driver"; 
	private String jdbcUrl = "jdbc:mysql://54.180.105.15/javadb?serverTimezone=UTC";
	private String user = "****";
	private String password = "****"; 

   private Connection conn;
   private PreparedStatement pstmt;
   private ResultSet rs;

   public static InformationDAO getInstance() {
      if (instance == null) {
         synchronized (InformationDAO.class) {
            instance = new InformationDAO();
         }
      } // if
      return instance;
   }// getInstance() -- Singleton

   public InformationDAO() {
      try {
         Class.forName(driver);
      } catch (ClassNotFoundException e) {
         e.printStackTrace();
      }
   }// Constructor

   public void getConnection() {
      try {
         conn = DriverManager.getConnection(jdbcUrl, user, password);
      } catch (SQLException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
   }// getConnection()

   // 예약정보 가져오기
   public ReservationDTO selectReservationData(String loginId) {
      int su = 0;
      String sql = "select * from reservation where f_id=?";
      this.getConnection();
      ReservationDTO rdto = null;
      try {
         pstmt = conn.prepareStatement(sql);
         pstmt.setString(1, loginId);
         rs = pstmt.executeQuery();
         while (rs.next()) {
            rdto = new ReservationDTO();
            rdto.setStartday(rs.getString("STARTDAY"));
            rdto.setEndday(rs.getString("ENDDAY"));
            rdto.setRoomNum(rs.getString("ROOMNUM"));
            rdto.setAdult(rs.getString("ADULT"));
            rdto.setChild(rs.getString("CHILD"));
            rdto.setPayway(rs.getString("PAYWAY"));
            rdto.setTotalpay(rs.getInt("TOTALPAY"));

         } // while
      } catch (SQLException e) {
         e.printStackTrace();
      } finally {
         try {
            if (pstmt != null) {
               pstmt.close();
            }
            if (conn != null) {
               conn.close();
            }
         } catch (SQLException e) {
            e.printStackTrace();
         }
      }
      return rdto;
   }// selectReservationData()

   // 예약데이터 지우기
   public void deleteReservationData(String loginId) {
      int su = 0;
      getConnection();
      String sql2 = "delete reservation where f_id=?";
      try {
         pstmt = conn.prepareStatement(sql2);
         pstmt.setString(1, loginId);

         su = pstmt.executeUpdate();
      } catch (SQLException e) {
         e.printStackTrace();
      }

      finally {// 에러가 있건 없건 무조건 수행해라
         try {
            if (pstmt != null) {
               pstmt.close();
            }
            if (conn != null) {
               conn.close();
            }
         } catch (SQLException e) {
            e.printStackTrace();
         }
      } // finally
      return;
   }// deleteReservationData()

   // 회원정보 가져오기
   public MemberDTO selectMemberData(String loginId) {
      String sql = "select * from member where ID=?";
      this.getConnection();
      MemberDTO dto = null;
      try {
         pstmt = conn.prepareStatement(sql);
         pstmt.setString(1, loginId);
         rs = pstmt.executeQuery();

         while (rs.next()) {
            dto = new MemberDTO();
            dto.setId(rs.getString("ID"));
            dto.setPw(rs.getString("PW"));
            dto.setPwCheck(rs.getString("PWCHECK"));
            dto.setName(rs.getString("NAME"));
            dto.setBirth(rs.getString("BIRTH"));
            dto.setEmail(rs.getString("EMAIL"));
            dto.setTel(rs.getString("TEL"));
            dto.setAddress1(rs.getString("ADDRESS1"));
         } // while
      } catch (SQLException e) {
         e.printStackTrace();
      } finally {// 에러가 있건 없건 무조건 수행해라
         try {
            if (pstmt != null) {
               pstmt.close();
            }
            if (conn != null) {
               conn.close();
            }
         } catch (SQLException e) {
            e.printStackTrace();
         }
      } // finally
      return dto;
   }

   // 회원정보 수정 완료하기
   public int updateMember(MemberDTO dto, String loginId) {
      int su = 0;
      this.getConnection();
      String sql = "update member set PW=?, PWCHECK=?, NAME=?, BIRTH=?, EMAIL=?, " + "TEL=?, ADDRESS1=? where ID = ?";

      try {
         pstmt = conn.prepareStatement(sql);
         pstmt.setString(1, dto.getPw());
         pstmt.setString(2, dto.getPwCheck());
         pstmt.setString(3, dto.getName());
         pstmt.setString(4, dto.getBirth());
         pstmt.setString(5, dto.getEmail());
         pstmt.setString(6, dto.getTel());
         pstmt.setString(7, dto.getAddress1());
         pstmt.setString(8, loginId);

         su = pstmt.executeUpdate();// 실행
      } catch (SQLException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      } finally {// 에러가 있건 없건 무조건 수행해라
         try {
            if (pstmt != null) {
               pstmt.close();
            }
            if (conn != null) {
               conn.close();
            }
         } catch (SQLException e) {
            e.printStackTrace();
         }
      } // finally

      return su;
   }// select selectMemberData()

   public void deleteAccount(String loginId) {
      int su = 0;
      getConnection();
      String sql = "delete from member where ID=?";
      try {
         pstmt = conn.prepareStatement(sql);
         pstmt.setString(1, loginId);

         su = pstmt.executeUpdate();
      } catch (SQLException e) {
         e.printStackTrace();
      }
      String sql2 = "delete from reservation where f_id=?";
      try {
         pstmt = conn.prepareStatement(sql2);
         pstmt.setString(1, loginId);

         su = pstmt.executeUpdate();
      } catch (SQLException e) {
         e.printStackTrace();
      }

      finally {// 에러가 있건 없건 무조건 수행해라
         try {
            if (pstmt != null) {
               pstmt.close();
            }
            if (conn != null) {
               conn.close();
            }
         } catch (SQLException e) {
            e.printStackTrace();
         }
      } // finally //이렇게 conn 종료하지 않으면 메모리 과부하로 크래시
      return;

   }// deleteAccount()

//   public void readReservationRec(String loginId) {// 캘린더에 예약 날짜 뿌리기
//      int su = 0;
//      String sql = "select * from reservation where f_id=?";
//      this.getConnection();
//      ReservationDTO rdto = null;
//      try {
//         pstmt = conn.prepareStatement(sql);
//         pstmt.setString(1, loginId);
//         rs = pstmt.executeQuery();
//         while (rs.next()) {
//            rdto = new ReservationDTO();
//            rdto.setStartday(rs.getString("STARTDAY"));
//            rdto.setEndday(rs.getString("ENDDAY"));
//            rdto.setRoomNum(rs.getString("ROOMNUM"));
//            rdto.setAdult(rs.getString("ADULT"));
//            rdto.setChild(rs.getString("CHILD"));
//            rdto.setPayway(rs.getString("PAYWAY"));
//            rdto.setTotalpay(rs.getInt("TOTALPAY"));
//
//         } // while
//      } catch (SQLException e) {
//         e.printStackTrace();
//      } finally {
//         try {
//            if (pstmt != null) {
//               pstmt.close();
//            }
//            if (conn != null) {
//               conn.close();
//            }
//         } catch (SQLException e) {
//            e.printStackTrace();
//         }
//      }
//
//   }// readReservationRec()

   public ArrayList<ReservationDTO> sortReservationByRoomNum(String roomNum) {
      int su = 0;
      ArrayList<ReservationDTO> arrayList = new ArrayList<ReservationDTO>();
      String sql = "select startday, endday from reservation where roomnum =?";
      this.getConnection();
      ReservationDTO rdto = null;
      try {
         pstmt = conn.prepareStatement(sql);
         pstmt.setString(1, roomNum);
         rs = pstmt.executeQuery();
         while (rs.next()) {
            rdto = new ReservationDTO();
            rdto.setStartday(rs.getString("startday"));
            rdto.setEndday(rs.getString("endday"));
            // rdto.setRoomNum(rs.getString("ROOMNUM"));

            arrayList.add(rdto);
         } // while
      } catch (SQLException e) {
         e.printStackTrace();
      } finally {
         try {
            if (pstmt != null) {
               pstmt.close();
            }
            if (conn != null) {
               conn.close();
            }
         } catch (SQLException e) {
            e.printStackTrace();
         }
      }
      return arrayList;
   }// sortReservationByRoomNum
   
   
   public void deleteReservationData(String loginId, String startDay) {
	      int su = 0;
	      getConnection();
	      String sql2 = "delete from reservation where f_id=? and startday = ?";
	      try {
	         pstmt = conn.prepareStatement(sql2);
	         pstmt.setString(1, loginId);
	         pstmt.setString(2, startDay);

	         su = pstmt.executeUpdate();
	      } catch (SQLException e) {
	         e.printStackTrace();
	      }

	      finally {// 에러가 있건 없건 무조건 수행해라
	         try {
	            if (pstmt != null) {
	               pstmt.close();
	            }
	            if (conn != null) {
	               conn.close();
	            }
	         } catch (SQLException e) {
	            e.printStackTrace();
	         }
	      } // finally
	      return;
	   }// deleteReservationData()
   
   
   
}
package model;

public class ReservationDTO {
	private String id;
	private String startday;
	private String endday;
	//private String name;
	//private String phone;
	private String roomNum;
	private String adult;
	private String child;
	private int comeway;
	private int pay; //방의 가격
	private int salepay; //할인 가격 0
	private int totalpay;
	private String payway;//1-현금결제 2-계좌간편이체 3-신용체크카드 4-토스 5-네이버페이
	
	//setter 
	
	public void setId(String id) {
		this.id = id;
	}
	
	public void setAdult(String adult) {
		this.adult = adult;
	}
	public void setChild(String child) {
		this.child = child;
	}
	public void setStartday(String startday) {
		this.startday = startday;
	}
	public void setEndday(String endday) {
		this.endday = endday;
	}
//	public void setName(String name) {
//		this.name = name;
//	}
//	public void setPhone(String phone) {
//		this.phone = phone;
//	}
	public void setRoomNum(String roomNum) {
		this.roomNum = roomNum;
	}
	public void setComeway(int comeway) {
		this.comeway = comeway;
	}
	public void setPay(int pay) {
		this.pay = pay;
	}
	public void setSalepay(int salepay) {
		this.salepay = salepay;
	}
	public void setTotalpay(int totalpay) {
		this.totalpay = totalpay;
	}
	public void setPayway(String payway) {
		this.payway = payway;
	}
	
	//getter
	
	public String getId() {
		return id;
	}
	public String getAdult() {
		return adult;
	}
	public String getChild() {
		return child;
	}
	public String getStartday() {
		return startday;
	}
	public String getEndday() {
		return endday;
	}
//	public String getName() {
//		return name;
//	}
//	public String getPhone() {
//		return phone;
//	}
	public String getRoomNum() {
		return roomNum;
	}
	public int getComeway() {
		return comeway;
	}
	public int getPay() {
		return pay;
	}
	public int getSalepay() {
		return salepay;
	}
	public int getTotalpay() {
		return totalpay;
	}
	public String getPayway() {
		return payway;
	}
	
	 @Override
	   public String toString() {
	      return id;
	   }

}

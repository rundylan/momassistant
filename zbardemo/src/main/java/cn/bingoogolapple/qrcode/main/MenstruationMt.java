package cn.bingoogolapple.qrcode.main;

public class MenstruationMt {
	private long date;//日期
    private int quantity;//月经流量程度�?~5�?
    private int pain; //痛经程度�?~5�?
	
    public long getDate() {
		return date;
	}
	public void setDate(long date) {
		this.date = date;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public int getPain() {
		return pain;
	}
	public void setPain(int pain) {
		this.pain = pain;
	}
	@Override
	public String toString() {
		return "Menstruation [date=" + date + ", quantity=" + quantity
				+ ", pain=" + pain + ", Id_time=" + "]";
	}
}

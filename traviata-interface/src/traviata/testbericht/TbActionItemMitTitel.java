package traviata.testbericht;

public abstract class TbActionItemMitTitel implements TbActionItem {
	private String titel;
	
	public void setTitel(String titel) {
		this.titel = titel;
	}

	public String getTitel() {
		return titel;
	}
}

package todo.gui.model.vo;

import java.io.Serializable;

public class TodoList implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3624519636836880974L;
	String doText;
	String priority;
	
	public TodoList() {
	}

	public TodoList(String doText, String priority) {
		super();
		this.doText = doText;
		this.priority = priority;
	}

	public String getDoText() {
		return doText;
	}

	public void setDoText(String doText) {
		this.doText = doText;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}
	
	@Override
	public String toString(){
		return "todolsit : "+doText+" , priority : "+priority;
	}
	
	
}

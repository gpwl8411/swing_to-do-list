package todo.gui.model.vo;

import java.io.Serializable;
import java.util.Date;

public class TodoList implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3624519636836880974L;
	
	private String doText;
	private String priority;
	private Date date;
	
	public TodoList() {
		doText = "입력한 리스트가 없습니다.";
	}

	public TodoList(String doText, String priority,Date date) {
		super();
		this.doText = doText;
		this.priority = priority;
		this.date =date;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
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
	@Override
	public boolean equals(Object obj){
		if(obj instanceof TodoList){
			TodoList todolist = (TodoList) obj;
			return(doText.equals(todolist.doText) && priority.equals(todolist.getPriority()));
		}else{
			return false;
		}
	}
//	@Override
//	public int hashCode(){
//		final int prime = 31;
//		return *prime;
//	}
	
}

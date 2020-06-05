package todo.gui.model.vo;

import java.io.Serializable;

public class TodoList implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3624519636836880974L;
	
	private String doText;
	private String priority;
	
	
	public TodoList() {
		doText = "입력한 리스트가 없습니다.";
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

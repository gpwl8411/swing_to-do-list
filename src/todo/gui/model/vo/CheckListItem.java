package todo.gui.model.vo;

import java.text.SimpleDateFormat;

public class CheckListItem {

	private String label;
	private TodoList list = new TodoList();
	private boolean isSelected = false;

	public CheckListItem(String label) {
		this.label = label;
	}
	public CheckListItem(TodoList list){
		this.list = list;
	}
	

	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}

	public TodoList getList() {
		return list;
	}
	public void setList(TodoList list) {
		this.list = list;
	}
	@Override
	public String toString() {
		SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd");
		String date = transFormat.format(list.getDate());
		
		return String.format("%-20s%-20s%-20s","   "+date,"  우선순위 : "+list.getPriority(),list.getDoText()) ;
	}


}

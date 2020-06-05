package todo.gui.model.vo;

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
		return list.getDoText();
	}
}

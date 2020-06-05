package todo.gui.controller;

import java.util.Comparator;

import todo.gui.model.vo.CheckListItem;

public class TodoListComparator implements Comparator<CheckListItem> {
	
	@Override
    public int compare(CheckListItem o1, CheckListItem o2) {
        return o1.getList().getDate().compareTo(o2.getList().getDate());
    }
}

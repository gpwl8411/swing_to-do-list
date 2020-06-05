package todo.gui.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JList;

import todo.gui.model.vo.CheckListItem;
import todo.gui.model.vo.TodoList;

public class ListIO {

	public void saveList(DefaultListModel<CheckListItem> list, String fileName) {

		try (FileOutputStream fos = new FileOutputStream(fileName);
				BufferedOutputStream bos = new BufferedOutputStream(fos);
				ObjectOutputStream oos = new ObjectOutputStream(bos);) {
			for (int i = 0; i < list.size(); i++) {
				CheckListItem c = list.getElementAt(i);
				System.out.println(c.getList());
				oos.writeObject(c.getList());
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public ArrayList<TodoList> loadList(String fileName) {

		ArrayList<TodoList> list = new ArrayList<TodoList>();

		try (FileInputStream fis = new FileInputStream(fileName);
				BufferedInputStream bis = new BufferedInputStream(fis);
				ObjectInputStream ois = new ObjectInputStream(bis);
		) {

			while (true) {
				TodoList t = (TodoList) ois.readObject();
				list.add(t);

			}

		} catch (EOFException e) {

		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {

		}

		return list;
	}


}

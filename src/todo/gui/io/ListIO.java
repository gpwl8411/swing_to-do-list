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
import java.util.List;

import todo.gui.model.vo.TodoList;

public class ListIO {

	public void saveList(TodoList vo) {

		try (FileOutputStream fos = new FileOutputStream("todolist.dat",true);
				BufferedOutputStream bos = new BufferedOutputStream(fos);
				ObjectOutputStream oos = new ObjectOutputStream(bos);) {
			oos.writeObject(vo);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	public ArrayList<TodoList> loadList(){
		
		ArrayList<TodoList> list = new ArrayList<TodoList>();
		try(FileInputStream fis = new FileInputStream("todolist.dat");
				BufferedInputStream bis = new BufferedInputStream(fis);
				
				){
			while(true) {
				ObjectInputStream ois = new ObjectInputStream(bis);
				TodoList t = (TodoList) ois.readObject();
				System.out.println(t);
				list.add(t);
			}
			
		}catch(EOFException e){
			
		}
		catch(IOException | ClassNotFoundException e){
			e.printStackTrace();
		}
		catch(NullPointerException e){
			
		}
		return list;
	}

}

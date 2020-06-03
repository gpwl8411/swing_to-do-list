package todo.common;


import javax.swing.JFrame;
import javax.swing.JPanel;

import todo.gui.view.AddListFrame;

public class GuiUtil {

	public static void init(JFrame f, int x, int y, int w, int h) {
		f.setBounds(x, y, w, h);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	public static void changePanel(JFrame f, JPanel before, JPanel after) {
		f.remove(before);
		f.add(after);
		
		
		//변경된 컴포넌트 반영 
		f.repaint();
		f.revalidate();

	}
}

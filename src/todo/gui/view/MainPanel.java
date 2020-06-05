package todo.gui.view;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MainPanel extends JPanel{
	public MainPanel(){
		/**
		 * 메인 리스트 확인 패널
		 */
//		// 할일과 환료한 힐 패널 두개가 들어있는 패널
//		JPanel mainpanel = new JPanel();
//		mainpanel.setLayout(null);
//		mainpanel.setBounds(0, 0, 700, 700);
		setLayout(null);
		setBounds(0, 0, 700, 700);
//
		// 할일 확인 패널
		JPanel ingpanel = new JPanel();
		// ingpanel.setLayout(null);
		ingpanel.setBounds(0, 0, 700, 300);
		ingpanel.setBackground(Color.gray);
//
		JLabel dolabel = new JLabel("할 일");
		dolabel.setBounds(10, 10, 100, 50);
		ingpanel.add(dolabel);

		// 할일 추가,삭제 버튼
		JButton goAdd = new JButton("할일추가");
		goAdd.setBounds(500, 10, 100, 50);
		ingpanel.add(goAdd);
//
//		// list가져오기
//		setList(ingpanel);
//		
//
//		// 완료한 일 확인 패널
//		JPanel edpanel = new JPanel();
//		// edpanel.setLayout(null);
//		edpanel.setBounds(0, 300, 700, 400);
//
//		JLabel edlabel = new JLabel("완료한 일");
//		edlabel.setBounds(10, 310, 100, 50);
//		edpanel.add(edlabel);
//
//		mainpanel.add(ingpanel);
//		mainpanel.add(edpanel);

	}

}

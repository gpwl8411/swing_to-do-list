package todo.gui.view;

import static todo.common.GuiUtil.changePanel;
import static todo.common.GuiUtil.init;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;

import todo.gui.io.ListIO;
import todo.gui.model.vo.TodoList;

public class AddListFrame extends JFrame {

	private ListIO listIO = new ListIO();
	private String priority;
	private static JList<String> itemList=new JList<String>();
	
	public AddListFrame() {

		init(this, 300, 300, 700, 700);
		/**
		 * 메인 리스트 확인 패널
		 */
		// 할일과 환료한 힐 패널 두개가 들어있는 패널
		JPanel mainpanel = new JPanel();
		mainpanel.setLayout(null);
		mainpanel.setBounds(0, 0, 700, 700);

		// 할일 확인 패널
		JPanel ingpanel = new JPanel();
		// ingpanel.setLayout(null);
		ingpanel.setBounds(0, 0, 700, 300);
		ingpanel.setBackground(Color.gray);

		JLabel dolabel = new JLabel("할 일");
		dolabel.setBounds(10, 10, 100, 50);
		ingpanel.add(dolabel);

		// 할일 추가,삭제 버튼
		JButton goAdd = new JButton("할일추가");
		goAdd.setBounds(500, 10, 100, 50);
		ingpanel.add(goAdd);

		// list가져오기
		setList(ingpanel);
		

		// 완료한 일 확인 패널
		JPanel edpanel = new JPanel();
		// edpanel.setLayout(null);
		edpanel.setBounds(0, 300, 700, 400);

		JLabel edlabel = new JLabel("완료한 일");
		edlabel.setBounds(10, 310, 100, 50);
		edpanel.add(edlabel);

		mainpanel.add(ingpanel);
		mainpanel.add(edpanel);

		/**
		 * 아래는 등록 패널
		 */
		JPanel panel = new JPanel();
		panel.setLayout(null);

		JLabel label = new JLabel("할일 1");
		label.setBounds(10, 50, 500, 20);

		JButton cancel = new JButton("취소");
		cancel.setBounds(550, 400, 70, 70);

		JTextField doText = new JTextField();
		doText.setBounds(100, 50, 500, 20);

		JButton addDo = new JButton("등록");
		addDo.setBounds(450, 400, 70, 70);

		JButton btn2 = new JButton("달력");
		btn2.setBounds(620, 50, 60, 50);

		JPanel abc = new JPanel();

		JRadioButton high = new JRadioButton("높음");
		JRadioButton medium = new JRadioButton("보통");
		JRadioButton low = new JRadioButton("낮음");

		abc.setBounds(200, 250, 200, 50);
		abc.add(high);
		abc.add(medium);
		abc.add(low);

		ButtonGroup group = new ButtonGroup();
		group.add(high);
		group.add(medium);
		group.add(low);

		panel.add(abc);
		panel.add(btn2);
		panel.add(cancel);
		panel.add(addDo);
		panel.add(label);
		panel.add(doText);

		// 우선순위 선택 라디오 버튼
		ActionListener listener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JRadioButton src = (JRadioButton) e.getSource();
				priority = src.getText();
				
			}
		};

		high.addActionListener(listener);
		medium.addActionListener(listener);
		low.addActionListener(listener);
		// 등록버튼
		addDo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (priority == null) {
					// 경고메세지 출력
					JOptionPane.showMessageDialog(null, "할일을 입력해주세요.");
					return;
				}

				// 리스트와 우선순위를 파일저장하도록 요청
				TodoList vo = new TodoList(doText.getText(), priority);
				listIO.saveList(vo);

				JOptionPane.showMessageDialog(null, "정상적으로 등록되었습니다.");
				doText.setText("할일을 입력해주세요.");
				setList(ingpanel);
			}
		});
		
		
		
		/**
		 * main <-> 등록
		 */
		// 할일추가버튼 : main -> 등록
		goAdd.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				changePanel(AddListFrame.this, mainpanel, panel);
			}
		});
		
		// 등록패널 취소버튼 : 등록 -> main
		cancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				changePanel(AddListFrame.this, panel, mainpanel);
				
				
			}

		});
		
		add(mainpanel);
		setVisible(true);

	}
	
	/**
	 * 등록되어 있는 할 일 리스트를 불러와서 표시해줌
	 * 등록 페이지에서 새로 등록되었을 경우 추가된 새로운 리스트를 생성
	 * @param panel
	 */
	public void setList(JPanel panel) {
		JPanel flowPanel = new JPanel(new FlowLayout());

		List<TodoList> loadlist = listIO.loadList();
		String[] list;
		if (loadlist == null) {
			list = new String[1];
			list[0] = "입력된 list가 없습니다.";
		} else {

			list = new String[loadlist.size()];
			for (int i = 0; i < loadlist.size(); i++) {
				list[i] = loadlist.get(i).getDoText();
			}
		}

		panel.remove(itemList);
		panel.revalidate();
		this.repaint();
		
		itemList = new JList<>(list);
		itemList.setSelectedIndex(0);
		itemList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		panel.add(itemList);

	}

}

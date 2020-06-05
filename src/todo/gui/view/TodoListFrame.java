
package todo.gui.view;

import static todo.common.GuiUtil.changePanel;
import static todo.common.GuiUtil.init;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
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
import todo.gui.model.vo.CheckListItem;
import todo.gui.model.vo.TodoList;

public class TodoListFrame extends JFrame {

	private ListIO listIO = new ListIO();
	private String priority;
	private static JList<CheckListItem> ingList;
	private static JList<CheckListItem> edList;
	private static DefaultListModel<CheckListItem> ingmodel;
	private static DefaultListModel<CheckListItem> edmodel;
	private static JPanel ingpanel;
	private static JPanel edpanel;

	public TodoListFrame() {
		
		init(this, 300, 300, 700, 700);
		this.addWindowListener(new WindowEventTest());

		/**
		 * 메인 리스트 확인 패널
		 */

		// 할일과 환료한 힐 패널 두개가 들어있는 패널
		JPanel mainpanel = new JPanel();
		mainpanel.setLayout(new GridLayout(2,1,10,3));
		mainpanel.setBounds(0, 0, 700, 700);
		
		//할일, 완료한 일 담는 리스트와 해당 리스트를 제어할 모델설정
		ingList = new JList(new DefaultListModel<CheckListItem>());
		edList = new JList(new DefaultListModel<CheckListItem>());
		ingmodel = (DefaultListModel<CheckListItem>) ingList.getModel();
		edmodel = (DefaultListModel<CheckListItem>) edList.getModel();
		
		//폰트변경
		Font f1 = new Font("돋움", Font.BOLD, 20);
		ingList.setFont(f1);
		ingList.setBackground(Color.lightGray);
		
		

		// 할일 확인 패널
		ingpanel = new JPanel();
//		 ingpanel.setLayout(null);
		ingpanel.setBounds(0, 0, 700, 300);
		ingpanel.setBackground(Color.gray);

		JLabel dolabel = new JLabel("할 일");
		dolabel.setBounds(10, 10, 100, 50);
		ingpanel.add(dolabel);

		// 할일 추가,삭제 버튼
		JButton goAdd = new JButton("할일추가");
		goAdd.setBounds(500, 10, 100, 50);
		ingpanel.add(goAdd);
		
		JButton delete = new JButton("전체삭제");
		goAdd.setBounds(500, 10, 100, 50);
		ingpanel.add(delete);
		

		// list가져오기
		setList(ingpanel, "ing", "inglist.dat");

		// 완료한 일 확인 패널
		edpanel = new JPanel();
		// edpanel.setLayout(null);
		edpanel.setBounds(0, 300, 700, 400);

		JLabel edlabel = new JLabel("완료한 일");
		edlabel.setBounds(10, 310, 100, 50);
		edpanel.add(edlabel);

		// list가져오기
		setList(edpanel, "ed", "edlist.dat");

		mainpanel.add(ingpanel);
		mainpanel.add(edpanel);
		
		//스크롤 설정
//		JScrollPane ingscroll = new JScrollPane();
//		ingscroll.setViewportView(ingList);
//		
//		JScrollPane edscroll = new JScrollPane();
//		edscroll.setViewportView(edList);
//		
//		ingpanel.add(ingscroll);
//		edpanel.add(edscroll);
		
		//delete button listener
		delete.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

//				if (priority == null) {
//					// 경고메세지 출력
//					JOptionPane.showMessageDialog(null, "할일을 입력해주세요.");
//					return;
//				}

				// 리스트와 우선순위를 파일저장하도록 요청
//				TodoList vo = new TodoList(doText.getText(), priority);
//				CheckListItem cvo = new CheckListItem(vo);
				// listIO.saveList(vo);

				JOptionPane.showMessageDialog(null, "정상적으로 삭제되었습니다.");
//				doText.setText("할일을 입력해주세요.");
				removeList(ingmodel);
			}
		});
		

		// list listener등록
		CheckClickListener ingListener = new CheckClickListener(ingmodel, edmodel);
		ingList.addMouseListener(ingListener);
		CheckClickListener edListener = new CheckClickListener(edmodel, ingmodel);
		edList.addMouseListener(edListener);

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
		
		btn2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
	
			}
		});

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
				CheckListItem cvo = new CheckListItem(vo);
				// listIO.saveList(vo);

				JOptionPane.showMessageDialog(null, "정상적으로 등록되었습니다.");
				doText.setText("할일을 입력해주세요.");
				addList(ingmodel, cvo);
			}
		});

		/**
		 * main <-> 등록
		 */
		// 할일추가버튼 : main -> 등록
		goAdd.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				changePanel(TodoListFrame.this, mainpanel, panel);

			}
		});

		// 등록패널 취소버튼 : 등록 -> main
		cancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				changePanel(TodoListFrame.this, panel, mainpanel);

			}

		});

		add(mainpanel);
		setVisible(true);

	}

	/**
	 * 등록되어 있는 할 일 리스트를 불러와서 표시해줌 처음 실행시 list 세팅역할
	 * 
	 * @param panel
	 */
	public void setList(JPanel panel, String type, String fileName) {
		JPanel flowPanel = new JPanel(new FlowLayout());
		List<TodoList> loadlist = listIO.loadList(fileName);
		CheckListItem[] checkList;

		if (type.equals("ing")) {
			if (loadlist == null) {
				checkList = new CheckListItem[1];
				checkList[0] = new CheckListItem(new TodoList());
			} else {

				checkList = new CheckListItem[loadlist.size()];
				for (int i = 0; i < loadlist.size(); i++) {
					checkList[i] = new CheckListItem(loadlist.get(i));
					ingmodel.addElement(checkList[i]);
	
				}
			}
			ingList.setCellRenderer(new CheckListRenderer());
			ingList.setSelectedIndex(0);
			ingList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

			panel.add(ingList);
		} else {
			if (loadlist == null) {
				checkList = new CheckListItem[1];
				checkList[0] = new CheckListItem(new TodoList());
			} else {

				checkList = new CheckListItem[loadlist.size()];
				for (int i = 0; i < loadlist.size(); i++) {
					checkList[i] = new CheckListItem(loadlist.get(i));
					edmodel.addElement(checkList[i]);

				}
			}
			edList.setCellRenderer(new CheckListRenderer());
			edList.setSelectedIndex(0);
			edList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

			panel.add(edList);
		}

	}

	// list 동적 추가 함수
	public void addList(DefaultListModel<CheckListItem> model, CheckListItem vo) {
		vo.setSelected(false);
		model.addElement(vo);

	}

	// list 동적 제거 함수
	public void removeList(DefaultListModel<CheckListItem> model, CheckListItem vo) {
		vo.setSelected(false);
		model.removeElement(vo);

	}
	//오버로딩 list 동적 전체 삭제 함수
	public void removeList(DefaultListModel<CheckListItem> model) {
			model.removeAllElements();
	}

	public class CheckClickListener extends MouseAdapter {

		DefaultListModel<CheckListItem> model;
		DefaultListModel<CheckListItem> otherModel;

		CheckClickListener(DefaultListModel<CheckListItem> model, DefaultListModel<CheckListItem> otherModel) {

			this.model = model;
			this.otherModel = otherModel;

		}

		@Override
		public void mouseClicked(MouseEvent event) {
			JList list = (JList) event.getSource();
			int index = list.locationToIndex(event.getPoint());// Get index
																// of item
																// clicked
			CheckListItem item = (CheckListItem) list.getModel().getElementAt(index);
			item.setSelected(!item.isSelected()); // Toggle selected state
			list.repaint(list.getCellBounds(index, index));// Repaint cell
			System.out.println(item.getList());
			removeList(model, item);
			addList(otherModel, item);

		}
	}

	public class WindowEventTest extends WindowAdapter{

		@Override
		public void windowClosing(WindowEvent arg0) {
			listIO.saveList(ingmodel, "inglist.dat");
			listIO.saveList(edmodel, "edlist.dat");
			
		}

		
	}
}

/**
 * list에서 체크된 컴포넌트 돌려줌
 * 
 * @author hyeji
 *
 */
class CheckListRenderer extends JCheckBox implements ListCellRenderer<Object> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected,
			boolean hasFocus) {
		setEnabled(list.isEnabled());
		setSelected(((CheckListItem) value).isSelected());
		setFont(list.getFont());
		setBackground(list.getBackground());
		setForeground(list.getForeground());
		setText(value.toString());
		
		return this;
	}
}


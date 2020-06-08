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
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
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

import net.sourceforge.jdatepicker.impl.DateComponentFormatter;
import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;
import todo.gui.controller.TodoListComparator;
import todo.gui.io.ListIO;
import todo.gui.model.vo.CheckListItem;
import todo.gui.model.vo.TodoList;

public class TodoListFrame extends JFrame {

	private ListIO listIO = new ListIO();
	private String priority;
	private Date selectedDate = null;
	private static JList<CheckListItem> ingList;
	private static JList<CheckListItem> edList;
	private static DefaultListModel<CheckListItem> ingmodel;
	private static DefaultListModel<CheckListItem> edmodel;
	private static JPanel ingpanel;
	private static JPanel edpanel;

	public TodoListFrame() {
		
		//윈도우 화면 어디에 띄울래?
		init(this, 150, 50, 850, 700);
		this.addWindowListener(new WindowEventTest());
		
		//프로그램 네이밍창
		setTitle("ToDoList");
		
		/**
		 * 메인 리스트 확인 패널
		 */

		// 할일과 완료한 일 패널 두개가 들어있는 패널
		JPanel mainpanel = new JPanel();
		mainpanel.setLayout(new GridLayout(2,1,10,3));
		mainpanel.setBounds(0, 0, 1920, 1080);
		mainpanel.setBackground(new Color(250, 222, 255));
		
		//할일, 완료한 일 담는 리스트와 해당 리스트를 제어할 모델설정
		ingList = new JList(new DefaultListModel<CheckListItem>());
		edList = new JList(new DefaultListModel<CheckListItem>());
		ingmodel = (DefaultListModel<CheckListItem>) ingList.getModel();
		edmodel = (DefaultListModel<CheckListItem>) edList.getModel();
		
		//폰트변경
		Font f1 = new Font("굴림", Font.BOLD, 20);
		Font f2 = new Font("휴먼매직체", Font.BOLD, 40);
		
		
		

		// 할일 확인 패널
		JPanel ingpanel = new JPanel();
		ingpanel.setLayout(null); 
		ingpanel.setBounds(0, 100, 1920, 1080); 
		ingpanel.setBackground(new Color(250, 222, 255));
	
		//할일제목라벨
		JLabel dolabel = new JLabel("해야할 일");
		dolabel.setBounds(50, 0, 300, 100);
		ingpanel.add(dolabel);
		dolabel.setFont(f2);
		
		 //할일 추가,삭제 버튼
		JButton goAdd = new JButton("할일추가");
		goAdd.setBounds(350, 20, 90, 50);
		ingpanel.add(goAdd);
		
		JButton ingdelete = new JButton("할일 전체삭제");
		ingdelete.setBounds(450, 20, 150, 50);
		ingpanel.add(ingdelete);
		
		//날짜순 리스트 정렬 버튼
		JButton sort = new JButton("날짜순 정렬");
		sort.setBounds(610, 20, 110, 50);
		ingpanel.add(sort);
		

		// list가져오기 , 출력
		setList(ingpanel, "ing", "inglist.dat");
		ingList.setLayout(null);
		ingList.setBounds(0, 100, 1920, 1080);
		ingList.setBackground(new Color(250, 240, 255));
		ingList.setFont(f1);

		
		// 완료한 일 확인 패널
		JPanel edpanel = new JPanel();
		edpanel.setLayout(null);
		edpanel.setBounds(0, 300, 1920, 1080);
		edpanel.setBackground(new Color(250, 222, 255));		
		
		JLabel edlabel = new JLabel("완료한 일");
		edlabel.setBounds(50, 0, 300, 100);
		edpanel.add(edlabel);
		edlabel.setFont(f2);
		
		JButton eddelete = new JButton("완료한 일 전체삭제");
		eddelete.setBounds(450, 0, 150, 50);
		edpanel.add(eddelete);

		
		// list가져오기
		setList(edpanel, "ed", "edlist.dat");
		edList.setLayout(null);
		edList.setBounds(0, 100, 1920, 1080);
		edList.setBackground(new Color(250, 240, 255));
		edList.setFont(f1);
		
		
		mainpanel.add(ingpanel);
		mainpanel.add(edpanel);
		
		//delete button listener
		ingdelete.addMouseListener(new DeleteListListener("ing"));
		eddelete.addMouseListener(new DeleteListListener("ed"));
		
		// sort button listener
		sort.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				//해야할 일 리스트 정렬
				List<CheckListItem> clist = Collections.list(ingmodel.elements());
				Collections.sort(clist, new TodoListComparator());
				ingmodel.removeAllElements();
				for(CheckListItem c : clist){
					ingmodel.addElement(c);
					
				}
				//완료한 일 리스트 정렬
				List<CheckListItem> clist2 = Collections.list(edmodel.elements());
				Collections.sort(clist2, new TodoListComparator());
				edmodel.removeAllElements();
				for(CheckListItem c : clist2){
					edmodel.addElement(c);
					
				}
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
		panel.setBackground(new Color(250, 240, 255));


		JLabel label = new JLabel("할일입력");
		label.setBounds(10, 50, 90, 20);
		label.setHorizontalAlignment(JLabel.CENTER);
		

		JTextField doText = new JTextField();
		doText.setBounds(100, 50, 400, 200);
		
		JLabel dateLabel = new JLabel("날짜 선택");
		dateLabel.setBounds(600, 50, 60, 50);
		UtilDateModel model = new UtilDateModel();
		JDatePanelImpl datePanel = new JDatePanelImpl(model);
		JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateComponentFormatter());
		datePicker.setBounds(600, 90, 100, 50);

		ImageIcon back = new ImageIcon("images/back.jpg");
		JButton cancel = new JButton(back);
		cancel.setBounds(450, 400, 70, 70);

		ImageIcon Ok = new ImageIcon("images/ok.jpg");
		JButton addDo = new JButton(Ok);
		addDo.setBounds(350, 400, 70, 70);
		

		JPanel abc = new JPanel();
		abc.setBackground(new Color(250, 240, 255));

		JRadioButton high = new JRadioButton("높음");
		high.setBackground(new Color(250, 240, 255));
		JRadioButton medium = new JRadioButton("보통");
		medium.setBackground(new Color(250, 240, 255));
		JRadioButton low = new JRadioButton("낮음");
		low.setBackground(new Color(250, 240, 255));

		abc.setBounds(200, 250, 200, 50);
		abc.add(high);
		abc.add(medium);
		abc.add(low);
		

		ButtonGroup group = new ButtonGroup();
		group.add(high);
		group.add(medium);
		group.add(low);

		panel.add(abc);
//		panel.add(btn2);
		panel.add(datePicker);
		panel.add(cancel);
		panel.add(addDo);
		panel.add(label);
		panel.add(doText);
		panel.add(dateLabel);
		

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
						selectedDate = (Date) datePicker.getModel().getValue();
						
						if(doText.getText().length()>=15){
							// 경고메세지 출력
							JOptionPane.showMessageDialog(null, "최대 15글자까지 입력가능합니다.");
							return;
						}
						if (doText.getText() == null || doText.getText().length()==0) {
							// 경고메세지 출력
							JOptionPane.showMessageDialog(null, "할 일을 입력해주세요.");
							return;
						}
						if (priority == null) {
							// 경고메세지 출력
							JOptionPane.showMessageDialog(null, "우선순위를 선택해주세요.");
							return;
						}
						if(selectedDate ==null){
							// 경고메세지 출력
							JOptionPane.showMessageDialog(null, "날짜를 선택해주세요.");
							return;
						}
						
						// 리스트와 우선순위 날짜를 파일저장하도록 요청
						TodoList vo = new TodoList(doText.getText(), priority, selectedDate);
						CheckListItem cvo = new CheckListItem(vo);
						// listIO.saveList(vo);

						JOptionPane.showMessageDialog(null, "정상적으로 등록되었습니다.");
						doText.setText("할일을 입력해주세요.");
						group.clearSelection();
						addList(ingmodel, cvo);
						//초기화
						priority=null;
						selectedDate=null;
						
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
//					System.out.println(checkList[i]);
	
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
//					System.out.println(checkList[i]);

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
	//list 전체 삭제 클릭시 이벤트 리스너
	public class DeleteListListener extends MouseAdapter{
		
		String type;
		DeleteListListener(String type){
			this.type = type;
		}

		@Override
		public void mouseClicked(MouseEvent event) {
			
			JOptionPane.showMessageDialog(null, "정상적으로 삭제되었습니다.");
			if(type=="ing"){				
				removeList(ingmodel);
			}else{
				removeList(edmodel);
			}
		}
	}
	//list체크박스 체크시클릭시 이벤트 리스너
	public class CheckClickListener extends MouseAdapter {

		DefaultListModel<CheckListItem> model;
		DefaultListModel<CheckListItem> otherModel;
		String checkText;

		CheckClickListener(DefaultListModel<CheckListItem> model, DefaultListModel<CheckListItem> otherModel) {

			this.model = model;
			this.otherModel = otherModel;
			
				if(model==ingmodel){
					checkText ="완료 리스트에 옮기시겠습니까?";
				}else{
					checkText ="할 일 리스트에 옮기시겠습니까?";
				}
			
			

		}

		@Override
		public void mouseClicked(MouseEvent event) {
			//옮길 리스트가 없을 경우 리턴
			if(model.size()==0) return;
			
			//리스트가 있을경우 이동 처리해 줌
			JList list = (JList) event.getSource();
			int index = list.locationToIndex(event.getPoint());// Get index
																// of item
																// clicked
			int result = JOptionPane.showConfirmDialog(ingList, checkText,"체크",JOptionPane.
					YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
			if (result == JOptionPane.CLOSED_OPTION){
				dispose();
				return;
			}
			else if (result == JOptionPane.YES_OPTION){
				
				CheckListItem item = (CheckListItem) list.getModel().getElementAt(index);
				item.setSelected(!item.isSelected()); // Toggle selected state
				list.repaint(list.getCellBounds(index, index));// Repaint cell
				System.out.println(item.getList());
				removeList(model, item);
				addList(otherModel, item);
				
			}
			
			
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
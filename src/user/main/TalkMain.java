package user.main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import user.home.Home;
import user.member.Login;
import user.member.MemberDetail;
import user.member.MyPage;
import user.member.RegistForm;
import util.db.DBManager;

public class TalkMain extends JFrame{
	//상수
	public static final int WIDTH = 600;
	public static final int HEIGHT = 700;
	
	//상위페이지
	public static final int HOME = 0;
	public static final int MYPAGE = 1;
	public static final int LOGIN = 2;
	
	//하위페이지
	public static final int REGISTFORM = 3;
	public static final int MEMBERDETAIL = 4;
	
	JPanel p_container;
	JPanel p_content;	//각각의 페이지 붙일곳
	
	JPanel p_menu;
	
	String[] menu_title = {"Home", "MyPage", "Login"};
	public JButton[] menu = new JButton[menu_title.length];
	
	Page[] page = new Page[5];
	
	DBManager dbManager;
	Connection con;

	private boolean hasSession = false;	//로그인 상태
	
	public TalkMain() {
		
		dbManager = new DBManager();
		p_container = new JPanel();
		p_content = new JPanel();
		p_menu = new JPanel();
		
		con = dbManager.connect();
		if(con == null) {
			JOptionPane.showMessageDialog(this, "db에 접속할 수 없습니다");
			System.exit(0);
		}else {
			this.setTitle("TalkTalk 실행중");
		}
		
		//상단 메뉴 생성
		for(int i=0; i<menu.length; i++) {
			menu[i] = new JButton(menu_title[i]);
			menu[i].setPreferredSize(new Dimension(100, 50));
			menu[i].setBackground(Color.WHITE);
			menu[i].setFont(new Font("Verdana", Font.BOLD, 15));
			p_menu.add(menu[i]);
		}
		
		//페이지 구성
		page[0] = new Home(this);
		page[1] = new MyPage(this);
		page[2] = new Login(this);		
		page[3] = new RegistForm(this);	
		page[4] = new MemberDetail(this);		
		
		//스타일
		p_container.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		p_content.setPreferredSize(new Dimension(WIDTH, HEIGHT-100));
		p_content.setBackground(Color.WHITE);
		p_menu.setPreferredSize(new Dimension(WIDTH, 75));
		p_menu.setBackground(Color.WHITE);
		
		//페이지 부착
		for(int i=0; i<page.length; i++) {
			p_content.add(page[i]);
		}
		
		//조립
		p_container.add(p_menu, BorderLayout.NORTH);
		p_container.add(p_content);
		
		add(p_container);
		
		setVisible(true);
		setSize(WIDTH+50, HEIGHT+50);
		setLocationRelativeTo(null);
		
		//프레임과 리스너 연결
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				dbManager.disConnect(con);
				System.exit(0);
			}
		});
		
		//메뉴 버튼과 리스너 연결
		for(int i=0; i<menu.length; i++) {
			menu[i].addActionListener((e)->{
				Object obj = e.getSource();
				if(obj==menu[0]) {	//home
					if(hasSession==false) {
						JOptionPane.showMessageDialog(TalkMain.this, "로그인이 필요한 서비스입니다");
						showPage(2);
					}else {
						showPage(0);						
					}
				}else if(obj==menu[1]) {	//MyPage
					if(hasSession==false) {
						JOptionPane.showMessageDialog(TalkMain.this, "로그인이 필요한 서비스입니다");
						showPage(2);
					}else {
						showPage(1);						
					}
				}else if(obj==menu[2]) {	//LOGIN
					if(hasSession) {
						int ans = JOptionPane.showConfirmDialog(TalkMain.this, "로그아웃 하시겠습니까?");
						
						if(ans==JOptionPane.OK_OPTION) {
							Login loginPage = (Login)page[TalkMain.LOGIN];
							loginPage.logout();
						}
					}else {
						showPage(2);						
					}
				}
			});
		}
		showPage(LOGIN);
		
	}
	
	public void showPage(int showIndex) {
		for(int i=0; i<page.length; i++) {
			if(i==showIndex) {
				page[i].setVisible(true);
			}else {
				page[i].setVisible(false);
			}
		}
	}
	
	public void addRemoveContent(Component removeObj, Component addObj) {
		this.remove(removeObj);
		this.add(addObj);
		
		((JPanel)addObj).updateUI();
	}
	
	public Page[] getPage() {
		return page;
	}

	public DBManager getDbManager() {
		return dbManager;
	}

	public Connection getCon() {
		return con;
	}
	
	public boolean isHasSession() {
		return hasSession;
	}

	public void setHasSession(boolean hasSession) {
		this.hasSession = hasSession;
	}

	public static void main(String[] args) {
		new TalkMain();
	}
}
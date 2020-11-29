package user.member;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import common.image.ImageUtil;
import user.main.Page;
import user.main.TalkMain;

public class Login extends Page{
	JPanel p_content;
	JPanel p_bt;	//버튼 담을 패널
	JLabel la_id;
	JTextField t_uid;
	JLabel la_pass;
	JPasswordField t_pass;
	JButton bt_login;
	JButton bt_regist;
	
	public Login(TalkMain talkMain) {
		super(talkMain);
		
		p_content = new JPanel();
		p_bt = new JPanel();
		la_id = new JLabel("ID");
		t_uid = new JTextField();
		la_pass =new JLabel("Password");
		t_pass = new JPasswordField();
		bt_login = new JButton("Login");
		bt_regist = new JButton("회원가입");
		
		//스타일
		p_content.setPreferredSize(new Dimension(TalkMain.WIDTH, 130));
		la_id.setPreferredSize(new Dimension(200,30));
		la_pass.setPreferredSize(new Dimension(200,30));
		la_id.setFont(new Font("Verdana", Font.BOLD, 17));
		la_pass.setFont(new Font("Verdana", Font.BOLD, 17));
		t_uid.setPreferredSize(new Dimension(250, 30));
		t_pass.setPreferredSize(new Dimension(250, 30));
		p_bt.setPreferredSize(new Dimension(TalkMain.WIDTH, 50));
		bt_login.setPreferredSize(new Dimension(100, 30));
		bt_regist.setPreferredSize(new Dimension(100, 30));
		bt_login.setFont(new Font("돋움", Font.CENTER_BASELINE, 15));
		bt_regist.setFont(new Font("돋움", Font.CENTER_BASELINE, 15));
		bt_login.setBackground(Color.DARK_GRAY);
		bt_regist.setBackground(Color.DARK_GRAY);
		bt_login.setForeground(Color.WHITE);
		bt_regist.setForeground(Color.WHITE);
		
		p_content.add(la_id);
		p_content.add(t_uid);
		p_content.add(la_pass);
		p_content.add(t_pass);
		p_bt.add(bt_login);
		p_bt.add(bt_regist);
		
		add(p_content);
		add(p_bt, BorderLayout.SOUTH);
		
		t_uid.requestFocus();
		
		//bt_regist 와 리스너 연결
		bt_regist.addActionListener((e)->{
			getTalkMain().showPage(TalkMain.REGISTFORM);
		});
		
		//bt_login 과 리스너 연결
		bt_login.addActionListener((e)->{
			TalkMember vo = new TalkMember();
			vo.setT_uid(t_uid.getText());
			vo.setPass(new String(t_pass.getPassword()));
			validCheck(vo);
		});
			
	}
	
	//로그인 유효성 체크
	public void validCheck(TalkMember talkMember) {
		if(talkMember.getT_uid().length()<1) {
			JOptionPane.showMessageDialog(this, "ID를 입력하세요");
		}else if(talkMember.getPass().length()<1) {
			JOptionPane.showMessageDialog(this, "비밀번호를 입력하세요");
		}else {
			if(login(talkMember)==null) {
				JOptionPane.showMessageDialog(this, "로그인 정보가 올바르지 않습니다");				
			}else {
				JOptionPane.showMessageDialog(this, "로그인 성공");
				MyPage myPage = (MyPage)getTalkMain().getPage()[TalkMain.MYPAGE];
				myPage.init(login(talkMember));
				getTalkMain().showPage(TalkMain.HOME);
				
				t_uid.setText("");
				t_pass.setText("");
				
				//버튼 라벨 로그아웃으로 전환하기
				getTalkMain().menu[2].setText("logout");
				getTalkMain().menu[2].setBackground(Color.DARK_GRAY);
				getTalkMain().menu[2].setForeground(Color.WHITE);
				
				getTalkMain().setHasSession(true);
			}
		}
	}
	
	//로그인
	public TalkMember login(TalkMember talkMember) {
		//System.out.println("로그인 원해?");
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		TalkMember vo = null;
		
		String sql = "select * from member";
		sql += " where t_uid=? and pass=?";
		
		try {
			pstmt = getTalkMain().getCon().prepareStatement(sql);
			pstmt.setString(1, talkMember.getT_uid());
			pstmt.setString(2, talkMember.getPass());
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				vo = new TalkMember();
				vo.setMember_id(rs.getInt("member_id"));
				vo.setName(rs.getString("name"));
				vo.setT_uid(rs.getString("t_uid"));
				vo.setPass(rs.getString("pass"));
				vo.setEmail(rs.getString("email"));
				vo.setPhone(rs.getString("phone"));
				vo.setFilename(rs.getString("filename"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			getTalkMain().getDbManager().close(pstmt, rs);
		}
		return vo;
	}
	
	//로그아웃
	public void logout() {
		getTalkMain().setHasSession(false);
		getTalkMain().menu[2].setText("login");
		getTalkMain().menu[2].setBackground(null);
		getTalkMain().menu[2].setForeground(null);
		getTalkMain().showPage(TalkMain.LOGIN);
	}
	
}

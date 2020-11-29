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

import user.main.Page;
import user.main.TalkMain;

public class RegistForm extends Page{
	JPanel p_center;	//입력박스들 올곳
	JPanel p_west;
	JPanel p_south;
	String[] la_title = {"NAME", "ID", "PASSWORD", "EMAIL", "PHONE", "IMAGE"};
	JLabel[] la = new JLabel[la_title.length];
	JTextField t_name;
	JTextField t_uid;
	JPasswordField t_pass;
	JTextField t_email;
	JTextField t_phone;
	JTextField t_filename;
	JButton bt_regist;
	
	public RegistForm(TalkMain talkMain) {
		super(talkMain);
		//생성
		p_center = new JPanel();
		p_west = new JPanel();
		p_south = new JPanel();
		t_name = new JTextField();
		t_uid = new JTextField();
		t_pass = new JPasswordField();
		t_email = new JTextField();
		t_phone = new JTextField();
		t_filename = new JTextField();
		bt_regist = new JButton("회원가입");	
		for(int i=0; i<la.length; i++) {
			la[i] = new JLabel(la_title[i]);
			la[i].setPreferredSize(new Dimension(100, 35));
			la[i].setBackground(Color.WHITE);
			la[i].setFont(new Font("Verdana", Font.BOLD, 15));
			p_west.add(la[i]);
		}
		
		//스타일
		p_center.setPreferredSize(new Dimension(370, TalkMain.HEIGHT-370));
		p_center.setBackground(Color.WHITE);
		p_west.setPreferredSize(new Dimension(150, TalkMain.HEIGHT-370));
		p_west.setBackground(Color.WHITE);
		Dimension d = new Dimension(250, 35);
		t_name.setPreferredSize(d);
		t_uid.setPreferredSize(d);
		t_pass.setPreferredSize(d);
		t_email.setPreferredSize(d);
		t_phone.setPreferredSize(d);
		t_filename.setPreferredSize(d);
		bt_regist.setPreferredSize(new Dimension(100, 30));
		bt_regist.setBackground(Color.DARK_GRAY);
		bt_regist.setForeground(Color.WHITE);
		
		//조립
		p_center.add(t_name);
		p_center.add(t_uid);
		p_center.add(t_pass);
		p_center.add(t_email);
		p_center.add(t_phone);
		p_center.add(t_filename);
		p_south.add(bt_regist);
		
		add(p_west, BorderLayout.WEST);
		add(p_center, BorderLayout.CENTER);
		add(p_south, BorderLayout.SOUTH);
		
		//db에 넣기
		bt_regist.addActionListener((e)->{
			if(checkId(t_uid.getText())) {
				JOptionPane.showMessageDialog(RegistForm.this, "중복된 아이디입니다");
			}else {
				if(regist()==0) {
					JOptionPane.showMessageDialog(RegistForm.this, "회원가입을 실패하였습니다");
				}else {
					JOptionPane.showMessageDialog(RegistForm.this, "회원가입을 축하합니다");
					t_name.setText("");
					t_uid.setText("");
					t_pass.setText("");
					t_email.setText("");
					t_phone.setText("");
					t_filename.setText("");
					getTalkMain().showPage(TalkMain.LOGIN);
				}				
			}		
		});
		
	}
	
	//회원 중복여부 체크
	public boolean checkId(String t_uid) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean flag = false;
		
		String sql = "select * from member where t_uid=?";
		
		try {
			pstmt = getTalkMain().getCon().prepareStatement(sql);
			pstmt.setString(1, t_uid);
			rs = pstmt.executeQuery();
			flag = rs.next();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			getTalkMain().getDbManager().close(pstmt, rs);
		}
		return flag;
	}
	
	//회원가입
	public int regist() {
		PreparedStatement pstmt = null;
		int result = 0;
		
		String sql = "insert into member(member_id, name, t_uid, pass, email, phone, filename)";
		sql += " values(seq_member.nextval, ?,?,?,?,?,?)";
		
		try {
			pstmt = getTalkMain().getCon().prepareStatement(sql);
		
			//바인드변수 대입
			pstmt.setString(1, t_name.getText());
			pstmt.setString(2, t_uid.getText());
			pstmt.setString(3, new String(t_pass.getPassword()));
			pstmt.setString(4, t_email.getText());
			pstmt.setString(5, t_phone.getText());
			pstmt.setString(6, t_filename.getText());
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			getTalkMain().getDbManager().close(pstmt);
		}
		return result;	
	}

}

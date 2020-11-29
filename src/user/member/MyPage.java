package user.member;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import user.main.Page;
import user.main.TalkMain;

public class MyPage extends Page{
	JPanel p_center;	//입력박스들 올곳
	JPanel p_west;		//label 붙일곳
	JPanel p_south;		//버튼붙일곳
	String[] la_title = {"NAME", "ID", "PASSWORD", "EMAIL", "PHONE", "IMAGE"};
	JLabel[] la = new JLabel[la_title.length];
	JTextField t_name;
	JTextField t_uid;
	JPasswordField t_pass;
	JTextField t_email;
	JTextField t_phone;
	JTextField t_filename;
	JButton bt_edit;
	
	int member_id;
	
	private TalkMember vo;
	
	public MyPage(TalkMain talkMain) {
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
		bt_edit = new JButton("수정하기");	
		for(int i=0; i<la.length; i++) {
			la[i] = new JLabel(la_title[i]);
			la[i].setPreferredSize(new Dimension(100, 35));
			la[i].setBackground(Color.WHITE);
			la[i].setFont(new Font("Verdana", Font.BOLD, 15));
			p_west.add(la[i]);
		}
		
		//스타일
		p_center.setPreferredSize(new Dimension(350, TalkMain.HEIGHT-250));
		p_center.setBackground(Color.WHITE);
		p_west.setPreferredSize(new Dimension(150, TalkMain.HEIGHT-250));
		p_west.setBackground(Color.WHITE);
		Dimension d = new Dimension(250, 35);
		t_name.setPreferredSize(d);
		t_uid.setPreferredSize(d);
		t_pass.setPreferredSize(d);
		t_email.setPreferredSize(d);
		t_phone.setPreferredSize(d);
		t_filename.setPreferredSize(d);
		bt_edit.setPreferredSize(new Dimension(100, 30));
		bt_edit.setBackground(Color.DARK_GRAY);
		bt_edit.setForeground(Color.WHITE);
		
		//조립
		p_center.add(t_name);
		p_center.add(t_uid);
		p_center.add(t_pass);
		p_center.add(t_email);
		p_center.add(t_phone);
		p_center.add(t_filename);
		p_south.add(bt_edit);
		
		add(p_west, BorderLayout.WEST);
		add(p_center, BorderLayout.CENTER);
		add(p_south, BorderLayout.SOUTH);
		
		bt_edit.addActionListener((e)->{
			int result = edit();
			if(result==0) {
				JOptionPane.showMessageDialog(this, "수정에 실패했습니다");
			}else {
				if(JOptionPane.showConfirmDialog(this, "수정하시겠습니까?")==JOptionPane.OK_OPTION) {
					JOptionPane.showMessageDialog(this, "수정이 완료되었습니다");
					this.updateUI();
				}	
			}
		});
	}
	
	//마이페이지 정보
	public void init(TalkMember vo) {
		this.vo = vo;
		
		t_name.setText(vo.getName());
		t_uid.setText(vo.getT_uid());
		t_pass.setText(vo.getT_uid());
		t_email.setText(vo.getEmail());
		t_phone.setText(vo.getPhone());
		t_filename.setText(vo.getFilename());
		
		member_id = vo.getMember_id();
	}
	
	//정보 수정
	public int edit() {
		PreparedStatement pstmt = null;
		int result = 0;
		//update member set phone='010-1212-3434' where member_id=6;
		String sql = "update member set name=?, t_uid=?, pass=?, email=?, phone=?, filename=? where member_id="+member_id;
		
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
	
	public TalkMember getVo() {
		return vo;
	}

	public void setVo(TalkMember vo) {
		this.vo = vo;
	}
	
}

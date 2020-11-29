package user.member;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import user.home.Home;
import user.main.Page;
import user.main.TalkMain;

public class MemberDetail extends Page{
	JPanel p_content;	//사진과 설명
	JPanel p_can;	//프로필사진
	JPanel p_la;		//라벨들 붙을곳
	JPanel p_bt;		//버튼 모을곳
	
	JLabel la_name;
	JLabel la_t_uid;
	JLabel la_email;
	JLabel la_phone;
	
	JButton bt_return;
	JButton bt_add;
	
	private TalkMember vo;
	private Image img;
	
	public MemberDetail(TalkMain talkMain) {
		super(talkMain);
		
		p_content = new JPanel();
		p_can = new JPanel() {
			public void paint(Graphics g) {
				g.drawImage(img, 20, 0, p_can);
			}
		};
		
		p_la = new JPanel();
		la_name = new JLabel();
		la_t_uid = new JLabel();
		la_email = new JLabel();
		la_phone = new JLabel();
		
		p_bt = new JPanel();
		bt_return = new JButton("이전으로");
		bt_add = new JButton("친구 추가");
		
		//스타일
		p_content.setPreferredSize(new Dimension(TalkMain.WIDTH, TalkMain.HEIGHT-300));
		//p_la.setPreferredSize(new Dimension(400, TalkMain.HEIGHT-200));
		
		Dimension d = new Dimension((TalkMain.WIDTH)/3, 30);
		la_name.setPreferredSize(d);
		la_t_uid.setPreferredSize(d);
		la_email.setPreferredSize(d);
		la_phone.setPreferredSize(d);
		la_name.setFont(new Font("고딕", Font.BOLD, 20));
		la_t_uid.setFont(new Font("고딕", Font.BOLD, 23));
		la_email.setFont(new Font("고딕", Font.LAYOUT_NO_LIMIT_CONTEXT, 23));
		la_phone.setFont(new Font("고딕", Font.BOLD, 21));
		
		bt_return.setPreferredSize(new Dimension((TalkMain.WIDTH)/3/2, 30));
		bt_add.setPreferredSize(new Dimension((TalkMain.WIDTH)/3/2, 30));
		bt_return.setBackground(Color.BLACK);
		bt_add.setBackground(Color.BLACK);
		bt_return.setFont(new Font("고딕", Font.CENTER_BASELINE, 13));
		bt_add.setFont(new Font("고딕", Font.CENTER_BASELINE, 13));
		bt_return.setForeground(Color.WHITE);
		bt_add.setForeground(Color.WHITE);
		
		p_content.setLayout(new GridLayout(1,2));
		p_la.setLayout(new GridLayout(5,1));
		
		p_la.add(la_name);
		p_la.add(la_t_uid);
		p_la.add(la_phone);
		p_la.add(la_email);
		p_bt.add(bt_return);
		p_bt.add(bt_add);
		
		p_content.add(p_can);
		p_content.add(p_la);
		
		add(p_content);
		add(p_bt);
		
		bt_return.addActionListener((e)->{
			getTalkMain().showPage(TalkMain.HOME);
		});
		
		bt_add.addActionListener((e)->{
			if(JOptionPane.showConfirmDialog(MemberDetail.this, "친구 추가 하시겠습니까?")==JOptionPane.OK_OPTION) {
				addMember();
				getTalkMain().showPage(TalkMain.HOME);
			}
		});
		
	}
	
	public void init(TalkMember vo, Image img) {
		this.vo = vo;
		
		la_name.setText("Name : "+vo.getName());
		la_t_uid.setText("ID : "+vo.getT_uid());
		la_email.setText(vo.getEmail());
		la_phone.setText("Phone : "+vo.getPhone());
		
		this.img = img;
		this.img = this.img.getScaledInstance(270, 370, Image.SCALE_SMOOTH);
		this.updateUI();
		
	}
	
	//친구추가
	public void addMember() {
		Home homePage = (Home)getTalkMain().getPage()[TalkMain.HOME];
		TalkMember memberVO = new TalkMember();	//empty vo
		memberVO.setMember_id(vo.getMember_id());
		memberVO.setName(vo.getName());
		memberVO.setEmail(vo.getEmail());
		memberVO.setFilename(vo.getFilename());
		
		homePage.addMember(memberVO);
		homePage.getMemberList();
	}

	public TalkMember getVo() {
		return vo;
	}

	public void setVo(TalkMember vo) {
		this.vo = vo;
	}

	public Image getImg() {
		return img;
	}

	public void setImg(Image img) {
		this.img = img;
	}
	
}

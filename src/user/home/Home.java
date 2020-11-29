package user.home;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import common.image.ImageUtil;
import user.cart.CartItem;
import user.chat.ChatClient;
import user.chat.ChatServer;
import user.main.Page;
import user.main.TalkMain;
import user.member.MemberDetail;
import user.member.MyPage;
import user.member.TalkMember;

public class Home extends Page{
	JPanel p_north;		//친구추가 버튼 올 영역
	JPanel p_content;	//친구목록 올 영역 (멤버리스트를 담게될영역)
	JTextField t_find;
	JButton bt_find;
	TalkMember talkMember;
	public String nick;
	
	ChatServer chatServer;
	ChatClient chatClient;
	
	HashMap<Integer, TalkMember> memberList;
	
	public Home(TalkMain talkMain) {
		super(talkMain);
		
		memberList = new HashMap<Integer, TalkMember>();
		
		//생성
		p_north = new JPanel();
		t_find = new JTextField(15);
		bt_find = new JButton("친구 검색");
		
		//스타일
		p_north.setBackground(Color.WHITE);
		t_find.setPreferredSize(new Dimension(150, 35));
		t_find.setFont(new Font("고딕", Font.CENTER_BASELINE, 15));
		bt_find.setPreferredSize(new Dimension(100, 35));
		bt_find.setBackground(Color.WHITE);
		bt_find.setFont(new Font("돋움", Font.BOLD, 15));
		p_north.setPreferredSize(new Dimension(TalkMain.WIDTH, 50));
		
		//조립
		setLayout(new BorderLayout());
		
		p_north.add(t_find);
		p_north.add(bt_find);
		
		add(p_north, BorderLayout.NORTH);
			
		bt_find.addActionListener((e)->{
			TalkMember obj= findMember(talkMember);
			
			if(obj==null) {
				JOptionPane.showMessageDialog(this, "해당 정보의 친구가 존재하지 않습니다");
			}else {
				//JOptionPane.showMessageDialog(this, "상세정보 보여줄게");
				MemberDetail memberDetail = (MemberDetail)getTalkMain().getPage()[TalkMain.MEMBERDETAIL];
				memberDetail.init(obj, ImageUtil.getImageFromURL(obj.getFilename()));
				t_find.setText("");
				getTalkMain().showPage(TalkMain.MEMBERDETAIL);
			}
		});		
	}
	
	public TalkMember findMember(TalkMember talkMember) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		TalkMember vo = null;
		
		String sql = "select * from member";
		sql += " where t_uid=?";
		
		try {
			pstmt = getTalkMain().getCon().prepareStatement(sql);
			pstmt.setString(1, t_find.getText());
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				vo = new TalkMember();	//회원이 존재할때는 vo 생성
				//name, t_uid, pass, email, phone, filename
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
	
	//친구목록에 추가
	public void addMember(TalkMember memberVO) {
		memberList.put(memberVO.getMember_id(), memberVO);
	}
	
	//친구삭제
	public void removeMember(int member_id) {
		memberList.remove(member_id);
	}
	
	//추가한 친구 패널에 넣기
	public void getMemberList() {
		Set<Integer> set = memberList.keySet();
		
		Iterator<Integer> it = set.iterator();
		
		int count = 0;
		if(p_content !=null) {
			this.remove(p_content);
			this.revalidate();
			this.updateUI();
			this.repaint();
		}
		
		//동적생성
		p_content = new JPanel();
		p_content.setPreferredSize(new Dimension(TalkMain.WIDTH, TalkMain.HEIGHT-100));	
		
		while(it.hasNext()) {
			int key = it.next();
			//System.out.println("key :" +key);
			TalkMember memberVO = memberList.get(key);
			
			CartItem item = new CartItem(memberVO);
			
			//친구삭제
			item.bt_del.addActionListener((e)->{
				if(JOptionPane.showConfirmDialog(Home.this, "친구목록에서 삭제하시겠습니까?")==JOptionPane.OK_OPTION) {
					removeMember(memberVO.getMember_id());
					getMemberList();
				}		
			});
			
			//채팅하기
			item.bt_chat.addActionListener((e)->{
				MyPage myPage = (MyPage)getTalkMain().getPage()[TalkMain.MYPAGE];
				nick = myPage.getVo().getName();
				//System.out.println(nick);
				chatServer = new ChatServer();
				chatClient = new ChatClient(nick);
			});		
			p_content.add(item);
			count++;
		}
		add(p_content);
		this.updateUI();
	}
	
}

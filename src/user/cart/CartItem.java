package user.cart;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import common.image.ImageUtil;
import user.home.Home;
import user.main.TalkMain;
import user.member.TalkMember;

public class CartItem extends JPanel{
	JPanel p_can;	//프로필이미지
	JPanel p_info;	//이름, 이메일 올곳
	JLabel la_name;
	JLabel la_email;
	public JButton bt_del;
	public JButton bt_chat;
	Image image;
	
	public CartItem(TalkMember talkMember) {
		image = ImageUtil.getCustomSize(ImageUtil.getImageFromURL(talkMember.getFilename()), 85, 100);
		
		p_can = new JPanel() {
			public void paint(Graphics g) {
				g.drawImage(image, 0, 0, p_can);
			}
		};
		
		p_info = new JPanel();
		la_name = new JLabel(talkMember.getName());
		la_email = new JLabel(talkMember.getEmail());
		bt_del = new JButton("친구삭제");
		bt_chat = new JButton("채팅하기");
		
		//스타일
		this.setPreferredSize(new Dimension(500, 115));
		this.setBackground(Color.WHITE);
		p_can.setPreferredSize(new Dimension(85, 100));
		p_info.setPreferredSize(new Dimension(210, 100));
		p_info.setBackground(Color.WHITE);
		la_name.setPreferredSize(new Dimension(150,40));
		la_email.setPreferredSize(new Dimension(210,40));
		la_name.setFont(new Font("고딕", Font.BOLD, 20));
		la_email.setFont(new Font("고딕", Font.LAYOUT_NO_LIMIT_CONTEXT, 17));		
		bt_del.setBackground(Color.BLACK);
		bt_chat.setBackground(Color.BLACK);
		bt_del.setFont(new Font("고딕", Font.CENTER_BASELINE, 13));
		bt_chat.setFont(new Font("고딕", Font.CENTER_BASELINE, 13));
		bt_del.setForeground(Color.WHITE);
		bt_chat.setForeground(Color.WHITE);
		
		//조립
		this.add(p_can);
		p_info.add(la_name);
		p_info.add(la_email);
		this.add(p_info);
		this.add(bt_del);
		this.add(bt_chat);
		
		p_can.repaint();	
	}	
	
}
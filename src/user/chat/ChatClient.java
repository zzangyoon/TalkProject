package user.chat;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import user.home.Home;

public class ChatClient extends JFrame{
	JTextArea area;
	JScrollPane scroll;
	JPanel p_south;
	JTextField t_msg;
	JButton bt_send;
	String nickname;
	Socket socket;
	
	ClientThread clientThread;
	
	public ChatClient(String nick) {
		this.nickname = nick;
		setTitle(nick+"님의 TalkTalk");
		nickname = JOptionPane.showInputDialog("닉네임 입력", nick);
		area = new JTextArea();
		scroll = new JScrollPane(area);
		p_south = new JPanel();
		t_msg = new JTextField(15);
		bt_send = new JButton("전송");
		
		//스타일
		area.setFont(new Font("고딕", Font.PLAIN, 15));
		area.setBackground(Color.WHITE);
		p_south.setBackground(Color.WHITE);
		t_msg.setPreferredSize(new Dimension(150, 40));
		t_msg.setFont(new Font("고딕", Font.PLAIN, 15));
		bt_send.setPreferredSize(new Dimension(100, 40));
		bt_send.setBackground(Color.DARK_GRAY);
		bt_send.setForeground(Color.WHITE);
		
		//조립
		p_south.add(t_msg);
		p_south.add(bt_send);
		
		add(scroll);
		add(p_south, BorderLayout.SOUTH);
		
		connect();
		
		bt_send.addActionListener((e)->{
			clientThread.send(nickname, t_msg.getText());
			t_msg.setText("");
		});
		
		t_msg.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER) {
					clientThread.send(nickname, t_msg.getText());
					t_msg.setText("");
				}
			}
		});
		
		setVisible(true);
		setBounds(700, 300, 400, 500);
	}
	
	public void connect() {
		try {
			socket = new Socket("localhost", 9797);
			area.append("대화를 시작할 수 있습니다\n");
			
			clientThread = new ClientThread(this, socket);
			clientThread.start();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	/*
	public static void main(String[] args) {
		new ChatClient();
	}
	*/
}

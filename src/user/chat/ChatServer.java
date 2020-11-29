package user.chat;

import java.awt.BorderLayout;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ChatServer extends JFrame{
	JTextField t_port;
	JPanel p_north;
	JTextArea area;
	JScrollPane scroll;
	
	ServerSocket server;
	int port = 9797;
	
	Thread thread;
	
	Vector<ChatThread> clientList = new Vector<ChatThread>();
	
	public ChatServer() {
		t_port = new JTextField(Integer.toString(port), 10);
		p_north = new JPanel();
		area = new JTextArea();
		scroll = new JScrollPane(area);
		
		p_north.add(t_port);
		add(p_north, BorderLayout.NORTH);
		add(scroll);
		
		thread = new Thread() {
			public void run() {
				startServer();
			}
		};
		thread.start();
		
		setVisible(false);
		setSize(350,500);
		
	}
	
	//서버가동
	public void startServer() {
		try {
			server = new ServerSocket(9797);
			area.append("서버 준비\n");
			
			while(true) {
				Socket socket = server.accept();
				area.append("접속자 발견\n");
				
				//대화용 쓰레드
				ChatThread chatThread = new ChatThread(this, socket);
				chatThread.start();
				
				clientList.add(chatThread);				
			}			
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		new ChatServer();
	}
}

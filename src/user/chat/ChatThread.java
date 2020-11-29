package user.chat;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class ChatThread extends Thread{
	Socket socket;
	BufferedReader buffr;
	BufferedWriter buffw;
	ChatServer chatServer;
	boolean flag = true;
	
	public ChatThread(ChatServer chatServer, Socket socket) {
		this.chatServer = chatServer;
		this.socket = socket;
		
		try {
			buffr = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			buffw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	public void run() {
		listen();
	}
	
	public void listen() {
		String msg = null;
		
		while(flag) {
			try {
				msg = buffr.readLine();
				
				if(msg.equals("exit")) {
					chatServer.clientList.remove(this);
					flag=false;
				}else {
					chatServer.area.append(msg+"\n");
					send(msg);
				}			
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void send(String msg) {
			try {
				for(int i=0; i<chatServer.clientList.size(); i++) {
					ChatThread chatThread = chatServer.clientList.get(i);
					chatThread.buffw.write(msg+"\n");
					chatThread.buffw.flush();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	
}

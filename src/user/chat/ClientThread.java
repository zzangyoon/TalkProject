package user.chat;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class ClientThread extends Thread{
	ChatClient chatClient;
	Socket socket;
	BufferedReader buffr;
	BufferedWriter buffw;
	boolean flag = true;
	
	public ClientThread(ChatClient chatClient, Socket socket) {
		this.chatClient = chatClient;
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
			try {
				while(flag) {
					msg = buffr.readLine();
					chatClient.area.append(msg+"\n");
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	
	public void send(String nick, String msg) {
		try {
			buffw.write(nick+" : "+msg+"\n");
			buffw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

package application;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Client {
	
	Socket socket;
	public Client(Socket socket) {
		this.socket = socket;
		receiveMsg();
	}
	
	// 의료상담 내용 전송 
		public void sendMsg(String msg) {
			Runnable thread = new Runnable() {
				@Override
				public void run() {
					try {
						OutputStream out = socket.getOutputStream();
						byte[] buffer = msg.getBytes("UTF-8");
						out.write(buffer);
						out.flush();
					}catch (Exception e) {
						try {
							System.out.println("<의료상담 메세지 전달 실패>" + socket.getRemoteSocketAddress() + " => " + Thread.currentThread().getName());
							Main.clients.remove(Client.this);
							socket.close();
						 } catch (Exception e2) {
						 	e2.printStackTrace();
						 }
					 }
				}
			};
			Main.threadPool.submit(thread);
		}
		
	// 의료상담 내용 수신 
	public void receiveMsg() {
		Runnable thread = new Runnable() {
			@Override
			public void run() {
				try {
					while(true) {
						InputStream in = socket.getInputStream();
						byte[] buf = new byte[400];	
						int len = in.read(buf);
						while(len == -1) throw new IOException();
						System.out.println("<의료상담 메세지 도착 성공>" + socket.getRemoteSocketAddress() + " => " + Thread.currentThread().getName());
						String msg = new String(buf, 0, len, "UTF-8"); 
						for(Client clients : Main.clients) {
							clients.sendMsg(msg);							
						}
					}
					
				}catch(Exception e) {
					try {
						System.out.println("<의료상담 메세지 도착 실패>" + socket.getRemoteSocketAddress() + " => " + Thread.currentThread().getName());
					} catch (Exception e2) {
						e2.printStackTrace();
					}
				}
			}
		};
		Main.threadPool.submit(thread);			
	}	
}

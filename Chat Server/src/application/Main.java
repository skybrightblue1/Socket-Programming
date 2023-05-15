package application;
	
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Iterator;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;


public class Main extends Application {
	
	public static ExecutorService threadPool;
	public static Vector<Client> clients = new Vector<Client>();
	ServerSocket serverSocket;
	
	// 서버 on, 클라이언트 접속 관련 
	public void serverOn(String IPaddress, int port) {
		try {
			serverSocket = new ServerSocket();
			serverSocket.bind(new InetSocketAddress(IPaddress, port));
		} catch (Exception e) {
			e.printStackTrace();
			if(!serverSocket.isClosed()) {
				serverOff();
			}
			return;
		}
		
		// 클라이언트들이 들어오길 기다리는 내용 
		Runnable thread = new Runnable() {			
			@Override
			public void run() {
				while (true) {
					try {
						Socket socketServer = serverSocket.accept();
						clients.add(new Client(socketServer));
						System.out.println("<더바른 치과 Medical Talk 사용중>" + " " + socketServer.getRemoteSocketAddress()+ " => " + Thread.currentThread().getName());
					} catch (Exception e) {
						if(!serverSocket.isClosed())
							serverOff();
						break;
					}
			     }
		     }	
		};
		threadPool = Executors.newCachedThreadPool();
		threadPool.submit(thread);
	}
	
	// 치과 서버 프로그램 동작 + 사용자 인터페이스 
	@Override
	public void start(Stage primaryStage) {
		  primaryStage.setTitle("더바른 치과 Medical 서버");
		
		  Group root = new Group();
		  Scene scene = new Scene(root, 600, 400, Color.DARKTURQUOISE);
		
		  BorderPane bd = new BorderPane();
		  bd.setLayoutX(100);
		  bd.setLayoutY(50);
		  bd.setPrefSize(400, 300);
	      bd.setPadding(new javafx.geometry.Insets(20)); 
	      TextArea textArea = new TextArea();
	      textArea.setEditable(false);
	      textArea.setFont(new Font("맑은 고딕", 18));
	      bd.setCenter(textArea);    
	      Button toggleButton = new Button("더바른 치과 서버 접속");
	      toggleButton.setMaxWidth(Double.MAX_VALUE);
	      BorderPane.setMargin(toggleButton, new javafx.geometry.Insets(2, 2, 2, 2));
	      bd.setBottom(toggleButton);      
	       String IPaddress = "192.168.0.2";
	       int port = 8765;
	       
	       toggleButton.setOnAction(event -> {
	          if (toggleButton.getText().equals("더바른 치과 서버 접속")) {
	             serverOn(IPaddress, port);
	             Platform.runLater(() -> {
	                String msg = String.format("<더바른 치과 서버를 시작합니다>\n", IPaddress, port);
	                textArea.appendText(msg);
	                toggleButton.setText("더바른 치과 서버 종료");
	             });
	          }else {
	             serverOff();
	             Platform.runLater(() -> {
	                String msg = String.format("<더바른 치과 서버를 종료합니다>\n\n", IPaddress, port);
	                textArea.appendText(msg);
	                toggleButton.setText("더바른 치과 서버 접속");
	             });
	          }
	       });       
	       root.getChildren().add(bd);
	       primaryStage.setOnCloseRequest(event -> serverOff());
	       primaryStage.setScene(scene);
	       primaryStage.show();
	}
	    			
	// 서버 off
	public void serverOff() {
		try {
			Iterator<Client> iterator = clients.iterator();
			while(iterator.hasNext()) {
				Client clients = iterator.next();
				clients.socket.close();
				iterator.remove();
			}
			if(serverSocket != null && !serverSocket.isClosed()) {
				serverSocket.close();
			}
			if (threadPool != null && !threadPool.isShutdown()) {
				threadPool.shutdown();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}

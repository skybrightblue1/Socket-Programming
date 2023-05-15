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
	
	// ���� on, Ŭ���̾�Ʈ ���� ���� 
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
		
		// Ŭ���̾�Ʈ���� ������ ��ٸ��� ���� 
		Runnable thread = new Runnable() {			
			@Override
			public void run() {
				while (true) {
					try {
						Socket socketServer = serverSocket.accept();
						clients.add(new Client(socketServer));
						System.out.println("<���ٸ� ġ�� Medical Talk �����>" + " " + socketServer.getRemoteSocketAddress()+ " => " + Thread.currentThread().getName());
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
	
	// ġ�� ���� ���α׷� ���� + ����� �������̽� 
	@Override
	public void start(Stage primaryStage) {
		  primaryStage.setTitle("���ٸ� ġ�� Medical ����");
		
		  Group root = new Group();
		  Scene scene = new Scene(root, 600, 400, Color.DARKTURQUOISE);
		
		  BorderPane bd = new BorderPane();
		  bd.setLayoutX(100);
		  bd.setLayoutY(50);
		  bd.setPrefSize(400, 300);
	      bd.setPadding(new javafx.geometry.Insets(20)); 
	      TextArea textArea = new TextArea();
	      textArea.setEditable(false);
	      textArea.setFont(new Font("���� ���", 18));
	      bd.setCenter(textArea);    
	      Button toggleButton = new Button("���ٸ� ġ�� ���� ����");
	      toggleButton.setMaxWidth(Double.MAX_VALUE);
	      BorderPane.setMargin(toggleButton, new javafx.geometry.Insets(2, 2, 2, 2));
	      bd.setBottom(toggleButton);      
	       String IPaddress = "192.168.0.2";
	       int port = 8765;
	       
	       toggleButton.setOnAction(event -> {
	          if (toggleButton.getText().equals("���ٸ� ġ�� ���� ����")) {
	             serverOn(IPaddress, port);
	             Platform.runLater(() -> {
	                String msg = String.format("<���ٸ� ġ�� ������ �����մϴ�>\n", IPaddress, port);
	                textArea.appendText(msg);
	                toggleButton.setText("���ٸ� ġ�� ���� ����");
	             });
	          }else {
	             serverOff();
	             Platform.runLater(() -> {
	                String msg = String.format("<���ٸ� ġ�� ������ �����մϴ�>\n\n", IPaddress, port);
	                textArea.appendText(msg);
	                toggleButton.setText("���ٸ� ġ�� ���� ����");
	             });
	          }
	       });       
	       root.getChildren().add(bd);
	       primaryStage.setOnCloseRequest(event -> serverOff());
	       primaryStage.setScene(scene);
	       primaryStage.show();
	}
	    			
	// ���� off
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

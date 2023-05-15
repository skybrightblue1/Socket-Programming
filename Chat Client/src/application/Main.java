package application;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {

   Socket socket;
   TextArea textArea;

   // Ŭ���̾�Ʈ ����
   public void clientOn(String IPaddress, int port) {
      Thread thread = new Thread() {
         public void run() {
            try {
               socket = new Socket(IPaddress, port);
               receiveMsg();
            } catch (Exception e) {
               if (!socket.isClosed()) {
                  clientOff();
                  System.out.println("<���� ���� ����>");
                  Platform.exit();
               }
            }
         }
      };
      thread.start();
   }

   // Ŭ���̾�Ʈ ����
   public void clientOff() {
      try {
         if (socket != null && !socket.isClosed()) {
            socket.close();
         }

      } catch (Exception e) {
         e.printStackTrace();
      }
   }

   // �޽��� ������
   public void sendMsg(String msg) {
      Thread thread = new Thread() {
         public void run() {
            try {
               OutputStream out = socket.getOutputStream();
               byte[] buf = msg.getBytes("UTF-8");
               out.write(buf);
               out.flush();
            } catch (Exception e) {
               clientOff();
            }
         }
      };
      thread.start();
   }

   // �޽��� �ޱ�
   public void receiveMsg() {
      while (true) {
         try {
            InputStream in = socket.getInputStream();
            byte[] buf = new byte[512];
            int leng = in.read(buf);
            if (leng == -1)
               throw new IOException();
            String msg = new String(buf, 0, leng, "UTF-8");
            Platform.runLater(() -> {
               textArea.appendText(msg);
            });

         } catch (Exception e) {
            clientOff();
            break;
         }
      }
   }

   // Ŭ���̾�Ʈ GUI�׸���+���α׷� ����
   @Override
   public void start(Stage primaryStage) {

      BorderPane root = new BorderPane();

      root.setPadding(new Insets(5));

      HBox hbox = new HBox();
      hbox.setSpacing(5);

      TextField userName = new TextField();
      userName.setPrefWidth(150);
      userName.setPromptText("������� �Է�");
      HBox.setHgrow(userName, Priority.ALWAYS);

      String name = name();

      TextField IPText = new TextField("211.205.151.190");
      TextField portText = new TextField("������ �̸�: " + name);
      portText.setPrefWidth(130);

      hbox.getChildren().addAll(userName, portText);
      root.setTop(hbox);

      textArea = new TextArea();
      textArea.setEditable(false);
      root.setCenter(textArea);

      TextField input = new TextField();
      input.setPrefWidth(Double.MAX_VALUE);
      input.setDisable(true);

      input.setOnAction(event -> {
         sendMsg(userName.getText() + ": " + input.getText() + "\n");
         input.setText("");
         input.requestFocus();
      });

      // ��� �ϴû� �̹���
      Image image = new Image("https://i.pinimg.com/originals/fe/7d/8d/fe7d8d116c6095edc0f67bec7121a511.png");
      root.setBackground(new Background(new BackgroundImage(image, BackgroundRepeat.NO_REPEAT,
            BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT)));

      Button sendButton = new Button("����");
      sendButton.setDisable(true);

      sendButton.setOnAction(event -> {
         sendMsg(userName.getText() + ": " + input.getText() + " \n");
         input.setText("");
         input.requestFocus();
      });

      Button connectionButton = new Button("������");
      connectionButton.setOnAction(event -> {
         if (connectionButton.getText().equals("������")) {
            int port = 8765;
            try {
               port = 8765;
            } catch (Exception e) {
               e.printStackTrace();
            }
            clientOn("211.205.151.190", port);
            Platform.runLater(() -> {
               textArea.appendText("<������>\n");
            });
            connectionButton.setText("�������");
            input.setDisable(false);
            sendButton.setDisable(false);
            input.requestFocus();
         } else {
            clientOff();
            Platform.runLater(() -> {
               textArea.appendText("<�������>\n");
            });
            connectionButton.setText("������");
            input.setDisable(true);
            sendButton.setDisable(true);

         }
      });

      BorderPane bd = new BorderPane();
      bd.setLeft(connectionButton);
      bd.setCenter(input);
      bd.setRight(sendButton);
      bd.setLayoutX(200);
      bd.setLayoutY(200);

      root.setBottom(bd);

      Scene scene = new Scene(root, 350, 600, Color.BEIGE);
      primaryStage.setTitle("<���ٸ� ġ�� Medical Talk>");
      primaryStage.setScene(scene);

      primaryStage.setOnCloseRequest(event -> clientOff());
      primaryStage.show();

      connectionButton.requestFocus();

   }

   public static String nName() {
      List<String> �� = Arrays.asList("��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "Ȳ",
            "��", "��", "��", "��", "ȫ", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��",
            "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "ä", "��", "õ", "��", "��", "��", "��", "��",
            "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "ǥ", "��",
            "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "Ź", "��", "��", "��", "��", "��", "��", "��",
            "��");
      List<String> �̸� = Arrays.asList("��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��",
            "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��",
            "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��",
            "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��",
            "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��",
            "��", "��", "��", "��", "��", "��", "��", "��", "��", "â", "ä", "õ", "ö", "��", "��", "��", "ġ", "Ž", "��", "��", "��",
            "��", "��", "��", "��", "��", "��", "��", "ȣ", "ȫ", "ȭ", "ȯ", "ȸ", "ȿ", "��", "��", "��", "��", "��", "��", "��", "��",
            "��", "ȥ", "Ȳ", "��", "��", "��", "��", "��", "��", "��", "Ź", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��",
            "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "Ÿ", "��", "��", "��", "��", "��", "��",
            "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��",
            "��", "��", "��", "��", "��", "��", "��", "��");
      Collections.shuffle(��);
      Collections.shuffle(�̸�);
      return ��.get(0) + �̸�.get(0) + �̸�.get(1);
   }

   public static String name() {
      String name = "";
      for (int i = 0; i < 500; i++) {
         if (i % 10 == 0) {
            System.err.println();
         }
         name = nName() + " ";
      }
      return name;
   }

   // ���α׷��� ������
   public static void main(String[] args) {
      launch(args);

   }
}

   //�����ϰ� ������ �̸� ����
class IDNEW {
   public static String nName() {
      List<String> �� = Arrays.asList("��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "Ȳ",
            "��", "��", "��", "��", "ȫ", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��",
            "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "ä", "��", "õ", "��", "��", "��", "��",
            "��");
      List<String> �̸� = Arrays.asList("��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��",
            "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��",
            "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��",
            "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��");
      Collections.shuffle(��);
      Collections.shuffle(�̸�);
      return ��.get(0) + �̸�.get(0) + �̸�.get(1);
   }
}
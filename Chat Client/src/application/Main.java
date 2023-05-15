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

   // 클라이언트 시작
   public void clientOn(String IPaddress, int port) {
      Thread thread = new Thread() {
         public void run() {
            try {
               socket = new Socket(IPaddress, port);
               receiveMsg();
            } catch (Exception e) {
               if (!socket.isClosed()) {
                  clientOff();
                  System.out.println("<서버 접속 실패>");
                  Platform.exit();
               }
            }
         }
      };
      thread.start();
   }

   // 클라이언트 중지
   public void clientOff() {
      try {
         if (socket != null && !socket.isClosed()) {
            socket.close();
         }

      } catch (Exception e) {
         e.printStackTrace();
      }
   }

   // 메시지 보내기
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

   // 메시지 받기
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

   // 클라이언트 GUI그리기+프로그램 시작
   @Override
   public void start(Stage primaryStage) {

      BorderPane root = new BorderPane();

      root.setPadding(new Insets(5));

      HBox hbox = new HBox();
      hbox.setSpacing(5);

      TextField userName = new TextField();
      userName.setPrefWidth(150);
      userName.setPromptText("진료과목 입력");
      HBox.setHgrow(userName, Priority.ALWAYS);

      String name = name();

      TextField IPText = new TextField("211.205.151.190");
      TextField portText = new TextField("전문의 이름: " + name);
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

      // 배경 하늘색 이미지
      Image image = new Image("https://i.pinimg.com/originals/fe/7d/8d/fe7d8d116c6095edc0f67bec7121a511.png");
      root.setBackground(new Background(new BackgroundImage(image, BackgroundRepeat.NO_REPEAT,
            BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT)));

      Button sendButton = new Button("전송");
      sendButton.setDisable(true);

      sendButton.setOnAction(event -> {
         sendMsg(userName.getText() + ": " + input.getText() + " \n");
         input.setText("");
         input.requestFocus();
      });

      Button connectionButton = new Button("상담시작");
      connectionButton.setOnAction(event -> {
         if (connectionButton.getText().equals("상담시작")) {
            int port = 8765;
            try {
               port = 8765;
            } catch (Exception e) {
               e.printStackTrace();
            }
            clientOn("211.205.151.190", port);
            Platform.runLater(() -> {
               textArea.appendText("<상담시작>\n");
            });
            connectionButton.setText("상담종료");
            input.setDisable(false);
            sendButton.setDisable(false);
            input.requestFocus();
         } else {
            clientOff();
            Platform.runLater(() -> {
               textArea.appendText("<상담종료>\n");
            });
            connectionButton.setText("상담시작");
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
      primaryStage.setTitle("<더바른 치과 Medical Talk>");
      primaryStage.setScene(scene);

      primaryStage.setOnCloseRequest(event -> clientOff());
      primaryStage.show();

      connectionButton.requestFocus();

   }

   public static String nName() {
      List<String> 성 = Arrays.asList("김", "이", "박", "최", "정", "강", "조", "윤", "장", "임", "한", "오", "서", "신", "권", "황",
            "안", "송", "류", "전", "홍", "고", "문", "양", "손", "배", "조", "백", "허", "유", "남", "심", "노", "정", "하", "곽", "성",
            "차", "주", "우", "구", "신", "임", "나", "전", "민", "유", "진", "지", "엄", "채", "원", "천", "방", "공", "강", "현", "함",
            "변", "염", "양", "변", "여", "추", "노", "도", "소", "신", "석", "선", "설", "마", "길", "주", "연", "방", "위", "표", "명",
            "기", "반", "왕", "금", "옥", "육", "인", "맹", "제", "모", "장", "남", "탁", "국", "여", "진", "어", "은", "편", "구",
            "용");
      List<String> 이름 = Arrays.asList("가", "강", "건", "경", "고", "관", "광", "구", "규", "근", "기", "길", "나", "남", "노", "누",
            "다", "단", "달", "담", "대", "덕", "도", "동", "두", "라", "래", "로", "루", "리", "마", "만", "명", "무", "문", "미", "민",
            "바", "박", "백", "범", "별", "병", "보", "빛", "사", "산", "상", "새", "서", "석", "선", "설", "섭", "성", "세", "소", "솔",
            "수", "숙", "순", "숭", "슬", "승", "시", "신", "아", "안", "애", "엄", "여", "연", "영", "예", "오", "옥", "완", "요", "용",
            "우", "원", "월", "위", "유", "윤", "율", "으", "은", "의", "이", "익", "인", "일", "잎", "자", "잔", "장", "재", "전", "정",
            "제", "조", "종", "주", "준", "중", "지", "진", "찬", "창", "채", "천", "철", "초", "춘", "충", "치", "탐", "태", "택", "판",
            "하", "한", "해", "혁", "현", "형", "혜", "호", "홍", "화", "환", "회", "효", "훈", "휘", "희", "운", "모", "배", "부", "림",
            "봉", "혼", "황", "량", "린", "을", "비", "솜", "공", "면", "탁", "온", "디", "항", "후", "려", "균", "묵", "송", "욱", "휴",
            "언", "령", "섬", "들", "견", "추", "걸", "삼", "열", "웅", "분", "변", "양", "출", "타", "흥", "겸", "곤", "번", "식", "란",
            "더", "손", "술", "훔", "반", "빈", "실", "직", "흠", "흔", "악", "람", "뜸", "권", "복", "심", "헌", "엽", "학", "개", "롱",
            "평", "늘", "늬", "랑", "얀", "향", "울", "련");
      Collections.shuffle(성);
      Collections.shuffle(이름);
      return 성.get(0) + 이름.get(0) + 이름.get(1);
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

   // 프로그램의 진입점
   public static void main(String[] args) {
      launch(args);

   }
}

   //랜점하게 진료진 이름 생성
class IDNEW {
   public static String nName() {
      List<String> 성 = Arrays.asList("김", "이", "박", "최", "정", "강", "조", "윤", "장", "임", "한", "오", "서", "신", "권", "황",
            "안", "송", "류", "전", "홍", "고", "문", "양", "손", "배", "조", "백", "허", "유", "남", "심", "노", "정", "하", "곽", "성",
            "차", "주", "우", "구", "신", "임", "나", "전", "민", "유", "진", "지", "엄", "채", "원", "천", "방", "공", "강", "현",
            "용");
      List<String> 이름 = Arrays.asList("가", "강", "건", "경", "고", "관", "광", "구", "규", "근", "기", "길", "나", "남", "노", "누",
            "다", "단", "달", "담", "대", "덕", "도", "마", "만", "명", "무", "문", "미", "민", "바", "박", "백", "범", "별", "병", "보",
            "상", "새", "서", "석", "선", "설", "섭", "성", "세", "소", "솔", "수", "숙", "순", "숭", "슬", "승", "시", "신", "아", "안",
            "애", "엄", "여", "연", "영", "예", "오", "옥", "완", "요", "용");
      Collections.shuffle(성);
      Collections.shuffle(이름);
      return 성.get(0) + 이름.get(0) + 이름.get(1);
   }
}
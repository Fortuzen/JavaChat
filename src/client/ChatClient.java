package client;

import java.net.Socket;
import java.util.Optional;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Popup;
import javafx.stage.Stage;

import shared.ICommunication;
import shared.DefaultCommunication;


public class ChatClient extends Application {
	
	public static ICommunication communication = null;
    public static TextArea taMessages;
	Socket socket;
	
    public void start(Stage primaryStage) {
		
        MenuBar menu = new MenuBar();
        MsgRec rec = new MsgRec();

        Menu menu1 = new Menu("Connect...");
        Menu menu2 = new Menu("Help");
        menu.getMenus().add(menu1);
        menu.getMenus().add(menu2);

        MenuItem menuItem = new MenuItem("Quick connect");
        MenuItem menuItem2 = new MenuItem("Connect to a server");
        MenuItem menuItem3 = new MenuItem("Disconnect");
        menu1.getItems().add(menuItem);
        menu1.getItems().add(menuItem2);
        menu1.getItems().add(menuItem3);
        
        MenuItem menu2Item = new MenuItem("Help");
        MenuItem menu2Item2 = new MenuItem("About");
        menu2.getItems().add(menu2Item);
        menu2.getItems().add(menu2Item2);
        

        taMessages = new TextArea("\nPlease connect to a server to start chatting with other people. Press help for more info.");
        taMessages.setEditable(false);
        taMessages.setWrapText(true);

        TextArea taInput = new TextArea();

        BorderPane border = new BorderPane();
        border.setTop(menu);
        border.setCenter(taMessages);
        border.setBottom(taInput);
        
        Scene mainFrame = new Scene(border, 800,600);

        primaryStage.setTitle("Chat");
        primaryStage.setScene(mainFrame);
        primaryStage.centerOnScreen();
        primaryStage.show();
        
        menuItem.setOnAction(e->{
    		String ip = "localhost";
    		int port = 8000;
            try {
                socket = new Socket(ip, port);
                System.out.println("Connected to " + ip + " " + port);
                communication = new DefaultCommunication(socket);
                rec.start(); // Listen to server
                taMessages.setText("");

            } catch (Exception error) {
            	Alert alert = new Alert(AlertType.INFORMATION);
            	alert.setTitle("Error");
            	alert.setHeaderText(null);
            	alert.setContentText("Could not connect to " + ip + " " + port);
            	alert.showAndWait();
            }
        });
        

        
        menuItem2.setOnAction(e->{
        	TextInputDialog dialog = new TextInputDialog("localhost");
        	dialog.setTitle("Connect to a server");
        	dialog.setHeaderText("Enter server IP address");
        	dialog.setContentText("IP address:");

        	Optional<String> result = dialog.showAndWait();
        	if (result.isPresent()){
        		String ip = result.get();
        		int port = 8000; // TODO: Change to multiple field dialog
                try {
                    socket = new Socket(ip, port);
                    System.out.println("Connected to " + ip + " " + port);
                    communication = new DefaultCommunication(socket);

                    rec.start(); // Listen to server
                    taMessages.setText("");

                } catch (Exception error) {
                	Alert alert = new Alert(AlertType.INFORMATION);
                	alert.setTitle("Error");
                	alert.setHeaderText(null);
                	alert.setContentText("Could not connect to " + ip + " " + port);
                	alert.showAndWait();
                }
        	}
        });
        
        menu2Item.setOnAction(e-> {
        	//TODO: Display helpful stuff
        });
        
        menuItem3.setOnAction(e-> { // Disconnect
        	
        });
        
        menu2Item2.setOnAction(e-> {
        	Alert alert = new Alert(AlertType.INFORMATION);
        	alert.setTitle("About");
        	alert.setHeaderText("The Chat");
        	alert.setContentText("Made by some people. Copyright 2018");

        	alert.showAndWait();
        });

        taInput.setOnKeyPressed(e -> {
            if(!e.getCode().equals(KeyCode.ENTER)) return;
            if(taInput.getText().isEmpty()) return;
            String msg = taInput.getText();   
            try {
                communication.sendMessage(msg); // Send message to server
            } catch (Exception er) {
            	Alert alert = new Alert(AlertType.INFORMATION);
            	alert.setTitle("Error");
            	alert.setHeaderText(null);
            	alert.setContentText("Could not send a message to server");
            	alert.showAndWait();
            } 
            taInput.setText("");
        });
	}
	
	public static void main(String[] args) {
		Application.launch(args);
    }
}
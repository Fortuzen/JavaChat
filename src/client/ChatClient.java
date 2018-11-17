package client;

import java.net.Socket;
import java.util.Optional;

import javafx.application.Application;
import javafx.geometry.Insets;
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
/**
 * ChatClient
 * @author Toni Luukkonen
 */
public class ChatClient extends Application {
	
	public static ICommunication communication = null;
    public static TextArea taMessages;
	Socket socket;
	MsgRec rec;
	
    public void start(Stage primaryStage) {
		
        MenuBar menu = new MenuBar(); // Toolbar

        Menu menu1 = new Menu("Connect..."); // First menu on toolbar
        menu.getMenus().add(menu1);
        Menu menu2 = new Menu("Help"); // Second menu on toolbar
        menu.getMenus().add(menu2);
        
        MenuItem menuItem = new MenuItem("Quick connect");
        MenuItem menuItem2 = new MenuItem("Connect to a server");
        MenuItem menuItem3 = new MenuItem("Disconnect");
        menu1.getItems().add(menuItem);
        menu1.getItems().add(menuItem2);
        menu1.getItems().add(menuItem3);   

//        MenuItem menu2Item = new MenuItem("Help");
        MenuItem menu2Item2 = new MenuItem("About");
//        menu2.getItems().add(menu2Item);
        menu2.getItems().add(menu2Item2);
        
        taMessages = new TextArea("\nPlease connect to a server to start chatting with other people. Press help for more info.");
        taMessages.setEditable(false);
        taMessages.setWrapText(true);

        TextArea taInput = new TextArea();

        BorderPane border = new BorderPane();
        border.setTop(menu);
        border.setCenter(taMessages);
        border.setBottom(taInput);
        BorderPane.setMargin(taInput, new Insets(12,12,12,12));
        
        Scene mainFrame = new Scene(border, 800,600);

        primaryStage.setTitle("Chat");
        primaryStage.setScene(mainFrame);
        primaryStage.centerOnScreen();
        primaryStage.show();
        
        menuItem.setOnAction(e->{ // Quick connect
    		String ip = "localhost";
    		int port = 8000;
            try {
            	rec = new MsgRec();
            	socket = new Socket(ip, port);
                System.out.println("Connected to " + ip + " " + port);
                communication = new DefaultCommunication(socket);
                rec.start();
                communication.sendMessage("Defaultname");
                communication.sendMessage("/joinroom room1 admin"); // TODO: remove later
                taMessages.setText("");

            } catch (Exception error) {
            	Alert alert = new Alert(AlertType.INFORMATION);
            	alert.setTitle("Error");
            	alert.setHeaderText(null);
            	alert.setContentText("Could not connect to " + ip + " " + port);
            	alert.showAndWait();
            }
        });
        

        
        menuItem2.setOnAction(e->{ // Normal connect
        	TextInputDialog dialog = new TextInputDialog("localhost");
        	dialog.setTitle("Connect to a server");
        	dialog.setHeaderText("Enter server IP address");
        	dialog.setContentText("IP address:");

        	Optional<String> result = dialog.showAndWait();
        	if (result.isPresent()){
        		String ip = result.get();
        		int port = 8000; // TODO: Change to multiple field dialog
                try {
                	rec = new MsgRec();
                    socket = new Socket(ip, port);
                    System.out.println("Connected to " + ip + " " + port);
                    communication = new DefaultCommunication(socket);
                    rec.start();
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
        
//        menu2Item.setOnAction(e-> { // Help
//        		communication.sendMessage("/help");   Crashes client if done three times
//        });
        
        menuItem3.setOnAction(e-> { // Disconnect
        	try {
				communication.sendMessage("/quit");
        		socket.close();
        		taMessages.setText("\nPlease connect to a server to start chatting with other people. Press help for more info.");
			} catch (Exception e1) {
			}
        });
        
        menu2Item2.setOnAction(e-> { // About
        	Alert alert = new Alert(AlertType.INFORMATION);
        	alert.setTitle("About");
        	alert.setHeaderText("The Chat");
        	alert.setContentText("Made by some people. Copyright 2018");
        	alert.showAndWait();
        });

        taInput.setOnKeyPressed(e -> { // Input text field
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
    /**
     * Main method.
     */
	public static void main(String[] args) {
		Application.launch(args);
    }
}
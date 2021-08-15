package com.example.gui_caht_0988;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class HelloController {

    Socket socket;
    @FXML
    public HBox input_msg_box;
    @FXML
    Button snd_btn;
    @FXML
    Button cnt_btn;
    @FXML
    TextField textField;
    @FXML
    TextArea textArea;

    @FXML
    private void send() {
        try {
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            String text = textField.getText();
            out.writeUTF(text);
            textField.clear(); // Очищаем поле ввода сообщения
            textField.requestFocus(); // Возвращаем фокусировку на поле ввода
            textArea.appendText("Вы: " + text + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void connect() {
        try {
            socket = new Socket("localhost", 8188);
            DataInputStream in = new DataInputStream(socket.getInputStream());
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        try {
                            String response = in.readUTF();
                            textArea.appendText(response + "\n");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            thread.start();
            cnt_btn.setVisible(false);
            input_msg_box.setDisable(false);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
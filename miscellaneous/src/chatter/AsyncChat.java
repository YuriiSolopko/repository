package chatter;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class AsyncChat extends Application {

    private ServerSocketChannel serverChannel;
    private SocketChannel clientServer;
    private SocketChannel clientChannel;
    private ByteBuffer readBuffer = ByteBuffer.allocate(100);
    private ByteBuffer writeBuffer = ByteBuffer.allocate(100);
    private int readBytes;
    private TextArea messages = new TextArea();
    private int serverPort = 30002;
//    private String clientIP;
    private Label connectionStatus;
    private TextField address;
    private TextField port;
    private Button connectBtn;

    private Task<Void> connectionListener = new Task<Void>() {
        @Override
        protected Void call() throws Exception {
            clientServer = serverChannel.accept();
//            System.out.println(clientServer.getRemoteAddress().toString().substring(1,clientServer.getRemoteAddress().toString().indexOf(':')));
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    connectionStatus.setText("Connected");
                    try {
                        address.setText( clientServer.getRemoteAddress().toString().substring(1,clientServer.getRemoteAddress().toString().indexOf(':')) );
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    address.setDisable(true);
                    port.setText(serverPort + "");
                    port.setDisable(true);
                    connectBtn.setDisable(true);
                }
            });
            Thread readBufferThread = new Thread(readBufferListener);
            readBufferThread.start();
            return null;
        }
    };

    private Task<Void> readBufferListener = new Task<Void>() {
        @Override
        protected Void call() throws Exception {
            SocketChannel sock = null;
            if(clientChannel!= null && clientChannel.isConnected()) {
                sock = clientChannel;
            } else if (clientServer !=null && clientServer.isConnected()){
                sock = clientServer;
            }

            while(true) {
                if(isCancelled()) {
                    break;
                }
                while ( (readBytes = sock.read(readBuffer)) > 0 ) {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            readBuffer.clear();
                            readBuffer.rewind();
                            if (messages.getText().equals("")) {
                                messages.appendText("Incoming: " + new String(readBuffer.array(), 0, readBytes));
                            } else {
                                messages.appendText("\nIncoming: " + new String(readBuffer.array(), 0, readBytes));
                            }
                        }
                    });

                }
                readBuffer.rewind();
                readBuffer.clear();
            }
            return null;
        }
    };

    @Override
    public void start(Stage primaryStage) throws Exception {

        serverChannel = ServerSocketChannel.open();
        clientChannel = SocketChannel.open();
        serverChannel.socket().bind(new InetSocketAddress(serverPort));
        messages.setEditable(false);
        Thread server = new Thread(connectionListener);
        server.start();

        primaryStage.setTitle("Chatter");
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                if (connectionListener.isRunning()) {
                    connectionListener.cancel();
                }
                if (readBufferListener.isRunning()) {
                    readBufferListener.cancel();
                }
            }
        });
        primaryStage.setScene(createScene());
        primaryStage.sizeToScene();
        primaryStage.show();

    }

    private Scene createScene() {
        HBox hboxTOP = new HBox();
        hboxTOP.setPadding(new Insets(10,10,10,10));
        hboxTOP.setSpacing(10);
        hboxTOP.setAlignment(Pos.CENTER_LEFT);
        Label ip = new Label("IP-address:");
        address = new TextField();
        address.setPromptText("255.255.255.255");
        Label portLabel = new Label("Port:");
        port = new TextField();
        port.setPromptText("1024 - 65535");
        port.setMaxWidth(100);
        connectBtn = new Button("Connect");
        connectBtn.setFocusTraversable(false);
        connectBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    clientChannel.connect( new InetSocketAddress( address.getText(), Integer.parseInt(port.getText()) ) );
                    connectionListener.cancel();
                    Thread thread = new Thread(readBufferListener);
                    thread.start();
                    address.setDisable(true);
                    port.setDisable(true);
                    connectBtn.setDisable(true);
                    connectionStatus.setText("Connected");
                } catch (IOException e) {
                    connectionStatus.setText("Connection failed");
                } catch (Exception e) {
                    connectionStatus.setText("Error");
                }
            }
        });
        connectionStatus = new Label("");
        hboxTOP.getChildren().addAll(ip, address, portLabel, port, connectBtn, connectionStatus);

        HBox hboxBottom = new HBox();
        hboxBottom.setPadding(new Insets(10,10,10,10));
        hboxBottom.setSpacing(10);
        TextField message = new TextField();

        message.setMinWidth(500);
        Button sendBtn = new Button("Send");
        sendBtn.setFocusTraversable(false);
        sendBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (clientChannel.isConnected()) {
                    sendMessage(clientChannel, message);
                } else if (clientServer.isConnected()) {
                    sendMessage(clientServer, message);
                }
            }
        });

        hboxBottom.getChildren().addAll(message, sendBtn);

        BorderPane pane = new BorderPane();
        pane.setTop(hboxTOP);
        pane.setBottom(hboxBottom);
        pane.setCenter(messages);

        Scene scene = new Scene(pane);

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    if (clientChannel.isConnected()) {
                        sendMessage(clientChannel, message);
                    } else if (clientServer.isConnected()) {
                        sendMessage(clientServer, message);
                    }
                }
            }
        });

        return scene;
    }

    public void sendMessage(SocketChannel socketChannel, TextField message) {
        if (!message.getText().equals("")) {

            writeBuffer.clear();

            writeBuffer.put(message.getText().getBytes());
            writeBuffer.flip();

            try {
                while (writeBuffer.hasRemaining()) {
                    socketChannel.write(writeBuffer);
                }
                if (messages.getText().equals("")) {
                    messages.appendText("You: " + message.getText());
                } else {
                    messages.appendText("\nYou: " + message.getText());
                }
                message.setText("");
            } catch (IOException e) {
                connectionStatus.setText("Send error");
            }
            writeBuffer.rewind();
            writeBuffer.clear();

        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

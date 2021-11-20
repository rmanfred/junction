package Client;

import Connection.*;

import java.io.IOException;
import java.net.Socket;
import java.util.List;

public class Client {
    private Connection connection;
    public static boolean tokenMSG;
    private static MessageChecker checker;
    private static ModelGuiClient model;
    private static ViewGuiClient gui;
    private boolean filter;
    private volatile boolean isConnect = false;

    public boolean isConnect() {
        return isConnect;
    }

    public void setConnect(boolean connect) {
        isConnect = connect;
    }

    public static void main(String[] args) {
        Client client = new Client();
        model = new ModelGuiClient();
        checker = new MessageChecker();
        gui = new ViewGuiClient(client);
        gui.initFrameClient();
        while (true) {
            if (client.isConnect()) {
                client.nameUserRegistration();
                client.receiveMessageFromServer();
                client.setConnect(false);
            }
        }
    }

    protected void connectToServer() {
        if (!isConnect) {
            while (true) {
                try {
                    String addressServer = gui.getServerAddressFromOptionPane();
                    int port = gui.getPortServerFromOptionPane();
                    //создаем сокет и объект connection
                    Socket socket = new Socket(addressServer, port);
                    connection = new Connection(socket);
                    isConnect = true;
                    gui.addMessage("Service message: You have connected to the server.\n");
                    break;
                } catch (Exception e) {
                    gui.errorDialogWindow("An error has occurred! Perhaps, you entered the wrong server address or port. Please, try again");
                    break;
                }
            }
        } else gui.errorDialogWindow("You are already connected!");
    }

    protected void nameUserRegistration() {
        while (true) {
            try {
                Message message = connection.receive();
                if (message.getTypeMessage() == MessageType.REQUEST_NAME_USER) {
                    String nameUser = gui.getNameUser();
                    connection.send(new Message(MessageType.USER_NAME, nameUser));
                }
                if (message.getTypeMessage() == MessageType.NAME_USED) {
                    gui.errorDialogWindow("This name is already in use, please enter another one");
                    String nameUser = gui.getNameUser();
                    connection.send(new Message(MessageType.USER_NAME, nameUser));
                }
                //если имя принято, получаем множество всех подключившихся пользователей, выходим из цикла
                if (message.getTypeMessage() == MessageType.NAME_ACCEPTED) {
                    gui.addMessage("Service message: your name is accepted!\n");
                    model.setUsers(message.getListUsers());
                    break;
                }
            } catch (Exception e) {
                e.printStackTrace();
                gui.errorDialogWindow("An error occurred while registering the name. Try to reconnect");
                try {
                    connection.close();
                    isConnect = false;
                    break;
                } catch (IOException ex) {
                    gui.errorDialogWindow("Error closing connection");
                }
            }

        }
    }

    protected void sendMessageOnServer(String text) {
        final String newMessage;
        try {
            filter = false;
            checker.fillMap(true);
            if (checker.getFilter()) {
                List<String> message = checker.isMessageBadFilter(text);
                if (message.size() != 0) {
                    checker.fillMap(false);
                    newMessage = checker.replaceWords(message, text);
                    connection.send(new Message(MessageType.TEXT_MESSAGE, newMessage));
                    return;
                }
            } else {
                if (text.length() != 0) {
                    if (checker.isMessageBadNoFilter(text)) {
                        FirstWarningWindow firstWarn = new FirstWarningWindow();
                    } else {
                        connection.send(new Message(MessageType.TEXT_MESSAGE, text));
                    }
                }
            }
        } catch (Exception e) {
            gui.errorDialogWindow("Error sending message");
        }
    }

    protected void receiveMessageFromServer() {
        while (isConnect) {
            try {
                Message message = connection.receive();
                if (message.getTypeMessage() == MessageType.TEXT_MESSAGE) {
                    gui.addMessage(message.getTextMessage());
                }
                if (message.getTypeMessage() == MessageType.USER_ADDED) {
                    model.addUser(message.getTextMessage());
                    gui.refreshListUsers(model.getUsers());
                    gui.addMessage(String.format("Service message: user %s joined chat.\n", message.getTextMessage()));
                }
                if (message.getTypeMessage() == MessageType.REMOVED_USER) {
                    model.removeUser(message.getTextMessage());
                    gui.refreshListUsers(model.getUsers());
                    gui.addMessage(String.format("Service message: user %s left the chat.\n", message.getTextMessage()));
                }
            } catch (Exception e) {
                gui.errorDialogWindow("An error occurred while receiving a message from the server.");
                setConnect(false);
                gui.refreshListUsers(model.getUsers());
                break;
            }
        }
    }

    protected void disableClient() {
        try {
            if (isConnect) {
                connection.send(new Message(MessageType.DISABLE_USER));
                model.getUsers().clear();
                isConnect = false;
                gui.refreshListUsers(model.getUsers());
            } else gui.errorDialogWindow("Вы уже отключены.");
        } catch (Exception e) {
            gui.errorDialogWindow("Сервисное сообщение: произошла ошибка при отключении.");
        }
    }
}

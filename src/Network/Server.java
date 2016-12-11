package Network;
import Tables.Parameters;
import Tables.Taxes;
import Tables.User;
import database.DBWorker;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server implements Runnable {

    private static final String FILE = "client.txt";
    private Socket factSocket;
    private DBWorker db = DBWorker.getInstance();
    private ObjectInputStream inStream;
    private ObjectOutputStream outStream;
    private static String currentUser;


    private String messServ=null;



    Server(Socket socket) throws IOException {
        factSocket = socket;

        try {
            inStream = new ObjectInputStream(factSocket.getInputStream());
            outStream = new ObjectOutputStream(factSocket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        String doing = getAddress() + "\t" + "on" + "\t" + new java.util.Date().toString();
        System.out.println(doing);
        writeInFile(doing);
    }

    public void close() {
        try {
            outStream.flush();
            inStream.close();
            outStream.close();
            factSocket.close();
        } catch (IOException e) {
            //e.printStackTrace();
        }
    }

    private String readMessage() {
        try {
            messServ = (String) inStream.readObject();
        } catch (Exception e) {
            System.err.println("Клиент отключился");

        }
        return messServ;
    }

    private void sendMessage(String messServ) {
        try {
            outStream.flush();
            outStream.writeObject(messServ);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private Object readObject() {
        Object object = null;
        try {
            object = inStream.readObject();
        } catch (Exception e ) {
            System.out.println("Нет данных в потоке");
            //e.printStackTrace();
        }

        return object;
    }



    private void sendObject(Object object) {
        try {
            outStream.flush();
            outStream.writeObject(object);
        } catch (IOException e) {
            //e.printStackTrace();
        }
    }


    private String getAddress() {
        return factSocket.getInetAddress().toString();
    }

    private void writeInFile(String doing) throws IOException {

        try (PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(FILE, true)))) {

            pw.println(doing);

        }
    }

    @Override
    public void run() {
        boolean workFlag=true;
                do {
                    messServ = readMessage();

                    switch (messServ) {

                        case "Shutdown": {
                            try {
                                String doing = getAddress() + "\t" + "off" + "\t" + new java.util.Date().toString();
                                System.out.println(doing);
                                writeInFile(doing);
                                close();
                                System.exit(0);

                            } catch (IOException ex) {
                                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            close();
                            workFlag = false;
                        }
                        break;

                        case "LogIn": {
                            User user = (User) readObject();
                            try {
                                currentUser = user.getLogin();
                            } catch (NullPointerException ex) {
                                System.out.println("User is not found");
                            }
                            try {
                                if (db.logIN(user.getLogin(), user.getPassword())) {
                                    sendMessage("Good");
                                } else sendMessage("Fail");
                            } catch (Exception ex) {
                                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                            }

                        }
                        break;
                        case "SignIn": {
                            User user = (User) readObject();
                            try {
                                if (db.RegisterNewUser(user.getUsername(), user.getUserSurname(), user.getEmail(), user.getLogin(), user.getPassword())) {
                                    sendMessage("Good");
                                } else sendMessage("Bad");
                            } catch (Exception ex) {
                                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                            }

                        }
                        break;
                        case "CarTax": {

                            Parameters parameters = (Parameters) readObject();
                            int totalCost = (int) readObject();

                            try {
                                if (db.insertCarTax(messServ, parameters.getAgeOfCar(), parameters.getEngine(), parameters.getValue(), totalCost)) {
                                    sendMessage("Good");
                                } else sendMessage("Bad");
                            } catch (Exception ex) {
                                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        break;
                        case "GoodsTax": {
                            Parameters parameters = (Parameters) readObject();
                            double goodsTaxValue = (double) readObject();

                            try {
                                if (db.insertGoodsTax(messServ, parameters.getValue(), parameters.getWeight(), goodsTaxValue, parameters.getDate())) {
                                    sendMessage("Good");
                                } else sendMessage("Bad");
                            } catch (Exception ex) {
                                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                            }

                        }
                        break;
                        case "MoneyTax": {
                            Parameters parameters = (Parameters) readObject();
                            try {
                                if (db.insertMoneyTax(messServ, parameters.getValue(), parameters.getDate())) {
                                    sendMessage("Good");
                                } else sendMessage("Bad");
                            } catch (Exception ex) {
                                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        break;
                        case "ShowHistory": {
                            try {
                                ///sendObject(currentUser);
                                sendObject(db.showHistory(currentUser));

                            } catch (Exception ex) {
                                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        break;
                        case "DeleteRecords": {
                            try {
                                if (db.deleteRecord(currentUser)) {
                                    sendMessage("Good");
                                } else sendMessage("bad");
                            } catch (Exception ex) {
                                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        break;
                        case "FindTax": {
                            String findSurname = (String) readObject();
                            try {
                                //sendObject(db.findTax(findSurname));
                            } catch (Exception ex) {
                                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        break;
                        case "MakeProfile": {
                            sendObject(db.userProfileInfo(currentUser));
                        }
                        break;
                        default:close();
                            workFlag = false;
                    }

                } while (workFlag);



    }
}

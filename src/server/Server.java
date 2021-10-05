package server;

import server.classes.Car;
import server.classes.User;

import java.net.InetAddress;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class Server {

    public static ArrayList<Car> carsModify = new ArrayList<>();
    public static ArrayList<User> listOfUsers = new ArrayList<>();
    public static int quantityCars = 0;

    public static ServerFunctions stubServer;


    public static void main(String[] args) {

        System.setProperty("java.rmi.server.hostname", "127.0.0.1");
        try {
            LojaFunctionsImpl refObjetoRemoto = new LojaFunctionsImpl();

            LojaFunctions skeletonServer = (LojaFunctions)
                    UnicastRemoteObject.exportObject(refObjetoRemoto, 0);

            LocateRegistry.createRegistry(20002);

            LocateRegistry.getRegistry(InetAddress.getLocalHost().getHostAddress());

            Registry registro = LocateRegistry.getRegistry(20002);

            registro.bind("LojaServer", skeletonServer);

            System.err.println("Servidor pronto!");

            //DEIXANDO SERVER COMO CLIENTE DE DATASERVER

            Registry registroServer = LocateRegistry.getRegistry("localhost", 20003);

            stubServer = (ServerFunctions) registroServer.lookup("LojaData");

            carsModify = stubServer.getData();
            listOfUsers = stubServer.getUsers();
            quantityCars = carsModify.size();

        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }

    }

    public static void sendChanges(ArrayList<Car> carsTemporary) throws RemoteException {

        carsModify = carsTemporary;
        stubServer.saveData(carsModify);

    }

}

package dataServer;

import server.classes.Car;
import server.ServerFunctions;
import server.ServerFunctionsImpl;
import server.classes.User;

import java.net.InetAddress;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class DataServer {

    public static ArrayList<Car> cars = new ArrayList<>();
    public static ArrayList<User> users = new ArrayList<>();

    public static void main(String[] args) {

        initUsers();
        initCars();

        try {

            ServerFunctionsImpl refObjetoRemoto = new ServerFunctionsImpl();

            ServerFunctions skeletonData = (ServerFunctions)
                    UnicastRemoteObject.exportObject(refObjetoRemoto, 1);

            LocateRegistry.createRegistry(20003);

            LocateRegistry.getRegistry(InetAddress.getLocalHost().getHostAddress());

            Registry registro = LocateRegistry.getRegistry(20003);

            registro.bind("LojaData", skeletonData);

            System.err.println("Servidor de Dados pronto!");

        }catch (Exception e){
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }


    }

    static void initUsers(){
        users.add(new User("Washington", "123456", "Client"));
        users.add(new User("Paulo", "123456", "Employee"));
    }

    static void initCars(){

        //CARROS ECONOMICOS
        cars.add(new Car("1111", "Palio", 2012, 28500));
        cars.add(new Car("2222", "Novo Uno", 2016, 29750));
        cars.add(new Car("3333", "Mobi", 2014, 20500));
        cars.add(new Car("4444", "Onix", 2013, 26500));

        //CARROS INTERMEDIARIO
        cars.add(new Car("5555", "Prisma", 2009, 32500));
        cars.add(new Car("6666", "HB20", 2013, 44500));
        cars.add(new Car("7777", "Etios", 2020, 52800));
        cars.add(new Car("8888", "Logan", 2017, 50000));

        //CARROS EXECUTIVOS
        cars.add(new Car("9999", "Corolla", 2014, 61500));
        cars.add(new Car("1010", "Civic", 2012, 78500));
        cars.add(new Car("0110", "A3", 2020, 82500));
        cars.add(new Car("1212", "Fusion", 2018, 68500));
    }

}

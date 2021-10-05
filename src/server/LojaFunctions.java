package server;

import server.classes.User;
import server.classes.Car;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface LojaFunctions extends Remote{

    //###################### FUNÇÕES CLIENT - SERVER ######################

    User login(String username, String pass) throws RemoteException;
    //String invoice(Car car) throws RemoteException;

    //-------FUNÇÕES EXCLUSIVAS FUNCIONARIO

    String addCar(String renavam, String name, int year, double price) throws RemoteException;
    ArrayList<Car> referenceCars() throws RemoteException;
    String deleteCar(String renavam) throws RemoteException;
    String editCar(String renavamAtual, String renavam, String name, int year, double price) throws RemoteException;

    //-------FUNÇÕES CLIENTE

    Car searchCarRenavam(String renavam) throws RemoteException;
    ArrayList<Car> searchCarName(String name) throws RemoteException;
    int infoQuantity() throws RemoteException;
    String buyCar(Car carBuy) throws RemoteException;


}

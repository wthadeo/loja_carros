package server;

import dataServer.DataServer;
import server.classes.Car;
import server.classes.User;

import java.rmi.RemoteException;
import java.util.ArrayList;

public class ServerFunctionsImpl implements ServerFunctions {

    @Override
    public void saveData(ArrayList<Car> cars) throws RemoteException {

        DataServer.cars = cars;

    }

    @Override
    public ArrayList<Car> getData() throws RemoteException {

        ArrayList<Car> carsTemporary = new ArrayList<>();
        carsTemporary = DataServer.cars;

        return carsTemporary;
    }

    @Override
    public ArrayList<User> getUsers() throws RemoteException {

        ArrayList<User> listOfUsers = new ArrayList<>();
        listOfUsers = DataServer.users;

        return listOfUsers;
    }
}

package server;

import server.classes.Car;
import server.classes.User;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface ServerFunctions extends Remote {

    //###################### FUNÇÕES SERVIDOR - DATASERVER ######################

    void saveData(ArrayList<Car> cars) throws RemoteException;
    ArrayList<Car> getData() throws RemoteException;
    ArrayList<User> getUsers() throws RemoteException;

    //############################################################################

}

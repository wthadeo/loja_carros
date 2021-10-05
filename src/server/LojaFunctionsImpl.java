package server;

import dataServer.DataServer;
import javafx.scene.input.DataFormat;
import server.classes.Car;
import server.classes.User;

import java.rmi.RemoteException;
import java.util.ArrayList;

public class LojaFunctionsImpl implements LojaFunctions{

    //##################################### LOGGIN DE USUARIOS #################################################
    @Override
    public User login(String username, String pass){

        ArrayList<User> validyUsers = new ArrayList<>();
        validyUsers = Server.listOfUsers;

        for(User user: validyUsers){

            if(user.username.equals(username)){
                if(user.pass.equals(pass)){
                    return user;
                } else {
                    System.out.println("Falha ao tentar logar. Senha incorreta!");
                    return null;
                }
            }
        }
        return null;
    }

    //##################################### FUNÇÃO DE USUARIOS #################################################

    @Override
    public String addCar(String renavam, String name, int year, double price) throws RemoteException {

        ArrayList<Car> carsTemporary = new ArrayList<>();
        carsTemporary = Server.carsModify;

        Car car = new Car(renavam, name, year, price);

        if(!(car != null)){
            return "Não foi possível adicionar o carro!";
        } else {
            carsTemporary.add(car);
            Server.quantityCars += 1;
        }
        Server.sendChanges(carsTemporary);
        return "Carro adicionado com sucesso";
    }

    @Override
    public String deleteCar(String renavam) throws RemoteException{

        ArrayList<Car> carsTemporary = new ArrayList<>();
        carsTemporary = Server.carsModify;
        Car carToRemove = null;

        for(Car atualCar: carsTemporary){
            if(atualCar.renavam.equals(renavam)){
                carToRemove = atualCar;
            }
        }
        if(carToRemove != null) {
            carsTemporary.remove(carToRemove);
            Server.quantityCars -= 1;
        } else {
            return "Este Renavam não pertence à um carro cadastrado no sistema.";
        }
        Server.sendChanges(carsTemporary);
        return "Carro deletado com sucesso";
    }

    @Override
    public ArrayList<Car> referenceCars(){

        ArrayList<Car> carsTemporary = new ArrayList<>();
        carsTemporary = Server.carsModify;

        return carsTemporary;
    }

    @Override
    public Car searchCarRenavam(String renavam) throws RemoteException {

        ArrayList<Car> carsTemporary = new ArrayList<>();
        carsTemporary = Server.carsModify;
        Car carRenavam = null;

        for(Car atualCar: carsTemporary){
            if(atualCar.renavam.equalsIgnoreCase(renavam)) {
                carRenavam = atualCar;
            }
        }

        if(carRenavam != null)
            return carRenavam;
        else {
            return null;
        }

    }

    @Override
    public ArrayList<Car> searchCarName(String name) throws RemoteException{

        ArrayList<Car> carsTemporary = new ArrayList<>();
        carsTemporary = Server.carsModify;
        ArrayList<Car> listCarName = new ArrayList<>();

        for(Car atualCar: carsTemporary){
            if(atualCar.name.equalsIgnoreCase(name)){
                listCarName.add(atualCar);
            }
        }

        return listCarName;

    }

    @Override
    public int infoQuantity() throws RemoteException {
        int quant = Server.quantityCars;

        return quant;
    }

    @Override
    public String buyCar(Car carBuy) throws RemoteException {

        ArrayList<Car> carsTemporary = new ArrayList<>();
        carsTemporary = Server.carsModify;

        carsTemporary.remove(carBuy);
        Server.quantityCars -= 1;

        Server.sendChanges(carsTemporary);
        return "Carro comprado com sucesso";
    }

    @Override
    public String editCar(String renavamAtual, String renavam, String name, int year, double price) throws RemoteException{

        ArrayList<Car> carsTemporary = new ArrayList<>();
        carsTemporary = Server.carsModify;
        Car carToEdit = null;
        int indice = 0;
        Car carEdited = new Car(renavam, name, year, price);

        for(Car atualCar: carsTemporary){
            if(atualCar.renavam.equalsIgnoreCase(renavamAtual)){
                carToEdit = atualCar;
                indice = carsTemporary.indexOf(atualCar);
            }
        }

        if(carToEdit != null) {
            carsTemporary.remove(carToEdit);
            carsTemporary.add(indice, carEdited);
        } else {
            return "Este Renavam não pertence à um carro cadastrado no sistema.";
        }
        Server.sendChanges(carsTemporary);

        return "Carro editado com sucesso";
    }


}

package client;

import server.classes.Invoice;
import server.classes.User;
import server.classes.Car;
import server.LojaFunctions;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Scanner;

public class Client {

    public Client(){}

    public static void main(String[] args) {

        boolean allFunctions = false;
        User actualUser = new User("", "", "");
        boolean logged = false, closeProgram = false;
        Scanner inputClient = new Scanner(System.in);
        String username, pass;
        ArrayList<Car> referenceCars = new ArrayList<>();
        ArrayList<Car> foundedCars = new ArrayList<>();
        Car foundCar = new Car();


        try {

            Registry registro = LocateRegistry.getRegistry("localhost", 20002);

            LojaFunctions stubClient = (LojaFunctions) registro.lookup("LojaServer");



            do{

                System.out.println("Insira o seu username: ");
                username = inputClient.nextLine();
                System.out.println("Insira a sua senha: ");
                pass = inputClient.nextLine();
                actualUser = stubClient.login(username, pass);
                if(actualUser != null){
                    logged = true;
                    if(actualUser.function.equals("Employee")){
                        allFunctions = true;
                    }
                    System.out.println("Login efetuado com sucesso!");
                } else {
                    System.out.println("Falha ao logar, tente novamente.");
                }

            } while(!logged);

            while(!closeProgram) {

                String choice;
                if (allFunctions) {

                    menuEmployee();
                    choice = inputClient.nextLine();
                    switch (Integer.parseInt(choice)){

                        case 1:
                            String a,b,c, d, resultado;
                            System.out.println("Informe os dados do veiculo para adiciona-lo");
                            System.out.println("Renavan:");
                            a = inputClient.nextLine();
                            System.out.println("Nome:");
                            b = inputClient.nextLine();
                            System.out.println("Ano de Fabricação:");
                            c = (inputClient.nextLine());
                            System.out.println("Preço:");
                            d = inputClient.nextLine();
                            resultado = stubClient.addCar(a, b, Integer.parseInt(c), Double.parseDouble(d));
                            System.out.println(resultado);
                            referenceCars = stubClient.referenceCars();
                            listCars(referenceCars);
                            break;

                        case 2:
                            String renavamDeletar;
                            System.out.println("Informe o renavam do carro que deseja deletar");
                            renavamDeletar = inputClient.nextLine();
                            resultado = stubClient.deleteCar(renavamDeletar);
                            System.out.println(resultado);
                            referenceCars = stubClient.referenceCars();
                            listCars(referenceCars);
                            break;

                        case 3:
                            System.out.println("Deseja Listar por categoria? (S/N)");
                            String yesOrNo, categoryChoosed;
                            yesOrNo = inputClient.nextLine();
                            if(yesOrNo.equalsIgnoreCase("s")){
                                System.out.println("Digite qual categoria deseja: ");
                                System.out.println("1. Econômica | 2. Intermediária | 3. Executiva");
                                categoryChoosed = inputClient.nextLine();
                                if(Integer.parseInt(categoryChoosed) == 1){
                                    referenceCars = stubClient.referenceCars();
                                    listCars(referenceCars, Car.Category.ECONOMIC);
                                } else if (Integer.parseInt(categoryChoosed) == 2){
                                    referenceCars = stubClient.referenceCars();
                                    listCars(referenceCars, Car.Category.INTERMEDIARY);
                                } else if (Integer.parseInt(categoryChoosed) == 3){
                                    referenceCars = stubClient.referenceCars();
                                    listCars(referenceCars, Car.Category.EXECUTIVE);
                                } else {
                                    System.out.println("Opção Inválida");
                                }
                            } else {
                                referenceCars = stubClient.referenceCars();
                                listCars(referenceCars);
                            }
                            break;

                        case 4:
                            String searchChoosed;
                            System.out.println("Digite como deseja fazer a consulta: ");
                            System.out.println("1. Por Renavam | 2. Por nome");
                            searchChoosed = inputClient.nextLine();
                            if(Integer.parseInt(searchChoosed) == 1){
                                String renavamConsulta;
                                System.out.println("Digite o número do renavam: ");
                                renavamConsulta = inputClient.nextLine();
                                foundCar = stubClient.searchCarRenavam(renavamConsulta);
                                System.out.println("Renavam: " + foundCar.renavam + " | Nome: " + foundCar.name +
                                        " | Categoria: " + foundCar.category + " | Ano de Fab: " + foundCar.year +
                                        "| Preço: " + foundCar.price);
                            } else if (Integer.parseInt(searchChoosed) == 2){
                                String nomeConsulta;
                                System.out.println("Digite o nome do carro: ");
                                nomeConsulta = inputClient.nextLine();
                                foundedCars = stubClient.searchCarName(nomeConsulta);
                                listCars(foundedCars);
                            } else {
                                System.out.println("Opção Inválida");
                            }
                            break;

                        case 5:
                            String carEdit;
                            referenceCars = stubClient.referenceCars();
                            listCars(referenceCars);
                            System.out.println("Informe o renavam do carro a editar: ");
                            carEdit = inputClient.nextLine();
                            Car carToEdit = stubClient.searchCarRenavam(carEdit);

                            if(carToEdit !=null) {
                                System.out.println("Renavam: " + carToEdit.renavam + " | Nome: " + carToEdit.name +
                                        " | Categoria: " + carToEdit.category + " | Ano de Fab: " + carToEdit.year +
                                        "| Preço: " + carToEdit.price);
                                System.out.println("Informe os novos dados para renavam, Nome, Ano de Fab e Preço: ");
                                String e = inputClient.nextLine();
                                String f = inputClient.nextLine();
                                String g = inputClient.nextLine();
                                String h = inputClient.nextLine();
                                resultado = stubClient.editCar(carEdit, e, f, Integer.parseInt(g), Double.parseDouble(h));
                            } else{
                                resultado = "Este Renavam não pertence à um carro cadastrado no sistema.";
                            }
                            System.out.println(resultado);
                            break;

                        case 6:
                            System.out.println("Atualmente existem " + stubClient.infoQuantity() +
                                    " carros cadastrados no sistema.");
                            break;

                        case 7:
                            String carBuy;
                            referenceCars = stubClient.referenceCars();
                            listCars(referenceCars);
                            System.out.println("Informe o renavam do carro que deseja comprar: ");
                            carBuy = inputClient.nextLine();
                            Car carToBuy = stubClient.searchCarRenavam(carBuy);
                            if(carToBuy !=null) {
                                System.out.println("Renavam: " + carToBuy.renavam + " | Nome: " + carToBuy.name +
                                        " | Categoria: " + carToBuy.category + " | Ano de Fab: " + carToBuy.year +
                                        "| Preço: " + carToBuy.price);
                                System.out.println("Para concluir a transação, ");
                                System.out.println("Por favor informe os dados a seguir");
                                System.out.println("Nome Completo: ");
                                String nomeCompleto = inputClient.nextLine();
                                System.out.println("CPF: ");
                                String cpf = inputClient.nextLine();
                                System.out.println("Data de Nascimento: (dd/mm/aaaa) ");
                                String dataNasc = inputClient.nextLine();
                                System.out.println("Endereço: ");
                                String endereco = inputClient.nextLine();
                                resultado = stubClient.buyCar(carToBuy);
                                System.out.println(resultado);
                                Invoice invoiceCarBuyed =
                                        new Invoice(nomeCompleto, cpf, dataNasc, endereco, carToBuy);
                                invoiceCarBuyed.showInvoice();
                            }

                            break;

                        case 8:
                            System.out.println("Até logo!");
                            closeProgram = true;
                            break;


                    }


                } else {

                    menuClient();
                    choice = inputClient.nextLine();
                    switch (Integer.parseInt(choice)){

                        case 1:
                            System.out.println("Deseja Listar por categoria? (S/N)");
                            String yesOrNo, categoryChoosed;
                            yesOrNo = inputClient.nextLine();
                            if(yesOrNo.equalsIgnoreCase("s")){
                                System.out.println("Digite qual categoria deseja: ");
                                System.out.println("1. Econômica | 2. Intermediária | 3. Executiva");
                                categoryChoosed = inputClient.nextLine();
                                if(Integer.parseInt(categoryChoosed) == 1){
                                    referenceCars = stubClient.referenceCars();
                                    listCars(referenceCars, Car.Category.ECONOMIC);
                                } else if (Integer.parseInt(categoryChoosed) == 2){
                                    referenceCars = stubClient.referenceCars();
                                    listCars(referenceCars, Car.Category.INTERMEDIARY);
                                } else if (Integer.parseInt(categoryChoosed) == 3){
                                    referenceCars = stubClient.referenceCars();
                                    listCars(referenceCars, Car.Category.EXECUTIVE);
                                } else {
                                    System.out.println("Opção Inválida");
                                }
                            } else {
                                referenceCars = stubClient.referenceCars();
                                listCars(referenceCars);
                            }
                            break;

                        case 2:
                            String searchChoosed;
                            System.out.println("Digite como deseja fazer a consulta: ");
                            System.out.println("1. Por Renavam | 2. Por nome");
                            searchChoosed = inputClient.nextLine();
                            if(Integer.parseInt(searchChoosed) == 1){
                                String renavamConsulta;
                                System.out.println("Digite o número do renavam: ");
                                renavamConsulta = inputClient.nextLine();
                                foundCar = stubClient.searchCarRenavam(renavamConsulta);
                                System.out.println("Renavam: " + foundCar.renavam + " | Nome: " + foundCar.name +
                                        " | Categoria: " + foundCar.category + " | Ano de Fab: " + foundCar.year +
                                        "| Preço: " + foundCar.price);
                            } else if (Integer.parseInt(searchChoosed) == 2){
                                String nomeConsulta;
                                System.out.println("Digite o nome do carro: ");
                                nomeConsulta = inputClient.nextLine();
                                foundedCars = stubClient.searchCarName(nomeConsulta);
                                listCars(foundedCars);
                            } else {
                                System.out.println("Opção Inválida");
                            }
                            break;

                        case 3:
                            System.out.println("Atualmente existem " + stubClient.infoQuantity() +
                                    " carros cadastrados no sistema.");
                            break;

                        case 4:
                            String carBuy;
                            referenceCars = stubClient.referenceCars();
                            listCars(referenceCars);
                            System.out.println("Informe o renavam do carro que deseja comprar: ");
                            carBuy = inputClient.nextLine();
                            Car carToBuy = stubClient.searchCarRenavam(carBuy);
                            if(carToBuy !=null) {
                                System.out.println("Renavam: " + carToBuy.renavam + " | Nome: " + carToBuy.name +
                                        " | Categoria: " + carToBuy.category + " | Ano de Fab: " + carToBuy.year +
                                        "| Preço: " + carToBuy.price);
                                System.out.println("Para concluir a transação, ");
                                System.out.println("Por favor informe os dados a seguir");
                                System.out.println("Nome Completo: ");
                                String nomeCompleto = inputClient.nextLine();
                                System.out.println("CPF: ");
                                String cpf = inputClient.nextLine();
                                System.out.println("Data de Nascimento: (dd/mm/aaaa) ");
                                String dataNasc = inputClient.nextLine();
                                System.out.println("Endereço: ");
                                String endereco = inputClient.nextLine();
                                String resultado2 = stubClient.buyCar(carToBuy);
                                System.out.println(resultado2);
                                Invoice invoiceCarBuyed =
                                        new Invoice(nomeCompleto, cpf, dataNasc, endereco, carToBuy);
                                invoiceCarBuyed.showInvoice();
                            }

                            break;

                        case 5:
                            System.out.println("Até logo!");
                            closeProgram = true;
                            break;


                    }
                }
            }


        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }


    }

    public static void listCars(ArrayList<Car> cars){

        for(Car atualCar: cars){
            System.out.println("Renavam: " + atualCar.renavam + " | Nome: " + atualCar.name +
                    " | Categoria: " + atualCar.category + " | Ano de Fab: " + atualCar.year + "| Preço: " + atualCar.price);
        }

    }

    public static void listCars(ArrayList<Car> cars, Car.Category category){

        for(Car atualCar: cars){
            if(atualCar.category.equals(category)) {
                System.out.println("Renavam: " + atualCar.renavam + " | Nome: " + atualCar.name +
                        " | Categoria: " + atualCar.category + " | Ano de Fab: " + atualCar.year + "| Preço: " + atualCar.price);
            }
        }
    }

    public static void menuEmployee(){
        System.out.println("SISTEMA RENT A CAR");
        System.out.println("ESCOLHA UMA DAS OPÇÕES ABAIXO PARA CONTINUAR");
        System.out.println("1. ADICIONAR CARRO");
        System.out.println("2. DELETAR CARRO");
        System.out.println("3. LISTAR CARROS");
        System.out.println("4. CONSULTAR CARRO");
        System.out.println("5. EDITAR CARRO");
        System.out.println("6. EXIBIR QUANTIDADE DE CARROS");
        System.out.println("7. COMPRAR CARRO");
        System.out.println("8. ENCERRAR SESSÃO");
    }

    public static void menuClient(){
        System.out.println("BEM VINDO A RENT A CAR");
        System.out.println("ESCOLHA UMA DAS OPÇÕES ABAIXO PARA CONTINUAR");
        System.out.println("1. LISTAR CARROS");
        System.out.println("2. CONSULTAR CARRO");
        System.out.println("3. EXIBIR QUANTIDADE DE CARROS");
        System.out.println("4. COMPRAR CARRO");
        System.out.println("5. ENCERRAR SESSÃO");
    }

}

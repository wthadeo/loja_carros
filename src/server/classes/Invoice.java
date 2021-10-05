package server.classes;

public class Invoice {


    public String nome;
    public String cpf;
    public String dataNasc;
    public String endereco;
    public Car carbuyed = new Car();

    public Invoice(){}

    public Invoice(String nome, String cpf, String dataNasc, String endereco, Car carBuyed){

        this.nome = nome;
        this.cpf = cpf;
        this.dataNasc = dataNasc;
        this.endereco = endereco;
        this.carbuyed = carBuyed;

    }

    public void showInvoice(){

        System.out.println("Nome Cliente: " + nome);
        System.out.println("CPF: " + cpf);
        System.out.println("Data de Nascimento: " + dataNasc);
        System.out.println("Endereço: " + endereco);
        System.out.println("Carro adquirido: ");
        System.out.println("Renavam: " + carbuyed.renavam + " | Nome: " + carbuyed.name +
                " | Categoria: " + carbuyed.category + " | Ano de Fab: " + carbuyed.year +
                "| Preço: " + carbuyed.price);

    }

}

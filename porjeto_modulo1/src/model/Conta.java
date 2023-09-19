package model;

import java.util.ArrayList;

public abstract class Conta {

    protected int numero;  // Controller
    protected int agencia; // Controller
    protected int tipo;      // User
    protected String titular; // User
    protected double saldo;    // User
    protected ArrayList<OperacoesBancarias> extrato = new ArrayList<>();


    public void visualizar() {

        String tipo = "";

        switch (this.tipo) {
            case 1:
                tipo = "Conta Corrente";
                break;
            case 2:
                tipo = "Conta Poupança";
                break;
        }

        System.out.println("\n\n*******************************************************");
        System.out.println("Dados da Conta:");
        System.out.println("***********************************************************");
        System.out.println("Numero da Conta: " + this.numero);
        System.out.println("Agência: " + this.agencia);
        System.out.println("Tipo da Conta: " + tipo);
        System.out.println("Titular: " + this.titular);
        System.out.println("Saldo: " + this.saldo);

    }
}
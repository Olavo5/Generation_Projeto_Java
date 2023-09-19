package model;

import java.util.ArrayList;

public interface Bancaria {

    public boolean sacar(double valor);
    public boolean depositar(double valor);
    public double getSaldo();
    public ArrayList<OperacoesBancarias> verExtrato();
}

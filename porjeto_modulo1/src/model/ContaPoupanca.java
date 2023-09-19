package model;

import java.util.ArrayList;

public class ContaPoupanca extends Conta implements Bancaria {
    private int aniversario;

    public ContaPoupanca(int numero, int agencia, int tipoDeConta, String titular, double primeiroDeposito, int aniversario) {
        this.numero = numero;
        this.agencia = agencia;
        this.tipo = tipoDeConta;
        this.titular = titular;
        this.saldo = primeiroDeposito;
        this.aniversario = aniversario;
    }

    private boolean validaTransacao(double valor) {
        return valor > 0;
    }

    public boolean sacar(double valor) {
        if (!validaTransacao(valor)) {
            return false;
        }

        if (valor <= saldo) {
            saldo -= valor;
            extrato.add(new OperacoesBancarias("Saque", valor));
            return true;
        }

        return false;
    }

    public boolean depositar(double deposito) {
        if (!validaTransacao(deposito)) {
            return false;
        }

        saldo += deposito;
        extrato.add(new OperacoesBancarias("Depósito", deposito));
        return true;
    }

    public ArrayList<OperacoesBancarias> verExtrato() {
        return extrato;
    }

    @Override
    public String toString() {
        return String.format("""
                Conta Poupança: %d
                Titular: %s
                Saldo: R$ %,.2f
                Aniversário da Conta: %d
                """
                , numero, titular, saldo, aniversario
        );
    }

    public double getSaldo() {
        return saldo;
    }

    public int getAniversario() {
        return aniversario;
    }
}
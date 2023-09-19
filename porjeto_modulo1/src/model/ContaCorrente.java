package model;

import java.util.ArrayList;

public class ContaCorrente extends Conta implements Bancaria{
    private double limiteCredito;
    private double LIMITE_MAXIMO_DE_CREDITO;

    public ContaCorrente(int numero, int agencia, int tipoDeConta, String titular, double primeiroDeposito, double limiteDeCredito) {
        this.numero = numero;
        this.agencia = agencia;
        this.tipo = tipoDeConta;
        this.titular = titular;
        this.saldo = primeiroDeposito;
        this.limiteCredito = limiteDeCredito;
        LIMITE_MAXIMO_DE_CREDITO = limiteDeCredito;
    }


    private boolean validaTransacao(double valor) {return valor > 0;}
    private boolean podeSacar(double valor) {return valor <= saldo;}
    private boolean podeUsarCredito(double valor) {return valor <= limiteCredito;}


    public boolean sacar(double valor) {
        if (!validaTransacao(valor)){ return false;}

        if(podeSacar(valor)) {
            saldo -= valor;
            extrato.add(new OperacoesBancarias("Saque", valor ));
            return true;
        }
        else if(podeUsarCredito(valor)) {
            limiteCredito -= valor;
            extrato.add(new OperacoesBancarias("Saque - Crédito", valor));
            return true;
        }
        return false;
    }


    public double getSaldoDevedor(){
        return LIMITE_MAXIMO_DE_CREDITO - limiteCredito;
    }

    public boolean depositar(double deposito){
        if(!validaTransacao(deposito)) {
            return false;
        }
        if (temSaldoDevedor()){
            var saldoDevedor = getSaldoDevedor();

            if (deposito <= saldoDevedor) { // Depositando valor a menor que a dívida
                limiteCredito += deposito;
                extrato.add(new OperacoesBancarias("Pagamento do cheque especial", deposito));
                return true;
            } else { // deposito é maior que a dívida
                limiteCredito += saldoDevedor;
                deposito -= saldoDevedor;
                saldo += deposito;
                var mensagem = String.format(
                        "Pagamento do Cheque Especial do (R$ %,.2f) e depósito de (R$ %,.2f)", saldoDevedor, deposito
                );
                extrato.add(new OperacoesBancarias(mensagem, deposito));
                return true;
            }
        } else { // Não tenho saldo devedor
            saldo += deposito;
            extrato.add(new OperacoesBancarias("Deposito", deposito));
            return true;
        }
    }

    private boolean temSaldoDevedor() {
        return limiteCredito < LIMITE_MAXIMO_DE_CREDITO;
    }


    public ArrayList<OperacoesBancarias> verExtrato(){
        return extrato;
    }

    @Override
    public String toString() {
        return String.format("""
                Conta Corrente: %d
                Titular: %s
                Saldo: R$ %,.2f
                Limite de crédito pré aprovado: R$ %,.2f
                """
                , numero, titular, saldo, limiteCredito
        );
    }

    public double getSaldo(){
        return saldo;
    }
    public double getLimiteCredito(){
        return limiteCredito;
    }
}

package controller;
import cores.Cores;
import model.ContaCorrente;
import model.ContaPoupanca;
import model.Conta;
import static cores.GeradorNumeroAleatorio.gerarNumeroAleatorio;


import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;

public class ContaCotroller {

    private Scanner leia = new Scanner(System.in);
    private static final int DOBRAR_LIMITE = 2;
    private ContaCorrente contaAtual;
    private ContaPoupanca contaAtualP;

    public void criarConta() {
        int numero;
        int agencia;
        int tipoDeConta;
        double primeiroDeposito;
        double limiteDeCredito = 0;
        String titular;
        int aniversario = 0;

        System.out.println(Cores.TEXT_WHITE + "Criar conta.");

        numero = gerarNumero();
        agencia = contruirAgencia();
        titular = contruirTitular();
        tipoDeConta = contruirTipoConta();
        primeiroDeposito = contruirPrimeiroDeposito();


        switch (tipoDeConta) {
            case 1 -> {
                limiteDeCredito = avaliarLimiteDeCredito(primeiroDeposito);
                contaAtual = new ContaCorrente(numero, agencia, tipoDeConta, titular, primeiroDeposito, limiteDeCredito);
                mostrarConta();
            }
            case 2 -> {
                // Criar uma nova ContaPoupanca
                contaAtualP = new ContaPoupanca(numero, agencia, tipoDeConta, titular, primeiroDeposito, aniversario);
                mostrarContaPoupanca();
            }
            default -> {
                System.out.println("Tipo de conta inválido.");
                return; // Encerrar a operação se o tipo de conta for inválido
            }
        }

    }

    private void mostrarConta() {
        System.out.println("------------- Conta cadastrada -------------");
        System.out.println(contaAtual);
        System.out.println("--------------------------------------------");
    }

    private void mostrarContaPoupanca() {
        System.out.println("------------- Conta cadastrada -------------");
        System.out.println(contaAtualP);
        System.out.println("--------------------------------------------");
    }

    public void exibirContaPoupanca() {
        if (contaAtualP != null) {
            mostrarContaPoupanca();
        } else {
            System.out.println("Conta Poupança não encontrada.");
        }
    }


    private int gerarNumero() {
        return gerarNumeroAleatorio();
    }

    private double avaliarLimiteDeCredito(double primeiroDeposito) {
        return primeiroDeposito * DOBRAR_LIMITE;
    }


    private double contruirPrimeiroDeposito() {
        System.out.println("Digite o depósito inicial da conta: ");
        return Double.parseDouble(leia.nextLine());
    }

    private int contruirTipoConta() {
        while (true) {
            try {
                System.out.println("Digite o tipo de conta (1-CC, 2-CP): ");
                int tipo = Integer.parseInt(leia.nextLine());
                if (tipo >= 1 && tipo <= 2) {
                    return tipo;
                }
            } catch (NumberFormatException e) {
                System.out.println("Número inválido. Tente novamente.");
            }
        }
    }

    private String contruirTitular() {
        System.out.println("Digite o nome do titular: ");
        return leia.nextLine();
    }

    private int contruirAgencia() {
        while (true) {
            try {
                System.out.println("Digite o no agência: ");
                return Integer.parseInt(leia.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Número inválido. Tente novamente.");
            }
        }
    }

    public void depositar() {
        if (!existeUmaConta()) {
            crieUmaContaMensagem();
            return;
        }

        System.out.println("------------- Deposito ------------");
        System.out.println("Digite o valor que deseja depositar:");

        var valor = coletarInputDouble();
        boolean depositoExecutadoComSucesso = false;

        if (contaAtual != null) {
            depositoExecutadoComSucesso = contaAtual.depositar(valor);
        } else if (contaAtualP != null) {
            depositoExecutadoComSucesso = contaAtualP.depositar(valor);
        } else {
            System.out.println("Conta não encontrada.");
            return;
        }

        if (contaAtual != null) {
            depositoExecutadoComSucesso = contaAtual.depositar(valor);

            System.out.printf("""
                            Deposito de R$ %,.2f executado com sucesso!
                            Seu novo saldo é R$ %,.2f
                            Seu limite é R$ %,.2f
                            Seu saldo devedor é R$ %,.2f\n"""
                    , valor, contaAtual.getSaldo(), contaAtual.getLimiteCredito(), contaAtual.getSaldoDevedor()
            );
        } else if (contaAtualP != null) {
            depositoExecutadoComSucesso = contaAtualP.depositar(valor);

            if (depositoExecutadoComSucesso) {
                System.out.printf("""
                                Depósito de R$ %,.2f executado com sucesso!
                                Seu novo saldo é R$ %,.2f
                                Seu aniversário é %d\n""",
                        valor, contaAtualP.getSaldo(), contaAtualP.getAniversario()
                );
            } else {
                System.out.println("Depósito não foi executado.");
            }
        }
    }

    public void sacar() {
        if (!existeUmaConta()) {
            crieUmaContaMensagem();
            return;
        }

        System.out.println("------------- Saque ------------");
        System.out.println("Digite o valor que deseja sacar:");

        var valor = coletarInputDouble();
        boolean saqueExecutadoComSucesso = false;

        if (contaAtual != null) {
            saqueExecutadoComSucesso = contaAtual.sacar(valor);

            if (saqueExecutadoComSucesso) {
                var mensagem = String.format("""
                                Saque de R$ %,.2f executado com sucesso.
                                Seu novo saldo é R$ %,.2f
                                Seu limite de crédito R$ %,.2f
                                Seu saldo devedor é R$ %,.2f
                                """,
                        valor, contaAtual.getSaldo(), contaAtual.getLimiteCredito(), contaAtual.getSaldoDevedor()
                );
                System.out.println("----------------------");
                System.out.println(mensagem);
                System.out.println("----------------------");
            } else {
                var mensagem = String.format("""
                                Saque de R$ %,.2f não executado pois seu saldo é insuficiente.
                                Seu saldo é R$ %,.2f
                                Seu limite de crédito R$ %,.2f
                                Seu saldo devedor é R$ %,.2f
                                """,
                        valor, contaAtual.getSaldo(), contaAtual.getLimiteCredito(), contaAtual.getSaldoDevedor()
                );
                System.out.println("----------------------");
                System.out.println(mensagem);
                System.out.println("----------------------");
            }
        } else if (contaAtualP != null) {
            saqueExecutadoComSucesso = contaAtualP.sacar(valor);

            if (saqueExecutadoComSucesso) {
                var mensagem = String.format("""
                                Saque de R$ %,.2f executado com sucesso.
                                Seu novo saldo é R$ %,.2f
                                Seu aniversário é %d
                                """,
                        valor, contaAtualP.getSaldo(), contaAtualP.getAniversario()
                );
                System.out.println("----------------------");
                System.out.println(mensagem);
                System.out.println("----------------------");
            } else {
                System.out.println("Saque não foi executado.");
            }
        }
    }

    private void crieUmaContaMensagem() {
        System.out.println("------------- Não há uma conta ------------");
        System.out.println("Por favor, crie uma conta primeiro.");
        System.out.println("-------------------------------------------");
    }


    public double coletarInputDouble() {
        while (true) {
            try {
                return Double.parseDouble(leia.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Número inválido. Tente novamente.");
            }
        }
    }


    private boolean existeUmaConta() {
        Optional<ContaCorrente> contaCorrenteOptional = Optional.ofNullable(contaAtual);
        Optional<ContaPoupanca> contaPoupancaOptional = Optional.ofNullable(contaAtualP);

        return contaCorrenteOptional.isPresent() || contaPoupancaOptional.isPresent();
    }

    public void exibirExtrato() {
        if (!existeUmaConta()) {
            crieUmaContaMensagem();
            return;
        }

        System.out.println("-------------- Extrato Bancário ------------------");

        if (contaAtual != null) {
            contaAtual.verExtrato().forEach(opBancaria -> System.out.println(opBancaria));
            System.out.printf("""
                        Seu saldo atual é R$ %,.2f
                        Seu limite de crédito R$ %,.2f
                        Seu saldo devedor é R$ %,.2f
                        """,
                    contaAtual.getSaldo(), contaAtual.getLimiteCredito(), contaAtual.getSaldoDevedor()
            );
        } else if (contaAtualP != null) {
            contaAtualP.verExtrato().forEach(opBancaria -> System.out.println(opBancaria));
            System.out.printf("""
                        Seu saldo atual é R$ %,.2f
                        Seu aniversário é %d
                        """,
                    contaAtualP.getSaldo(), contaAtualP.getAniversario()
            );
        }

        System.out.println("---------------------------------------------------");
    }
}






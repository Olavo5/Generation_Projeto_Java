import controller.ContaCotroller;
import cores.Cores;

import java.util.Scanner;


public class Menu {

    private Scanner leia = new Scanner(System.in);
    private ContaCotroller contaCotroller = new ContaCotroller();
    private int operacaoEscolhida;
    public boolean rodarPrograma = true;


    public void mostrarOpcoes() {

        System.out.println(Cores.TEXT_CYAN_BRIGHT + Cores.ANSI_BLACK_BACKGROUND
                + "*****************************************************");
        System.out.println("*****************************************************");
        System.out.println("                                                     ");
        System.out.println("               FINTECH NIT BRASIL                    ");
        System.out.println("                                                     ");
        System.out.println("*****************************************************");
        System.out.println("                                                     ");
        System.out.println("            1 - Criar Conta                          ");
        System.out.println("            2 - Depositar                            ");
        System.out.println("            3 - Sacar                                ");
        System.out.println("            4 - Exibir Extrato                       ");
        System.out.println("            5 - Sair                                 ");
        System.out.println("                                                     ");
        System.out.println("*****************************************************");
        System.out.println("Entre com a opção desejada:                          ");
        System.out.println("                                                     ");
        System.out.println("                                                     " + Cores.TEXT_RESET);
    }

    public void coletarEscolha() {
        operacaoEscolhida = Integer.parseInt(leia.nextLine());
    }

    public void executarTarfa() {
        switch (operacaoEscolhida) {
            case 1 -> contaCotroller.criarConta();
            case 2 -> contaCotroller.depositar();
            case 3 -> contaCotroller.sacar();
            case 4 -> contaCotroller.exibirExtrato();
            case 5 -> sair();
        }
    }



    private void sair() {
        rodarPrograma = false;
        System.out.println("Até Logo.");
    }
}

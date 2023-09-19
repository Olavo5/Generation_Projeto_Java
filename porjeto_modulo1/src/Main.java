
public class Main {
    public static void main(String[] args) {
        Menu menu = new Menu();

        while (menu.rodarPrograma){
            menu.mostrarOpcoes();
            menu.coletarEscolha();
            menu.executarTarfa();
        }
    }
}

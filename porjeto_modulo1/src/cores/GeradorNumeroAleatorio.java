package cores;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GeradorNumeroAleatorio {
    public static int gerarNumeroAleatorio() {
        List<Integer> digitos = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            digitos.add(i);
        }

        Collections.shuffle(digitos);

        StringBuilder numeroAleatorio = new StringBuilder();

        // Certifique-se de que o primeiro dígito não seja zero
        int primeiroDigito = digitos.remove(0);
        numeroAleatorio.append(primeiroDigito);

        // Pegue os próximos 4 dígitos
        for (int i = 0; i < 4; i++) {
            int digito = digitos.get(i);
            numeroAleatorio.append(digito);
        }

        return Integer.parseInt(numeroAleatorio.toString());
    }
}

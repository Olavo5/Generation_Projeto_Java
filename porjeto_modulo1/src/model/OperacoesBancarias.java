package model;

public record OperacoesBancarias(String nome, double valor) {
    @Override
    public String toString() {
        return String.format("%s: R$ %,.2f", nome, valor);
    }
}

package model;

public class Carro extends Veiculos {

    private static double valorPrimeiraHora = 8.00;
    private static double valorHoraAdicional = 3.00;

    public Carro(String placa) {
        super(placa, "Carro");
    }

    @Override
    public double calcularPrimeiraHora() {
        return valorPrimeiraHora;
    }

    @Override
    public double calcularHoraAdicional() {
        return valorHoraAdicional;
    }

    public static void alterarTarifas(double primeiraHora, double adicional) {
        valorPrimeiraHora = primeiraHora;
        valorHoraAdicional = adicional;
    }
}
public class Moto extends Veiculos {

    private static double valorPrimeiraHora = 4.00;
    private static double valorHoraAdicional = 2.00;

    public Moto(String placa) {
        super(placa, "Moto");
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
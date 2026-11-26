package model;

import java.time.Duration;
import java.time.LocalDateTime;

public class Calculadora {

    public static long calcularMinutos(LocalDateTime entrada, LocalDateTime saida) {
        Duration duracao = Duration.between(entrada, saida);

        return duracao.toMinutes();

    }

    public static boolean temTolerancia(long minutos) {
        return minutos <= 10;
    }

    public static double calcularValor(Veiculos veiculo, long minutos) {
        if (temTolerancia(minutos)) {
            return 0.0;
        }

        if (minutos >= 360) {
            if (veiculo instanceof Carro) {
                return 30.00;

            } else if (veiculo instanceof Moto) {
                return 15.00;
            }
        }

        long horas = (long) Math.ceil(minutos / 60.00);

        if (horas == 1) {
            return veiculo.calcularPrimeiraHora();

        }

        long horasAdicionais = horas - 1;

        return veiculo.calcularPrimeiraHora() + (horasAdicionais * veiculo.calcularHoraAdicional());

    }
}

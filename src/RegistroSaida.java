import java.time.LocalDateTime;

public class RegistroSaida {

  private RegistroEntrada registroEntrada;
  private LocalDateTime dataHoraSaida;
  private long minutosEstacionado;
  private double valorPago;
  private String formaPagamento;

  public RegistroSaida(RegistroEntrada registroEntrada,
      LocalDateTime dataHoraSaida,
      long minutosEstacionado,
      double valorPago,
      String formaPagamento) {

    this.registroEntrada = registroEntrada;
    this.dataHoraSaida = dataHoraSaida;
    this.minutosEstacionado = minutosEstacionado;
    this.valorPago = valorPago;
    this.formaPagamento = formaPagamento;

  }

  public RegistroEntrada getRegistroEntrada() {
    return registroEntrada;
  }

  public LocalDateTime getDataHoraSaida() {
    return dataHoraSaida;
  }

  public long getMinutosEstacionado() {
    return minutosEstacionado;
  }

  public double getValorPago() {
    return valorPago;
  }

  public String getFormaPagamento() {
    return formaPagamento;
  }

  public void exibirRegistroSaida() {
    System.out.println("\n=== HISTORICO DE SAIDA ===");

    System.out.println("Placa: " + registroEntrada.getVeiculo().getPlaca());

    System.out.println("Tipo: " + registroEntrada.getVeiculo().getTipo());

    System.out.println("Entrada: " + registroEntrada.getDataHoraEntrada());

    System.out.println("Saida: " + dataHoraSaida);

    System.out.println("Tempo estacionado: " + minutosEstacionado + " minutos");

    System.out.printf("Valor cobrado: R$ %.2f%n", valorPago);

    System.out.println("Forma de pagamento: " + formaPagamento);

  }

}

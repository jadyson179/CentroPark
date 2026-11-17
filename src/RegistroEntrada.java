import java.time.LocalDateTime;

public class RegistroEntrada {

    private String codigo;
    private Veiculos veiculo;
    private LocalDateTime dataHoraEntrada;

    public RegistroEntrada(String codigo, Veiculos veiculo ){
        this.codigo = codigo;
        this.veiculo = veiculo;
        this.dataHoraEntrada = LocalDateTime.now();
    }

    // Getters

    public Veiculos getVeiculo(){
        return veiculo;
    }

    public LocalDateTime getDataHoraEntrada(){
        return dataHoraEntrada;
    }

    //exibir informações
    public void exibirRegistro(){
        System.out.println("Código: " + codigo);
        veiculo.exibirDados();
        System.out.println("Data e hora: " + dataHoraEntrada);
        
    }
}

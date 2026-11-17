import java.util.ArrayList;

public class Estacionamento {

    private ArrayList<RegistroEntrada> registros;
    private ArrayList <RegistroEntrada> historico;

    private final int LIMITE_CARROS = 60;
    private final int LIMITE_MOTOS = 20;

    private int proximoCodigo = 1;

    public Estacionamento(){
        registros  = new ArrayList<>();
        historico = new ArrayList<>();
    }

    // Contar veiculos ativos de um determinado tipo

    private int contarVeiculos(Class<?> tipo){
        int quantidade = 0;

        for (RegistroEntrada registro : registros){

            if (registro.getVeiculo().getClass()== tipo){
                quantidade ++;
            }
        }

        return quantidade;

    }

        // verificar se a placa já está estacionada

        private boolean placaJaExiste(String placa){

            for (RegistroEntrada registro : registros){

                if (registro.getVeiculo().getPlaca().equalsIgnoreCase(placa)){

                    return true;
                }
            }

            return false;
        }
        // registrar entrada 

         public void registrarEntrada(Veiculos veiculo) {

        if (veiculo == null) {
            System.out.println("Veiculo invalido!");
            return;
        }

        if (placaJaExiste(veiculo.getPlaca())) {
            System.out.println("Essa placa ja esta estacionada!");
            return;
        }

        if (veiculo instanceof Carro) {

            if (contarVeiculos(Carro.class) >= LIMITE_CARROS) {
                System.out.println("Nao ha vagas para carros!");
                return;
            }

        } else if (veiculo instanceof Moto) {

            if (contarVeiculos(Moto.class) >= LIMITE_MOTOS) {
                System.out.println("Nao ha vagas para motos!");
                return;
            }

        } else {
            System.out.println("Tipo de veiculo nao reconhecido!");
            return;
        }

            String codigo = String.format("CP%04d", proximoCodigo);

            RegistroEntrada registro = new RegistroEntrada(codigo, veiculo);

          
        registros.add(registro);
        historico.add(registro);

        proximoCodigo++;

        System.out.println("Entrada registrada com sucesso!");
        registro.exibirRegistro();


        }

        public void registrarSaida(String placa){
            RegistroEntrada registroEncontrado = null;

            // procurar veiculo pela placa 

            for (RegistroEntrada registro : registros){
                if (registro.getVeiculo().getPlaca().equalsIgnoreCase(placa)){
                   registroEncontrado = registro;
                   break;
                }
            }

            // verificar se o veiculo foi encontrado
            
            if (registroEncontrado == null){
                System.out.println("Veiculo não encontrado!");
                return;
            }

            // registrar horario de saida

            java.time.LocalDateTime horaSaida = java.time.LocalDateTime.now();

            // calcular o tempo de permanencia

            java.time.Duration duracao = java.time.Duration.between(registroEncontrado.getDataHoraEntrada(), horaSaida);

            long minutos = duracao.toMinutes();

            System.out.println("\n=== SAIDA DO VEICULO ===");

            System.out.println("Placa: " + placa);

            System.out.println("Horario de entrada: "
            + registroEncontrado.getDataHoraEntrada());

            System.out.println("Horario de saida: " + horaSaida);

            System.out.println("Tempo estacionado: "
            + minutos + " minutos");

            // liberar vaga

            registros.remove(registroEncontrado);
             
            System.out.println("Saida registrada com sucesso!");
            System.out.println("Vaga liberada!");

        
        }

    

}

import java.util.ArrayList;
import java.util.Scanner;

public class Estacionamento {

    private ArrayList<RegistroEntrada> registros;
    private ArrayList <RegistroEntrada> historico;
    private ArrayList <RegistroSaida> historicoSaidas;
    private ArrayList <Mensalista> mensalistas;

    private Scanner scanner = new Scanner (System.in);

    private final int LIMITE_CARROS = 60;
    private final int LIMITE_MOTOS = 20;

    private int proximoCodigo = 1;

    public Estacionamento(){
        registros  = new ArrayList<>();
        historico = new ArrayList<>();
        historicoSaidas = new ArrayList<>();
        mensalistas = new ArrayList<>();
    };

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

            // Implementando a calculadora ao estacionamento

           Mensalista mensalista = buscarMensalista(placa);

           double valor;

           if (mensalista != null && !mensalista.estaEmAtraso()) {
               valor = 0.0;
          } else {
                  valor = Calculadora.calcularValor(
                registroEncontrado.getVeiculo(), minutos);
    }
            
            int opcao = 0;
            boolean pagamentoValido = false;

             while(!pagamentoValido){

                

            System.out.println("\n=== FORMA DE PAGAMENTO ===");
            System.out.println("1 - Dinheiro");
            System.out.println("2 - Pix");
            System.out.println("3 - Cartao");
            System.out.print("Escolha uma opcao: ");

            

           try {
            opcao = scanner.nextInt();
            scanner.nextLine();

            if (opcao >= 1 && opcao <= 3){
                pagamentoValido = true;
            }
            else {
                System.out.println("Opção invalida! escolha 1,2 ou 3.");
            }

       } catch (java.util.InputMismatchException erro) {

    System.out.println("Erro: digite apenas numeros!");

         scanner.nextLine();
         }
        }


           String formaPagamento;

           switch (opcao) {
    case 1:
        formaPagamento = "Dinheiro";
        break;

    case 2:
        formaPagamento = "Pix";
        break;

    case 3:
        formaPagamento = "Cartao";
        break;

    default:
        formaPagamento = "Invalido";
        System.out.println("Opcao invalida!");
        break;
}

            RegistroSaida saida = new RegistroSaida(
                registroEncontrado, horaSaida, minutos, valor, formaPagamento);

            historicoSaidas.add(saida);

            System.out.println("\n=== SAIDA DO VEICULO ===");

            System.out.println("Placa: " + placa);

            System.out.println("Horario de entrada: "
            + registroEncontrado.getDataHoraEntrada());

            System.out.println("Horario de saida: " + horaSaida);

            System.out.println("Tempo estacionado: "
            + minutos + " minutos");

            System.out.printf("Valor a pagar: R$ %.2f%n", valor);

            // liberar vaga

            registros.remove(registroEncontrado);
             
            System.out.println("Saida registrada com sucesso!");
            System.out.println("Vaga liberada!");

        
        }

        public void exibirHistoricoSaidas() {
        System.out.println("\n=== HISTORICO DE SAIDAS ===");

        if (historicoSaidas.isEmpty()) {
        System.out.println("Nenhuma saida registrada.");
        return;
    }

        for (RegistroSaida saida : historicoSaidas) {
             saida.exibirRegistroSaida();
    }
}

public void exibirVagasDisponiveis(){
    
    int carrosOcupados = contarVeiculos(Carro.class);
    int motosOcupadas = contarVeiculos(Moto.class);

    int carrosDisponiveis = LIMITE_CARROS - carrosOcupados;
    int motosDisponiveis = LIMITE_MOTOS - motosOcupadas;

    System.out.println("\n==== VAGAS DISPONIVEIS ====");

    System.out.println("Carros ocupados: " + carrosOcupados);
    System.out.println("Vagas para carros: " + carrosDisponiveis);

    System.out.println("Motos ocupadas: " + motosOcupadas);
   
    System.out.println("Vagas para motos: " + motosDisponiveis);
}


public void cadastrarMensalista(String nome, String telefone, String placa, java.time.LocalDate dataVencimento) {

    for (Mensalista mensalista : mensalistas) {
        if (mensalista.getPlaca().equalsIgnoreCase(placa)) {
            System.out.println("Ja existe um mensalista cadastrado com essa placa!");
            return;
        }
    }

    Mensalista novoMensalista = new Mensalista(nome, telefone, placa, dataVencimento);

    mensalistas.add(novoMensalista);

    System.out.println("Mensalista cadastrado com sucesso!");
    System.out.println("Nome: " + nome);
    System.out.println("Telefone: " + telefone);
    System.out.println("Placa: " + placa);
    System.out.println("Vencimento: " + dataVencimento);
}

public Mensalista buscarMensalista(String placa) {

    for (Mensalista mensalista : mensalistas) {
        if (mensalista.getPlaca().equalsIgnoreCase(placa)) {
            return mensalista;
        }
    }

    return null;
}



    

}

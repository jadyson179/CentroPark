import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

public class Estacionamento {

    private ArrayList<RegistroEntrada> registros;
    private ArrayList<RegistroEntrada> historico;
    private ArrayList<RegistroSaida> historicoSaidas;
    private ArrayList<Mensalista> mensalistas;

    private Scanner scanner = new Scanner(System.in);

    private final int LIMITE_CARROS = 60;
    private final int LIMITE_MOTOS = 20;

    private int proximoCodigo = 1;

    public Estacionamento() {
        registros = new ArrayList<>();
        historico = new ArrayList<>();
        historicoSaidas = new ArrayList<>();
        mensalistas = new ArrayList<>();
    };

    // Contar veiculos ativos de um determinado tipo

    private int contarVeiculos(Class<?> tipo) {
        int quantidade = 0;

        for (RegistroEntrada registro : registros) {

            if (registro.getVeiculo().getClass() == tipo) {
                quantidade++;
            }
        }

        return quantidade;

    }

    // verificar se a placa já está estacionada

    private boolean placaJaExiste(String placa) {

        for (RegistroEntrada registro : registros) {

            if (registro.getVeiculo().getPlaca().equalsIgnoreCase(placa)) {

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

        if (!horarioPermitido()) {
            System.out.println("Estacionamento fechado! Funcionamento das 06:00 às 22:00.");
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

    public void registrarSaida(String placa) {
        RegistroEntrada registroEncontrado = null;

        // procurar veiculo pela placa

        for (RegistroEntrada registro : registros) {
            if (registro.getVeiculo().getPlaca().equalsIgnoreCase(placa)) {
                registroEncontrado = registro;
                break;
            }
        }

        // verificar se o veiculo foi encontrado

        if (registroEncontrado == null) {
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

        while (!pagamentoValido) {

            System.out.println("\n=== FORMA DE PAGAMENTO ===");
            System.out.println("1 - Dinheiro");
            System.out.println("2 - Pix");
            System.out.println("3 - Cartao");
            System.out.print("Escolha uma opcao: ");

            try {
                opcao = scanner.nextInt();
                scanner.nextLine();

                if (opcao >= 1 && opcao <= 3) {
                    pagamentoValido = true;
                } else {
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

    public void exibirVagasDisponiveis() {

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

    public void consultarMensalista(String placa) {

        Mensalista mensalista = buscarMensalista(placa);

        if (mensalista == null) {
            System.out.println("Mensalista nao encontrado!");
            return;
        }

        System.out.println("\n=== DADOS DO MENSALISTA ===");
        System.out.println("Nome: " + mensalista.getNome());
        System.out.println("Telefone: " + mensalista.getTelefone());
        System.out.println("Placa: " + mensalista.getPlaca());
        System.out.println("Vencimento: " + mensalista.getDataVencimento());

        if (mensalista.estaEmAtraso()) {
            System.out.println("Situacao: PAGAMENTO ATRASADO");
            System.out.println("Cliente sera cobrado como avulso.");
        } else {
            System.out.println("Situacao: PAGAMENTO EM DIA");
            System.out.println("Cliente possui beneficio de mensalista.");
        }
    }

    public void fecharCaixa() {
        double totalDinheiro = 0.0;
        double totalPix = 0.0;
        double totalCartao = 0.0;
        double totalGeral = 0.0;

        for (RegistroSaida saida : historicoSaidas) {
            double valor = saida.getValorPago();
            String forma = saida.getFormaPagamento();

            totalGeral += valor;

            if (forma.equalsIgnoreCase("Dinheiro")) {
                totalDinheiro += valor;
            } else if (forma.equalsIgnoreCase("Pix")) {
                totalPix += valor;
            } else if (forma.equalsIgnoreCase("Cartão")
                    || forma.equalsIgnoreCase("Cartao")) {
                totalCartao += valor;
            }
        }

        System.out.println("\n===== FECHAMENTO DE CAIXA =====");
        System.out.printf("Total em dinheiro: R$ %.2f%n", totalDinheiro);
        System.out.printf("Total em Pix: R$ %.2f%n", totalPix);
        System.out.printf("Total em cartão: R$ %.2f%n", totalCartao);
        System.out.println("--------------------------------");
        System.out.printf("TOTAL GERAL: R$ %.2f%n", totalGeral);
        System.out.println("===============================");
    }

    public RegistroEntrada buscarPorCodigo(String codigo) {

        for (RegistroEntrada registro : registros) {

            if (registro.getCodigo().equalsIgnoreCase(codigo)) {
                return registro;
            }
        }

        return null;
    }

    public void exibirRelatorio() {

        int totalEntradas = historico.size();
        int totalSaidas = historicoSaidas.size();

        double totalArrecadado = 0.0;

        for (RegistroSaida saida : historicoSaidas) {
            totalArrecadado += saida.getValorPago();
        }

        int carrosEstacionados = contarVeiculos(Carro.class);
        int motosEstacionadas = contarVeiculos(Moto.class);

        System.out.println("\n===== RELATORIO DO ESTACIONAMENTO =====");

        System.out.println("Total de entradas: " + totalEntradas);
        System.out.println("Total de saidas: " + totalSaidas);

        System.out.println("Carros estacionados: " + carrosEstacionados);
        System.out.println("Motos estacionadas: " + motosEstacionadas);

        System.out.printf("Total arrecadado: R$ %.2f%n", totalArrecadado);

        System.out.println("=======================================");
    }

    public void alterarTarifas() {

        Scanner sc = new Scanner(System.in);

        System.out.println("\n=== ALTERAR TARIFAS ===");
        System.out.println("1 - Carros");
        System.out.println("2 - Motos");

        System.out.print("Escolha o tipo de veiculo: ");
        int opcao = sc.nextInt();

        System.out.print("Digite o valor da primeira hora: R$ ");
        double primeiraHora = sc.nextDouble();

        System.out.print("Digite o valor da hora adicional: R$ ");
        double adicional = sc.nextDouble();

        if (primeiraHora <= 0 || adicional <= 0) {
            System.out.println("Valores invalidos!");
            return;
        }

        if (opcao == 1) {
            Carro.alterarTarifas(primeiraHora, adicional);
            System.out.println("Tarifas dos carros alteradas!");

        } else if (opcao == 2) {
            Moto.alterarTarifas(primeiraHora, adicional);
            System.out.println("Tarifas das motos alteradas!");

        } else {
            System.out.println("Opcao invalida!");
        }
    }

    private boolean horarioPermitido() {
        int horaAtual = LocalDateTime.now().getHour();

        return horaAtual >= 6 && horaAtual < 22;
    }

}

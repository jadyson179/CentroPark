import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class App {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        Estacionamento estacionamento = new Estacionamento();
        LocalDate vencimento = LocalDate.now().minusDays(6);

        int opcao;
        do {
            System.out.println("================================");
            System.out.println("          CENTRO PARK           ");
            System.out.println("   SISTEMA DE ESTACIONAMENTO    ");
            System.out.println("================================");
            System.out.println("1 - Registrar entrada");
            System.out.println("2 - Registrar saida");
            System.out.println("3 - Consultar vagas disponiveis");
            System.out.println("4 - Cadastrar mensalista");
            System.out.println("5 - Consultar historico de saidas");
            System.out.println("6 - Consultar mensalista");
            System.out.println("7 - Regularizar pagamento de mensalista");
            System.out.println("8 - Fechar caixa");
            System.out.println("9 - Relatorio do estacionamento");
            System.out.println("10 - Alterar tarifas");
            System.out.println("0 - Sair");
            System.out.println("================================");

            System.out.println("Escolha uma opção: ");
            opcao = sc.nextInt();

            switch (opcao) {

                case 1:

                    sc.nextLine();

                    System.out.println("\n=== ENTRADA DE VEICULO ===");

                    System.out.print("Digite a placa: ");
                    String placaEntrada = sc.nextLine();

                    System.out.print("Tipo de veiculo (Carro/Moto): ");
                    String tipoVeiculo = sc.nextLine();

                    if (tipoVeiculo.equalsIgnoreCase("Carro")) {
                        Carro carro = new Carro(placaEntrada);
                        estacionamento.registrarEntrada(carro);

                    } else if (tipoVeiculo.equalsIgnoreCase("Moto")) {
                        Moto moto = new Moto(placaEntrada);
                        estacionamento.registrarEntrada(moto);

                    } else {
                        System.out.println("Tipo de veiculo invalido!");
                    }

                    break;

                case 2:
                    sc.nextLine();

                    System.out.println("\n=== SAIDA DE VEICULO ===");
                    System.out.println("1 - Buscar pela placa");
                    System.out.println("2 - Buscar pelo codigo");

                    System.out.print("Escolha uma opcao: ");
                    int tipoBusca = sc.nextInt();
                    sc.nextLine();

                    if (tipoBusca == 1) {
                        System.out.print("Digite a placa: ");
                        String placaSaida = sc.nextLine();

                        estacionamento.registrarSaida(placaSaida);

                    } else if (tipoBusca == 2) {
                        System.out.print("Digite o codigo: ");
                        String codigoSaida = sc.nextLine();

                        RegistroEntrada registro = estacionamento.buscarPorCodigo(codigoSaida);

                        if (registro != null) {
                            estacionamento.registrarSaida(
                                    registro.getVeiculo().getPlaca());
                        } else {
                            System.out.println("Codigo nao encontrado!");
                        }

                    } else {
                        System.out.println("Opcao invalida!");
                    }

                    break;

                case 3:
                    estacionamento.exibirVagasDisponiveis();
                    break;

                case 4:
                    sc.nextLine();

                    System.out.println("\n=== CADASTRO DE MENSALISTA ===");

                    System.out.print("Nome do cliente: ");
                    String nome = sc.nextLine();

                    System.out.print("Telefone: ");
                    String telefone = sc.nextLine();

                    System.out.print("Placa do veiculo: ");
                    String placa = sc.nextLine();

                    System.out.print("Data de vencimento (DD-MM-AAAA): ");
                    DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");

                    LocalDate dataVencimento = LocalDate.parse(
                            sc.nextLine(), formato);

                    estacionamento.cadastrarMensalista(nome, telefone, placa, vencimento);

                    break;

                case 5:
                    estacionamento.exibirHistoricoSaidas();
                    break;

                case 6:
                    sc.nextLine();

                    System.out.print("Digite a placa do mensalista: ");
                    String placaConsulta = sc.nextLine();

                    estacionamento.consultarMensalista(placaConsulta);
                    break;

                case 7:
                    sc.nextLine();

                    System.out.println("\n=== REGULARIZAR MENSALISTA ===");

                    System.out.print("Digite a placa do veiculo: ");
                    String placaRegularizacao = sc.nextLine();

                    Mensalista mensalista = estacionamento.buscarMensalista(placaRegularizacao);

                    if (mensalista == null) {
                        System.out.println("Mensalista nao encontrado!");
                    } else {
                        System.out.print("Digite a nova data de vencimento (DD/MM/AAAA): ");

                        DateTimeFormatter formatoRegularizacao = DateTimeFormatter.ofPattern("dd/MM/yyyy");

                        LocalDate novoVencimento = LocalDate.parse(
                                sc.nextLine(), formatoRegularizacao);

                        mensalista.regularizarPagamento(novoVencimento);
                    }

                    break;

                case 8:
                    estacionamento.fecharCaixa();
                    break;

                case 9:
                    estacionamento.exibirRelatorio();
                    break;

                case 10:
                    estacionamento.alterarTarifas();
                    break;

                case 0:

                    System.out.println("Encerrando o sistema...");
                    break;

                default:
                    System.out.println("Opção invalida! tente novamente.");

            }

        } while (opcao != 0);

        sc.close();
    }
}
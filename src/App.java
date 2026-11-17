public class App {

    public static void main(String[] args) {

        System.out.println("================================");
        System.out.println("          CENTRO PARK           ");
        System.out.println("   SISTEMA DE ESTACIONAMENTO    ");
        System.out.println("================================");

       // criando o estacionamento

       Estacionamento estacionamento = new Estacionamento();

       // criando veiculos

      Carro carro1 = new Carro("ABC1234");
      Moto moto1 = new Moto("DEF5678");

      // testando calculadora

      System.out.println("\n ==== TESTE DA CALCULADORA ====");
       
      System.out.println("Carro - 10 minutos: R$ " + Calculadora.calcularValor(carro1, 10));
      System.out.println("");
      System.out.println("Carro - 80 minutos: R$ " + Calculadora.calcularValor(carro1, 80));
      System.out.println("");
      System.out.println("Carro - 6 horas: R$ " + Calculadora.calcularValor(carro1, 360));
      System.out.println("");
      System.out.println("Moto - 80 minutos: R$ " + Calculadora.calcularValor(moto1, 80));
      System.out.println("");
      System.out.println("Moto - 6 horas: R$ " + Calculadora.calcularValor(moto1, 360));







       
      



    }
}
public class Carro extends Veiculos {

    public Carro (String placa){
        super (placa, "Carro");

    }

    @Override 

    public double calcularPrimeiraHora(){
        return 8.00;
    }

    @Override 

    public double calcularHoraAdicional(){
        return 3.00;
    }
    

}

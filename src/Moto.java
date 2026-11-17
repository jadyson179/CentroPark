public class Moto extends Veiculos {

    public Moto (String placa){
        super(placa, "Moto");
    }

    @Override 

    public double calcularPrimeiraHora(){
        return 4.00;
    }

    @Override 

    public double calcularHoraAdicional(){
        return 2.00;
    }
    
}

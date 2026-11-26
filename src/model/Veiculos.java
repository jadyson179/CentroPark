package model;

public abstract class Veiculos {

    // Atributos do veiculo

    private String placa;
    private String tipo;

    // contrutor

    public Veiculos(String placa, String tipo) {

        this.placa = placa;
        this.tipo = tipo;

    }

    // Getter e setter

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        if (placa != null && !placa.trim().isEmpty()) {
            this.placa = placa;
        } else {
            System.out.println("Placa inválida!");
        }

    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;

    }

    // Mostrar dados

    public void exibirDados() {
        System.out.println("Placa: " + placa);
        System.out.println("Tipo: " + tipo);
    }

    // Metodos abstratos

    public abstract double calcularPrimeiraHora();

    public abstract double calcularHoraAdicional();

}

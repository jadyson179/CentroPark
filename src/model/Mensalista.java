package model;

import java.time.LocalDate;

public class Mensalista {

    private String nome;
    private String telefone;
    private String placa;
    private LocalDate dataVencimento;

    public Mensalista(String nome,
            String telefone,
            String placa,
            LocalDate dataVencimento) {
        this.nome = nome;
        this.telefone = telefone;
        this.placa = placa;
        this.dataVencimento = dataVencimento;
    }

    public String getNome() {
        return nome;

    }

    public String getTelefone() {
        return telefone;
    }

    public String getPlaca() {
        return placa;
    }

    public LocalDate getDataVencimento() {
        return dataVencimento;
    }

    public boolean estaEmAtraso() {
        LocalDate hoje = LocalDate.now();

        return hoje.isAfter(dataVencimento.plusDays(5));
    }

    public void regularizarPagamento(LocalDate novoVencimento) {
        this.dataVencimento = novoVencimento;
        System.out.println("Pagamento regularizado com sucesso!");
    }

}

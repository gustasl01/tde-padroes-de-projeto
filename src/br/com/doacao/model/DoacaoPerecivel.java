package br.com.doacao.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DoacaoPerecivel extends Doacao {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private LocalDate dataValidade;

    public DoacaoPerecivel(int id, String doador, String item, int quantidade, LocalDate dataValidade) {
        super(id, doador, item, quantidade);
        this.dataValidade = dataValidade;
    }

    @Override
    public String getTipo() { return "PERECIVEL"; }

    public LocalDate getDataValidade() { return dataValidade; }
    public void setDataValidade(LocalDate dataValidade) { this.dataValidade = dataValidade; }

    @Override
    public String toString() {
        return super.toString() + " | Validade: " + dataValidade.format(FORMATTER);
    }
}
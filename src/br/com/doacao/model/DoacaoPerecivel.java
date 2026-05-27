package br.com.doacao.model;

public class DoacaoPerecivel extends Doacao {
    private String dataValidade;

    public DoacaoPerecivel(int id, String doador, String item, int quantidade, String dataValidade) {
        super(id, doador, item, quantidade);
        this.dataValidade = dataValidade;
    }

    @Override
    public String getTipo() { return "PERECIVEL"; }

    public String getDataValidade() { return dataValidade; }
    public void setDataValidade(String dataValidade) { this.dataValidade = dataValidade; }

    @Override
    public String toString() {
        return super.toString() + " | Validade: " + dataValidade;
    }
}
package br.com.doacao.model;

public abstract class Doacao {
    private int id;
    private String doador;
    private String item;
    private int quantidade;

    public Doacao(int id, String doador, String item, int quantidade) {
        this.id = id;
        this.doador = doador;
        this.item = item;
        this.quantidade = quantidade;
    }

    public abstract String getTipo();

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getDoador() { return doador; }
    public void setDoador(String doador) { this.doador = doador; }
    public String getItem() { return item; }
    public void setItem(String item) { this.item = item; }
    public int getQuantidade() { return quantidade; }
    public void setQuantidade(int quantidade) { this.quantidade = quantidade; }

    @Override
    public String toString() {
        return "[ID: " + id + "] Tipo: " + getTipo()
                + " | Doador: " + doador
                + " | Item: " + item
                + " | Qtd: " + quantidade;
    }
}
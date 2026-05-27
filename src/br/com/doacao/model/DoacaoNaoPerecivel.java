package br.com.doacao.model;

public class DoacaoNaoPerecivel extends Doacao {

    public DoacaoNaoPerecivel(int id, String doador, String item, int quantidade) {
        super(id, doador, item, quantidade);
    }

    @Override
    public String getTipo() { return "NAO PERECIVEL"; }
}
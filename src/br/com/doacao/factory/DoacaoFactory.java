package br.com.doacao.factory;

import br.com.doacao.model.Doacao;


public abstract class DoacaoFactory {
    public abstract Doacao criar(String doador, String item, int quantidade, String extra);
}
package br.com.doacao.factory;

import br.com.doacao.model.Doacao;
import br.com.doacao.model.DoacaoPerecivel;

public class DoacaoPereceFactory extends DoacaoFactory {
    @Override
    public Doacao criar(String doador, String item, int quantidade, String extra) {
        return new DoacaoPerecivel(0, doador, item, quantidade, extra);
    }
}
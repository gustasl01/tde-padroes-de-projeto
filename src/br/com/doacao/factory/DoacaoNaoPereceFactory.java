package br.com.doacao.factory;

import br.com.doacao.model.Doacao;
import br.com.doacao.model.DoacaoNaoPerecivel;
import java.time.LocalDate;

public class DoacaoNaoPereceFactory extends DoacaoFactory {
    @Override
    public Doacao criar(String doador, String item, int quantidade, LocalDate dataValidade) {
        return new DoacaoNaoPerecivel(0, doador, item, quantidade);
    }
}
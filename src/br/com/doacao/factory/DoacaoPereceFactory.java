package br.com.doacao.factory;

import br.com.doacao.model.Doacao;
import br.com.doacao.model.DoacaoPerecivel;
import java.time.LocalDate;

public class DoacaoPereceFactory extends DoacaoFactory {
    @Override
    public Doacao criar(String doador, String item, int quantidade, LocalDate dataValidade) {
        return new DoacaoPerecivel(0, doador, item, quantidade, dataValidade);
    }
}
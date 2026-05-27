package br.com.doacao.strategy;

import br.com.doacao.model.Doacao;
import java.util.List;

public interface ListagemStrategy {
    List<Doacao> listar(List<Doacao> doacoes);
}
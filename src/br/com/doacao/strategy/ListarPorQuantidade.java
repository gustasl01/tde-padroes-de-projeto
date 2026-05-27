package br.com.doacao.strategy;

import br.com.doacao.model.Doacao;
import java.util.Comparator;
import java.util.List;

public class ListarPorQuantidade implements ListagemStrategy {
    @Override
    public List<Doacao> listar(List<Doacao> doacoes) {
        return doacoes.stream()
                .sorted(Comparator.comparingInt(Doacao::getQuantidade).reversed())
                .toList();
    }
}
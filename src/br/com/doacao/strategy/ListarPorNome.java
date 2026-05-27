package br.com.doacao.strategy;

import br.com.doacao.model.Doacao;
import java.util.Comparator;
import java.util.List;

public class ListarPorNome implements ListagemStrategy {
    @Override
    public List<Doacao> listar(List<Doacao> doacoes) {
        return doacoes.stream()
                .sorted(Comparator.comparing(Doacao::getDoador))
                .toList();
    }
}
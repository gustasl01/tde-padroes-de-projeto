package br.com.doacao.repository;

import br.com.doacao.model.Doacao;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DoacaoRepository {

    private static DoacaoRepository instance; // unica instancia
    private final List<Doacao> doacoes = new ArrayList<>();
    private int proximoId = 1;

    private DoacaoRepository() {}

    public static DoacaoRepository getInstance() {
        if (instance == null) {
            instance = new DoacaoRepository();
        }
        return instance;
    }

    public void salvar(Doacao doacao) {
        doacao.setId(proximoId++);
        doacoes.add(doacao);
    }

    public List<Doacao> listarTodos() {
        return new ArrayList<>(doacoes);
    }

    public Optional<Doacao> buscarPorId(int id) {
        return doacoes.stream()
                .filter(d -> d.getId() == id)
                .findFirst();
    }

    public boolean atualizar(int id, String novoDoador, String novoItem, int novaQuantidade) {
        Optional<Doacao> opt = buscarPorId(id);
        if (opt.isPresent()) {
            Doacao d = opt.get();
            d.setDoador(novoDoador);
            d.setItem(novoItem);
            d.setQuantidade(novaQuantidade);
            return true;
        }
        return false;
    }

    public boolean remover(int id) {
        return doacoes.removeIf(d -> d.getId() == id);
    }
}
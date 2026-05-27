package br.com.doacao.service;

import br.com.doacao.factory.DoacaoFactory;
import br.com.doacao.factory.DoacaoNaoPereceFactory;
import br.com.doacao.factory.DoacaoPereceFactory;
import br.com.doacao.model.Doacao;
import br.com.doacao.repository.DoacaoRepository;
import br.com.doacao.strategy.ListagemStrategy;
import br.com.doacao.strategy.ListarPorNome;

import java.util.List;
import java.util.Optional;

public class DoacaoService {

    private final DoacaoRepository repository = DoacaoRepository.getInstance();

    private ListagemStrategy strategy = new ListarPorNome();

    public void setStrategy(ListagemStrategy strategy) {
        this.strategy = strategy;
    }

    public void registrar(int tipo, String doador, String item, int quantidade, String extra) {
        DoacaoFactory factory;
        if (tipo == 1) {
            factory = new DoacaoPereceFactory();
        } else {
            factory = new DoacaoNaoPereceFactory();
        }
        Doacao doacao = factory.criar(doador, item, quantidade, extra);
        repository.salvar(doacao);
        System.out.println("Doacao registrada com sucesso! ID: " + doacao.getId());
    }

    public List<Doacao> listar() {
        return strategy.listar(repository.listarTodos());
    }

    public Optional<Doacao> buscar(int id) {
        return repository.buscarPorId(id);
    }

    public boolean atualizar(int id, String doador, String item, int quantidade) {
        return repository.atualizar(id, doador, item, quantidade);
    }

    public boolean remover(int id) {
        return repository.remover(id);
    }
}
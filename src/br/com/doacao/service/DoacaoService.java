package br.com.doacao.service;

import br.com.doacao.factory.DoacaoFactory;
import br.com.doacao.factory.DoacaoNaoPereceFactory;
import br.com.doacao.factory.DoacaoPereceFactory;
import br.com.doacao.model.Doacao;
import br.com.doacao.model.DoacaoPerecivel;
import br.com.doacao.model.ResumoDoacoes;
import br.com.doacao.model.TipoDoacao;
import br.com.doacao.repository.DoacaoRepository;
import br.com.doacao.strategy.ListagemStrategy;
import br.com.doacao.strategy.ListarPorNome;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class DoacaoService {

    private final DoacaoRepository repository = DoacaoRepository.getInstance();

    public Doacao registrar(TipoDoacao tipo, String doador, String item, int quantidade, LocalDate dataValidade) {
        validarTexto("Doador", doador);
        validarTexto("Item", item);
        validarQuantidade(quantidade);

        DoacaoFactory factory;
        if (tipo == TipoDoacao.PERECIVEL) {
            if (dataValidade == null) {
                throw new IllegalArgumentException("Data de validade e obrigatoria para doacao perecivel.");
            }
            factory = new DoacaoPereceFactory();
        } else {
            factory = new DoacaoNaoPereceFactory();
        }
        Doacao doacao = factory.criar(doador, item, quantidade, dataValidade);
        repository.salvar(doacao);
        return doacao;
    }

    public List<Doacao> listar(ListagemStrategy strategy) {
        if (strategy == null) {
            strategy = new ListarPorNome();
        }
        return strategy.listar(repository.listarTodos());
    }

    public Optional<Doacao> buscar(int id) {
        return repository.buscarPorId(id);
    }

    public boolean atualizar(int id, String doador, String item, int quantidade) {
        validarTexto("Doador", doador);
        validarTexto("Item", item);
        validarQuantidade(quantidade);
        return repository.atualizar(id, doador, item, quantidade);
    }

    public boolean remover(int id) {
        return repository.remover(id);
    }

    public ResumoDoacoes obterResumo() {
        List<Doacao> doacoes = repository.listarTodos();
        int total = doacoes.size();
        int pereciveis = (int) doacoes.stream().filter(d -> d instanceof DoacaoPerecivel).count();
        int naoPereciveis = total - pereciveis;
        return new ResumoDoacoes(total, pereciveis, naoPereciveis);
    }

    private void validarTexto(String campo, String valor) {
        if (valor == null || valor.trim().isEmpty()) {
            throw new IllegalArgumentException(campo + " nao pode ficar vazio.");
        }
    }

    private void validarQuantidade(int quantidade) {
        if (quantidade <= 0) {
            throw new IllegalArgumentException("Quantidade deve ser maior que zero.");
        }
    }
}
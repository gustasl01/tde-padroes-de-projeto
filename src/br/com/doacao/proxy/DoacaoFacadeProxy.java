package br.com.doacao.proxy;

import br.com.doacao.facade.DoacaoFacade;
import br.com.doacao.model.Doacao;
import br.com.doacao.model.ResumoDoacoes;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

public class DoacaoFacadeProxy implements DoacaoFacade {

    private static final DateTimeFormatter LOG_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    private final DoacaoFacade target;

    public DoacaoFacadeProxy(DoacaoFacade target) {
        this.target = target;
    }

    @Override
    public Doacao cadastrar(int tipoOpcao, String doador, String item, int quantidade, String dataValidadeTexto) {
        log("Cadastrar doacao para doador: " + safeText(doador));
        Doacao doacao = target.cadastrar(tipoOpcao, doador, item, quantidade, dataValidadeTexto);
        log("Doacao cadastrada com ID: " + doacao.getId());
        return doacao;
    }

    @Override
    public List<Doacao> listar(int ordenacaoOpcao) {
        log("Listar doacoes com ordenacao opcao: " + ordenacaoOpcao);
        return target.listar(ordenacaoOpcao);
    }

    @Override
    public ResumoDoacoes obterResumo() {
        return target.obterResumo();
    }

    @Override
    public int exportarCsv(String caminhoArquivo, int ordenacaoOpcao) {
        log("Exportar CSV para: " + safeText(caminhoArquivo));
        int total = target.exportarCsv(caminhoArquivo, ordenacaoOpcao);
        log("CSV exportado com " + total + " registros.");
        return total;
    }

    @Override
    public Optional<Doacao> buscar(int id) {
        log("Buscar doacao por ID: " + id);
        return target.buscar(id);
    }

    @Override
    public boolean atualizar(int id, String doador, String item, int quantidade) {
        log("Atualizar doacao ID: " + id);
        return target.atualizar(id, doador, item, quantidade);
    }

    @Override
    public boolean remover(int id) {
        log("Remover doacao ID: " + id);
        return target.remover(id);
    }

    private void log(String mensagem) {
        System.out.println("[Proxy " + LocalDateTime.now().format(LOG_FORMAT) + "] " + mensagem);
    }

    private String safeText(String value) {
        if (value == null || value.trim().isEmpty()) {
            return "<vazio>";
        }
        return value.trim();
    }
}

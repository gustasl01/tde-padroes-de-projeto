package br.com.doacao.facade;

import br.com.doacao.model.Doacao;
import br.com.doacao.model.ResumoDoacoes;
import java.util.List;
import java.util.Optional;

public interface DoacaoFacade {
    Doacao cadastrar(int tipoOpcao, String doador, String item, int quantidade, String dataValidadeTexto);
    List<Doacao> listar(int ordenacaoOpcao);
    ResumoDoacoes obterResumo();
    int exportarCsv(String caminhoArquivo, int ordenacaoOpcao);
    Optional<Doacao> buscar(int id);
    boolean atualizar(int id, String doador, String item, int quantidade);
    boolean remover(int id);
}

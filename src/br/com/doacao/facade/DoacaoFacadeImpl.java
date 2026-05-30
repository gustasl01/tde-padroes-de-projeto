package br.com.doacao.facade;

import br.com.doacao.adapter.DataValidadeAdapter;
import br.com.doacao.adapter.DataValidadeAdapterPadrao;
import br.com.doacao.model.Doacao;
import br.com.doacao.model.DoacaoPerecivel;
import br.com.doacao.model.ResumoDoacoes;
import br.com.doacao.model.TipoDoacao;
import br.com.doacao.service.DoacaoService;
import br.com.doacao.strategy.ListagemStrategy;
import br.com.doacao.strategy.ListarPorNome;
import br.com.doacao.strategy.ListarPorQuantidade;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DoacaoFacadeImpl implements DoacaoFacade {

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private final DoacaoService service;
    private final DataValidadeAdapter dataAdapter;

    public DoacaoFacadeImpl() {
        this(new DoacaoService(), new DataValidadeAdapterPadrao());
    }

    public DoacaoFacadeImpl(DoacaoService service, DataValidadeAdapter dataAdapter) {
        this.service = service;
        this.dataAdapter = dataAdapter;
    }

    @Override
    public Doacao cadastrar(int tipoOpcao, String doador, String item, int quantidade, String dataValidadeTexto) {
        TipoDoacao tipo = TipoDoacao.fromOpcao(tipoOpcao);
        LocalDate dataValidade = null;
        if (tipo == TipoDoacao.PERECIVEL) {
            dataValidade = dataAdapter.parse(dataValidadeTexto);
        }
        return service.registrar(tipo, doador, item, quantidade, dataValidade);
    }

    @Override
    public List<Doacao> listar(int ordenacaoOpcao) {
        ListagemStrategy strategy = (ordenacaoOpcao == 2)
                ? new ListarPorQuantidade()
                : new ListarPorNome();
        return service.listar(strategy);
    }

    @Override
    public ResumoDoacoes obterResumo() {
        return service.obterResumo();
    }

    @Override
    public int exportarCsv(String caminhoArquivo, int ordenacaoOpcao) {
        List<Doacao> doacoes = listar(ordenacaoOpcao);
        List<String> linhas = new ArrayList<>();
        linhas.add("id,tipo,doador,item,quantidade,validade");

        for (Doacao doacao : doacoes) {
            String validade = "";
            if (doacao instanceof DoacaoPerecivel perecivel && perecivel.getDataValidade() != null) {
                validade = perecivel.getDataValidade().format(DATE_FORMAT);
            }
            linhas.add(String.join(",",
                    String.valueOf(doacao.getId()),
                    escaparCsv(doacao.getTipo()),
                    escaparCsv(doacao.getDoador()),
                    escaparCsv(doacao.getItem()),
                    String.valueOf(doacao.getQuantidade()),
                    escaparCsv(validade)
            ));
        }

        try {
            Path destino = Path.of(caminhoArquivo).toAbsolutePath();
            if (destino.getParent() != null) {
                Files.createDirectories(destino.getParent());
            }
            Files.write(destino, linhas, StandardCharsets.UTF_8);
            return doacoes.size();
        } catch (IOException e) {
            throw new IllegalArgumentException("Falha ao exportar CSV: " + e.getMessage());
        }
    }

    @Override
    public Optional<Doacao> buscar(int id) {
        return service.buscar(id);
    }

    @Override
    public boolean atualizar(int id, String doador, String item, int quantidade) {
        return service.atualizar(id, doador, item, quantidade);
    }

    @Override
    public boolean remover(int id) {
        return service.remover(id);
    }

    private String escaparCsv(String valor) {
        if (valor == null) {
            return "\"\"";
        }
        String normalizado = valor.replace("\"", "\"\"");
        return "\"" + normalizado + "\"";
    }
}

package br.com.doacao.view;

import br.com.doacao.facade.DoacaoFacade;
import br.com.doacao.facade.DoacaoFacadeImpl;
import br.com.doacao.model.Doacao;
import br.com.doacao.model.DoacaoPerecivel;
import br.com.doacao.model.ResumoDoacoes;
import br.com.doacao.proxy.DoacaoFacadeProxy;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class MenuView {

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final String TABLE_BORDER = "+------+---------------+----------------------+----------------------+--------+------------+";
    private static final String TABLE_HEADER = "| ID   | TIPO          | DOADOR               | ITEM                 | QTD    | VALIDADE   |";

    private final DoacaoFacade facade = new DoacaoFacadeProxy(new DoacaoFacadeImpl());
    private final Scanner scanner = new Scanner(System.in);

    public void iniciar() {
        System.out.println("=========================================");
        System.out.println("  SISTEMA DE DOACOES DE ALIMENTOS");
        System.out.println("  ODS 2 - Fome Zero | Fome Zero");
        System.out.println("=========================================");

        int opcao;
        do {
            exibirMenu();
            opcao = lerInt();
            switch (opcao) {
                case 1 -> cadastrar();
                case 2 -> listar();
                case 3 -> atualizar();
                case 4 -> remover();
                case 5 -> buscar();
                case 6 -> exportarCsv();
                case 0 -> System.out.println("Encerrando o sistema.");
                default -> System.out.println("Opcao invalida. Tente novamente.");
            }
        } while (opcao != 0);
    }

    private void exibirMenu() {
        exibirCardsResumo();
        System.out.println("\n--- MENU PRINCIPAL ---");
        System.out.println("1 - Cadastrar doacao");
        System.out.println("2 - Listar doacoes");
        System.out.println("3 - Atualizar doacao");
        System.out.println("4 - Remover doacao");
        System.out.println("5 - Buscar por ID");
        System.out.println("6 - Exportar listagem CSV");
        System.out.println("0 - Sair");
        System.out.print("Escolha: ");
    }

    private void exibirCardsResumo() {
        ResumoDoacoes resumo = facade.obterResumo();

        System.out.println("\n+----------------------+ +----------------------+ +----------------------+");
        System.out.println("| TOTAL DE DOACOES     | | PERECIVEIS           | | NAO PERECIVEIS       |");
        System.out.printf("| %-20d | | %-20d | | %-20d |%n",
                resumo.getTotal(),
                resumo.getPereciveis(),
                resumo.getNaoPereciveis());
        System.out.println("+----------------------+ +----------------------+ +----------------------+");
    }

    private void cadastrar() {
        System.out.println("\n-- CADASTRAR DOACAO --");
        try {
            System.out.print("Nome do doador: ");
            String doador = scanner.nextLine();
            System.out.print("Item doado: ");
            String item = scanner.nextLine();
            System.out.print("Quantidade (kg ou unidades): ");
            int quantidade = lerInt();
            System.out.println("Tipo: 1 - Perecivel  |  2 - Nao Perecivel");
            System.out.print("Tipo: ");
            int tipo = lerInt();
            String dataValidade = "";
            if (tipo == 1) {
                System.out.print("Data de validade (DD/MM/AAAA): ");
                dataValidade = scanner.nextLine();
            }

            Doacao doacao = facade.cadastrar(tipo, doador, item, quantidade, dataValidade);
            System.out.println("\n[SUCESSO] Doacao registrada com sucesso!");
            exibirTabelaCabecalho();
            exibirLinhaDoacao(doacao);
            exibirTabelaRodape();
        } catch (IllegalArgumentException ex) {
            System.out.println("ERRO: " + ex.getMessage());
        }
    }

    private void listar() {
        System.out.println("\nOrdenar por: 1 - Nome do doador  |  2 - Quantidade");
        System.out.print("Opcao: ");
        int op = lerInt();
        List<Doacao> lista = facade.listar(op);
        if (lista.isEmpty()) {
            System.out.println("Nenhuma doacao cadastrada ainda.");
        } else {
            System.out.println("\n-- LISTA DE DOACOES --");
            exibirTabelaCabecalho();
            lista.forEach(this::exibirLinhaDoacao);
            exibirTabelaRodape();
            System.out.println("Total de registros: " + lista.size());
        }
    }

    private void atualizar() {
        System.out.println("\n-- ATUALIZAR DOACAO --");
        try {
            System.out.print("ID da doacao: ");
            int id = lerInt();
            System.out.print("Novo nome do doador: ");
            String doador = scanner.nextLine();
            System.out.print("Novo item: ");
            String item = scanner.nextLine();
            System.out.print("Nova quantidade: ");
            int qtd = lerInt();
            boolean ok = facade.atualizar(id, doador, item, qtd);
            System.out.println(ok ? "[SUCESSO] Doacao atualizada com sucesso!" : "ERRO: doacao nao encontrada.");
        } catch (IllegalArgumentException ex) {
            System.out.println("ERRO: " + ex.getMessage());
        }
    }

    private void remover() {
        System.out.println("\n-- REMOVER DOACAO --");
        System.out.print("ID da doacao a remover: ");
        int id = lerInt();
        System.out.print("Confirmar remocao? (s/n): ");
        String confirmar = scanner.nextLine();
        if (!confirmar.equalsIgnoreCase("s")) {
            System.out.println("Operacao cancelada.");
            return;
        }
        boolean ok = facade.remover(id);
        System.out.println(ok ? "[SUCESSO] Doacao removida com sucesso!" : "ERRO: doacao nao encontrada.");
    }

    private void buscar() {
        System.out.println("\n-- BUSCAR POR ID --");
        System.out.print("ID: ");
        int id = lerInt();
        Optional<Doacao> resultado = facade.buscar(id);
        resultado.ifPresentOrElse(
                doacao -> {
                    System.out.println("\nRegistro encontrado:");
                    exibirTabelaCabecalho();
                    exibirLinhaDoacao(doacao);
                    exibirTabelaRodape();
                },
                () -> System.out.println("Doacao nao encontrada.")
        );
    }

    private void exportarCsv() {
        System.out.println("\n-- EXPORTAR CSV --");
        System.out.println("Ordenar por: 1 - Nome do doador  |  2 - Quantidade");
        System.out.print("Opcao de ordenacao: ");
        int op = lerInt();

        System.out.print("Nome do arquivo CSV (enter para doacoes.csv): ");
        String caminho = scanner.nextLine().trim();
        if (caminho.isEmpty()) {
            caminho = "doacoes.csv";
        }

        try {
            int total = facade.exportarCsv(caminho, op);
            System.out.println("[SUCESSO] CSV exportado em: " + caminho);
            System.out.println("Registros exportados: " + total);
        } catch (IllegalArgumentException ex) {
            System.out.println("ERRO: " + ex.getMessage());
        }
    }

    private void exibirTabelaCabecalho() {
        System.out.println(TABLE_BORDER);
        System.out.println(TABLE_HEADER);
        System.out.println(TABLE_BORDER);
    }

    private void exibirTabelaRodape() {
        System.out.println(TABLE_BORDER);
    }

    private void exibirLinhaDoacao(Doacao doacao) {
        String validade = "-";
        if (doacao instanceof DoacaoPerecivel perecivel && perecivel.getDataValidade() != null) {
            validade = perecivel.getDataValidade().format(DATE_FORMAT);
        }

        System.out.printf(
                "| %-4d | %-13s | %-20s | %-20s | %-6d | %-10s |%n",
                doacao.getId(),
                truncar(doacao.getTipo(), 13),
                truncar(doacao.getDoador(), 20),
                truncar(doacao.getItem(), 20),
                doacao.getQuantidade(),
                validade
        );
    }

    private String truncar(String valor, int tamanho) {
        if (valor == null) {
            return "";
        }
        if (valor.length() <= tamanho) {
            return valor;
        }
        return valor.substring(0, Math.max(0, tamanho - 3)) + "...";
    }

    private int lerInt() {
        while (!scanner.hasNextInt()) {
            System.out.print("Digite um numero valido: ");
            scanner.next();
        }
        int val = scanner.nextInt();
        scanner.nextLine(); // limpa o buffer
        return val;
    }
}
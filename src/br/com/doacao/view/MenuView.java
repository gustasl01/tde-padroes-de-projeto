package br.com.doacao.view;

import br.com.doacao.model.Doacao;
import br.com.doacao.service.DoacaoService;
import br.com.doacao.strategy.ListarPorNome;
import br.com.doacao.strategy.ListarPorQuantidade;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class MenuView {

    private final DoacaoService service = new DoacaoService();
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
                case 0 -> System.out.println("Encerrando o sistema.");
                default -> System.out.println("Opcao invalida. Tente novamente.");
            }
        } while (opcao != 0);
    }

    private void exibirMenu() {
        System.out.println("\n--- MENU PRINCIPAL ---");
        System.out.println("1 - Cadastrar doacao");
        System.out.println("2 - Listar doacoes");
        System.out.println("3 - Atualizar doacao");
        System.out.println("4 - Remover doacao");
        System.out.println("5 - Buscar por ID");
        System.out.println("0 - Sair");
        System.out.print("Escolha: ");
    }

    private void cadastrar() {
        System.out.println("\n-- CADASTRAR DOACAO --");
        System.out.print("Nome do doador: ");
        String doador = scanner.nextLine();
        System.out.print("Item doado: ");
        String item = scanner.nextLine();
        System.out.print("Quantidade (kg ou unidades): ");
        int quantidade = lerInt();
        System.out.println("Tipo: 1 - Perecivel  |  2 - Nao Perecivel");
        System.out.print("Tipo: ");
        int tipo = lerInt();
        String extra = "";
        if (tipo == 1) {
            System.out.print("Data de validade (DD/MM/AAAA): ");
            extra = scanner.nextLine();
        }
        service.registrar(tipo, doador, item, quantidade, extra);
    }

    private void listar() {
        System.out.println("\nOrdenar por: 1 - Nome do doador  |  2 - Quantidade");
        System.out.print("Opcao: ");
        int op = lerInt();
        if (op == 2) {
            service.setStrategy(new ListarPorQuantidade());
        } else {
            service.setStrategy(new ListarPorNome());
        }
        List<Doacao> lista = service.listar();
        if (lista.isEmpty()) {
            System.out.println("Nenhuma doacao cadastrada ainda.");
        } else {
            System.out.println("\n-- LISTA DE DOACOES --");
            lista.forEach(System.out::println);
        }
    }

    private void atualizar() {
        System.out.println("\n-- ATUALIZAR DOACAO --");
        System.out.print("ID da doacao: ");
        int id = lerInt();
        System.out.print("Novo nome do doador: ");
        String doador = scanner.nextLine();
        System.out.print("Novo item: ");
        String item = scanner.nextLine();
        System.out.print("Nova quantidade: ");
        int qtd = lerInt();
        boolean ok = service.atualizar(id, doador, item, qtd);
        System.out.println(ok ? "Doacao atualizada com sucesso!" : "ERRO: doacao nao encontrada.");
    }

    private void remover() {
        System.out.println("\n-- REMOVER DOACAO --");
        System.out.print("ID da doacao a remover: ");
        int id = lerInt();
        boolean ok = service.remover(id);
        System.out.println(ok ? "Doacao removida com sucesso!" : "ERRO: doacao nao encontrada.");
    }

    private void buscar() {
        System.out.println("\n-- BUSCAR POR ID --");
        System.out.print("ID: ");
        int id = lerInt();
        Optional<Doacao> resultado = service.buscar(id);
        resultado.ifPresentOrElse(
                System.out::println,
                () -> System.out.println("Doacao nao encontrada.")
        );
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
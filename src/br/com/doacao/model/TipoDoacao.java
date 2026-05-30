package br.com.doacao.model;

public enum TipoDoacao {
    PERECIVEL,
    NAO_PERECIVEL;

    public static TipoDoacao fromOpcao(int opcao) {
        return switch (opcao) {
            case 1 -> PERECIVEL;
            case 2 -> NAO_PERECIVEL;
            default -> throw new IllegalArgumentException("Tipo invalido. Use 1 para perecivel ou 2 para nao perecivel.");
        };
    }
}

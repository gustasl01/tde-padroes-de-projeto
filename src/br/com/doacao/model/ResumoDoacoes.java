package br.com.doacao.model;

public class ResumoDoacoes {
    private final int total;
    private final int pereciveis;
    private final int naoPereciveis;

    public ResumoDoacoes(int total, int pereciveis, int naoPereciveis) {
        this.total = total;
        this.pereciveis = pereciveis;
        this.naoPereciveis = naoPereciveis;
    }

    public int getTotal() {
        return total;
    }

    public int getPereciveis() {
        return pereciveis;
    }

    public int getNaoPereciveis() {
        return naoPereciveis;
    }
}

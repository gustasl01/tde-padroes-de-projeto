package br.com.doacao.adapter;

import java.time.LocalDate;

public interface DataValidadeAdapter {
    LocalDate parse(String textoData);
    String format(LocalDate data);
}

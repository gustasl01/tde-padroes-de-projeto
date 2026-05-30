package br.com.doacao.adapter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.Locale;

public class DataValidadeAdapterPadrao implements DataValidadeAdapter {

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("dd/MM/uuuu", Locale.forLanguageTag("pt-BR"))
                    .withResolverStyle(ResolverStyle.STRICT);

    @Override
    public LocalDate parse(String textoData) {
        if (textoData == null || textoData.trim().isEmpty()) {
            throw new IllegalArgumentException("Data de validade obrigatoria para doacao perecivel.");
        }
        try {
            return LocalDate.parse(textoData.trim(), FORMATTER);
        } catch (DateTimeParseException ex) {
            throw new IllegalArgumentException("Data invalida. Use o formato DD/MM/AAAA.");
        }
    }

    @Override
    public String format(LocalDate data) {
        if (data == null) {
            return "-";
        }
        return data.format(FORMATTER);
    }
}

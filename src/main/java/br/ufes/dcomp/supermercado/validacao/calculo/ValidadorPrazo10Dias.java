package br.ufes.dcomp.supermercado.validacao.calculo;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class ValidadorPrazo10Dias extends ValidadorCalculoHandler {

    @Override
    protected void realizarValidacao(LocalDate dataAtual, LocalDate dataUltimoCalculo) throws RuntimeException {
        if (dataUltimoCalculo != null) {
            long diasPassados = ChronoUnit.DAYS.between(dataUltimoCalculo, dataAtual);
            if (diasPassados < 10) {
                throw new RuntimeException("O cálculo só pode ser realizado novamente após 10 dias.");
            }
        }
    }
}
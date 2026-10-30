
package br.ufes.dcomp.supermercado.repositorio;

import br.ufes.dcomp.supermercado.model.Cupom;
import java.util.List;
import java.util.Optional;

public interface ICupomRepository {
    void adicionarCupom(Cupom cupom);
    Optional<Cupom> getPorCodigo(String codigo);
    List<Cupom> getCuponsDisponiveis();
}

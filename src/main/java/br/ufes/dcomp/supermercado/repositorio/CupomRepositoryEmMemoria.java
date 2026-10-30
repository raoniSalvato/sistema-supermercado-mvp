
package br.ufes.dcomp.supermercado.repositorio;

import br.ufes.dcomp.supermercado.model.Cupom;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class CupomRepositoryEmMemoria implements ICupomRepository {

    private List<Cupom> cuponsDisponiveis;

    public CupomRepositoryEmMemoria(){
        this.cuponsDisponiveis = new ArrayList<>();
        this.cuponsDisponiveis.add(new Cupom("DESC10", 10.0, LocalDateTime.of(2026, 9, 25, 0, 0), LocalDateTime.of(2026, 9, 27, 23, 59)));
        this.cuponsDisponiveis.add(new Cupom("DESC20", 20.0, LocalDateTime.of(2026, 10, 1, 0, 0), LocalDateTime.of(2026, 10, 5, 23, 59)));
        this.cuponsDisponiveis.add(new Cupom("DESC30", 30.0, LocalDateTime.of(2026, 9, 24, 0, 0), LocalDateTime.of(2026, 9, 24, 23, 59)));
        this.cuponsDisponiveis.add(new Cupom("DIAPAI12", 12.0, LocalDateTime.of(2026, 10, 9, 0, 0), LocalDateTime.of(2026, 10, 10, 23, 59)));
        this.cuponsDisponiveis.add(new Cupom("DIAMAE12", 12.0, LocalDateTime.of(2026, 10, 10, 0, 0), LocalDateTime.of(2026, 10, 12, 23, 59)));
        this.cuponsDisponiveis.add(new Cupom("NATAL10", 10.0, LocalDateTime.of(2026, 9, 20, 0, 0), LocalDateTime.of(2026, 9, 26, 23, 59)));
        this.cuponsDisponiveis.add(new Cupom("FESTA15", 15.0, LocalDateTime.of(2026, 9, 30, 18, 0), LocalDateTime.of(2026, 10, 1, 6, 0)));
        this.cuponsDisponiveis.add(new Cupom("BLACK50", 50.0, LocalDateTime.of(2026, 9, 28, 0, 0), LocalDateTime.of(2026, 9, 28, 23, 59)));
    }

    @Override
    public void adicionarCupom(Cupom cupom){
        this.cuponsDisponiveis.add(cupom);
    }

    @Override
    public Optional<Cupom> getPorCodigo(String codigo){
        for(Cupom cupom: cuponsDisponiveis){
            if(cupom.getCodigo().equalsIgnoreCase(codigo)){
                return Optional.of(cupom);
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Cupom> getCuponsDisponiveis(){
        return Collections.unmodifiableList(this.cuponsDisponiveis);
    }

}

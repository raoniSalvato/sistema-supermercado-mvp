
package br.ufes.dcomp.supermercado.servico;

import br.ufes.dcomp.supermercado.exception.CupomInvalidoException;
import br.ufes.dcomp.supermercado.model.Cupom;
import br.ufes.dcomp.supermercado.model.Pedido;
import br.ufes.dcomp.supermercado.repositorio.ICupomRepository;
import java.time.LocalDateTime;
import java.util.Optional;

public class AplicadorDeCupomService {
    private ICupomRepository repositorioDeCupons;

    public AplicadorDeCupomService(ICupomRepository repositorioDeCupons){
        this.repositorioDeCupons = repositorioDeCupons;
    }

    public Cupom validarERecuperarCupom(String codigoCupom, LocalDateTime dataAtual, double percentualDescontoAtual){
        Optional<Cupom> cupomOptional = repositorioDeCupons.getPorCodigo(codigoCupom);

        if(cupomOptional.isEmpty()){
            throw new CupomInvalidoException("O cupom " + codigoCupom + " não existe.");
        }

        Cupom cupomEncontrado = cupomOptional.get();

        if(!cupomEncontrado.estaValido(dataAtual)){
            throw new CupomInvalidoException("O cupom " + codigoCupom + " está fora do período de validade.");
        }
        
         if(percentualDescontoAtual >= cupomEncontrado.getPercentualFixoDesconto()){
            throw new CupomInvalidoException("O cupom já aplicado possui desconto igual ou maior que o novo cupom.");
        }
        
        return cupomEncontrado;
    }
}

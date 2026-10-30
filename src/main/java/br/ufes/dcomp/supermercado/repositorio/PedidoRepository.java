
package br.ufes.dcomp.supermercado.repositorio;

import br.projeto.painelpedidos.model.PedidoAbstract;
import br.projeto.painelpedidos.repositorio.IPedidoRepository;
import java.util.ArrayList;
import java.util.List;

public class PedidoRepository implements IPedidoRepository {
    private List<PedidoAbstract> bancoDeDados = new ArrayList<>();
    private int contadorId = 4000; // Começa no 4000, conforme a especificação da POC

    @Override
    public void salvar(PedidoAbstract pedido) {
        if (!bancoDeDados.contains(pedido)) {
            bancoDeDados.add(pedido);
        }
        System.out.println("Pedido guardado no repositório: " + pedido.getIdentificador());
    }

    @Override
    public List<PedidoAbstract> buscarTodos() {
        return bancoDeDados;
    }

    @Override
    public String gerarProximoIdentificador() {
        contadorId++;
        return "#" + contadorId;
    }
}

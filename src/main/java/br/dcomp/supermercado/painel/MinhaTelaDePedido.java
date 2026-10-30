
package br.dcomp.supermercado.painel;

import br.projeto.painelpedidos.listener.IVisualizarPedidoListener;
import br.projeto.painelpedidos.model.PedidoAbstract;
import br.ufes.dcomp.supermercado.model.Pedido;
import br.ufes.dcomp.supermercado.presenter.RealizarPedidoPresenter;
import br.ufes.dcomp.supermercado.repositorio.PedidoRepository;

public class MinhaTelaDePedido implements IVisualizarPedidoListener {
    
    private PedidoRepository pedidoRepository;

    public MinhaTelaDePedido(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    @Override
    public void visualizarPedido(PedidoAbstract pedidoAbstract) {
        Pedido pedidoCompleto = null;
        
        for (PedidoAbstract pAbs : pedidoRepository.buscarTodos()) {
            if (pAbs.getIdentificador().equals(pedidoAbstract.getIdentificador())) {
                pedidoCompleto = (Pedido) pAbs; 
                break;
            }
        }
        
        if (pedidoCompleto == null) {
            pedidoCompleto = (Pedido) pedidoAbstract;
        }
        
        RealizarPedidoPresenter presenterLeitura = new RealizarPedidoPresenter(pedidoCompleto);
        presenterLeitura.exibirTelaModal();
    }
}

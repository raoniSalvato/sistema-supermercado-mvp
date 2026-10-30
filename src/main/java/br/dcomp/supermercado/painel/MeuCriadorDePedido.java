package br.dcomp.supermercado.painel;

import br.projeto.painelpedidos.listener.ICriarNovoPedidoListener;
import br.projeto.painelpedidos.model.PedidoAbstract;
import br.ufes.dcomp.supermercado.model.Pedido;
import br.ufes.dcomp.supermercado.model.Usuario;
import br.ufes.dcomp.supermercado.repositorio.PedidoRepository;
import br.ufes.dcomp.supermercado.presenter.RealizarPedidoPresenter;
import br.ufes.dcomp.supermercado.repositorio.ProdutoRepository;
import br.ufes.dcomp.supermercado.repositorio.UsuarioRepository;
import java.util.ArrayList;
import java.util.List;

public class MeuCriadorDePedido implements ICriarNovoPedidoListener {
    
    private PedidoRepository pedidoRepository;
    private ProdutoRepository produtoRepository;
    private UsuarioRepository usuarioRepository;
    private Usuario usuarioLogado;

    public MeuCriadorDePedido(PedidoRepository pedidoRepository, ProdutoRepository produtoRepository, 
                              UsuarioRepository usuarioRepository, Usuario usuarioLogado) {
        this.pedidoRepository = pedidoRepository;
        this.produtoRepository = produtoRepository;
        this.usuarioRepository = usuarioRepository;
        this.usuarioLogado = usuarioLogado;
    }

    @Override
    public List<PedidoAbstract> criarNovoPedido() {
        RealizarPedidoPresenter presenter = new RealizarPedidoPresenter(
                pedidoRepository, produtoRepository, usuarioRepository, usuarioLogado);
        
        presenter.exibirTelaModal();
        List<PedidoAbstract> novosPedidos = new ArrayList<>();
        Pedido pedidoFeito = presenter.getPedidoCriado();
        
        if (pedidoFeito != null) {
            novosPedidos.add(pedidoFeito); 
        }
        return novosPedidos;
    }
}
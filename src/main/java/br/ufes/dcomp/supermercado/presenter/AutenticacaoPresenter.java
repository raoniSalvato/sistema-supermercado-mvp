
package br.ufes.dcomp.supermercado.presenter;

import br.projeto.painelpedidos.aplicacao.PainelPedidoFacade;
import br.projeto.painelpedidos.configuracao.ConfiguracaoPainelPedido;
import br.projeto.painelpedidos.configuracao.LeitorConfiguracaoPainelPedido;
import br.projeto.painelpedidos.model.PedidoAbstract;
import br.projeto.painelpedidos.som.SomTomStrategy;
import br.projeto.painelpedidos.tempo.RelogioServiceSistema;
import br.ufes.dcomp.supermercado.model.Pedido;
import br.ufes.dcomp.supermercado.model.StatusUsuario;
import br.ufes.dcomp.supermercado.model.Usuario;
import br.ufes.dcomp.supermercado.repositorio.CategoriaRepository;
import br.ufes.dcomp.supermercado.repositorio.HistoricoPrecoRepository;
import br.ufes.dcomp.supermercado.repositorio.PedidoRepository;
import br.ufes.dcomp.supermercado.repositorio.ProdutoRepository;
import br.ufes.dcomp.supermercado.repositorio.UsuarioRepository;
import br.ufes.dcomp.supermercado.servico.CalculoPrecoServico;
import br.ufes.dcomp.supermercado.servico.CategoriaServico;
import br.ufes.dcomp.supermercado.view.AutenticacaoView;
import java.time.LocalDateTime;
import java.util.List;
import javax.swing.JOptionPane;
import br.dcomp.supermercado.painel.MeuCriadorDePedido;
import br.dcomp.supermercado.painel.MeuPagamento;
import br.dcomp.supermercado.painel.MinhaTelaDePedido;

public class AutenticacaoPresenter {
    private AutenticacaoView view;
    private UsuarioRepository usuarioRepository;
    
    public AutenticacaoPresenter(UsuarioRepository usuarioRepository){
        this.usuarioRepository = usuarioRepository;
        this.view = new AutenticacaoView();
        
        this.view.setLocationRelativeTo(null);
        this.view.setResizable(false);
        
        configurarListeners();
        this.view.setVisible(true);
    }

    private void configurarListeners() {
        view.getBtnEntrar().addActionListener(e -> autenticar());
        view.getBtnFechar().addActionListener(e -> System.exit(0));
    }

    private void autenticar() {
        String identificacao = view.getTextUsuarioEmail().getText();
        
        String senha = new String(view.getTextSenha().getPassword());
        
        if(identificacao.trim().isEmpty() || senha.trim().isEmpty()){
            JOptionPane.showMessageDialog(view, "Por favor, preencha o usuário/e-mail e a senha.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        Usuario usuarioEncontrado = usuarioRepository.buscarPorIdentificacao(identificacao);

        if (usuarioEncontrado == null || !usuarioEncontrado.getSenha().equals(senha)) {
            JOptionPane.showMessageDialog(view, "Credenciais inválidas.", "Erro de Autenticação", JOptionPane.ERROR_MESSAGE);
            return; 
        }

        if (usuarioEncontrado.getStatus() == StatusUsuario.DESABILITADO) {
            JOptionPane.showMessageDialog(view, "Acesso negado. O seu utilizador encontra-se desabilitado.", "Erro de Autenticação", JOptionPane.ERROR_MESSAGE);
            return;
        }

        view.dispose();
        
        CategoriaRepository categoriaRepo = new CategoriaRepository();
        ProdutoRepository produtoRepo = new ProdutoRepository();
        HistoricoPrecoRepository historicoRepo = new HistoricoPrecoRepository();
        
        CategoriaServico categoriaServico = new CategoriaServico(categoriaRepo, produtoRepo);
        CalculoPrecoServico calculoServico = new CalculoPrecoServico(produtoRepo, historicoRepo);
        
        new TelaPrincipalPresenter(
            usuarioEncontrado, 
            categoriaRepo, 
            categoriaServico, 
            produtoRepo, 
            historicoRepo, 
            calculoServico
        );
        
        try {
            ConfiguracaoPainelPedido configuracao = 
                new LeitorConfiguracaoPainelPedido(".", "painel_pedidos.env").ler();

            PedidoRepository meuRepositorio = new PedidoRepository();

            PainelPedidoFacade painelPedido = 
                new PainelPedidoFacade(
                    meuRepositorio, 
                    configuracao, 
                    new SomTomStrategy(880, 200), 
                    new RelogioServiceSistema()
                );

            painelPedido.setVisualizarPedidoListener(new MinhaTelaDePedido(meuRepositorio));
            painelPedido.setConfirmarPagamentoListener(new MeuPagamento());
            painelPedido.setCriarNovoPedidoListener(new MeuCriadorDePedido(meuRepositorio, produtoRepo, usuarioRepository, usuarioEncontrado));
            
            if (!painelPedido.iniciar()) {
                System.out.println("O painel de pedidos já se encontra em execução.");
            } else {
                Pedido pedidoTeste = new Pedido(
                    "4001", 
                    LocalDateTime.now(),    
                    "Ana Sofia", 
                    "Rua Principal, 123", 
                    150.50
                );

                meuRepositorio.salvar(pedidoTeste);

                List<PedidoAbstract> novosPedidos = new java.util.ArrayList<>();
                novosPedidos.add(pedidoTeste);
                painelPedido.receberNovosPedidos(novosPedidos);
                
                System.out.println("Pedido de teste empurrado com sucesso para o painel!");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao iniciar o painel de pedidos: " + e.getMessage(), 
                    "Erro", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        
    }

}

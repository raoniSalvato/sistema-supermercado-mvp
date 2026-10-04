package br.ufes.dcomp.supermercado.presenter;

import br.ufes.dcomp.supermercado.model.PerfilUsuario;
import br.ufes.dcomp.supermercado.model.Usuario;
import br.ufes.dcomp.supermercado.repositorio.CategoriaRepository;
import br.ufes.dcomp.supermercado.repositorio.ClienteRepository;
import br.ufes.dcomp.supermercado.repositorio.HistoricoPrecoRepository;
import br.ufes.dcomp.supermercado.repositorio.ProdutoRepository;
import br.ufes.dcomp.supermercado.repositorio.UsuarioRepository;
import br.ufes.dcomp.supermercado.servico.CalculoPrecoServico;
import br.ufes.dcomp.supermercado.servico.CategoriaServico;
import br.ufes.dcomp.supermercado.servico.ClienteServico;
import br.ufes.dcomp.supermercado.servico.UsuarioServico;
import br.ufes.dcomp.supermercado.view.TelaPrincipalView;
import javax.swing.JFrame;

public class TelaPrincipalPresenter {
    
    private TelaPrincipalView view;
    private Usuario usuarioLogado;
    private CategoriaRepository categoriaRepository;
    private CategoriaServico categoriaServico;
    private ProdutoRepository produtoRepository;
    private HistoricoPrecoRepository historicoPrecoRepository;
    private CalculoPrecoServico calculoPrecoServico;
    
    public TelaPrincipalPresenter(Usuario usuarioLogado, CategoriaRepository categoriaRepository, CategoriaServico categoriaServico, ProdutoRepository produtoRepository, HistoricoPrecoRepository historicoPrecoRepository, CalculoPrecoServico calculoPrecoServico){
        this.usuarioLogado = usuarioLogado;
        this.categoriaRepository = categoriaRepository;
        this.categoriaServico = categoriaServico;
        this.produtoRepository = produtoRepository;
        this.historicoPrecoRepository = historicoPrecoRepository;
        this.calculoPrecoServico = calculoPrecoServico;
        
        this.view = new TelaPrincipalView();
        this.view.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.view.getLblNomeUsuario().setText("Usuário: " + usuarioLogado.getNomeCompleto());
        
        configurarListeners();

        if (this.usuarioLogado.getPerfil() != PerfilUsuario.ADMINISTRADOR) {
            this.view.getMenuUsuarios().setVisible(false); 
        }
        
        this.view.setVisible(true);
    }

    private void configurarListeners() {
        
        this.view.getBtnSair().addActionListener(e -> {
            this.view.dispose();
            new AutenticacaoPresenter(new UsuarioRepository()); 
        });
        
        this.view.getMenuCategorias().addActionListener(e -> {
            new CategoriaPresenter(categoriaRepository, categoriaServico);
        });
        
        this.view.getMenuProdutos().addActionListener(e -> {
            new BuscaProdutoPresenter(produtoRepository, categoriaRepository, historicoPrecoRepository);
        });
        
        this.view.getMenuClientes().addActionListener(e -> {
            ClienteRepository repoCliente = new ClienteRepository();
            ClienteServico servicoCliente = new ClienteServico(repoCliente);
            new ClientePresenter(servicoCliente);
        });
        
        this.view.getMenuGerenciarUsuarios().addActionListener(e -> {
            UsuarioRepository repoUser = new UsuarioRepository();
            UsuarioServico servicoUser = new UsuarioServico(repoUser);
            
            ClienteRepository repoCliente = new ClienteRepository();
            ClienteServico servicoCliente = new ClienteServico(repoCliente);
            
            new UsuarioPresenter(servicoUser, servicoCliente);
        });
    }
}

package br.ufes.dcomp.supermercado.presenter;

import br.ufes.dcomp.supermercado.model.StatusUsuario;
import br.ufes.dcomp.supermercado.model.Usuario;
import br.ufes.dcomp.supermercado.repositorio.CategoriaRepository;
import br.ufes.dcomp.supermercado.repositorio.HistoricoPrecoRepository;
import br.ufes.dcomp.supermercado.repositorio.ProdutoRepository;
import br.ufes.dcomp.supermercado.repositorio.UsuarioRepository;
import br.ufes.dcomp.supermercado.servico.CalculoPrecoServico;
import br.ufes.dcomp.supermercado.servico.CategoriaServico;
import br.ufes.dcomp.supermercado.view.AutenticacaoView;
import javax.swing.JOptionPane;

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
        
    }
}

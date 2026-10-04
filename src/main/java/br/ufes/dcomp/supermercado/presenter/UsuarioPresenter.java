package br.ufes.dcomp.supermercado.presenter;

import br.ufes.dcomp.supermercado.model.Cliente;
import br.ufes.dcomp.supermercado.model.PerfilUsuario;
import br.ufes.dcomp.supermercado.model.StatusUsuario;
import br.ufes.dcomp.supermercado.model.Usuario;
import br.ufes.dcomp.supermercado.presenter.state.cliente.ClienteEstadoInclusao;
import br.ufes.dcomp.supermercado.presenter.state.usuario.UsuarioEstadoVisualizacao;
import br.ufes.dcomp.supermercado.presenter.state.usuario.UsuarioPresenterState;
import br.ufes.dcomp.supermercado.repositorio.ClienteRepository;
import br.ufes.dcomp.supermercado.servico.ClienteServico;
import br.ufes.dcomp.supermercado.servico.UsuarioServico;
import br.ufes.dcomp.supermercado.view.UsuarioView;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class UsuarioPresenter {

    private UsuarioView view;
    private UsuarioServico usuarioServico;
    private ClienteServico clienteServico;
    private Usuario usuarioCorrente;
    
    private UsuarioPresenterState estadoAtual;

    public UsuarioPresenter(UsuarioServico usuarioServico, ClienteServico clienteServico) {
        this.usuarioServico = usuarioServico;
        this.clienteServico = clienteServico;
        this.view = new UsuarioView();
        
        this.view.setLocationRelativeTo(null);
        this.view.setResizable(false);
        this.view.setTitle("Gestão de Usuários");

        carregarComboBoxClientes();
        configurarListeners();
        carregarTabela();
        
        setEstado(new UsuarioEstadoVisualizacao(this));

        this.view.setVisible(true);
    }

    public void setEstado(UsuarioPresenterState novoEstado) {
        this.estadoAtual = novoEstado;
    }

    private void configurarListeners() {
        view.getBtnNovo().addActionListener(e -> estadoAtual.novo());
        view.getBtnEditar().addActionListener(e -> estadoAtual.editar());
        view.getBtnCancelar().addActionListener(e -> estadoAtual.cancelar());
        view.getBtnSalvar().addActionListener(e -> estadoAtual.salvar());
        view.getBtnExcluir().addActionListener(e -> estadoAtual.excluir());
        view.getBtnFechar().addActionListener(e -> view.dispose());

        view.getBtnHabilitar().addActionListener(e -> estadoAtual.habilitar());
        view.getBtnDesabilitar().addActionListener(e -> estadoAtual.desabilitar());

        view.getCbPerfilUsuario().addActionListener(e -> validarRegraComboBoxCliente());

        view.getTblUsuarios().getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && view.getTblUsuarios().getSelectedRow() != -1) {
                preencherFormularioComSelecionado();
                setEstado(new UsuarioEstadoVisualizacao(this));
            }
        });
        
        view.getChkMostrarSenha().addActionListener(e -> {
            if (view.getChkMostrarSenha().isSelected()) {
                view.getTextSenha().setEchoChar((char) 0);
                view.getTextConfirmarSenha().setEchoChar((char) 0);
            } else {
                view.getTextSenha().setEchoChar('*'); 
                view.getTextConfirmarSenha().setEchoChar('*');
            }
        });
        
        view.getBtnIncluirCliente().addActionListener(e -> {
            ClienteRepository repo = new ClienteRepository();
            ClienteServico servico = new ClienteServico(repo);
            
            ClientePresenter telaCliente = new ClientePresenter(servico);
            telaCliente.setEstado(new ClienteEstadoInclusao(telaCliente));
            
            telaCliente.getView().addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent windowEvent) {
                    carregarComboBoxClientes();
                    if (view.getCbClienteAssociado().getItemCount() > 1) {
                        view.getCbClienteAssociado().setSelectedIndex(view.getCbClienteAssociado().getItemCount() - 1);
                    }
                }
            });
        });
    }

    public void carregarComboBoxClientes() {
        view.getCbClienteAssociado().removeAllItems();
        view.getCbClienteAssociado().addItem("Selecione...");
        for (Cliente c : clienteServico.buscarTodos()) {
            view.getCbClienteAssociado().addItem(c);
        }
    }

    public void validarRegraComboBoxCliente() {
        String perfilSelecionado = view.getCbPerfilUsuario().getSelectedItem().toString();
        if (perfilSelecionado.equals(PerfilUsuario.CLIENTE.name())) {
            view.getCbClienteAssociado().setEnabled(true);
        } else {
            view.getCbClienteAssociado().setEnabled(false);
            view.getCbClienteAssociado().setSelectedIndex(0);
        }
    }

    public void habilitarCampos(boolean habilitar) {
        view.getTextNomeCompleto().setEnabled(habilitar);
        view.getTextEmail().setEnabled(habilitar);
        view.getTextNomeUsuario().setEnabled(habilitar);
        view.getTextSenha().setEnabled(habilitar);
        view.getTextConfirmarSenha().setEnabled(habilitar);
        view.getCbPerfilUsuario().setEnabled(habilitar);
    }

    public void limparCampos() {
        view.getTextNomeCompleto().setText("");
        view.getTextEmail().setText("");
        view.getTextNomeUsuario().setText("");
        view.getTextSenha().setText("");
        view.getTextConfirmarSenha().setText("");
        view.getCbPerfilUsuario().setSelectedIndex(0);
        view.getTextStatus().setText("");
        view.getChkMostrarSenha().setSelected(false);
        view.getTextSenha().setEchoChar('*');
        view.getTextConfirmarSenha().setEchoChar('*');
    }

    public void executarSalvamento(boolean isModoEdicao) {
        String senha = new String(view.getTextSenha().getPassword());
        String confirmaSenha = new String(view.getTextConfirmarSenha().getPassword());
        
        if (!senha.equals(confirmaSenha)) {
            JOptionPane.showMessageDialog(view, "As senhas não coincidem.", "Aviso", JOptionPane.WARNING_MESSAGE);
            throw new RuntimeException("As senhas não coincidem.");
        }

        try {
            PerfilUsuario perfil = PerfilUsuario.valueOf(view.getCbPerfilUsuario().getSelectedItem().toString());
            Cliente clienteSelecionado = null;
            
            if (perfil == PerfilUsuario.CLIENTE && view.getCbClienteAssociado().getSelectedIndex() > 0) {
                clienteSelecionado = (Cliente) view.getCbClienteAssociado().getSelectedItem();
            }

            if (!isModoEdicao) {
                usuarioCorrente = new Usuario(
                    null, view.getTextNomeCompleto().getText(), view.getTextEmail().getText(),
                    view.getTextNomeUsuario().getText(), senha, perfil
                );
            } else {
                usuarioCorrente.setNomeCompleto(view.getTextNomeCompleto().getText());
                usuarioCorrente.setEmail(view.getTextEmail().getText());
                usuarioCorrente.setNomeUsuario(view.getTextNomeUsuario().getText());
                usuarioCorrente.setSenha(senha);
                usuarioCorrente.setPerfil(perfil);
            }
            usuarioCorrente.setClienteAssociado(clienteSelecionado);

            usuarioServico.salvar(usuarioCorrente);
            
            JOptionPane.showMessageDialog(view, "Usuário salvo com sucesso!");
            carregarTabela();
            
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(view, ex.getMessage(), "Erro de Validação", JOptionPane.WARNING_MESSAGE);
            throw ex;
        }
    }

    public void executarExclusao() {
        try {
            int confirmacao = JOptionPane.showConfirmDialog(view, "Tem certeza que deseja excluir este usuário?", "Confirmar Exclusão", JOptionPane.YES_NO_OPTION);
            if (confirmacao == JOptionPane.YES_OPTION) {
                usuarioServico.excluir(usuarioCorrente);
                usuarioCorrente = null;
                limparCampos();
                carregarTabela();
            }
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(view, ex.getMessage(), "Operação Bloqueada", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void definirStatus(StatusUsuario novoStatus) {
        try {
            usuarioServico.alternarStatus(usuarioCorrente, novoStatus);
            carregarTabela();
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(view, ex.getMessage(), "Operação Bloqueada", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void carregarTabela() {
        List<Usuario> usuarios = usuarioServico.buscarTodos();
        DefaultTableModel modelo = new DefaultTableModel(new Object[]{"Nome", "Usuário", "Perfil", "Status", "Cliente Assoc."}, 0) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };

        for (Usuario u : usuarios) {
            String nomeCliente = u.getClienteAssociado() != null ? u.getClienteAssociado().getNome() : "-";
            modelo.addRow(new Object[]{
                u.getNomeCompleto(), u.getNomeUsuario(), u.getPerfil().name(), u.getStatus().name(), nomeCliente
            });
        }
        view.getTblUsuarios().setModel(modelo);
    }

    public void preencherFormularioComSelecionado() {
        int linhaSelecionada = view.getTblUsuarios().getSelectedRow();
        usuarioCorrente = usuarioServico.buscarTodos().get(linhaSelecionada);

        view.getTextNomeCompleto().setText(usuarioCorrente.getNomeCompleto());
        view.getTextEmail().setText(usuarioCorrente.getEmail());
        view.getTextNomeUsuario().setText(usuarioCorrente.getNomeUsuario());
        view.getTextSenha().setText(usuarioCorrente.getSenha());
        view.getTextConfirmarSenha().setText(usuarioCorrente.getSenha());
        view.getCbPerfilUsuario().setSelectedItem(usuarioCorrente.getPerfil().name());
        view.getTextStatus().setText(usuarioCorrente.getStatus().name());
        
        if (usuarioCorrente.getClienteAssociado() != null) {
            view.getCbClienteAssociado().setSelectedItem(usuarioCorrente.getClienteAssociado());
        } else {
            view.getCbClienteAssociado().setSelectedIndex(0);
        }
    }

    public UsuarioView getView() {
        return view;
    }

    public Usuario getUsuarioCorrente() {
        return usuarioCorrente;
    }

    public void setUsuarioCorrente(Usuario usuarioCorrente) {
        this.usuarioCorrente = usuarioCorrente;
    }
}
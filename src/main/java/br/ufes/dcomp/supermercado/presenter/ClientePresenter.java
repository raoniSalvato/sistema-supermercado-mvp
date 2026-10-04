package br.ufes.dcomp.supermercado.presenter;

import br.ufes.dcomp.supermercado.model.Cliente;
import br.ufes.dcomp.supermercado.presenter.state.cliente.ClienteEstadoVisualizacao;
import br.ufes.dcomp.supermercado.presenter.state.cliente.ClientePresenterState; 
import br.ufes.dcomp.supermercado.servico.ClienteServico;
import br.ufes.dcomp.supermercado.view.ClienteView;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class ClientePresenter {

    private ClienteView view;
    private ClienteServico servico;
    private Cliente clienteCorrente;
    
    private ClientePresenterState estadoAtual;

    public ClientePresenter(ClienteServico servico) {
        this.servico = servico;
        this.view = new ClienteView();
        
        this.view.setLocationRelativeTo(null);
        this.view.setResizable(false);
        this.view.setTitle("Clientes");

        configurarListeners();
        carregarTabela();
        
        setEstado(new ClienteEstadoVisualizacao(this));

        this.view.setVisible(true);
    }

    public void setEstado(ClientePresenterState novoEstado) {
        this.estadoAtual = novoEstado;
    }

    private void configurarListeners() {
        view.getBtnNovo().addActionListener(e -> estadoAtual.novo());
        view.getBtnEditar().addActionListener(e -> estadoAtual.editar());
        view.getBtnCancelar().addActionListener(e -> estadoAtual.cancelar());
        view.getBtnSalvar().addActionListener(e -> estadoAtual.salvar());
        view.getBtnExcluir().addActionListener(e -> estadoAtual.excluir());
        view.getBtnFechar().addActionListener(e -> view.dispose());

        view.getTblClientes().getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && view.getTblClientes().getSelectedRow() != -1) {
                preencherFormularioComSelecionado();
                setEstado(new ClienteEstadoVisualizacao(this));
            }
        });
    }

    public void habilitarCampos(boolean habilitar) {
        view.getTextNome().setEnabled(habilitar);
        view.getTextLogradouro().setEnabled(habilitar);
        view.getTextBairro().setEnabled(habilitar);
        view.getTextCidade().setEnabled(habilitar);
        view.getTextUf().setEnabled(habilitar);
    }

    public void limparCampos() {
        view.getTextNome().setText("");
        view.getTextLogradouro().setText("");
        view.getTextBairro().setText("");
        view.getTextCidade().setText("");
        view.getTextUf().setText("");
        view.getTextTipoCliente().setText("");
        view.getTextTotalCompras().setText("");
    }

    public void executarSalvamento(boolean isModoEdicao) {
        try {
            if (!isModoEdicao) {
                clienteCorrente = new Cliente(
                    null,
                    view.getTextNome().getText(),
                    view.getTextLogradouro().getText(),
                    view.getTextBairro().getText(),
                    view.getTextCidade().getText(),
                    view.getTextUf().getText()
                );
            } else {
                clienteCorrente.setNome(view.getTextNome().getText());
                clienteCorrente.setLogradouro(view.getTextLogradouro().getText());
                clienteCorrente.setBairro(view.getTextBairro().getText());
                clienteCorrente.setCidade(view.getTextCidade().getText());
                clienteCorrente.setUf(view.getTextUf().getText());
            }

            servico.salvar(clienteCorrente);
            
            JOptionPane.showMessageDialog(view, "Cliente salvo com sucesso!");
            carregarTabela();
            
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(view, ex.getMessage(), "Erro de Validação", JOptionPane.WARNING_MESSAGE);
            throw ex; 
        }
    }

    public void executarExclusao() {
        int confirmacao = JOptionPane.showConfirmDialog(view, "Tem certeza que deseja excluir este cliente?", "Confirmar Exclusão", JOptionPane.YES_NO_OPTION);
        if (confirmacao == JOptionPane.YES_OPTION) {
            servico.excluir(clienteCorrente);
            clienteCorrente = null;
            limparCampos();
            carregarTabela();
        }
    }

    private void carregarTabela() {
        List<Cliente> clientes = servico.buscarTodos();
        DefaultTableModel modelo = new DefaultTableModel(new Object[]{"Nome", "Cidade", "UF", "Bairro", "Tipo de cliente", "Total de compras (R$)"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };

        for (Cliente c : clientes) {
            modelo.addRow(new Object[]{
                c.getNome(),
                c.getCidade(),
                c.getUf(),
                c.getBairro(),
                c.getTipoCliente().name(),
                String.format("%.2f", c.getTotalCompras())
            });
        }
        view.getTblClientes().setModel(modelo);
    }

    public void preencherFormularioComSelecionado() {
        int linhaSelecionada = view.getTblClientes().getSelectedRow();
        List<Cliente> clientes = servico.buscarTodos();
        clienteCorrente = clientes.get(linhaSelecionada);

        view.getTextNome().setText(clienteCorrente.getNome());
        view.getTextLogradouro().setText(clienteCorrente.getLogradouro());
        view.getTextBairro().setText(clienteCorrente.getBairro());
        view.getTextCidade().setText(clienteCorrente.getCidade());
        view.getTextUf().setText(clienteCorrente.getUf());
        view.getTextTipoCliente().setText(clienteCorrente.getTipoCliente().name());
        view.getTextTotalCompras().setText(String.format("%.2f", clienteCorrente.getTotalCompras()));
    }
    
    public ClienteView getView() {
        return view;
    }

    public Cliente getClienteCorrente() {
        return clienteCorrente;
    }

    public void setClienteCorrente(Cliente clienteCorrente) {
        this.clienteCorrente = clienteCorrente;
    }
}
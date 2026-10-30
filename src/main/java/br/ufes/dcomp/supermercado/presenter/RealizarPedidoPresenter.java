package br.ufes.dcomp.supermercado.presenter;

import br.dcomp.supermercado.infra.APITaxaEntregaMoc;
import br.ufes.dcomp.supermercado.exception.CupomInvalidoException;
import br.ufes.dcomp.supermercado.model.Cupom;
import br.ufes.dcomp.supermercado.model.Item;
import br.ufes.dcomp.supermercado.model.Pedido;
import br.ufes.dcomp.supermercado.model.PerfilUsuario;
import br.ufes.dcomp.supermercado.model.Produto;
import br.ufes.dcomp.supermercado.model.Usuario;
import br.ufes.dcomp.supermercado.repositorio.CupomRepositoryEmMemoria;
import br.ufes.dcomp.supermercado.repositorio.ICupomRepository;
import br.ufes.dcomp.supermercado.repositorio.PedidoRepository;
import br.ufes.dcomp.supermercado.repositorio.ProdutoRepository;
import br.ufes.dcomp.supermercado.repositorio.UsuarioRepository;
import br.ufes.dcomp.supermercado.servico.AplicadorDeCupomService;
import br.ufes.dcomp.supermercado.view.RealizarPedidoView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

public class RealizarPedidoPresenter {
    
    private RealizarPedidoView view;
    private PedidoRepository pedidoRepository;
    private ProdutoRepository produtoRepository;
    private UsuarioRepository usuarioRepository;
    private Usuario usuarioLogado;
    private Pedido pedidoCriado;

    private ICupomRepository cupomRepository;
    private AplicadorDeCupomService aplicadorCupom;
    private Cupom cupomAtivo = null;

    public RealizarPedidoPresenter(PedidoRepository pedidoRepository, ProdutoRepository produtoRepository, 
                                   UsuarioRepository usuarioRepository, Usuario usuarioLogado) {
        this.pedidoRepository = pedidoRepository;
        this.produtoRepository = produtoRepository;
        this.usuarioRepository = usuarioRepository;
        this.usuarioLogado = usuarioLogado;
        this.view = new RealizarPedidoView(null, true); 
        
        this.cupomRepository = new CupomRepositoryEmMemoria();
        this.aplicadorCupom = new AplicadorDeCupomService(this.cupomRepository);

        inicializarTela();
        configurarListeners();
    }
    
    public RealizarPedidoPresenter(Pedido pedidoParaVisualizar) {
        this.view = new RealizarPedidoView(null, true);
        this.pedidoCriado = pedidoParaVisualizar;
        
        inicializarTelaSomenteLeitura();
        
        view.getBtnFechar().addActionListener(e -> view.dispose());
    }

    private void inicializarTelaSomenteLeitura() {
        view.getTxtNumeroPedido().setText(pedidoCriado.getIdentificador());
        view.getTxtNumeroPedido().setEditable(false);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        view.getTxtData().setText(pedidoCriado.getDataHora().format(formatter));
        view.getTxtData().setEditable(false);

        view.getCbCliente().removeAllItems();
        view.getCbCliente().addItem(pedidoCriado.getNomeCliente());
        view.getCbCliente().setEnabled(false);
        
        view.getTxtEndereco().setText(pedidoCriado.getEnderecoEntrega());
        view.getTxtEndereco().setEditable(false);

        view.getCbProduto().setEnabled(false);
        view.getBtnAdicionar().setEnabled(false);
        view.getTxtCupom().setEditable(false);
        view.getBtnAplicarCupom().setEnabled(false);
        view.getBtnRemoverCupom().setEnabled(false);
        view.getBtnCalcularValor().setEnabled(false);
        view.getBtnPagarPedido().setEnabled(false);

        view.getTxtValorPedido().setEditable(false);
        view.getTxtTaxaEntregaPadrao().setEditable(false);
        view.getTxtDescontosTaxa().setEditable(false);
        view.getTxtTaxaEntregaFinal().setEditable(false);
        view.getTxtTotalPagar().setEditable(false);
        DefaultTableModel modelo = new DefaultTableModel(
            new Object [][] {},
            new String [] {"Item", "Produto", "Preço unitário (R$)", "Quantidade", "Subtotal (R$)"}
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };
        view.getTblItens().setModel(modelo);

        double subtotalProdutos = 0.0;
        int itemIndex = 1;
        if (pedidoCriado.getItens() != null) {
            for (Item item : pedidoCriado.getItens()) {
                modelo.addRow(new Object[]{
                    itemIndex++, 
                    item.getNome(), 
                    item.getValorUnitario(), 
                    item.getQuantidade(), 
                    item.getValorTotal()
                });
                subtotalProdutos += item.getValorTotal();
            }
        }

        double total = pedidoCriado.getValorTotal();
        double taxaFinal = total - subtotalProdutos; 
        
        view.getTxtValorPedido().setText(String.format(Locale.US, "%.2f", subtotalProdutos));
        view.getTxtTaxaEntregaPadrao().setText(String.format(Locale.US, "%.2f", taxaFinal));
        view.getTxtDescontosTaxa().setText("0.00"); 
        view.getTxtTaxaEntregaFinal().setText(String.format(Locale.US, "%.2f", taxaFinal));
        view.getTxtTotalPagar().setText(String.format(Locale.US, "%.2f", total));
    }

    private void inicializarTela() {
        String numeroPedido = "PED-" + UUID.randomUUID().toString().substring(0, 5).toUpperCase();
        view.getTxtNumeroPedido().setText(numeroPedido);
        view.getTxtNumeroPedido().setEditable(false);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        view.getTxtData().setText(LocalDateTime.now().format(formatter));
        view.getTxtData().setEditable(false);

        view.getTxtValorPedido().setEditable(false);
        view.getTxtTaxaEntregaPadrao().setEditable(false);
        view.getTxtDescontosTaxa().setEditable(false);
        view.getTxtTaxaEntregaFinal().setEditable(false);
        view.getTxtTotalPagar().setEditable(false);
        view.getTxtCupomAplicado().setEditable(false); 

        // Configura a tabela permitindo edição APENAS na coluna 3 (Quantidade)
        DefaultTableModel modelo = new DefaultTableModel(
            new Object [][] {},
            new String [] {"Item", "Produto", "Preço unitário (R$)", "Quantidade", "Subtotal (R$)"}
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 3; 
            }
        };
        view.getTblItens().setModel(modelo);

        view.getCbProduto().removeAllItems();
        List<Produto> produtos = produtoRepository.buscarTodos();
        if (produtos.isEmpty()) {
            view.getCbProduto().addItem("Nenhum produto cadastrado");
            view.getBtnAdicionar().setEnabled(false);
        } else {
            for (Produto p : produtos) {
                view.getCbProduto().addItem(p.getNome() + " - R$ " + String.format(Locale.US, "%.2f", p.getPrecoVendaAtual()));
            }
        }

        view.getCbCliente().removeAllItems();
        if (usuarioLogado != null) {
            if (usuarioLogado.getPerfil() == PerfilUsuario.CLIENTE) {
                view.getCbCliente().addItem(usuarioLogado.getNomeCompleto()); 
                view.getCbCliente().setEnabled(false);
                preencherEnderecoDoCliente(usuarioLogado);
            } else {
                List<Usuario> todosUsuarios = usuarioRepository.buscarTodos();
                view.getCbCliente().addItem("Selecione um cliente...");
                for (Usuario u : todosUsuarios) {
                    if (u.getPerfil() == PerfilUsuario.CLIENTE) {
                        view.getCbCliente().addItem(u.getNomeCompleto());
                    }
                }
            }
        }
        
        sinalizarValoresDesatualizados();
    }

    private void configurarListeners() {
        view.getBtnFechar().addActionListener(e -> view.dispose());
        view.getBtnAdicionar().addActionListener(e -> adicionarProduto());
        view.getBtnCalcularValor().addActionListener(e -> calcularTotais());
        view.getBtnPagarPedido().addActionListener(e -> efetuarPagamento());
        view.getBtnAplicarCupom().addActionListener(e -> aplicarCupom());
        view.getBtnRemoverCupom().addActionListener(e -> removerCupom());

        // Listener do Cliente (Atualiza endereço e bloqueia pagamento se mudar)
        view.getCbCliente().addActionListener(e -> {
            String selecionado = (String) view.getCbCliente().getSelectedItem();
            if (selecionado != null && !selecionado.equals("Selecione um cliente...")) {
                List<Usuario> todos = usuarioRepository.buscarTodos();
                for (Usuario u : todos) {
                    if (u.getNomeCompleto().equals(selecionado)) {
                        preencherEnderecoDoCliente(u);
                        sinalizarValoresDesatualizados();
                        break;
                    }
                }
            } else {
                view.getTxtEndereco().setText("");
                sinalizarValoresDesatualizados();
            }
        });

        DefaultTableModel modelo = (DefaultTableModel) view.getTblItens().getModel();
        modelo.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                if (e.getType() == TableModelEvent.UPDATE && e.getColumn() == 3) {
                    int row = e.getFirstRow();
                    if (row >= 0 && row < modelo.getRowCount()) {
                        Object qtyObj = modelo.getValueAt(row, 3);
                        
                        int qtdTemp = 0;
                        try {
                            qtdTemp = Integer.parseInt(qtyObj.toString());
                        } catch (NumberFormatException ex) {}

                        final int novaQtd = qtdTemp;
                        final int linhaFinal = row;

                        if (novaQtd <= 0) {
                            SwingUtilities.invokeLater(() -> {
                                modelo.removeRow(linhaFinal);
                                sinalizarValoresDesatualizados();
                            });
                        } else {
                            double preco = Double.parseDouble(modelo.getValueAt(linhaFinal, 2).toString());
                            SwingUtilities.invokeLater(() -> {
                                modelo.setValueAt(novaQtd * preco, linhaFinal, 4); 
                                sinalizarValoresDesatualizados();
                            });
                        }
                    }
                }
            }
        });
    }

    private void preencherEnderecoDoCliente(Usuario u) {
        if (u.getClienteAssociado() != null) {
            br.ufes.dcomp.supermercado.model.Cliente c = u.getClienteAssociado();
            String enderecoCompleto = String.format("%s, %s - %s/%s", 
                c.getLogradouro(), 
                c.getBairro(), 
                c.getCidade(), 
                c.getUf());
                
            view.getTxtEndereco().setText(enderecoCompleto);
        } else {
            view.getTxtEndereco().setText("Endereço não cadastrado.");
        }
    }

    private void aplicarCupom() {
        String codigo = view.getTxtCupom().getText().trim();
        try {
            double percentualAtual = (this.cupomAtivo != null) ? this.cupomAtivo.getPercentualFixoDesconto() : 0.0;
            Cupom cupomValido = aplicadorCupom.validarERecuperarCupom(codigo, LocalDateTime.now(), percentualAtual);
            this.cupomAtivo = cupomValido;
            view.getTxtCupomAplicado().setText(cupomValido.getCodigo());
            sinalizarValoresDesatualizados();
            JOptionPane.showMessageDialog(view, "Cupom aplicado com sucesso!");
        } catch (CupomInvalidoException ex) {
            JOptionPane.showMessageDialog(view, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            removerCupom();
        }
    }

    private void removerCupom() {
        this.cupomAtivo = null;
        view.getTxtCupomAplicado().setText("");
        sinalizarValoresDesatualizados();
    }

    private void adicionarProduto() {
        String selecionado = (String) view.getCbProduto().getSelectedItem();
        if (selecionado == null || selecionado.equals("Nenhum produto cadastrado")) return;

        String[] partes = selecionado.split(" - R\\$ ");
        String nomeProduto = partes[0];
        double preco = Double.parseDouble(partes[1]);

        DefaultTableModel modelo = (DefaultTableModel) view.getTblItens().getModel();
        boolean encontrou = false;
        
        for (int i = 0; i < modelo.getRowCount(); i++) {
            String nomeTabela = (String) modelo.getValueAt(i, 1);
            if (nomeTabela.equals(nomeProduto)) {
                int qtdAtual = Integer.parseInt(modelo.getValueAt(i, 3).toString());
                modelo.setValueAt(qtdAtual + 1, i, 3); // O TableModelListener cuida do resto
                encontrou = true;
                break;
            }
        }

        if (!encontrou) {
            int itemIndex = modelo.getRowCount() + 1;
            modelo.addRow(new Object[]{itemIndex, nomeProduto, preco, 1, preco});
            sinalizarValoresDesatualizados();
        }
    }

    private void sinalizarValoresDesatualizados() {
        view.getTxtValorPedido().setText("");
        view.getTxtDescontosTaxa().setText("");
        view.getTxtTaxaEntregaFinal().setText("");
        view.getTxtTotalPagar().setText("");
        
        view.getBtnPagarPedido().setEnabled(false); 
    }

    private void calcularTotais() {
        DefaultTableModel modelo = (DefaultTableModel) view.getTblItens().getModel();
        
        if (modelo.getRowCount() == 0) {
            view.getBtnPagarPedido().setEnabled(false);
            return;
        }

        double subtotalProdutos = 0;

        for (int i = 0; i < modelo.getRowCount(); i++) {
            subtotalProdutos += Double.parseDouble(modelo.getValueAt(i, 4).toString());
        }
        view.getTxtValorPedido().setText(String.format(Locale.US, "%.2f", subtotalProdutos));

        double taxaPadrao = APITaxaEntregaMoc.getTaxaEntrega();
        double descontos = 0.0;
        
        if (this.cupomAtivo != null) {
            descontos = taxaPadrao * (this.cupomAtivo.getPercentualFixoDesconto() / 100.0);
        }
        
        view.getTxtDescontosTaxa().setText(String.format(Locale.US, "%.2f", descontos));
        double taxaFinal = Math.max(0, taxaPadrao - descontos); 
        view.getTxtTaxaEntregaFinal().setText(String.format(Locale.US, "%.2f", taxaFinal));

        double total = subtotalProdutos + taxaFinal;
        view.getTxtTotalPagar().setText(String.format(Locale.US, "%.2f", total));
        view.getBtnPagarPedido().setEnabled(true);
    }

    private void efetuarPagamento() {
        if (view.getTxtEndereco().getText().trim().isEmpty() || view.getTxtEndereco().getText().equals("Endereço não cadastrado.")) {
            JOptionPane.showMessageDialog(view, "Preencha um endereço de entrega válido.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        DefaultTableModel modelo = (DefaultTableModel) view.getTblItens().getModel();
        if (modelo.getRowCount() == 0) {
            JOptionPane.showMessageDialog(view, "O pedido deve conter pelo menos um produto.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        calcularTotais(); 
        
        double valorTotal = Double.parseDouble(view.getTxtTotalPagar().getText().replace(",", "."));
        String idPedido = view.getTxtNumeroPedido().getText();
        String cliente = (String) view.getCbCliente().getSelectedItem();
        String endereco = view.getTxtEndereco().getText();

        try {
            this.pedidoCriado = new Pedido(
                idPedido,
                LocalDateTime.now(),
                cliente == null || cliente.equals("Selecione um cliente...") ? "Desconhecido" : cliente,
                endereco,
                valorTotal
            );

            for (int i = 0; i < modelo.getRowCount(); i++) {
                String nomeProduto = (String) modelo.getValueAt(i, 1);
                double precoUnitario = Double.parseDouble(modelo.getValueAt(i, 2).toString());
                int quantidade = Integer.parseInt(modelo.getValueAt(i, 3).toString());
                
                Item item = new Item(nomeProduto, quantidade, precoUnitario, "Produto");
                this.pedidoCriado.adicionarItem(item);
            }

            pedidoRepository.salvar(this.pedidoCriado);
            PagamentoPresenter pagamentoPresenter = new PagamentoPresenter(this.pedidoCriado, pedidoRepository);
            
            view.dispose(); 
            pagamentoPresenter.exibirTela();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, "Erro ao processar pedido: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void exibirTelaModal() {
        view.setVisible(true); 
    }

    public Pedido getPedidoCriado() {
        return this.pedidoCriado;
    }
}
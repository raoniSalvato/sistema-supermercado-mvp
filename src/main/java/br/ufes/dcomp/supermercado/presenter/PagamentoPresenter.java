package br.ufes.dcomp.supermercado.presenter;

import br.ufes.dcomp.supermercado.model.Pedido;
import br.ufes.dcomp.supermercado.repositorio.PedidoRepository;
import br.ufes.dcomp.supermercado.servico.SimuladorFalhaPagamentoService;
import br.ufes.dcomp.supermercado.view.PagamentoView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.UUID;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class PagamentoPresenter {

    private PagamentoView view;
    private Pedido pedido;
    private PedidoRepository pedidoRepository;

    public PagamentoPresenter(Pedido pedido, PedidoRepository pedidoRepository) {
        this.pedido = pedido;
        this.pedidoRepository = pedidoRepository;
        this.view = new PagamentoView();
        this.view.setModal(true); 
        
        inicializarTela();
        configurarListeners();
    }

    private void inicializarTela() {
        view.getTxtNumeroPedido().setText(pedido.getIdentificador());
        view.getTxtNumeroPedido().setEditable(false);
        
        view.getTxtCliente().setText(pedido.getNomeCliente());
        view.getTxtCliente().setEditable(false);
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        view.getTxtDataHora().setText(pedido.getDataHora().format(formatter));
        view.getTxtDataHora().setEditable(false);

        view.getTxtTotalPagar().setText(String.format(Locale.US, "%.2f", pedido.getValorTotal()));
        view.getTxtTotalPagar().setEditable(false);
        
        view.getTxtValorItens().setText(String.format(Locale.US, "%.2f", pedido.getValorTotal()));
        view.getTxtValorItens().setEditable(false);
        view.getTxtTaxaEntrega().setText("Incluída");
        view.getTxtTaxaEntrega().setEditable(false);
        view.getTxtDesconto().setText("Aplicado");
        view.getTxtDesconto().setEditable(false);

        DefaultTableModel modelo = (DefaultTableModel) view.getTblResumoItens().getModel();
        modelo.setRowCount(0);

        view.getPainelPixCpf().setVisible(false);
        view.getPainelPixEmail().setVisible(false);
        view.getPainelCartao().setVisible(false);      
        view.getTxtNumeroPedido2().getParent().setVisible(false); 
    }

    private void configurarListeners() {
        view.getBtnFechar().addActionListener(e -> view.dispose());
        view.getRbPixCpf().addActionListener(e -> alternarPaineisPagamento("CPF"));
        view.getRbPixEmail().addActionListener(e -> alternarPaineisPagamento("EMAIL"));
        view.getRbCartao().addActionListener(e -> alternarPaineisPagamento("CARTAO"));
        
        view.getBtnFinalizarPagamento().addActionListener(e -> finalizarPagamento());
        
        view.getBtnCopiarCodigoPixCPF().addActionListener(e -> {
            JOptionPane.showMessageDialog(view, "Código Pix copiado para a área de transferência!");
        });
        view.getBtnCopiarCodigoPixEmail().addActionListener(e -> {
            JOptionPane.showMessageDialog(view, "Código Pix copiado para a área de transferência!");
        });
    }

    private void alternarPaineisPagamento(String tipo) {
        view.getPainelPixCpf().setVisible(tipo.equals("CPF"));
        view.getPainelPixEmail().setVisible(tipo.equals("EMAIL"));
        view.getPainelCartao().setVisible(tipo.equals("CARTAO"));
        
        if (tipo.equals("CPF")) {
            view.getTxtValorPagarPixCPF().setText(String.format(Locale.US, "%.2f", pedido.getValorTotal()));
            view.getTxtChavePixCPF().setText("123.456.789-00");
            view.getTxtCodigoCopiaColaCpf().setText("00020126580014br.gov.bcb.pix...");
        } else if (tipo.equals("EMAIL")) {
            view.getTxtValorPagarPixEmail().setText(String.format(Locale.US, "%.2f", pedido.getValorTotal()));
            view.getTxtChavePixEmail().setText("pagamentos@supermercado.com");
            view.getTxtCodigoCopiaColaEmail().setText("00020126580014br.gov.bcb.pix...");
        }
    }

    private void finalizarPagamento() {
        if (!view.getRbPixCpf().isSelected() && !view.getRbPixEmail().isSelected() && !view.getRbCartao().isSelected()) {
            JOptionPane.showMessageDialog(view, "Selecione uma forma de pagamento antes de finalizar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (view.getRbCartao().isSelected()) {
            if (view.getTxtNumeroCartao().getText().trim().isEmpty() || view.getTxtNomeImpresso().getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(view, "Preencha os dados do cartão de crédito.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
        }

        try {
            SimuladorFalhaPagamentoService.getInstancia().processarPagamento();

            String formaPagamento = view.getRbCartao().isSelected() ? "Cartão de Crédito" : "PIX";
            String codigoTransacao = "TX-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
            view.getTxtStatusPedido().setText("Em preparo"); 

            view.getTxtNumeroPedido2().setText(pedido.getIdentificador());
            view.getTxtFormaPagamento().setText(formaPagamento);
            view.getTxtCodigoTransacao().setText(codigoTransacao);
            view.getTxtDataConfirmacao().setText(LocalDateTime.now().format(formatter));
            
            view.getTxtNumeroPedido2().setEditable(false);
            view.getTxtStatusPedido().setEditable(false);
            view.getTxtFormaPagamento().setEditable(false);
            view.getTxtCodigoTransacao().setEditable(false);
            view.getTxtDataConfirmacao().setEditable(false);

            view.getTxtNumeroPedido2().getParent().setVisible(true); 
            view.getBtnFinalizarPagamento().setEnabled(false);
            view.getRbPixCpf().setEnabled(false);
            view.getRbPixEmail().setEnabled(false);
            view.getRbCartao().setEnabled(false);

            JOptionPane.showMessageDialog(view, "Pagamento aprovado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);

        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(view, ex.getMessage(), "Falha no Pagamento", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void mostrarDetalhesDoPedido() {
        RealizarPedidoPresenter presenterLeitura = new RealizarPedidoPresenter(this.pedido);
        presenterLeitura.exibirTelaModal(); 
    }

    public void exibirTela() {
        view.setLocationRelativeTo(null);
        view.setVisible(true);
    }
}
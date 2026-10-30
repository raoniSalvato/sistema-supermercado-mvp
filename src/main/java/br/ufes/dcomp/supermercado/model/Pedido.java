package br.ufes.dcomp.supermercado.model;

import br.projeto.painelpedidos.model.PedidoAbstract;
import br.dcomp.supermercado.infra.APITaxaEntregaMoc; 
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Pedido extends PedidoAbstract {

    private List<Item> itens; 

    public Pedido(String identificador, LocalDateTime dataHora, String nomeCliente,
            String enderecoEntrega, double valorTotal) {
        super(identificador, dataHora, nomeCliente, enderecoEntrega, valorTotal);
        this.itens = new ArrayList<>();
    }

    public void adicionarItem(Item item) {
        this.itens.add(item);
    }

    public List<Item> getItens() {
        return this.itens;
    }

    @Override
    public PedidoAbstract criarCopia(String novoIdentificador, LocalDateTime novaDataHora) {
        double subtotal = 0.0;
        for (Item i : this.itens) {
            subtotal += (i.getQuantidade() * i.getValorUnitario());
        }
        
        double valorNovoTotal = subtotal + APITaxaEntregaMoc.getTaxaEntrega();
        Pedido copia = new Pedido(novoIdentificador, novaDataHora,
                getNomeCliente(), getEnderecoEntrega(), valorNovoTotal);
        for (Item i : this.itens) {
            copia.adicionarItem(new Item(i.getNome(), i.getQuantidade(), i.getValorUnitario(), "Produto"));
        }
        
        return copia;
    }
}
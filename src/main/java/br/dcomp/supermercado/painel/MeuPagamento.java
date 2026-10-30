/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.dcomp.supermercado.painel;

import br.projeto.painelpedidos.listener.IConfirmarPagamentoListener;
import br.projeto.painelpedidos.model.PedidoAbstract;
import java.time.LocalDateTime;

public class MeuPagamento implements IConfirmarPagamentoListener {
    @Override
    public void confirmarPagamento(PedidoAbstract pedido) {
        System.out.println("A abrir a vista de Pagamento para o pedido: " + pedido.getIdentificador());
        pedido.registrarPagamento("Pix - chave CPF", LocalDateTime.now());
    }
}

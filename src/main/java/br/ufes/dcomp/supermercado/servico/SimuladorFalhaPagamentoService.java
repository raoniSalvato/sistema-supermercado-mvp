package br.ufes.dcomp.supermercado.servico;

import java.util.Random;

public class SimuladorFalhaPagamentoService {
    
    private static SimuladorFalhaPagamentoService instancia;
    private Random random;

    private SimuladorFalhaPagamentoService() {
        this.random = new Random();
    }

    public static SimuladorFalhaPagamentoService getInstancia() {
        if (instancia == null) {
            instancia = new SimuladorFalhaPagamentoService();
        }
        return instancia;
    }

    public void processarPagamento() {
        double chance = random.nextDouble() * 100.0; 
        
        if (chance < 10.0) {
            throw new RuntimeException("Servidor indisponível");
        } else if (chance < 20.0) {
            throw new RuntimeException("Falha ao processar pagamento");
        } else if (chance < 25.0) {
            throw new RuntimeException("Tempo limite excedido");
        } else if (chance < 30.0) {
            throw new RuntimeException("Falha de comunicação com o serviço de pagamento");
        }
    }
}
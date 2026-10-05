package br.ufes.dcomp.supermercado.model;

public class Cliente {
    private Long id;
    private String nome;
    private String logradouro;
    private String bairro;
    private String cidade;
    private String uf;
    private TipoCliente tipoCliente;
    private Double totalCompras;
    
    public Cliente(Long id, String nome, String logradouro, String bairro, String cidade, String uf){
        this.id = id;
        this.nome = nome;
        this.logradouro = logradouro;
        this.bairro = bairro;
        this.cidade = cidade;
        this.uf = uf;
        this.tipoCliente = TipoCliente.PRATA;
        this.totalCompras = 0.0;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public String getBairro() {
        return bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public String getUf() {
        return uf;
    }

    public TipoCliente getTipoCliente() {
        return tipoCliente;
    }

    public Double getTotalCompras() {
        return totalCompras;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public void setTipoCliente(TipoCliente tipoCliente) {
        this.tipoCliente = tipoCliente;
    }

    public void setTotalCompras(Double totalCompras) {
        this.totalCompras = totalCompras;
    }

    @Override
    public String toString() {
        return "Cliente{" + "id=" + id + ", nome=" + nome + '}';
    }
    
}

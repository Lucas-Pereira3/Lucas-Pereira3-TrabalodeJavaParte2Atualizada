package pastajavafx.model;

public class Cliente {
    private String produto;
    private Integer cod;
    private double valorUnt;
    private int quantidade;
    private int estoque;
    private String fornecedor;
    private String descricao;

    public Cliente(String produto, Integer cod, double valorUnt, int quantidade, int estoque, String fornecedor, String descricao) {
        this.produto = produto;
        this.cod = cod;
        this.valorUnt = valorUnt;
        this.quantidade = quantidade;
        this.estoque = estoque;
        this.fornecedor = fornecedor;
        this.descricao = descricao;
    }

    // Getters e setters
    public String getProduto() {
        return produto;
    }

    public void setProduto(String produto) {
        this.produto = produto;
    }

    public int getCod() {
        return cod;
    }

    public void setCod(Integer cod) {
        this.cod = cod;
    }

    public double getValorUnt() {
        return valorUnt;
    }

    public void setValorUnt(double valorUnt) {
        this.valorUnt = valorUnt;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public int getEstoque() {
        return estoque;
    }

    public void setEstoque(int estoque) {
        this.estoque = estoque;
    }

    public String getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(String fornecedor) {
        this.fornecedor = fornecedor;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}



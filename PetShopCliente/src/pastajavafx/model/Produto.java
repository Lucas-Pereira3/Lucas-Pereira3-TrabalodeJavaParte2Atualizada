
package pastajavafx.model;

public class Produto {
    private int codigo;
    private String produto;
    private double preco;
    private double quantidade;
    private double valor;
    private double desconto;
    private String vendedor;
    private double total;

    // Construtor
    public Produto(int codigo, String produto, double preco, double quantidade, double valor, double desconto, String vendedor, double total) {
        this.codigo = codigo;
        this.produto = produto;
        this.preco = preco;
        this.quantidade = quantidade;
        this.valor = valor;
        this.desconto = desconto;
        this.vendedor = vendedor;
        this.total = total;
    }

    // Getters e setters
    public int getCod() {
        return codigo;
    }

    public void setCod(int codigo) {
        this.codigo = codigo;
    }

    public String getProduto() {
        return produto;
    }

    public void setProduto(String produto) {
        this.produto = produto;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public double getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(double quantidade) {
        this.quantidade = quantidade;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public double getDesconto() {
        return desconto;
    }

    public void setDesconto(double desconto) {
        this.desconto = desconto;
    }

    public String getVendedor() {
        return vendedor;
    }

    public void setVendedor(String vendedor) {
        this.vendedor = vendedor;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}

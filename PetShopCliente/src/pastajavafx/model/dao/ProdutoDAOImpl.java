package pastajavafx.model.dao;

import java.util.ArrayList;
import java.util.List;
import pastajavafx.model.Produto;

public class ProdutoDAOImpl implements ProdutoDAO {
    private List<Produto> produtos = new ArrayList<>();

    @Override
    public void salvar(Produto produto) {
        produtos.add(produto);
        System.out.println("Produto salvo: " + produto.getProduto());
    }

    @Override
    public void atualizar(Produto produto) {
        for (Produto p : produtos) {
            if (p.getCod() == produto.getCod()) {
                p.setProduto(produto.getProduto());
                p.setPreco(produto.getPreco());
                System.out.println("Produto atualizado: " + produto.getProduto());
                return;
            }
        }
    }

    @Override
    public void excluir(int codigo) {
        produtos.removeIf(p -> p.getCod() == codigo);
        System.out.println("Produto excluído com código: " + codigo);
    }

    @Override
    public Produto buscarPorId(int codigo) {
        for (Produto p : produtos) {
            if (p.getCod() == codigo) {
                return p;
            }
        }
        return null;
    }

    @Override
    public List<Produto> listarTodos() {
        return produtos;
    }
}

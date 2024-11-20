
package pastajavafx.model.dao;

import java.util.List;
import pastajavafx.model.Produto;

public interface ProdutoDAO {
    
    void salvar(Produto produto);
    
    void atualizar(Produto produto);
    
    void excluir(int codigo);
    
    Produto buscarPorId(int codigo);
    
    List<Produto> listarTodos();
}

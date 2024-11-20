
package pastajavafx.model.dao;

import pastajavafx.model.Cliente;
import java.util.List;

public interface ClienteDAO {

    void salvar(Cliente cliente);

    void atualizar(Cliente cliente);

    void excluir(Integer cod);

    Cliente buscarPorId(Integer cod);

    List<Cliente> listarTodos();
}



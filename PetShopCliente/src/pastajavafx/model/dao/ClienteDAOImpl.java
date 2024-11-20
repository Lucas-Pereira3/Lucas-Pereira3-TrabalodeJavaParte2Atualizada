package pastajavafx.model.dao;

import java.util.ArrayList;
import java.util.List;
import pastajavafx.model.Cliente;

public class ClienteDAOImpl implements ClienteDAO {

    private final List<Cliente> clientes = new ArrayList<>();

    @Override
    public void salvar(Cliente cliente) {
        Cliente existente = buscarPorId(cliente.getCod());
        if (existente != null) {
            atualizar(cliente);  // Atualiza se já existir
        } else {
            clientes.add(cliente);  // Adiciona um novo cliente
            System.out.println("Cliente salvo: " + cliente.getProduto());
        }
    }

    @Override
    public void atualizar(Cliente cliente) {
        for (int i = 0; i < clientes.size(); i++) {
            if (clientes.get(i).getCod() == cliente.getCod()) {
                clientes.set(i, cliente);  // Atualiza os dados do cliente
                System.out.println("Cliente atualizado: " + cliente.getCod());
                return;
            }
        }
    }

    
    public void excluir(int cod) {
        clientes.removeIf(cliente -> cliente.getCod() == cod);  // Remove o cliente com o código fornecido
        System.out.println("Cliente excluído com código: " + cod);
    }

    
    public Cliente buscarPorId(int cod) {
        return clientes.stream()
                       .filter(cliente -> cliente.getCod() == cod)
                       .findFirst()
                       .orElse(null);  // Retorna null se o cliente não for encontrado
    }

    @Override
    public List<Cliente> listarTodos() {
        return new ArrayList<>(clientes);  // Retorna uma cópia da lista de clientes
    }

    @Override
    public void excluir(Integer cod) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Cliente buscarPorId(Integer cod) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}



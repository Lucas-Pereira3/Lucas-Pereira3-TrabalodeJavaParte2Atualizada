package pastajavafx.Controller.java;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import pastajavafx.model.Cliente;
import pastajavafx.model.dao.ClienteDAOImpl;

public class FXMLTelaController implements Initializable {

    @FXML
    private Button adicionar;

    @FXML
    private Button excluir;

    @FXML
    private Button cancelar;

    @FXML
    private Button proximo;

    @FXML
    private Button salvar;

    @FXML
    private Button editar;

    @FXML
    private TableView<Cliente> tableView;

    @FXML
    private TableColumn<Cliente, Integer> colunaCodigo;

    @FXML
    private TableColumn<Cliente, String> colunaProduto;

    @FXML
    private TableColumn<Cliente, Double> colunaValorUnt;

    @FXML
    private TableColumn<Cliente, Integer> colunaQuantidade;

    @FXML
    private TableColumn<Cliente, Integer> colunaEstoque;

    @FXML
    private TableColumn<Cliente, String> colunaFornecedor;

    @FXML
    private TableColumn<Cliente, String> colunaDescricao;

    @FXML
    private TextField produto;

    @FXML
    private TextField cod;

    @FXML
    private TextField valorUnt;

    @FXML
    private TextField quantidade;

    @FXML
    private TextField estoque;

    @FXML
    private TextField fornecedor;

    @FXML
    private TextField descricao;

    private String estadoFormulario = "inicial";

    private ObservableList<Cliente> cliente = FXCollections.observableArrayList();

    private Stage stage;

    private ClienteDAOImpl clienteDAO = new ClienteDAOImpl(); // Correção aqui: inicializando o DAO
    private Cliente clienteAtual; // Cliente sendo editado ou adicionado

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Configuração das colunas da tabela
        colunaCodigo.setCellValueFactory(new PropertyValueFactory<>("cod"));
        colunaProduto.setCellValueFactory(new PropertyValueFactory<>("produto"));
        colunaValorUnt.setCellValueFactory(new PropertyValueFactory<>("valorUnt"));
        colunaQuantidade.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
        colunaEstoque.setCellValueFactory(new PropertyValueFactory<>("estoque"));
        colunaFornecedor.setCellValueFactory(new PropertyValueFactory<>("fornecedor"));
        colunaDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));

        // Inicializa a TableView com a lista de clientes
        tableView.setItems(cliente);

        atualizarBotoes();
    }

    @FXML
private void handleAdicionar() {
    // Verifica se os campos obrigatórios estão preenchidos
    if (produto.getText().isEmpty() || cod.getText().isEmpty() || valorUnt.getText().isEmpty()) {
        showAlert(AlertType.ERROR, "Erro", "Por favor, preencha todos os campos obrigatórios.");
        return;
    }

    // Criação de um novo cliente com os dados dos campos
    Cliente clienteTemp = new Cliente(
            produto.getText(),
            Integer.parseInt(cod.getText()),
            Double.parseDouble(valorUnt.getText()),
            Integer.parseInt(quantidade.getText()),
            Integer.parseInt(estoque.getText()),
            fornecedor.getText(),
            descricao.getText()
    );

    // Adiciona o novo cliente à lista
    cliente.add(clienteTemp);

    // Chama o método de salvar no DAO para persistir o cliente
    clienteDAO.salvar(clienteTemp);

    // Atualiza a TableView
    tableView.setItems(cliente); // Atualiza a TableView com a nova lista

    // Limpa os campos para permitir a inserção de um novo cliente
    limparCampos();

    // Atualiza o estado do formulário
    estadoFormulario = "inicial";
    atualizarBotoes();
}




    @FXML
    private void handleProximo() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/petshop/FXMLTelaProduto.fxml"));
        stage = new Stage();
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setTitle("Cadastro de Produto");
        stage.show();
    }

    @FXML
    private void handleEditar() {
        clienteAtual = tableView.getSelectionModel().getSelectedItem();

        if (clienteAtual != null) {
            produto.setText(clienteAtual.getProduto());
            cod.setText(String.valueOf(clienteAtual.getCod()));
            valorUnt.setText(String.valueOf(clienteAtual.getValorUnt()));
            quantidade.setText(String.valueOf(clienteAtual.getQuantidade()));
            estoque.setText(String.valueOf(clienteAtual.getEstoque()));
            fornecedor.setText(clienteAtual.getFornecedor());
            descricao.setText(clienteAtual.getDescricao());

            estadoFormulario = "editando";
            atualizarBotoes();

            System.out.println("Editando produto: " + clienteAtual);
        } else {
            showAlert(AlertType.INFORMATION, "Seleção necessária", "Por favor, selecione um produto para editar.");
        }
    }

    @FXML
private void handleSalvar() {
    if (clienteAtual == null) {
        // Caso não haja cliente selecionado para edição
        showAlert(AlertType.ERROR, "Erro", "Não há produto para salvar.");
        return;
    }

    // Atualizando as informações do cliente
    clienteAtual.setProduto(produto.getText());
    clienteAtual.setCod(Integer.parseInt(cod.getText()));
    clienteAtual.setValorUnt(Double.parseDouble(valorUnt.getText()));
    clienteAtual.setQuantidade(Integer.parseInt(quantidade.getText()));
    clienteAtual.setEstoque(Integer.parseInt(estoque.getText()));
    clienteAtual.setFornecedor(fornecedor.getText());
    clienteAtual.setDescricao(descricao.getText());

    // Chama o método de salvar no DAO
    clienteDAO.salvar(clienteAtual);

    // Atualiza a lista da TableView removendo e re-adicionando o cliente atualizado
    int index = cliente.indexOf(clienteAtual);  // Encontra o índice do cliente na lista
    if (index != -1) {
        cliente.set(index, clienteAtual);  // Substitui o cliente na lista
    }

    // Atualiza a TableView
    tableView.setItems(cliente);

    // Atualiza o estado do formulário e limpa os campos
    estadoFormulario = "inicial";
    clienteAtual = null;
    atualizarBotoes();
    showAlert(AlertType.INFORMATION, "Sucesso", "Produto salvo com sucesso.");
    limparCampos();
}


    @FXML
    private void handleCancelar() {
        limparCampos();
        estadoFormulario = "inicial";
        clienteAtual = null;
        atualizarBotoes();
    }

    @FXML
    private void handleExcluir() {
        clienteAtual = tableView.getSelectionModel().getSelectedItem();
        if (clienteAtual != null) {
            cliente.remove(clienteAtual); // Correção aqui: use o nome correto da variável
            showAlert(AlertType.INFORMATION, "Sucesso", "Produto excluído com sucesso!");
        } else {
            showAlert(AlertType.INFORMATION, "Seleção necessária", "Por favor, selecione um produto para excluir.");
        }

        estadoFormulario = "inicial";
        atualizarBotoes();
    }

    private void atualizarBotoes() {
        switch (estadoFormulario) {
            case "inicial":
                adicionar.setDisable(false);
                salvar.setDisable(true);
                cancelar.setDisable(true);
                excluir.setDisable(false);
                proximo.setDisable(false);
                editar.setDisable(false);
                break;
            case "editando":
                adicionar.setDisable(true);
                salvar.setDisable(false);
                cancelar.setDisable(false);
                excluir.setDisable(true);
                proximo.setDisable(false);
                editar.setDisable(false);
                break;
        }
    }

    private void showAlert(AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Limpa todos os campos de texto
    private void limparCampos() {
        produto.clear();
        cod.clear();
        valorUnt.clear();
        quantidade.clear();
        estoque.clear();
        fornecedor.clear();
        descricao.clear();
    }
}







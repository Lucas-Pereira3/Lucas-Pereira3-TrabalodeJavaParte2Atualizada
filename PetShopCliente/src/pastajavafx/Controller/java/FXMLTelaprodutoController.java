package pastajavafx.Controller.java;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.application.Platform; 
import javafx.scene.control.ButtonType;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pastajavafx.model.Produto;
import pastajavafx.model.dao.ProdutoDAO;
import pastajavafx.model.dao.ProdutoDAOImpl;


public class FXMLTelaprodutoController implements Initializable {

    @FXML
    private Button Botao_Excluir;
    @FXML
    private Button Botao_Cancelar;
    @FXML
    private Button Botao_Adicionar;
    @FXML
    private Button Botao_Salvar;
    @FXML
    private Button Botao_Sair;
    @FXML
    private Button Botao_Editar;
     @FXML
    private Button VoltarTela;

    @FXML
    private TextField campoCodigo;
    @FXML
    private TextField campoNome;
    @FXML
    private TextField campoPreco;
    @FXML
    private TextField campoVenda;
    @FXML
    private TextField campoQuantidade;
    @FXML
    private TextField campoVendedor;
    @FXML
    private TextField campoTotal;
    @FXML
    private TextField campoValor;
    @FXML
    private TextField campoDesconto;
    @FXML
    private TextField campoPrecoUnitario;
    
    private Stage stage;

    private ProdutoDAO produtoDAO = new ProdutoDAOImpl(); // Instanciando o DAO
    private String estadoFormulario = "inicial"; // Controle do estado do formulário

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("Inicializando a tela.");
        atualizarBotoes(); // Atualiza o estado dos botões
    }

    @FXML
    private void handleAdicionar() {
        estadoFormulario = "editando";
        Produto produtoAtual = new Produto(0, "", 0.0, 0.0, 0.0, 0.0, "", 0.0); // Cria um novo produto em branco
        atualizarBotoes();
        limparCamposFormulario();  
    }

    
     @FXML
    private void handleVoltar() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/petshop/FXMLTelaCliente.fxml"));
        stage = new Stage();
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.setTitle("Cadastro de Produto"); 
        stage.show();
    }
    @FXML
    private void handleSalvar() {
        System.out.println("Salvando os dados.");

        try {
            // Obtém dados dos campos de entrada
            String nome = campoNome.getText();
            double preco = Double.parseDouble(campoPreco.getText());
            double quantidade = Double.parseDouble(campoQuantidade.getText());
            double valor = Double.parseDouble(campoValor.getText());
            double desconto = Double.parseDouble(campoDesconto.getText());
            String vendedor = campoVendedor.getText();
            double total = Double.parseDouble(campoTotal.getText());

            // Cria o produto
            Produto produto = new Produto(0, nome, preco, quantidade, valor, desconto, vendedor, total);

            // Salva o produto
            produtoDAO.salvar(produto);
            estadoFormulario = "adicionado";
            atualizarBotoes();
            limparCamposFormulario();  
            
            // Exibe alerta de sucesso
            Alert alerta = new Alert(AlertType.INFORMATION);
            alerta.setTitle("Sucesso");
            alerta.setHeaderText(null);
            alerta.setContentText("Produto salvo com sucesso!");
            alerta.showAndWait();

        } catch (NumberFormatException e) {
            System.out.println("Erro ao salvar: Dados de preço, quantidade, valor e total devem ser numéricos.");
            
            // Exibe alerta de erro
            Alert alerta = new Alert(AlertType.ERROR);
            alerta.setTitle("Erro");
            alerta.setHeaderText("Dados Inválidos");
            alerta.setContentText("Preço, quantidade, valor e total devem ser numéricos.");
            alerta.showAndWait();
        }
    }

    @FXML
    private void handleCancelar() {
        System.out.println("Cancelando a edição.");
        estadoFormulario = "inicial";
        atualizarBotoes();
        limparCamposFormulario();  
    }

    @FXML
    private void handleExcluir() {
        System.out.println("Excluindo item.");

        // Alerta de confirmação para exclusão
        Alert alertaConfirmacao = new Alert(AlertType.CONFIRMATION);
        alertaConfirmacao.setTitle("Confirmar Exclusão");
        alertaConfirmacao.setHeaderText("Você tem certeza que deseja excluir este produto?");
        alertaConfirmacao.setContentText("Esta ação não pode ser desfeita.");
        
        // Espera a resposta do usuário
        ButtonType resposta = alertaConfirmacao.showAndWait().orElse(ButtonType.CANCEL);

        if (resposta == ButtonType.OK) {
            try {
                int codigo = Integer.parseInt(campoCodigo.getText());
                produtoDAO.excluir(codigo);
                estadoFormulario = "excluindo";
                atualizarBotoes();
                limparCamposFormulario();  

                // Alerta de sucesso após exclusão
                Alert alertaSucesso = new Alert(AlertType.INFORMATION);
                alertaSucesso.setTitle("Sucesso");
                alertaSucesso.setHeaderText(null);
                alertaSucesso.setContentText("Produto excluído com sucesso!");
                alertaSucesso.showAndWait();

            } catch (NumberFormatException e) {
                System.out.println("Erro ao excluir: Código do produto inválido.");

                // Alerta de erro ao tentar excluir
                Alert alertaErro = new Alert(AlertType.ERROR);
                alertaErro.setTitle("Erro");
                alertaErro.setHeaderText("Código Inválido");
                alertaErro.setContentText("Digite um código válido para excluir o produto.");
                alertaErro.showAndWait();
            }
        }
    }

    @FXML
    private void handleSair() {
        System.out.println("Saindo do aplicativo.");
        // Finaliza a aplicação
        System.exit(0); // Encerra a aplicação
    }

    @FXML
    private void handleEditar() {
        System.out.println("Editando item.");
        try {
            int codigo = Integer.parseInt(campoCodigo.getText());
            Produto produto = produtoDAO.buscarPorId(codigo);

            if (produto != null) {
                // Preenche os campos com os dados do produto
                campoNome.setText(produto.getProduto());
                campoPreco.setText(String.valueOf(produto.getPreco()));
                campoQuantidade.setText(String.valueOf(produto.getQuantidade()));
                campoValor.setText(String.valueOf(produto.getValor()));
                campoDesconto.setText(String.valueOf(produto.getDesconto()));
                campoVendedor.setText(produto.getVendedor());
                campoTotal.setText(String.valueOf(produto.getTotal()));

                estadoFormulario = "editando";
                atualizarBotoes();
            } else {
                System.out.println("Produto não encontrado.");

                // Alerta de produto não encontrado
                Alert alerta = new Alert(AlertType.ERROR);
                alerta.setTitle("Erro");
                alerta.setHeaderText("Produto Não Encontrado");
                alerta.setContentText("Nenhum produto foi encontrado com o código informado.");
                alerta.showAndWait();
            }
        } catch (NumberFormatException e) {
            System.out.println("Erro ao editar: Código do produto inválido.");

            // Alerta de erro ao editar produto
            Alert alerta = new Alert(AlertType.ERROR);
            alerta.setTitle("Erro");
            alerta.setHeaderText("Código Inválido");
            alerta.setContentText("Digite um código válido para editar o produto.");
            alerta.showAndWait();
        }
    }

    private void atualizarBotoes() {
        System.out.println("Atualizando botões. Estado atual: " + estadoFormulario);
        switch (estadoFormulario) {
            case "inicial":
                Botao_Adicionar.setDisable(false);
                Botao_Salvar.setDisable(true);
                Botao_Cancelar.setDisable(true);
                Botao_Excluir.setDisable(false);
                Botao_Editar.setDisable(false);
                Botao_Sair.setDisable(false);
                
                break;

            case "editando":
                Botao_Adicionar.setDisable(true);
                Botao_Salvar.setDisable(false);
                Botao_Cancelar.setDisable(false);
                Botao_Excluir.setDisable(true);
                Botao_Editar.setDisable(true);
                Botao_Sair.setDisable(false);
                VoltarTela.setDisable(false);
                break;

            case "adicionado":
                Botao_Adicionar.setDisable(false);
                Botao_Salvar.setDisable(true);
                Botao_Cancelar.setDisable(true);
                Botao_Excluir.setDisable(false);
                Botao_Editar.setDisable(false);
                Botao_Sair.setDisable(false);
                VoltarTela.setDisable(false);
                break;

            case "excluindo":
                Botao_Adicionar.setDisable(true);
                Botao_Salvar.setDisable(true);
                Botao_Cancelar.setDisable(false);
                Botao_Excluir.setDisable(true);
                Botao_Editar.setDisable(true);
                Botao_Sair.setDisable(false);
                VoltarTela.setDisable(false);
                break;
        }
    }

    private void limparCamposFormulario() {
        if (campoCodigo != null) campoCodigo.clear();
        if (campoNome != null) campoNome.clear();
        if (campoPreco != null) campoPreco.clear();
        if (campoVenda != null) campoVenda.clear();
        if (campoQuantidade != null) campoQuantidade.clear();
        if (campoVendedor != null) campoVendedor.clear();
        if (campoTotal != null) campoTotal.clear();
        if (campoValor != null) campoValor.clear();
        if (campoDesconto != null) campoDesconto.clear();
        if (campoPrecoUnitario != null) campoPrecoUnitario.clear();
    }
}

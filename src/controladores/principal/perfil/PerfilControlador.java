package controladores.principal.perfil;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import modelos.entidades.Funcionario;

public class PerfilControlador {

    @FXML
    private TextField nome;

    @FXML
    private TextField email;

    @FXML
    private TextField senha;

    @FXML
    private TextField cpf;

    @FXML
    private TextField estado;

    @FXML
    private TextField municipio;

    @FXML
    private TextField bairro;

    private Stage tela;

    @FXML
    public void confirmar(ActionEvent event) {
        if (tela != null) {
            tela.close();
        }
    }

    @FXML
    public void fechar(MouseEvent event) {
        if (tela != null) {
            tela.close();
        }
    }

    public void setConectado(Funcionario funcionario) {
        if (funcionario != null) {
            setNome(funcionario.getNome());
            setEmail(funcionario.getEmail());
            setSenha(funcionario.getSenha());
            setCpf(funcionario.getCpf());
            setEstado(funcionario.getEndereco().getEstado().getNome());
            setMunicipio(funcionario.getEndereco().getCidade().getNome());
            setBairro(funcionario.getEndereco().getBairro());
        }
    }

    public void setTela(Stage tela) {
        this.tela = tela;
    }

    public void setNome(String nome) {
        this.nome.setText(nome);
    }

    public void setEmail(String email) {
        this.email.setText(email);
    }

    public void setSenha(String senha) {
        this.senha.setText(senha);
    }

    public void setCpf(String cpf) {
        this.cpf.setText(cpf);
    }

    public void setEstado(String estado) {
        this.estado.setText(estado);
    }

    public void setMunicipio(String municipio) {
        this.municipio.setText(municipio);
    }

    public void setBairro(String bairro) {
        this.bairro.setText(bairro);
    }

}

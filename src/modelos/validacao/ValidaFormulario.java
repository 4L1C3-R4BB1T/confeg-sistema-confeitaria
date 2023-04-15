package modelos.validacao;

import javafx.scene.control.Label;
import modelos.entidades.Cidade;
import modelos.entidades.Estado;
import modelos.entidades.TipoFuncionario;

public class ValidaFormulario extends Validacao {
    
    public boolean validarTipo(Label areaErro, TipoFuncionario tipo) {
        areaErro.setStyle("-fx-text-fill: red;");
        if (tipo == null) {
            areaErro.setText("* Por favor, selecione tipo de funcionário.");
            return false;
        } 
        areaErro.setStyle("-fx-text-fill: green;");
        areaErro.setText("✓ Válido");
        return true;
    }

    public boolean validarNome(Label areaErro, String nome) {
        areaErro.setStyle("-fx-text-fill: red;");
        if (nome.trim().isEmpty()) {
            areaErro.setText("* Preencha o campo nome");
            return false;
        } 
        areaErro.setStyle("-fx-text-fill: green;");
        areaErro.setText("✓ Válido");
        return true;
    }

    
    public boolean validarCep(Label areaErro, String cep) {
        areaErro.setStyle("-fx-text-fill: red;");
        if (cep.trim().isEmpty()) {
            areaErro.setText("* Preencha o campo cep");
            return false;
        } else if (!this.validarCep(cep)) {
            areaErro.setText("*- Cep inválido");
            return false;
        }
        areaErro.setStyle("-fx-text-fill: green;");
        areaErro.setText("✓ Válido");
        return true;
    }


    public boolean validarCPF(Label areaErro, String cpf) {
        areaErro.setStyle("-fx-text-fill: red;");
        if (cpf.trim().isEmpty()) {
            areaErro.setText("* Preencha o campo cpf");
            return false;
        } else if (!this.validarCpf(cpf)) {
            areaErro.setText("- Não é um cpf válido");
            return false;
        } 
        areaErro.setStyle("-fx-text-fill: green;");
        areaErro.setText("✓ Válido");
        return true;
    }

    public boolean validarEstado(Label areaErro, Estado estado) {
        areaErro.setStyle("-fx-text-fill: red;");
        if (estado == null) {
            areaErro.setText("* Selecione o estado");
            return false;
        }
        areaErro.setStyle("-fx-text-fill: green;");
        areaErro.setText("✓ Válido");
        return true;
    }

    public boolean validarCidade(Label areaErro, Estado estado, Cidade cidade) {
        areaErro.setStyle("-fx-text-fill: red;");
        if (estado == null) {
            areaErro.setText("- Escolha o estado para liberar as cidades");
            return false;
        } else if (cidade == null) {
            areaErro.setText("* Selecione a cidade");
            return false;
        } 

        areaErro.setStyle("-fx-text-fill: green;");
        areaErro.setText("✓ Válido");
        return true;
    }

    public boolean validarBairro(Label areaErro, String bairro) {
        areaErro.setStyle("-fx-text-fill: red;");
        if (bairro.trim().isEmpty()) {
            areaErro.setText("* Preencha o campo bairro");
            return false;
        } 
        areaErro.setStyle("-fx-text-fill: green;");
        areaErro.setText("✓ Válido");
        return true;
    }

    public boolean validarRua(Label areaErro, String rua) {
        areaErro.setStyle("-fx-text-fill: red;");
        if (rua.trim().isEmpty()) {
            areaErro.setText("* Preencha o campo rua");
            return false;
        } 
        areaErro.setStyle("-fx-text-fill: green;");
        areaErro.setText("✓ Válido");
        return true;
    }

    public boolean validarNum(Label areaErro, String numero) {
        areaErro.setStyle("-fx-text-fill: red;");
        if (!numero.trim().isEmpty()) {
            areaErro.setText("* Preencha o campo número");
            return false;
        } else if (!this.validarNumero(numero)) {
            areaErro.setText("- Número inválido");
            return false;
        } 

        areaErro.setStyle("-fx-text-fill: green;");
        areaErro.setText("✓ Válido");
        return true;
    }
}

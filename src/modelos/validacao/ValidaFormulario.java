package modelos.validacao;

import javafx.scene.control.Label;

public class ValidaFormulario extends Validacao {
    
    public boolean validarComboBox(Label area, Object obj, String msg) {
        area.setStyle("-fx-text-fill: red;");
        if (obj == null) {
            if (msg != null) {
                area.setText("* " + msg);
            } else {
                area.setText("* Selecione um opção.");
            }
            return false;
        } else {
            area.setStyle("-fx-text-fill: green;");
            area.setText("✓ Válido");
            return true;
        }
    }

    public boolean validarCampo(Label area, String valor, String msg) {
        area.setStyle("-fx-text-fill: red;");
        if (valor.trim().isEmpty()) {
            if (area != null) {
                if (msg != null) {
                    area.setText("* " + msg);
                } else {
                    area.setText("* Selecione um opção.");
                }
                area.setText("* Preencha este campo");
            }
            return false;
        } else {
            if (area != null) {
                area.setStyle("-fx-text-fill: green;");
                area.setText("✓ Válido");
            }
            return true;
        }
    }
    
    public boolean validarCep(Label area, String cep) {
        if (!validarCampo(area, cep, "Preencha o campo CEP.")) {
            return false;
        } else if (!this.validarCep(cep)) {
            area.setStyle("-fx-text-fill: red;");
            area.setText("- Cep inválido");
            return false;
        } else {
            area.setStyle("-fx-text-fill: green;");
            area.setText("✓ Válido");
            return true;
        }
      
    }

    public boolean validarCPF(Label area, String cpf) {
        if (!validarCampo(area, cpf, "Preencha o campo CPF")) {
            return false;
        } else if (!this.validarCpf(cpf)) {
            area.setStyle("-fx-text-fill: red;");
            area.setText("- Não é um CPF válido");
            return false;
        } else {
            area.setStyle("-fx-text-fill: green;");
            area.setText("✓ Válido");
            return true;
        }
    }

    public boolean validarValorNumerico(Label area, String numero) {
        if (!this.validarCampo(area, numero, "Preencha o campo número")) {
            return false;
        } else if (!this.validarNumero(numero)) {
            area.setStyle("-fx-text-fill: red;");
            area.setText("- Número inválido");
            return false;
        } else {
            area.setStyle("-fx-text-fill: green;");
            area.setText("✓ Válido");
            return true;
        }
    }

    public boolean validarData(Label area, String data, String msg) {
        if (!this.validarCampo(area, data, msg)) {
            return false;
        } else if (!data.matches("^\\d{2}\\/\\d{2}\\/\\d{4}$")) {
            if (area != null) {
                area.setStyle("-fx-text-fill: red;");
                area.setText("- O formato da data é inválido");
            }
            return false;
        } else {
            if (area != null) {
                area.setStyle("-fx-text-fill: green;");
                area.setText("✓ Válido");
            }
            return true;
        }
    }

  
}

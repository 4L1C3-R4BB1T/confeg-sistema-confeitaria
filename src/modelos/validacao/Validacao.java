package modelos.validacao;


public class Validacao {

    public Validacao() {}

    public boolean validarCpf(String valor) {
        if (valor == null) return false;
        return valor.replaceAll("[.-]", "").matches("^\\d{11}$");
    } 

    public boolean validarCep(String valor) {
        if (valor == null) return false;
        return valor.replaceAll("[-]", "").matches("^\\d{8}$");
    }


    public boolean validarNumero(String valor) {
        try {
            Double.parseDouble(valor);
            return true;
        } catch (Exception erro) {
            return false;
        }
    }

    public boolean validarNuloOuVazio(Object obj) {
        if (obj == null) return false;
        if (obj instanceof String) {
            if (((String) obj).trim().isEmpty()) return false;
        }
        return true;
    }


}

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

    public  String formatarCPF(String valor) {
        if (valor == null) {
            throw new RuntimeException("CPF: valor passado é null.");
        }
        if (!valor.matches("\\d{11}")) {
            throw new RuntimeException("CPF: não é uma sequência válida.");
        }

        String primeiro = valor.substring(0, 3);
        String segundo = valor.substring(3, 6);
        String terceiro = valor.substring(6, 9);
        String ultimo = valor.substring(9);
        return String.format("%s.%s.%s-%s", primeiro, segundo, terceiro, ultimo);
    }

}

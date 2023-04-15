package modelos.validacao;

public final class Validacao {

    private Validacao() {}

    public static boolean validarCpf(String valor) {
        if (valor == null) return false;
        return valor.replaceAll("[.-]", "").matches("^\\d{11}$");
    } 

    public static boolean validarCep(String valor) {
        if (valor == null) return false;
        return valor.replaceAll("[-]", "").matches("^\\d{8}$");
    }

    public static boolean validarNumero(String valor) {
        try {
            Integer.parseInt(valor);
            return true;
        } catch (Exception erro) {
            return false;
        }
    }

    public static boolean validarNuloOuVazio(Object obj) {
        if (obj == null) return false;
        if (obj instanceof String) {
            if (((String) obj).trim().isEmpty()) return false;
        }
        return true;
    }


}

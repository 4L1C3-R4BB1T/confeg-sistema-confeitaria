package modelos.validacao;

import java.util.Base64;

public class Validacao {

    public Validacao() {}

    public String encode(String valor) {
        if (valor == null) {
            throw new RuntimeException("Valor null");
        }
        byte[] bytes = valor.getBytes();
        return new String(Base64.getEncoder().encode(bytes));
    }

    public String decode(String valor) {
        if (valor == null) {
            throw new RuntimeException("Valor null");
        }
        byte[] bytes = valor.getBytes();
        return new String(Base64.getDecoder().decode(bytes));
    }

    @SuppressWarnings("unused")
    public String colocarAsterisco(String valor) {
        if (valor == null) {
            throw new RuntimeException("Valor null");
        }
        StringBuilder sb = new StringBuilder();
        for (char c: valor.toCharArray()) {
            sb.append("*");
        }
        return sb.toString();
    }

    public boolean validarCpf(String valor) {
        if (valor == null) return false;
        return valor.replaceAll("[.-]", "").matches("^\\d{11}$");
    } 

    public  boolean existeCpf(String cpf) {
        cpf = cpf.replaceAll("[^0-9]", "");

        if (cpf.length() != 11 || cpf.matches("(\\d)\\1{10}")) {
            return false;
        }

        int[] digitos = new int[11];
        for (int i = 0; i < 11; i++) {
            digitos[i] = Character.getNumericValue(cpf.charAt(i));
        }

        int soma = 0;
        for (int i = 0; i < 9; i++) {
            soma += digitos[i] * (10 - i);
        }
        int digito1 = 11 - (soma % 11);
        if (digito1 > 9) {
            digito1 = 0;
        }

        soma = 0;
        for (int i = 0; i < 10; i++) {
            soma += digitos[i] * (11 - i);
        }
        int digito2 = 11 - (soma % 11);
        if (digito2 > 9) {
            digito2 = 0;
        }

        return digito1 == digitos[9] && digito2 == digitos[10];
    }

    public boolean validarCep(String valor) {
        if (valor == null) return false;
        return valor.matches("\\d{5}-?\\d{3}");
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

    public String formatarCPF(String valor) {
        if (valor == null) {
            throw new RuntimeException("CPF: valor passado é null");
        }
        if (!valor.matches("\\d{11}")) {
            throw new RuntimeException("CPF: não é uma sequência válida");
        }

        String primeiro = valor.substring(0, 3);
        String segundo = valor.substring(3, 6);
        String terceiro = valor.substring(6, 9);
        String ultimo = valor.substring(9);
        return String.format("%s.%s.%s-%s", primeiro, segundo, terceiro, ultimo);
    }

    public String limparCPF(String valor) {
        if (valor == null) {
            throw new RuntimeException("CPF: valor passado é null");
        } else if (!validarCpf(valor)) {
            throw new RuntimeException("CPF: não é uma sequência válida");
        } else {
            return valor.replaceAll("[.-]", "");
        }
    }

    public String formatarCep(String valor) {
        return valor.replaceAll("[-]", "");
    }

    public String formatarCepEntrada(String valor) {
        if (valor == null) {
            throw new RuntimeException("CEP: valor passado é null");
        } else if (!valor.matches("\\d{8}")) {
            throw new RuntimeException("CEP: não é uma sequência válida");
        } else {
            return valor.replaceAll("(\\d{5})(\\d{3})", "$1-$2");
        }
    }

    public String formatarTelefone(String valor) {
        if (valor == null) {
            throw new RuntimeException("Telefone: valor passado é null");
        } else if (!valor.matches("\\d{11}")) {
            throw new RuntimeException("Telefone: não é uma sequência válida");
        } else {
            return valor.replaceAll("(\\d{2})(\\d{5})(\\d{4})", "($1) $2-$3");
        }
    }

    public String limparTelefone(String valor) {
        if (valor != null) {
            return valor.replaceAll("\\D", "");
        }
        throw new RuntimeException("Telefone: valor passado é null");
    }

}

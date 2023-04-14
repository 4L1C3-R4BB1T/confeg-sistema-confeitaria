import java.util.ArrayList;
import java.util.List;

@FunctionalInterface
interface Conversor<T, R> {
    R converter(T t);
}


public class App {

    public static void main(String[] args) {
        
        Conversor<String, Double> lambda = new Conversor<String, Double>() {
            @Override 
            public Double converter(String v) {
                final String regex = "^\\d+(\\.\\d+)*$";
                if (v != null && v.matches(regex)) {
                    return Double.parseDouble(v);
                } else {
                    return null;
                }
            }
        };

        System.out.println(lambda.converter("125.24"));


        List<Pessoa> pessoas = new ArrayList<>();

        pessoas.add(new Pessoa("Livia", 22));
        pessoas.add(new Pessoa("Gabriel", 23));
        pessoas.add(new Pessoa("Marcos", 15));

        for (Pessoa p : pessoas) {
            System.out.println(p);
        }

    }
}

class Pessoa {
    String nome;
    Integer idade;

    Pessoa(String nome, Integer idade) {
        this.nome = nome;
        this.idade = idade;
    }

    @Override
    public String toString() {
        return "nome: " + nome + " - idade: " + idade;
    }
}
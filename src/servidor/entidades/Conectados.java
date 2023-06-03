package servidor.entidades;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import modelos.entidades.Funcionario;

public class Conectados implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<Funcionario> conectados = new ArrayList<>();

    public void adicionarConectado(Funcionario f) {
        conectados.add(f);
    }

    public void adicionarTodos(List<Funcionario> conectados) {
        this.conectados.addAll(conectados);
    }
    
    public List<Funcionario> getConectados() {
        return conectados;
    }
    
}

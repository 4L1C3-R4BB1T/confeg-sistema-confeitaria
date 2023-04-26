import java.net.Socket;

import modelos.entidades.Funcionario;

public class Informacao {

    private Socket socket;
    private Funcionario funcionario;

    public Informacao(Socket socket, Funcionario funcionario) {
        this.socket = socket;
        this.funcionario = funcionario;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

}

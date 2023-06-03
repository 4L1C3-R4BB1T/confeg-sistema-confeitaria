package servidor;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import modelos.entidades.Funcionario;
import servidor.entidades.Conectados;
import servidor.entidades.Informacao;
import servidor.entidades.Mensagem;

public class ChatServidor {

    private ServerSocket servidor;
    private Map<InetAddress, Informacao> conectados = new ConcurrentHashMap<>();

    public ChatServidor(int port) {
        try {
            this.servidor = new ServerSocket(port);
            while (true) {
                Socket socket = this.servidor.accept();
                new Thread(new Cliente(socket)).start();
            }
        } catch (Exception erro) {
            System.out.println("Não foi possível abrir o servidor.");
        }
    }

    public class Cliente implements Runnable {

        private Socket soquete;
        private Funcionario funcionario;

        public Cliente(Socket soquete) {
            this.soquete = soquete;
        }

        @Override 
        public void run() {
            try {
                while (true) {
                    ObjectInputStream entrada = new ObjectInputStream(soquete.getInputStream());
                    Object objeto = entrada.readObject();
                    InetAddress endereco = this.soquete.getInetAddress();

                    if (objeto instanceof Funcionario) {
                        funcionario = (Funcionario) objeto;
                        conectados.put(endereco, new Informacao(soquete, funcionario));
                    }

                    List<Socket> soquetes = conectados.values().stream().map( info -> info.getSocket()).collect(Collectors.toList());

                    if (objeto instanceof String) {
                        String msg = (String) objeto;
                        System.out.println(msg);
                        enviarMensagem(soquetes, msg);
                    }

                    enviarConectados(soquetes);
                }
            } catch (Exception e) {
                try {
                    conectados.remove(soquete.getInetAddress());
                    soquete.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }

        public void enviarConectados(List<Socket> soquetes) throws Exception {
            List<Funcionario> funcionarios = conectados.values().stream().map( info -> info.getFuncionario()).collect(Collectors.toList());

            for (Socket soquete: soquetes) {
                ObjectOutputStream saida = new ObjectOutputStream(soquete.getOutputStream());
                Conectados conexoes = new Conectados();
                conexoes.adicionarTodos(funcionarios);
                saida.writeObject(conexoes);
            }
        }

        public void enviarMensagem(List<Socket> soquetes, String conteudo) throws Exception {
            for (Socket soquete: soquetes) {
                ObjectOutputStream saida = new ObjectOutputStream(soquete.getOutputStream());
                Mensagem mensagem = new Mensagem(funcionario, conteudo);
                saida.writeObject(mensagem);
            } 
        }
        
    }

    public static void main(String[] args) {
        new ChatServidor(3000);
    }

}
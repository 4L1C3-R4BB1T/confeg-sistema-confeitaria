
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import modelos.entidades.Conectado;
import modelos.entidades.Funcionario;
import modelos.entidades.Mensagem;


public class ChatServidor {

    private ServerSocket servidor;
    private Map<InetAddress, Informacao> conectados = new ConcurrentHashMap<>();

    public ChatServidor(int port) throws Exception {
        this.servidor = new ServerSocket(port);
        while (true) {
            Socket socket = this.servidor.accept();
            new Thread(new Cliente(socket)).start();
        }
    }

    public class Cliente implements Runnable {

        private Socket soquete;

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
                        Funcionario funcionario = (Funcionario) objeto;
                        conectados.put(endereco, new Informacao(soquete, funcionario));
                    }


                    if (objeto instanceof String) {
                        String mensagem = (String) objeto;
                        System.out.println(mensagem);
                        enviarMensagem(mensagem);
                    }

                    enviarConectados();
                    
                }


            } catch (Exception e) {
                e.printStackTrace();
               
                try {
                    soquete.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
          

        }

        public void enviarConectados() {
            try {
    
                List<Socket> soquetes = conectados.values().stream().map( info -> info.getSocket()).collect(Collectors.toList());
                List<Funcionario> funcionarios = conectados.values().stream().map( info -> info.getFuncionario()).collect(Collectors.toList());

                for (Socket so : soquetes) {
                    ObjectOutputStream saida = new ObjectOutputStream(so.getOutputStream());
                    Conectado conexoes = new Conectado();
                    conexoes.adicionarTodos(funcionarios);
                    saida.writeObject(conexoes);
                }
                
            } catch(Exception erro) {
                erro.printStackTrace();
            }
        }

        public void enviarMensagem(String conteudo) {
            try {
                List<Socket> soquetes = conectados.values().stream().map( info -> info.getSocket()).collect(Collectors.toList());
                List<Funcionario> funcionarios = conectados.values().stream().map( info -> info.getFuncionario()).collect(Collectors.toList());

                for (int i = 0 ; i < soquetes.size() ; i++) {
                    Socket soquete = soquetes.get(i);
                    Funcionario funcionario = funcionarios.get(i);
                    ObjectOutputStream saida = new ObjectOutputStream(soquete.getOutputStream());
                    Mensagem mensagem = new Mensagem(funcionario, conteudo);
                    saida.writeObject(mensagem);
                }
            } catch(Exception erro) {
                erro.printStackTrace();
            }
        }
    }





    public static void main(String[] args) throws Exception {
        new ChatServidor(3000);
    }

   
}
package modelos.consultas.entitidades;

import java.time.LocalDate;

import javafx.animation.Animation.Status;
import javafx.scene.layout.HBox;

public class PedidoConfirmado {

    private Long codigo;
    private String nome;
    private Long pedido;
    private String data;
    private String pago;
    private HBox botoes;

    public PedidoConfirmado(Long codigo, String nome, Long pedido, LocalDate data, Status status) {
        
    }
    
}

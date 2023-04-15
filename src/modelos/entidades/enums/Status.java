package modelos.entidades.enums;

public enum Status {

	PENDENTE("PENDENTE"),
    CONCLUIDO("CONCLUIDO"),
    CANCELADO("CANCELADO");
	
	private final String descricao;

    Status(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

}

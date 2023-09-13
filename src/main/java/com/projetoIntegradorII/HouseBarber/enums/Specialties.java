package com.projetoIntegradorII.HouseBarber.enums;

public enum Specialties {
    CABELO(0, "Cortar cabelo"), BARBA(1, "Barbear"), ATENDIMENTO(2, "Atendimento");
	
	private Integer codigo;
	private String descricao;
	
	private Specialties(Integer codigo, String descricao) {
		this.codigo = codigo;
        this.descricao = descricao;
    }

    public Integer getCodigo() {
		return codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public static Specialties toEnum(Integer cod) {
		if(cod == null) {
			return null;
		}
		for(Specialties x : Specialties.values() ) {
			if(cod.equals(x.getCodigo())) {
				return x;
			}
		}
		throw new IllegalArgumentException("Especializações invalidas");
	}
}

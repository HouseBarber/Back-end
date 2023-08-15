package com.projetoIntegradorII.HouseBarber.enums;

public enum Gender {
    MASCULINO(0, "MASCULINO"), FEMININO(1, "FEMININO");
	
	private Integer codigo;
	private String descricao;
	
	private Gender(Integer codigo, String descricao) {
		this.codigo = codigo;
        this.descricao = descricao;
    }

    public Integer getCodigo() {
		return codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public static Gender toEnum(Integer cod) {
		if(cod == null) {
			return null;
		}
		for(Gender x : Gender.values() ) {
			if(cod.equals(x.getCodigo())) {
				return x;
			}
		}
		throw new IllegalArgumentException("Genero invalido");
	}
}

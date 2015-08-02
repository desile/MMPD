package com.study.mmpd;

/**
 * Тип частиц - А или С.
 * 
 * @author DeSile
 * @version 1.0
 */
public enum ParticleType {
	A(1),C(2);
	
	int code;
	
	ParticleType(int code){
		this.code = code;
	}
	
	public static int getCode(ParticleType type){
		switch (type){
			case A:
				return 1;
			case C:
				return 2;
		}
		return 0;
	}
	
	/**
	 * Перевод типа в числовое значение.
	 */
	public int getCode(){
		return code;
	}
	
	
}

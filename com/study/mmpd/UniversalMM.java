package com.study.mmpd;

/**
 * Общий интерфейс 2D и 3D сцены.
 * @author DeSile
 * @version 1.0
 */
public interface UniversalMM {
	
	/**
	 * @return Количество частиц на экране (не считая основания)
	 */
	public int getNumberOfParticles();
	
	/**
	 * Очистка сцены и массива частиц.
	 */
	public void reset();
	
	/**
	 * 
	 * @return Шанс возникновения частицы типа С.
	 */
	public float getPaticleChance();
	
	/**
	 * 
	 * @param chance Шанс возникновения частицы типа С.
	 */
	public void setParticleChance(float chance);
	
}

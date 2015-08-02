package com.study.mmpd;


import java.util.ArrayList;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.physics.bullet.linearmath.int4;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Timer;

/**
 * Генератор двумерных частиц.
 * @author DeSile
 * @version 1.0
 */

public class ParticleGenerator {

	/**
	 * Размер генерируемых частиц.
	 */
	public static int particleSize = 20;
	
	/**
	 * <p>Сетка ячеек.</p>
	 * <p>Принимает разные значения в зависимости от находящийся частицы в ячейке.</p>
	 * <ol start="0">
	 * <li>Если пусто.</li>
	 * <li>Если частица типа А.</li>
	 * <li>Если частица типа С.</li>
	 * </ol>
	 */
	private static int[][] particlesMap;
	
	/**
	 * Величина сетки ячеек по X.
	 */
	private static int numX = (int)(MMPDHandler.WIDTH/particleSize) + 1;
	/**
	 * Величина сетки ячеек по Y.
	 */
	private static int numY = (int)(MMPDHandler.HEIGHT/particleSize) + 1;
	
	
	public ShapeRenderer sr = new ShapeRenderer();
	
	public ArrayList<Particle> particles;
	
	/**
	 * Шанс генерации частицы типа С.
	 */
	public float particleChance = 0.3f;
	
	public ParticleGenerator(ArrayList<Particle> particles){
		this.particles = particles;
		particlesMap = new int[numX][numY];
	}
	
	/**
	 * Генерация фундамента.
	 */
	public void generateFloor(){
		int numOfPtc = numX;
		for(int i = 0; i < numOfPtc; i++){
			particles.add(new Particle(i*particleSize, 0, particleSize, ParticleType.A,sr));
			particlesMap[i][0] = 1;
		}
	}
	
	/**
	 * Метод генерации одной частицы в случайной колонке.
	 */
	public void generate(){
				//if(Math.random() < 0.05){ //5% chance
					int randomColumn = (int)(Math.random()*numX);
					ParticleType type = ParticleType.A;
					if(particlesMap[randomColumn][numY-1] == 1) return;
					if(Math.random() < particleChance) type = ParticleType.C;
					particles.add(new Particle((randomColumn*particleSize), MMPDHandler.HEIGHT-particleSize, particleSize,type,sr));
					
					//System.out.println(stage.getActors().size);
					
				//
	}
	
	/**
	 * Узнать состояние ячейки по ее координатам.
	 * 
	 */
	public static int getCell(int x,int y){
		if(x >= numX || y >= numX || x < 0 || y < 0) return 3; 
		return particlesMap[x][y];
	}
	
	/**
	 * Установить в ячейку по координатам новое состояние.
	 */
	public static void setCell(int x, int y, int i){
		if(x >= numX || y >= numX || x < 0 || y < 0) return;
		System.out.println("x" + x + "y" + y);
		particlesMap[x][y] = i;
	}
	

	public static int getNumX(){
		return numX;
	}
	
	public static int getNumY(){
		return numY;
	}
	
	/**
	 * Установить в ячейку <u>по экранным</u> координатам новое состояние.
	 */
	public static void setCellByOrigin(int x,int y,int i){
		particlesMap[(int)(x*numX/MMPDHandler.WIDTH)+1][(int)(y*numY/MMPDHandler.HEIGHT)+1] = i;
	}
	
	/**
	 * Уничтожение частиц.
	 */
	public void dispose(){
		sr.dispose();
		particles.clear();
		for (int[] arr : particlesMap) {
			for(int a : arr){
				a = 0;
			}
		}
		sr = new ShapeRenderer();
	}
	

}

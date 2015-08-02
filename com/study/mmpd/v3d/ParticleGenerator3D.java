package com.study.mmpd.v3d;

import java.util.ArrayList;

import sun.security.action.GetBooleanAction;

import com.badlogic.gdx.utils.Timer;
import com.study.mmpd.MMPD;
import com.study.mmpd.Particle;
import com.study.mmpd.ParticleType;


/**
 * Генератор трехмерных частиц.
 * @author DeSile
 * @version 1.0
 */
public class ParticleGenerator3D {

	/**
	 * Размер генерируемых частиц.
	 */
	public static int particleSize = 5;
	private ArrayList<Particle3D> particles;
	
	/**
	 * <p>Сетка ячеек.</p>
	 * <p>Принимает разные значения в зависимости от находящийся частицы в ячейке.</p>
	 * <ol start="0">
	 * <li>Если пусто.</li>
	 * <li>Если частица типа А.</li>
	 * <li>Если частица типа С.</li>
	 * </ol>
	 */
	private static int[][][] particlesMap;
	/**
	 * Величина сетки ячеек по X.
	 */
	private static int numX = 20;
	/**
	 * Величина сетки ячеек по Y.
	 */
	private static int numY = 20;
	/**
	 * Величина сетки ячеек по Z.
	 */
	private static int numZ = 20;
	
	
	int randomColumnX;
	int randomColumnZ;
	
	/**
	 * Шанс генерации частицы типа С.
	 */
	public float particleChance = 0.3f;
	
	public ParticleGenerator3D(ArrayList<Particle3D> particles){
		this.particles = particles;
		particlesMap = new int[numX][numY][numZ];
	}
	/**
	 * Генерация фундамента.
	 */
	public void generateFloor(){
		for(int i = 0; i < numX; i++)
			for(int j = 0; j < numZ; j++){
				particles.add(new Particle3D(i*particleSize,0 , j*particleSize, particleSize, ParticleType.A));
				particlesMap[i][0][j] = 1;
			}
	}
	
	/**
	 * Метод генерации одной частицы в случайной колонке.
	 */
	public void generate(){

				System.out.println("look");
				// TODO Auto-generated method stub
				randomColumnX = (int)(Math.random()*numX);
				randomColumnZ = (int)(Math.random()*numZ);
				ParticleType type = ParticleType.A;
				if(particlesMap[randomColumnX][numY-1][randomColumnZ] == 1) return;
				if(Math.random() < particleChance) type = ParticleType.C;
				particles.add(new Particle3D((randomColumnX*particleSize),numY*particleSize,(randomColumnZ*particleSize), particleSize,type));

	}
	
	public static int getNumX(){
		return numX;
	}
	
	public static int getNumY(){
		return numY;
	}
	
	public static int getNumZ(){
		return numZ;
	}
	
	/**
	 * Узнать состояние ячейки по ее координатам.
	 * 
	 */
	public static int getCell(int x, int y, int z){
		if(x < numX && y < numY && z < numZ && x >= 0 && y >= 0 && z >= 0) return particlesMap[x][y][z];
		else return -1;
	}
	
	/**
	 * Установить в ячейку по координатам новое состояние.
	 */
	public static int setCell(int x, int y, int z, int code){
		if(x < numX && y < numY && z < numZ) {particlesMap[x][y][z] = code; return code;}
		else return -1;
	}
	
	/**
	 * Уничтожение частиц.
	 */
	public void dispose(){
		for (Particle3D p3 : particles) {
			p3.dispose();
		}
		particles.clear();
		particlesMap = new int[numX][numY][numZ];
	}
}

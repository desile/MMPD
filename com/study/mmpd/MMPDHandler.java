package com.study.mmpd;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.physics.bullet.linearmath.int4;
import com.study.mmpd.v3d.MMPD3D;

/**
 * Менеджер экранов и проводник универсальных методов.
 * @author DeSile
 * @version 1.0
 */

public class MMPDHandler extends Game {

	/**
	 * 2D сцена.
	 */
	public Screen v2Dscreen;
	/**
	 * 3D сцена.
	 */
	public Screen v3Dscreen;
	/**
	 * Начальная сцена.
	 */
	public Screen startScreen;
	
	/**
	 * Текущий номер модели.<br>
	 * Может принимать значения 1 или 2.
	 */
	private static int model = 1;
	
	/**
	 * Ширина экрана.
	 */
	public static final int WIDTH = 800;
	/**
	 * Высота экрана.
	 */
	public static final int HEIGHT = 500;
	/**
	 * Строка заголовка программы.
	 */
	public static final String TITLE = "Mixed Model of Particle Deposition";

	/**
	 * Метод, инициализирующий экраны.<br>
	 * Устанавливает активным экраном начальную сцену.<br>
	 * Вызывается первым при запуске программы.
	 */
	@Override
	public void create() {
		v2Dscreen = new MMPD(this);
		v3Dscreen = new MMPD3D(this);
		startScreen = new StartScreen(this);
		setScreen(startScreen);
	}
	
	/**
	 * Проводит универсальный интерфейс к экранам.
	 * @return общие методы экранов
	 */
	public UniversalMM getInterfaceToScreen(){
		return (UniversalMM)getScreen();
	}
	
	/**
	 * Полностью сбрасывает состояние программы.
	 */
	public void fullReset(){
		((MMPD) v2Dscreen).reset();
		((MMPD3D) v3Dscreen).reset();
		setScreen(startScreen);
	}
	
	/**
	 * Указывает, что первая модель - действующая.
	 */
	public static void setModel1(){
		model = 1;
	}
	
	/**
	 * Указывает, что вторая модель - действующая.
	 */
	public static void setModel2(){
		model = 2;
	}
	
	/**
	 * 
	 * @return номер действующей модели.
	 */
	public static int getModel(){
		return model;
	}

}

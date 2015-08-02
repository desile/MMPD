package com.study.mmpd;

import java.util.ArrayList;
import java.util.Iterator;


import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.study.mmpd.v3d.Particle3D;
import com.study.processors.UIProcessor;

//TODO: Распределить нагрузку на несколько шейп-рендеров
//TODO: Разобраться с вылетами приложения при заполнении экрана
//TODO: Панель управления (UI)

//TODO: Вращение при слайдинге

/**
 * Класс, отвечающий за моделлирование 2D сцены.
 * 
 * @author DeSile
 * @version 1.0
 */

public class MMPD implements Screen, UniversalMM {
	MMPDHandler apphandler;
	
	/**
	 * Агрегатор GUI элементов.
	 */
	Stage stage;
	
	/**
	 * Менеджер GUI элементов.
	 */
	UIProcessor ui;
	
	/**
	 * Коллекция частиц, находящихся на сцене.
	 */
	public ArrayList<Particle> particles;
	
	/**
	 * Генератор частиц.
	 */
	public ParticleGenerator pg;
	
	/**
	 * Время, в которое последний раз генерировалась частица(частицы).
	 */
    public long lastTimeGen;
    /**
     * Время, в которое последний раз отрисовывался кадр.
     */
    public long lastTimeAct;
    

    public SpriteBatch batch;
	
	public MMPD(MMPDHandler apphandler) {
		this.apphandler = apphandler;
		stage = new Stage();
		particles = new ArrayList<Particle>();
		batch = new SpriteBatch();
		ui = new UIProcessor(stage,apphandler);
		pg = new ParticleGenerator(particles);
		pg.generateFloor();
		pg.generate();
		
        lastTimeAct = System.currentTimeMillis();
        lastTimeGen = System.currentTimeMillis();
	}

	/**
	 * <p>Отрисовка 2D-сцены.</p>
	 * <p>Очищает экран, генерирует и отрисовывает частицы с указанной частотой.</p>
	 *
	 */
	@Override
	public void render (float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		
		long currTime = System.currentTimeMillis();
    	
    	if(currTime - lastTimeGen > 100){
    		lastTimeGen = currTime;
    		//for(int i = 0; i < 500; i++)
    			pg.generate();
    	}
    	
    	if(System.currentTimeMillis() - lastTimeAct > 30){
    		lastTimeAct = currTime;
    		for(Particle p : particles){
    			p.act();
    		}
    	}
    	for(Particle p : particles){
    		if(p.getVisible())
    			p.draw(batch);
        }
    	
    	for(Iterator<Particle> pIter = particles.iterator(); pIter.hasNext(); ){
    		Particle currentParticle = pIter.next();
    		if(!currentParticle.getVisible()){
    			pIter.remove();
    		}
    	}
    	
    	ui.render();
	}

	/**
	 * Колбэк при изменении размеров окна/экрана.
	 */
	@Override
	public void resize(int width, int height) {
		ui.resize(width, height);
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void dispose() {
		//stage.dispose();
		batch.dispose();
		
	}

	/**
	 * Указание активного ввода.
	 */
	@Override
	public void show() {
		Gdx.input.setInputProcessor(stage);
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public int getNumberOfParticles() {
		return particles.size()-ParticleGenerator.getNumX();//вычитаем количество частиц в фундаменте
	}
	
	@Override
	public void reset() {
		dispose();
		pg.dispose();
		//ui.dispose();//??
		
		//stage = new Stage();
		//particles = new ArrayList<Particle>();
		batch = new SpriteBatch();
		//ui = new UIProcessor(stage,apphandler);
		//pg = new ParticleGenerator(particles);
		pg.generateFloor();
		//Gdx.input.setInputProcessor(stage);
	}

	@Override
	public float getPaticleChance() {
		return pg.particleChance;
	}

	@Override
	public void setParticleChance(float chance) {
		pg.particleChance = chance;
		
	}
}

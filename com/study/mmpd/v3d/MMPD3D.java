package com.study.mmpd.v3d;

import java.util.ArrayList;
import java.util.Iterator;


import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.study.mmpd.MMPDHandler;
import com.study.mmpd.Particle;
import com.study.mmpd.UniversalMM;
import com.study.processors.UIProcessor;

/**
 * Класс, отвечающий за моделлирование 3D сцены.
 * 
 * @author DeSile
 * @version 1.0
 */

public class MMPD3D implements Screen, UniversalMM {
	MMPDHandler apphandler;
	/**
	 * Агрегатор GUI элементов.
	 */
	public Stage stage;
	/**
	 * Менеджер GUI элементов.
	 */
	public UIProcessor ui;
	
	/**
	 * Камера 3D сцены, содержащая матрицу обзора.
	 */
	public PerspectiveCamera cam;
	

	public ModelBatch modelBatch;
	
	/**
	 * Агрегатор способов ввода.
	 */
	InputMultiplexer multiplexer;
	
    /**
     * Окружение сцены, в том числе - настройка освещения.
     */
    public Environment environment;
    
    /**
     * Контроллер камеры.
     */
    public CameraInputController camController;
    
    /**
	 * Время, в которое последний раз генерировалась частица(частицы).
	 */
    public long lastTimeGen;
    /**
     * Время, в которое последний раз отрисовывался кадр.
     */
    public long lastTimeAct;

    /**
     * Генератор трехмерных частиц.
     */
    public ParticleGenerator3D pg;
    
    /**
     * Коллекция частиц, находящихся на сцене.
     */
    public ArrayList<Particle3D> particles;
    
	
    public MMPD3D(MMPDHandler apphandler) {
    	this.apphandler = apphandler;
    	modelBatch = new ModelBatch();
    	particles = new ArrayList<Particle3D>();
    	stage = new Stage();
    	ui = new UIProcessor(stage,apphandler);
    	
    	pg = new ParticleGenerator3D(particles);
    	
        cam = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        cam.position.set(51.76834f, 86.144844f, 157.05676f);
        //cam.lookAt(0.0026094206f, 0.9430818f, -0.33254874f);
        cam.direction.set(0, -0.40317917f, -0.915123f);
        cam.near = 1f;
        cam.far = 300f;
        cam.update();
        
        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
        environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));
        
        camController = new CameraInputController(cam);
        camController.target = new Vector3(ParticleGenerator3D.getNumX()*ParticleGenerator3D.particleSize/2,0,ParticleGenerator3D.getNumZ()*ParticleGenerator3D.particleSize/2);
        camController.rotateLeftKey = Keys.LEFT;
        camController.rotateRightKey = Keys.RIGHT;
        
        multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(stage);
        multiplexer.addProcessor(camController);
        Gdx.input.setInputProcessor(multiplexer);
        
        lastTimeAct = System.currentTimeMillis();
        lastTimeGen = System.currentTimeMillis();
        pg.generateFloor();
    }

	/**
	 * <p>Отрисовка 3D-сцены.</p>
	 * <p>Очищает экран, генерирует и отрисовывает частицы с указанной частотой.</p>
	 *
	 */
    @Override
    public void render (float delta) {
    	
	    	camController.update();
	    	
	    	long currTime = System.currentTimeMillis();
	    	
	    	if(currTime - lastTimeGen > 50){
	    		lastTimeGen = currTime;
	    		pg.generate();
	    	}
	    	
	    	if(System.currentTimeMillis() - lastTimeAct > 30){
	    		lastTimeAct = currTime;
	    		for(Particle3D p : particles){
	    			p.act();
	    		}
	    	}
	    	
	        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
	
	        modelBatch.begin(cam);
	        for(Particle3D p : particles){
	        	if(p.getVisible())
	        		p.draw(modelBatch, environment, cam);
	        }
	        modelBatch.end();
	        
	    	for(Iterator<Particle3D> pIter = particles.iterator(); pIter.hasNext(); ){
	    		Particle3D currentParticle = pIter.next();
	    		if(!currentParticle.getVisible()){
	    			pIter.remove();
	    		}
	    	}
	        
	        ui.render();
	        //System.out.println("Camera: p = " + cam.position + "; d = " + cam.direction);
    }

    @Override
    public void dispose () {
    	
        modelBatch.dispose();
        pg.dispose();
        
    }

    @Override
    public void resume () {
    }

    /**
     * Колбэк при изменении размеров окна/экрана.
     */
    @Override
    public void resize (int width, int height) {
    	ui.resize(width, height);
    }

    @Override
    public void pause () {
    }

	/**
	 * Указание активного ввода.
	 */
	@Override
	public void show() {
		Gdx.input.setInputProcessor(multiplexer);
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getNumberOfParticles() {
		return particles.size()-ParticleGenerator3D.getNumX()*ParticleGenerator3D.getNumZ();//вычитаем количество частиц в фундаменте
	}

	@Override
	public void reset() {
		dispose();
		modelBatch = new ModelBatch();
		Gdx.input.setInputProcessor(multiplexer);
		pg.generateFloor();
		
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
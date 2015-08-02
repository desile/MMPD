package com.study.processors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.study.mmpd.MMPDHandler;
import com.study.ui.ChanceLabel;
import com.study.ui.ChanceSlider;
import com.study.ui.NumOfParticlesLabel;
import com.study.ui.ResetButton;
import com.study.ui.SwitchDimensionsButton;
import com.badlogic.gdx.scenes.scene2d.ui.Slider.SliderStyle;

/**
 * Агрегатор элементов пользовательского интерфейса.
 * @author DeSile
 * @version 1.0
 */
public class UIProcessor {

	Stage stage;
	ShapeRenderer sr;
	Skin skin;
	//SpriteBatch batch;
	public Table rtable;
	public Table ltable;
	
	/**
	 * Режим отладки для GUI.
	 */
	private boolean uidbg = false;
	
	public MMPDHandler handler;
	
	/**
	 * Объявление элементов пользовательского интерфейса.
	 * @param stage хранитель элементов
	 * @param handler доступ к универсальному интерфейсу и переключение экранов
	 */
	public UIProcessor(Stage stage,MMPDHandler handler){
		this.stage = stage;
		this.handler = handler;
		//this.sr = sr;
		skin = new Skin();
		//batch = new SpriteBatch();
		
		// Create a table that fills the screen. Everything else will go inside this table.
		rtable = new Table();
		rtable.right().top();
		rtable.pad(10);
		rtable.setFillParent(true);
		rtable.setDebug(uidbg);
				
		stage.addActor(rtable);

				
		//table.add(new Image(skin.newDrawable("white", Color.RED))).size(64);
		Pixmap pixmap = new Pixmap(1,1,Format.RGBA8888);
		pixmap.setColor(Color.WHITE);
		pixmap.fill();
		skin.add("white", new Texture(pixmap));
		skin.add("default-vertical", new Texture(pixmap));
		skin.add("default", new BitmapFont());
		
		//button style
		TextButtonStyle textButtonStyle = new TextButtonStyle();
		textButtonStyle.up = skin.newDrawable("white",0.5f,0.5f,0.5f,0.5f);
		//textButtonStyle.down = skin.newDrawable("white", Color.DARK_GRAY);
		//textButtonStyle.checked = skin.newDrawable("white", Color.BLUE);
		textButtonStyle.over = skin.newDrawable("white", Color.LIGHT_GRAY);
		textButtonStyle.font = skin.getFont("default");
		skin.add("default", textButtonStyle);
				
		new SwitchDimensionsButton(this,skin);
		rtable.row();
		new ResetButton(this, skin);
		
		ltable = new Table();
		ltable.left().top();
		ltable.pad(10);
		ltable.setWidth(300);
		ltable.setFillParent(true);
		ltable.setDebug(uidbg);
				
		stage.addActor(ltable);		

		new NumOfParticlesLabel(this);
		ltable.row();
		new ChanceLabel(this);
		ltable.row();
		new ChanceSlider(this,skin);
		//ltable.add(new Slider(50, 1000, 50, true, skin));
	}
	
	public void render(){
		stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
		stage.draw();
	}
	
	public void resize (int width, int height) {
		stage.getViewport().update(width, height, true);
	}

	public void dispose () {
		skin.dispose();
	}
	
}

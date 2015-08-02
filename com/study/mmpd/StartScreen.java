package com.study.mmpd;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeType.Size;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * Начальный экран. Отображается первым после запуска программы и дает возможность выбрать модель алгоритма.
 * @author DeSile
 * @version 1.0
 */

public class StartScreen implements Screen {

	MMPDHandler apphandler;
	Stage stage;
	public SpriteBatch batch;
	Skin skin;
	Table table;
	
	/**
	 * Собирает GUI экрана.
	 */
	public StartScreen(MMPDHandler handler){
		this.apphandler = handler;
		stage = new Stage();
		batch = new SpriteBatch();
		skin = new Skin();
		table = new Table();
		table.setFillParent(true);
		stage.addActor(table);
		
		Pixmap pixmap = new Pixmap(1,1,Format.RGBA8888);
		pixmap.setColor(Color.WHITE);
		pixmap.fill();
		skin.add("white", new Texture(pixmap));
		skin.add("default-vertical", new Texture(pixmap));
		skin.add("default", new BitmapFont());
		
		TextButtonStyle textButtonStyle = new TextButtonStyle();
		textButtonStyle.up = skin.newDrawable("white",0.5f,0.5f,0.5f,0.5f);
		//textButtonStyle.down = skin.newDrawable("white", Color.DARK_GRAY);
		//textButtonStyle.checked = skin.newDrawable("white", Color.BLUE);
		textButtonStyle.over = skin.newDrawable("white", Color.LIGHT_GRAY);
		textButtonStyle.font = skin.getFont("default");
		skin.add("default", textButtonStyle);
		
		TextButton startbutton1 = new TextButton("Model I", skin);
		startbutton1.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				apphandler.setScreen(apphandler.v2Dscreen);
				apphandler.setModel1();
			}
		});
		
		TextButton startbutton2 = new TextButton("Model II", skin);
		startbutton2.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				apphandler.setScreen(apphandler.v2Dscreen);
				apphandler.setModel2();
			}
		});
		
		table.add(startbutton1).size(160,80).spaceBottom(10);
		table.row();
		table.add(startbutton2).size(160,80);
	}
	
	@Override
	public void show() {
		Gdx.input.setInputProcessor(stage);
		
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.draw();
		
	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, true);
		
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
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}



}

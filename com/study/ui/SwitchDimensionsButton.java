package com.study.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.study.processors.UIProcessor;

/**
 * Кнопка переключения между 2D и 3D сценами.
 * @author DeSile
 * @version 1.0
 */

public class SwitchDimensionsButton{

	public SwitchDimensionsButton(final UIProcessor ui,Skin skin){
		
		TextButton button = new TextButton("3D/2D", skin);
		ui.rtable.add(button).size(80, 40).spaceBottom(10);
		
		button.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				System.out.println(ui.handler.getScreen().getClass());
				if(ui.handler.getScreen().equals(ui.handler.v2Dscreen)){
					ui.handler.setScreen(ui.handler.v3Dscreen);
				}
				else {
					ui.handler.setScreen(ui.handler.v2Dscreen);
				}
			}
		});
	}

}

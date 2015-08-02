package com.study.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.study.processors.UIProcessor;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;

/**
 * Текстовая область, отображающая текущее количество частиц на экране.
 * @author DeSile
 *
 */

public class NumOfParticlesLabel {

	public NumOfParticlesLabel(final UIProcessor ui){
		//Skin skin = new Skin(Gdx.files.internal("skins/SimpleSkin.json"));
		Label label = new Label("Number of particles: ", new LabelStyle(new BitmapFont(false),Color.WHITE)){
			@Override
			public void draw(Batch batch, float parentAlpha) {
				// TODO Auto-generated method stub
				super.draw(batch, parentAlpha);
				setText("Number of particles: " + ui.handler.getInterfaceToScreen().getNumberOfParticles());
			}
		};
		ui.ltable.add(label).left().spaceBottom(10);
		
		
	}

}

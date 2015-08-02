package com.study.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.study.processors.UIProcessor;

/**
 * Текстовая область, отображающая шанс генерации частицы типа С.
 * @author DeSile
 * @version 1.0
 */

public class ChanceLabel {

	public ChanceLabel(final UIProcessor ui){
		Label label = new Label("Particle-C chance: ", new LabelStyle(new BitmapFont(false),Color.WHITE)){
			@Override
			public void draw(Batch batch, float parentAlpha) {
				// TODO Auto-generated method stub
				super.draw(batch, parentAlpha);
				setText("Particle-C chance: " + (int)(ui.handler.getInterfaceToScreen().getPaticleChance()*100) + "%" );
			}
		};
		ui.ltable.add(label).left();
	}

}

package com.study.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Slider.SliderStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.study.processors.UIProcessor;

/**
 * Слайдер для регулирования шанса генерации частиц типа С.<br>
 * От 0 до 100%.
 * @author DeSile
 * @version 1.0
 */

public class ChanceSlider {
	
	public ChanceSlider(final UIProcessor ui,Skin skin){
		final Slider slider = new Slider(0, 1, 0.1f, false, new SliderStyle(skin.newDrawable("white", Color.RED), new TextureRegionDrawable(new TextureRegion(new Texture("8px.png")))));
		slider.setSize(100, 20);
		slider.setValue(0.3f);
		slider.addListener(new ChangeListener(){

			@Override
			public void changed(ChangeEvent event, Actor actor) {
				
				ui.handler.getInterfaceToScreen().setParticleChance(slider.getValue());
				
			}
			
		});
		ui.ltable.add(slider).size(150,20).left();
	}
}

package com.study.ui;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.study.mmpd.MMPD;
import com.study.mmpd.v3d.MMPD3D;
import com.study.processors.UIProcessor;

/**
 * Кнопка вызывающая метод полной перезагрузки {@link com.study.mmpd.MMPDHandler#fullReset()}.
 * @author DeSile
 * @version 1.0
 */

public class ResetButton {
	
	public ResetButton(final UIProcessor ui,Skin skin){
		TextButton button = new TextButton("Reset", skin);
		ui.rtable.add(button).size(80, 40);
		
		button.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				System.out.println(ui.handler.getScreen().getClass());
				//ui.handler.getInterfaceToScreen().reset();
				ui.handler.fullReset();
			}
		});
	}

}

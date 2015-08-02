package com.study.mmpd;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.physics.bullet.linearmath.int4;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.sun.javafx.font.Disposer;

/**
 * Класс двумерной частицы.
 * @author DeSile
 * @version 1.0
 */

public class Particle extends Actor {

	/**
	 * Размер частицы в пикселях.
	 */
	private int size;
	/**
	 * Тип частицы.
	 */
	private ParticleType type;
	
	/**
	 * Текстура частицы.
	 */
	private Texture texture;
	
	/**
	 * true - показывать текстуру <br>
	 * false - не показывать текстуру
	 */
	private boolean showTexture = false;
	
	private ShapeRenderer sr;
	
	/**
	 * Текущее состояние частицы.
	 */
	private ParticleState state = ParticleState.Falling;
	
	/**
	 * Колонка, по которой движется частица.
	 */
	private int column;
	
	/**
	 * Скорость частицы в пикселях за кадр.
	 */
	private float speed;
	
	/**
	 * Переменная для анимации цвета частицы (в текущей версии не используется).
	 */
	private float colorVar = 0;

	/**
	 * true - частица не материальна и не отображается.<br>
	 * false - частица материальна и отображается
	 */
	private boolean dissapearing = false;
	
	/**
	 * Если действует вторая модель, то:<br>
	 * true - частица С соскальзывает только с А частиц<br>
	 * false - частица С соскальзывает с любых частиц
	 * Если действует первая модель, то значение переменной не играет роли.
	 */
	private boolean onlyAslideOption = true;
	
	/**
	 * 
	 * @param x экранная координата по горизонтали
	 * @param y экранная координата по вертикали
	 * @param size размер частицы в пикселях
	 * @param type тип частицы
	 * @param sr отрисовщик граней частицы
	 */
	public Particle(int x,int y, int size, ParticleType type,ShapeRenderer sr){
		
		//super();
		setX(x);
		setY(y);
		
		this.column = (int)(getX()/size); 
		this.size = size;
		this.type = type;
		
		this.speed = 12;
		
		this.sr = sr;
		
		if(type == ParticleType.A) setColor(Color.WHITE);
		if(type == ParticleType.C) setColor(Color.GRAY); 
		
		
		
		/*if(type == ParticleType.A){
			texture = new Texture("square.png");
		}
		if(type == ParticleType.C){
			texture = new Texture("facebook.png");
		}*/
	}
	
	/**
	 * Если {@link #showTexture} равна true, то используется batch для отрисовки.<br>
	 * Иначе - {@link #sr}.
	 * @param batch отрисовщик текстур.
	 */
	public void draw(Batch batch){
		if(showTexture) batch.draw(texture,getX(), getY(),size,size);
		else{
			//batch.end();
			sr.begin(ShapeType.Filled);
			sr.setColor(getColor());
			sr.rect(getX(), getY(), size,size);
			sr.end();
			sr.begin(ShapeType.Line);
			sr.setColor(Color.DARK_GRAY);
			sr.rect(getX(),getY(),size,size);
			sr.end();
			//batch.begin();
		}
	}
	
	/**
	 * Отрисовка границ (работает только в дебаг режиме).
	 */
	public void drawDebug(ShapeRenderer shapes){
		shapes.setColor(Color.WHITE);
		shapes.rect(getX(), getY(), size,size);
	}
	
	/**
	 * Логика поведения частицы.
	 */
	public void act(){
		int tmpY = 0;
		
		if(getY() <= 0) {
			state = ParticleState.Resting;
		}
		if(state == ParticleState.Falling){
			for(int j = ParticleGenerator.getNumY()-1; j >= 0; j--){
				if(ParticleGenerator.getCell(column, j) > 0){
					if(getY()-speed < (j+1)*size){
						if(type == ParticleType.C && onlyAslideOption(column, j) && MMPDHandler.getModel()==2){
							if(ParticleGenerator.getCell(column-1, j) == 0){
								state = ParticleState.SlidingLeft;
								break;
							}
							if(ParticleGenerator.getCell(column+1, j) == 0){
								state = ParticleState.SlidingRight;
								break;
							}
							 //??
						}
						setY((j+1)*size);
						ParticleGenerator.setCell(column, j+1, type.getCode());
						state = ParticleState.Resting;
						tmpY = j;
					}else{
						state = ParticleState.Falling;
					}
					break;
				}	
			}
		}
		if(state == ParticleState.Falling){
			moveBy(0, -speed);
		}
		if(state == ParticleState.SlidingLeft){
			if(getX() > (column-1)*size){
				moveBy(-speed, -speed);
			}
			else {
				column = column-1;
				setX(column*size);
				state = ParticleState.Falling;
			}
		}
		if(state == ParticleState.SlidingRight){
			if(getX() < (column+1)*size){
				moveBy(speed, -speed);
			}
			else {
				column = column+1;
				setX(column*size);
				state = ParticleState.Falling;
			}
		}
		if((state == ParticleState.Resting && type == ParticleType.A) || dissapearing){
			if(dissapearing){
				
			}
			else if(ParticleGenerator.getCell(column, tmpY) == 2){
				dissapearing = true;
				ParticleGenerator.setCell(column, tmpY+1, 0);
				
			}
		}
		//color fun
		//colorVar += 0.01;
		//if(colorVar > Math.PI*20) colorVar = 0;
		//setColor((float)Math.sin(colorVar), 1f, (float)Math.cos(colorVar), 1f);
	}
	
	/**
	 * @return видимость частицы.
	 */
	public boolean getVisible(){
		return !dissapearing;
	}
	
	/**
	 * Определяет возможность соскользнуть с переданной ячейки.
	 * @param column ячейка по горизонтали
	 * @param j ячейка по вертикали
	 * @return возможность соскальзывания
	 */
	private boolean onlyAslideOption(int column, int j){
		if(onlyAslideOption){
			return ParticleGenerator.getCell(column, j)==1;
		}
		else {
			return true;
		}
	}


}

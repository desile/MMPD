package com.study.mmpd.v3d;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;
import com.study.mmpd.MMPDHandler;
import com.study.mmpd.ParticleGenerator;
import com.study.mmpd.ParticleState;
import com.study.mmpd.ParticleType;

/**
 * Класс трехмерной частицы.
 * @author DeSile
 * @version 1.0
 */

public class Particle3D{

	/**
	 * Размер частицы в пикселях.
	 */
	private int size;
	/**
	 * Тип частицы.
	 */
	private ParticleType type;
	
	/**
	 * Трехмерная модель частицы.
	 */
	private Model model;
	/**
	 * Информация о трансформации, повороте модели частицы.
	 */
	private ModelInstance instance;
	
	/**
	 * true - показывать текстуру <br>
	 * false - не показывать текстуру
	 */
	private boolean showTexture = false;
	/**
	 * Текущее состояние частицы.
	 */
	private ParticleState state = ParticleState.Falling;
	
	/**
	 * Колонка по X, по которой движется частица.
	 */
	private int columnX;
	/**
	 * Колонка по Z, по которой движется частица.
	 */
	private int columnZ;
	
	/**
	 * Скорость частицы в пикселях за кадр.
	 */
	private float speed;
	
	private float x;
	private float y;
	private float z;
	
	/**
	 * Цвет частицы.
	 */
	private Color color;
	
	/**
	 * true - частица не материальна и не отображается.<br>
	 * false - частица материальна и отображается
	 */
	private boolean dissapearing = false;
	
	/**
	 * 
	 * @param x координата в пространстве
	 * @param y координата в пространстве
	 * @param z координата в пространстве
	 * @param size размер частицы в пикселях
	 * @param type тип частицы
	 */
	public Particle3D(int x,int y,int z, int size, ParticleType type){
		
		//super();
		this.x = x;
		this.y = y;
		this.z = z;
		
		this.columnX = (int)(x/size);
		this.columnZ = (int)(z/size);
		this.size = size;
		this.type = type;
		
		this.speed = 2;
		
		//sr = ParticleGenerator.sr;
		
		if(type == ParticleType.A) color = Color.WHITE;
		if(type == ParticleType.C) color = Color.GRAY; 
		
		model = new ModelBuilder().createBox(size, size, size, new Material(ColorAttribute.createDiffuse(color)), Usage.Position | Usage.Normal);
		
		instance = new ModelInstance(model, new Vector3(x, y, z));
		
		/*if(type == ParticleType.A){
			texture = new Texture("square.png");
		}
		if(type == ParticleType.C){
			texture = new Texture("facebook.png");
		}*/
	}
	
	/**
	 * Отрисовка частицы.
	 * @param modelBatch  отрисовщик модели
	 * @param environment окружение и свет
	 * @param cam камера обзора
	 */
	public void draw(ModelBatch modelBatch, Environment environment,PerspectiveCamera cam){
		if(showTexture);//???;
		else{
			//modelBatch.begin(cam);
			modelBatch.render(instance,environment);
			//modelBatch.end();
		}
	}

	/**
	 * Логика поведения частицы.
	 */
	public void act(){
		int tmpY = 0;
		if(y <= 0) {
			state = ParticleState.Resting;
		}
		if(state == ParticleState.Falling){
			for(int j = ParticleGenerator3D.getNumY()-1; j >= 0; j--){
				if(ParticleGenerator3D.getCell(columnX,j, columnZ) > 0){
					if(y-speed < (j+1)*size){
						if(type == ParticleType.C && ParticleGenerator3D.getCell(columnX, j,columnZ)==1 && MMPDHandler.getModel()==2){
							if(ParticleGenerator3D.getCell(columnX-1, j,columnZ) == 0){
								state = ParticleState.SlidingLeft;
								break;
							}
							if(ParticleGenerator3D.getCell(columnX+1, j,columnZ) == 0){
								state = ParticleState.SlidingRight;
								break;
							}
							 //??
						}
						y = ((j+1)*size);
						ParticleGenerator3D.setCell(columnX, j+1,columnZ, type.getCode());
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
			y = y - speed;
		}
		if(state == ParticleState.SlidingLeft){
			if(x > (columnX-1)*size){
				y = y - speed;
				x = x - speed;
			}
			else {
				columnX = columnX-1;
				x = columnX*size;
				state = ParticleState.Falling;
			}
		}
		if(state == ParticleState.SlidingRight){
			if(x < (columnX+1)*size){
				y = y - speed;
				x = x + speed;
			}
			else {
				columnX = columnX+1;
				x = columnX*size;
				state = ParticleState.Falling;
			}
		}
		if((state == ParticleState.Resting && type == ParticleType.A) || dissapearing){
			if(dissapearing){
				
			}
			else if(ParticleGenerator3D.getCell(columnX, tmpY,columnZ) == 2){
				dissapearing = true;
				ParticleGenerator3D.setCell(columnX, tmpY+1,columnZ, 0);
				
			}
		}
		
		instance.transform.setToTranslation(x, y,z);
		//color fun
		//colorVar += 0.01;
		//if(colorVar > Math.PI*20) colorVar = 0;
		//setColor((float)Math.sin(colorVar), 1f, (float)Math.cos(colorVar), 1f);
	}
	
	/**
	 * Уничтожить модель частицы.
	 */
	public void dispose(){
		model.dispose();
	}
	/**
	 * @return видимость частицы.
	 */
	public boolean getVisible(){
		return !dissapearing;
	}

}

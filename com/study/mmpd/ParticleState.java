package com.study.mmpd;

/**
 * Возможные состояния частицы:
 * <ul>
 * <li>Falling - состояние падения.</li>
 * <li>SlidingLeft/Right/Forward/Back - перемещение(соскальзывание) в одну из сторон. </li>
 * <li>Resting - состояние покоя.</li>
 * </ul>
 * @author DeSile
 * @version 1.0
 */
public enum ParticleState {
	Falling,SlidingLeft,SlidingRight,SlidingForward,SlidingBack,Resting
}

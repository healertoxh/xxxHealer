package cn.tedu.shoot;
import java.awt.image.BufferedImage;

import javax.swing.plaf.synth.SynthSeparatorUI;
/** ���: �Ƿ����� */
public class Sky extends FlyingObject {
	private int speed; //����(�����ƶ��ٶ�)
	private int y1;    //��2��ͼƬ��y����
	/** ���췽�� */
	public Sky(){
		super(World.WIDTH,World.HEIGHT,0,0);
		speed = 1;
		y1 = -World.HEIGHT;
	}
	
	/** ��дstep()�ƶ� */
	public void step() {
		y+=speed;  //y+(����)
		y1+=speed; //y1+(����)
		if(y>=World.HEIGHT){ //��y>=���ڵĸߣ���ʾ��ȥ��
			y=-World.HEIGHT; //���޸�y��ֵΪ���Ĵ��ڵĸ�(�ƶ�������ȥ)
		}
		if(y1>=World.HEIGHT){ //��y>=���ڵĸߣ���ʾ��ȥ��
			y1=-World.HEIGHT; //���޸�y��ֵΪ���Ĵ��ڵĸ�(�ƶ�������ȥ)
		}
	}
	
	/** ��дgetImage()��ȡ����ͼƬ */
	public BufferedImage getImage() {
		return Images.sky; //ֱ�ӷ���skyͼƬ����
	}
	
	/** ��ȡy1���� */
	public int getY1() {
		return y1; //����y1
	}
	
}

package cn.tedu.shoot;
import java.awt.image.BufferedImage;
/** �ӵ�: �Ƿ����� */
public class Bullet extends FlyingObject {
	private int speed; //����(�����ƶ��ٶ�)
	/** ���췽�� */
	public Bullet(int x,int y){
		super(8,20,x,y);
		speed = 3;
	}
	
	/** ��дstep()�ƶ� */
	public void step() {
		y-=speed; //y-(����)
	}
	
	/** ��дgetImage()��ȡ����ͼƬ */
	public BufferedImage getImage() {
		if(isLife()) { //�����ŵ�
			return Images.bullet; //ֱ�ӷ���bulletͼƬ����
		}else if(isDead()) { //�����˵�
			state = REMOVE;  //����ǰ״̬�޸�ΪREMOVEɾ����
		}
		return null; //���˵ĺ�ɾ���ģ���������ͼƬ
		/*
		 * 1)�����ŵģ�ֱ�ӷ���bulletͼƬ����
		 * 2)�����˵ģ���״̬�޸�ΪREMOVEɾ����,ͬʱ������ͼƬ
		 * 3)��ɾ���ģ�������ͼƬ
		 */	
	}
	
	/** ��дisOutOfBounds()�ж��ӵ��Ƿ�Խ�� */
	public boolean isOutOfBounds(){
		return this.y<=-this.height; //�ӵ���y<=�����ӵ��ĸߣ���ΪԽ����
	}
}
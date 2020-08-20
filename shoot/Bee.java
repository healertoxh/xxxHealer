package cn.tedu.shoot;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.xml.crypto.dsig.XMLSignatureException;
/** С�۷�: �Ƿ����Ҳ�ǽ��� */
public class Bee extends FlyingObject implements EnemyAward {
	private int xSpeed; //����(����x�����ƶ��ٶ�)
	private int ySpeed; //����(����y�����ƶ��ٶ�)
	private int awardType; //��������
	/** ���췽�� */
	public Bee(){
		super(60,51);
		xSpeed = 1;
		ySpeed = 2;
		Random rand = new Random(); //���������
		awardType = rand.nextInt(2); //0��1֮��������
	}
	
	/** ��дstep()�ƶ� */
	public void step() {
		x+=xSpeed; //x+(���������)
		y+=ySpeed; //y+(����)
		if(x<=0 || x>=World.WIDTH-width){ //��x<=0������x>=(���ڿ�-�۷��)����ʾ����ͷ��
			xSpeed*=-1; //���л�����(���为��������)
		}
	}
	
	private int index = 1; //�±�(����ͼ�±��1��ʼ)
	/** ��дgetImage()��ȡ����ͼƬ */
	public BufferedImage getImage() { //ÿ10������һ��
		if(isLife()) { //�����ŵ�
			return Images.bees[0]; //����bees�еĵ�1��ͼƬ
		}else if(isDead()) { //�����˵�
			BufferedImage img = Images.bees[index++]; //��ȡ����ͼ
			if(index==Images.bees.length) { //�����һ��ͼʱ
				state = REMOVE;             //�򽫵�ǰ״̬�޸�ΪREMOVEɾ��״̬
			}
			return img; //���ر���ͼ
		}
		return null; //REMOVE״̬ʱ��������ͼƬ
	}
	
	/** ��ȡ�������� */
	public int getAwardType(){
		return awardType; //���ؽ�������
	}
	
}


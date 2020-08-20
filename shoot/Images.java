package cn.tedu.shoot;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
/** ͼƬ������ */
public class Images {
//  ������    ��̬��     ͼƬ��������   ����
	public static BufferedImage sky;     //���ͼƬ
	public static BufferedImage bullet;  //�ӵ�ͼƬ
	public static BufferedImage[] heros; //Ӣ�ۻ�ͼƬ����
	public static BufferedImage[] airs;  //С�л�ͼƬ����
	public static BufferedImage[] bairs; //��л�ͼƬ����
	public static BufferedImage[] bees;  //С�۷�ͼƬ����
	
	public static BufferedImage start;    //����ͼ
	public static BufferedImage pause;    //��ͣͼ
	public static BufferedImage gameover; //��Ϸ����ͼ
	
	static { //��ʼ��ͼƬ
		start = readImage("start.png");
		pause = readImage("pause.png");
		gameover = readImage("gameover.png");
		
		sky = readImage("background.png");
		bullet = readImage("bullet.png");
		
		heros = new BufferedImage[2]; //2��ͼƬ
		heros[0] = readImage("hero0.png");
		heros[1] = readImage("hero1.png");
		
		airs = new BufferedImage[5];  //5��ͼƬ
		bairs = new BufferedImage[5];
		bees = new BufferedImage[5];
		airs[0] = readImage("airplane.png");
		bairs[0] = readImage("bigairplane.png");
		bees[0] = readImage("bee.png");
		for(int i=1;i<airs.length;i++) {
			airs[i] = readImage("bom"+i+".png");
			bairs[i] = readImage("bom"+i+".png");
			bees[i] = readImage("bom"+i+".png");
		}
	}
	
	/** ��ȡͼƬ fileName:ͼƬ���� */
	public static BufferedImage readImage(String fileName){
		try{
			BufferedImage img = ImageIO.read(FlyingObject.class.getResource(fileName)); //��ȡ��FlyingObject��ͬһ���е�ͼƬ
			return img;
		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
	
}





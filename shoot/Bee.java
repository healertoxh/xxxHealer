package cn.tedu.shoot;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.xml.crypto.dsig.XMLSignatureException;
/** 小蜜蜂: 是飞行物，也是奖励 */
public class Bee extends FlyingObject implements EnemyAward {
	private int xSpeed; //步长(控制x坐标移动速度)
	private int ySpeed; //步长(控制y坐标移动速度)
	private int awardType; //奖励类型
	/** 构造方法 */
	public Bee(){
		super(60,51);
		xSpeed = 1;
		ySpeed = 2;
		Random rand = new Random(); //随机数对象
		awardType = rand.nextInt(2); //0到1之间的随机数
	}
	
	/** 重写step()移动 */
	public void step() {
		x+=xSpeed; //x+(向左或向右)
		y+=ySpeed; //y+(向下)
		if(x<=0 || x>=World.WIDTH-width){ //若x<=0，或者x>=(窗口宽-蜜蜂宽)，表示到两头了
			xSpeed*=-1; //则切换方向(正变负、负变正)
		}
	}
	
	private int index = 1; //下标(爆破图下标从1开始)
	/** 重写getImage()获取对象图片 */
	public BufferedImage getImage() { //每10毫秒走一次
		if(isLife()) { //若活着的
			return Images.bees[0]; //返回bees中的第1张图片
		}else if(isDead()) { //若死了的
			BufferedImage img = Images.bees[index++]; //获取爆破图
			if(index==Images.bees.length) { //到最后一张图时
				state = REMOVE;             //则将当前状态修改为REMOVE删除状态
			}
			return img; //返回爆破图
		}
		return null; //REMOVE状态时，不返回图片
	}
	
	/** 获取奖励类型 */
	public int getAwardType(){
		return awardType; //返回奖励类型
	}
	
}


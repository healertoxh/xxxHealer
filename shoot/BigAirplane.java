package cn.tedu.shoot;
import java.awt.image.BufferedImage;
/** 大敌机: 是飞行物，也能得分 */
public class BigAirplane extends FlyingObject implements EnemyScore {
	private int speed; //步长(控制移动速度)
	/** 构造方法 */
	public BigAirplane(){
		super(66,89);
		speed = 2;
	}
	
	/** 重写step()移动 */
	public void step() {
		y+=speed; //y+(向下)
	}
	
	private int index = 1; //下标(爆破图下标从1开始)
	/** 重写getImage()获取对象图片 */
	public BufferedImage getImage() { //每10毫秒走一次
		if(isLife()) { //若活着的
			return Images.bairs[0]; //返回bairs中的第1张图片
		}else if(isDead()) { //若死了的
			BufferedImage img = Images.bairs[index++]; //获取爆破图
			if(index==Images.bairs.length) { //到最后一张图时
				state = REMOVE;             //则将当前状态修改为REMOVE删除状态
			}
			return img; //返回爆破图
		}
		return null; //REMOVE状态时，不返回图片
	}
	  
	/** 重写getScore()得分 */
	public int getScore(){
		return 3; //打掉大敌机，玩家得3分
	}
	
}
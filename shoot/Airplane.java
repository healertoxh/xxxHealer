package cn.tedu.shoot;
import java.awt.image.BufferedImage;
/** 小敌机: 是飞行物，也能得分 */
public class Airplane extends FlyingObject implements EnemyScore {
	private int speed; //步长(控制移动速度)
	/** 构造方法 */ 
	public Airplane(){
		super(48,50);
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
			return Images.airs[0]; //返回airs中的第1张图片
		}else if(isDead()) { //若死了的
			BufferedImage img = Images.airs[index++]; //获取爆破图
			if(index==Images.airs.length) { //到最后一张图时
				state = REMOVE;             //则将当前状态修改为REMOVE删除状态
			}
			return img; //返回爆破图
		}
		return null; //REMOVE状态时，不返回图片
		/*
		 *                 index=1
		 * 10M img=airs[1] index=2                  返回airs[1]
		 * 20M img=airs[2] index=3                  返回airs[2]
		 * 30M img=airs[3] index=4                  返回airs[3]
		 * 40M img=airs[4] index=5(REMOVE) 返回airs[4]
		 * 50M 返回null
		 */
	}
	
	/** 重写getScore()得分 */
	public int getScore(){
		return 1; //打掉小敌机，玩家得1分
	}
	
}

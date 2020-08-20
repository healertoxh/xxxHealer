package cn.tedu.shoot;
import java.awt.image.BufferedImage;
/** 子弹: 是飞行物 */
public class Bullet extends FlyingObject {
	private int speed; //步长(控制移动速度)
	/** 构造方法 */
	public Bullet(int x,int y){
		super(8,20,x,y);
		speed = 3;
	}
	
	/** 重写step()移动 */
	public void step() {
		y-=speed; //y-(向上)
	}
	
	/** 重写getImage()获取对象图片 */
	public BufferedImage getImage() {
		if(isLife()) { //若活着的
			return Images.bullet; //直接返回bullet图片即可
		}else if(isDead()) { //若死了的
			state = REMOVE;  //将当前状态修改为REMOVE删除的
		}
		return null; //死了的和删除的，都不返回图片
		/*
		 * 1)若活着的，直接返回bullet图片即可
		 * 2)若死了的，将状态修改为REMOVE删除的,同时不返回图片
		 * 3)若删除的，不返回图片
		 */	
	}
	
	/** 重写isOutOfBounds()判断子弹是否越界 */
	public boolean isOutOfBounds(){
		return this.y<=-this.height; //子弹的y<=负的子弹的高，即为越界了
	}
}
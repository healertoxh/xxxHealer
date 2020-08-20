package cn.tedu.shoot;
import java.util.Random;
import java.awt.image.BufferedImage;
/** 飞行物 */
public abstract class FlyingObject extends Object {
	public static final int LIFE = 0;   //活着的
	public static final int DEAD = 1;   //死了的
	public static final int REMOVE = 2; //删除的
	protected int state = LIFE; //当前状态(默认为活着的)
	
	protected int width;  //宽
	protected int height; //高
	protected int x;      //x坐标
	protected int y;      //y坐标
	
	/** 专门为小敌机、大敌机、小蜜蜂提供的 */
	//传2个参数: 敌人只有宽和高不同，所以得把宽高写活
	public FlyingObject(int width,int height){ 
		this.width = width;
		this.height = height;
		Random rand = new Random(); //随机数对象
		x = rand.nextInt(World.WIDTH-width); //x:窗口的宽-敌人宽之间的随机数
		y = -height; //y:负的敌人的高
	}
	/** 专门为英雄机、天空、子弹提供的 */
	//传4个参数:英雄机/天空/子弹的宽高xy都不一样，都得写活
	public FlyingObject(int width,int height,int x,int y){
		this.width = width;
		this.height = height;
		this.x = x;
		this.y = y;
	}
	
	/** 飞行物移动 */
	public abstract void step();
	
	/** 获取对象图片 */
	public abstract BufferedImage getImage(); 

	/** 判断对象是否是活着的 */
	public boolean isLife() {
		return state==LIFE; //当前状态为LIFE表示活着的，返回true，否则返回false
	}
	/** 判断对象是否是死了的 */
	public boolean isDead() {
		return state==DEAD; //当前状态为DEAD表示死了的，返回true，否则返回false
	}
	/** 判断对象是否是删除的 */
	public boolean isRemove() {
		return state==REMOVE; //当前状态为REMOVE表示删除的，返回true，否则返回false
	}
	
	/** 判断敌人是否越界 */
	public boolean isOutOfBounds(){
		return this.y>=World.HEIGHT; //敌人的y>=窗口的高，即为越界了
	}
	
	/** 判断敌人是否与子弹/英雄机撞上 this:敌人  other:子弹/英雄机 */
	public boolean isHit(FlyingObject other) {
		int x1 = this.x-other.width;  //x1:敌人的x-子弹/英雄机的宽
		int x2 = this.x+this.width;   //x2:敌人的x+敌人的宽
		int y1 = this.y-other.height; //y1:敌人的y-子弹/英雄机的高
		int y2 = this.y+this.height;  //y2:敌人的y+敌人的高
		int x = other.x;              //x:子弹/英雄机的x
		int y = other.y;              //y:子弹/英雄机的y
		
		return x>=x1 && x<=x2 
			   && 
			   y>=y1 && y<=y2; //x在x1与x2之间，并且，y在y1与y2之间，即为撞上了 
	}
	
	/** 飞行物去死 */
	public void goDead() {
		state = DEAD; //将对象状态修改为DEAD
	}
	
}





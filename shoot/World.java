package cn.tedu.shoot;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Random;
import java.util.Arrays;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
/** ������Ϸ���� */
class World extends JPanel {
	public static final int WIDTH = 400;  //���ڵĿ�
	public static final int HEIGHT = 700; //���ڵĸ�
	
	public static final int START = 0;     //����״̬
	public static final int RUNNING = 1;   //����״̬
	public static final int PAUSE = 2;     //��ͣ״̬
	public static final int GAME_OVER = 3; //��Ϸ����״̬
	private int state = START; //��ǰ״̬(Ĭ��Ϊ����״̬)
	
	private Sky sky = new Sky();         //���
	private Hero hero = new Hero();      //Ӣ�ۻ�
	private FlyingObject[] enemies = {}; //����(С�л�����л���С�۷�)����
	private Bullet[] bullets = {};       //�ӵ�����
	
	/** ���ɵ���(С�л�����л���С�۷�)���� */
	public FlyingObject nextOne(){
		Random rand = new Random();  //���������
		int type = rand.nextInt(20); //0��19֮��
		if(type<5){ //0��4ʱ������С�۷����
			return new Bee();
		}else if(type<12){ //5��11ʱ������С�л�����
			return new Airplane();
		}else{ //12��19ʱ�����ش�л�����
			return new BigAirplane();
		}
 	}
	
	private int enterIndex = 0; //�����볡����
	/** ����(С�л�����л���С�۷�)�볡 */
	public void enterAction(){ //ÿ10������һ��
		enterIndex++; //ÿ10������1
		if(enterIndex%40==0){ //ÿ400(40*10)������һ��
			FlyingObject obj = nextOne(); //��ȡ���˶���
			enemies = Arrays.copyOf(enemies,enemies.length+1); //����
			enemies[enemies.length-1] = obj; //�����˶���obj��ӵ�enemies�����һ��Ԫ����
		}
	}
	
	private int shootIndex = 0; //�ӵ��볡����
	/** �ӵ��볡 */
	public void shootAction(){ //ÿ10������һ��
		shootIndex++; //ÿ10������1
		if(shootIndex%30==0){ //ÿ300(30*10)������һ��
			Bullet[] bs = hero.shoot(); //��ȡ�ӵ�����
			bullets = Arrays.copyOf(bullets,bullets.length+bs.length); //����(bs�м���Ԫ�ؾ����󼸸�����)
			System.arraycopy(bs,0,bullets,bullets.length-bs.length,bs.length); //�����׷��
		}
		
	}
	
	/** �������ƶ� */
	public void stepAction(){ //ÿ10������һ��
		sky.step(); //��ն�
		for(int i=0;i<enemies.length;i++){ //�������е���
			enemies[i].step(); //���˶�
		}
		for(int i=0;i<bullets.length;i++){ //���������ӵ�
			bullets[i].step(); //�ӵ���
		}
	} 
	
	/** ɾ��Խ��ĵ��˺��ӵ�----�����ڴ�й© */
	public void outOfBoundsAction(){ //ÿ10������һ��
		for(int i=0;i<enemies.length;i++){ //�������е���
			if(enemies[i].isOutOfBounds() || enemies[i].isRemove()){ //Խ��Ļ���REMOVE״̬��
				enemies[i] = enemies[enemies.length-1]; //�����һ��Ԫ�ظ�ֵ�������Ԫ����
				enemies = Arrays.copyOf(enemies,enemies.length-1); //����(���������һ��Ԫ��)
			}
		} 
		
		for(int i=0;i<bullets.length;i++){ //���������ӵ�
			if(bullets[i].isOutOfBounds() || bullets[i].isRemove()){ //Խ��Ļ���REMOVE״̬��
				bullets[i] = bullets[bullets.length-1]; //�����һ��Ԫ�ظ�ֵ�������Ԫ����
				bullets = Arrays.copyOf(bullets,bullets.length-1); //����(���������һ��Ԫ��)
			}
		}
	}
	
	private int score = 0; //��ҵ÷�
	/** �ӵ�����˵���ײ */
	public void bulletBangAction() { //ÿ10������һ��
		for(int i=0;i<bullets.length;i++) { //���������ӵ�
			Bullet b = bullets[i]; //��ȡÿһ���ӵ�
			for(int j=0;j<enemies.length;j++) { //�������е���
				FlyingObject f = enemies[j]; //��ȡÿһ������
				if(b.isLife() && f.isLife() && f.isHit(b)) { //�������Ų���ײ����
					b.goDead(); //�ӵ�ȥ��
					f.goDead(); //����ȥ��
					if(f instanceof EnemyScore) { //����ײ�����ܵ÷�
						EnemyScore es = (EnemyScore)f; //����ײ����ǿתΪ�÷ֽӿ�
						score += es.getScore(); //��ҵ÷�
					}
					if(f instanceof EnemyAward) { //����ײ����Ϊ����
						EnemyAward ea = (EnemyAward)f; //����ײ����ǿתΪ�����ӿ�
						int type = ea.getAwardType(); //��ȡ��������
						switch(type) { //���ݽ�����������ȡ��ͬ�Ľ���
						case EnemyAward.AWARD_FIRE: //����������Ϊ����
							hero.addFire();         //��Ӣ�ۻ�������
							break;
						case EnemyAward.AWARD_LIFE: //����������Ϊ��
							hero.addLife();         //��Ӣ�ۻ�����
							break;
						}
					}
				}
			}
		}
	}
	
	/** Ӣ�ۻ�����˵���ײ */
	public void heroBangAction() { //ÿ10������һ��
		for(int i=0;i<enemies.length;i++) { //�������е���
			FlyingObject f = enemies[i]; //��ȡÿһ������
			if(f.isLife() && hero.isLife() && f.isHit(hero)) { //�������ţ�����ײ����
				f.goDead(); //����ȥ��
				hero.subtractLife(); //Ӣ�ۻ�����
				hero.clearFire(); //Ӣ�ۻ���ջ���ֵ
			}
		}   
	}
	
	/** �����Ϸ���� */
	public void checkGameOverAction() { //ÿ10������һ��
		if(hero.getLife()<=0) { //��Ӣ�ۻ�����<=0�����ʾ��Ϸ������
			state = GAME_OVER; //����ǰ״̬�޸�Ϊ��Ϸ����״̬
		}
	}
	
	/** ���������ִ�� */
	public void action() {
		//���������
		MouseAdapter m = new MouseAdapter(){ 
			/** ��дmouseMoved()����ƶ��¼� */
			public void mouseMoved(MouseEvent e){
				if(state==RUNNING) { //��������״̬��ִ��
					int x = e.getX(); //��ȡ����x����
					int y = e.getY(); //��ȡ����y����
					hero.moveTo(x, y); //Ӣ�ۻ���������ƶ�
				}
			}
			/** ��дmouseClicked()������¼� */
			public void mouseClicked(MouseEvent e){
				switch(state) { //���ݵ�ǰ״̬����ͬ�Ĵ���
				case START:        //����״̬ʱ
					state = RUNNING; //�޸�Ϊ����״̬
					break;
				case GAME_OVER:  //��Ϸ����״̬ʱ
					score = 0; //�����ֳ�(���ݹ���)
					sky = new Sky();
					hero = new Hero();
					enemies = new FlyingObject[0];
					bullets = new Bullet[0];
					state = START; //�޸�Ϊ����״̬
					break;
				}
			}
			/** ��дmouseExited()����Ƴ��¼� */
			public void mouseExited(MouseEvent e){
				if(state==RUNNING) { //����״̬ʱ
					state = PAUSE;   //�޸�Ϊ��ͣ״̬
				}
			}
			/** ��дmouseEntered()��������¼� */
			public void mouseEntered(MouseEvent e){
				if(state==PAUSE) {   //��ͣ״̬ʱ
					state = RUNNING; //�޸�Ϊ����״̬
				}
			}
		};
		this.addMouseListener(m);
		this.addMouseMotionListener(m);
		
		Timer timer = new Timer(); //��ʱ������
		int intervel = 10; //��ʱ���(�Ժ���Ϊ��λ)
		timer.schedule(new TimerTask(){
			public void run(){ //��ʱ�ɵ���(ÿ10������һ��)
				if(state==RUNNING) { //��������״̬��ִ��
					enterAction(); //����(С�л�����л���С�۷�)�볡
					shootAction(); //�ӵ��볡
					stepAction();  //�������ƶ�
					outOfBoundsAction(); //ɾ��Խ��ĵ��˺��ӵ�
					bulletBangAction();  //�ӵ�����˵���ײ
					heroBangAction();    //Ӣ�ۻ�����˵���ײ
					checkGameOverAction(); //�����Ϸ����
				}
				repaint(); //�ػ�(���µ���paint����)
			}  
		},intervel,intervel); //��ʱ�ƻ�
	}
	
	/** ��дpaint()��  g:���� */
	public void paint(Graphics g) {
		g.drawImage(sky.getImage(),sky.x,sky.y,null); //�����
		g.drawImage(sky.getImage(),sky.x,sky.getY1(),null); //����յĵ�2��ͼ
		g.drawImage(hero.getImage(),hero.x,hero.y,null); //��Ӣ�ۻ�
		for(int i=0;i<enemies.length;i++) { //�������е���
			FlyingObject f = enemies[i]; //��ȡÿһ������
			g.drawImage(f.getImage(),f.x,f.y,null); //������
		}
		for(int i=0;i<bullets.length;i++) { //���������ӵ�
			Bullet b = bullets[i]; //��ȡÿһ���ӵ�
			g.drawImage(b.getImage(),b.x,b.y,null); //���ӵ�
		}
		
		g.drawString("SCORE: "+score,10,25); //����
		g.drawString("LIFE: "+hero.getLife(),10,45); //����
		
		switch(state) { //���ݵ�ǰ״̬����ͬ��״̬ͼ
		case START: //����״̬ʱ������ͼ
			g.drawImage(Images.start,0,0,null);
			break;
		case PAUSE: //��ͣ״̬ʱ����ͣͼ
			g.drawImage(Images.pause,0,0,null);
			break;	
		case GAME_OVER: //��Ϸ����״̬ʱ����Ϸ����ͼ
			g.drawImage(Images.gameover,0,0,null);
			break;
		}
	}      
	
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		World world = new World();
		frame.add(world);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(WIDTH,HEIGHT);
		frame.setLocationRelativeTo(null); 
		frame.setVisible(true); //1)���ô��ڿɼ�  2)�������paint()����
		
		world.action(); //���������ִ��
	}
	
}



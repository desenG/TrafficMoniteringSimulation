package model;

import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;

import java.util.HashSet;
import java.util.Random;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;

import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import javax.persistence.UniqueConstraint;

import javax.persistence.CascadeType;


import java.util.Set;

@Entity
@Table(
		name = "car",  
		uniqueConstraints = 
			{
				@UniqueConstraint(columnNames = "LICENSE")
			}
		)
public class Car implements Runnable,Serializable{


//	private int carid;
	private String license;
	private float px;
	private float py;
	private float dx;
	private float dy;
	private String region;
	private float size;
	private boolean run;
	


	private Color color;
	
	private Set<Ticket> tickets = new HashSet<Ticket>(0);

//	@Id
//	@GeneratedValue(strategy=GenerationType.AUTO)
//	@Column(name = "Carid")
//	public int getCarid() {
//		return carid;
//	}
//	public void setCarid(int carid) {
//		this.carid = carid;
//	}


	
	/**
	 * @return the license
	 */
	@Id
	@Column(name = "LICENSE", unique = true, nullable = false)
	public String getLicense() {
		return license;
	}
	/**
	 * @param license the license to set
	 */
	public void setLicense(String license) {
		this.license = license;
	}
	public float getPx() {
		return px;
	}
	public void setPx(float px) {
		this.px = px;
	}
	public float getPy() {
		return py;
	}
	public void setPy(float py) {
		this.py = py;
	}
	public float getDx() {
		return dx;
	}
	public void setDx(float dx) {
		this.dx = dx;
	}
	public float getDy() {
		return dy;
	}
	public void setDy(float dy) {
		this.dy = dy;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	/**
	 * @return the size
	 */
	public float getSize() {
		return size;
	}
	/**
	 * @param size the size to set
	 */
	public void setSize(float size) {
		this.size = size;
	}
	
	public boolean getRun() {
		return run;
	}
	public void setRun(boolean run) {
		this.run = run;
	}
	
	
	public void setColor(Color color) {
		this.color = color;
	}
	public Color getColor() {
		return color;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "k.car", cascade=CascadeType.ALL)
	public Set<Ticket> getTickets() {
		return this.tickets;
	}

	public void setTickets(Set<Ticket> tickets) {
		this.tickets = tickets;
	}
	
	
	//varable
	

	private static Random random=new Random();
	private static int SIZE ;
	

	//constructor
	public Car()
	{		
		this.px=100;
		this.py=100;
		this.dx=2-random.nextInt(4);
		this.dy=2-random.nextInt(4);
		//this.region="CENTER";
		SIZE = random.nextInt(16)  + 5;//5<r<20;
		color=new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255));
		this.size=SIZE;
		run=true;

	}
	
	public Car(String region)
	{		
		this();
		this.region=region;
	}
	
	public Car(String region,String license)
	{		
		this(region);
		this.license=license;
	}
	
	public Car(float px,float py,String region)
	{
		this();
		this.px=px;
		this.py=py;
		this.region=region;
	}
	
	public Car(String license,float px,float py,String region)
	{
		this(px,py,region);
		this.license=license;
	}


	public Car(float px,float py,float dx,float dy,String region)
	{
		this(px,py,region);
		this.dx=dx;
		this.dy=dy;
	}
	
	public Car(float px,float py,float dx,float dy,float size,String region)
	{
		this(px,py,dx,dy,region);

		this.size=size;
	}
	
	public Car(float px,float py,float dx,float dy,float size,Color color,String region)
	{
		this(px,py,dx,dy,size,region);
		this.color=color;
	}
	
	public Car(String license,float px,float py,float dx,float dy,float size,Color color,String region)
	{
		this(px,py,dx,dy,size,color,region);
		this.license=license;
	}
	
//	public Car(int id,float px,float py,float dx,float dy,String region)
//	{
//		this(px,py,dx,dy,region);
//		this.carid=id;
//	}
	
	//behaviours
	
	
	//paint itself
    public void paint(Graphics g){
        g.setColor(color);
	    g.fillOval((int) (px-size/2), (int) (py-size/2), (int)size, (int)size);
    }
    
    

    
	@Override
	public void run() {
		while (run){
			
			//Car b;
        	
        	if(px < size/2 && dx<0)
            {
        		switch(region.trim())
        		{
        			case "LEFT":
        				px=size/2;
                        dx=-dx;
        				break;
        			case "RIGHT":
        				px=300-size/2;
        				region="CENTER";
        				return;
        			default://center
        				px=300-size/2;
        				region="LEFT";
        				return;
        		
        		}
        		
            } else if(px > 300-size/2 && dx>0)
            {
            	
        		switch(region)
        		{
        			case "RIGHT":
                    	px=300-size/2;
                    	dx=-dx;
                    	break;
        			case "LEFT":
        				px=size/2;
        				region="CENTER";
        				return;
        			default://center
        				px=size/2;
        				region="RIGHT";
        				return;
        		}
            	

            }
             
            if(py < size/2 && dy<0)
            {
            	 py=size/2;
            	dy=- dy;
            } 
            else if(py > 300-size/2 && dy>0) {          

                
            	py=300-size/2;

            	dy=- dy;

            }
        	
        	//make the car move
        	px+=dx;
        	py+=dy;
        	
	
            //acceleration
            //velocity.translate((int)acceleration.getX(),(int)acceleration.getY());

            //sleep while waiting to update the next frame of the animation
            try{

                    Thread.sleep(40);  // wake up roughly 25 frames per second
            	
            }
            catch ( InterruptedException exception )
            {
                exception.printStackTrace();
            }
		}
		run=true;
//		return;


		
	}




	
	
}

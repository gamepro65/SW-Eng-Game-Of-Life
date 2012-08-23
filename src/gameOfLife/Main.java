package gameOfLife;


/**
Name: Chris Drury
Class: CSc 2310: Introduction to programming
Filename: Main.java
Date written: April, 19, 2011

Description:
This class runs the game logic and sets up the screen. 
*/

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Random;
import javax.vecmath.Vector2f;
import processing.core.*;

public class Main extends PApplet{
	
	//Asteroids and bullets array lists
	ArrayList asteroids;
	ArrayList bullets;
	
	//My ingame health
	int health;
	//current difficulty multiplyer
	static public float difficulty;
	//Should we render with color?
	static public boolean useColor;
	//Current background color
	Color backgroundColor;
	
	ArrayList stars;
	
	//Hold our information for figuring
	//out our current fps. this includes a
	//Timer so we just update the info to our user
	//at a longer interval to make it easier to read
	double delta;
	long lastUpdateTime;
	float fpsCounter;
	static float _fps;
	
	//current score
	int score;
	//scoreboard score
	int visualScore;
	
	//Keyboard control bools
	boolean up;
	boolean left;
	boolean right;
	boolean paused;
	
	//should use mouse control or not
	static boolean mouseControl;
	
	//Setup our game.
	public void setup()
	{
		stars = new ArrayList();
		//Screen Width and Height
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.screen.width = (int)(dim.width);
		this.screen.height = (int)(dim.height);
		//Psuedo random number generator
		Random ran = new Random();
		//make a new random backgroundColor
		backgroundColor = new Color(ran.nextFloat(),ran.nextFloat(),ran.nextFloat(),1);
		//Default to color on
		useColor = true;
		//Health -1 means our game starts paused/menu screen
		health = -1;
		score = 0;
		visualScore = 0;
		
		//allows us to resize the frame
		frame.setResizable(true);
		size(this.screen.width,this.screen.height);
		frame.setSize(this.screen.width,this.screen.height); 
		frame.setLocation((dim.width/2 - this.screen.width/2), (dim.height/2 - this.screen.height/2));	
		
		smooth();
		//set mouse control off by default
		mouseControl = false;
		//Set framerate we want processing to try
		//and achieve.
		frameRate(60);
		lastUpdateTime = 0;
		//difficulty multiplyer starts at 1;
		difficulty = 1.3f;
		//initialize our array lists
		asteroids = new ArrayList();
		bullets = new ArrayList();
		delta = .017;
		lastUpdateTime = System.currentTimeMillis();
		_fps = 1;
		up = false;
		left = false;
		paused = true;
		right = false;
		//create my ship in the middle of the screen.
		PFont f;
		f = createFont("Arial",16,true); // Arial, 16 point, anti-aliasing on
		textFont(f); 
		
	}
	

	public void update()
	{		
		//If we are not pasued, aka we are running
		if (!paused)
		{
			
		}
	}
	
	public void mousePressed()
	{
		
	}
	
	public void keyPressed()
	{
		//+ sign
		if (keyCode == 61)
		{
			//+ hit so lets increase the screen size by 10
			this.screen.width += 10;
			this.screen.height += 10;
			Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
			size(this.screen.width,this.screen.height);
			frame.setSize(this.screen.width,this.screen.height); 
			//keep the frame in the middle of the screen
			frame.setLocation((dim.width/2 - this.screen.width/2), (dim.height/2 - this.screen.height/2));
		}
		//- sign
		if (keyCode == 45)
		{
			//- hit so lets decrease the screen size by 10
			this.screen.width -= 10;
			this.screen.height -= 10;
			Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
			size(this.screen.width,this.screen.height);
			frame.setSize(this.screen.width,this.screen.height); 
			//keep the frame in the middle of the screen
			frame.setLocation((dim.width/2 - this.screen.width/2), (dim.height/2 - this.screen.height/2));
		}
		
		//keycode 37 = left arrow
		if (keyCode == 37)
		{
			left = true;
		}
		//39 = right arrow
		if (keyCode == 39)
		{
			right = true;
		}
		//32 = spacebar
		if (keyCode == 32)
		{
			
		}
		//38 = up
		if (keyCode == 38)
		{
			up = true;
		}
		
		//key t is used to toggle color
		//on/off
		if (key == 't')
		{
			if (useColor)
				useColor = false;
			else
				useColor = true;
		}
		
		//key m is used to toggle mouseMode
		//on/off
		if (key == 'm')
		{
			if (mouseControl)
				mouseControl = false;
			else
				mouseControl = true;
		}
			
		//10 = enter
		if (keyCode == 10)
		{
			//If paused
			if (paused)
			{
				//unpause
				paused = false;
				//if health <= 0 reset the game
				if (health <= 0)
				{
					//remove any asteroids from previous games
					asteroids.clear();
					//reset score
					score = 0;
					visualScore = 0;
					//reset random background color
					Random ran = new Random();
					backgroundColor = new Color(ran.nextFloat(),ran.nextFloat(),ran.nextFloat(),1);
					//reset health
					health = 100;
					//reset difficulty
					difficulty = 1;
				}
			}
			else
			{
				//pause the game
				paused = true;
			}
		}
	}
	
	public void keyReleased()
	{
		//releasing the key removes the boolean
		//of that key
		if (keyCode == 37)
		{
			left = false;
		}
		if (keyCode == 39)
		{
			right = false;
		}
		if (keyCode == 38)
		{
			up = false;
		}
	}
	
	public static void main(String[] args) {
		//init the game
		PApplet.main(new String[] {"--present", "gameOfLife.Main"});		
		
	}
	
	public void draw()
	{
		//update our delta based from current - lastTime
		long currentTime = System.currentTimeMillis();
		delta = (currentTime - lastUpdateTime);
		delta = (1/(1000/delta));
		fpsCounter += delta;
		//if its been a 1/4 second
		//update the fps time to display
        if(fpsCounter > 0.25f) {
            fpsCounter = 0;
            _fps = (int)((1/delta));
        }						
		lastUpdateTime = currentTime;
		
		//if we are using color set our bg color
		if (useColor)
		{
			background(backgroundColor.getRGB());
		}
		else
		{
			//Gimmie black
			background(0);
		}
		//set no stroke and then draw our stars.
		noStroke();
		for (int x = 0; x < stars.size(); x++)
		{
			Particle p = (Particle)stars.get(x);
			p.draw();
		}
		stroke(0);
		
		
		//set text to white/black depending on mode
		if (!useColor)
			fill(255);
		else
			fill(0);
		//align text to center of position
		textAlign(CENTER);


		
		//draw score at middle of bottom of screen
		//text("Score: " + visualScore, this.screen.width/2, this.screen.height-70);
		
		//if we are not paused
		if (!paused)
		{
			//update our game logic
			update();
			/*
			//draw information text including our fps, toggle switces
			text("Press 'Enter' To Pause", 150, this.screen.height-90);
			text("Press 'T' To Toggle Color Mode", 150, this.screen.height-70);
			text("FPS: " + _fps, this.screen.width-200, 10);
			text ("Press 'M' To Toggle Mouse Mode", 150, this.screen.height-80);
			text("+/- Will resize your screen accordingly", 150, this.screen.height - 100);
			*/
		}
		else
		{
			/*
			//if health is less than 0
			if (health < 0)
			{				
				//tell them to hit enter to play and toggles
				text("Press 'Enter' To Play", 150, this.screen.height-90);
				text("Press 'T' To Toggle Color Mode", 150, this.screen.height-70);
				text ("Press 'M' To Toggle Mouse Mode", 150, this.screen.height-80);
				text("+/- Will resize your screen accordingly", 150, this.screen.height - 100);
			}
			else
			{
				//Game is just paused so tell them that
				//Also mention how to resume and toggle switches
				text("Paused.", this.screen.width/2, this.screen.height/2);
				text("Press 'Enter' To Resume", 150, this.screen.height-90);
				text("Press 'T' To Toggle Color Mode", 150, this.screen.height-70);
				text ("Press 'M' To Toggle Mouse Mode", 150, this.screen.height-80);
				text("+/- Will resize your screen accordingly", 150, this.screen.height - 100);
		
			}
			*/
			//Display the current fps.
			text("FPS: " + _fps, this.screen.width-200, 20);			
		}		
		
		//Put my name in the game.
		text("Created by: Group Name", 150, this.screen.height - 60);
		
		
		
		//loop and draw the bullets
		for (int x = 0; x < bullets.size(); x++)
		{
			Bullets temp = (Bullets)bullets.get(x);
			temp.draw();
		}
		
		//If health is > 0 aka we are in game
		if (health > 0)
		{
			//if use color color health bar
			if (useColor)
				fill(255, 0, 0);
			else
			{
				noFill();
			}
			//draw the current current health ammount across the top
			//as a percentage of the whole.
			rect(10, 10, (((float)this.screen.width-30)*((float)health/100)), 20);
			//set text to white/black depending on mode
			if (!useColor)
				fill(255);
			else
				fill(0);
			text("Health", 30, 25);
		}
		
		
	}
	
}

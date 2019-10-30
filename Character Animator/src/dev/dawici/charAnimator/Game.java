package dev.dawici.charAnimator;

import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import dev.dawici.charAnimator.display.Display;
import dev.dawici.charAnimator.gfx.ImageLoader;
import dev.dawici.charAnimator.gfx.SpriteSheet;

public class Game implements Runnable
{
	private Display display;
	public int width, height;
	public String title;
	
	private boolean running = false;
	private Thread thread;
	
	private BufferStrategy bs;
	private Graphics g;
	
	private BufferedImage testImage;
	private BufferedImage test;
	private SpriteSheet sheet;
	
	public Game(String title, int width, int height)
	{
		this.width = width;
		this.height = height;
	}
	
	private void init()
	{
		display = new Display(title, width, height);
		test = ImageLoader.loadImage("/textures/test.png");
		sheet = new SpriteSheet(test);
	}
	
	private void tick()
	{
		
	}
	
	int r, c = 0;
	int count = 0;
	private void render()
	{
		bs = display.getCanvas().getBufferStrategy();
		if(bs == null)
		{
			display.getCanvas().createBufferStrategy(3);
			return;
		}
		g = bs.getDrawGraphics();
		
		int quarter = test.getWidth()/4;
		count++;
		if(count == 2000)
		{
			if(r == 4)
			{
				r = 0;
				c++;
			}
			if(c == 4)
			{
				c = 0;
			}
			g.drawImage(sheet.crop(test.getWidth()*r/4, test.getHeight()*c/4, test.getWidth()/4, test.getHeight()/4), 100, 100, null);
			r++;
			count = 0;
		}
		
		
		bs.show();
		g.dispose();
	}
	
	public void run()
	{
		init();
		
		while(running)
		{
			tick();
			render();
		}
		
		stop();
		
	}
	
	public synchronized void start()
	{
		if(running)
			return;
		running = true;
		thread = new Thread(this);
		thread.start();
	}
	
	public synchronized void stop()
	{
		if(!running)
			return;
		try
		{
			thread.join();
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}
}

package dev.dawici.charAnimator;

import dev.dawici.charAnimator.display.Display;

public class Launcher
{
	public static void main(String args[])
	{
		Game game = new Game("Animator", 300, 300);
		game.start();
	}
}

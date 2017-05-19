package opengl;

import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.PixelFormat;
/**
 * class responsible for basic display functions
 */
public class ScreenDisplay {
	
	/**
	 * creates a display
	 */
	public static void openDisplay(){
		
		/* Using version 3.2 of opengl */
		ContextAttribs attribs = new ContextAttribs(3, 2).
				withForwardCompatible(true).withProfileCore(true);
		
		
		try {
			Helper.getScreenDimensions();
			Display.setDisplayMode(new DisplayMode( GLOBALS.FULLSCREEN_WIDTH, 
					GLOBALS.FULLSCREEN_HEIGHT));
			Display.create(new PixelFormat(), attribs);
			
			/* sets title */
			Display.setTitle(GLOBALS.CONSTANTS.TITLE);
			
		} catch (Exception e) {
			System.err.println("Error opening display");
			e.printStackTrace();
		}
		
		/* renders whole display */
		GL11.glViewport(0, 0, GLOBALS.FULLSCREEN_WIDTH, GLOBALS.FULLSCREEN_HEIGHT);
	}
	
	/**
	 * updates the display
	 */
	public static void updateDisplay(){
		
		/* sets max refresh rate */
		Display.sync(GLOBALS.CONSTANTS.FPS_CAP);
		Display.update();
	}
	
	/**
	 * removes the display
	 */
	public static void deleteDisplay(){
		Display.destroy();
	}

}

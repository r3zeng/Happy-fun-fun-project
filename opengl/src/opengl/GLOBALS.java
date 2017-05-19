package opengl;

import java.awt.GraphicsDevice;

public class GLOBALS {

	public static int FULLSCREEN_WIDTH;
	public static int FULLSCREEN_HEIGHT;
	public static GraphicsDevice graphics;
	
	public class CONSTANTS{
		
		/* may be changed */
		public static final int FPS_CAP = 120;
		public static final String TITLE = "HELLO WORLD";
		public static final float FOV_ANGLE = 70;
		public static final float FOV_NEAR = 0.1f;
		public static final float FOV_FAR = 1000;
		
		public static final float TERRAIN_SIZE = 800;
		public static final int TERRAIN_VERTEX_COUNT = 128;
		
		/* should not be changed Universal constants */
		public static final int VERTEXES_IN_TRIANGLE = 3;
	}
	
	public class FLAGS{
		public static final boolean FULLSCREEN = true;
	}

}

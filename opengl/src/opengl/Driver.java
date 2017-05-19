package opengl;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

/**
 * main game loop class.  Does updates for rendering, mechanics and game
 * logic
 */
public class Driver {
	public static void main(String[] args){
/******* Preparation ***************************************/
		
		if(args.length == 0){
			System.err.println("Please use name of model (without the .off) to render in command args \nmodel must be in res/model folder ");
			System.err.println("Example ./Driver bunny 10 (scale optional)");
			System.exit(1);
		}
		
		float scale = 1;
		
		if(args.length == 2){
			try{
				scale = Float.parseFloat(args[1]);
			}catch(Exception e){
				System.err.println("second parameter (scale) must be Integer");
				System.exit(1);
			}
		}
		
		/* creates a display */
		ScreenDisplay.openDisplay();
		/* creates a loader object */
		Loader loader = new Loader();
		
		/* creates render object */
		Render render = new Render();
		
		Model model = Helper.loadOFFModel(args[0], loader);
		Entity entity = new Entity(model, new Vector3f(0 ,0 , 0), 0, 0 ,0, 1 );
		
		entity.translate(0, -1, -2.5f);
		entity.Scale(scale);
		Lighting[] lights = new Lighting[6]; 
		
		lights[0] = new Lighting(new Vector3f(0, 1000, 0), new Vector3f(1f, 1f, 1f));
		lights[1] = new Lighting(new Vector3f(0, -1000, 0), new Vector3f(1f, 1f, 1f));
		lights[2] = new Lighting(new Vector3f(1000, 0, 0), new Vector3f(1f, 1f, 1f));
		lights[3] = new Lighting(new Vector3f(-1000, 0, 0), new Vector3f(1f, 1f, 1f));
		lights[4] = new Lighting(new Vector3f(0, 0, 1000), new Vector3f(1f, 1f, 1f));
		lights[5] = new Lighting(new Vector3f(0, 0, -1000), new Vector3f(1f, 1f, 1f));
		
		Camera camera = new Camera();
		
/******* Main Game Loop ************************************/
		/* loops until closed */
		while(!Display.isCloseRequested()){
			//update camera position
			camera.move();
	
			if(!Keyboard.isKeyDown(Keyboard.KEY_B)){
				if(Keyboard.isKeyDown(Keyboard.KEY_X)){
					entity.rotate(0.5f, 0, 0);
				}else if(Keyboard.isKeyDown(Keyboard.KEY_Y)){
					entity.rotate(0, 0.5f, 0);
				}else if(Keyboard.isKeyDown(Keyboard.KEY_Z)){
					entity.rotate(0, 0, 0.5f);
				}
			}else{
				if(Keyboard.isKeyDown(Keyboard.KEY_X)){
					entity.rotate(-0.5f, 0, 0);
				}else if(Keyboard.isKeyDown(Keyboard.KEY_Y)){
					entity.rotate(0, -0.5f, 0);
				}else if(Keyboard.isKeyDown(Keyboard.KEY_Z)){
					entity.rotate(0, 0, -0.5f);
				}
			}
			//render the entity
			render.renderEntity(entity);
			
			//render the scene
			render.render(lights, camera);
			ScreenDisplay.updateDisplay();
		}
/******* Clean Up ******************************************/
		render.delete();
		
		/* remove VAOs and VBOs */
		loader.cleanUp();
		/* removes display after use */
		ScreenDisplay.deleteDisplay();
	}
}

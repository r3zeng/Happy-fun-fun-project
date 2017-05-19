package opengl;

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
		
		ModelTexture texture = new ModelTexture(loader.loadTexture("white"), 50, 1);
		TexturedModel texturedModel = new TexturedModel(model, texture);
		Entity entity = new Entity(texturedModel, new Vector3f(0 ,0 , 0), 0, 0 ,0, 1 );
		
		
		entity.translate(0, -1, -2.5f);
		entity.Scale(scale);
		
		Lighting light = new Lighting(new Vector3f(100, 1000, 100), new Vector3f(1f, 1f, 1f));
		Camera camera = new Camera();
		
		
/******* Main Game Loop ************************************/
		/* loops until closed */
		while(!Display.isCloseRequested()){
			//update camera position
			camera.move();
	
			//render the entity
			render.renderEntity(entity);
			
			//render the scene
			render.render(light, camera);
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

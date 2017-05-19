package opengl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL11;

public class Render {
	private StaticShader Sshader;
	private EntityRender Erender;
	private Map<TexturedModel, List<Entity>> entities;
	
	public Render(){
		
		/* render only outwards facing faces */
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glCullFace(GL11.GL_BACK);
		
		/* create member instance variable using their default constructors */
		Sshader = new StaticShader();
		Erender = new EntityRender(Sshader);
		
		entities = new HashMap <TexturedModel, List<Entity>>();
	}
	 
	/**
	 * renders a scene
	 * 
	 * @param light: lighting used in the scene
	 * @param camera: camera used in the scene
	 */
	public void render(Lighting light, Camera camera){
		
		/* renders */
		prepare();
		Sshader.start();
		Sshader.loadLightingPosition(light);
		Sshader.loadViewMatrix(camera);
		Erender.render(entities);
		Sshader.stop();
		
		/* remove excess entities */
		entities.clear();
	}
	
	/**
	 *  adds an entity to map of entities to be render
	 * 
	 * @param entity: entity being added
	 */
	public void renderEntity(Entity entity){
		TexturedModel Tm = entity.getTexturedModel();
		List<Entity> batch = entities.get(Tm);
		/* checks if a List for the type of texture model already
		 * exists */
		if(batch != null){ //already exists
			batch.add(entity);
		}
		else{ //does not exist
			List<Entity> newBatch = new ArrayList<Entity>();
			newBatch.add(entity);
			entities.put(Tm, newBatch);
		}
	}
	
	/* apply global opengl effects */
	public void prepare(){
		/* renders forward vertices first */
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		
		/* sets background to red */
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		GL11.glClearColor(0.25f, 0.30f, 0.40f, 1);
	}
	
	/**
	 * delete all entities associated with the render
	 */
	public void delete(){
		Sshader.delete();
	}
}


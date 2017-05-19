package opengl;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

/**
 * Contains a number of helpful general purpose function to perform various repetitive, difficult or trivial tasks.
 */
public class Helper {
	/**
	 * creates a temporary JFrame to obtain the size of the screen from the graphics device
	 */
	public static void getScreenDimensions() {
		JFrame tempFrame = new JFrame();
		
		/* get info about screen of device */
		GraphicsDevice graphics = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		
		/* makes tempFrame full-screen */
		graphics.setFullScreenWindow(tempFrame);
		
		/* stores size of full-screen */
		GLOBALS.FULLSCREEN_WIDTH = tempFrame.getWidth();
		GLOBALS.FULLSCREEN_HEIGHT = tempFrame.getHeight();
		
		/* save graphic device */
		GLOBALS.graphics = graphics;
		
		/* exit full Screen */
		graphics.getFullScreenWindow().dispose();
		graphics.setFullScreenWindow(null);
	}
	
	/**
	 * creates a transformation matrix with given translation, rotation, and scaling
	 * 
	 * @param translation: vector representing translation in x,y, and z coordinates
	 * @param rx: rotation along the x axis
	 * @param ry: rotation along the y axis
	 * @param rz: rotation along the z axis
	 * @param scale: amount to scale by
	 * @return the transformation matrix
	 */
	public static Matrix4f createTransformationMatrix( Vector3f
			translation, float rx, float ry, float rz, float scale){
		
		/* creates a 4x4 identity matrix */
		Matrix4f matrix = new Matrix4f();
		matrix.setIdentity();
		
		/* translates the matrix */
		Matrix4f.translate(translation, matrix, matrix);
		
		/* rotates the matrix */
		Matrix4f.rotate((float) Math.toRadians(rx), new Vector3f(1, 0 ,0), matrix, matrix);
		Matrix4f.rotate((float) Math.toRadians(ry), new Vector3f(0, 1 ,0), matrix, matrix);
		Matrix4f.rotate((float) Math.toRadians(rz), new Vector3f(0, 0 ,1), matrix, matrix);
		
		/* scale the matrix */
		Matrix4f.scale(new Vector3f(scale, scale, scale), matrix, matrix);
		
		return matrix;
	}
	
	/**
	 * creates the view matrix of a camera
	 * 
	 * @param camera: reference to the camera
	 * @return: the view matrix of the camera
	 */
	public static Matrix4f createViewMatrix(Camera camera){
		
		/* creates a 4x4 identity matrix */
		Matrix4f viewMatrix = new Matrix4f();
		viewMatrix.setIdentity();
		
		/* rotate the camera */
		Matrix4f.rotate((float) Math.toRadians(camera.getPitch()), new Vector3f(1, 0, 0), viewMatrix, viewMatrix);
		Matrix4f.rotate((float) Math.toRadians(camera.getYaw()), new Vector3f(0, 1, 0), viewMatrix, viewMatrix);
		Matrix4f.rotate((float) Math.toRadians(camera.getRoll()), new Vector3f(0, 0, 1), viewMatrix, viewMatrix);
		
		/* applies translation to camera*/
		Vector3f negativePositions = new Vector3f( -camera.getPosition().x, -camera.getPosition().y, 
				-camera.getPosition().z);
		Matrix4f.translate(negativePositions, viewMatrix, viewMatrix);
	
		return viewMatrix;
	}
	
	
	/**
	 * loads an off file into a model
	 * 
	 * @param fileName: name of the Off file without extension.  Note file must be placed 
	 * inside of the res folder
	 * @param loader: a loader object
	 * @return: a model of the object
	 */
	public static Model loadOFFModel(String fileName, Loader loader){
		
		/* creates all the file IO objects and data structures to store data being read in */
		FileReader fileReader = null;
		try {
			fileReader = new FileReader(new File("res/models/" + fileName + ".off"));
		} catch (FileNotFoundException e) {
			System.err.println("Error loading file: " + fileName);
		}
	
		BufferedReader reader = new BufferedReader(fileReader);
		String line;
		
		ArrayList<Vector3f> vertices = new ArrayList<Vector3f>();
		ArrayList<Face> faces = new ArrayList<Face>();
		ArrayList<Integer> indices = new ArrayList<Integer>();
		
		float VertexArray [] = null;
		float NormalArray [] = null;
		float TextureArray[] = null;
		int IndexArray [] = null;
		
		Mesh mesh = null;
		
		try {
			
			//OFF line
			line = reader.readLine();
			
			//Second Line
			line = reader.readLine();
			String[] splits = line.split(" ");
			int numVertices = Integer.parseInt(splits[0]);
			int numFaces = Integer.parseInt(splits[1]);
			int numEdges = Integer.parseInt(splits[2]);
			
			System.err.println("Vert:" + numVertices);
			System.err.println("Face:" + numFaces);
			System.err.println("Edge:" + numEdges);
			
			for(int i = 0; i < numVertices; i++){
				line = reader.readLine();
				/* splits the line along spaces */
				String[] currentLine = line.split(" ");
				Vector3f vertex = new Vector3f(Float.parseFloat(currentLine[0]), Float.parseFloat(currentLine[1]), Float.parseFloat(currentLine[2]));
				vertices.add(vertex);
			}

			/* set the the size the Texture and Normal Array*/
			TextureArray = new float[vertices.size() * 2];
			NormalArray = new float[vertices.size() * 3];

			for(int i = 0; i < TextureArray.length; i++){
				Random rand = new Random();
				TextureArray[i] = rand.nextFloat();
			}
			
			//for(int i = 0; i < NormalArray.length; i++){
				//NormalArray[i] = 0.0f;
			//}
			
			/* rearrange vertex data in correct order */	
			for(int i = 0; i < numFaces; i++){
				line = reader.readLine();
				
				/* parse line to get numbers */
				String[] currentLine = line.split(" ");
				int vertex1 = Integer.parseInt(currentLine[1]);
				int vertex2 = Integer.parseInt(currentLine[2]);
				int vertex3 = Integer.parseInt(currentLine[3]);
				
				/* for index VBO */
				indices.add(vertex1);
				indices.add(vertex2);
				indices.add(vertex3);
				
				/* calculate surface normal the face */
				Vector3f edge1to2 = Vector3f.sub(vertices.get(vertex2),  vertices.get(vertex1), null);
				Vector3f edge1to3 = Vector3f.sub(vertices.get(vertex3),  vertices.get(vertex1), null);		
				Vector3f surfaceNormal = normalize(Vector3f.cross(edge1to2, edge1to3, null));

				/* create the face object that will be added to the mesh */
				Face face = new Face(vertex1, vertex2, vertex3, surfaceNormal);
				faces.add(face);
			}
			reader.close();
			
		} catch (IOException e) {
			System.err.println("Error reading file: " + fileName);
		}
		
		mesh = new Mesh(vertices, faces);
		
		/* calculate vertex normals */
		for(int i = 0; i < vertices.size(); i++){
			ArrayList<Face> adjcentFaces = mesh.getAdjcentFaceToVertex(i);
			Vector3f sum = new Vector3f(0.0f, 0.0f, 0.0f);
			for(int j = 0; j < adjcentFaces.size(); j++){
				sum = Vector3f.add(sum, adjcentFaces.get(j).getSurfaceNormal() , null);
			}
			sum = normalize(sum);
			
			NormalArray[(3*i)] = sum.x;
			NormalArray[(3*i)+1] = sum.y;
			NormalArray[(3*i)+2] = sum.z;
		}
		
		/* Initialize size of vertex and index array */
		VertexArray = new float[(vertices.size() * 3)];
		IndexArray = new int[indices.size()];
		
		int vertexCounter = 0;
		
		/* stores vectors as a float array */
		for(int i = 0; i < vertices.size(); i++){
			VertexArray[vertexCounter++] = vertices.get(i).x;
			VertexArray[vertexCounter++] = vertices.get(i).y;
			VertexArray[vertexCounter++] = vertices.get(i).z;
		}
		
		/* push all index data from Arraylist to array */
		for(int i = 0; i < indices.size(); i++){
			IndexArray[i] = indices.get(i);
		}
		
		/* return a model object */
		return loader.loadToVAO(VertexArray, TextureArray, NormalArray , IndexArray, mesh);
	}
	
	public static Vector3f normalize(Vector3f v){
		float magnitude = (float) Math.sqrt((v.x * v.x) + (v.y * v.y) + (v.z * v.z));
		return new Vector3f(v.x / magnitude, v.y / magnitude, v.x / magnitude);
	}
	
	/**
	 * creates the projection matrix
	 */
	public static Matrix4f createProjectionMatrix(){
		float aspectRatio = (float) Display.getWidth() / (float) Display.getHeight();
		float y_scale = (float) ((1f / Math.tan(Math.toRadians(GLOBALS.CONSTANTS.FOV_ANGLE/2f))) * aspectRatio);
		float x_scale = y_scale / aspectRatio;
		float frustum_length = GLOBALS.CONSTANTS.FOV_FAR - GLOBALS.CONSTANTS.FOV_NEAR;
		
		Matrix4f projectionMatrix;
		
		projectionMatrix = new Matrix4f();
		projectionMatrix.m00 = x_scale;
		projectionMatrix.m11 = y_scale;
		projectionMatrix.m22 = -((GLOBALS.CONSTANTS.FOV_FAR + GLOBALS.CONSTANTS.FOV_NEAR) / frustum_length);
		projectionMatrix.m23 = -1;
		projectionMatrix.m32 = -((2 * GLOBALS.CONSTANTS.FOV_NEAR * GLOBALS.CONSTANTS.FOV_FAR) / frustum_length);
		projectionMatrix.m33 = 0;
		
		return projectionMatrix;
	} 
}

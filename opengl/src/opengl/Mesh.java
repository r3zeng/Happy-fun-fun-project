package opengl;

import java.util.ArrayList;
import org.lwjgl.util.vector.Vector3f;

public class Mesh {

	private ArrayList<Vector3f> Vert_Coord; //Vertices
	private ArrayList<Face> Faces; //Triangles
	
	private ArrayList<ArrayList<Face>> FacesWithThisIndex;//index n will store all faces that have Vert_Coord[n]
	private ArrayList<ArrayList<Integer>> AdjacentVertexIndex;// if there was edge from 1 to 3
	//then the value 1 would be in the list of index 3 and vice-versa
	
	public Mesh(ArrayList<Vector3f> V, ArrayList<Face> F){
		Vert_Coord = V;
		Faces = F;
		
		//create shared index data structure
		FacesWithThisIndex = new ArrayList<ArrayList<Face>> ();
		for(int i = 0; i < V.size(); i++){
			FacesWithThisIndex.add(new ArrayList<Face>());
		}
		AdjacentVertexIndex = new ArrayList<ArrayList<Integer>>();
		for(int i = 0; i < V.size(); i++){
			AdjacentVertexIndex.add(new ArrayList<Integer>());
		}
		
		//Set up adjacency
		for(int i = 0; i < Faces.size(); i++){
			int v1 = Faces.get(i).getVertex1();
			int v2 = Faces.get(i).getVertex2();
			int v3 = Faces.get(i).getVertex3();
			
			FacesWithThisIndex.get(v1).add(Faces.get(i));
			FacesWithThisIndex.get(v2).add(Faces.get(i));
			FacesWithThisIndex.get(v3).add(Faces.get(i));
			
			if(!AdjacentVertexIndex.get(v1).contains(v2)){
				AdjacentVertexIndex.get(v1).add(v2);
			}
			if(!AdjacentVertexIndex.get(v1).contains(v3)){
				AdjacentVertexIndex.get(v1).add(v3);
			}
			if(!AdjacentVertexIndex.get(v2).contains(v1)){
				AdjacentVertexIndex.get(v2).add(v1);
			}
			if(!AdjacentVertexIndex.get(v2).contains(v3)){
				AdjacentVertexIndex.get(v2).add(v3);
			}
			if(!AdjacentVertexIndex.get(v3).contains(v1)){
				AdjacentVertexIndex.get(v3).add(v1);
			}
			if(!AdjacentVertexIndex.get(v3).contains(v2)){
				AdjacentVertexIndex.get(v3).add(v2);
			}
		}	
	}
	
	public int getNumberVertices(){
		return Vert_Coord.size();
	}
	
	public int getNumberFace(){
		return Faces.size();
	}
	
	public ArrayList<Face> getAdjcentFaceToVertex(int vertexIndex){
		return FacesWithThisIndex.get(vertexIndex);
	}
	
	public Vector3f getVertexatIndex(int index){
		return Vert_Coord.get(index);
	}
	
	
	public int[] getIndexVBO(){
		int[] ret = new int[3 * Faces.size()];//only triangle
		
		for(int i = 0; i < Faces.size(); i++){
			ret[(3 * i)] = Faces.get(i).getVertex1();
			ret[(3 * i)+1] = Faces.get(i).getVertex2();
			ret[(3 * i)+2] = Faces.get(i).getVertex3();
		}
		
		return ret;
	}
	
	public boolean ExistEdgeBetween2Vert(int index1, int index2){
		boolean ret = false;
		
		for(int i = 0; i < AdjacentVertexIndex.get(index1).size(); i++){
			if(AdjacentVertexIndex.get(index1).get(i) == index2){
				ret = true;
			}
		}
		
		return ret;
	}
	
}

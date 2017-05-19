package opengl;

import java.util.ArrayList;
import java.util.HashMap;

import org.lwjgl.util.vector.Vector3f;

public class Mesh {

	private ArrayList<Vector3f> Vert_Coord; //Vertices
	private ArrayList<Face> Faces; //Triangles
	
	private HashMap<Vector3f, ArrayList<Face>> VertexToFace;
	private HashMap<Vector3f, ArrayList<Vector3f>> VertexToVertex;
	
	public Mesh(ArrayList<Vector3f> V, ArrayList<Face> F){
		Vert_Coord = V;
		Faces = F;
		
		VertexToFace = new HashMap<Vector3f, ArrayList<Face>>();
		VertexToVertex = new HashMap<Vector3f, ArrayList<Vector3f>>();
		
		for(int i = 0; i < Faces.size(); i++){
			Vector3f v1 = Vert_Coord.get(Faces.get(i).getVertex1());
			Vector3f v2 = Vert_Coord.get(Faces.get(i).getVertex2());
			Vector3f v3 = Vert_Coord.get(Faces.get(i).getVertex3());
			
			//add vertex 1 to VtoT
			if(VertexToFace.containsKey(v1)){
				VertexToFace.get(v1).add(Faces.get(i));
			}else{
				ArrayList<Face> f = new ArrayList<Face>();
				f.add(Faces.get(i));
				VertexToFace.put(v1, f);
			}
			
			//add vertex2 to VtoT
			if(VertexToFace.containsKey(v1)){
				VertexToFace.get(v1).add(Faces.get(i));
			}else{
				ArrayList<Face> f = new ArrayList<Face>();
				f.add(Faces.get(i));
				VertexToFace.put(v2, f);
			}
			
			//add vertex3 to VtoT
			if(VertexToFace.containsKey(v3)){
				VertexToFace.get(v3).add(Faces.get(i));
			}else{
				ArrayList<Face> f = new ArrayList<Face>();
				f.add(Faces.get(i));
				VertexToFace.put(v3, f);
			}
			
			//add vertex 1 to VtoV
			if(VertexToVertex.containsKey(v1)){
				if(!VertexToVertex.get(v1).contains(v2)){
					VertexToVertex.get(v1).add(v2);
				}
				if(!VertexToVertex.get(v1).contains(v3)){
					VertexToVertex.get(v1).add(v3);
				}
			}else{
				VertexToVertex.put(v1, new ArrayList<Vector3f>());
				VertexToVertex.get(v1).add(v2);
				VertexToVertex.get(v1).add(v3);
			}
			
			//add vertex 2 to VtoV
			if(VertexToVertex.containsKey(v2)){
				if(!VertexToVertex.get(v2).contains(v1)){
					VertexToVertex.get(v2).add(v1);
				}
				if(!VertexToVertex.get(v2).contains(v3)){
					VertexToVertex.get(v2).add(v3);
				}
			}else{
				VertexToVertex.put(v2, new ArrayList<Vector3f>());
				VertexToVertex.get(v2).add(v1);
				VertexToVertex.get(v2).add(v3);
			}
			
			//add vertex 2 to VtoV
			if(VertexToVertex.containsKey(v3)){
				if(!VertexToVertex.get(v3).contains(v1)){
					VertexToVertex.get(v3).add(v1);
				}
				if(!VertexToVertex.get(v3).contains(v2)){
					VertexToVertex.get(v3).add(v2);
				}
			}else{
				VertexToVertex.put(v3, new ArrayList<Vector3f>());
				VertexToVertex.get(v3).add(v1);
				VertexToVertex.get(v3).add(v2);
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
		Vector3f key = Vert_Coord.get(vertexIndex);
		if(VertexToFace.containsKey(key)){
			return VertexToFace.get(key);
		}else{
			return new ArrayList<Face>();
		}
	}
	
	public Vector3f getVertexatIndex(int index){
		return Vert_Coord.get(index);
	}
	
	
	public void combineVertexIndex(int index1, int index2){
		
		
		Vector3f newVertexCoord = updateLocationVertexCombine(index1, index2);
		Vert_Coord.get(index1).x = newVertexCoord.x;
		Vert_Coord.get(index1).y = newVertexCoord.y;
		Vert_Coord.get(index1).z = newVertexCoord.z;
		
	
	}
	
	public Vector3f updateLocationVertexCombine(int index1, int index2){
		return new Vector3f(0.0f, 0.0f, 0.0f);
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

	
}

package cgg;
import java.util.*;
import tools.*;
import tools.Wavefront.MaterialData;
import tools.Wavefront.TriangleData;
import cgg.materials.*;

public class ObjectConverter {

    public static Group loadOBJModel(String filename) {

        List<tools.Wavefront.MeshData> meshdatas = tools.Wavefront.loadMeshData(filename);
        if (meshdatas.isEmpty()) {
            return null;
        }

        tools.Wavefront.MeshData wf_mesh = meshdatas.get(0);
        MaterialData wf_material = wf_mesh.material();

        IMaterial material = new TexturedPhongMaterial(
            wf_material.kdMap(),
            wf_material.ksMap(),
            wf_material.ns()
        );

        List<TriangleData> wf_triangles = wf_mesh.triangles();
        Group triangleGroup = new Group();
        
        for (TriangleData tri : wf_triangles) {
            triangleGroup.addShape(new Triangle(tri.v0(), tri.v1(), tri.v2(), material));
        }

  
        return triangleGroup;
    }
    
    public static Group loadOBJModel(String filename, IMaterial iMaterial) {

        List<tools.Wavefront.MeshData> meshdatas = tools.Wavefront.loadMeshData(filename);
        if (meshdatas.isEmpty()) {
            return null;
        }

        tools.Wavefront.MeshData wf_mesh = meshdatas.get(0);
        List<TriangleData> wf_triangles = wf_mesh.triangles();
        Group triangleGroup = new Group();

        for (TriangleData tri : wf_triangles) {
           
            Vertex v0 = tri.v0();
            Vertex v1 = tri.v1();
            Vertex v2 = tri.v2();
            triangleGroup.addShape(new Triangle(v0, v1, v2, iMaterial));
        }
        return triangleGroup;
    }
}
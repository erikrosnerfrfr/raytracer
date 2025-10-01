package cgg;
import java.util.ArrayList;
import java.util.List;
import static tools.Functions.*;
import static tools.BoundingBox.*;
import tools.Vec3;

import tools.BoundingBox;
import tools.Mat44;

public class BVH {

    List <IShape> flattenedShapes;

    public List<IShape> getFlatShapes () {
        return flattenedShapes;
    }

    public Group build(Group root) {
        collectFlattenedShapes(root);
        Group flatRoot = new Group();
        for (var shape : flattenedShapes) {
            flatRoot.addShape(shape);
        }
        return split(flatRoot);
    }

    public void collectFlattenedShapes (Group group) {
        flattenedShapes = new ArrayList<>();
        _collectShapes(group, group.transform.localToWorld());
    }

    private void _collectShapes (Group group, Mat44 parent) {

        for (var shape : group.shapes) {
            if (shape instanceof Group) {
                Group subGroup = (Group) shape;
                _collectShapes(subGroup, multiply(parent, subGroup.transform.localToWorld()));
            }

            else {
                Group finalTransformedShape = new Group(parent);
                finalTransformedShape.addShape(shape);
                flattenedShapes.add(finalTransformedShape); /////////////////// TODO: BVH Folie S38
            }
        }
    }



    public Group split (Group root) {

        if (root.shapes.size() <=2) {
            return root;}

        
            
        BoundingBox bbox = BoundingBox.around(root.getBounds());
        BoundingBox leftBox = bbox.splitLeft();
        BoundingBox rightBox = bbox.splitRight();

        Group left = new Group();
        Group right = new Group();
        List <IShape> stay = new ArrayList<>();

        //shapes zwischen subgroups verteilen
        for (var shape : root.shapes) {
            if(leftBox.contains(shape.getBounds())) {
                left.addShape(shape);
            }
            else if (rightBox.contains(shape.getBounds())){
                right.addShape(shape);
            } 
            else{stay.add(shape);}
            
        }


        //unentschiedene Shapes über Strategie verteilen

        // for (int i = 0; i < stay.size(); i++) {
        //     if (i%2==0) {left.addShape(stay.get(i));}
        //     else {right.addShape(stay.get(i));}
        // }
    //     int len = stay.size();
    //     Vec3 leftCenter = leftBox.center();
    //     Vec3 rightCenter = rightBox.center();
        
    //     if (len > 0) {
    //     for(IShape s : stay) {
    //         Vec3 center = s.getBounds().center();

    //         if (length(subtract(center, leftCenter)) < (length(subtract(center, rightCenter)))) {
    //             leftBox.extend(s.getBounds());
    //             leftCenter = leftBox.center();
    //             left.addShape(s);
    //         } else{
    //             rightBox.extend(s.getBounds());
    //             rightCenter = rightBox.center();
    //             right.addShape(s);}
    //     }
    // }


        // return null;


        //weiter aufteilen oder aufhören, wenn gut genug
        if (left.shapes.isEmpty()){
             return root;
        } 
        if(right.shapes.isEmpty()) {
            return root;
        }
        if (root.shapes.size() <=2) {
            return root;
            
        }
        if (left.shapes.size() > 2) {
            left = split(left);
        }

        if (right.shapes.size() > 2) {
            right = split(right);
        }

        root = new Group();
        root.addShape(left);
        root.addShape(right);

        for (var s : stay) {
            root.shapes.add(s);
        }

        return root;

    }

}

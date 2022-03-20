package bounce;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.Group;
import javafx.scene.shape.Sphere;

public class SphereClass {

	public static List<Sphere> spheres = new ArrayList<Sphere>();
	public static List<double[]> position = new ArrayList<double[]>();

	public static List<Sphere> setSpheres(List<Sphere> spheres, List<double[]> points, int radiusPoints, int layoutXPoints, int layoutYPoints, int layoutZPoints, double rootDeep) {
		for (int i = 0; i < points.size(); i++) {
			Sphere sphere = new Sphere();
			sphere.setId(i + "");

			sphere.setLayoutX(points.get(i)[layoutXPoints]);
			if (MainClass.addGravitation) {
				points.get(i)[layoutYPoints] = points.get(i)[layoutYPoints] + 0.1;
				sphere.setLayoutY(points.get(i)[layoutYPoints]);
				if (MainClass.simulateZAxis) {
//				sphere.setLayoutZ(points.get(i)[layoutZPoints]);
					sphere.setRadius(points.get(i)[radiusPoints] * (points.get(i)[layoutZPoints] / (rootDeep - 2 * points.get(i)[radiusPoints])));
				} else {
					sphere.setRadius(points.get(i)[radiusPoints]);
				}
				sphere.setStyle("-fx-background-color:grey");
				sphere.setOnMouseReleased(c -> {
					spheres.get(Integer.parseInt(sphere.getId())).toBack();
				});
				spheres.add(sphere);
			}
		}
		return spheres;
	}

//	public static List<Sphere> setSpheresLayout(List<Sphere> spheres, List<double[]> points, int layoutXPoints,
//			int layoutYPoints, int layoutZPoints, int radiusPoints, double rootDeep) {
//
//		for (int i = 0; i < points.size(); i++) {
//			spheres.get(i).setLayoutX(points.get(i)[layoutXPoints]);
//			spheres.get(i).setLayoutY(points.get(i)[layoutYPoints]);
////			spheres.get(i).setScaleZ(points.get(i)[layoutZPoints]);
//			spheres.get(i).setRadius(points.get(i)[radiusPoints]
//					* (points.get(i)[layoutZPoints] / (rootDeep - 2 * points.get(i)[radiusPoints])));
//		}
//		return spheres;
//	}

	public static Group addSpheres(List<Sphere> spheres, Group root) {
		root.getChildren().addAll(spheres);
		return root;
	}

	public void setLayout(List<double[]> points, int layoutXPoints, int layoutYPoints, int layoutZPoints, int radiusPoints, double rootDeep) {
		for (int i = 0; i < points.size(); i++) {
			setLayoutX(points, layoutXPoints, i);
			setLayoutY(points, layoutYPoints, i);
			if (MainClass.simulateZAxis) {
				setLayoutZ(points, layoutZPoints, i, radiusPoints, rootDeep);
			}

		}
	}

	public void setFarObjectsToBackground() {
		for (int m = MainClass.backgroundList.size() - 1; m >= 0; m--) {
			spheres.get((int) MainClass.backgroundList.get(m)[MainClass.pointID]).toFront();
//			System.out.println(MainClass.points.get((int) backgroundList.get(m)[idPoints])[2]);
		}
	}

	public void setLayoutX(List<double[]> points, int layoutXPoints, int index) {
		spheres.get(index).setLayoutX(points.get(index)[layoutXPoints]);
	}

	public void setLayoutY(List<double[]> points, int layoutYPoints, int index) {
		spheres.get(index).setLayoutY(points.get(index)[layoutYPoints]);
	}

	public void setLayoutZ(List<double[]> points, int layoutZPoints, int index, int radiusPoints, double rootDeep) {
		spheres.get(index).setRadius(points.get(index)[radiusPoints] * (points.get(index)[layoutZPoints] / (rootDeep - 2 * points.get(index)[radiusPoints])));
	}

}

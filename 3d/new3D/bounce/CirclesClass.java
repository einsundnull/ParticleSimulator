package bounce;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.Group;
import javafx.scene.shape.Circle;

public class CirclesClass  {

	public static List<Circle> circles = new ArrayList<Circle>();
	public static List<double[]> position = new ArrayList<double[]>();

	public static List<Circle> setCircles(List<Circle> Circles, List<double[]> points, int radiusPoints,
			int layoutXPoints, int layoutYPoints, int layoutZPoints, double rootDeep) {
		for (int i = 0; i < points.size(); i++) {
			Circle Circle = new Circle();
			Circle.setId(i + "");

			Circle.setLayoutX(points.get(i)[layoutXPoints]);
			Circle.setLayoutY(points.get(i)[layoutYPoints]);
			if(MainClass.simulateZAxis) {
//				Circle.setLayoutZ(points.get(i)[layoutZPoints]);
				Circle.setRadius(points.get(i)[radiusPoints]
						* (points.get(i)[layoutZPoints] / (rootDeep - 2 * points.get(i)[radiusPoints])));
			} else {
				Circle.setRadius(points.get(i)[radiusPoints]);
			}
//			if (MainClass.addGravitation) {
//				if(points.get(i)[layoutYPoints] > 0) {
//					points.get(i)[layoutYPoints] = points.get(i)[layoutYPoints] + 0.001;
//				} else {
//					if(points.get(i)[layoutYPoints] < 0) {
//						points.get(i)[layoutYPoints] = points.get(i)[layoutYPoints] - 0.001;
//					}
//				}
//				
//			}
			Circle.setStyle("-fx-background-color:grey");
			Circle.setOnMouseReleased(c -> {
				Circles.get(Integer.parseInt(Circle.getId())).toBack();
			});
			Circles.add(Circle);
		}
		return Circles;
	}



	public static Group addCircles(List<Circle> Circles, Group root) {
		root.getChildren().addAll(Circles);
		return root;
	}


	public void setLayout(List<double[]> points, int layoutXPoints, int layoutYPoints, int layoutZPoints,
			int radiusPoints, double rootDeep) {
		for (int i = 0; i < points.size(); i++) {
			setLayoutX(points, layoutXPoints, i);
			setLayoutY(points, layoutYPoints, i);
			if(MainClass.simulateZAxis) {
				setLayoutZ(points, layoutZPoints, i, radiusPoints, rootDeep);
			}

		}
	}


	public void setFarObjectsToBackground() {
		for (int m = MainClass.backgroundList.size() - 1; m >= 0; m--) {
			circles.get((int) MainClass.backgroundList.get(m)[MainClass.pointID]).toFront();
//			System.out.println(MainClass.points.get((int) backgroundList.get(m)[idPoints])[2]);
		}
	}

	
	public void setLayoutX(List<double[]> points, int layoutXPoints, int index) {
		circles.get(index).setLayoutX(points.get(index)[layoutXPoints]);
	}

	public void setLayoutY(List<double[]> points, int layoutYPoints, int index) {
		circles.get(index).setLayoutY(points.get(index)[layoutYPoints]);
	}


	public void setLayoutZ(List<double[]> points, int layoutZPoints, int index, int radiusPoints, double rootDeep) {
		circles.get(index).setRadius(points.get(index)[radiusPoints]
				* (points.get(index)[layoutZPoints] / (rootDeep - 2 * points.get(index)[radiusPoints])));
	}
	


}

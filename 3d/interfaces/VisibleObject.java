package interfaces;

import java.util.List;

public interface VisibleObject {

	public void setLayout(List<double[]> points, int layoutXPoints, int layoutYPoints, int layoutZPoints,
			int radiusPoints, double rootDeep);

	public void setFarObjectsToBackground( );

	public void setLayoutX(List<double[]> points, int layoutXPoints, int index);

	public void setLayoutY(List<double[]> points, int layoutYPoints, int index);

	public void setLayoutZ(List<double[]> points, int layoutZPoints, int index, int radiusPoints, double rootDeep);

}

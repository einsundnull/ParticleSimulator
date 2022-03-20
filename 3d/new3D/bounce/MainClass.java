package bounce;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Sphere;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class MainClass extends Application {

	static Stage stage;
	static Group root;
	static Scene scene;
	static SphereClass spheresClass = new SphereClass();
	static CirclesClass circlesClass = new CirclesClass();

	static List<Line[]> lines = new ArrayList<Line[]>();
	static List<Integer> directionsX = new ArrayList<Integer>();
	static List<Integer> directionsY = new ArrayList<Integer>();
	static List<double[]> points = new ArrayList<double[]>();
	static List<double[]> backgroundList = new ArrayList<double[]>();
	static List<List<double[]>> path = new ArrayList<List<double[]>>();
	static List<List<double[]>> pairs = new ArrayList<List<double[]>>();
	@SuppressWarnings("static-access")
	static List<Sphere> spheres = spheresClass.spheres;
	@SuppressWarnings("static-access")
	static List<Circle> circles = circlesClass.circles;
	private static short sizePoints = 13;

	private static short layoutXPoints = 0;
	private static short layoutYPoints = 1;
	private static short layoutZPoints = 2;
	private static short speedXPoints = 3;
	private static short speedYPoints = 4;
	private static short speedZPoints = 5;
	private static short directionXPoints = 6;
	private static short directionYPoints = 7;
	private static short directionZPoints = 8;
	private static short radiusPoints = 9;
	private static short vectorLengthPoints = 10;
	private static double dropValue = 0;
	private static short massPoints = 11;
	public static short idPoints = 12;

	static int pointID = 0;
	private static int pointPosition = 1;

	private static int sizePairs = 21;

	private static short startXPairs = 0;
	private static short startYPairs = 1;
	private static short startZPairs = 2;
	private static short endXPairs = 3;
	private static short endYPairs = 4;
	private static short endZPairs = 5;
	private static short speedXM = 6;
	private static short speedYM = 7;
	private static short speedZM = 8;
	private static short speedXN = 9;
	private static short speedYN = 10;
	private static short speedZN = 11;
	private static short radiusIPairs = 12;
	private static short radiusIIPairs = 13;
	private static short indexMPairs = 14;
	private static short indexNPairs = 15;
	private static short distanceXPairs = 16;
	private static short distanceYPairs = 17;
	private static short distanceZPairs = 18;
	private static short distancePairs = 19;
	private static short gravForcePairs = 20;

	private static short numberOfCircles = 40;

	private static short rootWidth = 1600;
	private static short rootHeight = 900;
	private static double rootDeep = 1600;

	private static double minRadius = 10;
	private static double maxRadius = 11;

	private static double minSpeed = -0.8;
	private static double maxSpeed = 0.8;
	private static double friction = 0.1;

	private static double mass = 100;

	private static Timer timer = new Timer(true);
	private static short speed = 2;
	private static long intervalAnimation = 50;

	private static double strokeWidth = 0.3;
	private static Paint lineColor = Color.GRAY;
	private static Color circleColor = Color.RED;

	static Label indexLabel = new Label();
	private static int labelWidht = 400;
	private static int labelHeight = 50;

	private static double circleOpacity = 0.5;

	static double sceneX = 0;

	static double addToValue = 0;
	static Sphere test = new Sphere();
	static double layoutZ = rootDeep / 2 - 100;

	static Circle testCircle = new Circle();
	static double testCircleRadius = 50;
	static double testCircleLayoutX = rootHeight - testCircleRadius;
	public static boolean addGravitation = false;
	private static boolean addFriction = false;
	public static boolean simulateZAxis = false;
	public static boolean showLinesBetweenElements = false;
	private static boolean simulateCollision = false;
	private static boolean getSceneX = true;
	private static boolean setSpheres = false;
	private static boolean setCircles = true;
	private static double minDistanceForCollision = 0;
	private static boolean addStickyForce;
	private static boolean addDrop;
	private static double downSpeed = 0;

//	static 

	@Override
	public void start(Stage arg0) throws Exception {
		setWindow();

	}

	public static void main(String[] args) {
		launch();
	}

	private static void setWindow() {

		stage = new Stage();
		root = new Group();
		root.setStyle("-fx-background-color:green");
		scene = new Scene(root, rootWidth, rootHeight);
		stage.setScene(scene);
		stage.setFullScreen(true);
		points = createPoints(points, true);
		backgroundList = createBackgroundList(backgroundList, points);
		backgroundList = orderBackgroundList(backgroundList, points);
		if (setSpheres) {
			spheres = SphereClass.setSpheres(spheres, points, radiusPoints, layoutXPoints, layoutYPoints, layoutZPoints, rootDeep);
			root = SphereClass.addSpheres(spheres, root);
		}
		if (setCircles) {
			circles = CirclesClass.setCircles(circles, points, radiusPoints, layoutXPoints, layoutYPoints, layoutZPoints, rootDeep);
			root = CirclesClass.addCircles(circles, root);
		}
		indexLabel.setVisible(false);
		pairs = createPointPairs(pairs, points);
		pairs = setLayoutToSimulatedLinesBetweenCircles(pairs, points);
		lines = createLinesBetweenCircles(lines, points);
		lines = setLayoutToLinesBetweenCircles(lines, pairs);
		circles = setCirclesOnMouseAction(circles, points);
		addLineBetweenCircles(lines);
		stage.requestFocus();
		stage.show();
//		if(showLinesBetweenElements) {
//			for(int i = 0; i < lines.size(); i++) {
//				root.getChildren().add(lines.get(i));
//			}
//			
//		}
		test.setRadius(25);
		double layoutText = rootWidth / 2 - 100;
		test.setLayoutX(layoutText);
		test.setRadius(25 * (rootDeep / layoutZ));
		root.getChildren().add(test);

		setAnimationTimer();

		scene.addEventHandler(KeyEvent.KEY_PRESSED, k -> {
			if (k.getCode().equals(KeyCode.PLUS)) {
				for (int i = 0; i < points.size(); i++) {
					points.get(i)[radiusPoints]++;
					circles.get(i).setRadius(points.get(i)[radiusPoints]);
				}
			}
			if (k.getCode().equals(KeyCode.MINUS)) {
				for (int i = 0; i < points.size(); i++) {
					points.get(i)[radiusPoints]--;
					circles.get(i).setRadius(points.get(i)[radiusPoints]);
				}
			}

			if (k.getCode().equals(KeyCode.L)) {
				showLinesBetweenElements = !showLinesBetweenElements;
				showLinesBetweenElements();
			}

			if (k.getCode().equals(KeyCode.C)) {
				simulateCollision = !simulateCollision;
			}

			if (k.getCode().equals(KeyCode.Z)) {
				simulateZAxis = !simulateZAxis;
			}
			if (k.getCode().equals(KeyCode.G)) {
				addGravitation = !addGravitation;
			}
			if (k.getCode().equals(KeyCode.D)) {
				addDrop = !addDrop;
			}
			if (k.getCode().equals(KeyCode.F)) {
				addFriction = !addFriction;
			}
			if (k.getCode().equals(KeyCode.S)) {
				addStickyForce = !addStickyForce;
				if (addStickyForce) {
					minDistanceForCollision = -10;
				} else {
					minDistanceForCollision = 0;
				}
			}

		});

		scene.addEventHandler(MouseEvent.MOUSE_DRAGGED, d -> {
			if (getSceneX) {
				getSceneX = false;
				sceneX = rootHeight / 2 - d.getSceneX();
			}
			addToValue = Math.sin((rootHeight - sceneX - d.getSceneX()) / rootHeight);
//			indexLabel.setText("" + addToValue);
//			test.setLayoutY(layoutText - layoutText * addToValue);
//			test.setRadius(25 + 25 * addToValue);
		});

		scene.addEventHandler(MouseEvent.MOUSE_RELEASED, d -> {
			getSceneX = true;
		});

		scene.addEventHandler(MouseEvent.MOUSE_CLICKED, c -> {
			c.getButton();
			if (c.getButton().equals(MouseButton.MIDDLE)) {
				if (speed != 0) {
					for (int i = 0; i < points.size(); i++) {
						points = returnDirection(points, speedXPoints, 1, i);
						points = returnDirection(points, speedYPoints, 2, i);
						points = returnDirection(points, speedZPoints, 3, i);
					}
				} 
			}		
		});

		scene.setOnScroll(c -> {
			if (c.getDeltaY() < 0) {
				speed--;
				if (speed <= 0) {
					speed = 0;
				}
			} else {
				speed++;
			}

		});
		indexLabel.setLayoutX((rootWidth - labelWidht) / 2);
		indexLabel.setLayoutY((rootHeight - labelHeight) / 2);
		indexLabel.setAlignment(Pos.CENTER);
		indexLabel.setFont(Font.font(30));
//		root.getChildren().add(indexLabel);
	}

	private static void setAnimationTimer() {
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				Platform.runLater(() -> {
					for (int i = 0; i < speed; i++) {
						makeMovement(speed, points, lines, pairs);
					}
					if(addDrop) {
						downSpeed = downSpeed - 0.001;
					}
				});
			}
		}, 1000, intervalAnimation);
	}

	private final static void makeMovement(int speed, List<double[]> points, List<Line[]> lines, List<List<double[]>> pairs) {
		if (simulateCollision) {
			points = simulateCollision(pairs, points);
		}
		MainClass.points = movePoints(points, pairs);
//		MainClass.points = movePoints(points, pairs, circles, spheres);
//		MainClass.backgroundList = orderBackgroundList(backgroundList, points);
		if (simulateCollision) {
			MainClass.pairs = setLayoutToSimulatedLinesBetweenCircles(pairs, points);
		}

		if (setSpheres) {
			spheresClass.setLayout(points, layoutXPoints, layoutYPoints, layoutZPoints, radiusPoints, rootDeep);
		}
		if (setCircles) {
			circlesClass.setLayout(points, layoutXPoints, layoutYPoints, layoutZPoints, radiusPoints, rootDeep);
		}

//		spheresClass.setFarObjectsToBackground(points, (int) idPoints);
		MainClass.lines = setLayoutToLinesBetweenCircles(lines, pairs);
	}

	private static List<double[]> movePoints(List<double[]> points, List<List<double[]>> pairs) {
		for (int i = 0; i < points.size(); i++) {
			points.get(i)[layoutXPoints] = points.get(i)[layoutXPoints] + points.get(i)[speedXPoints];
			points.get(i)[layoutYPoints] = points.get(i)[layoutYPoints] + points.get(i)[speedYPoints];
			if (simulateZAxis) {
				points.get(i)[layoutZPoints] = points.get(i)[layoutZPoints] + points.get(i)[speedZPoints];
				points = preventFromCrossingBoundsLogic(points, layoutZPoints, speedZPoints, i, rootHeight, 3, addGravitation);
			}
			if(addDrop) {
				System.out.println(points.get(0)[speedYPoints]);
				if(points.get(i)[speedYPoints]<0) {
					points.get(i)[speedYPoints] = points.get(i)[speedYPoints] + downSpeed;
				} else {
					points.get(i)[speedYPoints]= points.get(i)[speedYPoints] - downSpeed;
				}
			}
			points = preventFromCrossingBoundsLogic(points, layoutXPoints, speedXPoints, i, rootWidth, 1, addGravitation);
			points = preventFromCrossingBoundsLogic(points, layoutYPoints, speedYPoints, i, rootHeight, 2, addGravitation);

		}

		MainClass.backgroundList = orderBackgroundList(backgroundList, points);
		if (simulateZAxis) {
			if (setSpheres) {
				spheresClass.setFarObjectsToBackground();
			}
			if (setCircles) {
				circlesClass.setFarObjectsToBackground();
			}
		}

		return points;
	}

	private static double directionOfForce(double value1, double value2) {
		double directionOfForce = 1;
		if (value1 > value2) {
			directionOfForce = -1;
		}
		return directionOfForce;
	}

	private static double calculateVector(double speedX, double speedY) {
		return Math.sqrt(speedX * speedX + speedY * speedY);
	}

	private final static List<double[]> createPoints(List<double[]> points, boolean randomPosition) {
		double layoutX = -1;
		double layoutY = -1;
		double layoutZ = -1;
		double directionX = -1;
		double directionY = -1;
		double directionZ = -1;
		for (int i = 0; i < numberOfCircles; i++) {
			points.add(new double[sizePoints]);
			double radius = setRandomRadius();
			if (randomPosition) {
				layoutX = setRandomLayoutToPoints(radius, rootWidth);
				layoutY = setRandomLayoutToPoints(radius, rootHeight);
				layoutZ = setRandomLayoutToPoints(radius, rootDeep);
			} else {
				layoutX = layoutX + 100;
				layoutY = 20 + (10 * i * i);
				layoutZ = 0;
//				layoutX = 500+ 100*Math.sin(radius/(radius/i));
//				layoutY = 500+ 100*Math.sin(radius/(radius/i));
			}
			double speedX = setRandomSpeed();
			double speedY = setRandomSpeed();
			double speedZ = setRandomSpeed();
			if (speedX < 0) {
				directionX = 1;
			}
			if (speedY < 0) {
				directionY = 1;
			}
			if (speedZ < 0) {
				directionZ = 1;
			}
			points.get(i)[layoutXPoints] = layoutX;
			points.get(i)[layoutYPoints] = layoutY;
			if (simulateZAxis) {
				points.get(i)[layoutZPoints] = layoutZ;
			}
			points.get(i)[speedXPoints] = speedX;
			points.get(i)[speedYPoints] = speedY;
			points.get(i)[speedZPoints] = speedZ;
			points.get(i)[directionXPoints] = directionX;
			points.get(i)[directionYPoints] = directionY;
			if (simulateZAxis) {
				points.get(i)[directionZPoints] = directionZ;
			}
			points.get(i)[radiusPoints] = radius;
			points.get(i)[vectorLengthPoints] = calculateVector(speedX, speedY);
			points.get(i)[massPoints] = mass;
			points.get(i)[idPoints] = i;

		}
		return points;
	}

	private final static List<List<double[]>> createPointPairs(List<List<double[]>> pairs, List<double[]> points) {
		int size = points.size();
		for (int i = 0; i < size; i++) {
			ArrayList<double[]> pair = new ArrayList<double[]>();
			pairs.add(pair);
		}
		for (int i = 0; i < pairs.size(); i++) {
			for (int m = 0; m < pairs.size() - i; m++) {
				double[] pair = new double[sizePairs];
				pairs.get(i).add(pair);
				setValuesToPairs(pairs, points, i, m, (int) pairs.get(i).get(m)[indexNPairs]);
			}
		}
		return pairs;
	}

	private static List<Line[]> createLinesBetweenCircles(List<Line[]> lines, List<double[]> points) {
		int size = points.size();
		for (int i = 0; i < size; i++) {
			Line[] line = new Line[size - i];
			lines.add(line);
		}
		for (int i = 0; i < size; i++) {
			for (int n = 0; n < lines.get(i).length; n++) {
				Line line = new Line();
				line.setStrokeWidth(strokeWidth);
				line.setStroke(lineColor);
				line.setMouseTransparent(true);
				lines.get(i)[n] = line;
			}
		}
		return lines;
	}

	private static List<double[]> createBackgroundList(List<double[]> backgroundList, List<double[]> points) {
		for (int i = 0; i < numberOfCircles; i++) {
			double[] listItem = new double[2];
			listItem[pointID] = points.get(i)[idPoints];
			listItem[pointPosition] = points.get(i)[layoutZPoints];
			backgroundList.add(listItem);
//			System.out.println(backgroundList.get(i)[0]);
		}
//		System.out.println("\n");
		return backgroundList;
	}

	private final static List<double[]> orderBackgroundList(List<double[]> backgroundList, List<double[]> points) {
		for (int n = 0; n < numberOfCircles; n++) {

			for (int i = numberOfCircles - 1; i > 0; i--) {
				double layoutZI = backgroundList.get(i)[pointPosition];
				double layoutZII = backgroundList.get(i - 1)[pointPosition];
				if (layoutZI > layoutZII) {
					backgroundList.add(i - 1, backgroundList.get(i));
					backgroundList.remove(i + 1);
				}
			}
//			System.out.println(backgroundList.size());
//			System.out.println(backgroundList.get(n)[0]  + "  " + backgroundList.get(n)[1]);
		}

		return backgroundList;
	}

	private final static double setRandomRadius() {
		double radius = 30 * new Random().nextDouble() * 10;
		while (radius < minRadius || radius > maxRadius) {
			radius = 30 * new Random().nextDouble() * 10;
		}
		return radius;
	}

	private final static double setRandomSpeed() {
		double speed = 0.1;
		speed = new Random().nextDouble();
		while (speed > maxSpeed || speed < minSpeed) {
			speed = new Random().nextDouble();
		}
		return speed;
	}

	private final static double setRandomLayoutToPoints(double radius, double border) {
		double layout = border * new Random().nextDouble();
		while (layout < radius || layout > border - radius) {
			layout = border * new Random().nextDouble();
		}
		return layout;
	}

	private final static List<Circle> setCirclesOnMouseAction(List<Circle> circles, List<double[]> points) {
		for (int i = 0; i < numberOfCircles; i++) {
			Circle circle = circles.get(i);
			circle.setOnMouseClicked(c -> {
				indexLabel.setText(circle.getId());
			});
			circle.setOnMouseDragged(d -> {
				int index = Integer.parseInt(circle.getId());
				MainClass.points.get(index)[layoutXPoints] = d.getSceneX();
				MainClass.points.get(index)[layoutYPoints] = d.getSceneY();
				circle.setLayoutX(points.get(index)[layoutXPoints]);
				circle.setLayoutY(points.get(index)[layoutYPoints]);

			});
			circle.setOnMousePressed(d -> {
				int index = Integer.parseInt(circle.getId());
				MainClass.points.get(index)[layoutXPoints] = d.getSceneX();
				MainClass.points.get(index)[layoutYPoints] = d.getSceneY();
				circle.setLayoutX(d.getSceneX());
				circle.setLayoutY(d.getSceneY());

			});
			circles.remove(i);
			circles.add(i, circle);
		}
		return circles;
	}

	private static List<Line[]> setLayoutToLinesBetweenCircles(List<Line[]> lines, List<List<double[]>> pairs) {
		int size = lines.size() - 1;
		for (int i = 0; i < size; i++) {
			for (int m = 0; m < pairs.get(i).size(); m++) {
				lines.get(i)[m].setStartX(pairs.get(i).get(m)[startXPairs]);
				lines.get(i)[m].setStartY(pairs.get(i).get(m)[startYPairs]);
//				if(simulateZAxis) {
//					lines.get(i)[m].setStartZ(pairs.get(i).get(m)[startZPairs]);
//				}

				lines.get(i)[m].setEndX(pairs.get(i).get(m)[endXPairs]);
				lines.get(i)[m].setEndY(pairs.get(i).get(m)[endYPairs]);
//				if(simulateZAxis) {
////					lines.get(i)[m].setEndZ(pairs.get(i).get(m)[endZPairs]);
//				}

			}
		}
		return lines;
	}

	private static List<List<double[]>> setLayoutToSimulatedLinesBetweenCircles(List<List<double[]>> pairs, List<double[]> points) {
		int size = points.size();
		for (int i = 0; i < size; i++) {
			for (int m = 0; m < pairs.get(i).size(); m++) {
				for (int n = 1; n < pairs.get(i).size(); n++) {
					pairs = setValuesToPairs(pairs, points, i, m, n);
				}
			}
		}
		MainClass.points = points;
		return pairs;
	}

	private static List<List<double[]>> setValuesToPairs(List<List<double[]>> pairs, List<double[]> points, int i, int m, int n) {
		double startX = points.get(m)[layoutXPoints];
		double startY = points.get(m)[layoutYPoints];
		double startZ = points.get(m)[layoutZPoints];
		double endX = points.get(n)[layoutXPoints];
		double endY = points.get(n)[layoutYPoints];
		double endZ = points.get(n)[layoutZPoints];
		double distX = Math.abs(startX - endX);
		double distY = Math.abs(startY - endY);
		double distZ = Math.abs(startZ - endZ);
		double combinedRadius = 0;
		double distance = 0;
		if (simulateZAxis) {
			combinedRadius = points.get(m)[radiusPoints] * (points.get(m)[layoutZPoints] / rootDeep) + points.get(n)[radiusPoints] * (points.get(n)[layoutZPoints] / rootDeep);
			distance = Math.sqrt(distX * distX + distY * distY + distZ * distZ) - combinedRadius;
		} else {
			combinedRadius = points.get(m)[radiusPoints] + points.get(n)[radiusPoints];
			distance = Math.sqrt(distX * distX + distY * distY) - combinedRadius;
		}

		pairs.get(i).get(m)[startXPairs] = points.get(m)[layoutXPoints];
		pairs.get(i).get(m)[startYPairs] = points.get(m)[layoutYPoints];
		pairs.get(i).get(m)[startZPairs] = points.get(m)[layoutZPoints];
		pairs.get(i).get(m)[endXPairs] = points.get(n)[layoutXPoints];
		pairs.get(i).get(m)[endYPairs] = points.get(n)[layoutYPoints];
		pairs.get(i).get(m)[endZPairs] = points.get(n)[layoutZPoints];
		pairs.get(i).get(m)[radiusIPairs] = points.get(m)[radiusPoints];
		pairs.get(i).get(m)[radiusIIPairs] = points.get(n)[radiusPoints];
		pairs.get(i).get(m)[indexMPairs] = m;
		pairs.get(i).get(m)[indexNPairs] = n;
		pairs.get(i).get(m)[speedXM] = points.get(m)[speedXPoints];
		if (MainClass.addGravitation) {
			if (points.get(m)[speedYPoints] > 0) {
				points.get(m)[speedYPoints] = points.get(m)[speedYPoints] - 0.1;
			} else if (points.get(m)[speedYPoints] < 0) {
				points.get(m)[speedYPoints] = points.get(m)[speedYPoints] - 0.1;
			}
		}
		pairs.get(i).get(m)[speedYM] = points.get(m)[speedYPoints];
		pairs.get(i).get(m)[speedZM] = points.get(m)[speedZPoints];
		pairs.get(i).get(m)[speedXN] = points.get(n)[speedXPoints];
		pairs.get(i).get(m)[speedYN] = points.get(n)[speedYPoints];
		pairs.get(i).get(m)[speedZN] = points.get(n)[speedZPoints];
		pairs.get(i).get(m)[distanceXPairs] = distX;
		pairs.get(i).get(m)[distanceYPairs] = distY;
		pairs.get(i).get(m)[distanceZPairs] = distZ;
		pairs.get(i).get(m)[distancePairs] = distance;
//		if (addGravitation) {
//			pairs.get(i).get(m)[gravForcePairs] = calculateForce(m, n, points.get(m)[radiusPoints], points.get(n)[radiusPoints], distance);
//		} else {
//			pairs.get(i).get(m)[gravForcePairs] = 0;
//		}

		points.get(m)[speedXM] = points.get(m)[speedXM] + pairs.get(i).get(m)[gravForcePairs] * directionOfForce(startX, endX);
		points.get(m)[speedYM] = points.get(m)[speedYM] + pairs.get(i).get(m)[gravForcePairs] * directionOfForce(endX, startX);
		points.get(n)[speedXN] = points.get(n)[speedXN] + pairs.get(i).get(n)[gravForcePairs] * directionOfForce(startY, endY);
		points.get(n)[speedYN] = points.get(n)[speedYN] + pairs.get(i).get(n)[gravForcePairs] * directionOfForce(endY, startY);

		MainClass.points = points;
		return pairs;
	}

	private synchronized static List<double[]> simulateCollision(List<List<double[]>> pairs, List<double[]> points) {
		int size = points.size() - 1;
		for (int i = 0; i < size; i++) {
			for (int m = 0; m < pairs.get(i).size(); m++) {
				if (points.get(m)[layoutZPoints] > points.get((int) pairs.get(i).get(m)[indexNPairs])[layoutZPoints]) {
					if (setSpheres) {
						spheres.get((int) pairs.get(i).get(m)[indexNPairs]).toBack();
					}

					if (setCircles) {
						circles.get((int) pairs.get(i).get(m)[indexNPairs]).toBack();
					}

				} else {
					if (setSpheres) {
						spheres.get((int) pairs.get(i).get(m)[indexNPairs]).toFront();
					}

					if (setCircles) {
						circles.get((int) pairs.get(i).get(m)[indexNPairs]).toFront();
					}
				}
				double distance = pairs.get(i).get(m)[distancePairs];
				if (distance <= -1 ) {
					points = simulateCollisionLogic(points, i, m, (int) pairs.get(i).get(m)[indexNPairs], distance);
				}
				if(addStickyForce) {
					if (distance == -1) {
						points = simulateCollisionLogic(points, i, m, (int) pairs.get(i).get(m)[indexNPairs], distance);
						if (distance > 1) {
							points = simulateCollisionLogic(points, i, m, (int) pairs.get(i).get(m)[indexNPairs], distance);
						}
					}
				}
			}
		}
		return points;
	}

	private synchronized static List<double[]> simulateCollisionLogic(List<double[]> points, int i, int m, int n, double distance) {

		double speedMX = points.get(m)[speedXPoints];
		double speedMY = points.get(m)[speedYPoints];
		double speedMZ = points.get(m)[speedZPoints];
		double speedNX = points.get(n)[speedXPoints];
		double speedNY = points.get(n)[speedYPoints];
		double speedNZ = points.get(n)[speedZPoints];



		if (!addStickyForce) {
			points.get(n)[speedXPoints] = speedMX;
			points.get(n)[speedYPoints] = speedMY;
			points.get(n)[speedZPoints] = speedMZ;
			points.get(m)[speedXPoints] = speedNX;
			points.get(m)[speedYPoints] = speedNY;
			points.get(m)[speedZPoints] = speedNZ;
		} else {
			if(speedMX > speedNX) {
				points.get(n)[speedXPoints] = speedNX;
			} else {
				points.get(m)[speedXPoints] = speedMX;
			}
			if(speedMY > speedNY) {
				points.get(n)[speedYPoints] = speedNX;
			} else {
				points.get(m)[speedYPoints] = speedMY;
			}
			if(speedMZ > speedNZ) {
				points.get(n)[speedZPoints] = speedNX;
			} else {
				points.get(m)[speedZPoints] = speedMZ;
			}
			points.get(n)[speedXPoints] = speedNX;
			points.get(n)[speedYPoints] = speedNY;
			points.get(n)[speedZPoints] = speedNZ;
			points.get(m)[speedXPoints] = speedNX;
			points.get(m)[speedYPoints] = speedNY;
			points.get(m)[speedZPoints] = speedNZ;
		}

		return points;
	}

	private final static List<Line[]> addLineBetweenCircles(List<Line[]> lines) {

		int size = lines.size();
		for (int i = 0; i < size; i++) {
			for (int m = 0; m < lines.get(i).length; m++) {
				root.getChildren().add(lines.get(i)[m]);
			}
		}
		showLinesBetweenElements();

		return lines;
	}

	private static void showLinesBetweenElements() {
		for (int i = 0; i < lines.size(); i++) {
			for (int m = 0; m < lines.get(i).length; m++) {
				lines.get(i)[m].setVisible(showLinesBetweenElements);
			}
		}
	}

	private final static double calculateForce(double m1, double m2, double radius1, double radius2, double distance) {
		double force = 0;
		double g = 0.0055;
		double r = radius1 + radius2;
		distance = distance + r;
		force = g * ((m1 * m2) / (distance * distance));
		if (distance == 0) {
			force = g;
		}
		return force;
	}

	private static List<double[]> preventFromCrossingBoundsLogic(List<double[]> points, int layoutIndex, int speedIndex, int i, double border, int indexDirection,
			boolean addGravitation) {
		double radius = points.get(i)[radiusPoints];
		double layout = points.get(i)[layoutIndex];
		if (layout < radius || layout > border - radius) {
			if (layout <= radius) {
				points.get(i)[layoutIndex] = radius;
			} else if (layout >= border - radius) {
				points.get(i)[layoutIndex] = border - radius;
			}
			points = returnDirection(points, speedIndex, i, indexDirection);
		}
		return points;
	}

	private static List<double[]> returnDirection(List<double[]> points, int speedIndex, int i, int indexDirection) {
		points.get(i)[speedIndex] = points.get(i)[speedIndex] * (-1);
		if(addDrop) {
			if(points.get(i)[speedYPoints]<0) {
				points.get(i)[speedYPoints] = 0;
			} else {
				points.get(i)[speedYPoints]= 0;
			}
		}

//		if (addDrop && indexDirection == 2) {
//			if (points.get(i)[speedIndex] < 0) {
//				points.get(i)[speedIndex] = points.get(i)[speedIndex] -2* downSpeed;
//			} else {
//				points.get(i)[speedIndex] = points.get(i)[speedIndex] +  downSpeed;
//			} if (points.get(i)[speedIndex] == 0) {
//				points.get(i)[speedIndex] = points.get(i)[speedIndex] +2* downSpeed;
//			}
//		}
		return points;
	}

}

package main.ia;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.view.Viewer;
import org.graphstream.ui.view.ViewerListener;
import org.graphstream.ui.view.ViewerPipe;
import org.graphstream.graph.Node;
import java.awt.Color;
import java.util.ArrayDeque;
import java.util.Deque;

public class ChessPositionGraph implements ViewerListener {

    private Graph graph;

    private int edgeId;

    private String currentId;

    private Deque<String> ids;

    private Viewer viewer;

    private Position initialPosition;

    private boolean loop;

    private static String styleSheetNodeHidden = "text-size: 14;" 
	    + "   text-color: black;" + "   size-mode: dyn-size;" + "	text-mode: hidden;";

    private static String styleSheetNodeShown = "   text-size: 14;" 
	    + "   text-color: black;" + "   size-mode: dyn-size;" + "	text-mode: normal;";

    private static String styleSheetEdge = "edge {" + "text-mode: normal;" + "text-alignment: above;" + "}";

    public ChessPositionGraph() {
	graph = new SingleGraph("Chess Tree");
	System.setProperty("org.graphstream.ui", "swing");
	this.edgeId = 0;
	this.viewer = null;
    }

    public void addNodeWithScoreAndColor(Position p) {
	// Check if the node already exists before adding
	if (graph.getNode(p.getPositionId()) == null) {
	    System.out.println("Add " + p.getPositionId());
	    Node node = graph.addNode(p.getPositionId());
	    node.setAttribute("ui.label", "" + p.getMinMaxScore());
	    node.setAttribute("ui.size", Math.abs(p.getScore()) * 2 + 50); // Set the node size based on score
	    String color = scoreToColor(p.getMinMaxScore()); // Compute the color based on score
	    node.setAttribute("ui.style", "fill-color: " + color + ";"); // Set the node color based on score
	} else {
	    System.out.println("Node " + p.getPositionId() + " already exists");
	}
    }

    public void displayPosition(Position p) {
	if (viewer == null || !loop) {
	    viewer = graph.display();
	    viewer.setCloseFramePolicy(Viewer.CloseFramePolicy.HIDE_ONLY);
	    ViewerPipe viewerPipe = viewer.newViewerPipe();
	    viewerPipe.addViewerListener(this);
	    viewerPipe.addSink(graph);
	    viewer.getDefaultView().enableMouseOptions();

	    Thread viewerThread = new Thread(() -> {
		loop = true;
		while (loop) {
		    try {
			viewerPipe.blockingPump();
		    } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		    }
		}
	    });

	    // Start the separate thread
	    viewerThread.start();
	}

	this.setNewPosition(p);
	this.ids = new ArrayDeque<String>();
	this.initialPosition = p;
	currentId = p.getPositionId();
    }

    private void addMoves(Position p) {
	for (Move m : p.getMoves()) {
	    this.addNodeWithScoreAndColor(m.getPosition());
	    Edge edge = graph.addEdge("" + this.edgeId++, p.getPositionId(), m.getPosition().getPositionId());
	    edge.setAttribute("ui.label", m.getName());
	    // this.addMoves(m.getPosition());
	}
    }

    // Method to convert a score to a color (from red to green)
    private String scoreToColor(int score) {
	// Score goes from -50 (red) to 50 (green), with 0 being yellow.
	int normalizedScore = Math.max(-50, Math.min(50, score)); // Clamp score between -50 and 50

	// Calculate red, green, and blue values
	float red = (float) (50 + normalizedScore) / 100; // Red decreases as score increases
	float green = (float) (50 - normalizedScore) / 100; // Green increases as score increases
	float blue = 0; // No blue component

	// Convert to CSS-style color string (e.g., "rgb(255, 128, 0)")
	Color color = new Color(red, green, blue);
	return String.format("rgb(%d,%d,%d)", color.getRed(), color.getGreen(), color.getBlue());
    }

    @Override
    public void viewClosed(String viewName) {
	loop = false;
    }

    private Position searchPositionById(Position p, String id) {
	if (p.getPositionId().equals(id)) {
	    return p;
	} else {
	    for (Move move : p.getMoves()) {
		Position position = searchPositionById(move.getPosition(), id);
		if (position != null) {
		    return position;
		}
	    }

	}
	return null;
    }

    private void setNewPosition(Position p) {
	graph.clear();
	this.addNodeWithScoreAndColor(p);
	this.addMoves(p);

	graph.setAttribute("ui.stylesheet", "node {" + styleSheetNodeHidden+"}"+styleSheetEdge);
    }

    @Override
    public void buttonPushed(String id) {
	if (id.equals(currentId)) {
	    if (!ids.isEmpty()) {
		currentId = ids.pop();
		this.setNewPosition(this.searchPositionById(initialPosition, currentId));
	    }
	} else {
	    ids.add(currentId);
	    currentId = id;
	    this.setNewPosition(this.searchPositionById(initialPosition, currentId));
	}
    }

    @Override
    public void buttonReleased(String id) {
	// TODO Auto-generated method stub

    }

    @Override
    public void mouseOver(String id) {
	Node node = graph.getNode(id);
	if(node!=null)
	    node.setAttribute("ui.style", styleSheetNodeShown);
    }

    @Override
    public void mouseLeft(String id) {
	Node node = graph.getNode(id);
	if(node!=null)
	    node.setAttribute("ui.style", styleSheetNodeHidden);
    }
}

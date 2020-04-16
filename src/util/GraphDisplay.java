package util;

import java.awt.Dimension;

import javax.swing.JApplet;

import org.jgrapht.ListenableGraph;
import org.jgrapht.ext.JGraphXAdapter;

import com.mxgraph.layout.mxCircleLayout;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxConstants;

/**
 * TODO: could probably remove the Applet middleman here and just use a JFrame or JPanel.
 * @author nrowell
 *
 * @param <V>
 * @param <E>
 */
public class GraphDisplay<V, E> extends JApplet {
	
	private static final long serialVersionUID = 1L;
	private final Dimension DEFAULT_SIZE = new Dimension(1000, 1000);
	mxCircleLayout layout;
	mxGraphComponent component;
	JGraphXAdapter<V, E> jgxAdapter;
	
	public GraphDisplay(ListenableGraph<V, E> graph) {
		jgxAdapter = new JGraphXAdapter<V, E>(graph);
		jgxAdapter.getStylesheet().getDefaultEdgeStyle().put(mxConstants.STYLE_NOLABEL, "1");
		
		init();
	}
	
	public void beginUpdate() {
		component.getGraph().getModel().beginUpdate();
	}
	
	public void endUpdate() {
		component.getGraph().getModel().endUpdate();
		layout.execute(jgxAdapter.getDefaultParent());
	}
	
	@Override
    public void init()
    {
        setPreferredSize(DEFAULT_SIZE);
        component = new mxGraphComponent(jgxAdapter);
        component.setConnectable(false);
        component.getGraph().setAllowDanglingEdges(false);
        getContentPane().add(component);
        resize(DEFAULT_SIZE);

        // positioning via jgraphx layouts
        layout = new mxCircleLayout(jgxAdapter);

        // Center the circle
        int radius = 100;
        layout.setX0((DEFAULT_SIZE.width / 2.0) - radius);
        layout.setY0((DEFAULT_SIZE.height / 2.0) - radius);
        layout.setRadius(radius);
        layout.setMoveCircle(true);

        layout.execute(jgxAdapter.getDefaultParent());
    }
}

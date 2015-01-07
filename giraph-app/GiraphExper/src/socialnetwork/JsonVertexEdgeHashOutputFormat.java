package socialnetwork;

import java.io.IOException;

import org.apache.giraph.edge.Edge;
import org.apache.giraph.graph.Vertex;
import org.apache.giraph.io.formats.TextVertexOutputFormat;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.json.JSONArray;
import org.json.JSONException;
import org.apache.log4j.Logger;

public class JsonVertexEdgeHashOutputFormat extends
		TextVertexOutputFormat<LongWritable, DoubleWritable, FloatWritable> {
	protected static Logger log = Logger.getLogger(JsonVertexEdgeHashOutputFormat.class);

	@Override
	public TextVertexWriter createVertexWriter(TaskAttemptContext context) {
		return new JsonVertexEdgeHashWriter();
	}

	private class JsonVertexEdgeHashWriter extends TextVertexWriterToEachLine {

		public Text convertVertexToLine(
				Vertex<LongWritable, DoubleWritable, FloatWritable> vertex)
				throws IOException {
			log.info("About to write a line.");
			JSONArray jsonVertex = new JSONArray();
			try {
				jsonVertex.put(vertex.getId().get());
				jsonVertex.put(vertex.getValue().get());
				JSONArray jsonEdgeArray = new JSONArray();
				for (Edge<LongWritable, FloatWritable> edge : vertex.getEdges()) {
					JSONArray jsonEdge = new JSONArray();
					jsonEdge.put(edge.getTargetVertexId().get());
					jsonEdge.put(edge.getValue().get());
					jsonEdgeArray.put(jsonEdge);
				}
				jsonVertex.put(jsonEdgeArray);
			} catch (JSONException e) {
				throw new IllegalArgumentException(
						"writeVertex: Couldn't write vertex " + vertex);
			}
			return new Text(jsonVertex.toString());
		}

	}
}

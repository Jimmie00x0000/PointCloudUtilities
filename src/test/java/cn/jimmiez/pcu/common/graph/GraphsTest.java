package cn.jimmiez.pcu.common.graph;

import cn.jimmiez.pcu.common.graphics.Octree;
import org.junit.Test;

import javax.vecmath.Point3d;
import java.util.List;
import java.util.Random;
import java.util.Vector;

import static cn.jimmiez.pcu.common.graph.Graph.N;

import static org.junit.Assert.*;

public class GraphsTest {

    @Test
    public void knnGraphTest() {
        List<Point3d> vertices = new Vector<>();
        vertices.add(new Point3d(0, 0, 0));
        vertices.add(new Point3d(0, 0, 1));
        vertices.add(new Point3d(0, 1, 0));
        vertices.add(new Point3d(0, 1, 1));
        vertices.add(new Point3d(1, 0, 0));
        vertices.add(new Point3d(1, 0, 1));
        vertices.add(new Point3d(1, 1, 0));
        vertices.add(new Point3d(1, 1, 1));
        Octree octree = new Octree();
        octree.buildIndex(vertices);
        List<int[]> nnIndices = new Vector<>();
        for (int i = 0; i < vertices.size(); i ++)
            nnIndices.add(octree.searchNearestNeighbors(2, i));
        Graph knnGraph = Graphs.knnGraph(vertices, nnIndices);
        assertEquals(1, knnGraph.edgeWeight(0, 1),1e-7);
        assertEquals(Double.POSITIVE_INFINITY, knnGraph.edgeWeight(0, 7),1e-7);
    }

    @Test
    public void connectedComponentTest() {
        Graph graph = genData();
        List<List<Integer>> conns = Graphs.connectedComponents(graph);
        assertTrue(conns.size() == 2);

        Graph graph2 = genData2();
        conns = Graphs.connectedComponents(graph2);
        assertTrue(conns.size() == 3);

        conns = Graphs.connectedComponents(Graphs.empty());
        assertTrue(conns.size() == 0);

        Graph fcg = genData3();
        conns = Graphs.connectedComponents(fcg);
        assertTrue(conns.size() == 1);
    }

    private Graph genData() {
        final double[][] edges = new double[][] {
                {0,   3,   2,   N,   4,   N,   N},
                {3,   0,   0.5, 2,   0.8, N,   N},
                {2,   0.5, 0,   1,   N,   N,   N},
                {N,   2,   1,   0,   2,   N,   N},
                {4,   0.8, N,   2,   0,   N,   N},
                {N,   N,   N,   N,   N,   0,   0.3},
                {N,   N,   N,   N,   N,   0.3, 0},
        };
        final int[][] adjacency = new int[][] {
                {1, 2, 4},
                {0, 2, 3, 4},
                {0, 1, 3},
                {1, 2, 4},
                {0, 1, 3},
                {6},
                {5},
        };
        return Graphs.graph(edges, adjacency);
    }

    private Graph genData2() {
        final double[][] edges = new double[][] {
                {0,   N,  N},
                {N,   0,  N},
                {N,   N,  0},
        };
        final int[][] adjacency = new int[][] {
                {},
                {},
                {},
        };
        return Graphs.graph(edges, adjacency);
    }

    private Graph genData3() {
        Random random = new Random(System.currentTimeMillis());
        List<Point3d> vertices = new Vector<>();
        for (int i = 0; i < 3000; i ++) {
            vertices.add(new Point3d(random.nextDouble(), random.nextDouble(), random.nextDouble()));
        }
        return Graphs.fullConnectedGraph(vertices);
    }
}
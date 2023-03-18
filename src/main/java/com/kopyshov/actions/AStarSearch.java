package com.kopyshov.actions;

import com.kopyshov.model.ActionMapModel;
import com.kopyshov.model.GraphNode;

import java.util.*;

public class AStarSearch implements PathFinder{
    private final ActionMapModel grid;
    private final int width;
    private final int height;
    private final GraphNode start;
    private final GraphNode goal;
    Map<GraphNode, GraphNode> cameFrom = new HashMap<>();
    Map<GraphNode, Integer> gScore = new HashMap<>();
    Map<GraphNode, Integer> fScore = new HashMap<>();

    public AStarSearch(ActionMapModel grid, GraphNode start, GraphNode goal) {
        this.grid = grid;
        this.width = grid.getWidth();
        this.height = grid.getHeight();
        this.start = start;
        this.goal = goal;
    }

    public LinkedList<GraphNode> findPath() {
        Comparator<GraphNode> comparator = LengthComparator(start);
        PriorityQueue<GraphNode> openList = new PriorityQueue<>(comparator);
        openList.add(start);
        gScore.put(start, 0);
        fScore.put(start, heuristicCostEstimate(start, goal));
        while (!openList.isEmpty()) {
            GraphNode current = openList.poll();
            if (current.equals(goal)) {
                return reconstructPath(cameFrom, current);
            }
            List<GraphNode> neighbors = getNeighbors(current);
            for (GraphNode neighbor : neighbors) {
                int tentativeGScore = gScore.get(current) + cost(current, neighbor);
                if (!gScore.containsKey(neighbor) || tentativeGScore < gScore.get(neighbor)) {
                    cameFrom.put(neighbor, current);
                    gScore.put(neighbor, tentativeGScore);
                    fScore.put(neighbor, tentativeGScore + heuristicCostEstimate(neighbor, goal));
                    if (!openList.contains(neighbor)) {
                        openList.add(neighbor);
                    }
                }
            }
        }
        return null;
    }

    public static Comparator<GraphNode> LengthComparator(GraphNode start) {
        Comparator<GraphNode> comparator = (o1, o2) -> {
            int o1Length = Math.abs((start.x()) - o1.x()) + Math.abs(start.y() - o1.y());
            int o2Length = Math.abs(start.x() - o2.x()) + Math.abs(start.y() - o2.y());
            return Integer.compare(o1Length, o2Length);
        };
        return comparator;
    }

    private List<GraphNode> getNeighbors(GraphNode node) {
        int x = node.x();
        int y = node.y();
        List<GraphNode> neighbors = new ArrayList<>();
        //left
        if (x > 0 && isWalkableAt(x-1, y)) {
            neighbors.add(new GraphNode(x-1, y));
        }

        //right
        if (x < width-1 && isWalkableAt(x+1, y)) {
            neighbors.add(new GraphNode(x+1, y));
        }

        //down
        if (y > 0 && isWalkableAt(x, y-1)) {
            neighbors.add(new GraphNode(x, y-1));
        }

        //up
        if (y < height-1 && isWalkableAt(x, y+1)) {
            neighbors.add(new GraphNode(x, y+1));
        }

        //left-down
        if(x > 0 && y > 0 && isWalkableAt(x-1, y-1)) {
            neighbors.add(new GraphNode(x-1, y-1));
        }

        //left-up
        if(x > 0 && y < height-1 && isWalkableAt(x-1, y+1)) {
            neighbors.add(new GraphNode(x-1, y+1));
        }

        //right-down
        if(x < width-1 && y > 0 && isWalkableAt(x+1, y-1)) {
            neighbors.add(new GraphNode(x+1, y-1));
        }

        //right-up
        if(x < width-1 && y < height-1 && isWalkableAt(x+1, y+1)) {
            neighbors.add(new GraphNode(x+1, y+1));
        }

        return neighbors;
    }

    private int cost(GraphNode a, GraphNode b) {
        //Здесь можно внедрить формулу стоимости перехода из одной клетки в другую
        //Но так как в симуляции все клетки одинаковые то стоимость возвращаетс одна ита же
        //Так можно было бы конечно добавить стоимость преодоления преград (вода)
        return 1;
    }

    private int heuristicCostEstimate(GraphNode a, GraphNode b) {
        return Math.abs(a.x() - b.x()) + Math.abs(a.y() - b.y());
    }

    private LinkedList<GraphNode> reconstructPath(Map<GraphNode, GraphNode> cameFrom, GraphNode current) {
        LinkedList<GraphNode> path = new LinkedList<>();
        path.add(current);
        while (cameFrom.containsKey(current)) {
            current = cameFrom.get(current);
            path.addFirst(current);
        }
        return path;
    }

    private boolean isWalkableAt(int x, int y) {
        boolean walkable = true;
        if(goal.equals(new GraphNode(x, y))) {
            return true;
        } else if (x >= 0 && (x < grid.getWidth()) && (y >= 0 && y < grid.getHeight())) {
            GraphNode checkPos = new GraphNode(x, y);
            if (grid.getObstacles().containsKey(checkPos)) {
                walkable = false;
            } else if (grid.getCarnivores().containsKey(checkPos)) {
                walkable = false;
            } else if (grid.getHerbivores().containsKey(checkPos)) {
                walkable = false;
            }
        } else {
            walkable = false;
        }
        return walkable;
    }
}

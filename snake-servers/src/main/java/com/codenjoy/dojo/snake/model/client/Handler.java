package com.codenjoy.dojo.snake.model.client;

import com.codenjoy.dojo.services.Direction;
import lombok.SneakyThrows;

import java.util.*;

import static com.codenjoy.dojo.snake.model.client.StayAlive.stayAlive;

public class Handler {

    private Handler() {
    }

    @SneakyThrows
    public static Direction getDirectionFromMatrix(int[][] matrix, int startPointX, int startPointY, int endPointX, int endPointY) {
        List<PathPoint> path = getPath(matrix, startPointX, startPointY, endPointX, endPointY);
        if (path == null || path.isEmpty()) {
            return stayAlive(matrix, startPointX, startPointY);
        }
        PathPoint nextPoint = path.get(1);
        return getDirection(startPointX, startPointY, nextPoint.x, nextPoint.y);
    }

    private static List<PathPoint> getPath(int[][] matrix, int startPointX, int startPointY, int endPointX, int endPointY) {
        int[][] visited = new int[matrix.length][matrix[0].length];
        int[] dx = {0, 0, 1, -1};
        int[] dy = {1, -1, 0, 0};

        Queue<PathPoint> queue = new PriorityQueue<>();
        queue.add(new PathPoint(startPointX, startPointY));
        visited[startPointX][startPointY] = 1;
        while (!queue.isEmpty()) {
            PathPoint point = queue.poll();
            if (point.x == endPointX && point.y == endPointY) {
                point.path.add(point);
                return point.path;
            }
            for (int i = 0; i < 4; i++) {
                int newX = point.x + dx[i];
                int newY = point.y + dy[i];
                if (isValid(matrix, visited, newX, newY)) {
                    visited[newX][newY] = 1;
                    PathPoint newPoint = new PathPoint(newX, newY);
                    newPoint.path.addAll(point.path);
                    newPoint.path.add(point);
                    queue.add(newPoint);
                }
            }
        }
        return Collections.emptyList();
    }



    private static boolean isValid(int[][] schema, int[][] visited, int x, int y) {
        return x >= 0 && x < schema.length && y >= 0 && y < schema[0].length && schema[x][y] == 0 && visited[x][y] == 0;
    }

    private static Direction getDirection(int startPointX, int startPointY, int endPointX, int endPointY) {
        if (startPointX == endPointX) {
            if (startPointY < endPointY) {
                return Direction.UP;
            } else {
                return Direction.DOWN;
            }
        } else {
            if (startPointX < endPointX) {
                return Direction.RIGHT;
            } else {
                return Direction.LEFT;
            }
        }
    }

    private static class PathPoint {
        int x;
        int y;
        LinkedList<PathPoint> path;
        public PathPoint(int x, int y) {
            this.x = x;
            this.y = y;
            path = new LinkedList<>();
        }

        @Override
        public String toString() {
            return "(" + x + ", " + y + ")";
        }

    }

}

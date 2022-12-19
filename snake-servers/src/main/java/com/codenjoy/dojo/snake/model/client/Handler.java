package com.codenjoy.dojo.snake.model.client;

import com.codenjoy.dojo.services.Direction;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Handler {

    private Handler() {
    }

    public static Direction getDirectionFromMatrix(int[][] matrix, int startX, int startY, int endX, int endY) {
        try {
            List<PathPoint> path = getPath(matrix, startX, startY, endX, endY);
            if (path == null || path.isEmpty()) {
                return stayAlive(matrix, startX, startY);
            }

            PathPoint nextPoint = path.get(1);
            return getDirection(startX, startY, nextPoint.x, nextPoint.y);
        } catch (Exception e) {
            e.printStackTrace();
            return stayAlive(matrix, startX, startY);
        }
    }

    private static List<PathPoint> getPath(int[][] matrix, int startX, int startY, int endX, int endY) {
        int[][] visited = new int[matrix.length][matrix[0].length];
        int[] dx = {0, 0, 1, -1};
        int[] dy = {1, -1, 0, 0};

        Queue<PathPoint> queue = new LinkedList<>();
        queue.add(new PathPoint(startX, startY));
        visited[startX][startY] = 1;
        while(!queue.isEmpty()){
            PathPoint point = queue.poll();
            if(point.x == endX && point.y == endY){
                point.path.add(point);
                return point.path;
            }
            for(int i = 0; i < 4; i++){
                int newX = point.x + dx[i];
                int newY = point.y + dy[i];
                if(isValid(matrix, visited, newX, newY)){
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
    
    private static Direction stayAlive(int[][] matrix, int startX, int startY){
        if (matrix[startX+1][startY] != 1) {
            return Direction.RIGHT;
        } else if (matrix[startX-1][startY] != 1) {
            return Direction.LEFT;
        } else if (matrix[startX][startY+1] != 1) {
            return Direction.UP;
        } else if (matrix[startX][startY-1] != 1) {
            return Direction.DOWN;
        }
        return null;
    }

    private static boolean isValid(int[][] schema, int[][] visited, int x, int y){
        return x >= 0 && x < schema.length && y >= 0 && y < schema[0].length && schema[x][y] == 0 && visited[x][y] == 0;
    }

    private static Direction getDirection(int startX, int startY, int endX, int endY) {
        if (startX == endX) {
            if (startY < endY) {
                return Direction.UP;
            } else {
                return Direction.DOWN;
            }
        } else {
            if (startX < endX) {
                return Direction.RIGHT;
            } else {
                return Direction.LEFT;
            }
        }
    }

    private static class PathPoint {

        int x;
        int y;
        List<PathPoint> path;
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

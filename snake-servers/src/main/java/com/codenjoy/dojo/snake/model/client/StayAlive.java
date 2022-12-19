package com.codenjoy.dojo.snake.model.client;

import com.codenjoy.dojo.services.Direction;

public class StayAlive {

    public static Direction stayAlive(int[][] matrix, int startPointX, int startPointY) {
        if (matrix[startPointX + 1][startPointY] != 1) {
            return Direction.RIGHT;
        } else if (matrix[startPointX - 1][startPointY] != 1) {
            return Direction.LEFT;
        } else if (matrix[startPointX][startPointY + 1] != 1) {
            return Direction.UP;
        } else if (matrix[startPointX][startPointY - 1] != 1) {
            return Direction.DOWN;
        }
        return null;
    }
}

package com.codenjoy.dojo.snake.model.client;

/*-
 * #%L
 * Codenjoy - it's a dojo-like platform from developers to developers.
 * %%
 * Copyright (C) 2018 Codenjoy
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */


import com.codenjoy.dojo.client.Solver;
import com.codenjoy.dojo.client.WebSocketRunner;
import com.codenjoy.dojo.services.Dice;
import com.codenjoy.dojo.services.Direction;
import com.codenjoy.dojo.services.Point;
import com.codenjoy.dojo.services.RandomDice;

import java.util.List;

/**
 * User: Shirali Alihummatov
 */
public class YourSolver implements Solver<Board> {

    private Dice dice;
    private Board board;

    public YourSolver(Dice dice) {
        this.dice = dice;
    }

    public void setUp(Board board) {
        this.board = board;
    }

    public Direction getDirection(Board board) {
        Point head = board.getHead();
        Point apple = board.getApples().get(0);

        if (apple == null || head == null) {
            return Direction.UP;
        }
        int[][] matrix = buildSchema();

        return Handler.getDirectionFromMatrix(matrix,
                head.getX(), head.getY(), apple.getX(), apple.getY());
    }

    private int[][] buildSchema() {
        List<Point> stones = board.getStones();
        List<Point> snake = board.getSnake();

        int[][] schema = new int[15][15];
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                schema[i][j] = 0;
            }
        }
        for (int i = 0; i < 15; i++){
            schema[0][i] = 1;
            schema[14][i] = 1;
            schema[i][0] = 1;
            schema[i][14] = 1;
        }
        for (Point stone : stones) {
            schema[stone.getX()][stone.getY()] = 1;
        }
        for (Point point : snake) {
            schema[point.getX()][point.getY()] = 1;
        }
        return schema;
    }

    @Override
    public String get(Board board) {
        this.board = board;
        System.out.println(board.toString());

        return getDirection(board).toString();
    }

    public static void main(String[] args) {
        WebSocketRunner.runClient(
                // paste here board page url from browser after registration
                "http://159.89.27.106/codenjoy-contest/board/player/d6sn2iadg0oqtbv1gd1v?code=6258791996257775641",
                new YourSolver(new RandomDice()),
                new Board());
    }

}

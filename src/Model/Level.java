package Model;

import java.util.HashMap;
import java.util.Map;

public class Level {
    private static Map<String, Integer[]> levels = new HashMap<>();
    private int rows = 8;
    private int cols = 8;
    private int mines = 10;

    static{
        levels.put("beginner", new Integer[]{8, 8, 10});
        levels.put("medium", new Integer[]{16, 16, 40});
        levels.put("expert", new Integer[]{16, 40, 99});
    }


    public Level(int rows, int cols, int mines){
        this.rows = rows;
        this.cols = cols;
        this.mines = mines;
    }

    public Level(String level){
        level =  level.toLowerCase();
        Integer[] params = levels.get(level);
        this.rows = params[0];
        this.cols = params[1];
        this.mines = params[2];
    }

    public int getRows() {
        return rows;
    }

    public int getCols(){
        return cols;
    }

    public int getMines(){
        return mines;
    }
}

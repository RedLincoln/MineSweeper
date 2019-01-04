package Model;

import java.util.HashMap;
import java.util.Map;

public class Level {
    private static final Map<String, Integer[]> standardLevels = new HashMap<>();
    private final int rows;
    private final int cols;
    private final int mines;

    static{
        standardLevels.put("beginner", new Integer[]{8, 8, 10});
        standardLevels.put("medium", new Integer[]{16, 16, 40});
        standardLevels.put("expert", new Integer[]{16, 40, 99});
    }


    public Level(int rows, int cols, int mines){
        this.rows = rows;
        this.cols = cols;
        this.mines = mines;
    }

    public Level(String level){
        level =  level.toLowerCase();
        Integer[] params = standardLevels.get(level);
        this.rows = params[0];
        this.cols = params[1];
        this.mines = params[2];
    }

    public int getRows(){
        return rows;
    }

    public int getCols(){
        return cols;
    }

    public int getMines(){
        return mines;
    }
}

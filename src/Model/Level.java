package Model;

import java.util.HashMap;
import java.util.Map;

public class Level {
    private static Map<String, Integer[]> levelMap =  new HashMap<>();
    private int rows;
    private int cols;
    private int mines;

    static {
        levelMap.put("beginner", new Integer[]{8,8,10});
        levelMap.put("medium", new Integer[]{16,16,40});
        levelMap.put("expert", new Integer[]{16,30,99});
    }

    public Level(String level){
        level = level.toLowerCase();
        if (checkStandardLevels(level)){
            Integer[] details = levelMap.get(level);
            rows = details[0];
            cols = details[1];
            mines = details[2];
        }
    }

    public static boolean checkStandardLevels(String level){
        return levelMap.containsKey(level);
    }
    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public int getMines() {
        return mines;
    }

    public Level(int rows, int cols, int mones) {
        this.rows = rows;
        this.cols = cols;
        this.mines = mones;
    }
}

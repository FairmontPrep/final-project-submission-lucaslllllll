import java.util.*;

public class PathFinder {

    static int[][] A = {
        {0, 0, 0, 0, 0, 0},
        {0, 1, 1, 1, 0, 0},
        {0, 0, 0, 1, 0, 0},
        {0, 0, 0, 1, 1, 1},
        {0, 0, 0, 0, 0, 1},
    };

    static boolean[][] visited;
    static ArrayList<String> answerList = new ArrayList<>();

    public static void main(String[] args) {
        run();
    }

    public static void run() {
        visited = new boolean[A.length][A[0].length];
        int[] start = findStart();
        if (start != null) {
            dfs(start[0], start[1], "");
            Collections.reverse(answerList);
            System.out.println(answerList);
            printPath();
        } else {
            System.out.println("No valid start point found.");
        }
    }

    public static int[] findStart() {
        int rows = A.length;
        int cols = A[0].length;

        for (int i = 0; i < rows; i++) {
            if (A[i][0] == 1) return new int[]{i, 0};
            if (A[i][cols - 1] == 1) return new int[]{i, cols - 1};
        }
        for (int j = 0; j < cols; j++) {
            if (A[0][j] == 1) return new int[]{0, j};
            if (A[rows - 1][j] == 1) return new int[]{rows - 1, j};
        }
        return null;
    }

    public static boolean dfs(int i, int j, String prevDir) {
        if (i < 0 || j < 0 || i >= A.length || j >= A[0].length) return false;
        if (A[i][j] != 1 || visited[i][j]) return false;

        visited[i][j] = true;

        boolean deadEnd = true;

        int[][] dirs = {
            {-1, 0,}, 
            {1, 0},   
            {0, -1},  
            {0, 1}    
        };
        String[] dirNames = {"up", "down", "left", "right"};

        for (int d = 0; d < 4; d++) {
            int ni = i + dirs[d][0];
            int nj = j + dirs[d][1];
            if (ni >= 0 && nj >= 0 && ni < A.length && nj < A[0].length
                && A[ni][nj] == 1 && !visited[ni][nj]) {
                if (dfs(ni, nj, dirNames[d])) {
                    answerList.add("A[" + i + "][" + j + "]");
                    return true;
                }
                deadEnd = false;
            }
        }

        if (deadEnd) {
            answerList.add("A[" + i + "][" + j + "]");
            return true;
        }

        return false;
    }

    public static void printPath() {
        char[][] display = new char[A.length][A[0].length];
        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < A[0].length; j++) {
                display[i][j] = ' ';
            }
        }

        for (String s : answerList) {
            int i = Integer.parseInt(s.substring(s.indexOf('[') + 1, s.indexOf(']')));
            int j = Integer.parseInt(s.substring(s.lastIndexOf('[') + 1, s.lastIndexOf(']')));
            display[i][j] = '1';
        }

        for (char[] row : display) {
            for (char c : row) {
                System.out.print("[ " + c + " ]");
            }
            System.out.println();
        }
    }
}

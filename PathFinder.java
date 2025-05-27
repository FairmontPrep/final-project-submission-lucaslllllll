import java.util.*;

public class PathFinder {

    static int[][] A; 
    static List<int[]> finalPath; 

    public static void main(String[] args) {
        run(); 
    }

    public static void run() {
        A = convertToArray(Client.test_array_2);
        
        List<int[]> startPoints = findStartPoints(); 
        
        for (int[] start : startPoints) {
            List<int[]> path = bfs(start); 
            if (path != null && isValidPath(path, start)) {
                buildAnswerList(path); 
                printPath(); 
                return;
            }
        }
        System.out.println("No valid path found.");
    }

    private static int[][] convertToArray(ArrayList<ArrayList<Integer>> list) {
        int rows = list.size();
        int cols = list.get(0).size();
        int[][] arr = new int[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                arr[i][j] = list.get(i).get(j);
            }
        }
        return arr;
    }

    private static List<int[]> findStartPoints() {
        List<int[]> starts = new ArrayList<>();
        int rows = A.length;
        int cols = A[0].length;
        
        for (int i = 0; i < rows; i++) {
            if (A[i][0] == 1) starts.add(new int[]{i, 0});
            if (A[i][cols-1] == 1) starts.add(new int[]{i, cols-1});
        }
        
        for (int j = 0; j < cols; j++) {
            if (A[0][j] == 1) starts.add(new int[]{0, j});
            if (A[rows-1][j] == 1) starts.add(new int[]{rows-1, j});
        }
        return starts;
    }

    private static List<int[]> bfs(int[] start) {
        int rows = A.length;
        int cols = A[0].length;
        boolean[][] visited = new boolean[rows][cols];
        Queue<int[]> queue = new LinkedList<>();
        Map<String, int[]> parent = new HashMap<>(); 
        
        queue.add(start);
        visited[start[0]][start[1]] = true;
        parent.put(start[0]+","+start[1], null);

        int[][] dirs = {{-1,0}, {1,0}, {0,-1}, {0,1}}; 

        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            
            if (isValidExit(current, start)) {
                return buildPath(parent, current);
            }
            
            for (int[] dir : dirs) {
                int nx = current[0] + dir[0];
                int ny = current[1] + dir[1];
                
                if (nx >= 0 && nx < rows && ny >= 0 && ny < cols 
                    && A[nx][ny] == 1 && !visited[nx][ny]) {
                    visited[nx][ny] = true;
                    queue.add(new int[]{nx, ny});
                    parent.put(nx+","+ny, current);
                }
            }
        }
        return null;
    }

    private static boolean isValidExit(int[] point, int[] start) {
        if (!isOnEdge(point)) return false; 
        String startEdge = getEdge(start);
        String endEdge = getEdge(point);
        if (startEdge == null || endEdge == null) return false;
        
        return (startEdge.equals("LEFT") || startEdge.equals("RIGHT")) 
            ? (endEdge.equals("TOP") || endEdge.equals("BOTTOM"))
            : (endEdge.equals("LEFT") || endEdge.equals("RIGHT"));
    }

    private static boolean isOnEdge(int[] point) {
        int x = point[0], y = point[1];
        return x == 0 || x == A.length - 1 || y == 0 || y == A[0].length - 1;
    }

    private static String getEdge(int[] point) {
        int x = point[0], y = point[1];
        if (y == 0) return "LEFT";
        if (y == A[0].length-1) return "RIGHT";
        if (x == 0) return "TOP";
        if (x == A.length-1) return "BOTTOM";
        return null;
    }

    private static List<int[]> buildPath(Map<String, int[]> parent, int[] end) {
        List<int[]> path = new ArrayList<>();
        int[] current = end;
        while (current != null) {
            path.add(current);
            current = parent.get(current[0]+","+current[1]);
        }
        Collections.reverse(path);
        return path;
    }

    private static boolean isValidPath(List<int[]> path, int[] start) {
        if (path.size() < 2) return false;
        if (!isValidExit(path.get(path.size()-1), start)) return false;
        return has90DegreeTurn(path);
    }

    private static boolean has90DegreeTurn(List<int[]> path) {
        String prevDir = getDirection(path.get(0), path.get(1));
        for (int i = 2; i < path.size(); i++) {
            String currDir = getDirection(path.get(i-1), path.get(i));
            if (!currDir.equals(prevDir) && isPerpendicular(prevDir, currDir)) {
                return true;
            }
            prevDir = currDir;
        }
        return false;
    }

    private static String getDirection(int[] from, int[] to) {
        int dx = to[0] - from[0];
        int dy = to[1] - from[1];
        if (dx == -1) return "UP";
        if (dx == 1) return "DOWN";
        if (dy == -1) return "LEFT";
        if (dy == 1) return "RIGHT";
        return "";
    }

    private static boolean isPerpendicular(String dir1, String dir2) {
        return (dir1.matches("UP|DOWN") && dir2.matches("LEFT|RIGHT")) ||
               (dir1.matches("LEFT|RIGHT") && dir2.matches("UP|DOWN"));
    }

    private static void buildAnswerList(List<int[]> path) {
        finalPath = path;
        ArrayList<String> answerList = new ArrayList<>();
        for (int[] p : path) {
            answerList.add("A["+p[0]+"]["+p[1]+"]");
        }
        System.out.println("Valid Path Coordinates:");
        System.out.println(answerList);
    }

    private static void printPath() {
        System.out.println("\nPath Visualization:");
        char[][] grid = new char[A.length][A[0].length];
        for (char[] row : grid) Arrays.fill(row, ' ');
        
        for (int[] p : finalPath) {
            grid[p[0]][p[1]] = '1';
        }
        
        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < A[0].length; j++) {
                if (A[i][j] != 1) grid[i][j] = ' '; 
                System.out.print(grid[i][j] == '1' ? "[ 1 ]" : "[   ]");
            }
            System.out.println();
        }
    }
}
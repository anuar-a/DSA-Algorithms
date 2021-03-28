import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.FordFulkerson;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

import java.util.HashMap;

public class BaseballElimination {
    // private String[] teams;
    private HashMap<String, Integer> teams;
    private HashMap<Integer, String> teamsReverse;
    private HashMap<String, Bag<String>> subsets = new HashMap<String, Bag<String>>();
    private int[] w;
    private int[] l;
    private int[] r;
    private int[][] g;
    private int n;

    // create a baseball division from given filename in format specified below
    public BaseballElimination(String filename) {
        In in = new In(filename);
        n = Integer.parseInt(in.readLine());
        // StdOut.println(n);
        teams = new HashMap<String, Integer>();
        teamsReverse = new HashMap<Integer, String>();
        w = new int[n];
        l = new int[n];
        r = new int[n];
        g = new int[n][n];
        String line;
        for (int i = 0; i < n; ++i) {
            line = in.readString();
            teams.put(line, i);
            teamsReverse.put(i, line);
            w[i] = in.readInt();
            l[i] = in.readInt();
            r[i] = in.readInt();
            for (int j = 0; j < n; ++j) {
                g[i][j] = in.readInt();
            }
        }
    }


    // number of teams
    public int numberOfTeams() {
        return n;
    }

    // all teams
    public Iterable<String> teams() {
        return teams.keySet();
    }

    // number of wins for given team
    public int wins(String team) {
        if (!teams.containsKey(team)) throw new IllegalArgumentException("No such team: " + team);
        return w[teams.get(team)];
    }

    // number of losses for given team
    public int losses(String team) {
        if (!teams.containsKey(team)) throw new IllegalArgumentException("No such team: " + team);
        return l[teams.get(team)];
    }

    // number of remaining games for given team
    public int remaining(String team) {
        if (!teams.containsKey(team)) throw new IllegalArgumentException("No such team: " + team);
        return r[teams.get(team)];
    }

    // number of remaining games between team1 and team2
    public int against(String team1, String team2) {
        if (!teams.containsKey(team1)) throw new IllegalArgumentException("No such team: " + team1);
        if (!teams.containsKey(team2)) throw new IllegalArgumentException("No such team: " + team2);
        return g[teams.get(team1)][teams.get(team2)];
    }

    // is given team eliminated?
    public boolean isEliminated(String team) {
        if (!teams.containsKey(team)) throw new IllegalArgumentException("No such team: " + team);
        int teamId = teams.get(team);
        if (w[teamId] + r[teamId] <= maxWin()) return true;
        Stack<FlowEdge> vertices = new Stack<FlowEdge>();
        FlowNetwork flowNetwork;
        int numOfVertices = 2 + (n - 1) + ((n - 1) * (n - 1) - n + 1) / 2;
        // StdOut.println(numOfVertices);
        // StdOut.println(n);
        flowNetwork = new FlowNetwork(numOfVertices);
        int tempStart = 1 + ((n - 1) * (n - 1) - n + 1) / 2;
        int counter = 0;
        int maxCapacity = 0;
        for (int i = 0; i < n; ++i) {
            for (int j = i + 1; j < n; ++j) {
                if (i != teamId && j != teamId) {
                    FlowEdge flowEdge = new FlowEdge(0, ++counter, g[i][j]);
                    maxCapacity += g[i][j];
                    flowNetwork.addEdge(flowEdge);
                    FlowEdge flowEdge1 = new FlowEdge(counter, tempStart + i,
                                                      Double.POSITIVE_INFINITY);
                    flowNetwork.addEdge(flowEdge1);
                    FlowEdge flowEdge2 = new FlowEdge(counter, tempStart + j,
                                                      Double.POSITIVE_INFINITY);
                    flowNetwork.addEdge(flowEdge2);
                }
            }
            if (i != teamId) {
                FlowEdge flowEdge = new FlowEdge(tempStart + i, numOfVertices - 1,
                                                 w[teamId] + r[teamId] - w[i]);
                flowNetwork.addEdge(flowEdge);
            }
        }
        // StdOut.println(flowNetwork);
        FordFulkerson fordFulkerson;
        fordFulkerson = new FordFulkerson(flowNetwork, 0, numOfVertices - 1);
        // StdOut.println(fordFulkerson.value());
        // StdOut.println(maxCapacity);
        Bag<String> R = new Bag<String>();
        for (int i = 0; i < n; ++i) {
            if (i != teamId) {
                if (fordFulkerson.inCut(tempStart + i)) {
                    // StdOut.println(i);
                    R.add(teamsReverse.get(i));
                }
            }
        }
        subsets.put(team, R);
        StdOut.println(fordFulkerson.value());
        StdOut.println(maxCapacity);
        return fordFulkerson.value() < maxCapacity;
    }

    private int maxWin() {
        int answer = -1;
        for (int i = 0; i < n; ++i) {
            if (w[i] > answer) answer = w[i];
        }
        return answer;
    }

    // subset R of teams that eliminates given team; null if not eliminated
    public Iterable<String> certificateOfElimination(String team) {
        if (!teams.containsKey(team)) throw new IllegalArgumentException("No such team: " + team);
        int teamId = teams.get(team);
        Stack<String> answer = new Stack<String>();
        if (isEliminated(team)) {
            return subsets.get(team);
        }
        return null;
    }

    public static void main(String[] args) {
        BaseballElimination baseballElimination = new BaseballElimination("teams4a.txt");
        StdOut.println(baseballElimination.isEliminated("Ghaddafi"));
        for (String each : baseballElimination.certificateOfElimination("Ghaddafi")) {
            StdOut.println(each);
        }
    }
}

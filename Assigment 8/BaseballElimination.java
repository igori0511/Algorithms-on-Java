import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.FordFulkerson;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.HashMap;
import java.util.Map;

public class BaseballElimination {

    private final Map<String, Integer> teams;

    private Map<String, Bag<String>> eliminatedTeams;

    private final int[][] games;

    private final int[] wins;

    private final int[] loss;

    private final int[] remain;

    private final int numTeams;

    // create a baseball division from given filename in format specified below
    public BaseballElimination(String filename) {
        In in = new In(filename);
        numTeams = in.readInt();
        wins = new int[numTeams];
        loss = new int[numTeams];
        remain = new int[numTeams];
        games = new int[numTeams][numTeams];
        teams = new HashMap<>();
        eliminatedTeams = new HashMap<>();

        int fileLine = 0;
        while (!in.isEmpty()) {
            int teamPosition = fileLine;
            String teamName = in.readString();
            teams.put(teamName, teamPosition);
            eliminatedTeams.put(teamName, null);
            wins[fileLine] = in.readInt();
            loss[fileLine] = in.readInt();
            remain[fileLine] = in.readInt();
            for (int numGames = 0; numGames < games[fileLine].length; numGames++) {
                games[fileLine][numGames] = in.readInt();
            }
            fileLine++;
        }

        in.close();
    }

    // number of teams
    public int numberOfTeams() {
        return numTeams;
    }

    // all teams
    public Iterable<String> teams() {
        return teams.keySet();
    }

    // number of wins for given team
    public int wins(String team) {
        if (!teams.containsKey(team))
            throw new java.lang.IllegalArgumentException();
        return wins[teams.get(team)];
    }

    // number of losses for given team
    public int losses(String team) {
        if (!teams.containsKey(team))
            throw new java.lang.IllegalArgumentException();
        return loss[teams.get(team)];
    }

    // number of remaining games for given team
    public int remaining(String team) {
        if (!teams.containsKey(team))
            throw new java.lang.IllegalArgumentException();
        return remain[teams.get(team)];
    }

    // number of remaining games between team1 and team2
    public int against(String team1, String team2) {
        if (!teams.containsKey(team1) || !teams.containsKey(team2))
            throw new java.lang.IllegalArgumentException();
        return games[teams.get(team1)][teams.get(team2)];
    }

    // is given team eliminated?
    public boolean isEliminated(String team) {
        if (!teams.containsKey(team))
            throw new java.lang.IllegalArgumentException();

        if (!eliminatedTeams.containsKey(team)) return false;
        if (eliminatedTeams.get(team) != null) return true;

        FlowNetwork flowNetwork = createFlowNetwork(team);

        final int source = 0, target = flowNetwork.V() - 1;
        FordFulkerson fordFulkerson = new FordFulkerson(flowNetwork, source, target);

        for (FlowEdge e : flowNetwork.adj(source)) {
            if (Double.compare(e.flow(), e.capacity()) != 0) {
                addEliminationCertificate(fordFulkerson, team);
                return true;
            }
        }

        eliminatedTeams.remove(team);
        return false;
    }

    // subset R of teams that eliminates given team; null if not eliminated
    public Iterable<String> certificateOfElimination(String team) {
        if (!teams.containsKey(team))
            throw new java.lang.IllegalArgumentException();
        if (!eliminatedTeams.containsKey(team)) return null;
        // if team wasn't checked
        if (eliminatedTeams.get(team) == null) {
            isEliminated(team);
            return certificateOfElimination(team);
        } else return eliminatedTeams.get(team);
    }

    // create flow network for specified team
    private FlowNetwork createFlowNetwork(String team) {
        if (!teams.containsKey(team))
            throw new java.lang.IllegalArgumentException();

        int sourceVertex = 0;
        int teamIdentifier = teams.get(team);
        int numberOfGames = numberOfTeams() * (numberOfTeams() - 1) / 2;
        int numberOfVertices = numberOfGames + numberOfTeams() + 2;
        int targetVertexId = numberOfVertices - 1;
        FlowNetwork flowNetwork = new FlowNetwork(numberOfVertices);

        int gamesVertex = 1;
        for (int row = 0; row < games.length; row++) {
            for (int col = row + 1; col < games[row].length; col++) {
                // add edges from source to games vertices
                flowNetwork.addEdge(new FlowEdge(sourceVertex, gamesVertex, games[row][col]));
                // add edges from games to teams vertices
                flowNetwork.addEdge(new FlowEdge(gamesVertex, numberOfGames + row + 1, Double.POSITIVE_INFINITY));
                flowNetwork.addEdge(new FlowEdge(gamesVertex, numberOfGames + col + 1, Double.POSITIVE_INFINITY));
                gamesVertex++;
            }
            // add edges from teams to target
            int teamVertex = numberOfGames + row + 1;
            flowNetwork.addEdge(new FlowEdge(teamVertex, targetVertexId, Math.max(0, wins[teamIdentifier] + remain[teamIdentifier] - wins[row])));
        }
        return flowNetwork;
    }

    private void addEliminationCertificate(FordFulkerson fordFulkerson, String team) {
        Bag<String> certificate = new Bag<>();
        int numGames = numberOfTeams() * (numberOfTeams() - 1) / 2;

        for (String curTeam : teams()) {
            int teamIdentifier = teams.get(curTeam);
            int teamVertexFlowNetwork = numGames + 1 + teamIdentifier;
            if (fordFulkerson.inCut(teamVertexFlowNetwork)) {
                certificate.add(curTeam);
            }
        }
        eliminatedTeams.put(team, certificate);
    }

    public static void main(String[] args) {
        BaseballElimination division = new BaseballElimination(args[0]);
        for (String team : division.teams()) {
            if (division.isEliminated(team)) {
                StdOut.print(team + " is eliminated by the subset R = { ");
                for (String t : division.certificateOfElimination(team)) {
                    StdOut.print(t + " ");
                }
                StdOut.println("}");
            } else {
                StdOut.println(team + " is not eliminated");
            }
        }
    }
}

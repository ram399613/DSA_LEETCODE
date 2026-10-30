
import java.util.*;

class Solution {

    static class State {
        int node;
        int streak;
        long cost;

        State(int node, int streak, long cost) {
            this.node = node;
            this.streak = streak;
            this.cost = cost;
        }
    }

    public int shortestPath(int n, int[][] edges, String labels, int k) {

        if (k < 1) return -1;

        k = Math.min(k, n);

        List<int[]>[] graph = new ArrayList[n];

        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }

        for (int[] edge : edges) {
            graph[edge[0]].add(new int[]{edge[1], edge[2]});
        }

        PriorityQueue<State> pq = new PriorityQueue<>(
            (a, b) -> Long.compare(a.cost, b.cost)
        );

        Map<Long, Long> dist = new HashMap<>();

        long startKey = (long) k + 1 + 0;

        dist.put(1L, 0L);

        pq.offer(new State(0, 1, 0));

        while (!pq.isEmpty()) {

            State current = pq.poll();

            int u = current.node;
            int streak = current.streak;
            long cost = current.cost;

            long key = (long) u * (k + 1) + streak;

            if (cost != dist.getOrDefault(key, Long.MAX_VALUE)) {
                continue;
            }

            if (u == n - 1) {
                return (int) cost;
            }

            for (int[] edge : graph[u]) {

                int v = edge[0];
                int weight = edge[1];

                int newStreak;

                if (labels.charAt(u) == labels.charAt(v)) {
                    newStreak = streak + 1;
                } else {
                    newStreak = 1;
                }

                if (newStreak > k) {
                    continue;
                }

                long newCost = cost + weight;

                long newKey = (long) v * (k + 1) + newStreak;

                if (newCost < dist.getOrDefault(
                    newKey, Long.MAX_VALUE)) {

                    dist.put(newKey, newCost);

                    pq.offer(new State(v, newStreak, newCost));
                }
            }
        }

        return -1;
    }
}

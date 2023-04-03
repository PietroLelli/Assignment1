package u04lab.polyglot.a01b;

import java.util.*;
import java.util.stream.IntStream;

public class LogicsImplJava implements Logics {

    private Set<Pair<Integer,Integer>> minesSet = new HashSet<>();
    private Set<Pair<Integer,Integer>> selected = new HashSet<>();
    private int gridSize;

    public LogicsImplJava(int gridSize, int mines){
        this.gridSize = gridSize;
        final Random r = new Random();
        while (this.minesSet.size()!=mines) {
            this.minesSet.add(new Pair<>(r.nextInt(gridSize),r.nextInt(gridSize)));
        }
        System.out.println(this.minesSet);
    }

    private int neighbours(int x, int y){
        return (int)IntStream.rangeClosed(x-1, x+1)
                .boxed()
                .flatMap(xx -> IntStream.rangeClosed(y-1, y+1).boxed().map(yy -> new Pair<>(xx,yy)))
                .filter(p -> this.minesSet.contains(p))
                .count();
    }

    @Override
    public Optional<Integer> hit(int x, int y) {
        if (this.minesSet.contains(new Pair<>(x,y))) {
            return Optional.empty();
        }
        this.selected.add(new Pair<>(x,y));
        return Optional.of(neighbours(x,y));
    }

    @Override
    public boolean won() {
        return this.selected.size() + this.minesSet.size() == this.gridSize * this.gridSize;
    }
}
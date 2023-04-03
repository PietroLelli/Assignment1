package e1;

public interface MoveStrategy {
    boolean moveKnight(int row, int col, int size);
    boolean isCoordinateOk(int row, int col, int size);
}

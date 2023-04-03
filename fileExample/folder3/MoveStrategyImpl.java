package e1;

public class MoveStrategyImpl implements MoveStrategy {
    @Override
    public boolean moveKnight(int row, int col, int size) {
        return false;
    }

    @Override
    public boolean isCoordinateOk(int row, int col, int size) {
        if (row<0 || col<0 || row >= size || col >= size) {
            return false;
        }
        return true;
    }
}

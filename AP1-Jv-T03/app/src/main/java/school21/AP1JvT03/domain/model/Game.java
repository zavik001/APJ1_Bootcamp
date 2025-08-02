package school21.AP1JvT03.domain.model;

import java.util.UUID;

public class Game {
    private Matrix matrix;
    private String id;
    private GameStatus status;

    public Game() {
        matrix = new Matrix();
        id = UUID.randomUUID().toString();
        status = GameStatus.IN_PROGRESS;
    }

    public void setMatrix(int[][] matrix) {
        this.matrix.setMatrix(matrix);
    }

    public void setStatus(GameStatus status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public Matrix getMatrix() {
        return matrix;
    }

    public GameStatus getStatus() {
        return status;
    }

    public void setId(String id) {
        this.id = id;
    }
}

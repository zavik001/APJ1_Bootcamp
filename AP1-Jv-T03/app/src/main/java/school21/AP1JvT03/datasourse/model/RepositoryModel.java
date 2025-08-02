package school21.AP1JvT03.datasourse.model;

public class RepositoryModel {
    private String id;
    private String status;
    private int[][] matrix;

    public RepositoryModel(String id, String status, int[][] matrix) {
        this.id = id;
        this.status = status;
        this.matrix = matrix;
    }

    public String getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public int[][] getMatrix() {
        return matrix;
    }
}

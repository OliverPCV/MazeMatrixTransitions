package educanet.models;

public class Square extends GameModel {


    public Square(float x, float y, float z, float squareSize) {
        super();

        this.indices = new int[] {
                0, 1, 3, // First triangle
                1, 2, 3 // Second triangle
        };

        this.vertices = new float[] {  //square origin point is in Bottom Left
                x + squareSize  ,y + squareSize, z, // 0 -> Top    Right
                x + squareSize  ,y             , z, // 1 -> Bottom Right
                x               ,y             , z, // 2 -> Bottom Left
                x               ,y + squareSize, z, // 3 -> Top    Left
        };

        this.color = new float[] {
                0.0f, 0.0f, 0.0f, 0.0f,
                0.0f, 0.0f, 0.0f, 0.0f,
                0.0f, 0.0f, 0.0f, 0.0f,
                0.0f, 0.0f, 0.0f, 0.0f,
        };

        setup();
    }


    public void update() {

    }

}
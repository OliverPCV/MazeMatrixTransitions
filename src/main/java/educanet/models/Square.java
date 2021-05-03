package educanet.models;

import educanet.models.GameObject;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL33;
import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class Square extends GameObject {


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
        }; //some help from Filip Makrlík

        this.color = new float[] {
                1.0f, 1.0f, 1.0f, 1.0f,
                1.0f, 1.0f, 1.0f, 1.0f,
                1.0f, 1.0f, 1.0f, 1.0f,
                1.0f, 1.0f, 1.0f, 1.0f,
        };

        setup();
    }


    public void update() {

    }

}
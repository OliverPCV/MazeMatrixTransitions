package educanet.models;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL33;
import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public abstract class GameObject {

    public float x;
    public float y;
    public float z;
    public float size;

    protected float[] vertices;
    protected int[] indices;
    protected float[] color;

    public        int vaoId;
    protected final int vboId;
    protected final int eboId;
    protected final int colorId;

    protected FloatBuffer fb;
    protected IntBuffer ib;
    protected FloatBuffer cb;

    public GameObject() {
        vaoId = GL33.glGenVertexArrays();
        vboId = GL33.glGenBuffers();
        eboId = GL33.glGenBuffers();
        colorId = GL33.glGenBuffers();

    }


    public void draw() {
        GL33.glBindVertexArray(vaoId);
        GL33.glDrawElements(GL33.GL_TRIANGLES, indices.length, GL33.GL_UNSIGNED_INT, 0);
    }

    public abstract void update();

    protected void setup() {
        GL33.glBindVertexArray(vaoId); // Tell OpenGL I am using this square

        setFloatBuffer(vertices.length, "vert");
        setVertices(this.vertices);
        setFloatBuffer(vertices.length, "col");
        setColor(this.color);

        setIntBuffer(indices.length);
        setIndices(this.indices);

    }

    public void setVertices(float[] vertices) {
        // Tell OpenGL we are currently writing to this buffer (vboId)
        GL33.glBindBuffer(GL33.GL_ARRAY_BUFFER, vboId);

        fb.clear()
                .put(vertices)
                .flip();

        // Send the buffer (positions) to the GPU
        GL33.glBufferData(GL33.GL_ARRAY_BUFFER, fb, GL33.GL_STATIC_DRAW);
        GL33.glVertexAttribPointer(0, 3, GL33.GL_FLOAT, false, 0, 0);
        GL33.glEnableVertexAttribArray(0);
    }

    public void setIndices(int[] indices) {
        // Tell OpenGL we are currently writing to this buffer (eboId)
        GL33.glBindBuffer(GL33.GL_ELEMENT_ARRAY_BUFFER, eboId);
        ib.clear()
                .put(indices)
                .flip();

        GL33.glBufferData(GL33.GL_ELEMENT_ARRAY_BUFFER, ib, GL33.GL_STATIC_DRAW);
    }

    public void setColor(float[] color) {
        // Tell OpenGL we are currently writing to this buffer (colorsId)
        GL33.glBindBuffer(GL33.GL_ARRAY_BUFFER, colorId);

        FloatBuffer cb = BufferUtils.createFloatBuffer(color.length)
                .put(color)
                .flip();

        // Send the buffer (positions) to the GPU
        GL33.glBufferData(GL33.GL_ARRAY_BUFFER, cb, GL33.GL_STATIC_DRAW);
        GL33.glVertexAttribPointer(1, 4, GL33.GL_FLOAT, false, 0, 0);
        GL33.glEnableVertexAttribArray(1);
    }

    public void setIntBuffer(int length) {

        if (ib != null) MemoryUtil.memFree(ib);

        ib = BufferUtils.createIntBuffer(length);

    }

    public void setFloatBuffer(int length, String type) {
        if (type.equals("vert")) {
            if (fb != null) MemoryUtil.memFree(fb);

            fb = BufferUtils.createFloatBuffer(length);
        } else if (type.equals("col")) {
            if (cb != null) MemoryUtil.memFree(cb);

            cb = BufferUtils.createFloatBuffer(length);
        }

    }

}
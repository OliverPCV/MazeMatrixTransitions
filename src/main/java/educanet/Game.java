package educanet;

import educanet.models.Player;
import educanet.models.Square;
import educanet.utils.FileUtils;
import org.joml.Matrix4f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL33;

import java.io.File;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Game {

    public static void init(long window) {
        // Setup shaders
        Shaders.initShaders();
        createPlayer();
        prepareGamefield();
        createGamefield();
    }

    public static void render(long window) {
        // render Player
        renderPlayer();
        renderGamefield(); // TODO
    }

    public static void update(long window) {
        movePlayer(window, Player.getMatrix());
        // TODO collision
    }

    // PLAYER
    public static void createPlayer() {
        Player p = new Player();

        GL33.glUseProgram(Shaders.shaderProgramId); // use this shader to render
        GL33.glBindVertexArray(Player.getSquareVaoId());
        GL33.glDrawElements(GL33.GL_TRIANGLES, Player.getVertices().length, GL33.GL_UNSIGNED_INT, 0);
    }

    public static void renderPlayer() {
        // Draw using the glDrawElements function
        GL33.glUseProgram(Shaders.shaderProgramId);
        GL33.glBindVertexArray(Player.getSquareVaoId());
        GL33.glDrawElements(GL33.GL_TRIANGLES, Player.getIndices().length, GL33.GL_UNSIGNED_INT, 0);
    }

    public static void movePlayer(long window, Matrix4f matrix) { // TODO
        if(GLFW.glfwGetKey(window, GLFW.GLFW_KEY_W) == GLFW.GLFW_PRESS) { // Move forward
            matrix = matrix.translate(0, 0.0008f, 0f);
        }
        if(GLFW.glfwGetKey(window, GLFW.GLFW_KEY_A) == GLFW.GLFW_PRESS) { // Left
            matrix = matrix.translate(-0.0008f, 0f, 0f);
        }
        if(GLFW.glfwGetKey(window, GLFW.GLFW_KEY_S) == GLFW.GLFW_PRESS) { // Move backwards
            matrix = matrix.translate(0f, -0.0008f, 0f);
        }
        if (GLFW.glfwGetKey(window, GLFW.GLFW_KEY_D) == GLFW.GLFW_PRESS) { // Move right
            matrix = matrix.translate(0.0008f, 0f, 0f);
        }
        matrix.get(Player.getMatrixBuffer());
        GL33.glUniformMatrix4fv(Player.getUniformMatrixLocation(), false, Player.getMatrixBuffer());
    }

    public static ArrayList<Square> gamefieldObjectArrayList = new ArrayList<>();

    public static String gameField;
    public static int numberOfObjectsGamefield;


    public static void prepareGamefield() {

        String path = "src/main/resources/1.txt";
        File level = new File(path);

        if (level.exists() && level.canRead())
            gameField = FileUtils.readFile(path);


        Matcher m = Pattern.compile("\r\n|\r|\n").matcher(gameField);
        while (m.find()) {
            numberOfObjectsGamefield++;
        }
    }

    public static void createGamefield() { // TODO
        String[] objs = gameField.split("\n");

        for (int i = 0; i < numberOfObjectsGamefield; i++) {
            String[] objAtrribs = objs[i].split(";");
            Square s = new Square(Float.parseFloat(objAtrribs[0]),Float.parseFloat(objAtrribs[1]),0f ,Float.parseFloat(objAtrribs[2]));
            gamefieldObjectArrayList.add(s);
        }
    }

    public static void renderGamefield() { // TODO
        new Matrix4f().identity().get(Player.getMatrixBuffer());
        GL33.glUniformMatrix4fv(Player.getUniformMatrixLocation(), false, Player.getMatrixBuffer());
        for (int i = 0; i < numberOfObjectsGamefield; i++) {
            Square object = gamefieldObjectArrayList.get(i);
            if (object != null) object.draw();
        }
    }

}

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL46;

public class OpenGLTriangle {

    private long window;

    public void run() {
        init();
        loop();
        GLFW.glfwDestroyWindow(window);
        GLFW.glfwTerminate();
    }

    private void init() {
        // Initialize GLFW
        if (!GLFW.glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        // Set GLFW window hints
        GLFW.glfwDefaultWindowHints();
        GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE);
        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_TRUE);

        // Create the window
        window = GLFW.glfwCreateWindow(800, 600, "Red Triangle", 0, 0);
        if (window == 0) {
            throw new RuntimeException("Failed to create the GLFW window");
        }

        // Center the window
        GLFWVidMode vidMode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
        GLFW.glfwSetWindowPos(
                window,
                (vidMode.width() - 800) / 2,
                (vidMode.height() - 600) / 2
        );

        // Make the OpenGL context current
        GLFW.glfwMakeContextCurrent(window);
        GLFW.glfwSwapInterval(1);
        GLFW.glfwShowWindow(window);

        // Initialize OpenGL
        GL.createCapabilities();
    }

    private void loop() {
        // Set up the viewport
        GL46.glViewport(0, 0, 800, 600);

        // Set up the vertex data
        float[] vertices = {
            0.0f,  0.5f, 0.0f,  // Vertex 1 (X, Y, Z)
           -0.5f, -0.5f, 0.0f,  // Vertex 2 (X, Y, Z)
            0.5f, -0.5f, 0.0f   // Vertex 3 (X, Y, Z)
        };

        // Create and bind the Vertex Array Object (VAO)
        int vao = GL46.glGenVertexArrays();
        GL46.glBindVertexArray(vao);

        // Create and bind the Vertex Buffer Object (VBO)
        int vbo = GL46.glGenBuffers();
        GL46.glBindBuffer(GL46.GL_ARRAY_BUFFER, vbo);
        GL46.glBufferData(GL46.GL_ARRAY_BUFFER, vertices, GL46.GL_STATIC_DRAW);

        // Define the layout of the vertex data
        GL46.glVertexAttribPointer(0, 3, GL46.GL_FLOAT, false, 3 * Float.BYTES, 0);
        GL46.glEnableVertexAttribArray(0);

        // Unbind the VAO
        GL46.glBindVertexArray(0);

        // Main loop
        while (!GLFW.glfwWindowShouldClose(window)) {
            // Clear the screen
            GL46.glClear(GL46.GL_COLOR_BUFFER_BIT);

            // Bind the VAO and draw the triangle
            GL46.glBindVertexArray(vao);
            GL46.glDrawArrays(GL46.GL_TRIANGLES, 0, 3);

            // Swap the buffers
            GLFW.glfwSwapBuffers(window);

            // Poll for window events
            GLFW.glfwPollEvents();
        }

        // Cleanup
        GL46.glDeleteVertexArrays(vao);
        GL46.glDeleteBuffers(vbo);
    }

    public static void main(String[] args) {
        new OpenGLTriangle().run();
    }
}

package com.rushi.flipkart;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;

/**
 * ========================================================
 *  Flipkart Clone - JAR Version (Standalone Application)
 *  Developed by: Rushi
 *  Module: DevOps Multi-Cloud → Maven Day 02
 * ========================================================
 *
 * This is a STANDALONE Java application that uses Java's
 * built-in HttpServer (no external dependencies needed).
 *
 * Build : mvn clean package
 * Run   : java -jar target/flipkart-clone.jar
 * Open  : http://localhost:8080
 */
public class App {

    public static final int PORT = 8080;

    public static void main(String[] args) throws IOException {

        // Create an HTTP server bound to port 8080
        HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);

        // Register the home page handler at the root path "/"
        server.createContext("/", new HomePageHandler());

        // Use the default executor (single-threaded is fine for a demo)
        server.setExecutor(null);

        // Start the server
        server.start();

        printBanner();
    }

    /**
     * Handler that serves the index.html page from the classpath.
     */
    static class HomePageHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {

            // Read index.html from src/main/resources (bundled inside the JAR)
            try (InputStream is = getClass().getResourceAsStream("/index.html")) {

                if (is == null) {
                    String errorMsg = "<h1>404 - index.html not found in JAR</h1>";
                    exchange.sendResponseHeaders(404, errorMsg.length());
                    exchange.getResponseBody().write(errorMsg.getBytes());
                    exchange.close();
                    return;
                }

                byte[] response = is.readAllBytes();

                exchange.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
                exchange.sendResponseHeaders(200, response.length);

                OutputStream os = exchange.getResponseBody();
                os.write(response);
                os.close();
            }
        }
    }

    /**
     * Prints a friendly startup banner.
     */
    private static void printBanner() {
        System.out.println();
        System.out.println("============================================================");
        System.out.println("   Flipkart Clone (JAR Version)  -  Developed by Rushi      ");
        System.out.println("------------------------------------------------------------");
        System.out.println("   Standalone Java application powered by Maven             ");
        System.out.println("   Server running at: http://localhost:" + PORT);
        System.out.println("   Press Ctrl + C to stop                                   ");
        System.out.println("============================================================");
        System.out.println();
    }
}

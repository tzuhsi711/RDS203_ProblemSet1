package ps2;

import java.io.FileWriter;
import java.io.IOException;

public class Q2c {

    public static void main(String[] args) throws IOException {

        double beta = 0.3;
        double gamma = 0.1;
        double N = 10000;

        double S = 9999;
        double I = 1;
        double R = 0;

        double h = 1.0;
        int steps = (int)(200 / h);

        // Create CSV file
        FileWriter writer = new FileWriter("output_data/Q2c.csv");

        // Header
        writer.write("t,S,I,R\n");

        for (int z = 0; z <= steps; z++) {

            double t = z * h;

            // Write to file 
            writer.write(t + "," + S + "," + I + "," + R + "\n");

            double[] next = Q2b.euler(beta, gamma, S, I, R, N, h);

            S = next[0];
            I = next[1];
            R = next[2];
        }

        writer.close();

        System.out.println("Q2c.csv created.");
    }
}
package ps2;

public class Q2e {

    public static void main(String[] args) {

        double beta = 0.3;
        double gamma = 0.1;
        double N = 10000;

        double S = 9999;
        double I = 1;
        double R = 0;

        double h = 0.1;
        int steps = (int)(200 / h);

        double maxI = I;
        double peakTime = 0; // largest number of people in the infectious state

        for (int z = 0; z <= steps; z++) {

            double t = z * h;

            // check peak: when the current I is bigger than the previous I
            if (I > maxI) {
                maxI = I;
                peakTime = t;
            }

            double[] next = Q2b.euler(beta, gamma, S, I, R, N, h);

            S = next[0];
            I = next[1];
            R = next[2];
        }

        System.out.println("Peak time: " + peakTime);
        System.out.println("Peak I: " + maxI);
    }
}

package ps2;

public class Q2g_run {

    public static void main(String[] args) {

        double gamma = 0.1;
        double N = 10000;

        double S = 9999;
        double I = 1;
        double R = 0;

        double h = 0.1;
        int steps = (int)(200 / h);

        double maxI = I;
        double peakTime = 0;

        for (int z = 0; z <= steps; z++) {

            double t = z * h;

            if (Math.abs(t - Math.round(t)) < 1e-6) {

                if (I > maxI) {
                    maxI = I;
                    peakTime = Math.round(t);
                }
            }

       
            double[] next = Q2g.rungeKutta(gamma, S, I, R, N, h);

            S = next[0];
            I = next[1];
            R = next[2];
        }

        System.out.println("Peak time (integer): " + peakTime);
        System.out.println("Peak I: " + maxI);
    }
}

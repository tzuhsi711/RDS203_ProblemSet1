package ps2;

public class Q2g {

    public static double dynamicBeta(double I) {
        return 0.3 - 0.01 * Math.max(Math.log(I), 0);
    }

    public static double[] rungeKutta(double gamma,
                                      double S, double I, double R,
                                      double N, double h) {

        // --- k1 ---
        double beta1 = dynamicBeta(I);
        double dS1 = -Q2a.newCases(beta1, S, I, N);
        double dI1 = Q2a.newCases(beta1, S, I, N) - Q2a.recoveredCases(gamma, I);
        double dR1 = Q2a.recoveredCases(gamma, I);

        // --- k2 ---
        double S2 = S + h * dS1 / 2;
        double I2 = I + h * dI1 / 2;

        double beta2 = dynamicBeta(I2);
        double dS2 = -Q2a.newCases(beta2, S2, I2, N);
        double dI2 = Q2a.newCases(beta2, S2, I2, N) - Q2a.recoveredCases(gamma, I2);
        double dR2 = Q2a.recoveredCases(gamma, I2);

        // --- k3 ---
        double S3 = S + h * dS2 / 2;
        double I3 = I + h * dI2 / 2;

        double beta3 = dynamicBeta(I3);
        double dS3 = -Q2a.newCases(beta3, S3, I3, N);
        double dI3 = Q2a.newCases(beta3, S3, I3, N) - Q2a.recoveredCases(gamma, I3);
        double dR3 = Q2a.recoveredCases(gamma, I3);

        // --- k4 ---
        double S4 = S + h * dS3;
        double I4 = I + h * dI3;

        double beta4 = dynamicBeta(I4);
        double dS4 = -Q2a.newCases(beta4, S4, I4, N);
        double dI4 = Q2a.newCases(beta4, S4, I4, N) - Q2a.recoveredCases(gamma, I4);
        double dR4 = Q2a.recoveredCases(gamma, I4);

        // --- final ---
        double S_next = S + (h / 6) * (dS1 + 2*dS2 + 2*dS3 + dS4);
        double I_next = I + (h / 6) * (dI1 + 2*dI2 + 2*dI3 + dI4);
        double R_next = R + (h / 6) * (dR1 + 2*dR2 + 2*dR3 + dR4);

        return new double[]{S_next, I_next, R_next};
    }
}

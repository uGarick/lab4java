import java.awt.geom.Rectangle2D;

public class Mandelbrot extends FractalGenerator{
    public static final int MAX_ITERATIONS = 2000;


    //Позваляет генератору фракталов определить наиболее 'интересную' область
    //комплексной плоскости для конкретного фрактала
    //Начальный диапазон (-2-1.5i)-(1+1.5i)
    public void getInitialRange(Rectangle2D.Double range) {
        range.x = -2;
        range.y = -1.5;
        range.width = 3;
        range.height = 3;
    }

    public int numIterations(double x, double y) {
        int iteration = 0;
        double zreal = 0;
        double zimaginary = 0;

        /*
            Вычисляем функию для фрактала Мальберта Zn=Z(n-1) ^2 + c
         */

        while ((iteration < MAX_ITERATIONS) && ((zreal*zreal+zimaginary*zimaginary) < 4)) {
            double zrealNew = zreal*zreal-zimaginary*zimaginary + x;
            double zimaginaryNew = 2 * zreal*zimaginary + y;
            zreal = zrealNew;
            zimaginary = zimaginaryNew;
            iteration += 1;
        }

        if (iteration == MAX_ITERATIONS) {
            return -1;
        }

        return iteration;
    }
}

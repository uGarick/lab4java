import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.event.*;

public class FractalExplorer {
    //Размер экрана
    private int displaySize;
    //Ссылка JImageDisplay, для обновления отображения в разных методах в процессе вычисления фрактала
    private JImageDisplay display;
    //Объект FractalGenerator. Ссылка на базовый класс для отображения других видов фракталов.
    private FractalGenerator fractal;
    //Диапазон комплексной плоскости, которая выводится на экран
    private Rectangle2D.Double range;

    public FractalExplorer(int size) {
        displaySize = size;

        //Инициализирут фрактальный генератор и объекты диапазона
        fractal = new Mandelbrot();
        range = new Rectangle2D.Double();
        fractal.getInitialRange(range);
        display = new JImageDisplay(displaySize, displaySize);
    }


    //Инициализирует графический интерфейс Swing с помощью JFrame, содержащего
    //объект JImageDisplay и кнопку для сброса отображения
    public void createAndShowGUI() {
        display.setLayout(new BorderLayout());
        JFrame frame = new JFrame("Fractal Explorer");

        frame.add(display, BorderLayout.CENTER);
        JButton resetButton = new JButton("Reset");

        //Экземпляр ButtonHandler на кнопке сброса
        ButtonHandler resetHandler = new ButtonHandler();
        resetButton.addActionListener(resetHandler);

        //Экзепляр MouseHandler в компоненте фрактального отображения
        MouseHandler click = new MouseHandler();
        display.addMouseListener(click);

        //Операция закрытия frame по умолчания на 'exit'
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        frame.add(panel, BorderLayout.NORTH);

        //Добавляем кнопку очистки
        JPanel bottomPanel = new JPanel();
        bottomPanel.add(resetButton);
        frame.add(bottomPanel, BorderLayout.SOUTH);


        //Правильная разметка, включение отображения и запрет на изменение размера окна
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);

        /*Вспомогательный метод для отображения фрактала, который проходит кадый пиксель на дисплее
        и вычисляет кол-во итераций для соответствующих координат в области отображения фрактала.
        Если итераций -1 установит цвет пикселя в черный иначе выберет значение, основанное на кол-ве итераций.
        Обновляет отображение в соответствии с цветом для каждого пикселя и перекрасит JImageDisplay
        */
    }
    private void drawFractal() {
        //Проходим через каждый пиксель
        for (int x=0; x<displaySize; x++) {
            for (int y=0; y<displaySize; y++) {
                //находим координаты x и y
                double xCoord = fractal.getCoord(range.x, range.x + range.width, displaySize, x);
                double yCoord = fractal.getCoord(range.y, range.y + range.height, displaySize, y);

                //Кол-во итераций для координат
                int iteration = fractal.numIterations(xCoord, yCoord);

                if (iteration == -1) display.drawPixel(x, y, 0);
                else {
                    float hue = 0.6f + (float) iteration / 200f;
                    int rgbColor = Color.HSBtoRGB(hue, 1f, 1f);

                    display.drawPixel(x, y, rgbColor);
                }
            }
        }
        display.repaint();
    }

    //Внутренний класс для обработки событй ActionListener
    private class ButtonHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();

            if (command.equals("Reset")) {
                fractal.getInitialRange(range);
                drawFractal();
            }
        }
    }

    //Внутренний класс для обработки MouseListener
    private class MouseHandler extends MouseAdapter {
        /*
        При получении события о щелчке мышью отображает пиксельные координаты
        щелчка в области фрактала, а затем вызывает метод генератора recenterAndZoomRange()
        */
        @Override
        public void mouseClicked(MouseEvent e) {
            int x = e.getX();
            double xCoord = fractal.getCoord(range.x, range.x + range.width, displaySize, x);

            int y = e.getY();
            double yCoord = fractal.getCoord(range.y, range.y + range.height, displaySize, y);

            fractal.recenterAndZoomRange(range, xCoord, yCoord, 0.5);

            drawFractal();
        }
    }

    /*
    Метод main с инициализацией нового экземпляра класса FractalExplorer(800)
    вызовом метода createAndShowGUI() класса FractalExplorer
    вызовом метода drawFractal() класса FractalExplorer для отображения начального представения
     */
    public static void main(String[] args) {
        FractalExplorer displayExplorer = new FractalExplorer(800);
        displayExplorer.createAndShowGUI();
        displayExplorer.drawFractal();
    }
}

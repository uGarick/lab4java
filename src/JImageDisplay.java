import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class JImageDisplay extends JComponent {

    //Буфферезировнное изображение, содерживое которого можно записать
    private BufferedImage displayImage;

    public BufferedImage getImage() {
        return displayImage;
    }


    //Конструктор с параметрами ширины и высоты
    public JImageDisplay(int width, int height) {
        displayImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        Dimension imageDimension = new Dimension(width, height);
        super.setPreferredSize(imageDimension);
    }


    //Реализация отрисовки
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(displayImage, 0, 0, displayImage.getWidth(), displayImage.getHeight(), null);
    }


    //Делает пиксели в изображении черными
    public void clearImage() {
        int[] rgbArr = new int[getWidth() * getHeight()];
        displayImage.setRGB(0, 0, getWidth(), getHeight(), rgbArr, 0, 1);
    }

    //Делает пиксель определенного цвета
    public void drawPixel(int x, int y, int rgbColor) {
        displayImage.setRGB(x, y, rgbColor);
    }
}

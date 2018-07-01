import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Brands {

    private static final int MAX_WIDHT_SQARE = 480;
    private static final int MAX_HEIGHT_SQARE = 360;

    private static final int MAX_WIDHT_LONG = 580;
    private static final int MAX_HEIGHT_LONG = 220;

    public Brands() {
    }

    public void Execute() throws IOException {
        File directory = new File("C:\\Users\\Ray\\Desktop\\guess\\img\\brands_history\\");

        File[] folderEntries = directory.listFiles();
        assert folderEntries != null;
        for (File img : folderEntries) {
            // Получаем изображение шаблона
            BufferedImage imageSquareTemplate = ImageIO.read(new File("C:\\Users\\Ray\\Desktop\\guess\\brands-games1x1.png"));
            Graphics2D squareTemplate = imageSquareTemplate.createGraphics();
            BufferedImage imageLongTemplate = ImageIO.read(new File("C:\\Users\\Ray\\Desktop\\guess\\brands-games1x2.png"));
            Graphics2D longTemplate = imageLongTemplate.createGraphics();

            BufferedImage image = ImageIO.read(img);

            // Устанавливаем рендеринг
            squareTemplate.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
            longTemplate.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);

            // Наносим картинку

            // Квадратную в 1х1
            if (image.getWidth() == image.getHeight()) {
                // Если Ширина больше допустимой
                if (image.getWidth() > MAX_WIDHT_SQARE) {
                    Image tempImg = image.getScaledInstance(-1 , MAX_HEIGHT_SQARE, Image.SCALE_SMOOTH);
                    squareTemplate.drawImage(tempImg, getX(imageSquareTemplate, tempImg), getY(imageSquareTemplate, tempImg), null);

                } else {
                    squareTemplate.drawImage(image, getX(imageSquareTemplate, image), getY(imageSquareTemplate, image), null);
                }

                // Сохранаем новое изображение
                save(imageSquareTemplate, img.getName());
            }

            // Прямоугольную в 1х1
            else if (image.getWidth() / image.getHeight() < 2) {
                // Если Ширина больше допустимой
                if (image.getWidth() > MAX_WIDHT_SQARE) {
                    Image tempImg = image.getScaledInstance(MAX_WIDHT_SQARE, -1, Image.SCALE_SMOOTH);

                    // Если после сжатия высота больше допустимой
                    if (tempImg.getHeight(null) > MAX_HEIGHT_SQARE) {
                        Image tempImg2 = tempImg.getScaledInstance(-1, MAX_HEIGHT_SQARE, Image.SCALE_SMOOTH);
                        squareTemplate.drawImage(tempImg2, getX(imageSquareTemplate, tempImg2), getY(imageSquareTemplate, tempImg2), null);

                        // Если после сжатия высота допустима
                    } else {
                        squareTemplate.drawImage(tempImg, getX(imageSquareTemplate, tempImg), getY(imageSquareTemplate, tempImg), null);
                    }
                }

                //Если высота больше допустимой
                else if (image.getHeight() > MAX_HEIGHT_SQARE) {
                    Image tempImg = image.getScaledInstance(-1, MAX_HEIGHT_SQARE, Image.SCALE_SMOOTH);

                    // Если после сжатия ширина больше допустимой
                    if (tempImg.getWidth(null) > MAX_WIDHT_SQARE) {
                        Image tempImg2 = tempImg.getScaledInstance(MAX_WIDHT_SQARE, -1, Image.SCALE_SMOOTH);
                        squareTemplate.drawImage(tempImg2, getX(imageSquareTemplate, tempImg2), getY(imageSquareTemplate, tempImg2), null);

                    // Если после сжатия высота допустима
                    } else {
                        squareTemplate.drawImage(tempImg, getX(imageSquareTemplate, tempImg), getY(imageSquareTemplate, tempImg), null);
                    }
                } else {
                    squareTemplate.drawImage(image, getX(imageSquareTemplate, image), getY(imageSquareTemplate, image), null);
                }

                // Сохранаем новое изображение
                save(imageSquareTemplate, img.getName());
            }

            // Прямоугольную в 2х1
            else {
                // Если Ширина больше допустимой
                if (image.getWidth() > MAX_WIDHT_LONG) {
                    Image tempImg = image.getScaledInstance(MAX_WIDHT_LONG, -1, Image.SCALE_SMOOTH);

                    // Если после сжатия высота больше допустимой
                    if (tempImg.getHeight(null) > MAX_HEIGHT_LONG) {
                        Image tempImg2 = tempImg.getScaledInstance(-1, MAX_HEIGHT_LONG, Image.SCALE_SMOOTH);
                        longTemplate.drawImage(tempImg, getX(imageLongTemplate, tempImg2), getY(imageLongTemplate, tempImg2), null);

                        // Если после сжатия высота допустима
                    } else {
                        longTemplate.drawImage(tempImg, getX(imageLongTemplate, tempImg), getY(imageLongTemplate, tempImg), null);
                    }
                }

                //Если высота больше допустимой
                else if (image.getHeight() > MAX_HEIGHT_LONG) {
                    Image tempImg = image.getScaledInstance(-1, MAX_HEIGHT_LONG, Image.SCALE_SMOOTH);

                    // Если после сжатия ширина больше допустимой
                    if (tempImg.getWidth(null) > MAX_WIDHT_LONG) {
                        Image tempImg2 = tempImg.getScaledInstance(MAX_WIDHT_SQARE, -1, Image.SCALE_SMOOTH);
                        longTemplate.drawImage(tempImg, getX(imageLongTemplate, tempImg2), getY(imageLongTemplate, tempImg2), null);

                        // Если после сжатия высота допустима
                    } else {
                        longTemplate.drawImage(tempImg, getX(imageLongTemplate, tempImg), getY(imageLongTemplate, tempImg), null);
                    }
                } else {
                    longTemplate.drawImage(image, getX(imageLongTemplate, image), getY(imageLongTemplate, image), null);
                }
                // Сохранаем новое изображение
                save(imageLongTemplate, img.getName());
            }
        }
    }

    private static int getX(BufferedImage imgTemplate, Image image) {
        return (imgTemplate.getWidth() / 2) - (image.getWidth(null) / 2);
    }

    private static int getY(BufferedImage imgTemplate, Image image) {
        return (imgTemplate.getHeight() / 2) - (image.getHeight(null) / 2) + (117 / 2);
    }

    private static void save(BufferedImage template, String name) throws IOException {
        ImageIO.write(template, "png", new File("C:\\Users\\Ray\\Desktop\\images\\brands_history\\" + name));
    }
}

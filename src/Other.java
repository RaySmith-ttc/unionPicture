import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.Locale;

public class Other {

    private static final int MAX_WIDHT_SQARE = 580;
    private static final int MAX_HEIGHT_SQARE = 457;

    private static final int MAX_WIDHT_LONG = 680;
    private static final int MAX_HEIGHT_LONG = 317;

    public Other() {

    }

    public void Execute() throws IOException {
        // Получаем изображение шаблона 1х1
        BufferedImage imageSquareTemplate = ImageIO.read(new File("C:\\Users\\Ray\\Desktop\\guess\\games1x1.png"));
        BufferedImage bi_1x1 = ImageIO.read(new File("C:\\Users\\Ray\\Desktop\\guess\\games1x1.png"));
        Graphics2D squareTemplate = imageSquareTemplate.createGraphics();

        // Получаем изображение шаблона 2х1
        BufferedImage imageLongTemplate = ImageIO.read(new File("C:\\Users\\Ray\\Desktop\\guess\\games1x2.png"));
        BufferedImage bi_2x1 = ImageIO.read(new File("C:\\Users\\Ray\\Desktop\\guess\\games1x2.png"));
        Graphics2D longTemplate = imageLongTemplate.createGraphics();

        // Дериктория c картинками
        File directory = new File("C:\\Users\\Ray\\Desktop\\guess\\img\\games_2\\");

        // Перебераем каждую
        File[] folderEntries = directory.listFiles();
        assert folderEntries != null;
        for (File img : folderEntries) {
            if (img.isDirectory()) continue;

            BufferedImage image = ImageIO.read(img);

            // Устанавливаем рендеринг
            squareTemplate.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
            longTemplate.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);

            // Наносим картинку

            // Постеры и квадратные в 1х1
            if (image.getWidth() < image.getHeight() || image.getWidth() == image.getHeight() || ((float)image.getWidth() / (float)image.getHeight()) <= 1.54f) {
                Image newPic = image.getScaledInstance(MAX_WIDHT_SQARE, -1, Image.SCALE_SMOOTH);
                Image newTemp = bi_1x1.getScaledInstance(-1, -1, Image.SCALE_SMOOTH);

                if (newPic.getHeight(null) < MAX_HEIGHT_SQARE) {
                    newPic = newPic.getScaledInstance(-1, MAX_HEIGHT_SQARE, Image.SCALE_SMOOTH);
                }

                squareTemplate.drawImage(newPic, getX(imageSquareTemplate, newPic), getY(imageSquareTemplate, newPic, true),null);
                squareTemplate.drawImage(newTemp, getX(imageSquareTemplate, newTemp), getY(imageSquareTemplate, newTemp), null);

                // Сохранаем новое изображение
                save(imageSquareTemplate, img.getName());

            // Альбомные в 2х1
            } else {
                Image newPic = image.getScaledInstance(-1, MAX_HEIGHT_LONG, Image.SCALE_SMOOTH);
                Image newTemp = bi_2x1.getScaledInstance(-1, -1, Image.SCALE_SMOOTH);

                if (newPic.getWidth(null) < MAX_WIDHT_LONG) {
                    newPic = newPic.getScaledInstance(MAX_WIDHT_LONG, -1, Image.SCALE_SMOOTH);
                }

                longTemplate.drawImage(newPic, getX(imageLongTemplate, newPic), getY(imageLongTemplate, newPic, true), null);
                longTemplate.drawImage(newTemp, getX(imageLongTemplate, newTemp), getY(imageLongTemplate, newTemp), null);

                // Сохранаем новое изображение
                save(imageLongTemplate, img.getName());
            }
        }
    }

    private static int getX(BufferedImage imgTemplate, Image image) {
        return (imgTemplate.getWidth() / 2) - (image.getWidth(null) / 2);
    }

    private static int getX(BufferedImage imgTemplate, Image image, boolean l) {
        return (imgTemplate.getWidth() / 2) - (image.getWidth(null) / 2) + 61;
    }

    private static int getY(BufferedImage imgTemplate, Image image) {
        return (imgTemplate.getHeight() / 2) - (image.getHeight(null) / 2);
    }

    private static int getY(BufferedImage imgTemplate, Image image, boolean l) {
        return (imgTemplate.getHeight() / 2) - (image.getHeight(null) / 2) + 61;
    }

    private static void save(BufferedImage template, String name) throws IOException {
        File compressedImageFile = new File("C:\\Users\\Ray\\Desktop\\images\\games_2\\" + name);
        OutputStream os = new FileOutputStream(compressedImageFile);

        BufferedImage newImage = new BufferedImage( template.getWidth(), template.getHeight(), BufferedImage.TYPE_INT_RGB);
        newImage.createGraphics().drawImage( template, 0, 0, Color.BLACK, null);

        Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("jpg");
        ImageWriter writer = (ImageWriter) writers.next();

        ImageOutputStream ios = ImageIO.createImageOutputStream(os);
        writer.setOutput(ios);

        ImageWriteParam param = writer.getDefaultWriteParam();

        param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        //param.setCompressionQuality(0.5f);
        writer.write(null, new IIOImage(newImage, null, null), param);

        //ImageIO.write(template, "png", new File("C:\\Users\\Ray\\Desktop\\" + name));
    }
}

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.Buffer;

public class ImageWrapper {
    private BufferedImage image;
    private File imageFile;
    private int imageHeight;
    private int imageWidth;
    public boolean isValid;

    public ImageWrapper(File file) {
        try {
            imageFile = file;
            image = ImageIO.read(file);
            imageHeight = image.getHeight();
            imageWidth = image.getWidth();
        } catch (IOException e) {
            isValid = false;
        }
    }

    public BufferedImage[] split(int pixelHeight) {
        BufferedImage[] splitImages = new BufferedImage[(imageHeight / pixelHeight) + 1];
        int curImageOffset = 0;
        if(pixelHeight < 0 || pixelHeight > imageHeight) {
            return null;
        }
        int index = 0;
        while(true) {
            if(curImageOffset + pixelHeight < imageHeight) {
                splitImages[index] = imgCrop(image, 0, curImageOffset, imageWidth, curImageOffset + pixelHeight);
                curImageOffset = curImageOffset + pixelHeight;
                index++;
            }
            else {
                splitImages[index] = imgCrop(image, 0, curImageOffset, imageWidth, curImageOffset + (imageHeight - curImageOffset));
                break;
            }
        }
        return splitImages;
    }

    private BufferedImage imgCrop(BufferedImage originalImage, int xPosOne, int yPosOne, int xPosTwo, int yPosTwo){
        if(xPosOne > xPosTwo) {
            int temp = xPosOne;
            xPosTwo = xPosOne;
            xPosOne = temp;
        }
        if(yPosOne > yPosTwo) {
            int temp = yPosOne;
            yPosTwo = yPosOne;
            yPosOne = temp;
        }
        BufferedImage cropImage = new BufferedImage((xPosTwo-xPosOne), (yPosTwo-yPosOne), BufferedImage.TYPE_INT_ARGB);
        for(int y = yPosOne, yt = 0; y < yPosTwo; y++, yt++) {
            for(int x = xPosOne, xt = 0; x < xPosTwo; x++, xt++) {
                int curColour = originalImage.getRGB(x, y);
                cropImage.setRGB(xt, yt, curColour);
            }
        }
        return cropImage;
    }
}

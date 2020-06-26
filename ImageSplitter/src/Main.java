import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        new GUI();
    }

    public static void splitImage(File imageFile, int splitPixels, File saveDirectory) {
        ImageWrapper imageWrapper = new ImageWrapper(imageFile);
        BufferedImage[] imageSplitArray = imageWrapper.split(splitPixels);
        for(int i = 0; i < imageSplitArray.length; i++) {
            saveImage(imageSplitArray[i], saveDirectory.getAbsolutePath(),  imageFile.getName().substring(0, imageFile.getName().indexOf(".")) + "-" + formatLeadingZero(Integer.toString(i)));
        }
    }

    public static String formatLeadingZero(String nameOriginal) {
        String newName = ("00000" + nameOriginal).substring(nameOriginal.length());
        return newName;
    }

    public static void saveImage(BufferedImage image, String directory, String name) {
        try{
            File file = new File(directory + File.separator + name + ".png");
            ImageIO.write(image,"png",file);
        }catch(IOException e){}
    }

    public static File chooseDirectory() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        chooser.setAcceptAllFileFilterUsed(false);
        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            return chooser.getSelectedFile();
        } else {
            return null;
        }
    }

    public static File[] getDirectoryFiles(File f) {
        String[] stringFiles = f.list();
        File[] listFiles = new File[stringFiles.length];
        for(int i = 0; i < listFiles.length; i++) {
            listFiles[i] = new File(f.toString() + File.separator + stringFiles[i]);
            // sets to null if it is a directory
            if(listFiles[i].isDirectory()) {
                listFiles[i] = null;
            }
        }
        return listFiles;
    }
}

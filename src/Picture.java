import java.awt.*;
import java.awt.font.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.text.*;
import java.util.*;
import java.util.List; // resolves problem with java.awt.List and java.util.List

/**
 * A class that represents a picture.  This class inherits from
 * SimplePicture and allows the student to add functionality to
 * the Picture class.
 *
 * @author Barbara Ericson ericson@cc.gatech.edu
 */
public class Picture extends SimplePicture
{
    ///////////////////// constructors //////////////////////////////////

    /**
     * Constructor that takes no arguments
     */
    public Picture ()
    {
        /* not needed but use it to show students the implicit call to super()
         * child constructors always call a parent constructor
         */
        super();
    }

    /**
     * Constructor that takes a file name and creates the picture
     * @param fileName the name of the file to create the picture from
     */
    public Picture(String fileName)
    {
        // let the parent class handle this fileName
        super(fileName);
    }

    /**
     * Constructor that takes the width and height
     * @param height the height of the desired picture
     * @param width the width of the desired picture
     */
    public Picture(int height, int width)
    {
        // let the parent class handle this width and height
        super(width,height);
    }

    /**
     * Constructor that takes a picture and creates a
     * copy of that picture
     * @param copyPicture the picture to copy
     */
    public Picture(Picture copyPicture)
    {
        // let the parent class do the copy
        super(copyPicture);
    }

    /**
     * Constructor that takes a buffered image
     * @param image the buffered image to use
     */
    public Picture(BufferedImage image)
    {
        super(image);
    }

    ////////////////////// methods ///////////////////////////////////////

    /**
     * Method to return a string with information about this picture.
     * @return a string with information about the picture such as fileName,
     * height and width.
     */
    public String toString()
    {
        String output = "Picture, filename " + getFileName() +
                " height " + getHeight()
                + " width " + getWidth();
        return output;

    }

    /** Method to set the blue to 0 */
    public void zeroBlue()
    {
        Pixel[][] pixels = this.getPixels2D();
        for (Pixel[] rowArray : pixels)
        {
            for (Pixel pixelObj : rowArray)
            {
                pixelObj.setBlue(0);
            }
        }
    }

    public void keepOnlyBlue() {
        Pixel[][] pixels = this.getPixels2D();
        for (Pixel[] rowArray: pixels)
            for (Pixel pixelObj : rowArray) {
                pixelObj.setGreen(0);
                pixelObj.setRed(0);
            }
    }

    public void negate() {
        Pixel[][] pixels = this.getPixels2D();
        for (Pixel[] rowArray: pixels)
            for (Pixel pixelObj : rowArray) {
                pixelObj.setGreen(255 - pixelObj.getGreen());
                pixelObj.setRed(255 - pixelObj.getRed());
                pixelObj.setBlue(255 - pixelObj.getBlue());
            }
    }

    public void grayscale() {
        Pixel[][] pixels = this.getPixels2D();
        for (Pixel[] rowArray: pixels)
            for (Pixel pixelObj : rowArray) {
                pixelObj.setGreen((pixelObj.getBlue() + pixelObj.getRed() + pixelObj.getGreen()) / 3);
                pixelObj.setRed((pixelObj.getBlue() + pixelObj.getRed() + pixelObj.getGreen()) / 3);
                pixelObj.setBlue((pixelObj.getBlue() + pixelObj.getRed() + pixelObj.getGreen()) / 3);
            }
    }

    public void fixUnderwater() {
        Pixel[][] pixels = this.getPixels2D();
        for (Pixel[] rowArray: pixels)
            for (Pixel pixelObj : rowArray) {
                pixelObj.setRed(pixelObj.getRed() * 4);
            }
    }

    /** Method that mirrors the picture around a
     * vertical mirror in the center of the picture
     * from left to right */
    public void mirrorVertical()
    {
        Pixel[][] pixels = this.getPixels2D();
        Pixel leftPixel = null;
        Pixel rightPixel = null;
        int width = pixels[0].length;
        for (int row = 0; row < pixels.length; row++)
        {
            for (int col = 0; col < width / 2; col++)
            {
                leftPixel = pixels[row][col];
                rightPixel = pixels[row][width - 1 - col];
                rightPixel.setColor(leftPixel.getColor());
            }
        }
    }

    public void mirrorVerticalRightToLeft() {
        Pixel[][] pixels = this.getPixels2D();
        Pixel leftPixel = null;
        Pixel rightPixel = null;
        int width = pixels[0].length;
        for (int row = 0; row < pixels.length; row++)
        {
            for (int col = 0; col < width / 2; col++)
            {
                leftPixel = pixels[row][col];
                rightPixel = pixels[row][width - 1 - col];
                leftPixel.setColor(rightPixel.getColor());
            }
        }
    }

    public void mirrorHorizontal() {
        Pixel[][] pixels = this.getPixels2D();
        Pixel topPixel = null;
        Pixel bottomPixel = null;
        int height = pixels.length;
        for (int row = 0; row < height / 2; row++)
        {
            for (int col = 0; col < pixels[0].length; col++)
            {
                topPixel = pixels[row][col];
                bottomPixel = pixels[height - 1 - row][col];
                bottomPixel.setColor(topPixel.getColor());
            }
        }
    }

    public void mirrorHorizontalBotToTop() {
        Pixel[][] pixels = this.getPixels2D();
        Pixel topPixel = null;
        Pixel bottomPixel = null;
        int height = pixels.length;
        for (int row = 0; row < height / 2; row++)
        {
            for (int col = 0; col < pixels[0].length; col++)
            {
                topPixel = pixels[row][col];
                bottomPixel = pixels[height - 1 - row][col];
                topPixel.setColor(bottomPixel.getColor());
            }
        }
    }

    public void mirrorDiagonal() {
        Pixel[][] pixels = this.getPixels2D();
        if (pixels.length > pixels[0].length) {
            int lastRow = pixels[0].length;
            for (int row = 1; row < lastRow; row++ )
                for (int col = 0; col < pixels[0].length; col++ ) {
                    if (row == col)
                        break;
                    pixels[col][row].setColor(pixels[row][col].getColor());
                }
        }
        else {
            for (int row = 1; row < pixels.length; row++)
                for (int col = 0; col < pixels.length; col++) {
                    if (row == col)
                        break;
                    pixels[col][row].setColor(pixels[row][col].getColor());
                }
        }
    }

    /** Mirror just part of a picture of a temple */
    public void mirrorTemple()
    {
        int mirrorPoint = 276;
        Pixel leftPixel = null;
        Pixel rightPixel = null;
        int count = 0;
        Pixel[][] pixels = this.getPixels2D();
        // loop through the rows
        for (int row = 27; row < 97; row++)
        {
            // loop from 13 to just before the mirror point
            for (int col = 13; col < mirrorPoint; col++)
            {

                leftPixel = pixels[row][col];
                rightPixel = pixels[row]
                        [mirrorPoint - col + mirrorPoint];
                rightPixel.setColor(leftPixel.getColor());
                count++;
            }
            System.out.println(count);
        }
    }

    public void mirrorArms() {
        int mirrorPoint = 194;
        Pixel topPixel = null;
        Pixel bottomPixel = null;
        Pixel[][] pixels = this.getPixels2D();
        // loop through the rows
        for (int row = 158; row < mirrorPoint; row++)
        {
            // loop from 13 to just before the mirror point
            for (int col = 104; col < 169; col++)
            {
                topPixel = pixels[row][col];
                bottomPixel = pixels[mirrorPoint - row + mirrorPoint][col];
                bottomPixel.setColor(topPixel.getColor());
            }
        }

        for (int row = 171; row < mirrorPoint; row++)
        {
            // loop from 13 to just before the mirror point
            for (int col = 238; col < 293; col++)
            {
                topPixel = pixels[row][col];
                bottomPixel = pixels[mirrorPoint - row + mirrorPoint][col];
                bottomPixel.setColor(topPixel.getColor());
            }
        }
    }

    public void mirrorGull() {
        int mirrorPoint = 350;
        Pixel leftPixel = null;
        Pixel rightPixel = null;
        Pixel[][] pixels = this.getPixels2D();
        // loop through the rows
        for (int row = 235; row < 321; row++)
        {
            // loop from 13 to just before the mirror point
            for (int col = 235; col < mirrorPoint; col++)
            {

                leftPixel = pixels[row][col];
                rightPixel = pixels[row][mirrorPoint - col + mirrorPoint];
                rightPixel.setColor(leftPixel.getColor());
            }
        }
    }

    /** copy from the passed fromPic to the
     * specified startRow and startCol in the
     * current picture
     * @param fromPic the picture to copy from
     * @param startRow the start row to copy to
     * @param startCol the start col to copy to
     */
    public void copy(Picture fromPic,
                     int startRow, int startCol)
    {
        Pixel fromPixel = null;
        Pixel toPixel = null;
        Pixel[][] toPixels = this.getPixels2D();
        Pixel[][] fromPixels = fromPic.getPixels2D();
        for (int fromRow = 0, toRow = startRow;
             fromRow < fromPixels.length &&
                     toRow < toPixels.length;
             fromRow++, toRow++)
        {
            for (int fromCol = 0, toCol = startCol;
                 fromCol < fromPixels[0].length &&
                         toCol < toPixels[0].length;
                 fromCol++, toCol++)
            {
                fromPixel = fromPixels[fromRow][fromCol];
                toPixel = toPixels[toRow][toCol];
                toPixel.setColor(fromPixel.getColor());
            }
        }
    }
    
    public void copy(Picture fromPic, int startRow, int startCol, int fromStartRow, int fromEndRow, int fromStartCol, int fromEndCol) {
        Pixel fromPixel = null;
        Pixel toPixel = null;
        Pixel[][] toPixels = this.getPixels2D();
        Pixel[][] fromPixels = fromPic.getPixels2D();
        Pixel[][] newFromPixels = new Pixel[fromEndRow - fromStartRow][fromEndCol - fromStartCol];
        
        for (int row = fromStartRow; row < fromEndRow; row++) {
            for (int col = fromStartCol; col < fromEndCol; col++) {
                newFromPixels[row][col] = fromPixels[row][col];
            }
        }
        
        for (int fromRow = 0, toRow = startRow; fromRow < newFromPixels.length && toRow < toPixels.length; fromRow++, toRow++) {
            for (int fromCol = 0, toCol = startCol; fromCol < newFromPixels[0].length && toCol < toPixels[0].length; fromCol++, toCol++) {
                fromPixel = newFromPixels[fromRow][fromCol];
                toPixel = toPixels[toRow][toCol];
                toPixel.setColor(fromPixel.getColor());
            }
        }
    }

    /** Method to create a collage of several pictures */
    public void createCollage()
    {
        Picture flower1 = new Picture("flower1.jpg");
        Picture flower2 = new Picture("flower2.jpg");
        this.copy(flower1,0,0);
        this.copy(flower2,100,0);
        this.copy(flower1,200,0);
        Picture flowerNoBlue = new Picture(flower2);
        flowerNoBlue.zeroBlue();
        this.copy(flowerNoBlue,300,0);
        this.copy(flower1,400,0);
        this.copy(flower2,500,0);
        this.mirrorVertical();
        this.write("collage.jpg");
    }
    
    public void myCollage() {
        Picture gorge = new Picture("gorge.jpg");
        gorge.grayscale();
        Picture flower1 = new Picture("flower1.jpg");
        Picture flower2 = new Picture("flower2.jpg");
        this.copy(gorge, 0, 0, 0, 60, 0, 50);
        this.copy(flower1, 100, 0, 0, 50, 0, 60);
        this.copy(flower2, 100, 1, 2, 3, 4, 5);
        Picture flowerZeroBlue = new Picture(flower2);
        flowerZeroBlue.zeroBlue();
        flower1.negate();
        this.copy(flowerZeroBlue, 400, 0, 23, 45, 50, 6);
        this.copy(gorge, 400, 0, 23, 45, 50, 6);
        this.copy(flower1, 300, 1, 53, 54, 5, 0);
        this.mirrorVertical();
        this.write("collage.jpg");        
    }
    
    public void edgeDetection2(int edgeDist) {
        Pixel currentPixel = null;
        Pixel rightPixel = null;
        Pixel bottomPixel = null;
        Pixel[][] pixels = this.getPixels2D();
        Color rightColor = null;
        Color bottomColor = null;
        
        for (int col = 0; col < pixels[0].length; col++) {
            for (int row = 0; row < pixels.length - 1; row++) {
                currentPixel = pixels[row][col];
                rightPixel = pixels[row][col + 1];
                bottomPixel = pixels[row + 1][col];
                rightColor = rightPixel.getColor();
                bottomColor = bottomPixel.getColor();
                
                if ((currentPixel.colorDistance(rightColor)) > edgeDist || (currentPixel.colorDistance(bottomColor)) > edgeDist)
                    currentPixel.setColor(Color.BLACK);
                else
                    currentPixel.setColor(Color.WHITE);
            }
        }
    }
    
    /** Method to show large changes in color
     * @param edgeDist the distance for finding edges
     */
    public void edgeDetection(int edgeDist)
    {
        Pixel currentPixel = null;
        Pixel rightPixel = null;
        Pixel bottomPixel = null;
        Pixel[][] pixels = this.getPixels2D();
        Color rightColor = null;
        Color bottomColor = null;
        
        for (int row = 0; row < pixels.length - 1; row++) {
            for (int col = 0; col < pixels[0].length - 1; col++) {
                currentPixel = pixels[row][col];
                rightPixel = pixels[row][col + 1];
                bottomPixel = pixels[row + 1][col];
                rightColor = rightPixel.getColor();
                bottomColor = bottomPixel.getColor();
                
                if ((currentPixel.colorDistance(rightColor)) > edgeDist || (currentPixel.colorDistance(bottomColor)) > edgeDist)
                    currentPixel.setColor(Color.BLACK);
                else
                    currentPixel.setColor(Color.WHITE);
            }
        }
    }

    public void encode(Picture message) {
        Pixel[][] pixels = this.getPixels2D();
        Pixel[][] messagePixels = message.getPixels2D();

        for (int row = 0; row < pixels.length; row++)
            for (int col = 0; col < pixels[0].length; col++) {
                if (pixels[row][col].getRed() % 2 == 1)
                    pixels[row][col].setRed(pixels[row][col].getRed() - 1);
                if (messagePixels[row][col].getRed() < 100)
                    pixels[row][col].setRed(pixels[row][col].getRed() + 1);
            }
    }

    public void decode() {
        Pixel[][] pixels = this.getPixels2D();
        for (int row = 0; row < pixels.length; row++)
            for (int col = 0; col < pixels[0].length; col++) {
                if (pixels[row][col].getRed() % 2 == 1) {
                    pixels[row][col].setRed(0);
                    pixels[row][col].setGreen(0);
                    pixels[row][col].setBlue(0);
                }
                else {
                    pixels[row][col].setRed(255);
                    pixels[row][col].setGreen(255);
                    pixels[row][col].setBlue(255);
                }
            }
    }

    public void chromakey(int redVal, int greenVal, int blueVal, Picture overlay) {
        Pixel[][] pixels = this.getPixels2D();
        Pixel[][] otherPixels = overlay.getPixels2D();
        for (int row = 0; row < pixels.length; row++)
            for (int col = 0; col < pixels[0].length; col++) {
                if (Math.abs(pixels[row][col].getRed() - redVal) < 50 && Math.abs(pixels[row][col].getGreen() - greenVal) < 50 && Math.abs(pixels[row][col].getBlue() - blueVal) < 50)
                    pixels[row][col].setColor(otherPixels[row][col].getColor());
            }
    }

    /* Main method for testing - each class in Java can have a main
     * method
     */
    public static void main(String[] args)
    {
        Picture beach = new Picture("beach.jpg");
        beach.explore();
        beach.zeroBlue();
        beach.explore();
    }

} // this } is the end of class Picture, put all new methods before this

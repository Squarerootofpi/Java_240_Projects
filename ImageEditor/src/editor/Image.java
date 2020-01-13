package editor;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.File;


import java.util.Scanner;
import java.util.Vector;
/**
 * Magic number: P3
 * Width: 1
 * Height: 1
 * Max Color Value: 255
 *
 *
 */
public class Image
{
    private int width;
    private int height;
    private int maxColorVal;
    private Vector<Vector<Pixel>> pixels; //height X width
    private String magicNumber;
    public static void main()
    {
        System.out.println("Image main!!");
        //return;
    }
    Image(int w, int h, int maxCoVal, Scanner scanner, String magicNum)
    {
        width = w;
        height = h;
        maxColorVal = maxCoVal;
        magicNumber = magicNum;
        pixels = new Vector<Vector<Pixel>>();
        constructImage(scanner);
    }
    public void constructImage(Scanner scanner)
    {
        Integer r;
        Integer g;
        Integer b;
        for (int h = 0; h < height; h++)
        {
            Vector<Pixel> emptyVP = new Vector<Pixel>();
            pixels.add(emptyVP);
            for (int w = 0; w < width; w++)
            {
                r = scanner.nextInt();
                g = scanner.nextInt();
                b = scanner.nextInt();
                Pixel p = new Pixel(r,g,b);
                pixels.elementAt(h).add(p);
            }

        }
    }
    public void printFile(String filePathName) throws FileNotFoundException
    {
                System.out.println("Attempting to print file/path: " + filePathName);
                //File file = new File ("C:/Users/Me/Desktop/directory/file.txt");
                File file = new File(filePathName);
                PrintWriter printWriter = new PrintWriter (file);
                printWriter.print (fileToString());
                printWriter.close ();
            }

    @Override
    public String toString()
    {
        return "Image{" +
                ", magicNumber=" + magicNumber +
                "width=" + width +
                ", height=" + height +
                ", maxColorVal=" + maxColorVal +
                ", pixels=" + pixels +
                '}';
    }
    /**
     * DESCRIPTION OF PPM FILE INPUT
     * A "magic number" for identifying the file type. A ppm image's magic number is the two characters "P6".
     * Whitespace (blanks, TABs, CRs, LFs).
     * A width, formatted as ASCII characters in decimal.
     * Whitespace.
     * A height, again in ASCII decimal.
     * Whitespace.
     * The maximum color value (Maxval), again in ASCII decimal. Must be less than 65536 and more than zero.
     * A single whitespace character (usually a newline).
     * A raster of Height rows, in order from top to bottom. Each row consists of Width pixels, in order from left to right.
     * Each pixel is a triplet of red, green, and blue samples, in that order. Each sample is represented in pure binary by
     * either 1 or 2 bytes. If the Maxval is less than 256, it is 1 byte. Otherwise, it is 2 bytes. The most significant byte is first.
     * */
    public String fileToString()
    {
        System.out.println("pixel filetostring: " + pixels.elementAt(0).elementAt(0).toFileToString());
        StringBuilder sb = new StringBuilder();
        sb.append(magicNumber + "\n");
        sb.append(width + " " + height + "\n");
        sb.append(maxColorVal + "\n");
        for (int h = 0; h < height; h++)
        {
            for (int w = 0; w < width; w++)
            {

                sb.append(pixels.elementAt(h).elementAt(w).toFileToString());
            }
            sb.append("\n");
        }
//        System.out.print(sb.toString());
        return sb.toString();
    }
    /**
     * Invert
     * Every color value for every pixel is changed to its inverse value. For example, 0
     * becomes 255, 240 becomes 15, and 127 becomes 128. Remember that the minimum
     * color value is 0 and the maximum is 255.
     *
     *
     */
    public void invert()
    {
        for (Vector<Pixel> vp : pixels)
        {
            for (Pixel p: vp)
            {
                p.invert(maxColorVal);
            }
        }
    }
    /**
     *  Grayscale
     *      * To convert an image to grayscale, each pixel’s color value is changed to the average of
     *      * the pixel’s red, green, and blue value. For example:
     *      * 3
     *      * Original pixel color values:
     *      * Red: 25 Green: 230 Blue: 122
     *      * Grayscale conversion: (25 + 230 + 122) / 3 = 125 (using integer division)
     *      * Red: 125 Green: 125 Blue: 125
     *
     */
    public void grayscale()
    {
        for (Vector<Pixel> vp : pixels)
        {
            for (Pixel p: vp)
            {
                p.grayscale();
            }
        }
    }
    /**
     * Emboss
     * Assume an image is stored in a structure called image, with “height” x and “width” y.
     * Assume also that image[0][0] refers to the top-left of the image. For every pixel p at
     * position [x][y] (p = image[x][y]), set its red, green, and blue values to the same value by
     * doing the following:
     * Calculate the differences between red, green, and blue values for the pixel and the pixel
     * to its upper left.
     * redDiff = p.redValue - image[x-1][y-1].redValue
     * greenDiff = p.greenValue - image[x-1][y-1].greenValue
     * blueDiff = p.blueValue - image[x-1][y-1].blueValue
     *
     * Find the largest difference (positive or negative). We will call this difference
     * maxDifference. For example, if redDiff is -10 and blueDiff is 5, maxDifference will be -10
     * (not 5 or 10). We then add 128 to maxDifference. If there are multiple equal differences
     * with differing signs (e.g. -3 and 3), favor the red difference first, then green, then blue.
     *
     * v = 128 + maxDifference [which could be positive or negative]
     *
     * If needed, we then scale v to be between 0 and 255 by doing the following:
     * If v < 0, then we set v to 0.
     * If v > 255, then we set v to 255.
     * The pixel’s red, green, and blue values are all set to v.
     * Be sure to account for the situation where r-1 or c-1 is less than 0. V should be 128
     * in this case.
     *
     * Warning: Your code may not exactly match this pseudo-code. Many students face
     * problems at this point because they either don’t look to the upper left pixel (it may not be
     * image[x-1],[y-1] for your code) or they base the differences on already embossed/altered
     * pixels.
     */
    public void emboss()
    {
        //Do emboss on all but absolute north and west pixels
        //For every pixel
        for (int h = height - 1; h > 0; h--) //We start at one because we are skipping the first row and column.
        {
            for (int w = width - 1; w > 0; w--)
            {
                //Element at this point
                //pixels.elementAt(h).elementAt(w);
                //Do a largestdiff functions for this element and it's upper left element
                /**
                 * v = 128 + maxDifference [which could be positive or negative]
                 *
                 * If needed, we then scale v to be between 0 and 255 by doing the following:
                 * If v < 0, then we set v to 0.
                 * If v > 255, then we set v to 255.
                 * The pixel’s red, green, and blue values are all set to v.
                 * Be sure to account for the situation where r-1 or c-1 is less than 0. V should be 128
                 * in this case.
                 *
                 * Warning: Your code may not exactly match this pseudo-code. Many students face
                 * problems at this point because they either don’t look to the upper left pixel (it may not be
                 * image[x-1],[y-1] for your code) or they base the differences on already embossed/altered
                 * pixels.
                 */
                //System.out.println("index: " + h + " " + w);
                Integer largestDiff = pixels.elementAt(h).elementAt(w).maxDifference(pixels.elementAt(h-1).elementAt(w-1));
                Integer v = 128 + largestDiff;
                if (v < 0) { v = 0; }
                else {
                    if (v > 255) { v = 255; }
                }
                pixels.elementAt(h).elementAt(w).setAllTo(v);
                //Do a v=... calculator.
                //These should probably all be pixel functions.
            }
        }
        //Assign all north and west pixels to 128
        for (int w = 0; w < width; w++)
        {
            //System.out.println("index: " + 0 + " " + w);
            pixels.elementAt(0).elementAt(w).setAllTo(128);
        }
        for (int h = 1; h < height; h++)
        {
            //System.out.println("index: " + h + " " + 0);
            pixels.elementAt(h).elementAt(0).setAllTo(128);
        }
    }

    /**
     * Motion blur
     * A number will be provided in the command line arguments if the command is
     * “motionblur.” We will call this number n. n must be greater than 0.
     * The value of each color of each pixel is the average of that color value for n pixels (from
     * the current pixel to n-1) horizontally.
     * Example: if we store the pixels in a 2d array, the motion blur would average each color
     * from pixel[x][y] to pixel[x][y+n-1]
     * 4
     * Be sure to account for the situations where one or more of the values used in computing
     * the average do not exist. For example, if an image has width w and we are considering
     * the pixel on row r, column c, if c + n >= w, then we only average the pixels up to w.
     * @param blendLength is the length of blending required.
     */
    public void motionBlur(int blendLength)
    {


        for (Integer h = 0; h < height; h++)
        {
            for (Integer w = 0; w < width; w++)
            {

                //If it doesn't equal the width, then we know that we don't need to do anything.
                if (!w.equals(width - 1)) {
                    //for each pixel in the file
                    Integer sumR = 0;
                    Integer sumG = 0;
                    Integer sumB = 0;
                    //for each element to the left of it and itself, excluding elements if they are unaccessable (right next to w)
                    Integer counter = 0; //This tells how many we will have summed up.
                    for (int i = w; counter < blendLength; i++) //i will start at the widthIndex
                    {
                        if (i > (width - 1)) {
                            break;
                        } //if the index is greater than the width, stop.
                        //Sum the color values
                        sumR += pixels.elementAt(h).elementAt(i).getRed();
                        sumG += pixels.elementAt(h).elementAt(i).getGreen();
                        sumB += pixels.elementAt(h).elementAt(i).getBlue();
                        counter++;

                    }
                    pixels.elementAt(h).elementAt(w).assignRGB(sumR / counter, sumG / counter, sumB / counter);
                }

                //assign the sumAve to the current pixel for blending.
            }
        }
    }
}

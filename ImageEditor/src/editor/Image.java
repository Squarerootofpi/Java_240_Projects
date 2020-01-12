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
        sb.append(magicNumber + '\n');
        sb.append(width + " " + height + '\n');
        sb.append(maxColorVal + '\n');

        for (int h = 0; h < height; h++)
        {

            for (int w = 0; w < width; w++)
            {

                sb.append(pixels.elementAt(h).elementAt(w).toFileToString());
            }
            sb.append('\n');
        }
        System.out.print(sb.toString());
        return sb.toString();
    }

}

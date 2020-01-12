package editor;

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
    //private String magicNumber;
    public static void main()
    {
        System.out.println("Image main!!");
        //return;
    }
    Image(int w, int h, int maxCoVal, Scanner scanner)
    {
        width = w;
        height = h;
        maxColorVal = maxCoVal;
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

    @Override
    public String toString() {
        return "Image{" +
                "width=" + width +
                ", height=" + height +
                ", maxColorVal=" + maxColorVal +
                ", pixels=" + pixels +
                '}';
    }
}

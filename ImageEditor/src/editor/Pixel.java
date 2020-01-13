package editor;

import java.lang.Math;
/**
 * A raster of Height rows, in order from top to bottom. Each row consists of Width pixels, in order from left to right.
 * Each pixel is a triplet of red, green, and blue samples, in that order. Each sample is represented in pure binary by
 * either 1 or 2 bytes. If the Maxval is less than 256, it is 1 byte. Otherwise, it is 2 bytes. The most significant byte is first.
 *
 */
public class Pixel {
    private Integer red;
    private Integer green;
    private Integer blue;
    public static void main() {
        System.out.println("Image main!!");

        //return;
    }
    Pixel(int r, int g, int b)
    {
        red = r;
        green = g;
        blue = b;
    }

    @Override
    public String toString()
    {
        return "P{" +
                "r=" + red +
                " g=" + green +
                " b=" + blue +
                '}';
    }
    public String toFileToString()
    {
        return red.toString() + " " + green.toString() + " " + blue.toString() + " ";
    }
    public int getRed()
    {
        return red;
    }
    public int getGreen()
    {
        return green;
    }
    public int getBlue()
    {
        return blue;
    }
    public void invert(int maxColorValue)
    {
        red = Math.abs(red - maxColorValue);
        green = Math.abs(green - maxColorValue);
        blue = Math.abs(blue - maxColorValue);
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
     */
    public void grayscale()
    {
        red = green = blue = ((red + green + blue) / 3);
    }
    public void setAllTo(int toSet)
    {
        red = green = blue = toSet;
    }


    /**
     *
     * @param p The pixel the difference is calculated from
     * @return
     */
    /*
    redDiff = p.redValue - image[x-1][y-1].redValue
     * greenDiff = p.greenValue - image[x-1][y-1].greenValue
     * blueDiff = p.blueValue - image[x-1][y-1].blueValue
     *
     * Find the largest difference (positive or negative). We will call this difference
     * maxDifference. For example, if redDiff is -10 and blueDiff is 5, maxDifference will be -10
     * (not 5 or 10). We then add 128 to maxDifference. If there are multiple equal differences
     * with differing signs (e.g. -3 and 3), favor the red difference first, then green, then blue.
     *
     */
    public int maxDifference(Pixel p)
    {
        int redDiff = red - p.getRed();
        int greenDiff = green - p.getGreen();
        int blueDiff = blue - p.getBlue();
        Integer lM = largestMax(redDiff,greenDiff,blueDiff);
        //System.out.println(toString());
        //System.out.println("LDiff: " + lM);
        return lM;
    }
    private Integer largestMax(Integer r, Integer g, Integer b)
    {
        Integer lM;
        Integer r1 = Math.abs(r);
        Integer g1 = Math.abs(g);
        Integer b1 = Math.abs(b);

        if (r1.equals(g1)) //if they're equal, do the first one.
        {
            lM = r;
        }
        else
        {
            lM = maxOfTwo(r,g);
        }
        Integer lM1 = Math.abs(lM);
        if (lM1.equals(b1))
        {
            return lM; //At this point we know that red or green has greatest cardinality as blue, and is returned.
        }
        else //return the max of the last two, since they aren't equal.
            {
            lM = maxOfTwo(lM, b);
        }
        return lM;
    }
    //Returns which int has the greatest cardinality, or returns a if they have the same cardinality
    private Integer maxOfTwo(Integer a, Integer b)
    {
        //if it is equal, (where -1 == 1), then, it will return b.

        return (Math.abs(a) > Math.abs(b) ? a : b);
    }
    public void assignRGB(Integer r, Integer g, Integer b)
    {
        red = r;
        green = g;
        blue = b;
    }
}

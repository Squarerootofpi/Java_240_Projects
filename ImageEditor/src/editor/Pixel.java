package editor;

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
    public String toString() {
        return "Pixel{" +
                "red=" + red +
                ", green=" + green +
                ", blue=" + blue +
                '}';
    }
}

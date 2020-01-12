package editor;

import java.util.Scanner;
import java.io.File; //I guess these are already used lol...
import java.io.FileNotFoundException;

public class ImageEditor
{
    public static void main(String[] args) // Args is size 3, where order is <inputpicture> <outputpicture> <command-to-change>
    {
        System.out.println("Image Editor Open.");
        try
        {
            System.out.println(args[0]);

            File inFile1 = new File(args[0]);
            Scanner scanner = new Scanner(inFile1);
            //Scanner sc = new Scanner(new File(file));
            //regex "((#[^\\n]*\\n)|(\\s+))+"
            //This is the regular expression stated to ignore comments within pictures.
            scanner.useDelimiter("((#[^\\n]*\\n)|(\\s+))+");

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
            String magicNumber = scanner.next();
            System.out.println("Magic number: " + magicNumber);
            int width = scanner.nextInt();
            System.out.println("Width: " + width);
            int height = scanner.nextInt();
            System.out.println("Height: " + height);
            int maxColorVal = scanner.nextInt();
            System.out.println("Max Color Value: " + maxColorVal);
            //Use this to do the massive input into a file.

            int i;
//            while (scanner.hasNextInt())
//            {
//                //System.out.println("loop");
//
//                i = scanner.nextInt();
//                System.out.println(i);
//
//            }
            Image image = new Image(width,height,maxColorVal,scanner);
            System.out.println(image.toString());
            //Testing to see if works lol... This is if file is not ints, but just lines.
//            while (scanner.hasNext()) {
//                String str = scanner.nextLine();
//                System.out.println(str);
//            }

                //Read more: https://javarevisited.blogspot.com/2016/07/how-to-read-text-file-using-scanner-in-java-example-tutorial.html#ixzz6AmY6IG2D
            System.out.println("End of ImageEditorMain");
        }
        catch (Exception ex) {
            System.out.println("ImageEditor failed, error: " + ex.toString());
        }


    }




}

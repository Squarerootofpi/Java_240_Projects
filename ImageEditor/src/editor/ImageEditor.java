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
//            while (scanner.hasNextInt())
//            {
//                //System.out.println("loop");
//
//                i = scanner.nextInt();
//                System.out.println(i);
//
//            }
            Image image = new Image(width,height,maxColorVal,scanner,magicNumber);
            System.out.println(image.toString());

            System.out.println(args[0] + args[1] + args[2]);
            //What the commandline arguments would be.
            //java ImageEditor inputFileName outputFileName {grayscale|invert|emboss|motionblur
            //blurLength}
            switch (args[2]) {
                case "invert":
                    System.out.println("Inverting...");
                    break;
                case "grayscale":
                    System.out.println("Grayscaling...");

                    break;
                case "emboss":
                    System.out.println("Embossing...");


                    break;
                case "motionblur":
                    System.out.println("Motionbluring...");
                    System.out.println(args[3]);
                    break;
                default:
                    System.out.println("Unknown command.... \n USAGE: java ImageEditor in-file out-file " +
                            "(grayscale|invert|emboss|motionblur motion-blur-length)\n");
                    break;
            }
            image.printFile(args[1]);
            System.out.println("End of ImageEditorMain");
        }
        catch (Exception ex) {
            System.out.println("ImageEditor failed, error: " + ex.toString());
            System.out.println("USAGE: java ImageEditor in-file out-file " +
            "(grayscale|invert|emboss|motionblur motion-blur-length)\n");
        }



    }




}

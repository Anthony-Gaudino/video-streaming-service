package kr.tinywind.springbootstreaming.config;

/**
 * Created by tinywind on 2016-06-01.
 */
public class Constants
{
    public static final String VIDEO_ROOT;  //The directory path where movie files are stored

    static {
        final String path          = "/home/user/Videos/";
        final String fileSeparator = System.getProperty("file.separator");
        
        VIDEO_ROOT = path + (path.matches("^.*[" + (fileSeparator.equals("\\") ? "\\\\" : fileSeparator) + "]$") ? "" : fileSeparator);
    }
}
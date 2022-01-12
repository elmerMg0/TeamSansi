package org.bo.list;

import org.apache.commons.io.FileUtils;
import org.bo.list.Item.Dish;
import org.bo.list.Item.ItemDTO;
import org.bo.list.database.ConnectionDatabase;
import org.bo.list.database.ItemDao;
import org.bo.list.database.ItemDaoJDBC;
import org.bo.list.menu.Menu;

import java.io.*;
import java.sql.SQLException;
import java.util.Base64;
import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception {

        /*ItemDao itemDao = new ItemDaoJDBC(ConnectionDatabase.getConnection());
//        ItemDTO pique = new Dish("Planchita","Carne, platano, huevo",95.0);
//        itemDao.insert(pique);
        System.out.println(itemDao.select(0));
//        System.out.println(itemDao.select());*/
        String path = "/home/santihs/Downloads/pique.jpeg";
        byte[] imageBytes = loadImage64(path);
        String base64 = Base64.getEncoder().encodeToString(imageBytes);
        System.out.println(base64);

        File newImage = new File("/home/santihs/Downloads/pique_copy.jpeg");
        byte[] imageBytesCode = Base64.getDecoder().decode(base64);
        FileUtils.writeByteArrayToFile(newImage, imageBytesCode);
    }

    public static byte[] loadImage64(String url) throws Exception {
        File file = new File(url.toString());
        System.out.println(url.toString());
        if (file.exists()) {
            int lenght = (int) file.length();
            BufferedInputStream reader = new BufferedInputStream(new FileInputStream(file));
            byte[] bytes = new byte[lenght];
            reader.read(bytes, 0, lenght);
            reader.close();
            return bytes;
        } else {
            return null;
        }
    }


}

package org.bo.list;

import org.bo.list.database.*;
import org.bo.list.waiter.WaiterDTO;

import java.io.*;
import java.time.LocalDate;

public class Main {

    public static void main(String[] args) throws Exception {

        WaiterDao userDao = new WaiterDaoJDBC(ConnectionDatabase.getConnection());
        WaiterDTO user = new WaiterDTO(9430220, "Santiago Huanacu Sanchez",
                LocalDate.of(2000, 3, 20).toString(),
                63884985, LocalDate.now().toString(), "foto");
        userDao.update(user);
        /*String path = "/home/santihs/Downloads/pique.jpeg";
        byte[] imageBytes = loadImage64(path);
        String base64 = Base64.getEncoder().encodeToString(imageBytes);
        System.out.println(base64);

        File newImage = new File("/home/santihs/Downloads/pique_copy.jpeg");
        byte[] imageBytesCode = Base64.getDecoder().decode(base64);
        FileUtils.writeByteArrayToFile(newImage, imageBytesCode);*/
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

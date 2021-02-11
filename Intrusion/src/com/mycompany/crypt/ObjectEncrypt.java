package com.mycompany.crypt;

import java.io.*;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SealedObject;
import javax.crypto.SecretKey;

public class ObjectEncrypt {
    //
    // Store object in a file for future use.
    //
    public static void writeToFile(String filename, Object object) throws Exception {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;

        try {
            fos = new FileOutputStream(new File(filename));
            oos = new ObjectOutputStream(fos);
            oos.writeObject(object);
            oos.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (oos != null) {
                oos.close();
            }
            if (fos != null) {
                fos.close();
            }
        }
    }
    
    public static void main(String[] args) throws Exception {
        //
        // Generating a temporary key and stire it in a file.
        //
        SecretKey key = KeyGenerator.getInstance("DES").generateKey();
        writeToFile("secretkey.dat", key);

        //
        // Preparing Cipher object for encryption.
        //
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(Cipher.ENCRYPT_MODE, key);

        //
        // Here we seal (encrypt) a simple string message (a string object).
        //
        SealedObject sealedObject = new SealedObject("THIS IS A SECRET MESSAGE!", cipher);

        //
        // Write the object out as a binary file.
        //
        writeToFile("sealed.dat", sealedObject);
    }
}

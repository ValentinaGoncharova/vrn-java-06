package ru.dataart.academy.java;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

public class Calculator {
    /**
     * @param zipFilePath -  path to zip archive with text files
     * @param character   - character to find
     * @return - how many times character is in files
     */
    public Integer getNumberOfChar(String zipFilePath, char character) {
        int charCounter = 0;
        try (BufferedInputStream bufferReader = new BufferedInputStream(new FileInputStream(zipFilePath));
             ZipInputStream zipReader = new ZipInputStream(bufferReader)) {

            ZipFile zipFile = new ZipFile (zipFilePath);
            ZipEntry zipEntry;
            while ((zipEntry = zipReader.getNextEntry()) != null) {
                String fileContent = readContent(zipEntry, zipFile);
                charCounter+= fileContent.chars().filter(x->x==character).count();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return charCounter;
    }

    private String readContent (ZipEntry zipEntry, ZipFile zipFile){
        StringBuilder result = new StringBuilder();
        try (BufferedInputStream zipEntryReader = new BufferedInputStream(zipFile.getInputStream(zipEntry))) {
            byte[] contents = new byte[1024];
            int bytesRead;
            while((bytesRead = zipEntryReader.read(contents)) != -1) {
                result.append(new String(contents, 0, bytesRead));
            }
        } catch (Exception exception){
            exception.printStackTrace();
        } finally {
            return result.toString();
        }
    }

    /**
     * @param zipFilePath - path to zip archive with text files
     * @return - max length
     */

    public Integer getMaxWordLength(String zipFilePath) {
        int maxLengthCounter = 0;
        try (BufferedInputStream bufferReader = new BufferedInputStream(new FileInputStream(zipFilePath));
             ZipInputStream zipReader = new ZipInputStream(bufferReader)) {

            ZipFile zipFile = new ZipFile (zipFilePath);
            ZipEntry zipEntry;

            while ((zipEntry = zipReader.getNextEntry()) != null) {
                try (BufferedInputStream zipEntryReader = new BufferedInputStream(zipFile.getInputStream(zipEntry));
                     Scanner scanner = new Scanner(zipEntryReader)) {
                    while (scanner.hasNext()){
                        String[] strFromFile = scanner.next().split(" ");
                        for (int i = 0; i < strFromFile.length; i++){
                            if(strFromFile[i].length() > maxLengthCounter){
                                maxLengthCounter = strFromFile[i].length();
                            }
                        }
                    }
                } catch (Exception exception){
                    exception.printStackTrace();
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            return maxLengthCounter;
        }
    }
}

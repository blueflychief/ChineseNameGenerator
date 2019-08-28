package com.infinite.chinesename;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;

public class ChineseNameGenerator {

    public static void main(String[] args) {
        int firstNameSize = FirstName.FIRST_NAME.length;
        int boysSecondNameSize = SecondName.SECOND_NAME_BOY.length;
        int girlsSecondNameSize = SecondName.SECOND_NAME_GIRL.length;
        System.out.println("firstNameSize:" + firstNameSize
                + ",boysSecondNameSize:" + boysSecondNameSize
                + ",girlsSecondNameSize:" + girlsSecondNameSize

        );
        URL currentUrl = ChineseNameGenerator.class.getResource("/");
        System.out.println("currentUrl:" + currentUrl);

        File boysFile = generatorFile(currentUrl, "boys_name.json");
        File girlsFile = generatorFile(currentUrl, "girls_name.json");
        int boySize = generateNames(boysFile, true);
        int girlSize = generateNames(girlsFile, false);
        System.out.println("boys names size is:" + boySize);
        System.out.println("girls names size is:" + girlSize);
        System.out.println("all names size is:" + (boySize + girlSize));
    }

    private static File generatorFile(URL currentUrl, String s) {
        File girlsFile = new File(currentUrl.getPath(), s);
        if (girlsFile.exists() && girlsFile.isFile()) {
            girlsFile.delete();
        }
        try {
            girlsFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return girlsFile;
    }


    public static int generateNames(File file, boolean isBoy) {
        int firstNameSize = FirstName.FIRST_NAME.length;
        int boysSecondNameSize = SecondName.SECOND_NAME_BOY.length;
        int girlsSecondNameSize = SecondName.SECOND_NAME_GIRL.length;

        StringBuilder sb = new StringBuilder();
        sb.append("[");
        int currentSize = 0;
        boolean firstToEnd;
        boolean secondToEnd;
        long start = System.currentTimeMillis();
        System.out.println("start generator " + (isBoy ? "boy" : "girl") + " name");
        for (int i = 0; i < firstNameSize; i++) {
            int allSize = isBoy ? boysSecondNameSize : girlsSecondNameSize;
            for (int j = 0; j < allSize; j++) {
                sb.append("\"")
                        .append(FirstName.FIRST_NAME[i]).append(isBoy ?
                        SecondName.SECOND_NAME_BOY[j] : SecondName.SECOND_NAME_GIRL[j])
                        .append("\"");
                firstToEnd = i == (firstNameSize - 1);
                secondToEnd = j == (allSize - 1);
                if (firstToEnd && secondToEnd) {
                    sb.append("]");
                } else {
                    sb.append(",");
                }
                currentSize++;
                if (currentSize % 100 == 0 || (firstToEnd && secondToEnd)) {
                    System.out.println("write name to file");
                    try {
                        FileWriter fileWriter = new FileWriter(file.getName(), true);
                        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                        bufferedWriter.write(sb.toString());
                        bufferedWriter.close();
                        fileWriter.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                        System.out.println("write name exception：" + e.getMessage());
                    }
                    sb.setLength(0);
                }
            }
        }
        long end = System.currentTimeMillis();
        System.out.println("all write finish：cost time：" + (end - start) + "ms，names size is：" + currentSize);
        return currentSize;
    }
}

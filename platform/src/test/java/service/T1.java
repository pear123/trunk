package service;

import org.apache.commons.lang.StringUtils;

import java.io.*;

/**
 * Created with IntelliJ IDEA.
 * User: caol005
 * Date: 15-7-29
 * Time: 下午6:36
 * To change this template use File | Settings | File Templates.
 */
public class T1 {
    private static Integer skuIndex = 4;

    private static Integer safetyInventoryIndex = -11;

    public static void main(String[] args){
        BufferedReader br = null;
        File _file = null;
        String newName = null;

        _file = new File("e:\\Infinity-products-201507281156.csv");
        try {
            br = new BufferedReader(new FileReader(_file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        String line = null;
        StringBuilder sb = new StringBuilder();
        int i = 0;
        try {
            while (null != (line = br.readLine())) {
                String[] lines = line.split("\\|");
                String sku = lines[skuIndex];
                int specialChar_num = StringUtils.countMatches(line, "|") + 1;
    //                        String safetyInventory = null;
    //                        if (lines.length == specialChar_num) {
    //                            safetyInventory = lines[lines.length - 6];
    //                        } else if (lines.length > specialChar_num) {
                String safetyInventory = lines[specialChar_num + safetyInventoryIndex];
    //                        }
                i++;
                if (StringUtils.isEmpty(sku)) {
                    sb.append("At line " + (i + 1) + ", the sku is empty.");
                    continue;
                }
                if (!sku.matches("^\\d{9}$")) {
                    sb.append("At line " + (i + 1) + ", the barcode of item[sku:" + sku + "] is invalid[" + sku + "]");
                    sb.append("\r\n");
                    continue;
                }
                if (StringUtils.isNotBlank(safetyInventory)) {
                    if (!safetyInventory.matches("^\\d+$")
                            || Integer.parseInt(safetyInventory) < 0) {
                        sb.append("At line " + (i + 1) + ", the safety inventory of item[sku:" + sku + "] is invalid[" + safetyInventory + "]");
                        sb.append("\r\n");
                    } else {
                        int safetyInv = Integer.parseInt(safetyInventory);
    //                    productService.generateProduct(sku, safetyInv);
                    }
                } else {
    //                productService.generateProduct(sku, 0);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
//        newName = file.concat(".success.bak.").concat(sdf.format(new Date()));
//        FileUtils.copyFile(_file, new File(newName));
//        logger.debug("backup file [" + _file.getPath() + "] to new file[" + newName + "] successfully.");

        if (sb.length() > 0) {

        }
    }
}

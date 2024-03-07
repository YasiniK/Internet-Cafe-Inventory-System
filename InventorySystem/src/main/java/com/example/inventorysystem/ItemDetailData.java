package com.example.inventorysystem;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ItemDetailData {
    private static List<DealerItemDetail> allItems = new ArrayList<>();

    static {
        loadDataFromFile();
    }

    private static void loadDataFromFile() {
        String fileName = "src/main/resources/dealers.txt";

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 15) {
                    String dealerName = data[0].trim();

                    // First item details
                    String itemName1 = data[3].trim();
                    String itemBrand1 = data[4].trim();
                    int itemQuantity1 = Integer.parseInt(data[5].trim());
                    int itemPrice1 = Integer.parseInt(data[6].trim());

                    // Second item details
                    String itemName2 = data[7].trim();
                    String itemBrand2 = data[8].trim();
                    int itemQuantity2 = Integer.parseInt(data[9].trim());
                    int itemPrice2 = Integer.parseInt(data[10].trim());

                    // Third item details
                    String itemName3 = data[11].trim();
                    String itemBrand3 = data[12].trim();
                    int itemQuantity3 = Integer.parseInt(data[13].trim());
                    int itemPrice3 = Integer.parseInt(data[14].trim());

                    // Create DealerItemDetail objects for each item and add them to allItems list
                    allItems.add(new DealerItemDetail(itemName1, itemBrand1, itemPrice1, itemQuantity1, dealerName));
                    allItems.add(new DealerItemDetail(itemName2, itemBrand2, itemPrice2, itemQuantity2, dealerName));
                    allItems.add(new DealerItemDetail(itemName3, itemBrand3, itemPrice3, itemQuantity3, dealerName));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static List<DealerItemDetail> getItemsByDealer(Dealer dealer) {
        List<DealerItemDetail> itemsByDealer = new ArrayList<>();
        for (DealerItemDetail dealerItem : allItems) {
            if (dealerItem.getDealerName().equals(dealer.getName())) {
                itemsByDealer.add(dealerItem);
            }
        }
        return itemsByDealer;
    }
}

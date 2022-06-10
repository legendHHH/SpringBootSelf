package com.example.demo.customer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * XXX
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2022/5/9
 */
public class ReadCSV {

    public static void main(String[] args) {
        List<Customer> customerList = new ArrayList<>();
        try {
            File file = new File("D://customer.csv");
            BufferedReader reader = new BufferedReader(new FileReader(file));

            String tempString = null;
            int line = 1;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                //第一行跳过
                if (line == 1) {
                    line++;
                    continue;
                }
                tempString = tempString.replace("\"", "");
                String[] split = tempString.split(",");

                Customer customer = new Customer();
                customer.setCustomerID(split[0]);
                customer.setCompanyName(split[1]);
                customer.setContactName(split[2]);
                customer.setContactTitle(split[3]);
                customer.setAddress(split[4]);
                customer.setCity(split[5]);
                customer.setRegion(split[6]);
                customer.setPostalCode(split[7]);
                customer.setCountry(split[8]);
                customer.setPhone(split[9]);
                customer.setFax(split[10]);
                customerList.add(customer);

                line++;
            }
            reader.close();

            System.out.println("文件所有的客户数量：" + customerList.size());

            List<Customer> customers = customerList.stream().filter(customer -> (customer.getCustomerID().charAt(0) == 'A' || customer.getCustomerID().charAt(0) == 'R')).collect(Collectors.toList());
            /*customerList.forEach(customer -> {
                String customerID = customer.getCustomerID();
                char c = customerID.charAt(0);
                if (c == 'A' || c == 'R') {
                    customers.add(customer);
                }
            });*/
            System.out.println("符合条件的所有的客户数量：" + customers.size());
            System.out.println("客户信息列表：" + customers.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

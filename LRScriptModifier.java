package aavvvvvvv;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileModifier {

    public static void main(String[] args) {
        String inputFilePath = "input.txt";
        String outputFilePath = "output.txt";
        String currentDirectory = System.getProperty("user.dir");
        System.out.println("Current Directory: " + currentDirectory);
        System.out.print("\n");
        System.out.print("-ScriptGenerator-");
        System.out.print("");
        System.out.print("\n");
        System.out.print("\n");
        int startTransactionCount = countLinesStartingWith(inputFilePath, "lr_start_transaction");
        int endTransactionCount = countLinesStartingWith(inputFilePath, "lr_end_transaction");

        // Check if counts are equal
        if ((startTransactionCount != endTransactionCount) || (startTransactionCount == 0 || endTransactionCount == 0)) {

        	System.out.println("Error: Number of lr_start_transaction lines does not match number of lr_end_transaction lines.");
            System.out.println("  ");
            System.out.println("Number of lr_start_transaction lines: " + startTransactionCount);
            System.out.println("Number of lr_end_transaction lines: " + endTransactionCount);

            // Print the list of lr_start_transaction and lr_end_transaction lines
            List<String> startTransactionLines = getLinesStartingWith(inputFilePath, "lr_start_transaction");
            List<String> endTransactionLines = getLinesStartingWith(inputFilePath, "lr_end_transaction");
            System.out.println("\nList of lr_start_transaction lines:");
            for (String line : startTransactionLines) {
                System.out.println(line);
            }
            System.out.println("\nList of lr_end_transaction lines:");
            for (String line : endTransactionLines) {
                System.out.println(line);
            }
            return;
       
        }
        
        
     // Dynamically take the application name from the user
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String appName = "";
        try {
            System.out.println("Enter your application name:");
            appName = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        // Handle missing input file
        File inputFile = new File(inputFilePath);
        if (!inputFile.exists()) {
            System.out.println("Input file not found.");
            return;
        }

        modifyAndReplaceFile(inputFilePath, outputFilePath, appName);

        System.out.println("Processing completed successfully.");
    }

    private static int countLinesStartingWith(String filePath, String startString) {
        int count = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().startsWith(startString)) {
                    count++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return count;
    }
    
    private static List<String> getLinesStartingWith(String filePath, String startString) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().startsWith(startString)) {
                    lines.add(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }
    private static void modifyAndReplaceFile(String inputFilePath, String outputFilePath, String appName) {
        // Initialize the beginning lines
        String beginningLines = "\t//set in global variable\n" +
                                "\tint HttpResponseCodeAsInteger;\n" +
                                "\tint TT = 10;\n" +
                                "\tDouble trans_time;\n\n"
                                + "\n"
                                + "\t//set in Action() block\n"
                                + "\t//set socket option\n"
                                + "\tweb_set_sockets_option(\"SSL_VERSION\", \"AUTO\");\n"
                                + "\t//cleanup Cache and Cookies\r\n"
                                +"\tweb_cleanup_cookies();\n"
                                +"\tweb_cache_cleanup();\n"
                                + "\t //set_max_html_param_len\n"
                                + "\t web_set_max_html_param_len(\"9999999\");\n"
                                + "\n"
                                + "\n"
                                + "\n"
                                + "\n"
                                + "\t //---------------  string comparison code template ------web_reg_save_param(\"tamm\",\"LB=responsee\\\":\\\"\",\"RB=\\\",\",LAST)\r\n"
                                + "\t //---------------- if(stricmp(lr_eval_string(\"{tamm}\"),\"stringtocompare\") == 0)\r\n"
                                + "\t // -----------------"+ "\n"+
                                "\t //------------------------------------"+ "\n"+"\n"+
                              //lr_start_transaction("ENTERSTORE");
                              "\tlr_start_transaction(\"" + appName + "_00" + "_" + "Launch" + "\");\n\n" +
                              "\tlr_output_message(\"" + appName + "_00" + "_" + "Launch" + " started -> trans_time:%f : %s\", trans_time, lr_eval_string(\"Vuserid: {Vuserid}, Iteration: {" + "P_Iteration_number" + "},End_time: {" + "date_time" + "\"));\n"+"\n"+"\n"+"\n"+
                               "\t //------------------------------------"+ "\n"+"\n"+"\n"+
                              "\tHttpResponseCodeAsInteger = web_get_int_property(HTTP_INFO_RETURN_CODE);\n\n" +
                "\ttrans_time=lr_get_transaction_duration(\"" + appName + "_" + "00" + "_" + "Launch"+ "\");\n\n" +
                "\tif((atoi(lr_eval_string(\"{textchevkcount_Count_Found_In_Response_Launch}\")) > 0)&&(HttpResponseCodeAsInteger == 200)) {\n" +
                "\t//success -- \n" +
                "\tlr_end_transaction(\"" + appName + "_" + "00" + "_" + "Launch" + "\",LR_PASS); \n" +
                "\tlr_output_message(\"" + appName + "_00" + "_" + "Launch" + " Ended_Successfully -> trans_time:%f : %s\", trans_time, lr_eval_string(\"Vuserid: {Vuserid}, Iteration: {" + "P_Iteration_number" + "},End_time: {" + "date_time" + "\"));\n"+
                
                "\t}\n" +
                "\telse {\n\t//not success -- \n" +
                "\tlr_end_transaction(\"" + appName + "_" + "00" + "_" + "Launch" + "\",LR_FAIL); \n" +
                "\tlr_output_message(\"" + appName + "_00" + "_" + "Launch" + " Ended_Fail -> trans_time:%f : %s\", trans_time, lr_eval_string(\"Vuserid: {Vuserid}, Iteration: {" + "P_Iteration_number" + "},End_time: {" + "date_time" + "\"));\n"+"\n"+
                "\t}\n" +
                "\tlr_think_time(TT);\n"
                + "\n"
                + "\n"
                + "\n"
                + "\t//------------------------------------"+ "\n"
                + "\t//------------------------------------"
                + "\n"
                + "\n"
                ;
        
        String line;
        int startTransactionCounter = 1;
        int endTransactionCounter = 1;
        boolean startTransactionFound = false;
        boolean endTransactionFound = false;
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFilePath));
             BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath))) {

            // Write the beginning lines to the output file
            writer.write(beginningLines);

            // Process the input file line by line
            while ((line = reader.readLine()) != null) {
            	
                if (line.contains("lr_end_transaction")) {
                    endTransactionFound = true;
                    String[] parts = line.split("\\(");
                    String stepName = parts[1].split(",")[0].trim().replaceAll("\"", "");
                    String transactionType = parts[1].split(",")[1].trim().replaceAll("\\);", "");
                    String counterString = String.format("%02d", endTransactionCounter);
                    line = "\t//lr_end_transaction(\"" + stepName + "\",LR_AUTO);\n" +
                           "\tHttpResponseCodeAsInteger = web_get_int_property(HTTP_INFO_RETURN_CODE);\n\n" +
                           "\ttrans_time=lr_get_transaction_duration(\"" + appName + "_" + counterString + "_" + stepName + "\");\n\n" +
                           "\tif((atoi(lr_eval_string(\"{textchevkcount__Count_Found_In_Response_"+stepName+"}\")) > 0)&&(HttpResponseCodeAsInteger == 200)) {\n" +
                           "\t//success -- \n" +
                           "\tlr_end_transaction(\"" + appName + "_" + counterString + "_" + stepName + "\",LR_PASS); \n" +
                           "\tlr_output_message(\"" + appName + "_" + counterString + "_" + stepName + " Ended_Successfully-> trans_time:%f : %s\",trans_time, lr_eval_string(\"Vuserid: {Vuserid}, Iteration: {P_Iteration_number},End_time: {date_time}\"));\n\n"+
                           "\t}\n" +
                           "\telse {\n\t//not success -- \n" +
                           "\tlr_end_transaction(\"" + appName + "_" + counterString + "_" + stepName + "\",LR_FAIL); \n" +
                           "\tlr_output_message(\"" + appName + "_" + counterString + "_" + stepName + " Ended_Fail -> trans_time:%f : %s\",trans_time, lr_eval_string(\"Vuserid: {Vuserid}, Iteration: {P_Iteration_number},End_time: {date_time}\"));\n\n"+
                           "\t}\n\n" +
                           "\tlr_think_time(TT);\n";
                    endTransactionCounter++;
                } else if (line.contains("lr_start_transaction")) {
                    startTransactionFound = true;
                    String stepName = line.split("\\(\"")[1].split("\"\\)")[0];
                    String counterString = String.format("%02d", startTransactionCounter);
                    line = line.replaceFirst("lr_start_transaction\\(\"" + stepName + "\"\\);",
                            "\t//lr_start_transaction(\"" + stepName + "\");\n" +
                            "\tlr_start_transaction(\"" + appName + "_" + counterString + "_" + stepName + "\");\n" +
                            "\tlr_output_message(\"" + appName + "_" + counterString + "_" + stepName + " Started -> trans_time:%f : %s\",trans_time, lr_eval_string(\"Vuserid: {Vuserid}, Iteration: {P_Iteration_number},End_time: {date_time}\"));");
                    startTransactionCounter++;
                }else if (line.contains("lr_think_time")) {
                	
                    // Convert lr_think_time to comment
                    line = "\t// " + line.trim() + ";";
                }
                // Write the modified line to the output file
                writer.write(line + "\n");
            }

            // Handle the case where no lr_start_transaction or lr_end_transaction lines were found
            if (!startTransactionFound) {
                System.out.println("No lr_start_transaction lines found in the input file.");
            }
            if (!endTransactionFound) {
                System.out.println("No lr_end_transaction lines found in the input file.");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
}

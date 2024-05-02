private static void modifyAndReplaceFile(String inputFilePath, String outputFilePath, String appName) {
    // Initialize the beginning lines
    String beginningLines = "//set in global variable\n" +
                            "int HttpResponseCodeAsInteger;\n" +
                            "int TT = 10;\n" +
                            "Double trans_time;\n\n";

    String line;
    int startTransactionCounter = 1;
    int endTransactionCounter = 1;
    try (BufferedReader reader = new BufferedReader(new FileReader(inputFilePath));
         BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath))) {

        // Write the beginning lines to the output file
        writer.write(beginningLines);

        // Process the input file line by line
        while ((line = reader.readLine()) != null) {
            if (line.contains("lr_end_transaction")) {
                String[] parts = line.split("\\(");
                String stepName = parts[1].split(",")[0].trim().replaceAll("\"", "");
                String transactionType = parts[1].split(",")[1].trim().replaceAll("\\);", "");
                String counterString = String.format("%02d", endTransactionCounter);
                line = "//lr_end_transaction(\"" + stepName + "\",LR_AUTO);\n" +
                       "HttpResponseCodeAsInteger = web_get_int_property(HTTP_INFO_RETURN_CODE);\n\n" +
                       "trans_time=lr_get_transaction_duration(\"" + appName + "_" + counterString + "_" + stepName + "\");\n\n" +
                       "if((atoi(lr_eval_string(\"{textchevkcount}\")) > 0)&&(HttpResponseCodeAsInteger == 200)) {\n" +
                       "\t//success -- \n" +
                       "\tlr_end_transaction(\"" + appName + "_" + counterString + "_" + stepName + "\",LR_PASS); \n" +
                       "}\n" +
                       "else { // not success -- \n" +
                       "\tlr_end_transaction(\"" + appName + "_" + counterString + "_" + stepName + "\",LR_FAIL); \n" +
                       "}\n" +
                       "lr_think_time(TT);\n";
                endTransactionCounter++;
            } else if (line.contains("lr_start_transaction")) {
                String stepName = line.split("\\(\"")[1].split("\"\\)")[0];
                String counterString = String.format("%02d", startTransactionCounter);
                line = line.replaceFirst("lr_start_transaction\\(\"" + stepName + "\"\\);",
                        "//lr_start_transaction(\"" + stepName + "\");\n" +
                        "lr_start_transaction(\"" + appName + "_" + counterString + "_" + stepName + "\");\n" +
                        "lr_output_message(\"" + appName + "_" + counterString + "_" + stepName + " Passed for EndTime trans_time:%f : %s\",trans_time, lr_eval_string(\"Iteration : {P_Iteration_number},End_time :{date_time}\"));");
                startTransactionCounter++;
            }
            // Write the modified line to the output file
            writer.write(line + "\n");
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
}

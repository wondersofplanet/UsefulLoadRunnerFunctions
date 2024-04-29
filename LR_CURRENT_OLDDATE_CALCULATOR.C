Action()
{ 
    #include "time.h"
    #include "stdio.h"
    #include "lrs.h"

    int numberOfDays = 10;
    char unique_name[100]; // Assuming a maximum length for the unique name

    // Get current time
    time_t currentEpochTime = time(NULL);
    
    // Calculate time before numberOfDays days (numberOfDays * 24 * 60 * 60 seconds)
    time_t numberOfSecondsInADay = 24 * 60 * 60;
    time_t timeBefore = currentEpochTime - numberOfDays * numberOfSecondsInADay;

    // Print current time in epoch format with three zeros concatenated
    lr_output_message("Current Time (Epoch): %ld000", currentEpochTime);
    // Convert current time to human-readable format
    lr_output_message("Current Time (Human Readable): %s", asctime(localtime(&currentEpochTime)));
    
    // Print time numberOfDays before in epoch format with three zeros concatenated
    lr_output_message("%d Days Before (Epoch): %ld000", numberOfDays, timeBefore);
    // Convert timeBefore to human-readable format
    lr_output_message("%d Days Before (Human Readable): %s", numberOfDays, asctime(localtime(&timeBefore)));

    // Format the unique name using epoch times with three zeros concatenated
    sprintf(unique_name, "%ld000_%ld000_%ld000", (long)currentEpochTime, (long)timeBefore, (long)timeBefore);
    
    // Print the unique name
    lr_output_message("Unique Name: %s", unique_name);

    return 0;
}

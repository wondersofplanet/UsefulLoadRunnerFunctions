#include "time.h"
#include "stdio.h"

    int numberOfDays = 5;

    // Get current time
    time_t currentTime = time(NULL);
    
    // Subtract numberOfDays days (numberOfDays * 24 * 60 * 60 seconds)
    time_t daysBefore = numberOfDays * 24 * 60 * 60;
    time_t timeBefore = currentTime - daysBefore;

    // Print current time in epoch format
    lr_output_message("Current Time (Epoch): %ld", currentTime);
    // Convert current time to human-readable format
    lr_output_message("Current Time (Human Readable): %s", asctime(localtime(&currentTime)));
    
    // Print time numberOfDays before in epoch format
    lr_output_message("%d Days Before (Epoch): %ld", numberOfDays, timeBefore);
    // Convert timeBefore to human-readable format
    lr_output_message("%d Days Before (Human Readable): %s", numberOfDays, asctime(localtime(&timeBefore)));


// Format the unique name using epoch times
    sprintf(unique_name, "%ld_%ld_%ld", currentTime, fiveDaysBefore, fiveDaysBefore);
    
    // Print the unique name
    lr_output_message("Unique Name: %s", unique_name);

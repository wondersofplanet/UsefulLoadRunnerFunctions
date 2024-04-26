#include "time.h"
#include "stdio.h"
/// Get current time
    time_t currentTime = time(NULL);
    // Subtract 5 days (5 * 24 * 60 * 60 seconds)
    time_t fiveDaysBefore = currentTime - (5 * 24 * 60 * 60);
    // Print current time in epoch format
    lr_output_message("Current Time (Epoch): %ld", currentTime);
    // Print time 5 days before in epoch format
    lr_output_message("5 Days Before (Epoch): %ld", fiveDaysBefore);

// Format the unique name using epoch times
    sprintf(unique_name, "%ld_%ld_%ld", currentTime, fiveDaysBefore, fiveDaysBefore);
    
    // Print the unique name
    lr_output_message("Unique Name: %s", unique_name);

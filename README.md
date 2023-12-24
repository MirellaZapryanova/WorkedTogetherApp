Work Together App

Task Summary

The goal of this application is to identify pairs of employees who have worked together on common projects for the longest period of time, along with the duration of each project. The input data is provided in a CSV file, and the program must support various date formats, handle NULL values for the "DateTo" field, and calculate the number of days employees worked together.

Algorithm Overview
CSV Parsing:

Read the input data from the CSV file.
Parse each row to extract Employee ID (EmpID), Project ID (ProjectID), Start Date (DateFrom), and End Date (DateTo).

Date Format Handling:
Support multiple date formats to accommodate variations in the input data.
Convert all date formats to a standard representation for consistent processing.

Data Processing:
Create a data structure (e.g., a dictionary or list of tuples) to store relevant information.
Calculate the number of days employees worked together for each project.
Handle NULL values in the "DateTo" field by considering today's date as the end date.

Identify Longest Collaborations:
Find pairs of employees who worked together on common projects.
Determine the duration of their collaboration for each project.

Output Formatting:
Generate output in the required format, including EmpID1, EmpID2, and the duration of collaboration for each project.

Clean Code Conventions:
Follow clean code principles, including meaningful variable names, modular functions, and comments for clarity.



Usage

Input:
Place the input CSV file in the designated location.
Ensure that the CSV file contains columns in the order: EmpID, ProjectID, DateFrom, DateTo.

Run the Program:
Execute the main program file to analyze employee work history.
Adjust any configuration parameters as needed.

Output:
View the results in the console or an output file, as specified.


The output should include pairs of employees (EmpID1, EmpID2) and the duration of collaboration for each project.

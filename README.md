# FIT3077 Application
By Yet Khai Bryan Wong

## Background
This application helps practitioners monitor the total cholesterol levels and blood pressure levels (systolic and diastolic) of their patients. 

## Technologies
- IDE: Netbeans
- Java Project (Maven)
- Dependencies: gson-2.8.6.jar, okhttp-2.7.5.jar, okio-1.6.0.jar

## How to run
To run the program, run within IDE from the Driver class.

## Features
- Log-in validation of practitioner ID. If the login is invalid, an error message will display.
- Displays all of the logged-in practitioner's patients (with patient ID, name, surname) in table format.
- Practitioners can select a patient from the list, then press the 'Cholesterol'/'Blood Pressure' buttons to create a monitor in the 'Monitors' tabbed pane. Monitors are also viewed in a table format, including the patient ID, patient name, total cholesterol quantity, and last updated time. If a patient does not have a particular observation stored in the FHIR server, then an error message will display to the user. 
- Bar chart for cholesterol observations is displayed.
- Rows with total cholesterol amounts which are above the average total cholesterol quantity of all monitored patients are highlighted in red. If only one patient is being monitored, there is no highlighting.
- Rows with total diastolic or systolic blood pressure amounts which are above the thresholds defined by the user are highlighted in purple.
- Monitors are updated from the server every N seconds given by the input given under the 'Update frequency (seconds)' label. The default value can be changed. If an invalid input is put in, the frequency is reset to the default value.
- Monitors can be removed or added at any point in time.
- Monitored patients can have their details shown in the text area under the 'Patient details' label by selecting the patient row, and pressing the 'Show patient details' button. The details shown include birthdate, gender, and address (city, state, country).
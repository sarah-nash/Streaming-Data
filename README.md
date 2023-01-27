# Streaming-Data
oop project. purpose of this project was to filter through data using a windowed buffer, in preparation for filtering pixels of an image, mean and median of the window. 
 
This project is an object-oriented approach to filtering data using a windowed buffer. Data is read into a sliding window and the window is averaged at each step with mean and median calculations. The averaged data is finally saved to an output file. 

The StreamingDataFilter.java file uses three main parameters: 
- fileName: the name/path of the file containing the data to be streamlined. 
- windowWidth: the size of the window to average over (larger window size will show greater trends, smaller window size will more closely fit the data than show trends.
- column: the (numerical) column of the file containing the data of interest. 

Sample datasets included in this for this project are infrared, TPA weather, and US COVID data.
Project created Spring 2021 Object Oriented Programming with Dr. Caitrin Eaton. 


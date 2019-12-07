# final-project-robocallblocker
Begin the program by compiling the runner class. Please make sure to have your volume on. The GUI window will then open. Program welcomes you and creates your contact list with X number of people. X is randomly generated from 40 to 60 each time the program is ran. These people are taken from a CSV file that has 198 total contacts. You have the option of clicking on a start button to start the first call. 
![Screenshot](https://github.com/UPenn-CIT599/final-project-robocallblocker/blob/master/Screenshots/1.png) 

After clicking on the start button, ringtone music will start playing. A random caller is taken from the csv file. The contact info of the random caller is then compared against your contact list to determine whether or not the caller is spam or not. The program then states if the caller is spam or not. You are given the option to press accept or decline. 
![Screenshot](https://github.com/UPenn-CIT599/final-project-robocallblocker/blob/master/Screenshots/2.png) 

If you press accept, the ringtone music will stop playing. The program will then state that you are now speaking with the caller. You will have the option of pressing the start again to begin a new call. 
![Screenshot](https://github.com/UPenn-CIT599/final-project-robocallblocker/blob/master/Screenshots/3.png)

If you press start again, a new call is created. The new caller is then compared against your contact list as before. If the caller is determined to be spam, then the program will state the caller's phone number instead of the caller's name. You again have the option of pressing accept or decline. 
![Screenshot](https://github.com/UPenn-CIT599/final-project-robocallblocker/blob/master/Screenshots/4.png)

Once you press decline, the ringtone music will stop playing. You are presented with statistics on the number of spam calls and total calls. You are also presented with the new option of pressing block. 
![Screenshot](https://github.com/UPenn-CIT599/final-project-robocallblocker/blob/master/Screenshots/5.png)

After pressing block, the names and numbers of people who have been added to your block list will be displayed and also posted to a file called "BlockList.txt" in the project folder. 
![Screenshot](https://github.com/UPenn-CIT599/final-project-robocallblocker/blob/master/Screenshots/6.png)
![Screenshot](https://github.com/UPenn-CIT599/final-project-robocallblocker/blob/master/Screenshots/7.png)

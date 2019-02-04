# Design Information #
## Thomas Kim ##

1. A user will be able to choose to log in as a specific player or log in as the administrator when starting the application.  For simplicity, any authentication is optional, and you may assume there is a single system running the application.

    **The login process will be handled within the GUI implementation and the user will be handed off as a *Player* or *Administrator***

2. The application will allow players to (1) choose a cryptogram to solve, (2) solve cryptograms, and (3) view the list of player statistics.

    **The *Player* class has access to *CryptogramManager* to both pull a new *Cryptogram* and to solve previously opened *Cryptogram*s.  The *Player* class also has access to the *PlayerMananger* class to retrieve a list of player statistics.**

3. The application will allow the administrator to (1) create a cryptogram, (2) create a player, and (3) view the list of player statistics.

    **The *Administrator* class has access to the *CryptogramManager* class to create new *Cryptogram*s, access to the *PlayerManager* class to create new *Player*s and to view a list of player statistics.**

4. A cryptogram will have a solution (the plaintext phrase) and a maximum number of allowed solution attempts for each of three difficulty categories.  

    **The *Cryptogram* class has a *String* field to store the plaintext solution phrase and an *int[]* array to store the maximum allowed solution attempts for each of the three difficulty categories.  The difficulty categories are saved as *int* values from 0 to 2 (0 - easy, 1 - normal, 2 - hard) that are also used as indices to pull the maximum number of attempts from the *int[]* array.**

5. To add a player, the administrator will enter the following player information:

    a. A first name

    **A *String* variable in the *Player* class stores a first name.**

    b. A last name

    **A *String* variable in the *Player* class stores a last name.**

    c. A unique username

    **A *String* variable in the *Player* class stores a username.**

    d. A difficulty category: easy, normal or hard.

    **An *int* variable in the *Player* class stores the difficulty category in the same schema as described in the explanation to requirement #4.**

6. To add a new cryptogram, the administrator will:
 
    a. Enter a unique cryptogram name.

    **A *String* variable in the *Cryptogram* class stores its unique name.**

    b. Enter a solution (unencoded) phrase.

    **A *String* variable in the *Cryptogram* class stores its unencoded solution phrase.**

    c .Enter the number of allowed incorrect solution attempts for the easy difficulty.

    **Index position 0 in an *int[]* array stores the number of allowed solution attempts for the easy difficulty.**

    d. Enter the number of allowed incorrect solution attempts for the normal difficulty.

    **Index position 1 in an *int[]* array stores the number of allowed solution attempts for the normal difficulty.**

    e. Enter the number of allowed incorrect solution attempts for the hard difficulty.

    **Index position 2 in an *int[]* array stores the number of allowed solution attempts for the hard difficulty.**

    f. Edit any of the above steps as necessary.

    **The *Cryptogram* class contains setter methods for all of the above variables.**

    g. Save the complete cryptogram.

    **Each instance of the *Cryptogram* class is saved in a shared instance of the *CryptogramManager* class.**

    h. View a confirmation that the name assigned to the cryptogram is unique and return to the main menu, or be returned to editing the cryptogram after any error is displayed.

    **The *createCryptogram()* method will check whether a name has already been used or not and notify the user in the GUI.**

7. The encrypted phrase for the cryptogram will be generated for each player starting a new cryptogram by:

    **The encrypted phrase for the *Cryptogram* will be computed in the *generateCryptogram()* method in the *Cryptogram* class.**

    a. Replacing each letter with another letter randomly, so that all of any particular letter are replaced with the same other letter, such as all A’s becoming C’s, and every letter is paired with a unique encrypted letter.

    **The numbers 0-25 will be scrambled and the resulting table will be used to determine the encrypted letter for each original letter. I.e. a value of 2 in index position 0 means that all A’s in the original phrase will be changed to C’s.  Letters will be converted to integers and vice versa using the decimal ASCII code for each letter.**

    b. Preserving the capitalization in the original phrase.

    **Capitalization will be preserved by using the decimal ASCII codes for each letter. I.e. A-Z = 65-90 and a-z = 97-122.**

    c .Preserving any non-alphabetic characters (such as punctuation or white space) unaltered.

    **Non-alphabetic characters will be ignored if their decimal ASCII codes don’t fall in the limited ranges for A-Z and a-z.**

8. To choose and then solve a cryptogram, a player will:

    a. View the list of all unsolved cryptograms alongside their status as in progress or unstarted, and choose a cryptogram to solve.

	**The list of all cryptograms will be retrieved using the *getCryptogramList()* method in the *CryptogramManager* class. and their status can be retrieved using the *getStatus()* method in the *Cryptogram* class.  The list of cryptograms can be sorted by their status to view a list of all unsolved cryptograms.**

	b. View the chosen cryptogram and number of incorrect solution attempts remaining (starting at whatever number is allowed for the player’s difficulty level for that cryptogram).  If the cryptogram has not been played by this player before, the fully encrypted phrase should be generated and displayed.  If the cryptogram is in progress, the previous state of the phrase should be displayed.

	**The *getCurrentAttempts()* method will notify the user through the GUI of the number of incorrect solution attempts remaining.  The *getCurrentState* method will return the previous state of the phrase if the puzzle has been played before or will otherwise return the fully encrypted phase.**

	c. Match the replacement and encrypted letters together, and view the resulting potential solution.

	**This requirement will be fulfilled in the GUI implementation and is not represented in the UML diagram.**

	d. When all letters in the cryptogram are replaced and they are satisfied with the potential solution, submit their answer.

	**The user’s attempt will be checked using the *checkSolution()* method in the *Cryptogram* class.**

	e. Get a result indicating that the solution was successful, or decrementing the number of incorrect solution attempts remaining if it was unsuccessful.

	**The *checkSolution()* method will determine whether the solution was successful or not and, if applicable, will decrement the number of solution attempts.  The user will be notified of the output in the GUI.**
	
	f. At any point, the player may return to the list of unsolved cryptograms to try another.

    **The GUI implementation will fulfill this requirement.**

	g. If the number of incorrect solution attempts reaches zero, they will get a result that the cryptogram game was lost, and this cryptogram will be marked as complete, unavailable for this player to attempt again. They will then return to the menu.

	**The *checkSolution()* method will determine if the number of incorrect attempts reaches zero and notify the user through the GUI while also changing the lost field of that instance of *CryptogramProgress* to reflect the failure.  Then the user will be returned to the menu by the GUI implementation.**

	h. If the player successfully solves the cryptogram, they will get a result that the cryptogram game was won, and this cryptogram will be marked as complete, unavailable for this player to attempt again.  They will then return to the menu.

	**The *checkSolution()* method will determine if solution is correct and notify the user through the GUI while also changing the won field of that instance of *CryptogramProgress* to reflect the success.  Then the user will be returned to the menu by the GUI implementation.**

9. The list of player statistics will display a list of players in descending order of number of cryptograms won.  The entry for each player will show their first name, the number of cryptograms won, and the number of cryptograms lost.  An administrator should also see the username and difficulty status of the player.

	**The *getPlayerStatistics()* method in the *PlayerManager* class will generate the list of players with the necessary data and return it to the user through the GUI.**

10. The user interface must be intuitive and responsive.

    **This requirement will be fulfilled in the GUI implementation and is not represented in the UML diagram.**

11. The performance of the game should be such that students do not experience any considerable lag between their actions and the response of the application.
    
    **This requirement will be fulfilled in the GUI implementation and is not represented in the UML diagram.**
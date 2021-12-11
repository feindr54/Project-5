# Tests

## Test 1: User Sign Up

1. User launches the application.
2. User clicks on the "Sign Up" button.
3. User clicks the email textbox
4. User enters email via the keyboard
5. User selects the password textbox
6. User enters password via the keyboard
7. User selects the confirm button

### Expected result: Application confirms the email is valid, generates an identifier for them, and shows the user their LMS screen.

### Test Status:


## Test 2: User Log in: 

1. User launches the application.
2. User clicks on the "Log In" button.
3. User clicks the username textbox
4. User enters their given identifier via the keyboard
5. User selects the password textbox
6. User enters password via the keyboard
7. User selects the confirm button

### Expected result: Application confirms the validity of username and password and shows the pages.LMS screen.

### Test Status:

## Test 3: View a Course (Student)

1. User must be logged in as a student on the LMS page
2. User selects the dropdown menu
3. User selects an available course
4. User clicks the submit button

### Expected result: Application reads their choice of course and brings up the chosen course's page with the options of a student.

### Test Status:


## Test 4: Access a Course (Teacher)

1. User must be logged in as a teacher on the LMS page
2. User selects the 'access' button from the top panel
3. User selects the dropdown menu
4. User selects an available course
5. User clicks the submit button

### Expected result: Application reads their choice of course and brings up the chosen course's page with the options of a teacher.

### Test Status:


## Test 5: Add a course (Teacher)

1. User must be logged in as a teacher on the LMS page
2. User selects the 'add' button from the top panel
3. User selects the text box
4. User enters the name of the Course
5. User clicks the submit button

### Expected result: The course will be added to the list of available courses to add forums in, and for students to view.

### Test Status:


## Test 6: Edit a course (Teacher)

1. User must be logged in as a teacher on the LMS page
2. User selects the 'edit' button from the top panel
3. User selects the dropdown
4. User selects of the desired course to edit
5. User selects the text box
6. User types the new course name in
7. User clicks the submit button

### Expected result: The name of the desired course will be changed in all aspects of the code.

### Test Status:


## Test 7: Delete a course (Teacher)

1. User must be logged in as a teacher on the LMS page
2. User selects the 'delete' button from the top panel
3. User selects the dropdown
4. User selects of the desired course to delete
5. User clicks the submit button

### Expected result: The course is deleted as an option for use. Any clients currently inside the course will receive an error message and be brought back to the courses screen.

### Test Status:


## Test 8: Navigate to Settings page

1. User can be a teacher or a student, on any applicable screen with the setting option.
2. User selects the settings button

### Expected result: User is brought to the settings page

### Test Status:


## Test 9: Change username

1. User must be on the settings page
2. User selects the text box underneath the label which reads 'Change Username:'
3. User enters the identifier they would like to change to.
4. User selects the confirm button corresponding with the text box.

### Expected result: The user's identifier changes to the new desired name

### Test Status:


## Test 10: Change password

1. User must be on the settings page
2. User selects the text box underneath the label which reads 'Change Password:'
3. User enters the new desired password.
4. User selects the confirm button corresponding with the text box.

### Expected result: The user's password is updated to this new value for any future login attempt

### Test Status:


## Test 11: Go back to previous screen (From settings)

1. User must be on the settings page
2. User selects the back button

### Expected result: The user is brought back to whatever screen they were on directly before going to settings

### Test Status:


## Test 12: Log out (From settings)

1. User must be on the settings page
2. User selects the log out button

### Expected result: The user is brought back to the login page; their information appears in the textboxes.

### Test Status:



## Test 13: Access a forum (Teacher)

1. User must be logged in as a teacher on a Course page
2. User selects the 'access' button
3. Select the forum options dropdown
4. User selects the submit button


### Expected result: The user is brought to the selected forum's page

### Test Status:


## Test 14: Edit a forum (Teacher)

1. User must be logged in as a teacher on a Course page
2. User selects the 'edit' button
3. Select the forum options dropdown
4. User selects a forum from the options
5. Select the textbox the dropdown
6. User types the new forum name in the text box
7. User selects the submit button


### Expected result: The selected forum's topic is changed to the new entered name

### Test Status:


## Test 15: Delete a forum (Teacher)

1. User must be logged in as a teacher on a Course page
2. User selects the 'delete' button
3. Select the forum options dropdown
4. User selects a forum from the options
5. User selects the submit button

### Expected result: The chosen forum and its contents are deleted from the course.

### Test Status:


## Test 16: Add a new forum (Teacher)

1. User must be logged in as a teacher on a Course page
2. User selects the 'add' button
3. Select the textbox beneath the label
4. User enters the desired name for the forum
5. User selects the submit button

### Expected result: A new forum object is created within the course

### Test Status:

## Test 17: Add a forum from a file (Teacher)

1. User must be logged in as a teacher on a Course page
2. User selects the 'add' button
3. Select the textbox beneath the label
4. User enters the file name
5. User selects the submit button

### Expected result: A forum object is created with the contents given

### Test Status:



## Test 18: Access a forum (Student)

1. User must be logged in as a student on a Course page
2. User selects the forum dropdown
3. User selects a forum option
4. User clicks the submit button


### Expected result: The user is brought to the desired forum's page

### Test Status:


## Test 19: Reply to a forum topic (Student)

1. User must be logged in as a student on a forum page
2. User selects the textbox next to "Enter reply:"
3. User enters a reply
4. User selects the reply button


### Expected result: A reply is displayed with the timestamp and a formatted reply

### Test Status:



## Test 20: Comment to a reply on a forum (Student or teacher)

1. User must be on a forum page
2. User clicks on a reply which they would like to comment to
3. User selects the textbox next to "Enter Comment:"
4. User enters the desired comment
5. User clicks the 'comment' button

### Expected result: The comment is displayed directly underneath the reply, with the outline of the comment being green to differentiate

### Test Status:


## Test 21: Upvote a reply on a forum (Student)



### Expected result: 

### Test Status:



## Test 22: Grade a student (Teacher)

1. User must be a teacher on a course page
2. User selects the grade button from the display menu
3. User selects the dropdown beneath "Choose student to grade:"
4. User selects a user from the given options of students
5. User clicks the submit button

### Expected result: The student's grade for that course will be updated in their display

### Test Status:

## Test 23: Handling invalid input on the login screen

If:

1. The User selects confirm without entering anything
2. The user signs up with an invalid email
3. The user attempts to log in with incorrect information
4. The user selects no role while signing up

### Expected result: The user will be given the respective error message and be prompted to try again

### Test Status:











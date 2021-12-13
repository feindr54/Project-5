# Project 5 (Option 1 - Discussion Forum)

## Compilation and Running The Program:

1. Pull all files and directories within the "src" directory into IntelliJ  
2. Compile every java file in the directory except for RunLocalTest.java

## Running the Program

 

## Submissions:

Changxiang Gao - Submitted Vocareum Workspace 

Chloe Click - Submitted Report on Brightspace 
Ahmed Qarooni - Submitted Presentation on Brightspace

## Class Descriptions:

### ActualClient:

The ActualClient class represents the client that the user interacts with. The client connects to the server with a socket. This class is responsible for displaying the GUI for all the other classes to the user.

### ReaderThread

The ReaderThread class is responsible for updating the GUI based on how the user interacts with it and including switching between different pages and displaying error messages when appropriate.

### Server:

The server class is responsible for making changes to the data and saving it. The server contains methods to execute any changes to the data including creating new courses, forums, or replies, deleting courses or forums, editing course names or forum topics, and upvoting. This class handles saving the data by implementing the Serializable interface and using an object output and input stream to write the users and LMS data to different and reading it back, respectively.

### ClientHandler:

The ClientHandler class is the bridge between the client and the server. A ClientHandler object is created for every client that exists and it handles any requests from the client by using the methods from the server class. Then, it sends a response back to the client and broadcasts the changes to all the other clients so the changes are applied universally.

### Request:

The request class is a message sent by the client to the server to perform a specified change or computation. To create a request object, the client specifies an operation, an object, and an operand if necessary.

### Response:

The response class is the counterpart to the request class. This class represents a message sent by the server back to the client.

### User:

The user class creates the object for users, and is the parent class of student and teacher. A user must provide an 
email while signing up, which is then turned into the user's identifier for the LMS. In addition, the user must input 
a password for when they wish to sign back in at another point. The last variable in the constructor is a boolean 
isStudent, which differentiates teacher and student users and makes it easier to cast users into their specific type. 
The getters and setters for each field are utilized within aspects of other classes, including methods 
changeIdentifier and changePassword which lets the user change different parts of their account. This class is necessary for all other aspects of the 
program to run, especially logging in and signing up. It also works to save a lot of redundancies between the student 
and teacher child classes.

### Teacher:

The teacher class is the child of user which sets a user object to have the permissions of a student within the LMS. 
As students and teachers can do different things in the program, this class is needed to give teachers specific 
access to some things and restrict them from doing others. Teacher methods include the ability to 
create a course, create a forum in a given course, delete forums and courses, as well as edit courses and forums to 
change the name of them. These methods are important in the LMS because teachers need the ability to manipulate 
aspects of the overall forum system and change them at will. This class works within the course, forum, and LMS class 
because teachers have the option to change things within each level.

### Student:

The student class is similar to the teacher class in the sense of use. It is also a child of the user class, and is 
used to give students their specified permissions for different actions. Methods include createReply, createComment, upvoteReply, setGrade, getGrade and toString. The first methods exist to let the student make additions to the forum, and saves their work as their 
own so it can be retrieved later. Upvoting is something which any student can do to a reply, and the toString prints 
the student's information. The grading methods are used by teachers to assign grades and by students to view their assigned grades.

### Login:

The login class sets up the login page which is the initial page of the program. The login page allows the user to login with their existing credentials or sign up by making a new account. The user can make a new account by entering a unique email with an '@' sign, a password, and selecting whether they are a teacher or a student. Once the user hits the submit button, the client sends a request to the server to create a new user object for this user and save their login information. After loging in or signing up, the user will be directed to the LMS page for their role.

### LMS:

The LMS class is the class which sets up the main page of the system. It is the highest level of the hierarchy of the 
system. User and course getters and setters allow for other classes to utilize aspects of the LMS, and display which 
user is currently logged in.

### LMSTeacher:

The LMSTeacher class creates an LMSTeacher object which opens the LMS page for a teacher when it is called. The LMSTeacher page is automatically opened when a teacher logs in or signs up from the login page. This page gives the user the option switch using radio buttons to see different menus that do different things like select a course page to open from a list of all courses, create a new course, edit a course name, or delete a course. This page also allows the user to access the settings page using the settings button. The client sends a different request to the server based on what the user wants to do. 

### LMSStudent:

The LMSStudent class works similarly to the LMSTeacher class, however it has less options. The LMSStudent page only gives the user the option to select a course page to open from a list of all courses and allows the user to access the settings page using the settings button. The client sends a different request to the server based on what the user wants to do. 

### Course:

The course class is responsible for creating course objects is the next level within the program's hierarchy. After accessing the LMS, the user can enter a 
course. A course contains forums within them, as well as everything which is therefore within a forum. Methods include getters and setters for the name of the current course, forums within the course, and students who have accessed the course. This class is crucial for the LMS class to function properly.

### CourseTeacher:

The CourseTeacher class creates a CourseTeacher object which opens the course page for a teacher when it is called. The CourseTeacher page is opened when a teacher selects that course in the LMSTeacher page. This page gives the user the option to select a forum page to open from a list of all forums in that course, create a new forum, edit a forum topic, delete a forum, or assign a student in that course a grade for that course. The different menus for these options can be viewed by selecting different radio buttons. This page also allows the user to go back to the LMSTeacher page using the back button or access the settings page using the settings button. The client sends a different request to the server based on what the user wants to do. 

### CourseStudent:

The CourseStudent class functions similarly to the CourseTeacher class but with less choices. The CourseStudent page gives the user the option to select a forum page to open from a list of all forums in that course and view the grade assigned to them for that course. This page also allows the user to go back to the LMSStudent page using the back button or access the settings page using the settings button. The client sends a different request to the server based on what the user wants to do. 

### Forum:

The forum class is the next step after already being inside a course for the program to run. This class allows for 
specific forums within each course. Forums contain a topic, with replies underneath each topic. Each forum also has a 
timestamp which is gathered from using LocalDateTime. Replies are stored in an ArrayList of reply objects which can 
then be retrieved and printed. Getter and setter methods are used for time, the topic, and for replies. 

### ForumTeacher:
The ForumTeacher class holds the GUI for the ForumTeacher page. This page is opened when the user selects this forum on its parent course's page. The forum page displays the topic of the forum at the top of the page with a scrollable panel below it to show all the replies to that topic. When the user selects a reply panel, they can add a comment to that reply using a textbox that appears. The user can also sort all the replies by date/time, number of upvotes, and alphabetical order by the identifiers' of the reply authors.

### SortByDate:
This class is called to sort the forum page by date. It compares replies in a forum page by the time they were posted in a descending order. It is used by the ForumTeacher class.

### SortByName:
Similar to the SortByDate class, this class is called to sort the replies on a forum page by alphabetical order based on the identifier of the user who created the reply. It is called by the Forum Teacher class.

### SortByUpvotes:
SortByUpvotes has a similar function to the previous two classes. This class sorts the replies by number of upvotes in descending order and it's called by the ForumTeacher class.

### ForumStudent:
The ForumStudent class has a similar display to the ForumTeacher page. This page also shows the topic and all the replies underneath it in a scrollable format. The user can create a new reply using the textbox to answer the topic or import an answer from a file using the import button. The user can also select an existing reply to comment on that reply or upvote it once.

### Reply:

The reply class uses the forum class to allow for replies underneath each forum possible. Reply must have an owner 
for the reply (the user who posted it), the forum it belongs to, reply content, the current time of the reply, and 
comments which will go underneath the reply. Getter methods are used for the fields. Upvote methods also are used to 
increment and get the amount of upvotes which a reply has. The last methods are Comparator classes to sort the list of replies. With the 
teacher being able to view replies by different formats, these methods allow for sorting of replies by upvotes, 
identifier, and date. The reply class is vital for the display of each forum and allows for the users' replies to be 
stored, manipulated, and commented under.

### ReplyPanel:

The ReplyPanel class is used to create a panel on a forum page and it represents a reply. The panel shows the content of the reply, the identifier of the user who created the reply, the time the reply was created, and the amount of upvotes the reply has gained. When the reply panel is unselected, its border is black; when it is selected, it is green. This class also contains an ArrayList of comments to this reply and it creates comment panels to display them under the parent reply.

### Comment:

Comment is the class which sets up and formats the comments which can go under the replies on a post. The elements of 
a comment are the content of it, the reply being replied to, the user who made the comment, and the timestamp of the 
comment. There are getter methods to fetch the content or time.

### CommentPanel:

The CommentPanel class creates a panel for a comment which is displayed under its parent reply. The comment panel shows the content of the comment, the identifier of the user who created the comment, and the date/time when the comment was created.

### SettingsGUI:

The SettingsGUI class creates a SettingsGUI object which opens the settings page when it is called. The settings page gives the user the option to change their identifier, change their password, or log out. The client sends a different request to the server based on what the user wants to do. There is a settings button that takes the user to the Settings page on every other page except the login page.

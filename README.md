# Project 5 (Option 1 - Discussion Forum)

## Compilation and Running The Program:

1. Pull all files and directories within the "src" directory into IntelliJ  
2. Compile every java file in the directory except for RunLocalTest.java

## Running the Program

 

## Submissions:

Changxiang Gao - Submitted Vocareum Workspace 

Chloe Click - Submitted Report on Brightspace 

Someone - Submitted Presentation on Brightspace

## Class Descriptions:

### ActualClient:

### Client:

### Request:

### Response:

### Server:

### Login:

### LMSStudent:

### LMSTeacher:

### CourseStudent:

### CourseTeacher:

### ForumStudent:

### ForumTeacher:

### ReplyPanel:

### CommentPanel:

### SortByDate:

### SortByName:

### SortByUpvotes:




### LMS:

The LMS class is the class which sets up the main page of the system. It is the highest level of the hierarchy of the 
system. User and course getters and setters allow for other classes to utilize aspects of the LMS, and display which 
user is currently logged in. Finally, the access method is what displays the main menu. After login, it shows 
different menus for teachers and students, and essentially allows the user to enter the system. The teacher menu for 
the LMS enables teachers to create, access, and delete courses, as well as exit the system. On the other hand, 
students can only access courses or exit the system. When attempting to access for either type, all available courses 
are listed to the user, who then selects one.

### Course:

The Course class is the next level within the program's hierarchy. After accessing the LMS, the user can enter a 
course. A course contains forums within them, as well as everything which is therefore within a forum. Methods 
include getters and setters for the name of the current course, forums within the course, and a 
format to print out all forums. The access method in main.page.Course displays the menu for courses, differentiating between 
teacher and student. Teachers have the ability to create a forum, import a file to utilize in order to create a 
forum, access a forum, or delete one of them. The only option from course for students is to access and enter a 
forum. Both teachers and students can also exit. This class is crucial for the LMS class to function properly.

### Forum:

The forum class is the next step after already being inside a course for the program to run. This class allows for 
specific forums within each course. Forums contain a topic, with replies underneath each topic. Each forum also has a 
timestamp which is gathered from using LocalDateTime. Replies are stored in an ArrayList of reply objects which can 
then be retrieved and printed. Getter and setter methods are used for time, the topic, and for replies. The access 
method in Forum first prints out the forum topic, and all formatted replies underneath it. After that, it displays 
student or teacher menus. The teacher can comment on a reply, view a dashboard with sorted formats by upvotes, or go 
back to courses. Students can reply to the forum topic, comment on another reply, upvote, or go back to the course 
menu. The forum class utilizes reply and comment to format and display the forum, and it crucial for the course to 
work correctly.

### Reply:

The Reply class uses the forum class to allow for replies underneath each forum possible. Reply must have an owner 
for the reply (the user who posted it), the forum it belongs to, reply content, the current time of the reply, and 
comments which will go underneath the reply. Getter methods are used for the fields. Upvote methods also are used to 
increment and get the amount of upvotes which a reply has. The content and comments can be formatted and printed to 
display each reply in a good manner. The last methods are Comparator classes to sort the list of replies. With the 
teacher being able to view replies by different formats, these methods allow for sorting of replies by upvotes, 
identifier, and date. The reply class is vital for the display of each forum and allows for the users' replies to be 
stored, manipulated, and commented under.

### Comment:

Comment is the class which sets up and formats the comments which can go under the replies on a post. The elements of 
a comment are the content of it, the reply being replied to, the user who made the comment, and the timestamp of the 
comment. There are getter methods to fetch just the content or time. Lastly, the printContent method formats and 
prints the comment itself with the other elements. This class works with the reply class to display the forum content 
in a visibly pleasing way as well as stores aspects of a given comment.


### User:

The user class creates the object for users, and is the parent class of student and teacher. A user must provide an 
email while signing up, which is then turned into the user's identifier for the LMS. In addition, the user must input 
a password for when they wish to sign back in at another point. The last variable in the constructor is a boolean 
isStudent, which differentiates teacher and student users and makes it easier to cast users into their specific type. 
The getters and setters for each field are utilized within aspects of other classes, including methods 
changeIdentifier and changePassword which lets the user change different parts of their account. The toString for 
User displays all of the current user's account information. Finally, there are methods which check for valid email 
input, and uniqueness of a new identifier while logging in. This class is necessary for all other aspects of hthe 
program to run, especially logging in and signing up. It also works to save a lot of redundancies between the student 
and teacher child classes.

### Student:

The student class is the child of user which sets a user object to have the permissions of a student within the LMS. 
As students and teachers can do different things in the program, this class is needed to give students specific 
access to some things and restrict them from doing others. Methods include createReply, createComment, upvoteReply, 
and a toString. The first methods exist to let the student make additions to the forum, and saves their work as their 
own so it can be retrieved later. Upvoting is something which any student can do to a reply, and the toString prints 
the student's information.

### Teacher:

The teacher class is similar to the student class in the sense of use. It also is a child of the user class, and is 
used to give teachers their specified permissions for different actions. Teacher methods include the ability to 
create a course, create a forum in a given course, delete forums and courses, as well as edit courses and forums to 
change the name of them. These methods are important in the LMS because teachers need the ability to manipulate 
aspects of the overall forum system and change them at will. This class works within the course, forum, and LMS class 
because teachers have the option to change things within each level.

### SettingsGUI:

The SettingsGUI class creates a SettingsGUI object which opens the Settings page when it is called. The Settings page gives the user the option to change their identifier, change their password, or log out. The client sends a different request to the server based on what the user wants to do. There is a settings button that takes the user to the Settings page on other page except the login page.

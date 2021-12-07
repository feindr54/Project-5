package main.page;

import java.io.Serializable;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.time.*;

import users.*;

/**
 * Project 4 - Forum
 * <p>
 * Description - simulates a forum and allows the user to interact with the forum or go deeper into the pages.LMS.
 *
 * @author Changxiang Gao
 * @version 11/5/2021
 */

public class Forum implements Serializable {
    // fixed String menu 
    //private final String = 

    private Course course; // know who is the parent course
    // a number that indicates which course it is
    private String topic;
    private ArrayList<Reply> replies; // an AL that lists replies from latest to earliest
    private LocalDateTime currentTime;

    public Forum(Course course, String topic) {
        this.course = course;
        this.topic = topic;
        this.replies = new ArrayList<Reply>();
        this.currentTime = LocalDateTime.now();
    }

    public ArrayList<Reply> getReplies() {
        return replies;
    }

    // Setters and getters
    public String getTopic() {
        return this.topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    /**
     * @return currentTime in String form (Fri, Nov 05 2021 20:49:17)
     */
    public String getCurrentTime() {
        return this.currentTime.format(DateTimeFormatter.ofPattern("E, MMM dd yyyy HH:mm:ss"));
    }

    public void setTime(LocalDateTime ldt) {
        this.currentTime = ldt;
    }

    /**
     * Adds a Reply object to the start of the arraylist
     *
     * @param reply
     */
    public void addReply(Reply reply) {
        this.replies.add(0, reply);
    }

    // prints all the replies in the forum and all the comments of each reply
    //
    public void printReplies(User currentUser) {
        for (Reply r : this.replies) {
            /*
            // shows the reply number on top of the reply
            System.out.printf("#%d\n", r.getIdentifier());

            // Rightfully, shows the name and date on the first line
            // shows the content from the second line, and then prints the comments below
            // each reply
            System.out.printf("|%1$-20s %2$s|%n", r.getIdentifier(), r.getCurrentTime());

             */
            r.printContent();
        }
    }

    /**
     * When the method is called, users will "enter" into the current Forum page
     */
    public void access(User currentUser, Scanner sc) throws LogOutException {
        String stringInput = null;

        if (currentUser instanceof Teacher) {
            // prints the topic and asks the user for further action

            int response = 0;
            do {
                System.out.println(topic + "\t\t Created on " + getCurrentTime());
                printReplies(currentUser); // prints all the replies (and their comments) that it contains
                System.out.println("\nWhat do you want to do?");
                System.out.println("Options: ");

                // prints options to the Teacher
                String teacherPrompt = "1. Comment on a student's reply\n2. View Dashboard\n3. pages.Settings \n4. Back\n";
                response = InputSystem.intInput(sc, 4, teacherPrompt);

                switch (response) {
                    case -2:
                        Settings.access(currentUser, sc);
                        break;
                    case -1:
                        break;
                    case 1:
                        String prompt = "Which reply do you want to comment to?";
                        int replyChoice = InputSystem.intInput(sc, replies.size(), prompt);

                        switch (replyChoice) {
                            case -1:
                                break;
                            case -2:
                                Settings.access(currentUser, sc);
                                break;
                            default:
                                Reply chosenReply = replies.get(replies.size() - replyChoice);

                                // reprints the reply for the user
                                chosenReply.printContent();

                                String commentPrompt = "\nEnter the contents of the comment:";
                                String content = InputSystem.stringInput(sc, commentPrompt);

                                if (content == null) {
                                    break;
                                } else {
                                    // calls the createComment of the current User
                                    ((Teacher) currentUser).createComment(chosenReply, content);
                                    break;
                                }
                        }

                        break;
                    case 2:
                        // a dummy arraylist
                        ArrayList<Reply> dummy = (ArrayList<Reply>) replies.clone();

                        //query for type of sorting
                        StringBuilder sb = new StringBuilder("How would you like the replies to be sorted?");
                        sb.append("\n1. By Date");
                        sb.append("\n2. By Upvotes");
                        sb.append("\n3. By Name\n");

                        // get the response from the user
                        int response1 = InputSystem.intInput(sc, 3, sb.toString());
                        switch (response1) {
                            // by descending date (latest to earliest)
                            case -1:
                                break;
                            case -2:
                                Settings.access(currentUser, sc);
                            case 1:
                                dummy.sort(new SortByDate());
                                System.out.println("Replies sorted by date:");
                                System.out.println("\n" + topic + "\t\t Created on " + getCurrentTime());
                                for (Reply reply : dummy) {
                                    reply.printContent();
                                }
                                System.out.println("\n_______________________________\n");
                                System.out.println("Original forum:");
                                break;
                            // by descending upvotes
                            case 2:
                                dummy.sort(new SortByUpvotes());
                                System.out.println("Replies sorted by upvotes:");
                                System.out.println("\n" + topic + "\t\t Created on " + getCurrentTime());
                                for (Reply reply : dummy) {
                                    reply.printContent();
                                }
                                System.out.println("\n_____________________________\n");
                                System.out.println("Original forum:");
                                break;
                            // by alphabetical order of usernames (ascending order)
                            case 3:
                                dummy.sort(new SortByName());
                                System.out.println("Replies sorted by name:");
                                System.out.println("\n" + topic + "\t\t Created on " + getCurrentTime());

                                for (Reply reply : dummy) {
                                    reply.printContent();
                                }

                                System.out.println("\n_____________________________\n");
                                System.out.println("Original forum:");

                                break;
                            default:
                                break;
                        }
                        break;
                    case 3:
                        Settings.access(currentUser, sc);
                        break;
                    case 4:
                        break;
                }
            } while (response != -1 && response != 4);
        } else if (currentUser instanceof Student) {
            int responseStud = 0;

            // prints the topic and asks the user for further action


            do {
                System.out.println(topic + "\t\t Created on " + getCurrentTime());
                printReplies(currentUser); // prints all the replies (and their comments) that it contains
                System.out.println("\nWhat do you want to do?");
                System.out.println("Options: ");

                // prints options to the students
                String prompt = "1. Reply to the forum post\n2. Comment on a student's reply\n3. Upvote a reply\n" +
                        "4. pages.Settings\n5. Back\n";

                responseStud = InputSystem.intInput(sc, 5, prompt);
                switch (responseStud) {

                    case 1:
                        System.out.println("Enter the contents of the reply:");
                        String content = InputSystem.stringInput(sc, "");
                        ((Student) currentUser).createReply(this, content);
                        break;
                    case 2:
                        String commentPrompt = "Which reply do you want to comment to?";
                        printReplies(currentUser);
                        int choice = InputSystem.intInput(sc, this.replies.size(), commentPrompt);
                        do {
                            switch (choice) {
                                case -1:
                                    break;
                                case -2:
                                    Settings.access(currentUser, sc);
                                    break;
                                default:
                                    // selects the reply from the back of the list (based on identifier)
                                    Reply chosenReply = replies.get(replies.size() - choice);
                                    String commentContentPrompt = "Enter the contents of the comment:";
                                    String commentContent = InputSystem.stringInput(sc, commentContentPrompt);
                                    if (commentContent == null) {
                                        break;
                                    } else {
                                        ((Student) currentUser).createComment(chosenReply, commentContent);
                                    }

                            }
                        } while (choice == -2);


                        break;

                    // upvoting system
                    case 3:
                        String promptUpvote = "Which reply do you want to upvote?";

                        int upvoteChoice = InputSystem.intInput(sc, replies.size(), promptUpvote);
                        do {
                            switch (upvoteChoice) {
                                case -1:
                                    break;
                                case -2:
                                    Settings.access(currentUser, sc);
                                    break;
                                default:
                                    // calls the createComment of the current User, and inputs the reply as param
                                    // minus 1 since beginning index of AL is 0
                                    ((Student) currentUser).upvoteReply(replies.get(replies.size() - upvoteChoice));

                            }
                        } while (upvoteChoice == -2);

                        break;
                    case 4:
                        Settings.access(currentUser, sc);
                        break;
                    case 5:
                        break;
                    default:
                        System.out.println("Please input a correct option");
                }
            } while (responseStud != 5);

        }
    }
}

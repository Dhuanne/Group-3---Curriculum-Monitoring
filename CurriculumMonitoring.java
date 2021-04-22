import breya.CurriculumMonitor;

import java.lang.*;
import java.io.*;
import java.util.*;
public class CurriculumMonitoring {
    static Scanner kbd = new Scanner(System.in);
    static String pathFile;
    static int choice;
    public CurriculumMonitoring(String pf){
    pathFile = pf;
    }
    public static void main(String[] args) {
        CurriculumMonitoring run;
        try {
            run = new CurriculumMonitoring("Courses.csv");
            run.run();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            quit();
        }
    }

    public static void run() {
        enterFile();
        do {
            try {
                showMenu();
                System.out.print("Enter a number: ");
                choice = Integer.parseInt(kbd.nextLine());
                if (choice < 1 || choice > 13)
                    throw new UserDefinedException("\nNumber must be from 1 to 13...\n");
                switch (choice) {
                    case 1:
                        showSubjects();
                        break;
                    case 2:
                        showSubjectsWGrades();
                        break;
                    case 3:
                        enterGrades();
                        break;
                    case 4:
                        editCourse();
                        break;
                    case 5:
                        shifterProgram();
                        break;
                    case 6:
                        addFinishedCourse();
                        break;
                    case 7:
                        sortCourseNum();
                        break;
                    case 8:
                        sortDescTitle();
                        break;
                    case 9:
                        sortUnit();
                        break;
                    case 10:
                        sortGrades();
                        break;
                    case 11:
                        showGPA();
                        break;
                    case 12:
                        loadAnotherFile();
                        break;
                    case 13:
                        return;
                }
            } catch (UserDefinedException c) {
                System.out.println(c.getMessage());
                choice = 0;
            } catch (NumberFormatException number) {
                System.out.println("\nYou have entered an invalid integer... \nPlease enter an integer...\n");
                choice = 0;
            }

        } while (choice != 13);
    }

    public static void showMenu() {
        System.out.println("""
                My Checklist Management
                <1> Show subjects for each school term
                <2> Show subjects with grades for each term
                <3> Enter grades for subjects recently finished
                <4> Edit a course
                <5> Shifter from another program
                <6> Add a finished course
                <7> Show subjects for each school term sorted by course number
                <8> Show subjects for each school term sorted by descriptive title
                <9> Show subjects for each school term sorted by units
                <10> Show subjects for each school term sorted by grades
                <11> Show GPA
                <12> Load another data file
                <13> Quit""");

    }
    public static void showSubjects(){
        ArrayList<Curriculum> course = new ArrayList<Curriculum>();
        course = getData(course, kbd);
        printCourse(course);
    }

    public static void showSubjectsWGrades(){
        ArrayList<Curriculum> course = new ArrayList<Curriculum>();
        course = getData(course, kbd);
        printCourse(course);
    }

    public static void enterGrades(){
        boolean invalid = false;
        byte year = 0;
        byte term = 0;
        String currentNumber = "";
        ArrayList<Curriculum> course;
        year = enterYearLevel(year, invalid);
        term = enterTerm(term, invalid);
        course = printCourse(year, term);
        Curriculum cc = new Curriculum(course);
        do{
            try {
                currentNumber = enterCourseNum(currentNumber);
                invalid = false;
                for (int i = 0; i < course.size(); i++) {
                    if (currentNumber.equalsIgnoreCase(course.get(i).getCourseNum())&& year == course.get(i).getYearLevel() && term == course.get(i).getTerm()) {
                       boolean condition = false;
                        do{
                           try{
                        System.out.print("Enter the new grade: ");
                        double newGrade = Double.parseDouble(kbd.nextLine());
                        if(newGrade < 68 )
                            course.get(i).setGrades(74);
                        else if(newGrade > 99)
                            course.get(i).setGrades(99);
                        else
                            course.get(i).setGrades(newGrade);
                        i = i + course.size();
                        condition = false;
                           }catch(NumberFormatException n){
                               System.out.println("\nYou have entered an invalid integer... \nPlease enter an integer...\n");
                               condition = true;
                           }
                       }while(condition);
                    }
                    else if(i == course.size() - 1) {
                        throw new UserDefinedException("\nNothing matches that course number...\n");
                }
                }

            }catch(UserDefinedException u){
                System.out.println(u.getMessage());
                invalid = true;
            }
        }while(invalid);
                writeData(course);
                printCourse(year, term);
    }
    public static void editCourse(){
        boolean invalid = false;
        byte year = 0;
        byte term = 0;
        String currentNumber = "";
        ArrayList<Curriculum> course;
        year = enterYearLevel(year, invalid);
        term = enterTerm(term, invalid);
        course = printCourse(year, term);
        Curriculum cc = new Curriculum(course);
        int editingChoice = 0;

                do{
                    try {
                        currentNumber = enterCourseNum(currentNumber);
                        invalid = false;
                        //For loop iterates through the course list and finds the matching course number
                        for (int i = 0; i < course.size(); i++) {
                                    if (currentNumber.equalsIgnoreCase(course.get(i).getCourseNum()) && year == course.get(i).getYearLevel() && term == course.get(i).getTerm()) {
                                        editingChoice = editChoice(editingChoice, invalid);
                                        invalid = false;
                                        boolean condition = false;
                                        do {
                                            try {
                                                if (editingChoice == 1) {
                                                    System.out.print("Enter the new course number: ");
                                                    course.get(i).setCourseNum(kbd.nextLine());
                                                    i = i + course.size();
                                                    condition = false;
                                                } else if (editingChoice == 2) {
                                                    System.out.print("Enter the new Description Title: ");
                                                    course.get(i).setDescTitle(kbd.nextLine());
                                                    i = i + course.size();
                                                    condition = false;
                                                } else if (editingChoice == 3) {
                                                    System.out.print("Enter the new units: ");
                                                    float u = Float.parseFloat(kbd.nextLine());
                                                    if(u < 1)
                                                        throw new UserDefinedException("\nUnits cannot be less than 1...\n");
                                                    course.get(i).setUnits(u);
                                                    i = i + course.size();
                                                    condition = false;
                                                } else if (editingChoice == 4) {
                                                    System.out.print("Enter the new year level: ");
                                                    byte y = Byte.parseByte(kbd.nextLine());
                                                    if(y < 1 || y > 4)
                                                        throw new UserDefinedException("\nYour curriculum consists of only 4 years...\n" +
                                                                "\nEnter year from 1 to 4...\n");
                                                    course.get(i).setYearLevel(y);
                                                    i = i + course.size();
                                                    condition = false;
                                                } else {
                                                    System.out.print("Enter the new term: ");
                                                    byte t = Byte.parseByte(kbd.nextLine());
                                                    if(t < 1 || t > 3)
                                                        throw new UserDefinedException("\nYour terms consists of only three..." +
                                                                "\nEnter 1 for first term, 2 for second term, and 3 for short term.\n");
                                                    course.get(i).setTerm(t);
                                                    i = i + course.size();
                                                    condition = false;
                                                }
                                            }catch(NumberFormatException n){
                                                System.out.println("\nYou have entered an invalid integer... \nPlease enter an integer...\n");
                                                condition = true;
                                            }catch(UserDefinedException u){
                                                System.out.println(u.getMessage());
                                                condition = true;
                                            }
                                        }while(condition);

                                    } else if (i == course.size() - 1)
                                throw new UserDefinedException("\nNothing matches that course number...\n");
                        }
                    }catch(UserDefinedException u){
                        System.out.println(u.getMessage());
                        invalid = true;
                    }
                }while(invalid);
        writeData(course);
        printCourse(year, term);

    }
    public static void quit() {
        System.out.println("Thank you for using this program, do well on your study!");
        System.exit(0);
    }
    public static ArrayList<Curriculum> printCourse(int y, int t){
        ArrayList<Curriculum> course = new ArrayList<Curriculum>();
        course = getData(course, kbd);
        Curriculum cc = new Curriculum(course);
        if(choice == 4)
            System.out.println("-----------------------------------------------------------------------------------" +
                    "-----------------------------------");
        else
            System.out.println("-----------------------------------------------------------------------------------" +
                    "-------------------------------------------------");
        System.out.println("Year = " + yearLevel(y) + " Term = " + term(t));
        if(choice == 4)
            System.out.printf("%-13s%-100s%-6s%n", "Course NO.","Descriptive title", "Units");
        else
            System.out.printf("%-13s%-100s%-6s%-8s%n", "Course NO.","Descriptive title", "Units", "Grades");
        if(choice == 4)
            System.out.println("-----------------------------------------------------------------------------------" +
                    "-----------------------------------");
        else
            System.out.println("-----------------------------------------------------------------------------------" +
                    "-------------------------------------------------");
        for (int index = 0; index<course.size(); index++) {
            cc.setIndex(index);
            if(course.get(index).getYearLevel()==y && course.get(index).getTerm()==t)
                if(choice == 4)
                    System.out.print(cc);
                else
                    System.out.print(cc.toStringWithGrades());

        }
        if(choice == 4)
            System.out.println("***********************************************************************************" +
                    "***********************************");
        else
            System.out.println("***********************************************************************************" +
                    "****************************************************");
        return course;
    }

    public static void printCourse(ArrayList<Curriculum> course){
        Curriculum cc = new Curriculum(course);
        int y = 4;
        int t = 3;

        for (byte yearLevel = 1; yearLevel <= y; yearLevel++){
            for (byte term = 1; term <= t; term++){
                if(choice == 1)
                System.out.println("-----------------------------------------------------------------------------------" +
                        "-----------------------------------");
                else
                    System.out.println("-----------------------------------------------------------------------------------" +
                            "-------------------------------------------------");
                System.out.println("Year = " + yearLevel(yearLevel) + " Term = " + term(term));
                if(choice == 1)
                System.out.printf("%-13s%-100s%-6s%n", "Course NO.","Descriptive title", "Units");
                else
                    System.out.printf("%-13s%-100s%-6s%-8s%n", "Course NO.","Descriptive title", "Units", "Grades");
                if(choice == 1)
                    System.out.println("-----------------------------------------------------------------------------------" +
                            "-----------------------------------");
                else
                    System.out.println("-----------------------------------------------------------------------------------" +
                            "-------------------------------------------------");
                for (int index = 0; index<course.size(); index++) {
                    cc.setIndex(index);
                    if(course.get(index).getYearLevel()==yearLevel && course.get(index).getTerm()==term)
                        if(choice == 1)
                            System.out.print(cc);
                        else
                            System.out.print(cc.toStringWithGrades());
                }
                if(choice == 1)
                System.out.println("***********************************************************************************" +
                        "***********************************");
                else
                    System.out.println("***********************************************************************************" +
                        "****************************************************");
                System.out.println("Press enter key to see courses for the next term.");
                kbd.nextLine();
            }
        }
    }
    public static String yearLevel(int y){
        int year = y;
        if(year == 1)
            return "First Year";
        else if(year == 2)
            return "Second Year";
        else if(year == 3)
            return "Third Year";
        else
            return "Fourth Year";
    }
    public static String term(int t){
        int term = t;
        if(term == 1)
            return "First Term";
        else if(term == 2)
            return "Second Term";
        else
            return "Short Term";
    }
    public static ArrayList<Curriculum> getData(ArrayList<Curriculum> course, Scanner kbd){
        boolean invalid = false;
        do {
            try {
                kbd = new Scanner(new File(pathFile));
                invalid = false;
            } catch (IOException e) {
                System.out.println(e.getMessage());
                System.out.print("Change the path file: ");
                pathFile = kbd.nextLine();
                invalid = true;
            }
        }while(invalid);
        kbd.nextLine();  // Skips the column headings and goes straight to the data
        while(kbd.hasNextLine()){
            String data[] = kbd.nextLine().split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
            byte y = Byte.parseByte(data[0]);
            byte t = Byte.parseByte(data[1]);
            String cN = data[2];
            String dT = data[3];
            float u = Float.parseFloat(data[4]);
            double g = Double.parseDouble(data[5]);
            course.add(new Curriculum(cN, dT, u, g, y, t));
        }
        return course;
    }
    public static void writeData(ArrayList<Curriculum> course) {
        Curriculum cc = new Curriculum(course);
        String string = "year,\"term(1 = first term, 2=second term, 3=short term)\",course number,descriptive title,unit,grade" + '\n';
        int index = -1;
        for (Curriculum c : course) {
            index++;
            cc.setIndex(index);
            string = string + c.toCSV() + "\n";
        }
        String fileName = null;
        boolean condition = false;
        do {
            try {
                System.out.print("Enter the name of the file to be saved: ");
                fileName = kbd.nextLine();
                File file = new File(fileName);
                FileWriter writer = null;
                if (file.exists()) {
                    if (fileExists() == false) {
                        writer = new FileWriter(file, true);
                        condition = true;
                    } else{
                        writer = new FileWriter(file, false);
                        condition = false;
                    }
                }
                writer.write(string);
                writer.close();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }while(condition);
        loadFile(fileName);
    }
    public static byte enterYearLevel(byte year, boolean invalid) {
        do {
            try {
                System.out.print("What year level do you want to edit?: ");
                year = Byte.parseByte(kbd.nextLine());
                invalid = false;
                if (year > 4 || year < 1)
                    throw new UserDefinedException("\nYour curriculum consists of only 4 years..." +
                            "\nEnter year from 1 to 4...\n");
            } catch (UserDefinedException choice) {
                System.out.println(choice.getMessage());
                invalid = true;
            }catch(NumberFormatException n) {
                System.out.println("\nYou have entered an invalid integer... \nPlease enter an integer...\n");
                invalid = true;
            }
        } while (invalid);
        return year;
    }
    public static boolean fileExists(){
        int fileChoice = 0;
        boolean condition = false;
        do{
            try{
        System.out.print("File already exists. Do you want to overwrite the file?"+
                "\n<1> Yes" +
                "\n<2> No" +
                "\nEnter a number: ");
        fileChoice = Integer.parseInt(kbd.nextLine());
        condition = false;
        if (fileChoice < 1 || fileChoice > 2)
            throw new UserDefinedException("\nNumber must be from 1 or 2...\n");
    } catch (NumberFormatException number) {
        System.out.println("\nYou have entered an invalid integer... \nPlease enter an integer...\n");
        condition = true;
    }catch (UserDefinedException u){
        System.out.println(u.getMessage());
        condition = true;;
    }
            if(fileChoice == 1)
                return true;
            else
                return false;
} while (condition);

    }

    public static byte enterTerm(byte term, boolean invalid){
        do {
            try{
                System.out.print("What term do you want to edit?: ");
                term = Byte.parseByte(kbd.nextLine());
                invalid = false;
                if (term > 3 || term < 1)
                    throw new UserDefinedException("\nYour terms consists of only three..." +
                            "\nEnter 1 for first term, 2 for second term, and 3 for short term.\n");
            }catch(UserDefinedException choice){
                System.out.println(choice.getMessage());
                invalid = true;
            }catch(NumberFormatException n) {
                System.out.println("\nYou have entered an invalid integer... \nPlease enter an integer...\n");
                invalid = true;
            }
        }while(invalid);
        return term;
    }
    public static String enterCourseNum(String courseNum){
                System.out.print("What is the number of the course: ");
                courseNum = kbd.nextLine();
                return courseNum;
    }
    public static void shifterProgram() {
        byte y = 0;
        byte t = 0;
        boolean invalid = false;
        y = enterYearLevel(y, invalid);
        t = enterTerm(t, invalid);
        addCourse(y, t);
    }
    public static void addCourse(byte y, byte t){
        ArrayList<Curriculum> course = new ArrayList<Curriculum>();
        System.out.print("Enter the course number: ");
        String cN = kbd.nextLine().toUpperCase();
        System.out.print("Enter the descriptive title: ");
        String dT = kbd.nextLine();
        System.out.print("Enter the number of units: ");
        float u = Float.parseFloat(kbd.nextLine());
        System.out.print("Enter your final grade: ");
        double g = Double.parseDouble(kbd.nextLine());

        course = getData(course,  kbd);
        course.add(new Curriculum(cN, dT, u, g, y, t));
        Collections.sort(course);
        writeData(course);
        printCourse(y, t);
    }
    public static int editChoice(int editingChoice, boolean invalid){
        do{
            try{
                System.out.println("What would you like to edit?");
                System.out.println("1. Course Number");
                System.out.println("2. Description Title");
                System.out.println("3. Units");
                System.out.println("4. Year Level");
                System.out.println("5. Term");
                System.out.print("Enter number: ");
                editingChoice = Integer.parseInt(kbd.nextLine());
                invalid = false;
                if (editingChoice < 1 || editingChoice > 5)
                    throw new UserDefinedException("\nNumber must be from 1 to 5...\n");
            }catch(UserDefinedException u){
                System.out.println(u.getMessage());
                invalid = true;
            }catch(NumberFormatException n){
                System.out.println("\nYou have entered an invalid integer... \nPlease enter an integer...\n");
                invalid = true;
            }
        }while(invalid);

        return editingChoice;
    }
    public static void addFinishedCourse(){
        byte year = 0;
        byte term = 0;
        boolean invalid = false;
        year = enterYearLevel(year, invalid);
        term = enterTerm(term, invalid);
        addCourse(year, term);
    }
    public static void sortCourseNum(){

    }
    public static void sortDescTitle(){

    }
    public static void sortUnit(){

    }
    public static void sortGrades(){

    }
    public static void showGPA(){

    }
    public static void loadAnotherFile(){
        System.out.print("Enter the path file: ");
        String pathfile = kbd.nextLine();
        new CurriculumMonitoring(pathfile);
    }
    public static void loadFile(String pathfile){
        int fileChoice = 0;
        boolean condition = false;
        if(pathfile.equalsIgnoreCase(pathFile))
            return;
        do {
            try {
                System.out.print("Do you want to load that file?" +
                        "\n<1> Yes" +
                        "\n<2> No" +
                        "\nEnter a number: ");
                fileChoice = Integer.parseInt(kbd.nextLine());
                condition = false;
                if (fileChoice < 1 || fileChoice > 2)
                    throw new UserDefinedException("\nNumber must be from 1 or 2...\n");
                if(fileChoice == 1) {
                    new CurriculumMonitoring(pathfile);
                    condition = false;
                }
            } catch (NumberFormatException number) {
                System.out.println("\nYou have entered an invalid integer... \nPlease enter an integer...\n");
                condition = true;
            }catch (UserDefinedException u){
                System.out.println(u.getMessage());
                condition = true;;
            }
        } while (condition);
    }
    public static void enterFile(){
        int fileChoice = 0;
        boolean condition = false;
        do {
            try {
                System.out.print("Load default data file record or your new data file record?" +
                        "\n<1> New data file" +
                        "\n<2> Default data file" +
                        "\nEnter a number: ");
                fileChoice = Integer.parseInt(kbd.nextLine());
                condition = false;
                if (fileChoice < 1 || fileChoice > 2)
                    throw new UserDefinedException("\nNumber must be from 1 or 2...\n");
                if(fileChoice == 1) {
                    loadAnotherFile();
                    condition = false;
                }else{
                    new CurriculumMonitoring("Courses.csv");
                }
            } catch (NumberFormatException number) {
                System.out.println("\nYou have entered an invalid integer... \nPlease enter an integer...\n");
                condition = true;
            }catch (UserDefinedException u){
                System.out.println(u.getMessage());
                condition = true;;
            }
        } while (condition);
    }
    public static void editElective(){

    }
}

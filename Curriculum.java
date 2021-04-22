import java.io.*;
import java.util.*;


public class Curriculum implements Comparable<Curriculum> {

    static int index;
    private String courseNum;
    private String descTitle;
    private float units;
    private double grades;
    private byte yearLevel;
    private byte term;
    static ArrayList<Curriculum> course = new ArrayList<Curriculum>();

    public Curriculum(){
            courseNum="";
            descTitle="";
            units= 0;
            yearLevel=(byte) 0;
            term=(byte) 0;
            grades=0;

    }
    public Curriculum(ArrayList<Curriculum> c){
        course = c;
    }
    public Curriculum(String cN, String dT, float u, double g, byte yL, byte t){
        courseNum = cN;
        descTitle = dT;
        units = u;
        grades = g;
        yearLevel = yL;
        term = t;
    }
    public  void setIndex(int i){
        index = i;
    }
    public int getIndex(){
        return index;
    }
    public void setCourseNum(String cN){
        courseNum = cN;
    }
    public void setDescTitle(String dT){
        descTitle = dT;
    }
    public void setUnits(float u){
        units = u;
    }
    public void setGrades(double g){
        grades = g;
    }
    public void setYearLevel(byte yL){
        yearLevel = yL;
    }
    public void setTerm(byte t){
        term = t;
    }
    public String getCourseNum(){
        return courseNum;
    }
    public String getDescTitle(){
        return descTitle;
    }
    public float getUnits(){
        return units;
    }
    public double getGrades(){
        return grades;
    }
    public byte getYearLevel(){
        return yearLevel;
    }
    public byte getTerm(){
        return term;
    }
    public String toStringWithGrades(){
        if (course.get(getIndex()).getGrades() > 74)
            return String.format("%-13s%-100s%-6s%-8s%n", course.get(getIndex()).getCourseNum(), course.get(getIndex()).getDescTitle(), course.get(getIndex()).getUnits(), course.get(getIndex()).getGrades());
        else if(course.get(getIndex()).getGrades() > 99 )
            return String.format("%-13s%-100s%-6s%-8s%n", course.get(getIndex()).getCourseNum(), course.get(getIndex()).getDescTitle(), course.get(getIndex()).getUnits(), course.get(getIndex()).getGrades() == 99);
        else if(course.get(getIndex()).getGrades() < 75 && course.get(getIndex()).getGrades() > 0)
            return String.format("%-13s%-100s%-6s%-8s%n", course.get(getIndex()).getCourseNum(), course.get(getIndex()).getDescTitle(), course.get(getIndex()).getUnits(), "Failed");
        else
            return String.format("%-13s%-100s%-6s%-8s%n", course.get(getIndex()).getCourseNum(), course.get(getIndex()).getDescTitle(), course.get(getIndex()).getUnits(), "Not yet taken");

    }
    public String toString(){
            return String.format("%-13s%-100s%-6s%n", course.get(getIndex()).getCourseNum(), course.get(getIndex()).getDescTitle(), course.get(getIndex()).getUnits());
    }
    public String toCSV(){
        return(yearLevel + "," + term + "," + courseNum + "," + descTitle + "," + units + "," + grades);
    }
    public int compareTo(Curriculum another){
        if (this.getYearLevel() == another.getYearLevel()) {
            if (this.getTerm() == another.getTerm())
                return 0;
            else if (this.getTerm() < another.getTerm())
                return -1;
            else
                return 1;
        }
        else if (this.getYearLevel() < another.getYearLevel())
            return -1;
        else
            return 1;
    }
}

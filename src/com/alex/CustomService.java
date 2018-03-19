package com.alex;

import entity.Section;
import entity.Subsection;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CustomService {
    private String PATH="C:\\Users\\Alex\\IdeaProjects\\SOAP_GUI_APP\\JsReference";

    /*public JsRefference getJsRefference(){
        return new JsRefference(getSections(),new entity.Author("Alex","Mamayko","Artifical Intellijence"));
    }*/

    /*public List<String> getSections() {
        List<String> sectionList = new ArrayList<>();
        File mainDir = new File(PATH);
        for (File file : mainDir.listFiles())
            sectionList.add(file.getName());
        return sectionList;
    }
    */
    public List<String> getSectionsStr() {
        List<String> sectionList = new ArrayList<>();
        File mainDir = new File(PATH);
        for (File file : mainDir.listFiles())
            sectionList.add(file.getName());
        return sectionList;
    }

    public List<String> getSubsectionsList(String sectionName){
        List<String> strings = new ArrayList<>();
        for (String section : getSectionsStr())
            if (section.equals(sectionName)) {
                File file = new File(PATH+"\\" + sectionName);
                for (File file1 : Objects.requireNonNull(file.listFiles())) {
                    strings.add(file1.getName());
                }
            }
        return strings;
    }

    public Subsection getSubsection(String sectionName, String subsectionName){
        StringBuilder stringBuilder = new StringBuilder();
        try {
            File file = new File(PATH + "\\" + sectionName + "\\" + subsectionName);
            FileInputStream fstream = new FileInputStream(file);
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            String strLine;
            while ((strLine = br.readLine()) != null) {
                stringBuilder.append(strLine + "\n");
            }
            fstream.close();
            br.close();
        } catch (IOException e){
            System.out.println("Error");
        }
        return new Subsection(subsectionName,stringBuilder.toString());
    }

    public void deleteSubsection(String sectionName, String subsectionName){
        File file = new File(PATH+"\\" + sectionName + "\\" + subsectionName);
        file.delete();
    }

    public void updateSubsection(String sectionName, Subsection subsection){
        File file = new File(PATH+"\\" + sectionName + "\\" + subsection.getName());
        PrintWriter printWriter = null;
        try {
            printWriter = new PrintWriter(file);
        } catch (FileNotFoundException e) {
            System.out.println("Error");
        }
        printWriter.print(subsection.getInfo());
        printWriter.close();
    }

    public void addSubsection(String sectionName, Subsection subsection){
        File file = new File(PATH+"\\" + sectionName + "\\" + subsection.getName());
        try {
            file.createNewFile();
        } catch (IOException e) {
            System.out.println("Error");
        }
    }

    public void addSection(Section section) {
        File file = new File(PATH+"\\" + section.getName());
        System.out.println(file.getPath());
        file.mkdir();
    }

}
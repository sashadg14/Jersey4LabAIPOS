package client;


import entity.Section;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class Contoller {
    private ServiseConnector serviseConnector = new ServiseConnector();

    public JTree buildInfo(JLabel jLabel){
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("JsReference");
        for (Section section :getSections()) {
            //System.out.println(section.getName());
            DefaultMutableTreeNode node=new DefaultMutableTreeNode(section.getName());
            List<String> arr=section.getSubsectionsNames();
            if (arr!=null)
            for(String s:arr) {
                node.add(new DefaultMutableTreeNode(s));
            }
            root.add(node);
        }
        JTree jTree = new JTree(root);
        expandAll(jTree,new TreePath(root));
        jTree.setShowsRootHandles(true);
        jTree.setRootVisible(false);
        return jTree;
    }



    private void expandAll(JTree tree, TreePath parent) {
        TreeNode node = (TreeNode) parent.getLastPathComponent();
        if (node.getChildCount() >= 0) {
            for (Enumeration e = node.children(); e.hasMoreElements();) {
                TreeNode n = (TreeNode) e.nextElement();
                TreePath path = parent.pathByAddingChild(n);
                expandAll(tree, path);
            }
        }
        tree.expandPath(parent);
        // tree.collapsePath(parent);
    }

    private List<Section> getSections() {
        List<Section> sections=new ArrayList<>();
        for(String string:serviseConnector.getSections())
            sections.add(serviseConnector.getSection(string));
        return sections;
    }


/*
    public List<String> getSubsections(String sectionName) {
        return serviseConnector.getSubsections(sectionName);
    }

    public String getInfoFromSubsection(String sectionName, String subsectionName){
        return serviseConnector.getSubsectionInfo(sectionName,subsectionName);
    }

    public void deleteSubsection(String sectionName, String subsectionName){
            serviseConnector.deleteSubsection(sectionName,subsectionName);
    }

    public void setInfoToSubsection(String sectionName, String subsectionName, String newText){
       serviseConnector.setInfoInSubsection(sectionName,subsectionName,newText);
    }

    public void addSubsection(String sectionName, String subsectionName){
        serviseConnector.addSubsection(sectionName,subsectionName);
    }

    public void addSection(String sectionName){
        serviseConnector.addSection(sectionName);
    }*/

}

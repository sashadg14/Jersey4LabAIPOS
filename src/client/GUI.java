package client;

import javax.swing.*;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import java.awt.event.ActionListener;

public class GUI {
    private JFrame jFrame = new JFrame("JS Guide");
    private JButton reloadButton = new JButton(new ImageIcon("src\\resources\\resize.png"));
    private JTextArea jTextArea;
    private JButton editTextButton;
    private JButton addSubsectionButton;
    private JButton addSectionButton;
    private JButton deleteSubsectionButton;
    private ServiseConnector thriftServiseConnector =new ServiseConnector();
    private int leftAligmentBtn=300;
    private int heightOfTextArea=500,widthOfTextArea=1000;

    private JLabel authorLable;

    public GUI() {
        deleteSubsectionButton = new JButton("Удалить подраздел");
        editTextButton = new JButton("Изменить содержимое");
        addSubsectionButton = new JButton("Добавить подраздел");
        addSectionButton= new JButton("Добавить раздел");
        jFrame.getContentPane().setLayout(null);
        jFrame.add(reloadButton);
        reloadButton.setBounds(0, 0, 40, 30);
        jFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        jTextArea = createTextAreaAtScrollPane();
        reloadButton.addActionListener(e -> {
                createTree();
        });

        addSubsectionButton.setBounds(leftAligmentBtn, 0, 300, 30);
        addSubsectionButton.addActionListener(createAddSubsectionBtnActionListener());
        jFrame.add(addSubsectionButton);

        addSectionButton.setBounds(leftAligmentBtn, 50, 300, 30);
        addSectionButton.addActionListener(createAddSectionBtnActionListener());
        jFrame.add(addSectionButton);

        deleteSubsectionButton.setBounds(leftAligmentBtn, 100, 300, 30);
        deleteSubsectionButton.addActionListener(createDeleteBtnActionListener());
        jFrame.add(deleteSubsectionButton);

        editTextButton.setBounds(1000, 700, 200, 30);
        editTextButton.addActionListener(createEditBtnActionListener());
        jFrame.add(editTextButton);

        authorLable=new JLabel("Author");
        authorLable.setBounds(leftAligmentBtn,heightOfTextArea+200,300,50);
        jFrame.add(authorLable);

        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private ActionListener createEditBtnActionListener() {
        return e -> {
                thriftServiseConnector.updateSubsection(parPath.getLastPathComponent().toString(),selectedObj.toString(),jTextArea.getText());
        };
    }

    private ActionListener createDeleteBtnActionListener() {
        return e -> {
            if(selectedObj!=null) {
                thriftServiseConnector.deleteSubsection(parPath.getLastPathComponent().toString(), selectedObj.toString());
                jTextArea.setText("");
                createTree();
            }
        };
    }

    private ActionListener createAddSubsectionBtnActionListener() {
        return e -> {
                String n=JOptionPane.showInputDialog ("Название подраздела");
                if(n!=null&&!n.equals("")) {
                        thriftServiseConnector.addSubsection(selectedSection.toString(),n);
                }
                createTree();
        };
    }

    private ActionListener createAddSectionBtnActionListener() {
        return e -> {
                String sectionName=JOptionPane.showInputDialog ("Название раздела");
                if(sectionName!=null&&!sectionName.equals("")) {
                        thriftServiseConnector.addSection(sectionName);
                }
           createTree();
        };
    }

    private JTree jTree;
    private JScrollPane jScrollPane;
    private void createTree(){
        if(jScrollPane!=null)
        jFrame.remove(jScrollPane);
        jTree= new Contoller().buildInfo(authorLable);
        jScrollPane = new JScrollPane(jTree);
        jScrollPane.setBounds(20, 100, 200, 700);
        jFrame.add(jScrollPane);
        jScrollPane.updateUI();
        jTree.addTreeSelectionListener(createTreeSelectionListener(jTree));
    }
    private TreePath parPath;
    private Object selectedObj;
    private Object selectedSection;

    private TreeSelectionListener createTreeSelectionListener(JTree jTree) {
        return e -> {
            parPath = e.getPath().getParentPath();
            selectedObj = jTree.getLastSelectedPathComponent();
            String selected = selectedObj.toString();
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) jTree.getLastSelectedPathComponent();
            if (parPath.toString().equals("[JsReference]")) {
                addSubsectionButton.setText("Добавить подраздел в " + selected);
              /*  for (String s : new soap.Contoller().getSubsections(selected))
                    node.add(new DefaultMutableTreeNode(s));*/
               // jTree.expandPath(e.getPath());
                selectedObj=null;
                selectedSection=jTree.getLastSelectedPathComponent();
            } else {
                    jTextArea.setText(thriftServiseConnector.getSubsection(parPath.getLastPathComponent().toString(), selected).getInfo());
            }
        };
    }

    private JTextArea createTextAreaAtScrollPane() {
        JTextArea jTextArea = new JTextArea();
        JScrollPane jScrollPane = new JScrollPane(jTextArea);
        jScrollPane.setBounds(leftAligmentBtn, 150, widthOfTextArea, heightOfTextArea);
        jFrame.add(jScrollPane);
        return jTextArea;
    }
}

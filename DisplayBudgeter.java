import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.ArrayList;
import java.io.File;
import java.util.Map;
import javax.swing.table.DefaultTableCellRenderer;

//Implement prepareDisplay() method
public class DisplayBudgeter extends JFrame implements ActionListener {
    
    private Compute computer;
    private static List<String> collegeNames;
    private static JPanel mainPanel;
    private static JScrollPane resultsPane;
    private static JButton go;
    private static JLabel bLabel, cLabel;
    private static JTextField budgetChoice;
    private static JComboBox collegeChoice;
    private static JTable results;
    private static JPanel budgetPanel, topWhiteSpace, collegePanel, goButton;
    private String c = "UCI"; int b = -1;

    public DisplayBudgeter(){
        
        collegeNames = new ArrayList<String>();

        topWhiteSpace= new JPanel();
        budgetPanel= new JPanel();
        collegePanel = new JPanel();
        goButton = new JPanel();
        mainPanel = new JPanel();
        bLabel = new JLabel("Enter Budget:");
        cLabel = new JLabel("Select College:");
        go = new JButton("GO");

        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        
        go.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                    b = Integer.parseInt(budgetChoice.getText());
                    c = (String)collegeChoice.getSelectedItem();
                    System.out.println(b + ", " + c);
                    if(b > -1){
                        hideMainVisibility();
                        computer = new Compute(b, c);
                        computer.defaultValues();
                        computer.updateCityDifferences();
                        computer.computeUpdatedAvgs();
                        computer.formatVals();
                        String[][] combined = computer.threeDArray();
                        prepareDisplay(combined);
                    }     
            }
        }
        );
        go.setBorderPainted(true);
        go.setFocusPainted(true);
        go.setContentAreaFilled(false);

        String[] a = assignCollegeNames();
        
        budgetChoice = new JTextField("");
        budgetChoice.setPreferredSize(new Dimension(100,25));

        collegeChoice = new JComboBox(a);
        collegeChoice.setPreferredSize(new Dimension(100,25));
        
        budgetPanel.add(bLabel);
        budgetPanel.add(budgetChoice);
        collegePanel.add(cLabel);
        collegePanel.add(collegeChoice);
        goButton.add(go);
        
        mainPanel.add(topWhiteSpace);
        mainPanel.add(budgetPanel);
        mainPanel.add(collegePanel);
        mainPanel.add(goButton);
        
        add(mainPanel);
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(600,600));
        pack();
        setVisible(true);
    }

    public void prepareDisplay(String[][] k){
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        setLayout(new BorderLayout());
        String[] columnNames = {"Category", "Amount"};
        results = new JTable(k, columnNames);
        results.setPreferredSize(new Dimension(300,150));
        results.setFont(new Font("Sans", Font.ITALIC, 20));
        results.setRowHeight(80);
        results.setPreferredScrollableViewportSize(results.getPreferredSize());
        results.setFillsViewportHeight(true);
        results.getTableHeader().setFont(new Font("Sans", Font.BOLD, 60));
        resultsPane = new JScrollPane(results);
        for(int i =0; i<results.getColumnCount(); i++){
            results.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        add(resultsPane, SwingConstants.CENTER);
        validate();
        resultsPane.setVisible(true);
    }

    public String[] assignCollegeNames(){
        File folder = new File("Data/");
        File[] listOfFiles = folder.listFiles();
        for(File files : listOfFiles){
            if(files.isFile()){
                collegeNames.add(files.getName());
            }
        }

        String[] names = new String[collegeNames.size()];
            for(int i=0;i<collegeNames.size();i++){
                names[i] = collegeNames.get(i);
            }
            for(int i = 0; i < names.length; i++){
                for(int j=0; j<names[i].length(); j++){
                    if(names[i].substring(j,j+1).equals(".")){
                        names[i] = names[i].substring(0,j);
                    }
                }
            }
            return names;
    }

    void hideMainVisibility(){
        getContentPane().removeAll();
        repaint();
        budgetPanel.setVisible(false);
        topWhiteSpace.setVisible(false);
        collegePanel.setVisible(false);
        goButton.setVisible(false);
    }

    public void actionPerformed(ActionEvent e){  
        System.out.println(c + ", " + b);      
    }

    public static void main(String[] args) {
        DisplayBudgeter obj = new DisplayBudgeter();
    }
}

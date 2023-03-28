import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Client implements ActionListener {

    static JFrame f = new JFrame();
    JTextField text;

    static DataInputStream din ;
    static DataOutputStream dout;
    static JPanel a;
    static Box Vertical = Box.createVerticalBox();
    Client(){
        f.setSize(450,700);
        f.setLayout(null);
        f.setLocation(500,50);
        f.setUndecorated(true); //Set value to true for removing the upper border


        f.getContentPane().setBackground(Color.BLACK);

        JPanel p = new JPanel();
        p.setBackground(new Color(7,94,84));
        p.setBounds(0,0,450,70);
        p.setLayout(null);
        f.add(p);

        a = new JPanel();
        a.setBounds(5,70,440,550);
//        a.setBackground(Color.WHITE);
        a.setLayout(null);
        f.add(a);

        text = new JTextField();
        text.setFont(new Font("SANS_SERIF",Font.PLAIN,18));
        text.setBounds(5,625,360,70);
        f.add(text);

        JButton send = new JButton("Send");
        send.setBackground(new Color(7,94,84));
        send.setFont(new Font("SANS_SERIF",Font.BOLD,14));
        send.setForeground(Color.WHITE);
        send.setBounds(370,625,70,70);
        send.addActionListener(this);
        f.add(send);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/3.png"));
        Image i2 = i1.getImage().getScaledInstance(25,25,Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel back = new JLabel(i3);
        back.setBounds(5,20,25,25);
        p.add(back);

        back.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e){
                System.exit(0);
            }
        });


        JLabel name = new JLabel("Abinash Senapati");
        name.setBounds(35,20,250,25);
        name.setForeground(Color.WHITE);
        name.setFont(new Font("SANS_SERIF",Font.BOLD,18));
        p.add(name);

        JLabel status = new JLabel("Active now");
        status.setBounds(35,40,250,25);
        status.setForeground(Color.WHITE);
        status.setFont(new Font("SANS_SERIF",Font.PLAIN,14));
        p.add(status);




        f.setVisible(true);
    }

    public void actionPerformed(ActionEvent e){

        try {
            String out = text.getText();

            JPanel p2 =formatLabel(out);
//        p2.add(output);

            a.setLayout(new BorderLayout());


            JPanel right = new JPanel(new BorderLayout());
            right.add(p2,BorderLayout.LINE_END);
            Vertical.add(right);
            Vertical.add(Box.createVerticalStrut(15));
            a.add(Vertical,BorderLayout.PAGE_START);

            dout.writeUTF(out);

            text.setText("");

            f.repaint();
            f.invalidate();
            f.validate();

        }catch (Exception ae){
            ae.printStackTrace();
        }


        //        System.out.println(out);

    }

    public static JPanel formatLabel(String out){
        JPanel panel = new JPanel();
        JLabel owner = new JLabel("Abinash Senapti");
        owner.setForeground(Color.BLACK);
        owner.setFont(new Font("SANS_SERIF",Font.PLAIN,14));
        panel.add(owner);

        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));

        JLabel output = new JLabel("<html><p style=\"width:150px\">"+out+"</p></html>");
        output.setFont(new Font("Tohoma",Font.PLAIN,16));
        output.setBackground(new Color(37,211,102));
        output.setOpaque(true);
        output.setBorder(new EmptyBorder(15,15,15,50));

        panel.add(output);


        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        JLabel time = new JLabel();
        time.setText(sdf.format(cal.getTime()));

        panel.add(time);

        return panel;
    }

    public static void main(String[] args) {
        new Client();

        try {
            Socket s = new Socket("127.0.0.1",5000);
            din = new DataInputStream(s.getInputStream());
            dout = new DataOutputStream(s.getOutputStream());
            while(true){
                a.setLayout(new BorderLayout());
                String msg = din.readUTF();



                JPanel panel = formatLabel(msg);

                JPanel left = new JPanel(new BorderLayout());
                left.add(panel,BorderLayout.LINE_START);
                Vertical.add(left);
                Vertical.add(Box.createVerticalStrut(15));
                a.add(Vertical,BorderLayout.PAGE_START);
                f.validate();
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

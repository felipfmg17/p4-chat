
import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;
import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import javax.swing.JOptionPane;


/**
 *
 * @author Usuario
 */
public class ClientChat extends javax.swing.JFrame implements Runnable{

    String user;

    
    String multicast_ip="230.0.113.0";
    int multicast_port=4446;
    InetAddress group;
    MulticastSocket input_socket;
    DatagramSocket output_socket;
    String conversation="";
    
    
    final String CARITA_FELIZ1="<img src=\"file:///C:\\Users\\Usuario\\HOME\\Escom\\Semestre 5\\APLICACIONES PARA RED\\PARCIAL 2\\p4 chat\\p4 chat\\resources\\images\\feliz.jpg\"  > </img>";
    final String CARITA_FELIZ2="<img src=\"file:///C:\\Users\\Usuario\\HOME\\Escom\\Semestre 5\\APLICACIONES PARA RED\\PARCIAL 2\\p4 chat\\p4 chat\\resources\\images\\feliz2.jpg\"  > </img>";
    final String CARITA_TRISTE="<img src=\"file:///C:\\Users\\Usuario\\HOME\\Escom\\Semestre 5\\APLICACIONES PARA RED\\PARCIAL 2\\p4 chat\\p4 chat\\resources\\images\\triste.jpg\"  > </img>";
    

    
    public void getUserValues(){
        user=JOptionPane.showInputDialog("Enter User Name");
        //String s_port=JOptionPane.showInputDialog("Enter local port");
        //local_port=Integer.parseInt(s_port);
    }
    
    public void connect() throws IOException {
        group=InetAddress.getByName(multicast_ip);
        input_socket=new MulticastSocket(multicast_port);
        input_socket.joinGroup(group);
        output_socket=new DatagramSocket();
    }
    
    
    public void sendChatMessage(ChatMessage cm) throws IOException {
        byte[] buf=objectToByte(cm);
        DatagramPacket pack=new DatagramPacket(buf,0, buf.length, group, multicast_port);
        output_socket.send(pack);
    }
    
    public ChatMessage receiveChatMessage() throws IOException, ClassNotFoundException {
        byte[] buf=new byte[10240];
        DatagramPacket pack=new DatagramPacket(buf,buf.length);
        input_socket.receive(pack);
        return (ChatMessage)byteToObject(buf, 0, pack.getLength());
    }
    
    public byte[] objectToByte(Object obj)throws IOException {
        ByteOutputStream byte_stream=new ByteOutputStream();
        ObjectOutputStream object_stream= new ObjectOutputStream(byte_stream);
        object_stream.writeObject(obj);
        return byte_stream.getBytes();
    }
    
    public Object byteToObject(byte[] buf,int offset,int length) throws IOException, ClassNotFoundException {
        ByteInputStream byte_stream=new ByteInputStream(buf,offset,length);
        ObjectInputStream object_stream=new ObjectInputStream(byte_stream);
        return object_stream.readObject();
    }
    
    
    public void displayChatMessage(ChatMessage cm){
        String user=cm.user+" says:<br>";
        conversation+="------------------------------------------<br> ";
        conversation+=user;
        conversation+=cm.message+"<br>";
        jEditorPane1.setText(conversation);
//        jEditorPane1.append("-----------------------------------------------\n");
//        jEditorPane1.append(user);
//        jEditorPane1.append(cm.message+"\n");
            //jEditorPane1.setText(<"C:\\Users\\Usuario\\HOME\\Escom\\Semestre 5\\APLICACIONES PARA RED\\PARCIAL 2\\p4 chat\\p4 chat\\src\\carita.jpg" );
    }
    
    
    public ChatMessage getChatMessage(){
        String message=jTextField1.getText();
        message=replaceEmoticons(message);
        jTextField1.setText("");
        return new ChatMessage(user, message);
    }
    
    public String replaceEmoticons(String message){
        message=message.replace(":D",CARITA_FELIZ1);
        message=message.replace(":)",CARITA_FELIZ2);
        message=message.replace(":(",CARITA_TRISTE);
        return message;
    }
    
    public void listen () throws IOException, ClassNotFoundException {
        while(true){
            ChatMessage cm=receiveChatMessage();
            displayChatMessage(cm);
        }
    }

    @Override
    public void run() {
        try{
            listen();
        }
        catch(Exception e){
            e.printStackTrace();
        }
       
    }
    
    public void startListener(){
        Thread listener=new Thread(this);
        listener.start();
    }
    
    
    public void close() throws IOException{
        input_socket.leaveGroup(group);
        input_socket.close();
        output_socket.close();
    }
    
    
    
    
    public ClientChat() {
        initComponents();
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jEditorPane1 = new javax.swing.JEditorPane();
        jPanel2 = new javax.swing.JPanel();
        jTextField1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jEditorPane1.setEditable(false);
        jEditorPane1.setContentType("text/html"); // NOI18N
        jEditorPane1.setText("");
        jScrollPane2.setViewportView(jEditorPane1);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 377, Short.MAX_VALUE)
        );

        jButton1.setText("Send");
        jButton1.setToolTipText("");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try{
            ChatMessage cm=getChatMessage();
             sendChatMessage(cm);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ClientChat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ClientChat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ClientChat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ClientChat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try{
                    ClientChat chat=new ClientChat();
                    chat.getUserValues();
                    chat.connect();
                    chat.setTitle(chat.user);
                    chat.startListener();
                    chat.setVisible(true);
                }
                catch(Exception e){
                    e.printStackTrace();
                }
                
            }
        });
    }
    
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JEditorPane jEditorPane1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}

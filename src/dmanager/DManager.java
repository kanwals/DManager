package dmanager;

import java.awt.HeadlessException;
import java.net.*;
import java.io.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;
import java.awt.Canvas;
import java.awt.Color;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import javax.swing.*;
import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.embedded.videosurface.CanvasVideoSurface;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;

public class DManager extends javax.swing.JFrame {

    ArrayList<Downloaded> dw;
    ArrayList<Downloadable> downloads;
    TableModel tm;
    TableModel2 tm2;
    File files[];

    public DManager() {
        downloads = new ArrayList<>();
        dw = new ArrayList<>();
        tm = new TableModel();
        tm2 = new TableModel2();
        initComponents();
        File f_search = new File("C:\\Users\\Public\\My Downloads\\");
        files = f_search.listFiles();
        for (int i = 0; i < files.length; i++) {
            Downloaded obj = new Downloaded();
            obj.f_name = files[i].getName();
            obj.f_size = files[i].length();
            dw.add(obj);
            tm2.fireTableDataChanged();
        }
        setLocation(100, 40);
        d_table.getColumnModel().getColumn(6).setCellRenderer(new jprogressBarRenderer());
        d_table.getColumnModel().getColumn(6).setWidth(75);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        File search = new File("");
        File f = new File("C:\\Users\\Public\\saved.txt");
        if (f.exists()) {
            try {
                FileReader fr = new FileReader("C:\\Users\\Public\\saved.txt");
                BufferedReader br = new BufferedReader(fr);
                while (true) {
                    String s = br.readLine();
                    if (s == null) {
                        break;
                    } else {
                        String str[] = s.split(";");
                        DManager.Downloadable obj = new DManager.Downloadable();
                        obj.link = str[0];
                        obj.org_size = Long.parseLong(str[1]);
                        URL url = new URL(str[0]);
                        obj.status = str[2];
                        obj.date = str[3];
                        obj.hours = str[4];
                        obj.mins = str[5];
                        String resource = url.getPath();
                        StringTokenizer st = new StringTokenizer(url.toString(), "/");
                        int n = st.countTokens();
                        while (n > 1) {
                            st.nextToken();
                            n--;
                        }
                        String fn = st.nextToken();
                        obj.file_name = fn;
                        File fnew = new File("C:\\Users\\Public\\My Downloads\\" + fn + ".part");
                        if (fnew.exists()) {
                            obj.org_count = fnew.length();
                            float one_percent = (float) obj.org_size / (float) 100;
                            float temp = (float) ((obj.org_count) / (float) one_percent);
                            obj.percent = obj.percent.format("%.0f", temp);
                        } else {
                            obj.org_count = 0;
                            obj.percent = "0";
                        }
                        obj.pause_flag = true;
                        obj.file_downloaded = false;
//                        if (obj.org_count == 0) {
////                            obj.status = "stopped";
//                        } else {
//                            obj.status = "paused";
//                        }
                        downloads.add(obj);
                        tm.fireTableDataChanged();
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        new Thread(new Runnable() {

            @Override
            public void run() {

                try {
                    while (true) {
                        for (int i = 0; i < downloads.size(); i++) {
                            if (downloads.get(i).status.equals("scheduled")) {
                                String temp_date = downloads.get(i).date;
                                String temp[] = temp_date.split("/");
                                int year = Integer.parseInt(temp[2]) - 1900;
                                int month = Integer.parseInt(temp[1]) - 1;
                                int day = Integer.parseInt(temp[0]);
                                int hours = Integer.parseInt(downloads.get(i).hours);
                                int mins = Integer.parseInt(downloads.get(i).mins);
                                Date our = new Date(year, month, day, hours, mins);

                                Date now = new Date();

                                int result = now.compareTo(our);
                                if (result >= 0) {
                                    downloads.get(i).pause_flag = false;
                                    downloads.get(i).status = "downloading";
                                    new Thread(downloads.get(i)).start();
                                } else {
                                    continue;
                                }
                                Thread.sleep(60000);
                            }
                        }
                    }
                } catch (Exception e) {
                }

            }
        }).start();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        download = new javax.swing.JButton();
        url = new javax.swing.JTextField();
        link = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        d_table = new javax.swing.JTable();
        pause = new javax.swing.JButton();
        resume = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("DManager");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        download.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        download.setText("DOWNLOAD");
        download.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                downloadActionPerformed(evt);
            }
        });

        url.setText("Enter URL ");
        url.setFocusable(false);
        url.setRequestFocusEnabled(false);
        url.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                urlMouseClicked(evt);
            }
        });
        url.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                urlActionPerformed(evt);
            }
        });

        link.setText("URL: ");

        d_table.setModel(tm);
        d_table.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        jScrollPane1.setViewportView(d_table);

        pause.setText("PAUSE");
        pause.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pauseActionPerformed(evt);
            }
        });

        resume.setText("RESUME");
        resume.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resumeActionPerformed(evt);
            }
        });

        jButton4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton4.setText("My Downloads");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("DOWNLOADED FILES");

        jTable1.setModel(tm2);
        jScrollPane2.setViewportView(jTable1);

        jButton1.setText("SCHEDULE A DOWNLOAD");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("REMOVE");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("PLAY");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton5.setText("OPEN");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setText("ABOUT");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(link)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(url, javax.swing.GroupLayout.PREFERRED_SIZE, 442, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(download)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(91, 91, 91))
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(pause, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(resume, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 647, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 125, Short.MAX_VALUE)
                        .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
            .addGroup(layout.createSequentialGroup()
                .addGap(125, 125, 125)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 392, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE, false)
                        .addComponent(url, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                        .addComponent(link, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(download, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(pause)
                    .addComponent(resume)
                    .addComponent(jButton2)
                    .addComponent(jButton3))
                .addGap(12, 12, 12)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 201, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(135, 135, 135))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jButton6)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap())))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    private void downloadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_downloadActionPerformed
        if (evt.getSource() == download) {
            Downloadable obj = new Downloadable();
            downloads.add(obj);
            obj.link = this.url.getText();
            Thread ob = new Thread(obj);
            ob.start();
        }
    }//GEN-LAST:event_downloadActionPerformed

    private void pauseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pauseActionPerformed
        try {
            int a = d_table.getSelectedRow();
            System.out.println(a);
            if (a != -1) {
                downloads.get(a).status = "paused";
                downloads.get(a).pause_flag = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "SELECT A DOWNLOAD FIRST.");
        }
    }//GEN-LAST:event_pauseActionPerformed

    private void urlMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_urlMouseClicked
        url.setText("");
        url.setFocusable(true);
        url.requestFocus();
    }//GEN-LAST:event_urlMouseClicked

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        try {
            File myfile = new File("/users/public/My Downloads/open");
            String path = myfile.getAbsolutePath();
            File dir = new File(path.substring(0, path.lastIndexOf(File.separator)));
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(dir);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "FOLDER DOES NOT EXIST.");
        }

    }//GEN-LAST:event_jButton4ActionPerformed

    private void urlActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_urlActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_urlActionPerformed

    private void resumeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resumeActionPerformed
        try {
            int r = d_table.getSelectedRow();

            if (downloads.get(r).pause_flag == false) {
                JOptionPane.showMessageDialog(this, "Already Downloading");
            } else {
                downloads.get(r).pause_flag = false;
                new Thread(downloads.get(r)).start();
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "SELECT A DOWNLOAD FIRST.");
        }
    }//GEN-LAST:event_resumeActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        try {
            FileWriter fw = new FileWriter("C:\\Users\\Public\\saved.txt");
            PrintWriter pw = new PrintWriter(fw);
            for (int i = 0; i < downloads.size(); i++) {
                downloads.get(i).pause_flag = true;
                if (downloads.get(i).status.equals("scheduled") || downloads.get(i).status.equals("stopped")) {

                } else {
                    downloads.get(i).status = "paused";
                }
                String str = downloads.get(i).link + ";" + downloads.get(i).org_size + ";" + downloads.get(i).status + ";" + downloads.get(i).date + ";" + downloads.get(i).hours + ";" + downloads.get(i).mins;
                pw.println(str);
                pw.flush();
            }
        } catch (Exception e) {
        }
    }//GEN-LAST:event_formWindowClosing

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        scheduler obj = new scheduler();
        obj.setVisible(true);
        obj.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        
        try {
            int n = d_table.getSelectedRow();
            if (n != -1) {
                int ans = JOptionPane.showConfirmDialog(this,"Removing a download will stop it and the download will be saved as .removed extension. Continue? ");
               if( ans == JOptionPane.YES_OPTION){
                   String name = downloads.get(n).file_name;
                    downloads.get(n).pause_flag = true;
                    downloads.get(n).fos.close();
                    downloads.remove(n);
                    tm.fireTableDataChanged();
                    File f = new File("C:\\Users\\Public\\My Downloads\\"+name+".part");
                    f.renameTo(new File("C:\\Users\\Public\\My Downloads\\"+name+".part.removed"));
                    JOptionPane.showMessageDialog(this,"Download removed and saved with .removed extension");
                }
                
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "SELECT A DOWNLOAD FIRST.");
        }


    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed

        try {
            int n = jTable1.getSelectedRow();
            if (n != -1) {
                File f = new File("C:\\Users\\Public\\My Downloads\\" + dw.get(n).f_name);
                Desktop.getDesktop().open(f);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "please select a downloaded file first");
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        try {
            int n = d_table.getSelectedRow();
            if (n != 1) {
                if (downloads.get(n).file_name.endsWith(".mp3") || downloads.get(n).file_name.endsWith(".mp4") || downloads.get(n).file_name.endsWith(".flv") || downloads.get(n).file_name.endsWith(".mkv") || downloads.get(n).file_name.endsWith(".wmv") || downloads.get(n).file_name.endsWith(".avi") || downloads.get(n).file_name.endsWith(".3gp") || downloads.get(n).file_name.endsWith(".aac") || downloads.get(n).file_name.endsWith(".m4c") || downloads.get(n).file_name.endsWith(".mpeg4")) {
                    String path = "C:\\Users\\Public\\My Downloads\\" + downloads.get(n).file_name + ".part";
                    LocalFilePlayer obj = new LocalFilePlayer(path);
                } else {
                    JOptionPane.showMessageDialog(this, "File format not supported");
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "select a music file from downloading list.");
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        about obj = new about();
        obj.setVisible(true);
        
    }//GEN-LAST:event_jButton6ActionPerformed
    class TableModel2 extends AbstractTableModel {

        String Title[] = {"S.no.", "NAME", "SIZE"};

        public String getColumnName(int a) {
            return Title[a];
        }

        public int getRowCount() {
            return dw.size();
        }

        public int getColumnCount() {
            return 3;
        }

        public Object getValueAt(int i, int j) {
            Downloaded temp = dw.get(i);
            if (j == 0) {
                return i + 1;
            }
            if (j == 1) {
                return temp.f_name;
            }
            if (j == 2) {
                return temp.f_size / 1024 + " Kb";
            }
            return 0;
        }

    }

    class TableModel extends AbstractTableModel {

        String title[] = {"S.No.", "File", "Total Size (MB)", "Downloaded (MB)", "Avg. Speed (KBps)", "Status", "progress", "Time Remaining (mins)", "Time elapsed (mins)"};

        public String getColumnName(int a) {
            return title[a];
        }

        public int getRowCount() {
            return downloads.size();
        }

        public int getColumnCount() {
            return title.length;
        }

        public Object getValueAt(int i, int j) {

            DManager.Downloadable temp = downloads.get(i);

            if (j == 0) {
                return i + 1;
            }
            if (j == 1) {
                return temp.file_name;
            }
            if (j == 2) {
                return String.format("%.2f", (float) temp.org_size / (float) 1024000);
            }
            if (j == 3) {
                if (temp.status.equals("Downloading")) {
                    return String.format("%.2f", (float) (temp.count + temp.org_count) / (float) 1024000);
                } else if (temp.status.equals("Downloaded")) {
                    return String.format("%.2f", (float) temp.org_count / (float) 1024000);
                } else if (temp.status.equals("paused")) {
                    return String.format("%.2f", (float) temp.org_count / (float) 1024000);
                }
            }
            if (j == 4) {
                return String.format("%.2f", temp.kb);
            }
            if (j == 5) {
                return temp.status;
            }
            if (j == 6) {
                return temp.percent + " %";
            }
            if (j == 7) {
                return temp.eta / 60 + ": " + temp.eta % 60;
            }
            if (j == 8) {
                if (temp.status.equals("Downloading")) {
                    return (temp.org_time_taken + temp.time_taken) / 60 + ": " + (temp.org_time_taken + temp.time_taken) % 60;
                } else if (temp.status.equals("Downloaded")) {
                    return temp.org_time_taken / 60 + ": " + temp.org_time_taken % 60;
                } else if (temp.status.equals("paused")) {
                    return temp.org_time_taken / 60 + ": " + temp.org_time_taken % 60;
                }
            }
            return 0;

        }

    }

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
            java.util.logging.Logger.getLogger(DManager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DManager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DManager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DManager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DManager().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable d_table;
    private javax.swing.JButton download;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel link;
    private javax.swing.JButton pause;
    private javax.swing.JButton resume;
    private javax.swing.JTextField url;
    // End of variables declaration//GEN-END:variables
    public class Downloaded {

        String f_name;
        long f_size;
    }

    public class Downloadable implements Runnable {

        DataOutputStream dos;
        DataInputStream dis;
        FileOutputStream fos;
        File f;
        Socket s;
        long partialSize;
        String str, link, date, hours, mins;
        URL url;
        int eta;
        String host;
        String resource, resource2, query;
        String file_name;
        StringTokenizer st;
        float kb;
        byte buffer[];
        long size, size2, count, time_taken, org_time_taken = 0;
        String percent = "0", status;
        boolean download_flag = false;
        boolean pause_flag = false;
        boolean resume_flag = false;
        boolean file_downloaded = false;

        long org_size = 0, org_count = 0;

        Downloadable() {

        }

        public void run() {
            for (int i = 0; i < downloads.size(); i++) {
                if (i == downloads.indexOf(this)) {

                } else {
                    if (link.equals(downloads.get(i).link)) {
                        JOptionPane.showMessageDialog(DManager.this, "FILE ALREADY BEING DOWNLOADED");
                        file_downloaded = true;
                        downloads.remove(this);
                        tm.fireTableDataChanged();
                        break;
                    }
                }
            }
            if (file_downloaded == false) {
                try {
                    try {

                        url = new URL(link);
                        host = url.getHost();
                        resource = url.getPath();
                        query = url.getQuery();
                        if (query != null) {
                            resource2 = resource + "?" + query;
                        } else {
                            resource2 = resource;
                        }
                        s = new Socket(host, 80);
                        dos = new DataOutputStream(s.getOutputStream());
                        dis = new DataInputStream(s.getInputStream());
                    } catch (Exception e) {
                        e.printStackTrace();
                        downloads.remove(this);
                        tm.fireTableDataChanged();
                        JOptionPane.showMessageDialog(DManager.this, "INVALID URL OR NOT CONNECTED TO INTERNET. TRY AGAIN.");

                    }
                    st = new StringTokenizer(url.toString(), "/");
                    int n = st.countTokens();
                    while (n > 1) {
                        st.nextToken();
                        n--;
                    }

                    file_name = st.nextToken();

                    File files = new File("C:\\Users\\Public\\My Downloads\\" + file_name + ".part");
                    if (files.exists()) {
                        partialSize = files.length();
                    }

                    if (partialSize > 0) {
                        String str = "GET " + resource2 + " HTTP/1.1\r\nHost: " + host + "\r\nRange: bytes=" + partialSize + "-\r\n\r\n";
                        System.out.println(str);
                        dos.writeBytes(str);

                    } else {
                        str = "GET " + resource2 + " HTTP/1.1\r\nHost: " + host + "\r\n\r\n";
                        dos.writeBytes(str);
                    }

                    while (true) {
                        String sr = dis.readLine();
                        System.out.println(sr);
                        if (sr.equals("")) {
                            break;
                        }
                        if (sr.contains(" 200 OK") || sr.contains(" 206")) {
                            download_flag = true;
                        }
                        if (sr.contains("Content-Length")) {
                            size = Long.parseLong(sr.substring(16));
                        }
                        if (sr.contains("Content-Range")) {
                            size2 = Long.parseLong(sr.substring(sr.indexOf("/") + 1));
                        }
                        if (sr.contains(" 404")) {
                            download_flag = false;
                            JOptionPane.showMessageDialog(DManager.this, "File not found");
                            downloads.remove(this);
                            tm.fireTableDataChanged();
                        }
                        if (sr.contains(" 400")) {
                            download_flag = false;
                            JOptionPane.showMessageDialog(DManager.this, "Bad request");
                            downloads.remove(this);
                            tm.fireTableDataChanged();
                        }
                        if (sr.contains(" 401")) {
                            download_flag = false;
                            JOptionPane.showMessageDialog(DManager.this, "UnAuthorized access");
                            downloads.remove(this);
                            tm.fireTableDataChanged();
                        }
                        if (sr.contains(" 403")) {
                            download_flag = false;
                            JOptionPane.showMessageDialog(DManager.this, "File Forbidden");
                            downloads.remove(this);
                            tm.fireTableDataChanged();
                        }
                        if (sr.contains("Location")) {
                            String redirectURL = sr.substring(sr.indexOf(" ") + 1);
                            System.out.println("Redirect URL: " + redirectURL);
                            redirectURL = redirectURL.replaceAll(" ", "%20");
                            if (redirectURL.startsWith("http")) {
                                link = redirectURL;
                                new Thread(this).start();
                            } else {
                                System.out.println("RESOURCE:::::" + resource);
                                link = "http://" + host + resource.substring(0, resource.lastIndexOf("/")) + redirectURL.substring(1);
                                System.out.println("LINK: " + link);
                                new Thread(this).start();
                            }
                        }

                    }

                } catch (Exception e) {
                }

                org_size = size;
                if (partialSize > 0) {
                    org_size = size2;
                    org_count = partialSize;
                }

//                tm.fireTableDataChanged();
                if (download_flag) {
                    try {
                        boolean directory_created = false;
                        status = "Downloading";
                        f = new File("C:\\Users\\Public\\My Downloads\\" + file_name + ".part");
                        System.out.println(f.getParentFile().mkdirs());
                        if (f.getParentFile().mkdirs() == false) {
                            directory_created = true;
                        }
                        if (!directory_created) {
                            throw new IOException("Unable to create " + f.getParentFile());
                        }
                        fos = new FileOutputStream(f, true);
                        buffer = new byte[50000];
                        count = 0;
                        int r;
                        long endtime;
                        long startTime = System.nanoTime(); // starting time of download
                        tm.fireTableDataChanged();
                        while (!pause_flag) {
                            r = dis.read(buffer, 0, 50000);
                            fos.write(buffer, 0, r);
                            float one_percent = (float) org_size / (float) 100;
                            float temp = (float) ((org_count + count) / (float) one_percent);
                            percent = percent.format("%.0f", temp);
                            count = count + r;
//                        System.out.println(count);
//                        System.out.println(size);
                            int k = downloads.size();

                            //System.out.println(count + " :: " +  org_size);
                            if (count == size) {
                                status = "Downloaded";
                                file_downloaded = true;
                                endtime = System.nanoTime();
                                //     System.out.println((org_count + count) / one_percent + "%");
                                temp = (float) ((org_count + count)) / (float) one_percent;
                                percent = percent.format("%.0f", temp);
                                for (int i = 0; i < downloads.size(); i++) {
                                    for (int j = 3; j < 9; j++) {
                                        tm.fireTableCellUpdated(i, j);
                                    }
                                }
                                tm.fireTableDataChanged();

                                break;
                            }
                            //  System.out.println((org_count + count) / one_percent + "%");
                            float bytesPerSec = 0;
                            if (System.nanoTime() != startTime) {
                                time_taken = ((System.nanoTime() - startTime) / 1000000000);
                                if (time_taken != 0) {
                                    bytesPerSec = count / time_taken;
                                }
                            }
                            kb = bytesPerSec / 1024;
                            if (time_taken != 0) {
                                eta = (int) (size - count) / (int) bytesPerSec;
                            }
                            for (int i = 0; i < downloads.size(); i++) {
                                for (int j = 3; j < 9; j++) {
                                    tm.fireTableCellUpdated(i, j);
                                }
                            }

                        }

                        org_time_taken += time_taken;
                        org_count += count;
                        fos.close();

                        if (status.equals("Downloaded")) {
                            tm2.fireTableDataChanged();
                            System.out.println(file_name);
                            File fnew = new File("C:\\Users\\Public\\My Downloads\\" + file_name + ".part");
                            File all_current_files[];
                            File check_file = new File("C:\\Users\\Public\\My Downloads\\");
                            int copies = 0;
                            all_current_files = check_file.listFiles();
                            for (int i = 0; i < all_current_files.length; i++) {
                                if (all_current_files[i].getName().contains(file_name)) {
                                    copies++;
                                }
                            }
                            if (copies > 1) {
                                JOptionPane.showMessageDialog(DManager.this, "File already exists. Downloaded with new name.");
                                fnew.renameTo(new File("C:\\Users\\Public\\My Downloads\\" + "(copy " + (copies - 1) + ")" + file_name));
                            } else {
                                fnew.renameTo(new File("C:\\Users\\Public\\My Downloads\\" + file_name));
                            }
                            downloads.remove(this);
                            tm.fireTableDataChanged();
                            tm2.fireTableDataChanged();
                            JOptionPane.showMessageDialog(DManager.this, "DOWNLOAD COMPLETE");
                            File here = new File("C:\\Users\\Public\\My Downloads\\");
                            files = here.listFiles();
                            dw.clear();
                            for (int i = 0; i < files.length; i++) {
                                Downloaded obj1 = new Downloaded();
                                obj1.f_name = files[i].getName();
                                obj1.f_size = files[i].length();
                                dw.add(obj1);
                                tm2.fireTableDataChanged();
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        downloads.remove(this);
                        tm.fireTableDataChanged();
                    }
                }
            }

        }
    }

    class jprogressBarRenderer implements TableCellRenderer {

        JProgressBar jp;

        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            jp = new JProgressBar();
            jp.setStringPainted(true);
            int per = Integer.parseInt(downloads.get(row).percent);
            jp.setValue(per);
            jp.setString(per + "%");
            jp.setForeground(Color.BLACK);
            jp.setBorderPainted(false);
            return jp;
        }
    }

    public class scheduler extends javax.swing.JFrame {

        /**
         * Creates new form scheduler
         */
        public scheduler() {
            initComponents();
            Calendar cl = Calendar.getInstance();

            for (int i = 1; i < 8; i++) {
                int year = cl.get(Calendar.YEAR);
                int month = cl.get(Calendar.MONTH) + 1;
                int day = cl.get(Calendar.DAY_OF_MONTH);

                String date = day + "/" + month + "/" + year;

                cl.add(Calendar.DAY_OF_MONTH, 1);
                jComboBox6.addItem(date);
            }
            for (int i = 0; i < 24; i++) {
                jComboBox4.addItem("" + i);
            }
            for (int i = 0; i < 60; i++) {
                jComboBox5.addItem("" + i);
            }

        }

        /**
         * This method is called from within the constructor to initialize the
         * form. WARNING: Do NOT modify this code. The content of this method is
         * always regenerated by the Form Editor.
         */
        @SuppressWarnings("unchecked")
        // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
        private void initComponents() {

            jLabel1 = new javax.swing.JLabel();
            jLabel2 = new javax.swing.JLabel();
            jLabel3 = new javax.swing.JLabel();
            jTextField1 = new javax.swing.JTextField();
            jComboBox4 = new javax.swing.JComboBox();
            jComboBox5 = new javax.swing.JComboBox();
            jButton1 = new javax.swing.JButton();
            jComboBox6 = new javax.swing.JComboBox();
            jLabel4 = new javax.swing.JLabel();
            jLabel5 = new javax.swing.JLabel();

            setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
            setTitle("SCHEDULER");

            jLabel1.setText("URL");

            jLabel2.setText("DATE");

            jLabel3.setText("TIME");

            jComboBox4.setModel(new javax.swing.DefaultComboBoxModel(new String[]{}));

            jComboBox5.setModel(new javax.swing.DefaultComboBoxModel(new String[]{}));

            jButton1.setText("SCHEDULE");
            jButton1.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    jButton1ActionPerformed(evt);
                }
            });

            jComboBox6.setModel(new javax.swing.DefaultComboBoxModel(new String[]{}));

            jLabel4.setText("hours");

            jLabel5.setText("mins");

            javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
            getContentPane().setLayout(layout);
            layout.setHorizontalGroup(
                    layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                            .addGap(44, 44, 44)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 57, Short.MAX_VALUE))
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 348, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(jComboBox6, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addGroup(layout.createSequentialGroup()
                                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                    .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                    .addComponent(jLabel4))
                                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                    .addComponent(jLabel5)
                                                                    .addComponent(jComboBox5, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                            .addContainerGap(65, Short.MAX_VALUE))
            );
            layout.setVerticalGroup(
                    layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                            .addGap(50, 50, 50)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextField1))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jComboBox6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jComboBox5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel5))
                            .addGap(54, 54, 54)
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addContainerGap(90, Short.MAX_VALUE))
            );

            pack();
        }// </editor-fold>                        

        private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
            Downloadable obj = new Downloadable();
            obj.status = "scheduled";
            obj.link = jTextField1.getText();
            boolean url_correct = true;
            try {
                URL url = new URL(obj.link);
                String resource = url.getPath();
                StringTokenizer st = new StringTokenizer(url.toString(), "/");
                int n = st.countTokens();
                while (n > 1) {
                    st.nextToken();
                    n--;
                }
                String fn = st.nextToken();
                obj.file_name = fn;
            } catch (Exception e) {
                url_correct = false;
                JOptionPane.showMessageDialog(null, "Invalid URL");
                obj.file_name = "NOT AVAILABLE";
            }
            obj.org_count = 0;
            obj.percent = "0";
            obj.partialSize = 0;
            obj.eta = 0;
            obj.time_taken = 0;
            obj.date = jComboBox6.getSelectedItem().toString();
            obj.hours = jComboBox4.getSelectedItem().toString();
            obj.mins = jComboBox5.getSelectedItem().toString();
            if (url_correct) {
                downloads.add(obj);
            }
            tm.fireTableDataChanged();
            new Thread(new Runnable() {

                @Override
                public void run() {

                    try {
                        while (true) {
                            for (int i = 0; i < downloads.size(); i++) {
                                if (downloads.get(i).status.equals("scheduled")) {
                                    String temp_date = downloads.get(i).date;
                                    String temp[] = temp_date.split("/");
                                    int year = Integer.parseInt(temp[2]) - 1900;
                                    int month = Integer.parseInt(temp[1]) - 1;
                                    int day = Integer.parseInt(temp[0]);
                                    int hours = Integer.parseInt(downloads.get(i).hours);
                                    int mins = Integer.parseInt(downloads.get(i).mins);
                                    Date our = new Date(year, month, day, hours, mins);

                                    Date now = new Date();

                                    int result = now.compareTo(our);
                                    if (result >= 0) {
                                        downloads.get(i).pause_flag = false;
                                        downloads.get(i).status = "downloading";
                                        new Thread(downloads.get(i)).start();
                                    } else {
                                        }
                                    
                                }
                                Thread.sleep(6000);
                            }
                            
                        }
                    } catch (Exception e) {
                    }

                }
            }).start();
            this.dispose();
        }

        /**
         * @param args the command line arguments
         */
        // Variables declaration - do not modify                     
        private javax.swing.JButton jButton1;
        private javax.swing.JComboBox jComboBox4;
        private javax.swing.JComboBox jComboBox5;
        private javax.swing.JComboBox jComboBox6;
        private javax.swing.JLabel jLabel1;
        private javax.swing.JLabel jLabel2;
        private javax.swing.JLabel jLabel3;
        private javax.swing.JLabel jLabel4;
        private javax.swing.JLabel jLabel5;
        private javax.swing.JTextField jTextField1;
    }
}

class LocalFilePlayer extends JFrame {

    EmbeddedMediaPlayer mediaPlayer;
    Canvas canvas;
    String media;

    public LocalFilePlayer(String path) {
        media = path;
        /////////////////////////////// STEP-1/////////////////////////////////
        NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(), "C:\\vlcj data\\");
        Native.loadLibrary(RuntimeUtil.getLibVlcLibraryName(), LibVlc.class);

        /////////////////////////////// STEP-2/////////////////////////////////
        MediaPlayerFactory mediaPlayerFactory = new MediaPlayerFactory();

        /////////////////////////////// STEP-3/////////////////////////////////
        mediaPlayer = mediaPlayerFactory.newEmbeddedMediaPlayer();

        canvas = new Canvas();
        add(canvas);
        canvas.setBackground(Color.black);

        setSize(1000, 700);
        setVisible(true);
        setTitle("PLAYING PARTIALLY DOWNLOADED FILE");

        /////////////////////////////// STEP-4/////////////////////////////////
        CanvasVideoSurface videoSurface = mediaPlayerFactory.newVideoSurface(canvas);
        mediaPlayer.setVideoSurface(videoSurface);

        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        /////////////////////////////// STEP-5,6/////////////////////////////////
        mediaPlayer.playMedia(media);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    }

    private void formWindowClosing(java.awt.event.WindowEvent evt) {
        try {
            remove(canvas);
            mediaPlayer.stop();

        } catch (Exception e) {
        }
    }
}

class about extends javax.swing.JFrame {

    /**
     * Creates new form about
     */
    public about() {
        initComponents();
        
        jTextArea1.setEditable(false);
        jTextArea1.setEnabled(true);
        setTitle("ABOUT");
        setResizable(false);
        setLocation(400,150);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jTextArea1.setText("APPLICATION: \tDManager\nTYPE: \tDownload Manager\nDeveloped by: \tGurkanwal Singh\nContact e-mail: kanwal.best@gmail.com\nQualification: \tUndergraduate(PEC, Chandigarh)\nDeveloped in: \tNetBeans IDE 8.0\n\nUnder supervision of VMM Education(JULY 2014)");
        jScrollPane1.setViewportView(jTextArea1);

        jButton1.setText("CLOSE");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel1.setText("DETAILS: ");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 433, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>                        

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        this.dispose();
    }                                        

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
            java.util.logging.Logger.getLogger(about.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(about.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(about.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(about.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new about().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify                     
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    // End of variables declaration                   
}

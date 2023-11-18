package io.github.arrayv.prompts;

import io.github.arrayv.dialogs.ShuffleDialog;
import io.github.arrayv.frames.AppFrame;
import io.github.arrayv.frames.UtilFrame;
import io.github.arrayv.main.ArrayManager;
import io.github.arrayv.panes.JErrorPane;
import io.github.arrayv.utils.Distributions;
import io.github.arrayv.utils.Shuffles;

import javax.swing.*;
import java.util.Arrays;

/*
 *
MIT License

Copyright (c) 2019 w0rthy
Copyright (c) 2021-2022 ArrayV Team

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
 *
 */

public final class ShufflePrompt extends javax.swing.JFrame implements AppFrame {
    private static final long serialVersionUID = 1L;
    private static final String ADVANCED = "(Advanced)";

    private final ArrayManager arrayManager;
    private final JFrame frame;
    private final UtilFrame utilFrame;

    private final DefaultListModel<String> shuffleModel;
    private boolean initializing;

    /**
     * Creates new form SortPrompt
     */
    @SuppressWarnings("unchecked")
    public ShufflePrompt(ArrayManager arrayManager, JFrame frame, UtilFrame utilFrame) {
        this.arrayManager = arrayManager;
        this.frame = frame;
        this.utilFrame = utilFrame;

        setAlwaysOnTop(true);
        setUndecorated(true);
        initComponents();

        initializing = true;
        jList1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jList1.setListData(arrayManager.getDistributionIDs());
        for (int i = 0; i < arrayManager.getDistributions().length; i++) {
            if (arrayManager.getDistribution().equals(arrayManager.getDistributions()[i])) {
                jList1.setSelectedIndex(i);
                break;
            }
        }
        shuffleModel = new DefaultListModel<>();
        jList2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jList2.setModel(shuffleModel);
        Arrays.stream(arrayManager.getShuffleIDs()).forEach(shuffleModel::addElement);
        // Add ghost option if advanced shuffle graph is active
        if (arrayManager.getShuffle().size() > 1) {
            shuffleModel.add(0, ADVANCED);
            jList2.setSelectedIndex(0);
        } else {
            for (int i = 0; i < arrayManager.getShuffles().length; i++) {
                if (arrayManager.containsShuffle(arrayManager.getShuffles()[i])) {
                    jList2.setSelectedIndex(i);
                    break;
                }
            }
            if (jList2.getSelectedIndex() == -1) {
                shuffleModel.add(0, ADVANCED);
                jList2.setSelectedIndex(0);
            }
        }
        initializing = false;

        reposition();
        setVisible(true);
    }

    @Override
    public void reposition() {
        setLocation(frame.getX()+(frame.getWidth()-getWidth())/2, frame.getY()+(frame.getHeight()-getHeight())/2);
    }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings({ "rawtypes" })
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        // Variables declaration - do not modify//GEN-BEGIN:variables
        javax.swing.JButton jButton1 = new javax.swing.JButton();

        javax.swing.JLabel jLabel1 = new javax.swing.JLabel();
        javax.swing.JScrollPane jScrollPane1 = new javax.swing.JScrollPane();
        this.jList1 = new javax.swing.JList();

        javax.swing.JLabel jLabel2 = new javax.swing.JLabel();
        javax.swing.JScrollPane jScrollPane2 = new javax.swing.JScrollPane();
        this.jList2 = new javax.swing.JList();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jButton1.setText("Open Advanced Editor");
        jButton1.addActionListener(evt -> jButton1ActionPerformed());

        jLabel1.setText("What shape do you want the array to have?");
        jLabel2.setText("How do you want the array to be shuffled?");

        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        jScrollPane1.setViewportView(this.jList1);
        jScrollPane2.setViewportView(this.jList2);

        jList1.addListSelectionListener(evt -> {
            try {
                jList1ValueChanged();
            } catch (Exception e) {
                JErrorPane.invokeErrorMessage(e);
            }
        });

        jList2.addListSelectionListener(evt -> {
            try {
                jList2ValueChanged();
            } catch (Exception e) {
                JErrorPane.invokeErrorMessage(e);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addGroup(layout.createSequentialGroup()
                            .addGap(20, 20, 20)
                            .addComponent(jLabel1)
                            .addGap(5, 5, 5))
                    .addGroup(layout.createSequentialGroup()
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(20, 20, 20))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGap(475, 475, 475)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addComponent(jLabel2)
                                .addGap(5, 5, 5))
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(20, 20, 20))
                .addGroup(javax.swing.GroupLayout.Alignment.CENTER, layout.createSequentialGroup()
                    .addComponent(jButton1)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, true)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(20, 20, 20))
                .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, true)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(10, 10, 10)
                        .addComponent(jButton1)
                        .addGap(15, 15, 15))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed() {//GEN-FIRST:event_jList1ValueChanged
        utilFrame.jButton6ResetText();
        dispose();
        new ShuffleDialog(arrayManager, this);
    }//GEN-LAST:event_jList1ValueChanged

    private int jList1PrevSelection;

    private void jList1ValueChanged() {//GEN-FIRST:event_jList1ValueChanged
        if (initializing || jList1.getValueIsAdjusting())
            return;
        int selection = jList1.getSelectedIndex();
        Distributions[] distributions = arrayManager.getDistributions();
        if (selection >= 0 && selection < distributions.length) {
            if (arrayManager.setDistribution(distributions[selection])) {
                jList1PrevSelection = selection;
            } else {
                // Selection failed for whatever reason. Need to revert to the previous selection.
                initializing = true;
                jList1.setSelectedIndex(jList1PrevSelection);
                initializing = false;
            }
        }
    }//GEN-LAST:event_jList1ValueChanged

    private void jList2ValueChanged() {//GEN-FIRST:event_jList1ValueChanged
        if (initializing || jList2.getValueIsAdjusting())
            return;
        int selection = jList2.getSelectedIndex();
        // Remove ghost option if something else has been selected
        if (shuffleModel.getElementAt(0).equals(ADVANCED)) {
            if (selection == 0) return;
            shuffleModel.remove(0);
            selection--;
        }
        Shuffles[] shuffles = arrayManager.getShuffles();
        if (selection >= 0 && selection < shuffles.length)
            arrayManager.setShuffleSingle(shuffles[selection]);
    }//GEN-LAST:event_jList1ValueChanged

    @SuppressWarnings("rawtypes")
    private javax.swing.JList jList1;

    @SuppressWarnings("rawtypes")
    private javax.swing.JList jList2;
    // End of variables declaration//GEN-END:variables
}

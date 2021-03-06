package com.github.jmchilton.jgalaxy;

import com.github.jmchilton.blend4j.galaxy.ConfigurationClient;
import com.github.jmchilton.blend4j.galaxy.HistoriesClient;
import com.github.jmchilton.blend4j.galaxy.beans.History;
import com.github.jmchilton.blend4j.galaxy.beans.HistoryContents;
import com.github.jmchilton.blend4j.galaxy.beans.HistoryDetails;
import com.github.jmchilton.jgalaxy.HistoryContentsActionManager.HistoryContentsActionEvent;
import com.github.jmchilton.jgalaxy.InstanceManager.InstanceUpdateEvent;
import com.github.jmchilton.jgalaxy.util.ReflectionUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JMenuItem;

@Singleton
public class MainFrame extends javax.swing.JFrame {
  private static final String DEFAULT_HISTORY_CONTENTS_ACTION = "*Select Action*";
  private static final HistoryCellRenderer HISTORY_CELL_RENDERER = new HistoryCellRenderer();
  private static final HistoryContentsListCellRenderer HISTORY_CONTENTS_RENDERER = new HistoryContentsListCellRenderer();
  private InstanceManager instanceManager;
  private History currentHistory;
  private EventBus eventBus;
  private Map<String, HistoryContentsActionManager.HistoryContentsActionDescription> historyContentsActionMap = Maps.newHashMap();

  @Inject
  public MainFrame(final InstanceManager instanceManager,
                   final EventBus eventBus) {
    initComponents();
    this.eventBus = eventBus;
    this.instanceManager = instanceManager;
    eventBus.register(this);
    initData();
  }

  private void initData() {
    initInstances();
    //initHistoryContentsActions();
  }
  
  private void initInstances() {
    boolean firstInstance = true;
    for(final Instance instance : instanceManager.getInstances()) {
      if(firstInstance) {
        connectionMenu.addSeparator();
        firstInstance = false;
      }
      final JMenuItem item = new JMenuItem();
      item.setText(String.format("%s@%s", instance.getApiKey(), instance.getUrl()));
      item.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent ae) {
          eventBus.post(new ConnectionPresenter.EstablishConnectionEvent(instance.getUrl(), instance.getApiKey()));
        }
      });
      connectionMenu.add(item);
    }
  }

  private void initHistoryContentsActions() {
    historyContentsActions.removeAllItems();
    historyContentsActionMap.clear();
    historyContentsActions.addItem("*Select Action*");

    addAction(new HistoryContentsActionManager.HistoryContentsActionDescription("Bulk Download", BulkDownloadPresenter.BulkDownloadEvent.class));
    addAction(new HistoryContentsActionManager.HistoryContentsActionDescription("Bulk Upload", UploadPresenter.UploadEvent.class));
    // TODO: Move this conditional logic into a thread to make UI more responsive
    // and allow quicker access to first two options.
    final ConfigurationClient client = instanceManager.getCurrentInstance().getConfigurationClient();
    final Map<String, Object> config = client.getRawConfiguration();
    final Boolean useCompositeMultifiles = (Boolean) config.get("use_composite_multifiles");
    if(useCompositeMultifiles != null && useCompositeMultifiles.booleanValue()) {
      addAction(new HistoryContentsActionManager.HistoryContentsActionDescription("Multiple File Dataset Upload", UploadPresenter.MultipleFileDatasetUploadEvent.class));
    }
    historyContentsActions.updateUI();
  }

  public void addAction(final HistoryContentsActionManager.HistoryContentsActionDescription actionDescription) {
    final String name = actionDescription.getName();
    historyContentsActions.addItem(name);
    historyContentsActionMap.put(name, actionDescription);
  }
    
  @Subscribe
  public void handleInstanceUpdateEvent(final InstanceUpdateEvent event) {
    initHistoryContentsActions();
    loadHistories();    
  }

  private void loadHistories() {
    historiesList.removeAll();   
    final Vector<History> histories = new Vector<History>();
    for(final History history : getHistoriesClient().getHistories()) {
      histories.add(history);
    }
    historiesList.setListData(histories);
  }
  
  private void loadContents() {
    historyContentsList.removeAll();
    final Vector<HistoryContents> historyContentsVector = new Vector<HistoryContents>();
    final String historyId = this.currentHistory.getId();
    final HistoryDetails details = getHistoriesClient().showHistory(historyId);
    final Set<String> okIds = Sets.newHashSet(details.getStateIds().get("ok"));
    for(final HistoryContents historyContents : getHistoriesClient().showHistoryContents(historyId)) {
      if(okIds.contains(historyContents.getId())) {
        historyContentsVector.add(historyContents);
      }
    }
    historyContentsList.setListData(historyContentsVector);
  }
  
  private List<HistoryContents> getSelectedHistoryContentsList() {    
    final List<HistoryContents> historyContentList = Lists.newArrayList();
    for(Object historyContentsObject : this.historyContentsList.getSelectedValues()) {
      historyContentList.add((HistoryContents) historyContentsObject);
    }
    return historyContentList;
  }
  
  private HistoriesClient getHistoriesClient() {
    return this.instanceManager.getCurrentInstance().getHistoriesClient();
  }
  
  private void setHistory(final History history) {
    this.currentHistory = history;
    if(history != null) {
      loadContents();
    }
  }  
  
  /**
   * This method is called from within the constructor to initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is always
   * regenerated by the Form Editor.
   */
  @SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    jSeparator1 = new javax.swing.JSeparator();
    jLabel1 = new javax.swing.JLabel();
    historiesPane = new javax.swing.JScrollPane();
    historiesList = new javax.swing.JList();
    historyContentsLabel = new javax.swing.JLabel();
    historyContentsPane = new javax.swing.JScrollPane();
    historyContentsList = new javax.swing.JList();
    historyContentsActions = new javax.swing.JComboBox();
    menuBar = new javax.swing.JMenuBar();
    jMenu1 = new javax.swing.JMenu();
    exitMenuItem = new javax.swing.JMenuItem();
    connectionMenu = new javax.swing.JMenu();
    newConnectionMenuItem = new javax.swing.JMenuItem();
    helpMenu = new javax.swing.JMenu();
    aboutMenuItem = new javax.swing.JMenuItem();

    setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

    jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);

    jLabel1.setText("Histories");

    historiesList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
    historiesList.setCellRenderer(HISTORY_CELL_RENDERER);
    historiesList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
      public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
        historiesListValueChanged(evt);
      }
    });
    historiesPane.setViewportView(historiesList);

    historyContentsLabel.setText("Datasets");

    historyContentsList.setCellRenderer(HISTORY_CONTENTS_RENDERER);
    historyContentsPane.setViewportView(historyContentsList);

    historyContentsActions.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "*Select Action*" }));
    historyContentsActions.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        historyContentsActionsActionPerformed(evt);
      }
    });

    jMenu1.setText("File");

    exitMenuItem.setText("Exit");
    exitMenuItem.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        exitMenuItemActionPerformed(evt);
      }
    });
    jMenu1.add(exitMenuItem);

    menuBar.add(jMenu1);

    connectionMenu.setText("Connection");

    newConnectionMenuItem.setText("New Connection...");
    newConnectionMenuItem.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        newConnectionMenuItemActionPerformed(evt);
      }
    });
    connectionMenu.add(newConnectionMenuItem);

    menuBar.add(connectionMenu);

    helpMenu.setMnemonic('h');
    helpMenu.setText("Help");

    aboutMenuItem.setMnemonic('a');
    aboutMenuItem.setText("About");
    helpMenu.add(aboutMenuItem);

    menuBar.add(helpMenu);

    setJMenuBar(menuBar);

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addContainerGap()
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addComponent(historiesPane)
          .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 326, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(historyContentsActions, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(historyContentsPane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 388, javax.swing.GroupLayout.PREFERRED_SIZE))
          .addComponent(historyContentsLabel))
        .addContainerGap())
    );
    layout.setVerticalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addComponent(jSeparator1)
      .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
        .addContainerGap()
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addComponent(historyContentsLabel)
          .addComponent(jLabel1))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addGroup(layout.createSequentialGroup()
            .addComponent(historyContentsPane, javax.swing.GroupLayout.DEFAULT_SIZE, 324, Short.MAX_VALUE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addComponent(historyContentsActions, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
          .addComponent(historiesPane))
        .addContainerGap())
    );

    pack();
  }// </editor-fold>//GEN-END:initComponents

  private void exitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitMenuItemActionPerformed
    System.exit(0);
  }//GEN-LAST:event_exitMenuItemActionPerformed

  private void newConnectionMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newConnectionMenuItemActionPerformed
    eventBus.post(new ConnectionPresenter.ManageConnectionEvent());
  }//GEN-LAST:event_newConnectionMenuItemActionPerformed

  private void historiesListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_historiesListValueChanged
    final History history = (History) historiesList.getSelectedValue();
    setHistory(history);
  }//GEN-LAST:event_historiesListValueChanged

  private void historyContentsActionsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_historyContentsActionsActionPerformed
    final Object selectedItem = historyContentsActions.getSelectedItem();
    if(selectedItem == null) {
      return;
    }
    final String actionLabel = selectedItem.toString();
    if(!actionLabel.equals(DEFAULT_HISTORY_CONTENTS_ACTION)) {
      HistoryContentsSelectionModel model = new HistoryContentsSelectionModel(instanceManager.getCurrentInstance(), currentHistory, getSelectedHistoryContentsList());

      Class<? extends HistoryContentsActionEvent> eventClass = historyContentsActionMap.get(actionLabel).getEventClass();
      HistoryContentsActionEvent event = ReflectionUtils.newInstance(eventClass);
      event.setModel(model);

      eventBus.post(event);
    }
  }//GEN-LAST:event_historyContentsActionsActionPerformed

  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JMenuItem aboutMenuItem;
  private javax.swing.JMenu connectionMenu;
  private javax.swing.JMenuItem exitMenuItem;
  private javax.swing.JMenu helpMenu;
  private javax.swing.JList historiesList;
  private javax.swing.JScrollPane historiesPane;
  private javax.swing.JComboBox historyContentsActions;
  private javax.swing.JLabel historyContentsLabel;
  private javax.swing.JList historyContentsList;
  private javax.swing.JScrollPane historyContentsPane;
  private javax.swing.JLabel jLabel1;
  private javax.swing.JMenu jMenu1;
  private javax.swing.JSeparator jSeparator1;
  private javax.swing.JMenuBar menuBar;
  private javax.swing.JMenuItem newConnectionMenuItem;
  // End of variables declaration//GEN-END:variables
}

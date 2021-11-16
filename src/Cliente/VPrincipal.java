package Cliente;

import common.Cliente;
import java.util.HashMap;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;

public class VPrincipal extends javax.swing.JFrame {
    
    private final Cliente_Impl cliente;
    private final HashMap<String,String> mensajes;

    public VPrincipal(Cliente_Impl cliente) {
        initComponents();
        this.cliente = cliente;
        this.mensajes = new HashMap<>();
        this.listaAmigos.addListSelectionListener((ListSelectionEvent listSelectionEvent) -> {
            this.panelMensajes.setText(mensajes.get(this.listaAmigos.getSelectedValue()));
        });
        
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                cliente.shutdown();
            }   
        });
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        listaAmigos = new javax.swing.JList<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        panelMensajes = new javax.swing.JTextArea();
        campoAmistad = new javax.swing.JTextField();
        botonAmistad = new javax.swing.JButton();
        campoMensaje = new javax.swing.JTextField();
        botonMensaje = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Chat GBF-MCD");

        listaAmigos.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(listaAmigos);

        panelMensajes.setColumns(20);
        panelMensajes.setRows(5);
        jScrollPane2.setViewportView(panelMensajes);

        botonAmistad.setText("Enviar solicitud");
        botonAmistad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonAmistadActionPerformed(evt);
            }
        });

        botonMensaje.setText("Enviar");
        botonMensaje.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonMensajeActionPerformed(evt);
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
                        .addComponent(campoAmistad, javax.swing.GroupLayout.PREFERRED_SIZE, 315, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(botonAmistad)
                        .addGap(0, 194, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(campoMensaje)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(botonMensaje)))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(campoAmistad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botonAmistad))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 440, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jScrollPane2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(campoMensaje, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(botonMensaje))))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void botonAmistadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonAmistadActionPerformed
        if(!campoAmistad.getText().isEmpty()){
            if(cliente.enviarSolicitud(campoAmistad.getText())) JOptionPane.showMessageDialog(this, "Solicitud enviada");
            else JOptionPane.showMessageDialog(this, "Error en el envío de la petición", "Error envío", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_botonAmistadActionPerformed

    private void botonMensajeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonMensajeActionPerformed
        if(!campoMensaje.getText().isEmpty()){
            cliente.send(listaAmigos.getSelectedValue(), campoMensaje.getText());
            campoMensaje.setText("");
        }
    }//GEN-LAST:event_botonMensajeActionPerformed

    public void popUpSolicitud(String solicitante, String solicitado){
        Object[] opciones = { "Aceptar", "Rechazar"};
        cliente.responderSolicitud(solicitante, solicitado, (JOptionPane.showOptionDialog(null,
             "El usuario "+solicitante+" quiere ser su amigo. Acepta?",
             "Solicitud de amistad",
             JOptionPane.YES_NO_OPTION,
             JOptionPane.PLAIN_MESSAGE,
             null,
             opciones,
             null) == JOptionPane.YES_OPTION)==true);
    }
    
    void informarSolicitud(String solicitado, boolean respuesta) {
        if(respuesta) JOptionPane.showMessageDialog(this, "El usuario "+solicitado+" es ahora su amigo");
        else JOptionPane.showMessageDialog(this, "El usuario "+solicitado+"ha rechazado su solicitud de amistad", "Solicitud rechazada", JOptionPane.ERROR_MESSAGE);
    }

    void actualizarAmigos(HashMap<String, Cliente> amigosOnline) {
        DefaultListModel<String> lm = new DefaultListModel();
        for(String nombreAmigo : amigosOnline.keySet()){
            lm.addElement(nombreAmigo);
            if(!mensajes.containsKey(nombreAmigo)) mensajes.put(nombreAmigo, "");
        }
        listaAmigos.setModel(lm);
    }
    
    void registrarMensaje(String emisor, String mensaje) {
        String result = this.mensajes.get(emisor) + "\n" + mensaje;
        this.mensajes.put(emisor, result);
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botonAmistad;
    private javax.swing.JButton botonMensaje;
    private javax.swing.JTextField campoAmistad;
    private javax.swing.JTextField campoMensaje;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JList<String> listaAmigos;
    private javax.swing.JTextArea panelMensajes;
    // End of variables declaration//GEN-END:variables
}

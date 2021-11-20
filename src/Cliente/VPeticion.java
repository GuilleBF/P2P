package Cliente;

public class VPeticion extends javax.swing.JFrame {

    VPrincipal ventana;
    String solicitante;
    String solicitado;
    boolean respondido = false;
    
    public VPeticion(String solicitante, String solicitado, VPrincipal ventana) {
        initComponents();
        this.solicitante = solicitante;
        this.solicitado = solicitado;
        this.texto.setText("El usuario "+solicitante+" quiere ser su amigo.");
        this.ventana = ventana;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        texto = new javax.swing.JLabel();
        botonAceptar = new javax.swing.JButton();
        botonRechazar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Petición de amistad");

        texto.setText("jLabel1");

        botonAceptar.setText("Aceptar");
        botonAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonAceptarActionPerformed(evt);
            }
        });

        botonRechazar.setText("Rechazar");
        botonRechazar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonRechazarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(texto)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(botonAceptar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(botonRechazar)))
                .addContainerGap(244, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(texto)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(botonAceptar)
                    .addComponent(botonRechazar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void botonAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonAceptarActionPerformed
        ventana.responderSolicitud(this, true);
        respondido = true;
        this.setVisible(false);
        this.dispose();
    }//GEN-LAST:event_botonAceptarActionPerformed

    private void botonRechazarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonRechazarActionPerformed
        ventana.responderSolicitud(this, false);
        respondido = false;
        this.setVisible(false);
        this.dispose();
    }//GEN-LAST:event_botonRechazarActionPerformed

    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botonAceptar;
    private javax.swing.JButton botonRechazar;
    private javax.swing.JLabel texto;
    // End of variables declaration//GEN-END:variables
}

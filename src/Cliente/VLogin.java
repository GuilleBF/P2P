package Cliente;

import javax.swing.JOptionPane;

public class VLogin extends javax.swing.JFrame {

    private final Cliente_Impl cliente;
    
    public VLogin(Cliente_Impl cliente) {
        initComponents();
        this.cliente = cliente;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        panelLogin = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        campoUsuarioLogin = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        campoContrasenhaLogin = new javax.swing.JTextField();
        botonLogin = new javax.swing.JButton();
        panelRegistro = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        campoUsuarioRegistro = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        campoContrasenhaRegistro = new javax.swing.JTextField();
        botonRegistro = new javax.swing.JButton();

        setMinimumSize(new java.awt.Dimension(450, 270));

        jTabbedPane1.setMaximumSize(new java.awt.Dimension(426, 246));
        jTabbedPane1.setMinimumSize(new java.awt.Dimension(426, 246));

        jLabel1.setText("Usuario");

        jLabel2.setText("Contraseña");

        botonLogin.setText("Entrar");
        botonLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonLoginActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelLoginLayout = new javax.swing.GroupLayout(panelLogin);
        panelLogin.setLayout(panelLoginLayout);
        panelLoginLayout.setHorizontalGroup(
            panelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLoginLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelLoginLayout.createSequentialGroup()
                        .addGroup(panelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addGap(0, 301, Short.MAX_VALUE))
                    .addComponent(campoContrasenhaLogin)
                    .addComponent(campoUsuarioLogin)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelLoginLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(botonLogin)))
                .addContainerGap())
        );
        panelLoginLayout.setVerticalGroup(
            panelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLoginLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(campoUsuarioLogin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(campoContrasenhaLogin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(botonLogin)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Login", panelLogin);

        panelRegistro.setMaximumSize(null);
        panelRegistro.setMinimumSize(null);

        jLabel3.setText("Usuario");

        jLabel4.setText("Contraseña");

        botonRegistro.setText("Enviar");
        botonRegistro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonRegistroActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelRegistroLayout = new javax.swing.GroupLayout(panelRegistro);
        panelRegistro.setLayout(panelRegistroLayout);
        panelRegistroLayout.setHorizontalGroup(
            panelRegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRegistroLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelRegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelRegistroLayout.createSequentialGroup()
                        .addGroup(panelRegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4))
                        .addGap(0, 301, Short.MAX_VALUE))
                    .addComponent(campoContrasenhaRegistro)
                    .addComponent(campoUsuarioRegistro)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelRegistroLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(botonRegistro)))
                .addContainerGap())
        );
        panelRegistroLayout.setVerticalGroup(
            panelRegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRegistroLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(campoUsuarioRegistro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(campoContrasenhaRegistro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(botonRegistro)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Registro", panelRegistro);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(26, Short.MAX_VALUE))
        );

        jTabbedPane1.getAccessibleContext().setAccessibleName("Registro");
        jTabbedPane1.getAccessibleContext().setAccessibleDescription("");
    }// </editor-fold>//GEN-END:initComponents

    private void botonLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonLoginActionPerformed
        cliente.login(campoUsuarioLogin.getText(),campoContrasenhaLogin.getText());
    }//GEN-LAST:event_botonLoginActionPerformed

    private void botonRegistroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonRegistroActionPerformed
        cliente.registrar(campoUsuarioRegistro.getText(),campoContrasenhaRegistro.getText());
    }//GEN-LAST:event_botonRegistroActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botonLogin;
    private javax.swing.JButton botonRegistro;
    private javax.swing.JTextField campoContrasenhaLogin;
    private javax.swing.JTextField campoContrasenhaRegistro;
    private javax.swing.JTextField campoUsuarioLogin;
    private javax.swing.JTextField campoUsuarioRegistro;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JPanel panelLogin;
    private javax.swing.JPanel panelRegistro;
    // End of variables declaration//GEN-END:variables

    void estadoRegistro(int codigo) {
        switch(codigo){
            case 0:
                JOptionPane.showMessageDialog(this, "Registrado correctamente");
                break;
            case 1:
                JOptionPane.showMessageDialog(this, "El usuario ya existe", "Error", JOptionPane.ERROR_MESSAGE);
                break;
            case 2:
                JOptionPane.showMessageDialog(this, "Error de conexión, vuelva a intentarlo", "Error", JOptionPane.ERROR_MESSAGE);
        }  
    }

    void loginErroneo() {
        JOptionPane.showMessageDialog(this, "Error en el login", "Error", JOptionPane.ERROR_MESSAGE);
    }
}

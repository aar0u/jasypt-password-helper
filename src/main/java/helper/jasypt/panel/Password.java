package helper.jasypt.panel;

import helper.jasypt.service.Encryptor;
import java.awt.*;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Password extends JPanel {

  private final Encryptor encryptor = new Encryptor();

  public Password() {
    this.setLayout(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridwidth = GridBagConstraints.REMAINDER;

    gbc.insets = new Insets(20, 30, 5, 30);

    JLabel label = new JLabel("Value to be encrypted or decrypted ( with ENC() ):");
    this.add(label, gbc);

    gbc.insets = new Insets(0, 30, 5, 30);

    final JTextField textField = new JTextField(16);
    // textField.setBounds(5, 5, 280, 50); // to get height, set large font
    textField.setFont(textField.getFont().deriveFont(20f));
    this.add(textField, gbc);

    label = new JLabel("Output:");
    this.add(label, gbc);

    gbc.insets = new Insets(5, 30, 20, 30);

    JTextField textField2 = new JTextField(30);
    // textField.setBounds(5, 5, 280, 50); // to get height, set large font
    textField2.setFont(textField2.getFont().deriveFont(20f));
    this.add(textField2, gbc);

    textField
        .getDocument()
        .addDocumentListener(
            new DocumentListener() {
              public void changedUpdate(DocumentEvent e) {
                doThings();
              }

              public void removeUpdate(DocumentEvent e) {
                doThings();
              }

              public void insertUpdate(DocumentEvent e) {
                doThings();
              }

              public void doThings() {
                // JOptionPane.showMessageDialog(null, "msg", "title",JOptionPane.ERROR_MESSAGE);
                String input = textField.getText();
                if (input.length() == 0) {
                  textField2.setText("N/A");
                  return;
                }
                String output =
                    input.startsWith("ENC(")
                        ? encryptor.decrypt(input)
                        : "ENC(" + encryptor.encrypt(input) + ")";
                textField2.setText(output);
              }
            });
  }

  public static void run() {
    EventQueue.invokeLater(
        () -> {
          try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
          } catch (ClassNotFoundException
              | InstantiationException
              | IllegalAccessException
              | UnsupportedLookAndFeelException e) {
            log.error("", e);
          }

          JFrame frame = new JFrame("Password Helper");
          frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
          frame.add(new Password());
          frame.pack();
          frame.setLocationRelativeTo(null);
          frame.setVisible(true);
        });
  }
}

package org.server.utils;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
@Log4j2
public class MailComponent {

    private final JavaMailSender javaMailSender;

    public MailComponent(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendVerificationEmail(String to, String userName, String verificationLink) {
        MimeMessage message = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(to);
            helper.setSubject("Verify your Nomada account");

            String htmlContent = String.format(
                    """
                    <html>
                    <body style="font-family: Arial, sans-serif; background-color: #f4f4f4; margin: 0; padding: 0;">
                      <table align="center" width="100%%" cellpadding="0" cellspacing="0" style="max-width: 600px; background-color: #ffffff; margin-top: 40px; border-radius: 8px; overflow: hidden;">
                        <tr>
                          <td style="padding: 20px 0; text-align: center; background-color: #2196F3;">
                            <img src="cid:nomada_logo" alt="Nomada Logo" style="height: 60px;">
                          </td>
                        </tr>
                        <tr>
                          <td style="padding: 30px;">
                            <h2 style="color: #333333;">Welcome to Nomada, %s!</h2>
                            <p style="color: #555555; font-size: 16px;">
                              Thanks for signing up! Please confirm your email address by clicking the button below.
                            </p>
                            <div style="text-align: center; margin: 30px 0;">
                              <a href="%s" style="background-color: #1565C0; color: #ffffff; padding: 14px 24px; text-decoration: none; font-weight: bold; border-radius: 6px; display: inline-block;">
                                Verify My Account
                              </a>
                            </div>
                            <p style="color: #999999; font-size: 14px;">
                              If you didn’t create a Nomada account, you can safely ignore this email.
                            </p>
                          </td>
                        </tr>
                        <tr>
                          <td style="background-color: #f4f4f4; padding: 20px; text-align: center; font-size: 12px; color: #999;">
                            &copy; 2025 Nomada. All rights reserved.<br>
                            Pin Your Finds, Inspire the World. 🌍
                          </td>
                        </tr>
                      </table>
                    </body>
                    </html>
                    """, userName, verificationLink
            );
            helper.setText(htmlContent, true);
            FileSystemResource logo = new FileSystemResource(new File("src/main/resources/static/images/app_logo.png"));
            helper.addInline("nomada_logo", logo);
            javaMailSender.send(message);
        } catch (MessagingException e) {
            log.error(e.getMessage(), e);
        }
    }
}

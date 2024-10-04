package app.ecosynergy.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("noreply@ecosynergybr.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }

    public void sendConfirmationEmail(String to, String code) {
        String subject = "Confirmação de Email - Ecosynergy";
        String text = "Seu código de confirmação é: " + code + ".\nInsira este código para verificar seu email.";
        sendEmail(to, subject, text);
    }

    public void sendWelcomeEmail(String recipientEmail, String name) {
        String subject = "Bem-vindo(a) à Ecosynergy!";

        String body = String.format(
                """
                        Olá %s,

                        Bem-vindo(a) à Ecosynergy! Estamos felizes em tê-lo(a) conosco.

                        Aqui estão algumas coisas que você pode fazer na plataforma:
                        1. Criar uma nova equipe e começar a gerenciar suas metas ambientais.
                        2. Convidar outros usuários para sua equipe e colaborar na redução de emissões.
                        3. Ou, aguardar um convite para participar de uma equipe já existente e contribuir com as metas dela.

                        Desejamos muito sucesso na sua jornada rumo à sustentabilidade ambiental!

                        Atenciosamente,
                        Equipe Ecosynergy""",
                name
        );

        sendEmail(recipientEmail, subject, body);
    }

    public void sendInviteEmail(String recipientEmail, String teamName, String inviterName) {
        String subject = "Você foi convidado para se juntar à equipe " + teamName + " - Ecosynergy";
        String text = "Olá,\n\n" + inviterName + " convidou você para se juntar à equipe " + teamName + " no sistema Ecosynergy."
                + "\n\nAcesse sua conta para aceitar ou recusar o convite."
                + "\n\nAtenciosamente,\nEquipe Ecosynergy";
        sendEmail(recipientEmail, subject, text);
    }

    public void sendInviteAcceptedNotification(String senderEmail, String recipientName, String teamName) {
        String subject = "Convite Aceito - Ecosynergy";
        String text = "Olá,\n\n" + recipientName + " aceitou seu convite para se juntar à equipe " + teamName + "."
                + "\n\nAtenciosamente,\nEquipe Ecosynergy";
        sendEmail(senderEmail, subject, text);
    }

    public void sendInviteRejectedNotification(String senderEmail, String recipientName, String teamName) {
        String subject = "Convite Recusado - Ecosynergy";
        String text = "Olá,\n\n" + recipientName + " recusou seu convite para se juntar à equipe " + teamName + "."
                + "\n\nAtenciosamente,\nEquipe Ecosynergy";
        sendEmail(senderEmail, subject, text);
    }
}

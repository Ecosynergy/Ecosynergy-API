package app.ecosynergy.api.services;

import app.ecosynergy.api.models.Invite;
import app.ecosynergy.api.models.User;
import app.ecosynergy.api.util.ValidationUtils;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(String to, String subject, String htmlContent) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

        helper.setText(htmlContent, true);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setFrom("noreply@ecosynergybr.com");

        mailSender.send(mimeMessage);
    }

    public void sendConfirmationEmail(String recipientEmail, String name, String code) throws MessagingException {
        String subject = "Verifique seu E-mail";

        String htmlContent = "<html>\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <title>Email de Confirmação Ecosynergy</title>\n" +
                "    <link href=\"https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;600&display=swap\" rel=\"stylesheet\">\n" +
                "</head>\n" +
                "<body style=\"margin: 0; padding: 0; background-color: #f9f9f9; font-family: 'Poppins', sans-serif;\">\n" +
                "    <table style=\"width: 100%; background-color: #f9f9f9; padding: 20px; text-align: center;\">\n" +
                "        <tr>\n" +
                "            <td style=\"text-align: center;\">\n" +
                "                <table style=\"max-width: 600px; width: 100%; background-color: #ffffff; padding: 20px; border-radius: 8px; box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1); margin: 0 auto; text-align: start;\">\n" +
                "                    <tr>\n" +
                "                        <td style=\"padding: 10px; font-size: 16px; color: #333333;\">\n" +
                "                            <p style=\"margin: 0; font-weight: 400;\">Seu código de confirmação de e-mail da sua conta Ecosynergy é: <strong>" + code + "</strong></p>\n" +
                "                        </td>\n" +
                "                    </tr>\n" +
                "                    <tr>\n" +
                "                        <td style=\"padding: 20px 0; text-align: center;\">\n" +
                "                            <img src=\"https://github.com/Ecosynergy/VisualIdentity/blob/master/Logo_Symbol_Transparent_Wtih_Shine.png?raw=true\" alt=\"Logo Ecosynergy\" style=\"max-width: 200px; height: auto; display: block; margin: 0 auto;\">\n" +
                "                        </td>\n" +
                "                    </tr>\n" +
                "                    <tr>\n" +
                "                        <td style=\"padding: 20px; font-size: 16px; line-height: 24px; color: #333333;\">\n" +
                "                            <p style=\"margin: 0 0 15px;\"><i>Olá, " + ValidationUtils.formatFullName(name) + ".</i></p>\n" +
                "                            <p style=\"margin: 0 0 15px;\">Nós recebemos uma solicitação de um código de uso único para sua conta Ecosynergy.</p>\n" +
                "                            <p style=\"margin: 0 0 15px;\"><i>Verifique os detalhes da sessão para garantir que tenha sido você.</i></p>\n" +
                "                            <table style=\"width: 100%; background-color: #e2e1e1; padding: 20px; margin: 20px 0; border-radius: 6px; text-align: center;\">\n" +
                "                                <tr>\n" +
                "                                    <td>\n" +
                "                                        <p style=\"margin: 0; font-size: 16px; font-weight: 600;\">Seu código de confirmação é:</p>\n" +
                "                                        <h2 style=\"margin: 10px 0; font-size: 50px; font-weight: 400; color: #333333;\">" + code + "</h2>\n" +
                "                                    </td>\n" +
                "                                </tr>\n" +
                "                            </table>\n" +
                "                            <p style=\"margin: 0 0 15px;\"><i>Insira este código para verificar seu email e ativar sua conta no sistema Ecosynergy.</i></p>\n" +
                "                            <p style=\"margin: 0 0 15px;\">A partir da verificação, você poderá explorar todas as funcionalidades da nossa plataforma, garantindo segurança e confiabilidade em suas operações.</p>\n" +
                "                            <p style=\"margin: 0 0 15px; font-size: 14px; color: #555555;\">Se você não solicitou este código, ignore este email.</p>\n" +
                "                        </td>\n" +
                "                    </tr>\n" +
                "                    <tr>\n" +
                "                        <td style=\"padding: 20px; font-size: 14px; color: #777777; text-align: center;\">\n" +
                "                            <p style=\"margin: 0;\">Atenciosamente,<br><strong>Ecosynergy</strong></p>\n" +
                "                        </td>\n" +
                "                    </tr>\n" +
                "                </table>\n" +
                "            </td>\n" +
                "        </tr>\n" +
                "    </table>\n" +
                "</body>\n" +
                "</html>";

        sendEmail(recipientEmail, subject, htmlContent);
    }

    public void sendWelcomeEmail(User user) throws MessagingException {
        String subject = "Bem-vindo(a) à Ecosynergy!";

        String htmlContent = "";

        sendEmail(user.getEmail(), subject, htmlContent);
    }

    public void sendInviteEmail(Invite invite) throws MessagingException {
        String subject = "Você foi convidado para se juntar à equipe " + invite.getTeam().getName() + ".";
        String text = "<html>\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <title>Email de Envio de Convite Ecosynergy</title>\n" +
                "    <link href=\"https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;600&display=swap\" rel=\"stylesheet\">\n" +
                "</head>\n" +
                "<body style=\"margin: 0; padding: 0; background-color: #f9f9f9; font-family: 'Poppins', sans-serif;\">\n" +
                "    <table style=\"width: 100%; background-color: #f9f9f9; padding: 20px; text-align: center;\">\n" +
                "        <tr>\n" +
                "            <td style=\"text-align: center;\">\n" +
                "                <table style=\"max-width: 600px; width: 100%; background-color: #ffffff; padding: 20px; border-radius: 8px; box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1); margin: 0 auto; text-align: start;\">\n" +
                "                    <tr>\n" +
                "                        <td style=\"padding: 10px; font-size: 16px; color: #333333;\">\n" +
                "                            <p style=\"margin: 0; font-weight: 400;\">Confira o convite e veja como participar da equipe.</p>\n" +
                "                        </td>\n" +
                "                    </tr>\n" +
                "                    <tr>\n" +
                "                        <td style=\"padding: 20px 0; text-align: center;\">\n" +
                "                            <img src=\"https://github.com/Ecosynergy/VisualIdentity/blob/master/Logo_Symbol_Transparent_Wtih_Shine.png?raw=true\" alt=\"Logo Ecosynergy\" style=\"max-width: 200px; height: auto; display: block; margin: 0 auto;\">\n" +
                "                        </td>\n" +
                "                    </tr>\n" +
                "                    <tr>\n" +
                "                        <td style=\"padding: 20px; font-size: 16px; line-height: 24px; color: #333333;\">\n" +
                "                            <p style=\"margin: 0 0 15px;\"><i>Olá, " + invite.getRecipient().getFullName().split(" ")[0] + ".</i></p>\n" +
                "                            <h1 style=\"margin: 0 0 20px; font-size: 22px; font-weight: 400; color: #333333; font-weight: bold;\">\n" +
                "                                Você foi convidado para se juntar à equipe " + invite.getTeam().getName() + " no Ecosynergy.\n" +
                "                            </h1>\n" +
                "                            <p style=\"margin: 0 0 15px;\">A equipe está aguardando sua resposta para iniciar a colaboração.</p>\n" +
                "                            <table style=\"width: 100%; background-color: #e2e1e1; padding: 20px; margin: 20px 0; border-radius: 6px; text-align: start;\">\n" +
                "                                <tr>\n" +
                "                                    <td>\n" +
                "                                        <p style=\"margin: 0; font-size: 16px;\">\n" +
                "                                            Se você ainda não conhece o\n" +
                "                                            <strong>Ecosynergy</strong>, somos uma solução de\n" +
                "                                            monitoramento ambiental dedicada a\n" +
                "                                            ajudar empresas a reduzir suas\n" +
                "                                            emissões e tomar o ambiente industrial\n" +
                "                                            mais sustentável.\n" +
                "                                        </p>\n" +
                "                                        <p style=\"margin: 10px 0; font-size: 16px;\">\n" +
                "                                            A equipe " + invite.getTeam().getName() + " acredita que,<br>\n" +
                "                                            juntos, podemos contribuir para um<br>\n" +
                "                                            futuro mais verde e responsável.\n" +
                "                                        </p>\n" +
                "                                    </td>\n" +
                "                                </tr>\n" +
                "                            </table>\n" +
                "                            <p style=\"margin: 0 0 15px;\">Quando estiver pronto, clique no botão abaixo para aceitar ou recusar o convite:</p>\n" +
                "                            <table style=\"width: 100%; margin: 20px 0;\">\n" +
                "                                <tr>\n" +
                "                                    <td style=\"text-align: center;\">\n" +
                "                                        <a href=\"ecosynergy://invite?inviteId=" + invite.getId() + "\" onclick=\"redirectToApp()\" style=\"display: inline-block; padding: 10px 20px; font-size: 16px; color: #000; background-color: #28a745; text-decoration: none; border-radius: 5px; font-family: 'Poppins', sans-serif; text-align: center; font-weight: 600;\">\n" +
                "                                            ACESSAR PLATAFORMA ECOSYNERGY\n" +
                "                                        </a>                                        \n" +
                "                                    </td>\n" +
                "                                </tr>\n" +
                "                            </table>\n" +
                "                        </td>\n" +
                "                    </tr>\n" +
                "                    <tr>\n" +
                "                        <td style=\"padding: 20px; font-size: 14px; color: #777777; text-align: center;\">\n" +
                "                            <p style=\"margin: 0;\">Atenciosamente,<br><strong>Ecosynergy</strong></p>\n" +
                "                        </td>\n" +
                "                    </tr>\n" +
                "                </table>\n" +
                "            </td>\n" +
                "        </tr>\n" +
                "    </table>\n" +
                "    <script>\n" +
                "        function redirectToApp() {\n" +
                "            const appLink = 'ecosynergy://invite?inviteId=" + invite.getId() + "';\n" +
                "            const fallbackLink = 'http://ecosynergybr.com.s3-website-us-east-1.amazonaws.com/';\n" +
                "    \n" +
                "            window.location.href = appLink;\n" +
                "\n" +
                "            setTimeout(() => {\n" +
                "                window.location.href = fallbackLink;\n" +
                "            }, 2000);\n" +
                "        }\n" +
                "    </script>\n" +
                "</body>\n" +
                "</html>";
        sendEmail(invite.getRecipient().getEmail(), subject, text);
    }

    public void sendInviteAcceptedNotification(Invite invite) throws MessagingException {
        String subject = "Convite para a equipe " + invite.getTeam().getName() + " aceito.";

        String htmlContent = "<html>\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <title>Email Ecosynergy</title>\n" +
                "</head>\n" +
                "<body style=\"margin: 0; padding: 0; background-color: #f9f9f9; font-family: 'Poppins', sans-serif;\">\n" +
                "    <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"background-color: #f9f9f9; padding: 20px; text-align: center; border: none;\">\n" +
                "        <tr>\n" +
                "            <td style=\"text-align: center\">\n" +
                "                <table cellpadding=\"0\" cellspacing=\"0\" width=\"600\" style=\"max-width: 600px; width: 100%; background-color: #ffffff; padding: 20px; border-radius: 8px; box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1); text-align: start; border: none;\">\n" +
                "                    <tr>\n" +
                "                        <td style=\"padding: 20px 0; text-align: center\">\n" +
                "                            <img src=\"https://github.com/Ecosynergy/VisualIdentity/blob/master/Logo_Symbol_Transparent_Wtih_Shine.png?raw=true\" alt=\"Logo Ecosynergy\" style=\"max-width: 200px; height: auto;\">\n" +
                "                        </td>\n" +
                "                    </tr>\n" +
                "                    <tr>\n" +
                "                        <td style=\"padding: 20px; font-size: 16px; line-height: 24px; color: #333333;\">\n" +
                "                            <p style=\"margin: 0 0 20px;\">Olá, <strong>" + invite.getSender().getFullName().split(" ")[0] + "</strong>.</p>\n" +
                "                            <h1 style=\"margin: 0 0 20px; font-size: 22px; font-weight: 400; color: #333333;\">\n" +
                "                                Informamos que <strong>" + invite.getRecipient().getFullName() + "</strong> aceitou seu convite para se juntar à equipe <strong>" + invite.getTeam().getName() + "</strong> no Ecosynergy.\n" +
                "                            </h1>\n" +
                "                            <p style=\"margin: 0 0 20px; font-size: 16px; color: #555555;\">\n" +
                "                                <i>Agora que a equipe está completa, vocês poderão colaborar de forma eficaz e aproveitar ao máximo as ferramentas que nossa plataforma oferece para monitorar emissões.</i>\n" +
                "                            </p>\n" +
                "                            <div style=\"background-color: #e2e1e1; padding: 20px; margin: 20px 0; border-radius: 6px;\">\n" +
                "                                <p style=\"margin: 0; font-size: 16px; font-weight: 600;\">Dicas para começar:</p>\n" +
                "                                <ol style=\"margin: 10px 0 0 20px; padding: 0; color: #555555;\">\n" +
                "                                    <li style=\"margin: 10px 0;\">\n" +
                "                                        <p style=\"margin: 0; font-size: 14px;\">\n" +
                "                                            <strong>Explore as Ferramentas:</strong> Familiarize-se com as funcionalidades do Ecosynergy.\n" +
                "                                        </p>\n" +
                "                                    </li>\n" +
                "                                    <li style=\"margin: 10px 0;\">\n" +
                "                                        <p style=\"margin: 0; font-size: 14px;\">\n" +
                "                                            <strong>Comunicação:</strong> Utilize a plataforma para facilitar a comunicação entre os membros da equipe.\n" +
                "                                        </p>\n" +
                "                                    </li>\n" +
                "                                    <li style=\"margin: 10px 0;\">\n" +
                "                                        <p style=\"margin: 0; font-size: 14px;\">\n" +
                "                                            <strong>Defina Metas:</strong> Estabeleça metas claras para o projeto.\n" +
                "                                        </p>\n" +
                "                                    </li>\n" +
                "                                </ol>\n" +
                "                            </div>\n" +
                "                            <p style=\"margin: 0; font-size: 16px; color: #555555;\">\n" +
                "                                <i>Se você ou a equipe tiverem alguma dúvida ou precisarem de suporte, não hesitem em entrar em contato com nossa equipe de suporte.</i>\n" +
                "                            </p>\n" +
                "                        </td>\n" +
                "                    </tr>\n" +
                "                    <tr>\n" +
                "                        <td style=\"padding: 20px; font-size: 14px; color: #777777; text-align: center;\">\n" +
                "                            <p style=\"margin: 0;\">Atenciosamente,<br><strong>Ecosynergy</strong></p>\n" +
                "                        </td>\n" +
                "                    </tr>\n" +
                "                </table>\n" +
                "            </td>\n" +
                "        </tr>\n" +
                "    </table>\n" +
                "</body>\n" +
                "</html>";

        sendEmail(invite.getSender().getEmail(), subject, htmlContent);
    }

    public void sendInviteRejectedNotification(Invite invite) throws MessagingException {
        String subject = "Convite para equipe " + invite.getTeam().getName() + " recusado";
        String htmlContent = "<html>\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <title>Email Ecosynergy</title>\n" +
                "    <link href=\"https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;600&display=swap\" rel=\"stylesheet\">\n" +
                "</head>\n" +
                "<body style=\"margin: 0; padding: 0; background-color: #f9f9f9; font-family: 'Poppins', sans-serif;\">\n" +
                "    <table style=\"width: 100%; background-color: #f9f9f9; padding: 20px; text-align: center;\">\n" +
                "        <tr>\n" +
                "            <td style=\"text-align: center;\">\n" +
                "                <table style=\"max-width: 600px; width: 100%; background-color: #ffffff; padding: 20px; border-radius: 8px; box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1); margin: 0 auto; text-align: center;\">\n" +
                "                    <tr>\n" +
                "                        <td style=\"padding: 20px 0; text-align: center;\">\n" +
                "                            <img src=\"https://github.com/Ecosynergy/VisualIdentity/blob/master/Logo_Symbol_Transparent_Wtih_Shine.png?raw=true\" alt=\"Logo Ecosynergy\" style=\"max-width: 200px; height: auto; display: block; margin: 0 auto;\">\n" +
                "                        </td>\n" +
                "                    </tr>\n" +
                "                    <tr>\n" +
                "                        <td style=\"padding: 20px; font-size: 16px; line-height: 24px; color: #333333; text-align: start;\">\n" +
                "                            <p style=\"margin: 0 0 20px;\">Olá, <strong>" + invite.getSender().getFullName().split(" ")[0] + "</strong>.</p>\n" +
                "                            <h1 style=\"margin: 0 0 20px; font-size: 22px; font-weight: 400; color: #333333;\">\n" +
                "                                Informamos que <strong>" + invite.getRecipient().getFullName() + "</strong> recusou seu convite para se juntar à equipe <strong>Ecosynergy</strong> no Ecosynergy.\n" +
                "                            </h1>\n" +
                "                            <p style=\"margin: 0 0 20px; font-size: 16px; color: #555555;\">\n" +
                "                                <i>Entendemos que essa decisão pode ser inesperada, e queremos garantir que você tenha todas as informações necessárias para seguir em frente.</i>\n" +
                "                            </p>\n" +
                "                            <table style=\"width: 100%; background-color: #e2e1e1; padding: 20px; margin: 20px 0; border-radius: 6px; text-align: start;\">\n" +
                "                                <tr>\n" +
                "                                    <td>\n" +
                "                                        <p style=\"margin: 0 0 10px; font-size: 16px; font-weight: 600;\">\n" +
                "                                            Se você desejar, pode enviar um novo convite a " + invite.getRecipient().getFullName().split(" ")[0] + " ou explorar outras opções de colaboração dentro da nossa plataforma.\n" +
                "                                        </p>\n" +
                "                                        <p style=\"margin: 10px 0; text-align: left;\">Se desejar, você pode:</p>\n" +
                "                                        <ol style=\"margin: 10px 0 0 20px; padding: 0; color: #555555; text-align: left;\">\n" +
                "                                            <li style=\"margin: 10px 0;\">\n" +
                "                                                <p style=\"margin: 0; font-size: 14px;\">\n" +
                "                                                    <strong>Reenviar o convite</strong> a " + invite.getRecipient().getFullName().split(" ")[0] + ".\n" +
                "                                                </p>\n" +
                "                                            </li>\n" +
                "                                            <li style=\"margin: 10px 0;\">\n" +
                "                                                <p style=\"margin: 0; font-size: 14px;\">\n" +
                "                                                    <strong>Convidar outros membros</strong> para a equipe.\n" +
                "                                                </p>\n" +
                "                                            </li>\n" +
                "                                            <li style=\"margin: 10px 0;\">\n" +
                "                                                <p style=\"margin: 0; font-size: 14px;\">\n" +
                "                                                    <strong>Continuar trabalhando</strong> com os membros atuais usando as ferramentas do Ecosynergy.\n" +
                "                                                </p>\n" +
                "                                            </li>\n" +
                "                                        </ol>\n" +
                "                                    </td>\n" +
                "                                </tr>\n" +
                "                            </table>\n" +
                "                            <p style=\"margin: 0; font-size: 16px; color: #555555;\">\n" +
                "                                <i>Se precisar de mais informações ou assistência, não hesite em entrar em contato com nossa equipe de suporte.</i>\n" +
                "                            </p>\n" +
                "                        </td>\n" +
                "                    </tr>\n" +
                "                    <tr>\n" +
                "                        <td style=\"padding: 20px; font-size: 14px; color: #777777; text-align: center;\">\n" +
                "                            <p style=\"margin: 0;\">Atenciosamente,<br><strong>Ecosynergy</strong></p>\n" +
                "                        </td>\n" +
                "                    </tr>\n" +
                "                </table>\n" +
                "            </td>\n" +
                "        </tr>\n" +
                "    </table>\n" +
                "</body>\n" +
                "</html>";

        sendEmail(invite.getSender().getEmail(), subject, htmlContent);
    }
}

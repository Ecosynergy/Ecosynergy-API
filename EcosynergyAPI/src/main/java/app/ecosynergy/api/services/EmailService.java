package app.ecosynergy.api.services;

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

        String htmlContent = "<html><head><style>@import url('https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&display=swap');* {font-family: 'Poppins', sans-serif;font-weight: 300;font-style: normal;box-sizing: border-box;padding: 0;margin: 0;}body{display: flex;justify-content: center;width: 100vw;height: 100vh;}.header {margin-bottom: 40px;}.img {display: flex;justify-content: center;}i {font-weight: 200;font-style: normal;font-size: medium;}strong {font-weight: 600;font-style: normal;}p {margin: 15px 0px;}.code {background-color: #e2e1e1;padding: 20px 10px;margin: 10px 0px;}.code p, .code h2 {margin: 0px;width: 80%;font-weight: bolder;font-style: oblique;}h2 {font-weight: 400 !important;font-style: normal !important;font-size: 50px;}</style></head><body><div class=\"container\"><div class=\"header\"><p>Seu código de confirmação do e-mail da sua conta Ecosynergy é: <strong>" + code + "</strong></p></div><div class=\"img\"><img src=\"https://github.com/Ecosynergy/VisualIdentity/blob/master/Logo_Symbol_Transparent_Wtih_Shine.png?raw=true\" alt=\"Logo Ecosynergy\" style=\"width: 200px;\"></div><div class=\"content\"><p><i>Olá, " + name + ".</i></p><p>Nós recebemos uma solicitação de um código de uso único para sua conta Ecosynergy.</p><p><i>Verifique os detalhes da sessão para garantir que tenha sido você.</i></p><div class=\"code\"><p>Seu código de confirmação é:</p><br><h2>" + code + "</h2></div><p><i>Insira este código para verificar seu email e ativar sua conta no sistema Ecosynergy.</i></p><p>A partir da verificação, você poderá explorar todas as funcionalidades da nossa plataforma, garantindo segurança e confiabilidade em suas operações.</p><p>Se você não solicitou este código, ignore este email.</p></div><div class=\"footer\"><p>Atenciosamente,<br>Ecosynergy.</p></div></div></body></html>";

        sendEmail(recipientEmail, subject, htmlContent);
    }

    public void sendWelcomeEmail(String recipientEmail, String recipientName) throws MessagingException {
        String subject = "Bem-vindo(a) à Ecosynergy!";

        String htmlContent = "";

        sendEmail(recipientEmail, subject, htmlContent);
    }

    public void sendInviteEmail(String recipientEmail, String teamName, String inviterName) throws MessagingException {
        String subject = "Você foi convidado para se juntar à equipe " + teamName + ".";
        String text = "Olá,\n\n" + inviterName + " convidou você para se juntar à equipe " + teamName + " no sistema Ecosynergy."
                + "\n\nAcesse sua conta para aceitar ou recusar o convite."
                + "\n\nAtenciosamente,\nEquipe Ecosynergy";
        sendEmail(recipientEmail, subject, text);
    }

    public void sendInviteAcceptedNotification(String senderEmail, String senderName, String recipientName, String teamName) throws MessagingException {
        String subject = "Convite para a equipe " + teamName + " aceito.";

        String htmlContent = "<html>\n" +
                "<head>\n" +
                "    <style>\n" +
                "        @import url('https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&display=swap');\n" +
                "        * {\n" +
                "            font-family: 'Poppins', sans-serif;\n" +
                "            font-weight: 300;\n" +
                "            font-style: normal;\n" +
                "            box-sizing: border-box;\n" +
                "            padding: 0;\n" +
                "            margin: 0;\n" +
                "        }\n" +
                "        body{\n" +
                "            display: flex;\n" +
                "            justify-content: center;\n" +
                "            width: 100vw;\n" +
                "            height: 100vh;\n" +
                "        }\n" +
                "        .header {\n" +
                "            margin-bottom: 40px;\n" +
                "        }\n" +
                "        .img {\n" +
                "            display: flex;\n" +
                "            justify-content: center;\n" +
                "        }\n" +
                "        h1 {\n" +
                "            font-size: 25px;\n" +
                "            font-weight: 400;\n" +
                "            font-style: normal;\n" +
                "        }" +
                "        i {\n" +
                "            font-weight: 200;\n" +
                "            font-style: normal;\n" +
                "        }\n" +
                "        strong {\n" +
                "            font-weight: 600;\n" +
                "            font-style: normal;\n" +
                "        }\n" +
                "        p {\n" +
                "            margin: 15px 0px;\n" +
                "            font-size: 20px;\n" +
                "        }\n" +
                "        .frame {\n" +
                "            background-color: #e2e1e1;\n" +
                "            padding: 40px;\n" +
                "            margin: 10px 0px;\n" +
                "        }\n" +
                "        ol {\n" +
                "            margin-left: 15px;\n" +
                "        }\n" +
                "        ol li p {\n" +
                "            margin: 0px;\n" +
                "            margin-left: 10px;\n" +
                "        }\n" +
                "        ol li {\n" +
                "            margin: 10px 0px;\n" +
                "        }\n" +
                "        h2 {\n" +
                "            font-weight: 400 !important;\n" +
                "            font-style: normal !important;\n" +
                "            font-size: 50px;\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <div class=\"container\">\n" +
                "        <div class=\"img\">\n" +
                "            <img src=\"https://github.com/Ecosynergy/VisualIdentity/blob/master/Logo_Symbol_Transparent_Wtih_Shine.png?raw=true\" alt=\"Logo Ecosynergy\" style=\"width: 200px;\">\n" +
                "        </div>\n" +
                "        <div class=\"content\">\n" +
                "            <p>Olá, " + senderName + ".</p>\n" +
                "            <h1>\n" +
                "                Informamos que <strong>" + recipientName + "</strong> aceitou seu<br>\n" +
                "                convite para se juntar à equipe <strong>" + teamName + "</strong> no Ecosynergy.\n" +
                "            </h1>\n" +
                "            <p>\n" +
                "                <i>\n" +
                "                    Agora que a equipe está completa, vocês poderão colaborar<br>\n" +
                "                    de forma eficaz e aproveitar ao máximo as ferramentas que<br>\n" +
                "                    nossa plataforma oferece para monitorar emissões.\n" +
                "                </i>\n" +
                "            </p>\n" +
                "            <div class=\"frame\">\n" +
                "                <p>Dicas para começar:</p>\n" +
                "                <ol>\n" +
                "                    <li>\n" +
                "                        <p>\n" +
                "                            <strong>Explore as Ferramentas:</strong>\n" +
                "                            Familiarize-se com as funcionalidades<br>\n" +
                "                            do Ecosynergy.\n" +
                "                        </p>\n" +
                "                    </li>\n" +
                "                    <li>\n" +
                "                        <p>\n" +
                "                            <strong>Comunicação:</strong>\n" +
                "                            Utilize a plataforma<br>\n" +
                "                            para faciitar a comunicação entre os<br>\n" +
                "                            membros da equipe.\n" +
                "                        </p>\n" +
                "                    </li>\n" +
                "                    <li>\n" +
                "                        <p>\n" +
                "                            <strong>Defina Metas:</strong>\n" +
                "                            Estabeleça metas claras para o projeto.\n" +
                "                        </p>\n" +
                "                    </li>\n" +
                "                </ol>\n" +
                "            </div>\n" +
                "            <p>\n" +
                "                <i>\n" +
                "                    Se você ou a equipe tiverem alguma dúvida ou precisarem<br>\n" +
                "                    de suporte, não hesitem em entrar em contato com nossa<br>\n" +
                "                    equipe de suporte.\n" +
                "                </i>\n" +
                "            </p>\n" +
                "        </div>\n" +
                "        <div class=\"footer\">\n" +
                "            <p>Atenciosamente,<br>Ecosynergy.</p>\n" +
                "        </div>\n" +
                "    </div>\n" +
                "</body>\n" +
                "</html>";

        sendEmail(senderEmail, subject, htmlContent);
    }

    public void sendInviteRejectedNotification(String senderEmail, String senderName, String recipientName, String teamName) throws MessagingException {
        String subject = "Convite para equipe " + teamName + " recusado";
        String htmlContent = "<html>\n" +
                "<head>\n" +
                "    <style>\n" +
                "        @import url('https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&display=swap');\n" +
                "        * {\n" +
                "            font-family: 'Poppins', sans-serif;\n" +
                "            font-weight: 300;\n" +
                "            font-style: normal;\n" +
                "            box-sizing: border-box;\n" +
                "            padding: 0;\n" +
                "            margin: 0;\n" +
                "        }\n" +
                "        body{\n" +
                "            display: flex;\n" +
                "            justify-content: center;\n" +
                "            width: 100vw;\n" +
                "            height: 100vh;\n" +
                "        }\n" +
                "        .header {\n" +
                "            margin-bottom: 40px;\n" +
                "        }\n" +
                "        .img {\n" +
                "            display: flex;\n" +
                "            justify-content: center;\n" +
                "        }\n" +
                "        h1 {\n" +
                "            font-size: 25px;\n" +
                "            font-weight: 400;\n" +
                "            font-style: normal;\n" +
                "        }\n" +
                "        i {\n" +
                "            font-weight: 200;\n" +
                "            font-style: normal;\n" +
                "        }\n" +
                "        strong {\n" +
                "            font-weight: 600;\n" +
                "            font-style: normal;\n" +
                "        }\n" +
                "        p {\n" +
                "            margin: 15px 0px;\n" +
                "            font-size: 20px;\n" +
                "        }\n" +
                "        .frame {\n" +
                "            background-color: #e2e1e1;\n" +
                "            padding: 40px;\n" +
                "            margin: 10px 0px;\n" +
                "        }\n" +
                "        ol {\n" +
                "            margin-left: 15px;\n" +
                "        }\n" +
                "        ol li p {\n" +
                "            margin: 0px;\n" +
                "            margin-left: 10px;\n" +
                "        }\n" +
                "        ol li {\n" +
                "            margin: 10px 0px;\n" +
                "        }\n" +
                "        h2 {\n" +
                "            font-weight: 400 !important;\n" +
                "            font-style: normal !important;\n" +
                "            font-size: 50px;\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <div class=\"container\">\n" +
                "        <div class=\"img\">\n" +
                "            <img src=\"https://github.com/Ecosynergy/VisualIdentity/blob/master/Logo_Symbol_Transparent_Wtih_Shine.png?raw=true\" alt=\"Logo Ecosynergy\" style=\"width: 200px;\">\n" +
                "        </div>\n" +
                "        <div class=\"content\">\n" +
                "            <p>Olá, " + senderName + ".</p>\n" +
                "            <h1>\n" +
                "                Informamos que <strong>" + recipientName + "</strong> recusou seu<br>\n" +
                "                convite para se juntar à equipe <strong>" + teamName + "</strong> no Ecosynergy.\n" +
                "            </h1>\n" +
                "            <p>\n" +
                "                <i>\n" +
                "                    Entendemos que essa decisão pode ser inesperada, e <br>\n" +
                "                    queremos garantir que você tenha todas as informações <br>\n" +
                "                    necessárias para seguir em frente.\n" +
                "                </i>\n" +
                "            </p>\n" +
                "            <div class=\"frame\">\n" +
                "                <p>\n" +
                "                    Se você desejar, pode enviar um novo convite a <br>\n" +
                "                    " + recipientName.split(" ")[0] + " ou explorar outras opções de <br>\n" +
                "                    colaboração dentro da nossa plataforma.\n" +
                "                </p>\n" +
                "                <p>Se desejar, você pode:</p>\n" +
                "                <ol>\n" +
                "                    <li>\n" +
                "                        <p>\n" +
                "                            <strong>Reenviar o convite</strong>\n" +
                "                            a " + recipientName.split(" ")[0] + ".\n" +
                "                        </p>\n" +
                "                    </li>\n" +
                "                    <li>\n" +
                "                        <p>\n" +
                "                            <strong>Convidar outros membros</strong>\n" +
                "                            para a equipe.\n" +
                "                        </p>\n" +
                "                    </li>\n" +
                "                    <li>\n" +
                "                        <p>\n" +
                "                            <strong>Continuar trabalhando</strong>\n" +
                "                            com os membros atuais usando as ferramentas do Ecosynergy.\n" +
                "                        </p>\n" +
                "                    </li>\n" +
                "                </ol>\n" +
                "            </div>\n" +
                "            <p>\n" +
                "                <i>\n" +
                "                    Se precisar de mais informações ou assistência, não hesite <br>\n" +
                "                    em entrar em contato com nossa equipe de suporte.\n" +
                "                </i>\n" +
                "            </p>\n" +
                "        </div>\n" +
                "        <div class=\"footer\">\n" +
                "            <p>Atenciosamente,<br>Ecosynergy.</p>\n" +
                "        </div>\n" +
                "    </div>\n" +
                "</body>\n" +
                "</html>";

        sendEmail(senderEmail, subject, htmlContent);
    }
}

package com.PetShere.util;

public class MailTemplates {

    public static String postRegistrationMail(String userName) {
        return """
                <html>
                    <body style="font-family: Arial, sans-serif; background-color: #f5f5f5; padding: 40px;">
                        <table align="center" width="600" cellpadding="0" cellspacing="0"\s
                               style="background-color: #ffffff; padding: 30px; border-radius: 8px; box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);">
                            <tr>
                                <td align="center">
                                    <img src="cid:logo" alt="PetShere Logo" style="width: 250px;">
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <h1 style="color: #4DAF50; text-align: center;">Bienvenido a PetShere</h1>
                                    <p style="font-size: 16px; text-align: center;">
                                        <strong>¡Gracias por unirte a PetShere, %s!</strong>
                                    </p>
                                    <p style="font-size: 14px; text-align: left;">
                                        Estamos emocionados de tenerte con nosotros. Ahora puedes comenzar a disfrutar\s
                                        de todas las funciones que PetShere ofrece para ti y tu mascota.
                                    </p>
                                    <p style="font-size: 14px; text-align: left;">
                                        Si tienes alguna pregunta, no dudes en contactarnos.
                                    </p>
                                    <hr>
                                    <p style="font-size: 14px; text-align: left;">Atentamente,</p>
                                    <p style="font-weight: bold; text-align: left;">El equipo de PetShere</p>
                                </td>
                            </tr>
                        </table>
                    </body>
                </html>
                """.formatted(userName);

        /*
                        <html>
                    <body style="text-align: left; font-family: Arial, sans-serif;">
                        <div style="text-align: center;">
                            <img src="cid:logo" style="width: 80px; margin-bottom: 20px;"/>
                        </div>
                        <h1 style="color: #4DAF50; text-align: center;">¡Bienvenido a PetShere!</h1>
                        <strong>¡Gracias por unirte a PetShere, %s!</strong>
                        <p>
                            Estamos emocionados de tenerte con nosotros. Ahora puedes comenzar a disfrutar de todas lass
                            funciones que PetShere ofrece para ti y tu mascota.
                        </p>
                        <p>Si tienes alguna pregunta, no dudes en contactarnos.</p>
                        <hr>
                        <p style="font-size: 14px;">Atentamente,</p>
                        <p style="font-weight: bold;">El equipo de PetShere</p>
                    </body>
                </html>
        */
    }


}

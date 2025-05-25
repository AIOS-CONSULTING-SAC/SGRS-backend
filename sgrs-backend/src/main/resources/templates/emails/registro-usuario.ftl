<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Bienvenid@ a SGRS</title>
</head>
<body style="font-family: Arial, sans-serif; background-color: #f6f8fa; margin: 0; padding: 20px;">
    <table width="100%" cellpadding="0" cellspacing="0" style="max-width: 600px; margin: auto; background-color: #ffffff; border-radius: 8px; box-shadow: 0 0 10px rgba(0,0,0,0.05);">
        <tr>
            <td style="background-color: #007b5e; padding: 20px; text-align: center; border-top-left-radius: 8px; border-top-right-radius: 8px;">
                <h2 style="color: #ffffff; margin: 0;">Sistema de Gestión de Residuos Sólidos - SGRS</h2>
            </td>
        </tr>
        <tr>
            <td style="padding: 30px;">
                <p style="font-size: 16px;">Hola <strong>${nombre}</strong>,</p>

                <p style="font-size: 16px;">
                    Te damos la bienvenida al <strong>Sistema de Gestión de Residuos Sólidos (SGRS)</strong>.
                    Tu usuario ha sido creado exitosamente por un administrador del sistema.
                </p>

                <p style="font-size: 16px;">
                    A continuación te compartimos tus credenciales de acceso:
                </p>

                <ul style="font-size: 16px; padding-left: 20px;">
                    <li><strong>Usuario:</strong> ${email}</li>
                    <li><strong>Contraseña:</strong> ${contrasena}</li>
                </ul>

                <p style="font-size: 16px;">
                    Puedes ingresar al sistema desde la siguiente dirección:
                    <br/>
                    <a href=${link} target="_blank" style="color: #007b5e;">${link}</a>
                </p>

                <p style="font-size: 16px;">
                    Por favor, una vez dentro del sistema, se recomienda cambiar tu contraseña por seguridad.
                </p>

                <p style="font-size: 16px;">
                    Si tienes alguna duda, no dudes en contactarnos.
                </p>

                <p style="font-size: 16px;">
                    Atentamente,<br/>
                    <strong>${empresa}</strong>
                </p>
            </td>
        </tr>
        <tr>
            <td style="background-color: #f0f0f0; padding: 10px 20px; text-align: center; font-size: 12px; color: #888;">
                Sistema desarrollado por
                <a href="http://aios-consulting.com/" target="_blank" style="color: #007b5e; text-decoration: none;">
                    AIOS Consulting SAC
                </a>
                &nbsp;|&nbsp; © ${year}
            </td>
        </tr>
    </table>
</body>
</html>

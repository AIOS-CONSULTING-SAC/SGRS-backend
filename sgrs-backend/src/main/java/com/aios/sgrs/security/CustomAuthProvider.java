package com.aios.sgrs.security;

import com.aios.sgrs.dao.UsuarioDao;
import com.aios.sgrs.model.response.seguridad.UsuarioLogeadoResponse;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class CustomAuthProvider implements AuthenticationProvider {
    private final UsuarioDao usuarioDao;

    public CustomAuthProvider(UsuarioDao usuarioDao) {
        this.usuarioDao = usuarioDao;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String correo = authentication.getName();
        String password = authentication.getCredentials().toString();

        UsuarioLogeadoResponse result = usuarioDao.iniciarSesion(correo, password);

        if (result.getResultado() == 0 || result.getResultado() == 1 || result.getResultado() == 2) {
            throw new BadCredentialsException(result.getMensaje());
        }

        // Successful
        // Assign ROLE_USER or ROLE_ADMIN based on rolId or tipoUsuario
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(
                result.getRolId() != null ? "ROLE_" + result.getRolId() : "ROLE_USER");

        return new UsernamePasswordAuthenticationToken(
                result.getNombres(), password, Collections.singletonList(authority)
        );
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
package org.application.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.application.dto.UtilisateurDto;
import org.application.service.UtilisateurService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.HashSet;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;

//@AutoConfigureMockMvc(addFilters = false) // pour désactiver le filtre de sécurité qui bloque le test par 403
@WebMvcTest(controllers = UtilisateurController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UtilisateurService utilisateurService;


    @Test
    @DisplayName("Tester la création d'un utilisateur")
    void creerUtilisateurTest() throws Exception {
        // init:
        UtilisateurDto utilisateurDto = UtilisateurDto.builder()
                .userId(1L)
                .login("Login")
                .email("email@gmaiL.fr")
                .nom("nom")
                .prenom("prenom")
                .roles(new HashSet<>())
                .build();

        UserDetails userDetails = User.builder()
                .username("admin")
                .password("{noop}admin")
                .authorities(new String[] {"ADMIN", "USER"})
                .build();


        given(utilisateurService.creerUtilisateur(ArgumentMatchers.any(UtilisateurDto.class)))
                .willAnswer(invocation -> invocation.getArgument(0)); // retourner le premier argument


        // action:
        ResultActions reponse = mockMvc.perform(MockMvcRequestBuilders.post("/user")
                .with(jwt().authorities(new SimpleGrantedAuthority("USER")))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(utilisateurDto))
        );


        // vérification:
        reponse.andDo(MockMvcResultHandlers.print()) // pour afficher la réponse
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.userId", CoreMatchers.is(Integer.valueOf(utilisateurDto.getUserId().toString()))))
                .andExpect(MockMvcResultMatchers.jsonPath("$.login", CoreMatchers.is(utilisateurDto.getLogin())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nom", CoreMatchers.is(utilisateurDto.getNom())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.prenom", CoreMatchers.is(utilisateurDto.getPrenom())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", CoreMatchers.is(utilisateurDto.getEmail())));
    }


}

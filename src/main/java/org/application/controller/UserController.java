package org.application.controller;

import org.application.dto.UserDto;
import org.application.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/{userId}")
    @PreAuthorize("hasAnyAuthority('SCOPE_USER')")
    public UserDto recupererUtilisateurParId( @PathVariable(name = "userId") Long id) {
        return userService.recupererUtilisateurParId(id);
    }

    @DeleteMapping("/{userId}")
    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN')")
    public UserDto supprimerUtilisateur( @PathVariable(name = "userId") Long id) {
        return userService.recupererUtilisateurParId(id);
    }


    @PostMapping
    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN')")
    public UserDto creerUtilisateur(@RequestBody UserDto userDto) {
        return userService.creerUtilisateur(userDto);
    }

}

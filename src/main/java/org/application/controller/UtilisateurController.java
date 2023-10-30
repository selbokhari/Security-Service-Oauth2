package org.application.controller;

import org.application.dto.UtilisateurDto;
import org.application.service.UtilisateurService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/user")
public class UtilisateurController {

    private final UtilisateurService utilisateurService;

    @GetMapping("/{userId}")
    @PreAuthorize("hasAnyAuthority('SCOPE_USER')")
    public ResponseEntity<UtilisateurDto> recupererUtilisateurParId(@PathVariable(name = "userId") Long id) {
        return ResponseEntity.ok(utilisateurService.recupererUtilisateurParId(id));
    }

    @DeleteMapping("/{userId}")
    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN')")
    public ResponseEntity<Void> supprimerUtilisateur(@PathVariable(name = "userId") Long id) {
        utilisateurService.supprimerUtilisateur(id);
        return ResponseEntity.ok().build();
    }


    @PostMapping
    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN')")
    public ResponseEntity<UtilisateurDto> creerUtilisateur(@RequestBody UtilisateurDto utilisateurDto) {
        return new ResponseEntity<>(utilisateurService.creerUtilisateur(utilisateurDto), HttpStatus.CREATED);
    }

}

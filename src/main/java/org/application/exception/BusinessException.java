package org.application.exception;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {

    public enum Raison {
        UTILISATEUR_NON_TROUVEE(1,"Uilisateur non trouvé");

        private Integer code;

        private String message;

        public Integer getCode() {
            return code;
        }

        public String getMessage() {
            return message;
        }

        Raison(int i, String s) {
            this.code= i;
            this.message = s;
        }

    }

    private int code;

    public BusinessException(Raison raison) {
        super(raison.getMessage());
        this.code = raison.code;
    }


}

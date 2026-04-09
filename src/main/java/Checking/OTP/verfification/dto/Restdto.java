package Checking.OTP.verfification.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter

public class Restdto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String email;
    private String otp;
    private String newPassword;
}

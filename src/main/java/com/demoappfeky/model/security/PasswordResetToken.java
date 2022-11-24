package com.demoappfeky.model.security;

import com.demoappfeky.model.Users;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

@Entity
@Getter
@Setter
@ToString // toString for Fields
public class PasswordResetToken {
    private static final int EXPIRATION = 60 * 24;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String token;

    @OneToOne(targetEntity = Users.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false,name = "user_id")
    private Users user;

    private Date expiryDate;

    public PasswordResetToken(){}

    public PasswordResetToken(final String token , final Users user){
        super();

        this.token = token;
        this.user = user;
        this.expiryDate = calculateExoiryDate(EXPIRATION);
    }

    private Date calculateExoiryDate(final int expiryTimeInMin) {
        final Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(new Date().getTime());
        cal.add(Calendar.MINUTE, expiryTimeInMin);

        return new Date(cal.getTime().getTime());
    }

    public void updateToken (final String token){
        this.token = token;
        this.expiryDate = calculateExoiryDate(EXPIRATION);
    }

}
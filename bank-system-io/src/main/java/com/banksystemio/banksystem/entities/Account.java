package com.banksystemio.banksystem.entities;

import com.banksystemio.banksystem.enums.Status;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Account {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome não pode estar em branco")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "O nome não pode conter caracteres especiais")
    private String name;

    @Email(message = "O email deve ser válido")
    private String email;

    @NotNull(message = "O número da conta não pode estar vazio")
    @PositiveOrZero
    private Integer accountNumber;

    @DecimalMin(value = "0.00", message = "O saldo deve ser maior ou igual a zero")
    private BigDecimal balance;

    @NotBlank(message = "A senha não pode estar em branco")
    @Size(min = 6, max = 20, message = "A senha deve ter entre 6 e 20 caracteres")
    private String password;

    @Enumerated(EnumType.STRING)
    private Status accountStatus;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private final LocalDate since;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private List<Deposit> depositRequests = new ArrayList<>();

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private List<Withdraw> withdrawRequests = new ArrayList<>();

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private List<Transfer> transferRequests = new ArrayList<>();

    public Account () {
        this.since = LocalDate.now();
        this.balance = BigDecimal.ZERO;
    }

}

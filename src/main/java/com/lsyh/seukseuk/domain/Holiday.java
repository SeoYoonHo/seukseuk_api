package com.lsyh.seukseuk.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class Holiday {
    @Id
    @Column(nullable = false)
    private LocalDate date;
    private String description;
}
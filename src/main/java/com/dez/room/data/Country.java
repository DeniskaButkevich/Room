package com.dez.room.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sxgeo_country")
public class Country {
    @Id
    private short id;
    @Length(max = 2)
    private String  iso;
    @Length(max = 2)
    private String  continent;
    @Length(max = 128)
    private String nameRu;
    @Column(name = "name_en")
    @Length(max = 128)
    private String nameEn;
    @Digits(integer=6, fraction=2)
    private BigDecimal lat;
    @Digits(integer=6, fraction=2)
    private BigDecimal lon;
    @Length(max = 30)
    private String timezone;
}

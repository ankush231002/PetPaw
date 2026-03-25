package com.cat.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name="info")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Info {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ownerName;
    private String petName;
    private String phone;

    private String imagePath;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @Column(unique = true)
    private String publicUrl;

    private String imagePublicId;
}
